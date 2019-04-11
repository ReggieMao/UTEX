package com.utex.service;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;

import com.utex.common.Constants;
import com.utex.listener.MediaContentObserver;
import com.utex.mvp.share.ExnowShareActivity;
import com.utex.utils.ToastUtils;
import com.utex.widget.popuwindow.ShareSmallWindow;

/**
 * Created by Demon on 2018/10/16.
 */
public class ScreenService extends Service {

    private MediaContentObserver internalContentObserver;
    private MediaContentObserver externalContentObserver;
    private ShareSmallWindow shareSmallWindow;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("HelloService", "onCreate");
        //初始化截屏
        init(getContentResolver());
    }

    private void init(ContentResolver contentResolver) {
        HandlerThread handlerThread = new HandlerThread("screen");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        internalContentObserver = new MediaContentObserver(handler, contentResolver, true);
        externalContentObserver = new MediaContentObserver(handler, contentResolver, true);

        contentResolver.registerContentObserver(MediaStore.Images.Media.INTERNAL_CONTENT_URI, false, internalContentObserver);
        contentResolver.registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, false, externalContentObserver);
        internalContentObserver.setMediaListener(path -> ToastUtils.show("hello"));
        externalContentObserver.setMediaListener(path -> {
            Log.e("UM", path);
            Intent intent = new Intent(this, ExnowShareActivity.class);
            intent.putExtra(Constants.SHARE_PATH, path);
            startActivity(intent);
//            if (shareSmallWindow == null) {
//                shareSmallWindow = new ShareSmallWindow(ExnowApplication.getInstance());
//            } else {
//                shareSmallWindow.getDialogUtils().show();
//            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("HelloService", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (internalContentObserver != null) {
            this.getContentResolver().unregisterContentObserver(internalContentObserver);
        }

        if (externalContentObserver != null) {
            this.getContentResolver().unregisterContentObserver(externalContentObserver);
        }
    }
}
