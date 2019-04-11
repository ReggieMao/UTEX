package com.utex.core;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatDelegate;
import android.util.DisplayMetrics;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.bugtags.library.Bugtags;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.umeng.socialize.PlatformConfig;
import com.utex.bean.UserDO;
import com.utex.common.Constants;
import com.utex.common.FiledConstants;
import com.utex.common.SDUrl;
import com.utex.db.DaoMaster;
import com.utex.db.DaoSession;
import com.utex.mvp.hometab.bean.CheckAppVersionVO;
import com.utex.service.ScreenService;
import com.utex.service.TickerSocketService;
import com.utex.utils.SharedPreferencesUtil;
import com.utex.utils.ToastUtils;
import com.utex.widget.smartrefresh.layout.SmartRefreshLayout;
import com.utex.widget.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.utex.widget.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.utex.widget.smartrefresh.layout.api.RefreshFooter;
import com.utex.widget.smartrefresh.layout.api.RefreshHeader;
import com.utex.widget.smartrefresh.layout.api.RefreshLayout;
import com.utex.widget.smartrefresh.layout.footer.ClassicsFooter;
import com.utex.widget.smartrefresh.layout.header.ClassicsHeader;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import org.android.agoo.huawei.HuaWeiRegister;
import org.android.agoo.mezu.MeizuRegister;
import org.android.agoo.xiaomi.MiPushRegistar;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by admin on 2017/4/7.
 * <p>
 * 自定义Application 用于初始化各种服务以及数据
 */
public class UTEXApplication extends Application {

    private static UTEXApplication instance;

    private static UserDO user;

    private static DaoSession mDaoSession;
    private static CheckAppVersionVO.DataBean newVersionData;

    private static JSONObject rate;
    private Intent tickerServie;

    private static Activity HomeActivity;

    public static UTEXApplication getInstance() {
        return instance;
    }

    private AppComponent appComponent;

    private static String username;

    private static String uid;

    private static String token;

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
//                    layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        initAppComponent();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }
//        JPushInterface.init(this);
        initUM();
        initLanguage();
        initLightDark();
        initDb();
        initService();
        generateFolder();
        ZXingLibrary.initDisplayOpinion(this);
        ToastUtils.initToast(this);
        initBugTags();
    }

    UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
        @Override
        public void dealWithCustomAction(Context context, UMessage msg) {
            Log.e("Hello123456", "56789");
        }

        @Override
        public void launchApp(Context context, UMessage msg) {
            Log.e("Hello123456", "launchApp msg=" + msg.getRaw() + ", msg.custom=" + msg.custom);

            super.launchApp(context, msg);
        }

        @Override
        public void openUrl(Context context, UMessage msg) {
            Log.e("Hello123456", "openUrl msg=" + msg.getRaw() + ", msg.custom=" + msg.custom);
            super.openUrl(context, msg);
        }

        @Override
        public void openActivity(Context context, UMessage msg) {
            Log.e("Hello123456", "openActivity msg=" + msg.getRaw() + ", msg.custom=" + msg.custom);
            super.openActivity(context, msg);
        }
    };

    UmengMessageHandler messageHandler = new UmengMessageHandler() {
        /**
         * 通知的回调方法
         * @param context
         * @param msg
         */
        @Override
        public void dealWithNotificationMessage(Context context, UMessage msg) {
            //调用super则会走通知展示流程，不调用super则不展示通知
            super.dealWithNotificationMessage(context, msg);
            Log.e("Hello123456", "123456");
        }
    };

    /**
     * 初始化友盟
     */
    private void initUM() {
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "4b54a7352db92353f8c4e145212fa41d"); // 友盟相关值 已替换
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        //错误统计，上线打开，
        MobclickAgent.setCatchUncaughtExceptions(true);
        PlatformConfig.setSinaWeibo("3485606309", "8d78fc5093d984cff2eb7582014fd49d", "http://sns.whalecloud.com"); // 友盟相关值 已替换
        PlatformConfig.setWeixin("wxcdd8e7198cfd702a", "db7a4fc830cefb640b5b0f4667b47baa"); // 友盟相关值 已替换
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba"); // 友盟相关值 未替换

        MeizuRegister.register(this, "116268", "7d6a82a3ca6a4e7888fa6cba23a4c34a"); // 友盟相关值 未替换
        HuaWeiRegister.register(this);
        MiPushRegistar.register(this, "2882303761517880184", "5881788036184"); // 友盟相关值 未替换

        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.e("Hello123456", deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.e("Hello123456", s);

            }
        });


        mPushAgent.setMessageHandler(messageHandler);


        mPushAgent.setNotificationClickHandler(notificationClickHandler);

//        mPushAgent.setPushIntentServiceClass(PushNotivicationService.class);
//        setNotificationClickHandler();
    }

    /**
     * 自定义行为的回调处理，参考文档：高级功能-通知的展示及提醒-自定义通知打开动作
     * UmengNotificationClickHandler是在BroadcastReceiver中被调用，故
     * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
     */
    public void setNotificationClickHandler() {
        /**
         * 该Handler是在BroadcastReceiver中被调用，故
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         * */
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                Map<String, String> params = new HashMap<>();
                params.put("msg_id", msg.msg_id);
                Log.e("Hello123456", "dealWithCustomAction msg=" + msg.toString() + ", msg.custom=" + msg.custom);

            }

//            @Override
//            public void launchApp(Context context, UMessage msg) {
//                Log.e("Hello123456", "launchApp msg=" + msg.toString() + ", msg.custom=" + msg.custom);
//
//                super.launchApp(context, msg);
//            }
//
//            @Override
//            public void openUrl(Context context, UMessage msg) {
//                super.openUrl(context, msg);
//                Log.e("Hello123456", "openUrl msg=" + msg.toString() + ", msg.custom=" + msg.custom);
//            }
//
//            @Override
//            public void openActivity(Context context, UMessage msg) {
//                super.openActivity(context, msg);
//                Log.e("Hello123456", "openActivity msg=" + msg.toString() + ", msg.custom=" + msg.custom);
//            }
        };
        PushAgent.getInstance(this).setNotificationClickHandler(notificationClickHandler);
    }

    private void initBugTags() {
        //在这里初始化
        Bugtags.start("15663a6b66d4eacd7b69efd2897c4915", this, Bugtags.BTGInvocationEventBubble);
    }

    private void initLightDark() {
        int isSWitchFrist = SharedPreferencesUtil.getInteger(FiledConstants.LIGHT_DARK, 1);
        switch (isSWitchFrist) {
            case 1:
                //亮色
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case 2:
                //暗版
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
        }
    }

    /**
     * 生成目录
     */
    private void generateFolder() {
        File file = new File(SDUrl.picPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        file = new File(SDUrl.apkPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 启动service
     */
    private void initService() {
        tickerServie = new Intent(this, TickerSocketService.class);
        startService(tickerServie);

//        servie = new Intent(this, OrderSocketService.class);
//        startService(servie);

        Intent screenService = new Intent(this, ScreenService.class);
        startService(screenService);
    }

    /**
     * 初始化 db
     */
    private void initDb() {
        //创建一个数据库，名字为"wesker"
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "utex", null);
        //一个DaoMaster就代表着一个数据库的连接；
        DaoMaster mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        // DaoSession可以让我们使用一些Entity的基本操作和获取Dao操作类，
        // DaoSession可以创建多个，每一个都是属于同一个数据库连接的
        mDaoSession = mDaoMaster.newSession();
        //开始的时候不设置，只有去登录页面才进行设置
//        user = ExUserDao.query();
//        setLoginUser(user);
    }

    public static DaoSession getmDaoSession() {
        return mDaoSession;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    /**
     * 初始化 dagger
     */
    private void initAppComponent() {
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    /**
     * 设置语言
     */
    private void initLanguage() {
        int position = SharedPreferencesUtil.getInteger(Constants.SP_LANGUGE_POSITION, 2);
        Resources resources = getResources();//获得res资源对象
        Configuration config = resources.getConfiguration();//获得设置对象
        DisplayMetrics dm = resources.getDisplayMetrics();//获得屏幕参数：主要是分辨率，像素等。

        switch (position) {
            case 0:
                //简体中文
                config.locale = Locale.CHINA;
                break;
            case 1:
                //切换成英语
                config.locale = Locale.ENGLISH;
                break;
            case 2:
                //切换成繁体中文
                config.locale = Locale.TAIWAN;
                break;
            case 3:
                //日语
                config.locale = Locale.JAPAN;
                break;
        }

        resources.updateConfiguration(config, dm);

    }

    /**
     * 设置登录用户
     */
    public static void setLoginUser(UserDO data) {
        user = data;
        if (user != null) {
            username = user.getUsername();
            uid = String.valueOf(user.getUid());
            token = user.getToken();
        }
    }

    public static UserDO getLoginUser() {
        return user;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UTEXApplication.username = username;
    }

    public static String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        UTEXApplication.token = token;
    }

    public static void clearUser() {
        user = null;
        username = null;
        uid = null;
        token = null;
    }

    public static void setNewData(CheckAppVersionVO.DataBean data) {
        newVersionData = data;
    }

    public static CheckAppVersionVO.DataBean getNewVersionData() {
        return newVersionData;
    }

    public static void setRate(JSONObject data) {
        rate = data;
    }

    /**
     * 根据币种 code 获取汇率 只有交易区的才有
     */
    public static double getRate(String code) {
        String country = SharedPreferencesUtil.getString(FiledConstants.PALTFORM_VALUAT, "USD");
        try {
            if (rate != null) {
                return Double.parseDouble(rate.getString(code.toUpperCase() + "_" + country.toUpperCase()));
            }
        } catch (Exception e) {

        }
        return 0;
    }

    public static Activity getHomeActivity() {
        return HomeActivity;
    }

    public static void setHomeActivity(Activity homeActivity) {
        HomeActivity = homeActivity;
    }

    /**
     * 获取当前语言
     * 0 简体
     * 1 英文
     * 2 繁体
     * 3 日语
     */
    public static int getCurrLanguage() {
        return SharedPreferencesUtil.getInteger(Constants.SP_LANGUGE_POSITION, 2);
    }

    /**
     * 当前语言是否中文
     */
    public static boolean isZhLanguage() {
        if (getCurrLanguage() == 0 || getCurrLanguage() == 2) {
            return true;
        } else {
            return false;
        }
    }

}
