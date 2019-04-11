package com.utex.utils;

import android.content.Context;
import android.content.pm.PackageManager;

import com.utex.core.UTEXApplication;

public class APKVersionCodeUtils {
    /**
     * 获取当前本地apk的版本
     */
    public static int getVersionCode(Context mContext) {
        int versionCode = 0;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = mContext.getPackageManager().
                    getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取版本号名称
     */
    public static String getVerName() {
        String verName = "";
        try {
            verName = UTEXApplication.getInstance().getPackageManager().
                    getPackageInfo(UTEXApplication.getInstance().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }
}