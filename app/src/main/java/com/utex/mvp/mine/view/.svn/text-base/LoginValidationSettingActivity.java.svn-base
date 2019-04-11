package com.utex.mvp.mine.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.utex.R;
import com.utex.base.BaseActivity;
import com.utex.bean.UserDO;
import com.utex.common.FiledConstants;
import com.utex.core.AppComponent;
import com.utex.core.UTEXApplication;
import com.utex.db.ExUserDao;
import com.utex.mvp.mine.dagger2.DaggerLoginValidationComponent;
import com.utex.mvp.mine.dagger2.LoginValidationModule;
import com.utex.mvp.mine.presenter.ILoginValidationPresenter;
import com.utex.utils.Utils;
import com.utex.widget.button.switchbutton.FSwitchButton;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginValidationSettingActivity extends BaseActivity implements ILoginValidationView {

    @Inject
    ILoginValidationPresenter iLoginValidationPresenter;

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;

    @BindView(R.id.tv_login_validation_1_name)
    TextView tvLoginValidation1Name;

    @BindView(R.id.tv_login_validation_2_name)
    TextView tvLoginValidation2Name;

    @BindView(R.id.tv_login_validation_1_detail)
    TextView tvLoginValidation1Detail;

    @BindView(R.id.fb_login_validation_fingerprint)
    FSwitchButton fbLoginValidationFingerprint;

    private int type;

    private UserDO loginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_validation_setting);
        ButterKnife.bind(this);

        type = getIntent().getIntExtra(FiledConstants.TYPE, 1);

        loginUser = UTEXApplication.getLoginUser();


        setPageData();

        fbLoginValidationFingerprint.setOnCheckedChangeCallback((checked, view) -> iLoginValidationPresenter.switchVerification(type, checked));
    }

    private void setPageData() {
        switch (type) {
            case 1:
                //手机
                tvToolbarTitle.setText(Utils.getResourceString(R.string.shou_ji_yan_zheng));
                tvLoginValidation1Name.setText(Utils.getResourceString(R.string.shou_ji_hao));
                tvLoginValidation2Name.setText(Utils.getResourceString(R.string.duan_xin_yan_zheng));
                if (!TextUtils.isEmpty(loginUser.getTel())) {
                    tvLoginValidation1Detail.setText(loginUser.getTel());
                    tvLoginValidation1Name.setCompoundDrawables(null, null, null, null);
                } else {
                    tvLoginValidation1Detail.setText(Utils.getResourceString(R.string.wei_bang_ding));
                }
                if (loginUser.isTel_status()) {
                    fbLoginValidationFingerprint.setChecked(true, false, false);
                }
                break;
            case 2:
                //谷歌
                tvToolbarTitle.setText(Utils.getResourceString(R.string.gu_ge_shen_fen_yan_zheng_qi));
                tvLoginValidation1Name.setText(Utils.getResourceString(R.string.gu_ge_shen_fen_yan_zheng_qi));
                tvLoginValidation2Name.setText(Utils.getResourceString(R.string.gu_ge_shen_fen_yan_zheng));
                if (loginUser.getIs_google_authed() == 1) {
                    tvLoginValidation1Detail.setText(Utils.getResourceString(R.string.yi_bang_ding));
                } else {
                    tvLoginValidation1Detail.setText(Utils.getResourceString(R.string.wei_bang_ding));
                }

                if (loginUser.isGoogle_status()) {
                    fbLoginValidationFingerprint.setChecked(true, false, false);
                }
                break;
            case 3:
                tvToolbarTitle.setText(Utils.getResourceString(R.string.you_xiang_yan_zheng));
                tvLoginValidation1Name.setText(Utils.getResourceString(R.string.youxiang_di_zhi));
                tvLoginValidation2Name.setText(Utils.getResourceString(R.string.you_xiang_yan_zheng));
                if (!TextUtils.isEmpty(loginUser.getEmail())) {
                    tvLoginValidation1Detail.setText(loginUser.getEmail());
                    tvLoginValidation1Name.setCompoundDrawables(null, null, null, null);
                } else {
                    tvLoginValidation1Detail.setText(Utils.getResourceString(R.string.wei_bang_ding));
                }

                if (loginUser.isEmail_status()) {
                    fbLoginValidationFingerprint.setChecked(true, false, false);
                }
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setPageData();
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerLoginValidationComponent
                .builder()
                .appComponent(appComponent)
                .loginValidationModule(new LoginValidationModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.rl_login_validation_1_parent, R.id.iv_toolbar_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_left:
                finish();
                break;
            case R.id.rl_login_validation_1_parent:
                switch (type) {
                    case 1:
                        //手机
                        if (!TextUtils.isEmpty(loginUser.getTel())) {
                            return;
                        }

                        Intent phone = new Intent(this, BindingPageActivity.class);
                        phone.putExtra(FiledConstants.TYPE, 1);
                        startActivity(phone);
                        break;
                    case 2:
                        //谷歌
                        if (loginUser.getIs_google_authed() == 1) {
                            return;
                        }

                        Intent google = new Intent(this, BindingPageActivity.class);
                        google.putExtra(FiledConstants.TYPE, 2);
                        startActivity(google);
                        break;
                    case 3:
                        //邮箱

                        break;
                }
                break;
        }
    }

    @Override
    public void switchStatusSuccess() {
        if (fbLoginValidationFingerprint.isChecked()) {
            switch (type) {
                case 1:
                    loginUser.setTel_status(true);
                    break;
                case 2:
                    loginUser.setGoogle_status(true);
                    break;
                case 3:
                    loginUser.setEmail_status(true);
                    break;
            }

        } else {
            switch (type) {
                case 1:
                    loginUser.setTel_status(false);
                    break;
                case 2:
                    loginUser.setGoogle_status(false);
                    break;
                case 3:
                    loginUser.setEmail_status(false);
                    break;
            }
        }

        ExUserDao.insertUser(loginUser);
    }

    @Override
    public void switchStatusFail(String message) {
        boolean checked = fbLoginValidationFingerprint.isChecked();
        if (checked) {
            //打开失败
            fbLoginValidationFingerprint.setChecked(false, false, false);
        } else {
            //关闭失败
            fbLoginValidationFingerprint.setChecked(true, false, false);
        }
        Utils.showMessage(message);
    }

}
