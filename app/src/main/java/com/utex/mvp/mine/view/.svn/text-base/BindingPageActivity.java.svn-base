package com.utex.mvp.mine.view;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.utex.R;
import com.utex.base.BaseActivity;
import com.utex.bean.UserDO;
import com.utex.common.Constants;
import com.utex.common.FiledConstants;
import com.utex.core.AppComponent;
import com.utex.core.UTEXApplication;
import com.utex.db.ExUserDao;
import com.utex.listener.ETtextChangeListener;
import com.utex.mvp.mine.bean.GoogleVO;
import com.utex.mvp.mine.bean.PhoneCountryNumVO;
import com.utex.mvp.mine.dagger2.BindingPageModule;
import com.utex.mvp.mine.dagger2.DaggerBindingPageComponent;
import com.utex.mvp.mine.presenter.IBindingPagePresenter;
import com.utex.mvp.user.bean.SendCodeDTO;
import com.utex.mvp.user.bean.GtCodeTypeEnum;
import com.utex.task.SecondTask;
import com.utex.utils.SharedPreferencesUtil;
import com.utex.utils.ToastUtils;
import com.utex.utils.Utils;
import com.utex.widget.CodeInputView;
import com.geetest.sdk.Bind.GT3GeetestBindListener;
import com.geetest.sdk.Bind.GT3GeetestUtilsBind;
import com.geetest.sdk.Bind.O0000O0o;

import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BindingPageActivity extends BaseActivity implements IBindingPageView {

    @Inject
    IBindingPagePresenter iBindingPagePresenter;

    @BindView(R.id.ll_bind_page_input_google_parent)
    LinearLayout llBindPageInputGoogleParent;

    @BindView(R.id.ll_bind_page_google_parent)
    LinearLayout llBindPageGoogleParent;

    @BindView(R.id.ll_bind_page_phone_parent)
    LinearLayout llBindPagePhoneParent;

    @BindView(R.id.iv_bind_page_qrcode)
    ImageView ivBindPaegQrcode;

    @BindView(R.id.tv_bind_page_secret_key)
    TextView tvBindPageSecretKey;

    @BindView(R.id.et_bind_page_input_phone)
    EditText etBindPaegInputPhone;

    @BindView(R.id.animation_bind_phone)
    LottieAnimationView animationBindPhone;

    @BindView(R.id.tv_register_second)
    TextView tvRegisterSecond;

    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    @BindView(R.id.et_bind_page_code)
    EditText etBindPageCode;

    @BindView(R.id.tv_bind_page_phone_sel)
    TextView tvBindPagePhoneSel;

    @BindView(R.id.civ_activate)
    CodeInputView civActivate;

    @BindView(R.id.tv_binding_page_label)
    TextView tvBindingPageLabel;

    /**
     * 1 手机
     * 2 谷歌
     * 3 邮箱
     */
    private int type;

    private GT3GeetestUtilsBind gt3GeetestUtils;

    private ArrayList<PhoneCountryNumVO.DataBean> countryList;
    private String secret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding_page);
        ButterKnife.bind(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        gt3GeetestUtils = new GT3GeetestUtilsBind(this);
        gt3GeetestUtils.getISonto();
        gt3GeetestUtils.setDialogTouch(true);

        type = getIntent().getIntExtra(FiledConstants.TYPE, 1);

        switch (type) {
            case 1:
                //手机
                llBindPagePhoneParent.setVisibility(View.VISIBLE);
                llBindPageInputGoogleParent.setVisibility(View.GONE);
                llBindPageGoogleParent.setVisibility(View.GONE);
                iBindingPagePresenter.getPhoneCountry();
                String string = SharedPreferencesUtil.getString(Constants.SP_COUNTRY_PHONE_NUM_POSITION, "86");
                tvBindPagePhoneSel.setText("+" + string);
                etBindPageCode.addTextChangedListener(new ETtextChangeListener() {
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        super.onTextChanged(charSequence, i, i1, i2);
                        if (!TextUtils.isEmpty(charSequence) && !TextUtils.isEmpty(etBindPaegInputPhone.getText())) {
                            tvConfirm.setClickable(true);
                            tvConfirm.setBackgroundResource(R.drawable.login_selector);
                        } else {
                            tvConfirm.setClickable(false);
                            tvConfirm.setBackgroundColor(Utils.getResourceColor(BindingPageActivity.this, R.color.bd4d6d5_33ffffff));
                        }
                    }
                });
                break;
            case 2:
                //谷歌
                llBindPagePhoneParent.setVisibility(View.GONE);
                llBindPageInputGoogleParent.setVisibility(View.GONE);
                llBindPageGoogleParent.setVisibility(View.VISIBLE);
                iBindingPagePresenter.getGoogleBindInfo();
                break;
            case 3:
                //邮箱绑定
                tvBindPagePhoneSel.setVisibility(View.GONE);
                llBindPagePhoneParent.setVisibility(View.VISIBLE);
                llBindPageInputGoogleParent.setVisibility(View.GONE);
                llBindPageGoogleParent.setVisibility(View.GONE);
                iBindingPagePresenter.getPhoneCountry();
                etBindPageCode.addTextChangedListener(new ETtextChangeListener() {
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        super.onTextChanged(charSequence, i, i1, i2);
                        if (!TextUtils.isEmpty(charSequence) && !TextUtils.isEmpty(etBindPaegInputPhone.getText())) {
                            tvConfirm.setClickable(true);
                            tvConfirm.setBackgroundResource(R.drawable.login_selector);
                        } else {
                            tvConfirm.setClickable(false);
                            tvConfirm.setBackgroundColor(Utils.getResourceColor(BindingPageActivity.this, R.color.bd4d6d5_33ffffff));
                        }
                    }
                });

                tvBindingPageLabel.setText(Utils.getResourceString(R.string.bang_ding_you_xiang_di_zhi));

                etBindPaegInputPhone.setHint(Utils.getResourceString(R.string.qing_shu_ru_nin_yao_bang_ding_de_you_xiang_zhang_hao));

                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        String string = SharedPreferencesUtil.getString(Constants.SP_COUNTRY_PHONE_NUM_POSITION, "+86");
        tvBindPagePhoneSel.setText("+" + string);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBindingPageComponent
                .builder()
                .appComponent(appComponent)
                .bindingPageModule(new BindingPageModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.iv_toolbar_left, R.id.tv_bind_page_phone_sel, R.id.tv_bind_page_secret_key,
            R.id.tv_register_second, R.id.tv_confirm, R.id.tv_binding_page_google_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_bind_page_secret_key:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(secret);
                Toast.makeText(BindingPageActivity.this, Utils.getResourceString(R.string.fu_zhi_cheng_gong), Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_toolbar_left:
                finish();
                break;
            case R.id.tv_bind_page_phone_sel:
                Intent intent = new Intent(this, SysSetLanguageValuationActivity.class);
                intent.putExtra(FiledConstants.COUNTRYLIST, countryList);
                intent.putExtra(FiledConstants.TYPE, 2);
                startActivity(intent);
                break;
            case R.id.tv_register_second:
                if (!TextUtils.isEmpty(etBindPaegInputPhone.getText())) {
//                    tvRegisterSecond.setClickable(false);
                    gt3GeetestUtils.showLoadingDialog(this, null);
                    iBindingPagePresenter.getGTCode();
                }
                break;
            case R.id.tv_confirm:
                if (!TextUtils.isEmpty(etBindPaegInputPhone.getText()) && !TextUtils.isEmpty(etBindPageCode.getText())) {
                    tvConfirm.setClickable(false);
                    animationBindPhone.setVisibility(View.VISIBLE);

                    if (type == 1) {
                        //手机
                        iBindingPagePresenter.confirmBindPhone(tvBindPagePhoneSel.getText().toString(), etBindPaegInputPhone.getText().toString(),
                                etBindPageCode.getText().toString());
                    } else if (type == 3) {
                        iBindingPagePresenter.bindEmail(etBindPaegInputPhone.getText().toString(),
                                etBindPageCode.getText().toString());
                    }
                }
                break;
            case R.id.tv_binding_page_google_next:
                //绑定谷歌下一步
                llBindPagePhoneParent.setVisibility(View.GONE);
                llBindPageInputGoogleParent.setVisibility(View.VISIBLE);
                llBindPageGoogleParent.setVisibility(View.GONE);

                civActivate.setCodeInputViewListener(new CodeInputView.CodeInputViewListener() {
                    @Override
                    public void activate(String code) {
                        //最终的code,请求网络
                        iBindingPagePresenter.bindGoogle(secret, code);
                    }
                });
                break;
        }
    }

    @Override
    public void getGoogleBindInfoSuccess(GoogleVO body) {
        Glide.with(this)
                .load(body.getData().getUrl())
                .into(ivBindPaegQrcode);

        Utils.setFontSpan(tvBindPageSecretKey,
                new String[]{Utils.getResourceString(R.string.shi_liu_wei_mi_yao), body.getData().getSecret()}
                , new Integer[]{R.color.b333333_ffffff, R.color.b999999_e6ffffff});
        secret = body.getData().getSecret();
    }

    @Override
    public void getEmailGTCodeSuccess(JSONObject jsonObject) {
        gt3GeetestUtils.gtSetApi1Json(jsonObject);
        gt3GeetestUtils.getGeetest(this, null, null, null, new GT3GeetestBindListener() {
            public void gt3DialogOnError(String error) {
                Log.e("TAG", error);
                gt3GeetestUtils.gt3TestClose();
                bindPageReset();
            }

            @Override
            public void gt3DialogReady() {
                super.gt3DialogReady();
            }

            @Override
            public boolean gt3SetIsCustom() {
                return true;
            }

            @Override
            public void gt3GetDialogResult(boolean status, String json) {
                super.gt3GetDialogResult(status, json);
                if (status) {
                    try {
                        JSONObject res_json = new JSONObject(json);

                        //邮箱验证码，倒计时手动记录
                        SendCodeDTO sendCodeDTO = new SendCodeDTO();
                        sendCodeDTO.setChallenge(res_json.getString("geetest_challenge"));
                        sendCodeDTO.setValidate(res_json.getString("geetest_validate"));
                        sendCodeDTO.setSeccode(res_json.getString("geetest_seccode"));
                        sendCodeDTO.setStatus(1);
                        if (type == 1) {
                            sendCodeDTO.setUsername(etBindPaegInputPhone.getText().toString());
                            sendCodeDTO.setCode_type(GtCodeTypeEnum.BIND_TEL.getCodeType());
                            sendCodeDTO.setCarrier(SendCodeDTO.LOGIN_TYPE_TEL);
                            sendCodeDTO.setCountry_code(tvBindPagePhoneSel.getText().toString());
                        } else {
                            sendCodeDTO.setUsername(etBindPaegInputPhone.getText().toString());
                            sendCodeDTO.setCarrier(SendCodeDTO.LOGIN_TYPE_EMAIL);
                            sendCodeDTO.setCode_type(GtCodeTypeEnum.BIND_EMAIL.getCodeType());
                        }

                        iBindingPagePresenter.sendTelCode(sendCodeDTO);
                    } catch (Exception e) {
                        gt3GeetestUtils.gt3TestClose();
                        bindPageReset();
                    }

                } else {
                    //失败
                    gt3GeetestUtils.gt3TestClose();
                    bindPageReset();
                }

                O0000O0o dialog = gt3GeetestUtils.getDialog();
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        bindPageReset();
                    }
                });

                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        bindPageReset();
                    }
                });

            }
        });
    }

    @Override
    public void sendTelCodeSuccess() {
        //发送验证码成功
        gt3GeetestUtils.gt3TestFinish();
        bindPageReset();
        SecondTask.startTask(3);
        SecondTask.setListener(new SecondTask.SecondListener() {
            @Override
            public void upDateView(int second) {

            }

            @Override
            public void upDateForgetPwdView(int second) {

            }

            @Override
            public void upDateActivateView(int second) {

            }

            @Override
            public void upDateBindPhoneView(int second) {
                if (tvRegisterSecond != null) {
                    //读秒监听
                    if (second > 0) {
                        tvRegisterSecond.setText("(" + String.valueOf(second) + ")s" + Utils.getResourceString(R.string.chong_xin_fa_song));
                        tvRegisterSecond.setTextColor(Utils.getResourceColor(BindingPageActivity.this, R.color.bcccccc_4cffffff));
                        tvRegisterSecond.setClickable(false);
                    } else {
                        //重新发送
                        tvRegisterSecond.setText(Utils.getResourceString(R.string.fa_song_yan_zheng_ma));
                        tvRegisterSecond.setTextColor(Utils.getResourceColor(BindingPageActivity.this, R.color.f398155));
                        tvRegisterSecond.setClickable(true);
                    }
                }
            }
        });
    }

    @Override
    public void confirmBindPhoneSuccess() {
        UserDO loginUser = UTEXApplication.getLoginUser();
        loginUser.setTel(tvBindPagePhoneSel.getText().toString() + etBindPaegInputPhone.getText().toString());
        loginUser.setTwice_auth_way("tel");
        loginUser.setTel_status(true);
        ExUserDao.insertUser(loginUser);
        ToastUtils.show(Utils.getResourceString(R.string.bang_ding_cheng_gong));
        finish();
    }

    @Override
    public void confirmBindPhoneFail(String str) {
        bindPageReset();
        Utils.showMessage(str);
    }

    @Override
    public void getPhoneCountrySuccess(ArrayList<PhoneCountryNumVO.DataBean> data) {
        //获得国家号成功
        this.countryList = data;
    }

    @Override
    public void getGTCodeFail(String message) {
        gt3GeetestUtils.gt3Dismiss();
        Utils.showMessage(message);
        if ("2003".equals(message)) {
            civActivate.clearData();
        }
    }

    @Override
    public void bindGoogleSuccess() {
        UserDO loginUser = UTEXApplication.getLoginUser();
        loginUser.setIs_google_authed(1);
        loginUser.setTwice_auth_way("google");
        loginUser.setGoogle_status(true);
        ExUserDao.insertUser(loginUser);
        ToastUtils.show(Utils.getResourceString(R.string.bang_ding_cheng_gong));
        finish();
    }

    @Override
    public void bindGoogleFail() {
        ToastUtils.show(Utils.getResourceString(R.string.shi_bai));

    }

    @Override
    public void bindEmailSuccess() {
        UserDO loginUser = UTEXApplication.getLoginUser();
        loginUser.setEmail(etBindPaegInputPhone.getText().toString());
        loginUser.setEmail_status(true);
        ExUserDao.insertUser(loginUser);
        ToastUtils.show(Utils.getResourceString(R.string.bang_ding_cheng_gong));
        finish();
    }

    @Override
    public void failMessage(String errMessage) {
        Utils.showMessage(errMessage);
    }

    /**
     * 绑定手机页面重置
     */
    private void bindPageReset() {
        tvConfirm.setClickable(true);
        tvRegisterSecond.setClickable(true);
        animationBindPhone.setVisibility(View.GONE);
    }
}
