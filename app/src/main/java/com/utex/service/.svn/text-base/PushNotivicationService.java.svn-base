//package com.exnow.service;
//
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.BitmapFactory;
//import android.os.Build;
//import android.support.v4.app.NotificationCompat;
//import android.util.Log;
//
//import com.exnow.R;
//import com.exnow.core.ExnowApplication;
//import com.exnow.mvp.splash.view.SplashActivity;
//import com.umeng.message.UmengMessageService;
//
//import org.android.agoo.common.AgooConstants;
//
///**
// * Created by Demon on 2018/11/2.
// */
//public class PushNotivicationService extends UmengMessageService {
//    private NotificationManager notificationManager;
//    private Notification.Builder builder;
//    private NotificationCompat.Builder builderCompat;
//
//    @Override
//    public void onMessage(Context context, Intent intent) {
//        String message = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
//        Log.e("hello123456", message);
//
//        Intent intentP = new Intent(this,
//                SplashActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(
//                this, 0, intentP,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        notificationManager = (NotificationManager) UTEXApplication.getInstance().getSystemService(UTEXApplication.getInstance().NOTIFICATION_SERVICE);
//        if (Build.VERSION.SDK_INT >= 26) {
//            NotificationChannel channel = new NotificationChannel("exnow", "apk", NotificationManager.IMPORTANCE_DEFAULT);
//            channel.enableVibration(false);
//            notificationManager.createNotificationChannel(channel);
//
//            builder = new Notification.Builder(ExnowApplication.getInstance(), "exnow")
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .setLargeIcon(BitmapFactory.decodeResource(ExnowApplication.getInstance().getResources(), R.mipmap.ic_launcher))
//                    .setShowWhen(false)
//                    .setContentTitle("行情提示")
//                    .setContentIntent(pendingIntent)
//                    .setContentText("比特币")
//                    .setSmallIcon(android.R.drawable.stat_notify_more)
//                    .setAutoCancel(true);
//            notificationManager.notify(1, builder.build());
//        } else {
//            builderCompat = new NotificationCompat.Builder(ExnowApplication.getInstance())
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .setLargeIcon(BitmapFactory.decodeResource(ExnowApplication.getInstance().getResources(), R.mipmap.ic_launcher))
//                    .setShowWhen(false)
//                    .setContentTitle("行情提示")
//                    .setContentIntent(pendingIntent)
//                    .setContentText("比特币")
//                    .setSmallIcon(android.R.drawable.stat_notify_more)
//                    .setAutoCancel(true);
//            builderCompat.setVibrate(new long[]{0});
//            notificationManager.notify(1, builderCompat.build());
//        }
//    }
//}
