package com.company.photogallery.thread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import com.company.photogallery.network.FlickerFetchr;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hectorleyvavillanueva on 1/5/17.
 */

public class ThumbnailDownloader<T> extends HandlerThread {
    private static final String TAG = ThumbnailDownloader.class.getSimpleName();
    private static final int MESSAGE_DOWNLOAD = 0;


    private Handler mRequestHandler;
    private ConcurrentHashMap<T, String> mRequestMap = new ConcurrentHashMap<>();
    private Handler mResponseHandler;
    private ThumbnailDownloadListener<T> mThumbnailDownloadListener;


    public interface ThumbnailDownloadListener<T>{
        void onThumbnailDownload(T target, Bitmap thumbnail);
    }

    public void setThumbnailDownloadListener(ThumbnailDownloadListener<T> listener) {
        mThumbnailDownloadListener = listener;
    }

    public ThumbnailDownloader(Handler response) {
        super(TAG);
        mResponseHandler = response;
    }

    @Override
    protected void onLooperPrepared() {
        mRequestHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                T target = (T) msg.obj;
                Log.i(TAG, "Got a request for URL :" + mRequestMap.get(target));
                handleRequest(target);
            }
        };
    }


    public void queueThumbnail(T target, String url) {
        Log.i(TAG, "Got a url: " + url);

        if (url == null) {
            mRequestMap.remove(target);
        }else{
            mRequestMap.put(target, url);
            mRequestHandler.obtainMessage(MESSAGE_DOWNLOAD, target)
                    .sendToTarget();
        }

    }


    public void clearQueue() {
        mRequestHandler.removeMessages(MESSAGE_DOWNLOAD);
    }


    private void handleRequest(final T target) {
        try{
            final String url = mRequestMap.get(target);
            if (url == null) {
                return;
            }
            byte[] bitmapBytes = new FlickerFetchr().getUrlBytes(url);
            final Bitmap bitmap = BitmapFactory
                    .decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
            Log.i(TAG, "Bitmap created");

            mResponseHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mRequestMap.get(target) != url) {
                        return;
                    }
                    mRequestMap.remove(target);
                    mThumbnailDownloadListener.onThumbnailDownload(target, bitmap);
                }
            });

        }catch (Exception e){
            Log.e(TAG, "Error downloading the image", e);
        }
    }

}
