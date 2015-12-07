package com.cx.android.photogallery.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.cx.android.photogallery.R;
import com.cx.android.photogallery.activity.PhotoViewActivity;
import com.cx.android.photogallery.adapter.PhotoGalleryAdapter;
import com.cx.android.photogallery.model.GalleryItem;
import com.cx.android.photogallery.service.PhotoService;
import com.cx.android.photogallery.tools.DiskLruCacheUtil;
import com.cx.android.photogallery.tools.HttpUtil;
import com.cx.android.photogallery.tools.PhotoGalleryFetcher;
import com.cx.android.photogallery.tools.ThumbnailDownloader;

import java.util.List;

/**
 * Created by 陈雪 on 2015/10/8.
 */
public class PhotoGalleryFragment extends Fragment{
    private GridView mPhotoGridView;
    private List<GalleryItem> mItems;
    private ThumbnailDownloader<ImageView> mThumbnailDownloader;
    private LruCache<String,Bitmap> mLruCache;
    private DiskLruCacheUtil mDiskLruCacheUtil;
    private SearchView mSearchView;
    private SharedPreferences mSharedPreferences;
    private static final String QUERY_TAG = "query";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String query = mSharedPreferences.getString(QUERY_TAG,"");
        updateDatas(query);

        Intent i = new Intent(getActivity(), PhotoService.class);
        getActivity().startService(i);

        int cacheSize = 4 * 1024 * 1024; // 4MiB
        mLruCache = new LruCache<String,Bitmap>(cacheSize){
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();}};

        mDiskLruCacheUtil = new DiskLruCacheUtil(getActivity(),DiskLruCacheUtil.FORDLER_BITMAP);
        mThumbnailDownloader = new ThumbnailDownloader<>(new Handler(),mDiskLruCacheUtil,mLruCache);
        mThumbnailDownloader.setmHandleResponseListener(new ThumbnailDownloader.HandleResponseListener<ImageView>() {
            @Override
            public void handleResponse(String url, Bitmap bitmap) {
                if (isVisible()) {
                    ImageView imageView = (ImageView) mPhotoGridView.findViewWithTag(url);
                    if (imageView != null) {
                        imageView.setImageBitmap(bitmap);
                    }
                }
            }
        });
        mThumbnailDownloader.start();
        mThumbnailDownloader.getLooper();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_gallery, container, false);
        mPhotoGridView = (GridView) view.findViewById(R.id.gv_photo_gallery);
        mPhotoGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //if(hasBrower()){
                    String url = mItems.get(position).getImageUrl();
                    Uri uri = Uri.parse(url);
                    Intent i = new Intent(getActivity(), PhotoViewActivity.class);
                    i.setData(uri);
                    startActivity(i);
               /* }else{
                    Toast.makeText(getActivity(),getString(R.string.hasNotBrower),Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        mSearchView = (SearchView) view.findViewById(R.id.sv_search);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                updateDatas(query);

                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putString(QUERY_TAG, query);
                editor.apply();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return view;
    }

    private boolean hasBrower(){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.addCategory(Intent.CATEGORY_BROWSABLE);
        List<ResolveInfo> list = getActivity().getPackageManager().queryIntentActivities(i, PackageManager.GET_INTENT_FILTERS);
        return list != null && list.size() > 0;
    }

    private class GetPhotoGallery extends AsyncTask<String,Void,List<GalleryItem>>{

        @Override
        protected List<GalleryItem> doInBackground(String... params) {
            return new PhotoGalleryFetcher().getPhotoList(params[0]);
        }

        @Override
        protected void onPostExecute(List<GalleryItem> galleryItems) {
            mItems = galleryItems;
            setAdapter();
        }
    }

    private void setAdapter(){
        if(getActivity() == null || mPhotoGridView == null)return;

        if(mItems != null){
            mPhotoGridView.setAdapter(new PhotoGalleryAdapter(getActivity(),mItems,mThumbnailDownloader
            ,mDiskLruCacheUtil,mLruCache));
        }else{
            mPhotoGridView.setAdapter(null);
        }
    }

    private void updateDatas(String keywords){
        new GetPhotoGallery().execute(keywords);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mThumbnailDownloader.cleanTheard();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mThumbnailDownloader.quit();
        mLruCache.evictAll();
    }
}
