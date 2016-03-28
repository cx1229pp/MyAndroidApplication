package com.cx.android.weather.data.cache;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.jakewharton.disklrucache.DiskLruCache;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * 硬盘缓存工具类
 * Created by 陈雪 on 2015/10/15.
 */
public class DiskLruCacheUtil {
    private Context mContext;
    private String mFordlerName;
    private DiskLruCache mDiskLruCache = null;

    public DiskLruCacheUtil(Context context, String fordlerName){
        mContext = context;
        mFordlerName = fordlerName;
        getInstance();
    }

    public void getInstance(){
        File file = getDiskCacheDir(mContext,mFordlerName);
        if(!file.exists()){
            file.mkdirs();
        }

        try {
            mDiskLruCache = DiskLruCache.open(file, getAppVersion(mContext), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeDiskCache(String key,byte[] data) throws IOException {
        if(mDiskLruCache == null)return;
        DiskLruCache.Editor editor = null;
        try {
            editor = mDiskLruCache.edit(hashKeyForDisk(key));
            if(editor != null){
                OutputStream out = editor.newOutputStream(0);
                IOUtils.write(data, out);
                editor.commit();
            }
        } catch (IOException e) {
            if(editor != null){
                editor.abort();
            }
            e.printStackTrace();
        }

        mDiskLruCache.flush();
    }

    public void writeDiskCache(String key,String data) throws IOException {
        if(mDiskLruCache == null)return;
        DiskLruCache.Editor editor = null;
        try {
            editor = mDiskLruCache.edit(hashKeyForDisk(key));
            if(editor != null){
                OutputStream out = editor.newOutputStream(0);
                IOUtils.write(data, out);
                editor.commit();
            }
        } catch (IOException e) {
            if(editor != null){
                editor.abort();
            }
            e.printStackTrace();
        }

        mDiskLruCache.flush();
    }

    public InputStream getDiskCache(String key) throws IOException {
        if(mDiskLruCache == null)return null;
        DiskLruCache.Snapshot snapshot = mDiskLruCache.get(hashKeyForDisk(key));
        if(snapshot != null){
            return snapshot.getInputStream(0);
        }

        return null;
    }

    public String getStringDiskCache(String key){
        InputStream in = null;
        try {
            in = getDiskCache(key);
            if(in == null)return null;

            List list = IOUtils.readLines(in);
            if(list != null && list.size() > 0){
                return list.get(0).toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    public int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xFF & aByte);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
