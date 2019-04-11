package com.utex.mvp.web;

import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.utex.R;
import com.utex.base.BaseActivity;
import com.utex.common.FiledConstants;
import com.utex.core.AppComponent;
import com.utex.core.UTEXApplication;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebActivity extends BaseActivity {
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.iv_toolbar_left)
    ImageView ivBack;
    @BindView(R.id.tv_toolbar_title)
    TextView toolbarTitle;
    private String errorHtml = "";
    static final String mimeType = "text/html";
    static final String encoding = "utf-8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        errorHtml = "<HTML><div class=\"text\" style=\" text-align:center;line-height: 1000px;font-size:50px;\">请检查网络！</div>";
//        ivBack.setImageDrawable(getResources().getDrawable(R.drawable.market_detail_time_sort_back));

        initWeb();
    }

    private void initWeb() {
//        showProgress();
        final String url = getIntent().getExtras().getString(FiledConstants.LINK_URL);
        WebSettings webSettings = webView.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setUserAgentString("exnow_android");
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setDisplayZoomControls(false);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);

        //加载需要显示的网页
        webView.loadUrl(url);

        //设置Web视图
        WebChromeClient wvcc = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                toolbarTitle.setText(title);
            }

        };

        // 设置setWebChromeClient对象
        webView.setWebChromeClient(wvcc);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                hideProgress();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                webView.loadDataWithBaseURL(null, errorHtml, mimeType, encoding, null);

            }
        });

        //监听
//        webView.addJavascriptInterface(new JsUse(), "client");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清空所有Cookie
        CookieSyncManager.createInstance(UTEXApplication.getInstance());  //Create a singleton CookieSyncManager within a context
        CookieManager cookieManager = CookieManager.getInstance(); // the singleton CookieManager instance
        cookieManager.removeAllCookie();// Removes all cookies.
        CookieSyncManager.getInstance().sync(); // forces sync manager to sync now

        webView.setWebChromeClient(null);
        webView.setWebViewClient(null);
        webView.getSettings().setJavaScriptEnabled(false);
        webView.clearCache(true);
    }

    @OnClick({R.id.iv_toolbar_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_left:
                finish();
                break;
        }
    }

//    @Override
//    protected Context getActivityContext() {
//        return this;
//    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

//    /**
//     * js调用原生
//     */
//    private class JsUse {
//
//        public JsUse() {
//
//        }
//
//        @JavascriptInterface
//        public String getToken() {
//            User user = UserModelDao.queryUser();
//            if (user == null) {
//                return "";
//            }
//            return user.getAccess_token();
//        }
//
//        @JavascriptInterface
//        public void share(String title, String desc, String imgUrl, String link) {
//            //分享
//            OnekeyShare oks = new OnekeyShare();
//            //关闭sso授权
//            oks.disableSSOWhenAuthorize();
//            // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
//            oks.setTitle(title);
//            // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
//            oks.setTitleUrl(link);
//            oks.setUrl(link);
//            // text是分享文本，所有平台都需要这个字段
//            oks.setText(desc);
//            //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
//            oks.setImageUrl(imgUrl);
//            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
////        oks.setImagePath();//确保SDcard下面存在此张图片
//            // 启动分享GUI
//            oks.show(WebActivity.this);
//        }
//    }
}
