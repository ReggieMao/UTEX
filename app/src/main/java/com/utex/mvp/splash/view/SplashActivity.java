package com.utex.mvp.splash.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.utex.R;
import com.utex.base.BaseActivity;
import com.utex.common.FiledConstants;
import com.utex.core.AppComponent;
import com.utex.mvp.hometab.view.HomeTabActivity;
import com.utex.mvp.splash.dagger2.DaggerSplashComponent;
import com.utex.mvp.splash.dagger2.SplashModule;
import com.utex.mvp.splash.presenter.ISplashPresenter;
import com.utex.utils.SharedPreferencesUtil;
import com.utex.utils.Utils;
import com.yanzhenjie.permission.AndPermission;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends BaseActivity implements ImageLoaderInterface<ImageView>, ISplashView {

    @Inject
    ISplashPresenter iSplashPresenter;

    @BindView(R.id.rl_splash_parent_bg)
    RelativeLayout rlSplashParentBg;

    @BindView(R.id.b_splash_banner)
    Banner bSplashBanner;

    @BindView(R.id.tv_text_1)
    TextView tvText1;

    @BindView(R.id.tv_text_2)
    TextView tvText2;

    @BindView(R.id.tv_enter)
    TextView tvEnter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(SplashActivity.this, HomeTabActivity.class);
            startActivity(intent);
            finish();
        }
    };
    private int integer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        int screenHeight = getScreenHeight(this);
        int screenWidth = getScreenWidth(this);

        Log.e("TAG", "height:" + screenHeight);
        Log.e("TAG", "width:" + screenWidth);

        iSplashPresenter.getCoinList();
        integer = SharedPreferencesUtil.getInteger(FiledConstants.IS_FIRST_START_UP, 1);

        switch (integer) {
            case 0:
                //不是首次
                rlSplashParentBg.setBackgroundResource(R.drawable.splash);
                tvText1.setVisibility(View.GONE);
                tvText2.setVisibility(View.GONE);
                break;
            case 1:
                //是首次，出现引导图
                rlSplashParentBg.setBackgroundResource(R.drawable.splash);
                tvText1.setVisibility(View.GONE);
                tvText2.setVisibility(View.GONE);
                tvEnter.setVisibility(View.VISIBLE);

//                List<Integer> data = new ArrayList<>();
//                data.add(R.drawable.guide_one);
//                data.add(R.drawable.guide_two);
//                data.add(R.drawable.guide_three);
//                //设置banner样式
//                bSplashBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
//                //设置图片加载器
//                bSplashBanner.setImageLoader(this);
//                //设置图片集合
//                bSplashBanner.setImages(data);
//                //设置banner动画效果
//                bSplashBanner.setBannerAnimation(Transformer.Accordion);
//                //设置标题集合（当banner样式有显示title时）
////        bHomeBanner.setBannerTitles(titles);
//                //设置自动轮播，默认为true
//                bSplashBanner.isAutoPlay(false);
////        bHomeBanner.setBannerTitles(titles);
//                //设置轮播时间
////                bSplashBanner.setDelayTime(3000);
//                //设置指示器位置（当banner模式中有指示器时）
//                bSplashBanner.setIndicatorGravity(BannerConfig.CENTER);
//                //banner设置方法全部调用完毕时最后调用
//                bSplashBanner.start();
//
//
//                bSplashBanner.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//                    @Override
//                    public void onPageSelected(int position) {
//                        super.onPageSelected(position);
//                        switch (position) {
//                            case 0:
//                                tvText1.setText(Utils.getResourceString(R.string.quan_qiu_fu_wu));
//                                tvText2.setText(Utils.getResourceString(R.string.zhi_chi_duo_zhong_yu_yan_xiang_mu_bian_bu_quan_qiu));
//                                tvEnter.setVisibility(View.GONE);
//                                break;
//                            case 1:
//                                tvText1.setText(Utils.getResourceString(R.string.zi_chan_an_quan));
//                                tvText2.setText(Utils.getResourceString(R.string.leng_re_qian_bao_fen_li_zhang_hu_an_quan_bian_jie));
//                                tvEnter.setVisibility(View.GONE);
//                                break;
//                            case 2:
//                                tvText1.setText(Utils.getResourceString(R.string.gao_xiao_jiao_yi));
//                                tvText2.setText(Utils.getResourceString(R.string.leng_re_qian_bao_fen_li_zhang_hu_an_quan_bian_jie));
//                                tvEnter.setVisibility(View.VISIBLE);
//                                break;
//                        }
//                    }
//                });
                break;


        }

        //权限管理
        AndPermission.with(this)
                .runtime()
                .permission(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .start();
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSplashComponent
                .builder()
                .appComponent(appComponent)
                .splashModule(new SplashModule(this))
                .build()
                .inject(this);
    }

    @OnClick(R.id.tv_enter)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_enter:
                handler.sendEmptyMessage(0);
                SharedPreferencesUtil.putInteger(FiledConstants.IS_FIRST_START_UP, 0);
                break;
        }
    }

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Integer res = (Integer) path;
        Glide.with(context).load(res).into(imageView);
    }

    @Override
    public ImageView createImageView(Context context) {
        ImageView imageView = new ImageView(context);
        return imageView;
    }

    @Override
    public void goHome() {
        if (integer != 1) {
            handler.sendEmptyMessageDelayed(0, 3000);
        }
    }

    /**
     * 获取屏幕的宽
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕的高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }
}
