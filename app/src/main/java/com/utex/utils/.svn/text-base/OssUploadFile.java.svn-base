package com.utex.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.GetObjectResult;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.utex.R;
import com.utex.common.SDUrl;
import com.utex.core.UTEXApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Demon on 2018/7/20.
 */
public class OssUploadFile {

    private OSS oss;

    private String ossHosts = "https://www.utex.sg/images/";

    private String identity = "utex/";

    private String apk = "apk/";

    private String bucketName = "utex-photo-server";

    private Notification.Builder builder;

    private NotificationManager notificationManager;
    private NotificationCompat.Builder builderCompat;

    public void init(Context context, String AccessKeyId, String SecretKeyId, String SecurityToken) {
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";

        //if null , default will be init
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(60 * 1000 * 60); // connction time out default 15s
        conf.setSocketTimeout(60 * 1000 * 60); // socket timeout，default 15s
        conf.setMaxConcurrentRequest(5); // synchronous request number，default 5
        conf.setMaxErrorRetry(2); // retry，default 2
        OSSLog.enableLog(); //write local log fie ,path is SDCard_path\OSSLog\logs.csv

        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(AccessKeyId, SecretKeyId, SecurityToken);

        oss = new OSSClient(context, endpoint, credentialProvider, conf);
    }

    public void downFile(String apkName) {
        GetObjectRequest get = new GetObjectRequest(bucketName, apk + apkName);

        get.setProgressListener(new OSSProgressCallback<GetObjectRequest>() {
            @Override
            public void onProgress(GetObjectRequest request, long progress, long totalSize) {
                //ho 这是下载进度
                if (builder == null) {
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
                                .setProgress((int) totalSize, (int) progress, false)
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
                                .setProgress((int) totalSize, (int) progress, false)
                                .setSmallIcon(android.R.drawable.stat_notify_more)
                                .setAutoCancel(true);
                        builderCompat.setVibrate(new long[]{0});
                        notificationManager.notify(1, builderCompat.build());
                    }

                }
                if ((progress * 1000) % progress * 1000 == 0) {
                    if (builderCompat != null) {
                        builderCompat.setContentText("下载中..." + ArithmeticUtil.div(progress, totalSize, 4) * 100 + "%");
                        builderCompat.setProgress((int) totalSize, (int) progress, false);
                        notificationManager.notify(1, builderCompat.build());
                    } else {
                        builder.setContentText("下载中..." + ArithmeticUtil.div(progress, totalSize, 4) * 100 + "%");
                        builder.setProgress((int) totalSize, (int) (progress), false);
                        notificationManager.notify(1, builder.build());
                    }

                }
            }
        });

        OSSAsyncTask task = oss.asyncGetObject(get, new OSSCompletedCallback<GetObjectRequest, GetObjectResult>() {

            @Override
            public void onSuccess(GetObjectRequest request, GetObjectResult result) {
                // Request succeeds
                Log.d("TAG", "Down Success");
//                responseHeader [x-oss-storage-class]: Standard


                InputStream inputStream = result.getObjectContent();
                write("exnow.apk", inputStream);

            }

            @Override
            public void onFailure(GetObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // Request exception
                if (clientExcepion != null) {
                    // Local exception, such as a network exception
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // Service exception
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
    }


    private void write(String filename, InputStream in) {

        File file = new File(SDUrl.apkPath);
        if (!file.exists()) {
            if (!file.mkdirs()) {//若创建文件夹不成功
                System.out.println("Unable to create external cache directory");
            }
        }

        File targetfile = new File(SDUrl.apkPath + filename);
        OutputStream os = null;
        try {
            os = new FileOutputStream(SDUrl.apkPath + filename);
            int ch = 0;
            while ((ch = in.read()) != -1) {
                os.write(ch);
            }
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void upLoadFile(String filePath) {
        final String fileName = UTEXApplication.getLoginUser().getUid() + System.currentTimeMillis() + ".jpg";

        Log.e("TAG", filePath);

        PutObjectRequest put = new PutObjectRequest(bucketName, identity + fileName, filePath);

        // You can set progress callback during asynchronous upload
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);

            }
        });

        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d("PutObject", "UploadSuccess");
                if (ossUploadFileListener != null) {
                    ossUploadFileListener.onSuccess(ossHosts + identity + fileName);
                }
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // Request exception
                if (clientExcepion != null) {
                    // Local exception, such as a network exception
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // Service exception
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
                if (ossUploadFileListener != null) {
                    ossUploadFileListener.onFailure();
                }

            }
        });

        // task.cancel(); // Cancel the task
        // task.waitUntilFinished(); // Wait till the task is finished as needed

    }

    private OssUploadFileListener ossUploadFileListener;

    public void setOssUploadFileListener(OssUploadFileListener ossUploadFileListener) {
        this.ossUploadFileListener = ossUploadFileListener;
    }

    public interface OssUploadFileListener {

        void onSuccess(String fileName);

        void onFailure();

    }

}
