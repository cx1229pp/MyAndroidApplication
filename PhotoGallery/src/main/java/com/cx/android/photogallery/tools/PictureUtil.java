package com.cx.android.photogallery.tools;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by ldn on 2015/10/2.
 */
public class PictureUtil {
    public static BitmapDrawable getScaledBitmapDrawable(Activity a,String path){
        Display display = a.getWindowManager().getDefaultDisplay();
        float defaultWidth = display.getWidth();
        float defaultHeight = display.getHeight();
        Log.d("PictureUtil","defaultWidth:"+defaultWidth);
        Log.d("PictureUtil","defaultHeight:"+defaultHeight);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path,options);

        float imageWidth = options.outWidth;
        float imageHeight = options.outHeight;
        int inSampleSize = 1;
        if(imageWidth > defaultWidth || imageHeight > defaultHeight){
            if(imageWidth > imageHeight){
                inSampleSize = Math.round(imageHeight / imageWidth);
            }else{
                inSampleSize = Math.round(imageWidth / imageHeight);
            }
        }

        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;

        Bitmap bitmap = BitmapFactory.decodeFile(path,options);
        return new BitmapDrawable(a.getResources(),bitmap);
    }

    public static Bitmap getScaledBitmapDrawable(Activity a,InputStream is){
        Display display = a.getWindowManager().getDefaultDisplay();
        float defaultWidth = display.getWidth();
        float defaultHeight = display.getHeight();
        Log.d("PictureUtil","defaultWidth:"+defaultWidth);
        Log.d("PictureUtil","defaultHeight:"+defaultHeight);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is,null,options);

        float imageWidth = options.outWidth;
        float imageHeight = options.outHeight;
        Log.d("PictureUtil","imageWidth:"+imageWidth);
        Log.d("PictureUtil","imageHeight:"+imageHeight);
        int inSampleSize = 1;
        if(imageWidth > defaultWidth || imageHeight > defaultHeight){
            if(imageWidth > imageHeight){
                inSampleSize = Math.round(imageHeight / imageWidth);
            }else{
                inSampleSize = Math.round(imageWidth / imageHeight);
            }
        }

        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
        Log.d("PictureUtil","inSampleSize:"+inSampleSize);

        Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
        return bitmap;
    }

    public static void cleanBitmap(BitmapDrawable bitmapDrawable){
        if(bitmapDrawable != null){
            cleanBitmap(bitmapDrawable.getBitmap());
        }
    }

    public static void cleanBitmap(ImageView imageView){
        if(!(imageView.getDrawable() instanceof BitmapDrawable)) return;

        cleanBitmap((BitmapDrawable)imageView.getDrawable());
    }

    public static void cleanBitmap(Bitmap bitmap){
        if(bitmap != null){
            bitmap.recycle();
        }
    }
}
