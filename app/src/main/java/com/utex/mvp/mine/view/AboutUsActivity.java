package com.utex.mvp.mine.view;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.utex.R;
import com.utex.base.BaseActivity;
import com.utex.common.FiledConstants;
import com.utex.core.AppComponent;
import com.utex.data.ApiServiceModule;
import com.utex.mvp.web.WebActivity;
import com.utex.utils.ToastUtils;
import com.utex.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;

    @BindView(R.id.tv_about_us_version)
    TextView tvAboutUsVersion;

    @BindView(R.id.tv_about_us_email)
    TextView tvAboutUsEmail;

    @BindView(R.id.tv_about_us_tel)
    TextView tvAboutUsTel;

    @BindView(R.id.tv_about_us_wechat)
    TextView tvAboutUsWeChat;

    @BindView(R.id.tv_about_us_telegram_china)
    TextView tvAboutUsTelegramChina;

    @BindView(R.id.tv_about_us_telegram_english)
    TextView tvAboutUsTelegramEnglish;

    @BindView(R.id.tv_about_us_twitter)
    TextView tvAboutUsTwitter;

    @BindView(R.id.tv_about_us_facebook)
    TextView tvAboutUsFacebook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);

        tvToolbarTitle.setText(Utils.getResourceString(R.string.guan_yu_wo_men));

        tvAboutUsVersion.setText("V" + Utils.getVersionCode(this));
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @OnClick({R.id.iv_toolbar_left, R.id.rl_about_us_email, R.id.rl_about_us_tel, R.id.rl_about_us_wechat,
            R.id.rl_about_us_telegram_china, R.id.rl_about_us_telegram_english, R.id.rl_about_us_twitter,
            R.id.rl_about_us_facebook, R.id.tv_about_us_privacy})
    public void onClick(View view) {
        Intent intent = new Intent(this, WebActivity.class);
        switch (view.getId()) {
            case R.id.tv_about_us_privacy:
                intent.putExtra(FiledConstants.LINK_URL, ApiServiceModule.USER_REGISTER_ITEM);
                startActivity(intent);
                break;
            case R.id.rl_about_us_email:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(tvAboutUsEmail.getText());
                ToastUtils.show(Utils.getResourceString(R.string.fu_zhi_cheng_gong));
                break;
            case R.id.rl_about_us_tel:
                Intent tel = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + tvAboutUsTel.getText());
                tel.setData(data);
                startActivity(tel);
                break;
            case R.id.rl_about_us_wechat:
                Intent weChat = new Intent(this, WeChatActivity.class);
                intent.putExtra(FiledConstants.TYPE, 0);
                startActivity(weChat);
                break;
            case R.id.rl_about_us_telegram_china:
                intent.putExtra(FiledConstants.LINK_URL, tvAboutUsTelegramChina.getText());
                startActivity(intent);
                break;
            case R.id.rl_about_us_telegram_english:
                intent.putExtra(FiledConstants.LINK_URL, tvAboutUsTelegramEnglish.getText());
                startActivity(intent);
                break;
            case R.id.rl_about_us_twitter:
                ClipboardManager cm1 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm1.setText(tvAboutUsTwitter.getText());
                ToastUtils.show(Utils.getResourceString(R.string.fu_zhi_cheng_gong));
//                intent.putExtra(FiledConstants.LINK_URL, tvAboutUsTwitter.getText());
//                startActivity(intent);
                break;
            case R.id.rl_about_us_facebook:
                ClipboardManager cm2 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm2.setText(tvAboutUsFacebook.getText());
                ToastUtils.show(Utils.getResourceString(R.string.fu_zhi_cheng_gong));
//                intent.putExtra(FiledConstants.LINK_URL, tvAboutUsFacebook.getText());
//                startActivity(intent);
                break;
            case R.id.iv_toolbar_left:
                finish();
                break;
        }
    }

}
