package com.cx.android.photogallery.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cx.android.photogallery.R;
import com.cx.android.photogallery.model.GalleryItem;
import com.cx.android.photogallery.tools.DiskLruCacheUtil;
import com.cx.android.photogallery.tools.PictureUtil;
import com.cx.android.photogallery.tools.ThumbnailDownloader;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by 陈雪 on 2015/10/9.
 */
public class PhotoGalleryAdapter extends BaseAdapter{
    private Context mContext;
    private List<GalleryItem> mItems;
    private ThumbnailDownloader<ImageView> mThumbnailDownloader;
    private LruCache<String,Bitmap> mLruCache;
    private DiskLruCacheUtil mDiskLruCacheUtil;

    public PhotoGalleryAdapter(Context mContext, List<GalleryItem> mItems,
                               ThumbnailDownloader<ImageView> mThumbnailDownloader
                               ,DiskLruCacheUtil mDiskLruCacheUtil
                                ,LruCache<String,Bitmap> mLruCache) {
        this.mContext = mContext;
        this.mItems = mItems;
        this.mThumbnailDownloader = mThumbnailDownloader;
        this.mDiskLruCacheUtil = mDiskLruCacheUtil;
        this.mLruCache = mLruCache;
    }

    @Override
    public int getCount() {
        if(mItems != null && mItems.size() > 0){
            return mItems.size();
        }else{
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if(mItems != null && mItems.size() > 0){
            return mItems.get(position);
        }else{
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GalleryItem item = mItems.get(position);
        if(item == null)return null;

        View view;
        ViewHandler viewHandler;
        if(convertView == null){
            viewHandler = new ViewHandler();
            view = View.inflate(mContext, R.layout.photo_gallery_item,null);
            viewHandler.itemImage = (ImageView) view.findViewById(R.id.iv_item_image);
            viewHandler.itemTitle = (TextView) view.findViewById(R.id.tv_item_title);

            view.setTag(viewHandler);
        }else{
            view = convertView;
            viewHandler = (ViewHandler) view.getTag();
        }

        String imageUrl = item.getImageUrl();
        viewHandler.itemImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.item_default));
        viewHandler.itemImage.setTag(imageUrl);

        InputStream is = null;
        try{
            Bitmap bitmap = mLruCache.get(imageUrl);
            is = mDiskLruCacheUtil.getDiskCache(imageUrl);
            if(bitmap != null){
                Log.d("PhotoGalleryAdapter","内存");
                viewHandler.itemImage.setImageBitmap(bitmap);
            }else if(is != null){
                Log.d("PhotoGalleryAdapter","硬盘");
                is = mDiskLruCacheUtil.getDiskCache(imageUrl);

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                viewHandler.itemImage.setImageBitmap(BitmapFactory.decodeStream(is,null,options));
            }else{
                Log.d("PhotoGalleryAdapter","网络");
                mThumbnailDownloader.downloader(viewHandler.itemImage, imageUrl);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        viewHandler.setText(item.getTitle());

        return view;
    }

    private class ViewHandler{
        TextView itemTitle;
        ImageView itemImage;

        private void setText(String title) {
            itemTitle.setText(title);
        }
    }
}
