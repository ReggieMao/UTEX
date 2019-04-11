package com.utex.listener;


import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;


/**
 * Created by Demon on 2018/10/16.
 */
public class MediaContentObserver extends ContentObserver {

    private final ContentResolver mContentResolver;

    private static final String[] MEDIA_PROJECTIONS = {
            MediaStore.Images.ImageColumns.DATA,
            MediaStore.Images.ImageColumns.DATE_TAKEN
    };

    private static final String[] KEYWORDS = {
            "screenshot", "screen_shot", "screen-shot", "screen shot", "screencapture", "screen_capture", "screen-capture", "screen capture",
            "screencap", "screen_cap", "screen-cap", "screen cap", "截屏"
    };
    private static long lastDateTaken;
    private final boolean isExnowSet;
    private MediaListener mediaListener;

    /**
     * Creates a content observer.
     *
     * @param handler         The handler to run {@link #onChange} on, or null if none.
     * @param contentResolver
     */
    public MediaContentObserver(Handler handler, ContentResolver contentResolver, boolean isExnowSet) {
        super(handler);
        this.mContentResolver = contentResolver;
        this.isExnowSet = isExnowSet;
    }

//    @Override
//    public void onChange(boolean selfChange) {
//        super.onChange(selfChange);
//        if (ExnowApplication.getInstance().getProcessPid() == android.os.Process.myPid()) {
//            Log.e("Hello", "Test:" + ExnowApplication.getInstance().getProcessPid() + "     " + android.os.Process.myPid());
//        }
//    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        handleMediaContentChange(uri);
    }

    /**
     * 读取媒体数据库时需要读取的列, 其中 WIDTH 和 HEIGHT 字段在 API 16 以后才有
     */
    private static final String[] MEDIA_PROJECTIONS_API_16 = {
            MediaStore.Images.ImageColumns.DATA,
            MediaStore.Images.ImageColumns.DATE_TAKEN,
            MediaStore.Images.ImageColumns.WIDTH,
            MediaStore.Images.ImageColumns.HEIGHT,
    };


    private void handleMediaContentChange(Uri contentUri) {
        Cursor cursor = null;
        try {
            cursor = mContentResolver.query(
                    contentUri,
                    Build.VERSION.SDK_INT < 16 ? MEDIA_PROJECTIONS : MEDIA_PROJECTIONS_API_16,
                    null,
                    null,
                    MediaStore.Images.ImageColumns.DATE_ADDED + " desc limit 1");

            if (cursor == null) {
                return;
            }

            if (!cursor.moveToFirst()) {
                return;
            }
            int dataIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            int dateTakenIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN);
            String data = cursor.getString(dataIndex);
            long dateTaken = System.currentTimeMillis();
            Log.e("UMCall", data);
            handleMediaRowData(data, dateTaken);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static long lastDate;

    private synchronized void handleMediaRowData(String path, long dateTaken) {
        long duration = 0;
        long step = 100;

        while (!checkScreenShot(path) && duration <= 500) {
            try {
                duration += step;
                Thread.sleep(step);
            } catch (Exception e) {

            }
        }

        if (lastDate == dateTaken || (dateTaken - lastDate) < 500) {
            return;
        }

        if (checkScreenShot(path) && isExnowSet) {
            //回调回去
            lastDate = dateTaken;
            mediaListener.call(path);
        }
    }


    private boolean checkScreenShot(String path) {
        if (path == null) {
            return false;
        }

        path = path.toLowerCase();
        for (String keyWord : KEYWORDS) {
            if (path.contains(keyWord)) {
                return true;
            }
        }

        return false;
    }

    public interface MediaListener {
        void call(String path);
    }

    public void setMediaListener(MediaListener mediaListener) {
        this.mediaListener = mediaListener;
    }

}
