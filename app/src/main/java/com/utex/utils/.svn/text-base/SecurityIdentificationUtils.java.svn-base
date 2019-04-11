package com.utex.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.util.Log;

import com.utex.R;
import com.utex.common.FiledConstants;
import com.utex.core.UTEXApplication;
import com.utex.widget.popuwindow.FingerprintAuthenticationPopupWindow;

/**
 * Created by Demon on 2018/7/24.
 * 安全识别，指纹跟图形解锁
 */
public class SecurityIdentificationUtils {

    private static FingerprintAuthenticationPopupWindow popupWindow;

    private static CancellationSignal cannel;

    /**
     * 显示指纹解锁页面
     *
     * @param context
     * @param parentRes
     */
    public static void fingerprintShow(Context context, int parentRes) {
        FingerprintManagerCompat manager = FingerprintManagerCompat.from(context);
        if (manager.isHardwareDetected() && manager.hasEnrolledFingerprints()) {
            popupWindow = new FingerprintAuthenticationPopupWindow(context, parentRes);
            popupWindow.show();
            popupWindow.setListener(() -> {
                securityIdentificationListener = null;
                if (cannel != null) {
                    cannel.cancel();
                }
            });
            cannel = new CancellationSignal();
            manager.authenticate(null, 0, cannel, new MyCallBack(), null);
        } else {
            //指纹已通过其他等等等方式取消
            SharedPreferencesUtil.putInteger(FiledConstants.HAS_SECURITY_IDENTIFICATION, 0);
        }
    }

    /**
     * 是否有这个指纹模块
     *
     * @return
     */
    public static boolean isHardwareDetected() {
        FingerprintManagerCompat manager = FingerprintManagerCompat.from(UTEXApplication.getInstance());
        if (manager.isHardwareDetected()) {
            //可以指纹解锁
            return true;
        } else {
            //没有指纹模块
            ToastUtils.show(Utils.getResourceString(R.string.bao_qian_nin_de_shou_ji_bu_neng_zhi_wen_jie_suo));
            return false;
        }
    }


    /**
     * 判断是否可以指纹解锁
     * 是否已经录制过指纹
     *
     * @return
     */
    public static boolean hasEnrolledFingerprints() {
        //有指纹模块,再执行这个
        if (isHardwareDetected()) {
            FingerprintManagerCompat manager = FingerprintManagerCompat.from(UTEXApplication.getInstance());
            if (manager.hasEnrolledFingerprints()) {
                return true;
            } else {
                //需要去录制指纹
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 去设置指纹页面
     */
    public static void goSetFingerprintsPage(Context context) {
        String brand = android.os.Build.BRAND;
        //获取手机厂商
        String pcgName = "com.android.settings";
        String clsName = "com.android.settings.Settings";
        switch (brand.toLowerCase()) {
            case "xiaomi":
                clsName = "com.android.settings.Settings$FingerprintEnrollSuggestionActivity";
                break;
            case "sony":
                clsName = "com.android.settings.Settings$FingerprintEnrollSuggestionActivity";
                break;
            case "oppo":
                clsName = "com.coloros.fingerprint.FingerLockActivity";
                break;
            case "huawei":
                clsName = "com.android.settings.fingerprint.FingerprintSettingsActivity";
                break;
        }

        Intent intent = new Intent();
        ComponentName componentName = new ComponentName(pcgName, clsName);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setComponent(componentName);
        context.startActivity(intent);
        ToastUtils.show(Utils.getResourceString(R.string.qing_she_zhi_zhi_wen));
    }


    public static void clear() {
        securityIdentificationListener = null;
        if (cannel != null) {
            cannel.cancel();
            cannel = null;
        }
        popupWindow = null;
    }

    static class MyCallBack extends FingerprintManagerCompat.AuthenticationCallback {
        private static final String TAG = "MyCallBack";

        // 当出现错误的时候回调此函数，比如多次尝试都失败了的时候，errString是错误信息
        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            Log.d(TAG, "onAuthenticationError: " + errString);
            if (securityIdentificationListener != null) {
                securityIdentificationListener.fail();
            }
        }

        // 当指纹验证失败的时候会回调此函数，失败之后允许多次尝试，失败次数过多会停止响应一段时间然后再停止sensor的工作
        @Override
        public void onAuthenticationFailed() {
            Log.d(TAG, "onAuthenticationFailed: " + "验证失败");
//            Toast.makeText(getApplicationContext(), "指纹错误", Toast.LENGTH_LONG).show();
            if (securityIdentificationListener != null) {
                securityIdentificationListener.fail();
            }
        }

        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            Log.d(TAG, "onAuthenticationHelp: " + helpString);
        }

        // 当验证的指纹成功时会回调此函数，然后不再监听指纹sensor
        @Override
        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
            if (popupWindow != null) {
                popupWindow.dimss();
            }

            if (securityIdentificationListener != null) {
                securityIdentificationListener.success();
            }
//            Toast.makeText(getApplicationContext(), "识别成功", Toast.LENGTH_LONG).show();
            Log.d(TAG, "onAuthenticationSucceeded: " + "验证成功");
        }
    }

    private static SecurityIdentificationListener securityIdentificationListener;

    public static void setSecurityIdentificationListener(SecurityIdentificationListener securityIdentificationListener) {
        SecurityIdentificationUtils.securityIdentificationListener = securityIdentificationListener;
    }

    public interface SecurityIdentificationListener {
        void success();

        void fail();
    }

}
