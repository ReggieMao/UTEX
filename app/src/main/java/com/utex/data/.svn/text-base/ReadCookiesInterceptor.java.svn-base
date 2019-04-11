package com.utex.data;

import com.utex.common.FiledConstants;
import com.utex.core.UTEXApplication;
import com.utex.utils.SharedPreferencesUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 读取Cookie
 */
public class ReadCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

//        final Request.Builder builder = chain.request().newBuilder();

        //获取原始的originalRequest
        Request originalRequest = chain.request();

        //获取老的url
        HttpUrl oldUrl = originalRequest.url();

        //获取originalRequest的创建者builder
        Request.Builder builder = originalRequest.newBuilder();

        builder.addHeader("authorization_username",
                UTEXApplication.getUsername() == null ? "" :
                        UTEXApplication.getUsername());

        builder.addHeader("authorization_uid",
                UTEXApplication.getUid() == null ? "" :
                        UTEXApplication.getUid());

        builder.addHeader("authorization_token",
                UTEXApplication.getToken() == null ? "" :
                        UTEXApplication.getToken());
        builder.addHeader("platform", "app");

        String country = SharedPreferencesUtil.getString(FiledConstants.SP_LANGUGE_ABBREVIATION, "tw");

        builder.addHeader("lan", country);

        //获取头信息的集合如：manage,mdffx
        List<String> urlnameList = originalRequest.headers("baseUrl");

        if (urlnameList != null && urlnameList.size() > 0) {
            //删除原有配置中的值,就是namesAndValues集合里的值
            builder.removeHeader("urlname");

            //获取头信息中配置的value,如：manage或者mdffx
            String urlname = urlnameList.get(0);
            HttpUrl baseURL = null;
            //根据头信息中配置的value,来匹配新的base_url地址
            if ("platform".equals(urlname)) {
                baseURL = HttpUrl.parse(ApiServiceModule.PLATFORM_URL);
            } else if ("user".equals(urlname)) {
                baseURL = HttpUrl.parse(ApiServiceModule.USER_URL);
            } else if ("asset".equals(urlname)) {
                baseURL = HttpUrl.parse(ApiServiceModule.ASSET_URL);
            } else if ("rate".equals(urlname)) {
                baseURL = HttpUrl.parse(ApiServiceModule.RATE_URL);
            } else {
                baseURL = HttpUrl.parse("");
            }

            //重建新的HttpUrl，需要重新设置的url部分
            HttpUrl newHttpUrl = oldUrl.newBuilder()
                    .scheme(baseURL.scheme())//http协议如：http或者https
                    .host(baseURL.host())//主机地址
                    .port(baseURL.port())//端口
                    .build();

            //获取处理后的新newRequest
            Request newRequest = builder.url(newHttpUrl).build();

            return chain.proceed(newRequest);
        } else {
            return chain.proceed(originalRequest);
        }


//        return chain.proceed(builder.build());
    }

}