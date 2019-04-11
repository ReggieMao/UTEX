package com.utex.service;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.utex.R;
import com.utex.common.FiledConstants;
import com.utex.common.SDUrl;
import com.utex.core.UTEXApplication;
import com.utex.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

/**
 * Created by Demon on 2017/7/18.
 * App下载
 */

public class DownLoadService extends IntentService {

    private Notification.Builder builder;

    private NotificationManager notificationManager;
    private NotificationCompat.Builder builderCompat;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public DownLoadService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        final int intExtra = intent.getIntExtra(FiledConstants.TYPE, 0);
        String downAddress = intent.getStringExtra(FiledConstants.DOWN_ADDRESS);
        OkHttpUtils
                .get()
                .url(downAddress)
                .build()
                .execute(new FileCallBack(SDUrl.apkPath, "exnow.apk") {
                    @Override
                    public void onResponse(File response, int id) {
                        //下载成功
                        Log.e("upload", "下载成功");

                        if (builder != null) {
                            builder.setAutoCancel(true);
                            builder.setContentTitle("Exnow-下载完成");
                            notificationManager.notify(1, builder.build());
                            notificationManager.cancelAll();
                        } else if (builderCompat != null) {
                            builderCompat.setAutoCancel(true);
                            builderCompat.setContentTitle("Exnow-下载完成");
                            notificationManager.notify(1, builderCompat.build());
                            notificationManager.cancelAll();
                        }

                        Intent broadcast = new Intent();
                        broadcast.setAction("com.download.app" + intExtra);
                        sendBroadcast(broadcast);

                    }

                    @SuppressLint("NewApi")
                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);

                        String showProgress = Utils.getScientificCountingMethodAfterData(Double.valueOf(progress) * 100, 4);
                        progress = Float.parseFloat(Utils.getScientificCountingMethodAfterData(Double.valueOf(progress), 4));

                        if (builder == null && builderCompat == null) {
                            notificationManager = (NotificationManager) UTEXApplication.getInstance().getSystemService(UTEXApplication.getInstance().NOTIFICATION_SERVICE);

                            if (Build.VERSION.SDK_INT >= 26) {
                                NotificationChannel channel = new NotificationChannel("exnow", "apk", NotificationManager.IMPORTANCE_DEFAULT);
                                channel.enableVibration(false);
                                notificationManager.createNotificationChannel(channel);

                                builder = new Notification.Builder(UTEXApplication.getInstance(), "exnow")
                                        .setOngoing(true)
                                        .setSmallIcon(R.mipmap.ic_launcher)
                                        .setLargeIcon(BitmapFactory.decodeResource(UTEXApplication.getInstance().getResources(), R.mipmap.ic_launcher))
                                        .setShowWhen(false)
                                        .setContentText("下载中..." + 0 + "%")
                                        .setProgress((int) total, (int) progress, false)
                                        .setSmallIcon(android.R.drawable.stat_notify_more)
                                        .setAutoCancel(true);
                                notificationManager.notify(1, builder.build());
                            } else {
                                builderCompat = new NotificationCompat.Builder(UTEXApplication.getInstance())
                                        .setOngoing(true)
                                        .setSmallIcon(R.mipmap.ic_launcher)
                                        .setLargeIcon(BitmapFactory.decodeResource(UTEXApplication.getInstance().getResources(), R.mipmap.ic_launcher))
                                        .setShowWhen(false)
                                        .setContentText("下载中..." + 0 + "%")
                                        .setProgress((int) total, (int) progress, false)
                                        .setSmallIcon(android.R.drawable.stat_notify_more)
                                        .setAutoCancel(true);
                                builderCompat.setVibrate(new long[]{0});
                                notificationManager.notify(1, builderCompat.build());
                            }

                        }

                        if ((progress * 1000) % progress * 1000 == 0) {
                            if (builderCompat != null) {
                                builderCompat.setContentText("下载中..." + showProgress + "%");
                                builderCompat.setProgress((int) total, (int) (progress * total), false);
                                notificationManager.notify(1, builderCompat.build());
                            } else {
                                builder.setContentText("下载中..." + showProgress + "%");
                                builder.setProgress((int) total, (int) (progress * total), false);
                                notificationManager.notify(1, builder.build());
                            }
                        }


                        Log.e("upload", progress + ":" + total);
                    }

                    @Override
                    public void onError(okhttp3.Call call, Exception e, int id) {
                        Log.e("upload", e.getMessage() + "");
                    }

                });
    }
}
