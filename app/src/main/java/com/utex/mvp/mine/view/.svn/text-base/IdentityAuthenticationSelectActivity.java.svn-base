package com.utex.mvp.mine.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.utex.R;
import com.utex.base.BaseActivity;
import com.utex.common.FiledConstants;
import com.utex.common.UMConstants;
import com.utex.core.AppComponent;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IdentityAuthenticationSelectActivity extends BaseActivity {

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_authentication_select);
        ButterKnife.bind(this);
        tvToolbarTitle.setVisibility(View.GONE);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @OnClick({R.id.iv_toolbar_left, R.id.iv_identity_other, R.id.tv_identity_other,
            R.id.iv_identity_zh, R.id.tv_identity_zh})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_left:
                finish();
                break;
            case R.id.iv_identity_other:
            case R.id.tv_identity_other:
                //跳入其他
                MobclickAgent.onEvent(this, UMConstants.MINE_ACCOUNT_CERTIFICATION_SELECT_OTHER_CITY_BTN);
                Intent other = new Intent(this, IdentityInputActivity.class);
                other.putExtra(FiledConstants.TYPE, 0);
                startActivity(other);
                finish();
                break;
            case R.id.tv_identity_zh:
            case R.id.iv_identity_zh:
                //跳入中国
                MobclickAgent.onEvent(this, UMConstants.MINE_ACCOUNT_CERTIFICATION_SELECT_CHINA_BTN);
                Intent zh = new Intent(this, IdentityInputActivity.class);
                zh.putExtra(FiledConstants.TYPE, 1);
                startActivity(zh);
                finish();
                break;
        }
    }

}
