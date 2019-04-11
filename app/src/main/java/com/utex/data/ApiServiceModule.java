package com.utex.data;

import android.util.Log;

import com.utex.core.UTEXApplication;
import com.utex.utils.Utils;

import javax.inject.Singleton;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by demon 2017/4/20.
 */
@Module
public class ApiServiceModule {

    /**
     * ********************************正式服务器************************************************
     * //
     */
    public static final String ASSET_URL = "http://asset.utex.sg/";
    public static final String PLATFORM_URL = "http://platform.utex.sg/";
    public static final String USER_URL = "http://user.utex.sg/";

    public static final String ORDER_WEBSOCKET = "ws://notify.utex.sg/websocket?";
    public static final String TICKER_WEBSOCKET = "ws://quote.utex.sg";
    /**
     * zendesk 登录
     */
    public static final String ZENDESK_LOGIN = "http://user.utex.sg/api/user/zendesk/";
    public static final String RATE_URL = "http://api.utex.sg/";

    /**
     * ********************************测试服务器************************************************
     */
//    public static final String ASSET_URL = "http://192.168.10.144:8088/";
//    public static final String PLATFORM_URL = "http://192.168.10.144:83/";
//    public static final String USER_URL = "http://192.168.10.144:82/";
//
//    public static final String ORDER_WEBSOCKET = "ws://192.168.10.144:8090/websocket?";
//    public static final String TICKER_WEBSOCKET = "ws://192.168.10.144:8082";
//
//    public static final String ZENDESK_LOGIN = "http://192.168.10.144:82/api/user/zendesk/";
//    public static final String RATE_URL = "http://192.168.10.134:8089/";
    /**
     * *****************************************************************************************
     */

    /**
     * ********************************测试服务器111************************************************
     //     */
//    public static final String ASSET_URL = "http://192.168.10.111:8088/";
//    public static final String PLATFORM_URL = "http://192.168.10.111:83/";
//    public static final String USER_URL = "http://192.168.10.111:82/";
//
//    public static final String ORDER_WEBSOCKET = "ws://192.168.10.111:8090/websocket?";
//    public static final String TICKER_WEBSOCKET = "ws://192.168.10.111:8082";
//
//    /**
//     * zendesk 登录
//     */
//    public static final String ZENDESK_LOGIN = "http://192.168.10.111:82/api/user/zendesk/";
//    public static final String RATE_URL = "http://192.168.10.134:8089/";
    /**
     * *****************************************************************************************
     */


    /**
     * 用户隐私调控
     */
    public static final String USER_REGISTER_ITEM = "https://www.utex.sg/privacy";

    public static final String BASE_ZENDASK = "https://support.utex.sg/hc/";

    /**
     * 帮助中心
     */
    public static final String COMMON_PROBLEM = "/categories/360000278971-%E5%B8%AE%E5%8A%A9%E4%B8%AD%E5%BF%83";

    /**
     * 提交工单
     */
    public static final String SUBMIT_WORK_ORDER = "/requests/new";


    /**
     * cnc 充值url
     */
    public static final String CNC_DEPOSIT_URL = "https://www.utex.sg/moneydealer.html";

    /**
     * 公告中心
     */
    public static final String NOTICK_CENTER = "/categories/360000278951-%E5%85%AC%E5%91%8A%E4%B8%AD%E5%BF%83";

    /**
     * 费率说明
     */
    public static final String RATE_EXPLAIN_ZH = "https://support.utex.sg/hc/zh-cn/articles/360006924212-%E6%9C%8D%E5%8A%A1%E8%B4%B9%E8%AF%B4%E6%98%8E";
    public static final String RATE_EXPLAIN_EN = "https://support.utex.sg/hc/en-us/articles/360006924212-Fee-Structure";


    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient();

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();

        builder.addInterceptor(new ReadCookiesInterceptor());

        builder.sslSocketFactory(Utils.getSSLSocketFactory(UTEXApplication.getInstance()));

        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("TAG", message);
            }
        });

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        builder.addInterceptor(logging);

        return builder.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ASSET_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }


    //忽略验证
//    X509TrustManager xtm = new X509TrustManager() {
//        @Override
//        public void checkServerTrusted(X509Certificate[] chain, String authType) {
//        }
//
//        @Override
//        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//
//        }
//
//        @Override
//        public X509Certificate[] getAcceptedIssuers() {
//            X509Certificate[] x509Certificates = new X509Certificate[0];
//            return x509Certificates;
//        }
//    };

//    SSLContext sslContext = null;
//        try {
//        sslContext = SSLContext.getInstance("SSL");
//
//        sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());
//
//    } catch (NoSuchAlgorithmException e) {
//        e.printStackTrace();
//    } catch (KeyManagementException e) {
//        e.printStackTrace();
//    }
//    HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
//        @Override
//        public boolean verify(String hostname, SSLSession session) {
//            return true;
//        }
//    };
//
//        builder.sslSocketFactory(sslContext.getSocketFactory());
//        builder.hostnameVerifier(DO_NOT_VERIFY);

}
