package com.utex.mvp.share;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.LocaleList;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.utex.R;
import com.utex.common.Constants;
import com.utex.utils.SharedPreferencesUtil;
import com.utex.utils.ToastUtils;
import com.utex.utils.Utils;
import com.utex.widget.popuwindow.LoadingPopupWindow;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExnowShareActivity extends Activity implements UMShareListener {

    @BindView(R.id.rv_share_list)
    RecyclerView rvShareList;

    @BindView(R.id.iv_share_small_icon)
    ImageView ivShareSmallIcon;

    @BindView(R.id.ll_share_small_parent)
    LinearLayout llShareSmallParent;

    @BindView(R.id.ll_share_bottom_parent)
    LinearLayout llShareBottomParent;

    @BindView(R.id.img_loading)
    ImageView mImgLoading;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            finish();
        }
    };
    private LoadingPopupWindow loadingPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exnow_share_activity);
        getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        ButterKnife.bind(this);
        String path = getIntent().getStringExtra(Constants.SHARE_PATH);
        rvShareList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        loadingPopupWindow = new LoadingPopupWindow(this, R.layout.activity_exnow_share_activity);
        ExnowShareAdapter exnowShareAdapter = new ExnowShareAdapter(this, path, loadingPopupWindow, mImgLoading);
        rvShareList.setAdapter(exnowShareAdapter);
        ivShareSmallIcon.setImageBitmap(BitmapFactory.decodeFile(path));
        handler.sendEmptyMessageDelayed(0, 4000);
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
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        configuration.setLocales(new LocaleList(locale));
        return context.createConfigurationContext(configuration);
    }

    @OnClick({R.id.ll_share_small_parent, R.id.tv_share_cannel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_share_small_parent:
                handler.removeCallbacksAndMessages(null);
                llShareSmallParent.setVisibility(View.GONE);
                llShareBottomParent.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_share_cannel:
                finish();
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {
        Log.e("UMCall", "onStart");
        loadingPopupWindow.dimss();
        mImgLoading.setVisibility(View.GONE);
//        new Handler()
//                .postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        finish();
//                    }
//                }, 700);
    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        Log.e("UMCall", "onResult");

    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        Log.e("UMCall", throwable.getMessage());
        ToastUtils.show(Utils.getResourceString(R.string.wei_an_zhuan_ying_yong));
        handler.postDelayed(() -> finish(), 300);

    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        Log.e("UMCall", "onCancel");
        finish();
    }

}
