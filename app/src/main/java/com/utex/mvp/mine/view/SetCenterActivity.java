package com.utex.mvp.mine.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.utex.R;
import com.utex.base.BaseActivity;
import com.utex.bean.UserDO;
import com.utex.common.Constants;
import com.utex.common.FiledConstants;
import com.utex.common.UMConstants;
import com.utex.core.AppComponent;
import com.utex.core.UTEXApplication;
import com.utex.db.ExUserDao;
import com.utex.mvp.hometab.view.HomeTabActivity;
import com.utex.mvp.mine.dagger2.DaggerSecurityCertificationComponent;
import com.utex.mvp.mine.dagger2.SecurityCertificationModule;
import com.utex.mvp.mine.presenter.ISecurityCertificationPresenter;
import com.utex.mvp.user.view.SetPwdActivity;
import com.utex.utils.SecurityIdentificationUtils;
import com.utex.utils.SharedPreferencesUtil;
import com.utex.utils.ToastUtils;
import com.utex.utils.Utils;
import com.utex.widget.button.switchbutton.FSwitchButton;
import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetCenterActivity extends BaseActivity implements ISecurityCertificationView {

    /**
     *
     */
    private static final int SETIMAEG_REQUEST = 199;

    private static final int LANGUAGE_REQUEST = 901;


    @Inject
    ISecurityCertificationPresenter iSecurityCertificationPresenter;

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;

    @BindView(R.id.fb_secuity_fingerprint)
    FSwitchButton fbSecuityFingerprint;

    @BindView(R.id.fb_secuity_image)
    FSwitchButton fbSecuityImage;

    @BindView(R.id.tv_security_certification_phone)
    TextView tvSecurityCertificationPhone;

    @BindView(R.id.tv_security_certification_google)
    TextView tvSecurityCertificationGoogle;

    @BindView(R.id.rl_security_fingerprint)
    RelativeLayout rlSecurityFingerprint;

    @BindView(R.id.rl_security_certification_modify_pwd)
    RelativeLayout rlSecurityCertificationModifyPwd;

    @BindView(R.id.rl_security_certification_phone)
    RelativeLayout rlSecurityCertificationPhone;

    @BindView(R.id.rl_security_certification_google)
    RelativeLayout rlSecurityCertificationGoogle;

    @BindView(R.id.rl_security_certification_image)
    RelativeLayout rlSecurityCertificationImage;

    @BindView(R.id.tv_about_us_quite)
    TextView tvAboutUsQuite;

    @BindView(R.id.tv_set_center_language_label)
    TextView tvSetCenterLanguageLabel;

    @BindView(R.id.tv_set_center_valuation_label)
    TextView tvSetCenterValuationLabel;

    @BindView(R.id.ll_set_center_titp_1)
    LinearLayout llSetCenterTitp1;

    @BindView(R.id.ll_set_center_titp_2)
    LinearLayout llSetCenterTitp2;

    @BindView(R.id.tv_set_center_bind_email)
    TextView tvSetCenterBindEmail;

    /**
     * 1 指纹
     * 2 图形
     */
    private int status;

    private boolean defaultStatusImage;

    private boolean defaultStatusFingerprint;
    private int lastPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_center);
        ButterKnife.bind(this);


        tvToolbarTitle.setText(getString(R.string.she_zhi_zhong_xin));
        int type = SharedPreferencesUtil.getInteger(FiledConstants.HAS_SECURITY_IDENTIFICATION, 0);

        switch (type) {
            case 1:
                //图形解锁
                fbSecuityImage.setChecked(true, false, false);
                defaultStatusImage = true;
                break;
            case 2:
                //指纹解锁
                fbSecuityFingerprint.setChecked(true, false, false);
                defaultStatusFingerprint = true;
                break;
        }

        if (!SecurityIdentificationUtils.isHardwareDetected()) {
            rlSecurityFingerprint.setVisibility(View.GONE);
        }


        fbSecuityFingerprint.setOnCheckedChangeCallback((checked, view) -> {
            if (checked) {
                status = 1;
                iSecurityCertificationPresenter.checkPassword(R.layout.activity_set_center);
            } else {
                SharedPreferencesUtil.putInteger(FiledConstants.HAS_SECURITY_IDENTIFICATION, 0);
                defaultStatusFingerprint = false;
            }
        });

        fbSecuityImage.setOnCheckedChangeCallback((checked, view) -> {
            if (checked) {
                status = 2;
                iSecurityCertificationPresenter.checkPassword(R.layout.activity_set_center);
            } else {
                SharedPreferencesUtil.putInteger(FiledConstants.HAS_SECURITY_IDENTIFICATION, 0);
                defaultStatusImage = false;
            }
        });

        fbSecuityFingerprint.setOnTouchListener((view, motionEvent) -> {
            if (!fbSecuityFingerprint.isChecked()) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        status = 1;
                        iSecurityCertificationPresenter.checkPassword(0);
                        break;
                }

                return true;
            } else {
                return false;
            }


        });

        fbSecuityImage.setOnTouchListener((view, motionEvent) -> {
            if (!fbSecuityImage.isChecked()) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        status = 2;
                        iSecurityCertificationPresenter.checkPassword(0);
                        break;
                }

                return true;
            } else {
                return false;
            }

        });

        UserDO loginUser = UTEXApplication.getLoginUser();
        if (loginUser == null) {
            rlSecurityCertificationModifyPwd.setVisibility(View.GONE);
            rlSecurityCertificationPhone.setVisibility(View.GONE);
            rlSecurityCertificationGoogle.setVisibility(View.GONE);
            rlSecurityFingerprint.setVisibility(View.GONE);
            rlSecurityCertificationImage.setVisibility(View.GONE);
            tvAboutUsQuite.setVisibility(View.GONE);
            llSetCenterTitp1.setVisibility(View.GONE);
            llSetCenterTitp2.setVisibility(View.GONE);
        }


        tvSetCenterValuationLabel.setText(SharedPreferencesUtil.getString(FiledConstants.PALTFORM_VALUAT.toUpperCase(), "USD"));

        int position = SharedPreferencesUtil.getInteger(Constants.SP_LANGUGE_POSITION, 2);
        String str = "";
        switch (position) {
            case 0:
                //简体中文
                str = Utils.getResourceString(R.string.zhong_wen);
                break;
            case 1:
                //切换成英语
                str = Utils.getResourceString(R.string.ying_yu);
                break;
            case 2:
                //切换成繁体中文
                str = Utils.getResourceString(R.string.fan_ti_zhong_wen);
                break;
            case 3:
                //日语
                str = Utils.getResourceString(R.string.ri_yu);
                break;
        }
        tvSetCenterLanguageLabel.setText(str);


        setBindInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setBindInfo();
    }

    private void setBindInfo() {
        UserDO loginUser = UTEXApplication.getLoginUser();
        if (loginUser == null) {
            return;
        }

        if (loginUser.isEmail_status()) {
            tvSetCenterBindEmail.setText(Utils.getResourceString(R.string.yi_kai_qi));
        } else {
            if (isBindEmail()) {
                tvSetCenterBindEmail.setText(Utils.getResourceString(R.string.wei_kai_qi));
            }
        }

        if (loginUser.isTel_status()) {
            tvSecurityCertificationPhone.setText(Utils.getResourceString(R.string.yi_kai_qi));
        } else {
            if (isBindTel()) {
                tvSecurityCertificationPhone.setText(Utils.getResourceString(R.string.wei_kai_qi));
            }
        }

        if (loginUser.isGoogle_status()) {
            tvSecurityCertificationGoogle.setText(Utils.getResourceString(R.string.yi_kai_qi));
        } else {
            if (isBindGoogle()) {
                tvSecurityCertificationGoogle.setText(Utils.getResourceString(R.string.wei_kai_qi));
            }
        }
    }

    private boolean isBindEmail() {
        if (!TextUtils.isEmpty(UTEXApplication.getLoginUser().getEmail())) {
            tvSetCenterBindEmail.setText(Utils.getResourceString(R.string.yi_bang_ding));
            return true;
        } else {
            tvSetCenterBindEmail.setText(Utils.getResourceString(R.string.wei_bang_ding));
            return false;
        }
    }


    private boolean isBindTel() {
        if (!TextUtils.isEmpty(UTEXApplication.getLoginUser().getTel())) {
            tvSecurityCertificationPhone.setText(Utils.getResourceString(R.string.yi_bang_ding));
            return true;
        } else {
            tvSecurityCertificationPhone.setText(Utils.getResourceString(R.string.wei_bang_ding));
            return false;
        }
    }

    private boolean isBindGoogle() {
        if (UTEXApplication.getLoginUser().getIs_google_authed() == 1) {
            tvSecurityCertificationGoogle.setText(Utils.getResourceString(R.string.yi_bang_ding));
            return true;
        } else {
            tvSecurityCertificationGoogle.setText(Utils.getResourceString(R.string.wei_bang_ding));
            return false;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        tvSetCenterValuationLabel.setText(SharedPreferencesUtil.getString(FiledConstants.PALTFORM_VALUAT.toUpperCase(), "USD"));
        setBindInfo();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SETIMAEG_REQUEST && resultCode == 200) {
            //成功
            SharedPreferencesUtil.putInteger(FiledConstants.HAS_SECURITY_IDENTIFICATION, 1);
        } else if (requestCode == SETIMAEG_REQUEST) {
            fbSecuityImage.setChecked(false, true, false);
        } else if (requestCode == LANGUAGE_REQUEST) {
            int position = SharedPreferencesUtil.getInteger(Constants.SP_LANGUGE_POSITION, 2);

            if (lastPosition != position) {
                //切换语言
                Intent intent = new Intent(this, HomeTabActivity.class);
                intent.putExtra(Constants.DARK_LIGHT_SWITCH, 1);
                startActivity(intent);

                UTEXApplication.getHomeActivity().finish();
                Intent homeTab = UTEXApplication.getHomeActivity().getIntent();
                homeTab.putExtra(Constants.DARK_LIGHT_SWITCH, 1);


                new Handler()
                        .postDelayed(() -> {
                            Intent setCenter = new Intent(SetCenterActivity.this, SetCenterActivity.class);
                            startActivity(setCenter);

                            finish();
                        }, 100);


//                finish();
                overridePendingTransition(R.anim.start_anim, R.anim.out_anim);
            }
        }
    }

    static boolean isAppClick = true;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSecurityCertificationComponent
                .builder()
                .appComponent(appComponent)
                .securityCertificationModule(new SecurityCertificationModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.iv_toolbar_left, R.id.rl_security_certification_modify_pwd,
            R.id.rl_security_certification_phone, R.id.rl_security_certification_google,
            R.id.tv_about_us_quite, R.id.fb_secuity_image, R.id.fb_secuity_fingerprint,
            R.id.rl_set_center_language, R.id.rl_set_center_valuation, R.id.rl_set_center_bind_email_parent})
    public void onClick(View view) {
        UserDO loginUser = UTEXApplication.getLoginUser();
        switch (view.getId()) {
            case R.id.rl_set_center_bind_email_parent:
                //绑定邮箱
                if (loginUser == null || null == loginUser.getEmail()) {
                    MobclickAgent.onEvent(this, UMConstants.MINE_SETTING_BIND_PHONE_ACTION);
                    Intent phone = new Intent(this, BindingPageActivity.class);
                    phone.putExtra(FiledConstants.TYPE, 3);
                    startActivity(phone);
                } else {
                    if (TextUtils.isEmpty(loginUser.getEmail())) {
                        MobclickAgent.onEvent(this, UMConstants.MINE_SETTING_BIND_PHONE_ACTION);
                        Intent phone = new Intent(this, BindingPageActivity.class);
                        phone.putExtra(FiledConstants.TYPE, 3);
                        startActivity(phone);
                    } else {
                        Intent phone = new Intent(this, LoginValidationSettingActivity.class);
                        phone.putExtra(FiledConstants.TYPE, 3);
                        startActivity(phone);
                    }
                }
                break;
            case R.id.rl_set_center_valuation:
                //计价方式
                MobclickAgent.onEvent(this, UMConstants.MINE_CURRENCY_BTN);

                Intent valuation = new Intent(this, SysSetLanguageValuationActivity.class);
                valuation.putExtra(FiledConstants.TYPE, 1);
                startActivity(valuation);

                break;
            case R.id.rl_set_center_language:
                //语言设置
                MobclickAgent.onEvent(this, UMConstants.MINE_LANGUAGE_BTN);

                lastPosition = SharedPreferencesUtil.getInteger(Constants.SP_LANGUGE_POSITION, 2);

                Intent language = new Intent(this, SysSetLanguageValuationActivity.class);
                language.putExtra(FiledConstants.TYPE, 0);
                startActivityForResult(language, LANGUAGE_REQUEST);

                break;
            case R.id.fb_secuity_fingerprint:
                status = 1;
                break;
            case R.id.fb_secuity_image:
                status = 2;
                break;
            case R.id.tv_about_us_quite:
                SharedPreferencesUtil.putInteger(FiledConstants.HAS_SECURITY_IDENTIFICATION, 0);
                UTEXApplication.clearUser();
                ExUserDao.clearUser();
                MobclickAgent.onEvent(this, UMConstants.MINE_SETTING_LOGOUT_BTN);

                finish();
                break;
            case R.id.iv_toolbar_left:
                finish();
                break;
            case R.id.rl_security_certification_modify_pwd:
                MobclickAgent.onEvent(this, UMConstants.MINE_SETTING_CHANGEPWD_ACTION);

                Intent intent = new Intent(this, SetPwdActivity.class);
                intent.putExtra(FiledConstants.TYPE, 2);
                startActivity(intent);
                break;
            case R.id.rl_security_certification_phone:
                if (TextUtils.isEmpty(loginUser.getTel())) {
                    MobclickAgent.onEvent(this, UMConstants.MINE_SETTING_BIND_PHONE_ACTION);
                    Intent phone = new Intent(this, BindingPageActivity.class);
                    phone.putExtra(FiledConstants.TYPE, 1);
                    startActivity(phone);
                } else {
                    Intent phone = new Intent(this, LoginValidationSettingActivity.class);
                    phone.putExtra(FiledConstants.TYPE, 1);
                    startActivity(phone);
                }
                break;
            case R.id.rl_security_certification_google:
                if (loginUser.getIs_google_authed() != 1) {
                    MobclickAgent.onEvent(this, UMConstants.MINE_SETTING_BIND_GOOGLE_ACTION);
                    Intent phone = new Intent(this, BindingPageActivity.class);
                    phone.putExtra(FiledConstants.TYPE, 2);
                    startActivityForResult(phone, 199);
                } else {
                    Intent google = new Intent(this, LoginValidationSettingActivity.class);
                    google.putExtra(FiledConstants.TYPE, 2);
                    startActivity(google);
                }
                break;
        }
    }

    @Override
    public void checkPasswordFail() {
        ToastUtils.show(Utils.getResourceString(R.string.mi_ma_jiao_yan_cuo_wu));
        fbSecuityImage.setChecked(defaultStatusImage, false, false);
        fbSecuityFingerprint.setChecked(defaultStatusFingerprint, false, false);
    }

    @Override
    public void checkPasswordSuccess() {
        switch (status) {
            case 1:
                fbSecuityImage.setChecked(false, true, false);
                fbSecuityFingerprint.setChecked(true, true, false);
                if (SecurityIdentificationUtils.isHardwareDetected()) {
                    //有模块
                    if (!SecurityIdentificationUtils.hasEnrolledFingerprints()) {
                        //没有指纹
                        SecurityIdentificationUtils.goSetFingerprintsPage(SetCenterActivity.this);
                        fbSecuityFingerprint.setChecked(false, true, false);
                    } else {
                        SharedPreferencesUtil.putInteger(FiledConstants.HAS_SECURITY_IDENTIFICATION, 2);
                    }
                }
                defaultStatusFingerprint = true;
                defaultStatusImage = false;
                break;
            case 2:
                fbSecuityFingerprint.setChecked(false, true, false);
                fbSecuityImage.setChecked(true, true, false);
                //跳出图形界面录入图形
                String string = SharedPreferencesUtil.getString(FiledConstants.IMAGE_UNLOCKING, "");
                if (TextUtils.isEmpty(string)) {
                    Intent intent = new Intent(SetCenterActivity.this, ImageEntryActivity.class);
                    startActivityForResult(intent, SETIMAEG_REQUEST);
                } else {
                    SharedPreferencesUtil.putInteger(FiledConstants.HAS_SECURITY_IDENTIFICATION, 1);
                }

                defaultStatusFingerprint = false;
                defaultStatusImage = true;
                break;
        }
    }

    @Override
    public void popupWindowDismiss() {
        fbSecuityImage.setChecked(defaultStatusImage, false, false);
        fbSecuityFingerprint.setChecked(defaultStatusFingerprint, false, false);
    }

    @Override
    public void keyboardDiss() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

}
