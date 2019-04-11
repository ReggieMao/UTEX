package com.utex.mvp.hometab.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bugtags.library.Bugtags;
import com.utex.R;
import com.utex.base.BaseActivity;
import com.utex.base.BaseFragment;
import com.utex.bean.TickerDo;
import com.utex.common.Constants;
import com.utex.common.FiledConstants;
import com.utex.common.SDUrl;
import com.utex.core.AppComponent;
import com.utex.core.UTEXApplication;
import com.utex.db.ExTickerDao;
import com.utex.mvp.home.view.MarketPageAdapter;
import com.utex.mvp.hometab.bean.JumpEvent;
import com.utex.mvp.hometab.dagger2.DaggerHomeTabComponent;
import com.utex.mvp.hometab.dagger2.HomeTabModule;
import com.utex.mvp.hometab.presenter.IHomeTabPresenter;
import com.utex.mvp.trad.view.TradFragment;
import com.utex.recevier.DownloadAppReceiver;
import com.utex.recevier.TickerReceiver;
import com.utex.service.DownLoadService;
import com.utex.service.TickerSocketService;
import com.utex.utils.MarketSocketPackUtils;
import com.utex.utils.SharedPreferencesUtil;
import com.utex.utils.Utils;
import com.utex.widget.viewpager.NoScrollViewPager;
import com.quintar.IMarketAidlInterface;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeTabActivity extends BaseActivity implements IHomeTabView, BottomNavigationBar.OnTabSelectedListener, DownloadAppReceiver.TickerListener {

    @Inject
    IHomeTabPresenter iHomeTabPresenter;

    @BindView(R.id.bnb_home_bottom_bar)
    BottomNavigationBar bnbHomeBottomBar;

    @BindView(R.id.nsvp_home_page)
    NoScrollViewPager nsvpHomePage;

    private List<BaseFragment> fragments;
    private DownloadAppReceiver downLoadService;

    IMarketAidlInterface mMarketAidl;

    /**
     * 服务绑定
     */
    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMarketAidl = IMarketAidlInterface.Stub.asInterface(service);
            MarketSocketPackUtils.init(mMarketAidl, tickerReceiver);
        }
    };
    private TickerReceiver tickerReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        UTEXApplication.setHomeActivity(this);

        Intent intent = new Intent(this, TickerSocketService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);

        tickerReceiver = new TickerReceiver();
        IntentFilter tickerIntentFilter = new IntentFilter();
        tickerIntentFilter.addAction("com.ticker");
        registerReceiver(tickerReceiver, tickerIntentFilter);


        downLoadService = new DownloadAppReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.download.app1");
        registerReceiver(downLoadService, intentFilter);

        downLoadService.setTickerListener(this);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        bnbHomeBottomBar.setMode(BottomNavigationBar.MODE_FIXED);
        bnbHomeBottomBar.setActiveColor(R.color.f398155)
                .setInActiveColor(R.color.bafb3b0_7fffffff)
                .setBarBackgroundColor(R.color.bffffff_1e2129);

        bnbHomeBottomBar.addItem(new BottomNavigationItem(R.drawable.tab_icon_home_sel,
                Utils.getResourceString(R.string.shou_ye))
                .setInactiveIconResource(R.drawable.tab_icon_home_n))
                .addItem(new BottomNavigationItem(R.drawable.tab_icon_jy_sel,
                        Utils.getResourceString(R.string.jiao_yi))
                        .setInactiveIconResource(R.drawable.tab_icon_jy_n))
                .addItem(new BottomNavigationItem(R.drawable.tab_icon_zc_sel,
                        Utils.getResourceString(R.string.zi_chan))
                        .setInactiveIconResource(R.drawable.tab_icon_zc_n))
                .addItem(new BottomNavigationItem(R.drawable.tab_icon_my_sel,
                        Utils.getResourceString(R.string.wo))
                        .setInactiveIconResource(R.drawable.tab_icon_my_n))
                .initialise();

        setBottomNavigationItem(bnbHomeBottomBar, 6, 20, 10);

        bnbHomeBottomBar.setTabSelectedListener(this);

        iHomeTabPresenter.initFragment();

        int intExtra = getIntent().getIntExtra(Constants.DARK_LIGHT_SWITCH, 0);
        if (intExtra != 1) {
            iHomeTabPresenter.checkAppVersion();
        }

        EventBus.getDefault().register(this);

        iHomeTabPresenter.getExchangeRateList();

    }


    private void setBottomNavigationItem(BottomNavigationBar bottomNavigationBar, int space, int imgLen, int textSize) {
        Class barClass = bottomNavigationBar.getClass();
        Field[] fields = barClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            if (field.getName().equals("mTabContainer")) {
                try {
                    //反射得到 mTabContainer
                    LinearLayout mTabContainer = (LinearLayout) field.get(bottomNavigationBar);
                    for (int j = 0; j < mTabContainer.getChildCount(); j++) {
                        //获取到容器内的各个Tab
                        View view = mTabContainer.getChildAt(j);
                        //获取到Tab内的各个显示控件
                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(56));
                        FrameLayout container = (FrameLayout) view.findViewById(R.id.fixed_bottom_navigation_container);
                        container.setLayoutParams(params);
                        container.setPadding(dip2px(12), dip2px(0), dip2px(12), dip2px(0));

                        //获取到Tab内的文字控件
                        TextView labelView = (TextView) view.findViewById(com.ashokvarma.bottomnavigation.R.id.fixed_bottom_navigation_title);
                        //计算文字的高度DP值并设置，setTextSize为设置文字正方形的对角线长度，所以：文字高度（总内容高度减去间距和图片高度）*根号2即为对角线长度，此处用DP值，设置该值即可。
                        labelView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
                        labelView.setIncludeFontPadding(false);
                        labelView.setPadding(0, 0, 0, dip2px(20 - textSize - space / 2));

                        //获取到Tab内的图像控件
                        ImageView iconView = (ImageView) view.findViewById(com.ashokvarma.bottomnavigation.R.id.fixed_bottom_navigation_icon);
                        //设置图片参数，其中，MethodUtils.dip2px()：换算dp值
                        params = new FrameLayout.LayoutParams(dip2px(imgLen), dip2px(imgLen));
                        params.setMargins(0, 0, 0, space / 2);
                        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
                        iconView.setLayoutParams(params);
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int dip2px(float dpValue) {
        final float scale = getApplication().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerHomeTabComponent
                .builder()
                .appComponent(appComponent)
                .homeTabModule(new HomeTabModule(this))
                .build()
                .inject(this);
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
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Bugtags.onDispatchTouchEvent(this, ev);
        return super.dispatchTouchEvent(ev);
    }


    @Override
    protected void onDestroy() {
        //保存自选
        Log.e("TAG", "旧的消亡");
        List<TickerDo> tickerByMarket = ExTickerDao.getTickerByMarket(Utils.getResourceString(R.string.zi_xuan));
        String str = "";


        for (TickerDo tickerDo : tickerByMarket) {
            str += tickerDo.getCoin_market_code() + ",";

        }


        SharedPreferencesUtil.putString(FiledConstants.OPTION_COIN, str);

        EventBus.getDefault().unregister(this);

        int intExtra = getIntent().getIntExtra(Constants.DARK_LIGHT_SWITCH, 0);
        if (intExtra != 1) {
            UTEXApplication.clearUser();
        }
        Log.e("Demon", "HomeTab destroy");

        if (downLoadService != null) {
            unregisterReceiver(downLoadService);
        }

        super.onDestroy();
    }

    @Override
    public void onTabSelected(int position) {
        //未选中->选中
        nsvpHomePage.setCurrentItem(position);
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
    public void onTabUnselected(int position) {
        //选中->未选中
    }

    @Override
    public void onTabReselected(int position) {
        //选中->选中

    }

    @Override
    public void setPage(List<BaseFragment> fragments) {
        this.fragments = fragments;
        nsvpHomePage.setOffscreenPageLimit(4);
        nsvpHomePage.setAdapter(new MarketPageAdapter(getSupportFragmentManager(), fragments));
        nsvpHomePage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        int intExtra = getIntent().getIntExtra(Constants.DARK_LIGHT_SWITCH, 0);
        if (intExtra == 1) {
            bnbHomeBottomBar.selectTab(3);
        }
    }

    @Override
    public void updateApp(String download) {
        Intent intent = new Intent(this, DownLoadService.class);
        intent.putExtra(FiledConstants.DOWN_ADDRESS, download);
        intent.putExtra(FiledConstants.TYPE, 1);
        startService(intent);
    }


    boolean isRefreshTrad;

    @Override
    protected void onRestart() {
        super.onRestart();
        new Handler()
                .postDelayed(() -> {
                    if (isRefreshTrad) {
                        isRefreshTrad = false;
                        TradFragment tradFragment = (TradFragment) fragments.get(1);
                        tradFragment.resetPage();
                    }
                }, 666);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(final JumpEvent jumpEvent) {
        isRefreshTrad = true;

        /* Do something */
        new Handler()
                .postDelayed(() -> {
                    TradFragment tradFragment = (TradFragment) fragments.get(1);
                    tradFragment.setDirection(jumpEvent.getStatus());
                    bnbHomeBottomBar.selectTab(1);
                }, 100);


    }

    @Override
    public void onReceive() {
        startInstallApp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 199) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                boolean hasInstallPermission = getPackageManager().canRequestPackageInstalls();
                if (hasInstallPermission) {
                    startInstallApp();
                }
            }
        } else if (requestCode == 902) {
            Log.e("TAG", "由安装返回");
        }
    }

    private void startInstallApp() {
        Uri contentUri = Uri.fromFile(new File(SDUrl.apkPath + "utex.apk"));
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //兼容7.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");

                //兼容8.0
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    boolean hasInstallPermission = getPackageManager().canRequestPackageInstalls();
                    if (!hasInstallPermission) {
                        setAllowedInstallationApp();
                        return;
                    }
                }
            } else {
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            if (getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
                startActivityForResult(intent, 902);
//                finish();
            }
        } catch (Throwable e) {
            e.printStackTrace();
//                DataEmbeddingUtil.dataEmbeddingAPPUpdate(e.toString());
//                CommonUtils.makeEventToast(MyApplication.getContext(), MyApplication.getContext().getString(R.string.download_hint), false);
        }
    }

    private void setAllowedInstallationApp() {
        //注意这个是8.0新API
        Uri packageURI = Uri.parse("package:" + getPackageName());
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        startActivityForResult(intent, 199);
    }

}
