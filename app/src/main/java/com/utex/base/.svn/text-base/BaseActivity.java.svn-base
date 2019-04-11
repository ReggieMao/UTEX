package com.utex.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.bugtags.library.Bugtags;
import com.utex.common.Constants;
import com.utex.common.FiledConstants;
import com.utex.core.AppComponent;
import com.utex.core.UTEXApplication;
import com.utex.utils.SharedPreferencesUtil;
import com.gyf.barlibrary.ImmersionBar;
import com.umeng.message.PushAgent;

import java.util.Locale;

/**
 * Created by admin on 2017/4/7.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActivityComponent(UTEXApplication.getInstance().getAppComponent());
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
        PushAgent.getInstance(this).onAppStart();


        int isSWitchFrist = SharedPreferencesUtil.getInteger(FiledConstants.LIGHT_DARK, 1);
        switch (isSWitchFrist) {
            case 1:
                //亮色
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                break;
            case 2:
                //暗版
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注：回调 1
        Log.e("Demon", "onResume");
        Bugtags.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //注：回调 2
        Log.e("Demon", "onPause");
        Bugtags.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();
        //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }

    protected abstract void setupActivityComponent(AppComponent appComponent);

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Bugtags.onDispatchTouchEvent(this, ev);
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                if (hideInputMethod(this, v)) {
                    return true; //隐藏键盘时，其他控件不响应点击事件==》注释则不拦截点击事件
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0], top = leftTop[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public Boolean hideInputMethod(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            return imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        return false;
    }

    @Override
    protected void attachBaseContext(Context base) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            super.attachBaseContext(updateResources(base));
        } else {
            super.attachBaseContext(base);
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    protected Context updateResources(Context context) {
        int position = SharedPreferencesUtil.getInteger(Constants.SP_LANGUGE_POSITION, 2);
        Resources resources = context.getResources();
        Locale locale = Locale.TAIWAN;
        switch (position) {
            case 0:
                //简体中文
                locale = Locale.CHINA;
                break;
            case 1:
                //切换成英语
                locale = Locale.ENGLISH;
                break;
            case 2:
                //切换成繁体中文
                locale = Locale.TAIWAN;
                break;
            case 3:
                //日语
                locale = Locale.JAPAN;
                break;
        }
        Log.e("liuhao", "hello:" + position);
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        configuration.setLocales(new LocaleList(locale));
        return context.createConfigurationContext(configuration);
    }


}
