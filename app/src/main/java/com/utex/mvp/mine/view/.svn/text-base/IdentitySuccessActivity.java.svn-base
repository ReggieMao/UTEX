package com.utex.mvp.mine.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.utex.R;
import com.utex.base.BaseActivity;
import com.utex.common.FiledConstants;
import com.utex.core.AppComponent;
import com.utex.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IdentitySuccessActivity extends BaseActivity {

    @BindView(R.id.tv_identity_success_status)
    TextView tvIdentitySuccessStatus;

    @BindView(R.id.tv_identity_success_detail)
    TextView tvIdentitySuccessDetail;

    @BindView(R.id.tv_identity_go_home)
    TextView tvIdentityGoHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_success);
        ButterKnife.bind(this);

        int intExtra = getIntent().getIntExtra(FiledConstants.TYPE, 0);

        switch (intExtra) {
            case 1:
                tvIdentitySuccessStatus.setText(Utils.getResourceString(R.string.ren_zheng_cheng_gong));
                tvIdentitySuccessDetail.setText(Utils.getResourceString(R.string.shen_fen_ren_zheng_cheng_gong));
                tvIdentityGoHome.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @OnClick({R.id.iv_toolbar_left, R.id.tv_identity_go_home})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_left:
                finish();
                break;
            case R.id.tv_identity_go_home:
                finish();
                break;
        }
    }
}
