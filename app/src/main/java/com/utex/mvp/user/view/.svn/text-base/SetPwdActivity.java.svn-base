package com.utex.mvp.user.view;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.utex.R;
import com.utex.base.BaseActivity;
import com.utex.common.FiledConstants;
import com.utex.common.UMConstants;
import com.utex.core.AppComponent;
import com.utex.core.UTEXApplication;
import com.utex.listener.ETtextChangeListener;
import com.utex.mvp.mine.bean.PhoneCountryNumVO;
import com.utex.mvp.mine.view.SysSetLanguageValuationActivity;
import com.utex.mvp.user.bean.GtCodeTypeEnum;
import com.utex.mvp.user.bean.SendCodeDTO;
import com.utex.mvp.user.bean.UserDTO;
import com.utex.mvp.user.bean.UserVerifyVO;
import com.utex.mvp.user.dagger2.DaggerSetPwdComponent;
import com.utex.mvp.user.dagger2.SetPwdModule;
import com.utex.mvp.user.presenter.ISetPwdPresenter;
import com.utex.task.SecondTask;
import com.utex.utils.ToastUtils;
import com.utex.utils.Utils;
import com.geetest.sdk.Bind.GT3GeetestBindListener;
import com.geetest.sdk.Bind.GT3GeetestUtilsBind;
import com.geetest.sdk.Bind.O0000O0o;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 忘记密码修改密码页面
 */
public class SetPwdActivity extends BaseActivity implements SecondTask.SecondListener, ISetPwdView {

    @Inject
    ISetPwdPresenter iSetPwdPresenter;

    @BindView(R.id.et_forget_pwd_email)
    EditText etForgetPwdEmail;

    @BindView(R.id.et_forget_pwd_code)
    EditText etForgetPwdCode;

    @BindView(R.id.tv_forget_pwd_code_send)
    TextView tvForgetPwdCodeSend;

    @BindView(R.id.ll_forget_pwd_one_parent)
    LinearLayout llForgetPwdOneParent;

    @BindView(R.id.ll_forget_pwd_two_parent)
    LinearLayout llForgetPwdTwoParent;

    @BindView(R.id.ll_forget_pwd_three_parent)
    LinearLayout llForgetPwdThreeParent;

    @BindView(R.id.tv_next)
    TextView tvNext;

    @BindView(R.id.et_forget_pwd)
    EditText etForgetPwd;

    @BindView(R.id.tv_complete)
    TextView tvComplete;

    @BindView(R.id.et_forget_old_pwd)
    EditText etForgetOldPwd;

    @BindView(R.id.et_forget_new_pwd)
    EditText etForgetNewPwd;

    @BindView(R.id.et_forget_confirm_new_pwd)
    EditText etForgetConfirmNewPwd;

    @BindView(R.id.et_forget_pwd_1)
    EditText etForgetPwd1;

    @BindView(R.id.tv_forget_confirm_reset)
    TextView tvForgetConfirmReset;

    @BindView(R.id.animation_set_pwd)
    LottieAnimationView animationSetPwd;

    @BindView(R.id.animation_forget)
    LottieAnimationView animationForget;

    @BindView(R.id.tv_forget_pwd_country_type)
    TextView tvForgetPwdCountryType;

    private GT3GeetestUtilsBind gt3GeetestUtils;
    private int type;
    private final int SELECT_PHONE = 199;
    private ArrayList<PhoneCountryNumVO.DataBean> countryList;
    private PhoneCountryNumVO.DataBean currCountry;

    /**
     * 0 极验 忘记密码第一步
     * 1 极验 发送短信或者邮箱
     */
    private int status;

    private UserVerifyVO.DataBean user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        ButterKnife.bind(this);
        type = getIntent().getIntExtra(FiledConstants.TYPE, 1);


        gt3GeetestUtils = new GT3GeetestUtilsBind(this);
        gt3GeetestUtils.getISonto();
        gt3GeetestUtils.setDialogTouch(true);

        switch (type) {
            case 1:
                //忘记密码
                llForgetPwdOneParent.setVisibility(View.VISIBLE);
                llForgetPwdTwoParent.setVisibility(View.GONE);
                llForgetPwdThreeParent.setVisibility(View.GONE);

                iSetPwdPresenter.getCountery();

                etForgetPwdEmail.addTextChangedListener(new ETtextChangeListener() {
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        super.onTextChanged(charSequence, i, i1, i2);
                        changeBtnStatus();

                        Drawable bottomDrawable = getResources().getDrawable(R.drawable.shape_login_et_bottom_line_bg);
                        bottomDrawable.setBounds(0, 0, bottomDrawable.getMinimumWidth(), bottomDrawable.getMinimumHeight()); //设置边界

                        if (TextUtils.isEmpty(etForgetPwdEmail.getText())) {
                            etForgetPwdEmail.setCompoundDrawables(null, null, null, bottomDrawable);
                        } else {
                            Drawable drawable = getResources().getDrawable(R.drawable.my_icon_ycx);

                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                            etForgetPwdEmail.setCompoundDrawables(null, null, drawable, bottomDrawable);//画在右边
                        }

                    }
                });
                etForgetPwdCode.addTextChangedListener(new ETtextChangeListener() {
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        super.onTextChanged(charSequence, i, i1, i2);
                        if (TextUtils.isEmpty(etForgetPwd.getText().toString()) || TextUtils.isEmpty(etForgetPwd1.getText().toString()) || TextUtils.isEmpty(etForgetPwdCode.getText().toString())) {
                            tvComplete.setClickable(false);
                            tvComplete.setBackgroundColor(Utils.getResourceColor(SetPwdActivity.this, R.color.bd4d6d5_33ffffff));
                        } else {
                            tvComplete.setClickable(true);
                            tvComplete.setBackgroundResource(R.drawable.login_selector);
                        }
                    }
                });

                etForgetPwd.addTextChangedListener(new ETtextChangeListener() {
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        super.onTextChanged(charSequence, i, i1, i2);
                        if (TextUtils.isEmpty(etForgetPwd.getText().toString()) || TextUtils.isEmpty(etForgetPwd1.getText().toString()) || TextUtils.isEmpty(etForgetPwdCode.getText().toString())) {
                            tvComplete.setClickable(false);
                            tvComplete.setBackgroundColor(Utils.getResourceColor(SetPwdActivity.this, R.color.bd4d6d5_33ffffff));
                        } else {
                            tvComplete.setClickable(true);
                            tvComplete.setBackgroundResource(R.drawable.login_selector);
                        }
                    }
                });

                etForgetPwd1.addTextChangedListener(new ETtextChangeListener() {
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        super.onTextChanged(charSequence, i, i1, i2);
                        if (TextUtils.isEmpty(etForgetPwd.getText().toString()) || TextUtils.isEmpty(etForgetPwd1.getText().toString()) || TextUtils.isEmpty(etForgetPwdCode.getText().toString())) {
                            tvComplete.setClickable(false);
                            tvComplete.setBackgroundColor(Utils.getResourceColor(SetPwdActivity.this, R.color.bd4d6d5_33ffffff));
                        } else {
                            tvComplete.setClickable(true);
                            tvComplete.setBackgroundResource(R.drawable.login_selector);
                        }
                    }
                });


                SecondTask.setListener(this);

                etForgetPwdEmail.setOnTouchListener((view, motionEvent) -> {
                    Drawable drawable = etForgetPwdEmail.getCompoundDrawables()[2];
                    //如果右边没有图片，不再处理
                    if (drawable == null)
                        return false;
                    //如果不是按下事件，不再处理
                    if (motionEvent.getAction() != MotionEvent.ACTION_UP)
                        return false;
                    if (motionEvent.getX() > etForgetPwdEmail.getWidth()
                            - etForgetPwdEmail.getPaddingRight()
                            - drawable.getIntrinsicWidth()) {

                        etForgetPwdEmail.setText("");
                    }
                    return false;
                });

                break;
            case 2:
                //修改密码
                llForgetPwdOneParent.setVisibility(View.GONE);
                llForgetPwdTwoParent.setVisibility(View.GONE);
                llForgetPwdThreeParent.setVisibility(View.VISIBLE);

                etForgetOldPwd.addTextChangedListener(new ETtextChangeListener() {
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        super.onTextChanged(charSequence, i, i1, i2);
                        changeBtnStatus();
                    }
                });
                etForgetNewPwd.addTextChangedListener(new ETtextChangeListener() {
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        super.onTextChanged(charSequence, i, i1, i2);
                        changeBtnStatus();
                    }
                });
                etForgetConfirmNewPwd.addTextChangedListener(new ETtextChangeListener() {
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        super.onTextChanged(charSequence, i, i1, i2);
                        changeBtnStatus();
                    }
                });
                break;
        }
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSetPwdComponent
                .builder()
                .appComponent(appComponent)
                .setPwdModule(new SetPwdModule(this))
                .build()
                .inject(this);
    }

    /**
     * 改变按钮状态
     */
    private void changeBtnStatus() {
        switch (type) {
            case 1:
                if (TextUtils.isEmpty(etForgetPwdEmail.getText().toString())) {
                    tvNext.setBackgroundColor(Utils.getResourceColor(this, R.color.bd4d6d5_33ffffff));
                } else {
                    tvNext.setBackgroundResource(R.drawable.login_selector);
                }
                break;
            case 2:
                if (TextUtils.isEmpty(etForgetOldPwd.getText().toString()) || TextUtils.isEmpty(etForgetNewPwd.getText().toString()) ||
                        TextUtils.isEmpty(etForgetConfirmNewPwd.getText().toString())) {
                    tvForgetConfirmReset.setBackgroundColor(Utils.getResourceColor(this, R.color.bd4d6d5_33ffffff));
                } else {
                    tvForgetConfirmReset.setBackgroundResource(R.drawable.login_selector);
                }
                break;
        }

    }

    /**
     * 重置设置密码页面
     */
    private void reSetPwdPage() {
        tvForgetConfirmReset.setClickable(true);
        tvForgetConfirmReset.setText(Utils.getResourceString(R.string.que_ren_chong_zhi));
        animationSetPwd.setVisibility(View.GONE);
    }

    /**
     * 重置忘记密码页面
     */
    private void reForgetPwdPage() {
        tvComplete.setClickable(true);
        tvComplete.setText(Utils.getResourceString(R.string.que_ren_chong_zhi));
        animationForget.setVisibility(View.GONE);
    }


    @OnClick({R.id.tv_next, R.id.iv_toolbar_left, R.id.tv_forget_pwd_code_send, R.id.tv_complete, R.id.tv_forget_confirm_reset,
            R.id.tv_forget_pwd_country_type})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_pwd_country_type:
                Intent intent = new Intent(this, SysSetLanguageValuationActivity.class);
                intent.putExtra(FiledConstants.COUNTRYLIST, countryList);
                intent.putExtra(FiledConstants.TYPE, 2);
                startActivityForResult(intent, SELECT_PHONE);
                break;
            case R.id.tv_next:
                //下一步
                if (TextUtils.isEmpty(etForgetPwdEmail.getText())) {
                    //输入不得为空
                    failMessage("1106");
                    return;
                }

                status = 0;
                iSetPwdPresenter.getGTCode(etForgetPwdEmail.getText().toString());
//                iSetPwdPresenter.checkUser(etForgetPwdEmail.getText().toString());
//                iSetPwdPresenter.forgetPwdNext(etForgetPwdEmail.getText().toString(), etForgetPwdCode.getText().toString());
                break;
            case R.id.iv_toolbar_left:
                finish();
                break;
            case R.id.tv_forget_pwd_code_send:
                //忘记密码第二步的 发送信息
                status = 1;
                iSetPwdPresenter.getGTCode(etForgetPwdEmail.getText().toString());

                gt3GeetestUtils.showLoadingDialog(this, null);
                break;
            case R.id.tv_complete:
                //忘记密码第二步
                if (TextUtils.isEmpty(etForgetPwd.getText()) || TextUtils.isEmpty(etForgetPwd1.getText())) {
                    return;
                }

                if (TextUtils.isEmpty(etForgetPwdCode.getText())) {
                    failMessage("9001");
                    return;
                }

                if (!Utils.checkPwd(etForgetPwd1.getText().toString())) {
                    failMessage("1101");
                    return;
                }

                if (!etForgetPwd1.getText().toString().equals(etForgetPwd.getText().toString())) {
                    failMessage("9006");
                    return;
                }


                iSetPwdPresenter.forgetPwdComplete(user, etForgetPwd.getText().toString(), etForgetPwdCode.getText().toString());
                tvComplete.setClickable(false);
                tvComplete.setText("");
                animationForget.setVisibility(View.VISIBLE);

                break;
            case R.id.tv_forget_confirm_reset:
                if (TextUtils.isEmpty(etForgetNewPwd.getText()) || TextUtils.isEmpty(etForgetConfirmNewPwd.getText()) || TextUtils.isEmpty(etForgetOldPwd.getText())) {
                    failMessage("1106");
                    return;
                }

                if (!Utils.checkPwd(etForgetNewPwd.getText().toString())) {
                    failMessage("1101");
                    return;
                }


                if (!etForgetNewPwd.getText().toString().equals(etForgetConfirmNewPwd.getText().toString())) {
                    failMessage("9006");
                    return;
                }

                if (etForgetOldPwd.getText().toString().equals(etForgetConfirmNewPwd.getText().toString())) {
                    failMessage("9007");
                    return;
                }

                MobclickAgent.onEvent(this, UMConstants.MINE_SETTING_CHANGEPWD_PAGE_DONE_BTN);


                iSetPwdPresenter.confirmResetPwd(etForgetOldPwd.getText().toString(), etForgetNewPwd.getText().toString(), etForgetConfirmNewPwd.getText().toString());
                tvForgetConfirmReset.setText("");
                tvForgetConfirmReset.setClickable(false);
                animationSetPwd.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void failMessage(String message) {
        Utils.showMessage(message);
        gt3GeetestUtils.gt3TestClose();
    }

    @Override
    public void checkUserSuccess(UserVerifyVO.DataBean data) {
        this.user = data;
        gt3GeetestUtils.gt3TestFinish();
        //第一步校验成功跳入第二步
        llForgetPwdOneParent.setVisibility(View.GONE);
        llForgetPwdTwoParent.setVisibility(View.VISIBLE);
    }

    @Override
    public void upDateView(int second) {

    }

    @Override
    public void upDateForgetPwdView(int second) {
        if (tvForgetPwdCodeSend != null) {
            //读秒监听
            if (second > 0) {
                tvForgetPwdCodeSend.setText("(" + String.valueOf(second) + ")s" + Utils.getResourceString(R.string.chong_xin_fa_song));
                tvForgetPwdCodeSend.setTextColor(Utils.getResourceColor(SetPwdActivity.this, R.color.bcccccc_4cffffff));
                tvForgetPwdCodeSend.setClickable(false);
            } else {
                //重新发送
                tvForgetPwdCodeSend.setText(Utils.getResourceString(R.string.fa_song_yan_zheng_ma));
                tvForgetPwdCodeSend.setTextColor(Utils.getResourceColor(SetPwdActivity.this, R.color.f398155));
                tvForgetPwdCodeSend.setClickable(true);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 199 && resultCode == 200) {
            currCountry = (PhoneCountryNumVO.DataBean) data.getSerializableExtra("country");
            tvForgetPwdCountryType.setText(currCountry.getName_en() + "(+" + currCountry.getArea_code() + ")");
        }
    }

    @Override
    public void upDateActivateView(int second) {

    }

    @Override
    public void upDateBindPhoneView(int second) {

    }

    @Override
    public void getGTCodeSuccess(JSONObject jsonObject) {
        gt3GeetestUtils.gtSetApi1Json(jsonObject);
        gt3GeetestUtils.getGeetest(this, null, null, null, new GT3GeetestBindListener() {
            public void gt3DialogOnError(String error) {
                Log.e("TAG", error);
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

                        switch (SetPwdActivity.this.status) {
                            case 0:
                                //忘记密码第一步
                                UserDTO userDTO = new UserDTO();
                                userDTO.setUsername(etForgetPwdEmail.getText().toString());
                                userDTO.setChallenge(res_json.getString("geetest_challenge"));
                                userDTO.setValidate(res_json.getString("geetest_validate"));
                                userDTO.setSeccode(res_json.getString("geetest_seccode"));
                                userDTO.setStatus(1);
                                userDTO.setToken_type(UserDTO.TOKEN_TYPE_FORGET_PASSWORD_TOKEN);

                                iSetPwdPresenter.checkUser(userDTO);
                                break;
                            case 1:
                                //忘记密码第二步

                                //邮箱验证码，倒计时手动记录
                                SendCodeDTO sendCodeDTO = new SendCodeDTO();
                                sendCodeDTO.setChallenge(res_json.getString("geetest_challenge"));
                                sendCodeDTO.setValidate(res_json.getString("geetest_validate"));
                                sendCodeDTO.setSeccode(res_json.getString("geetest_seccode"));
                                sendCodeDTO.setCode_type(GtCodeTypeEnum.FORGET_PASSWORD.getCodeType());
                                sendCodeDTO.setStatus(1);
                                sendCodeDTO.setUsername(etForgetPwdEmail.getText().toString());

                                if (etForgetPwdEmail.getText().toString().contains("@")) {
                                    sendCodeDTO.setCarrier(SendCodeDTO.LOGIN_TYPE_EMAIL);
                                } else {
                                    sendCodeDTO.setCarrier(SendCodeDTO.LOGIN_TYPE_TEL);
                                    sendCodeDTO.setCountry_code("+" + currCountry.getArea_code());
                                }

                                iSetPwdPresenter.sendEmail(sendCodeDTO);
                                break;
                        }

                    } catch (Exception e) {
                        gt3GeetestUtils.gt3TestClose();
                    }


                } else {
                    //失败
                    gt3GeetestUtils.gt3TestClose();
                }
            }
        });

        O0000O0o dialog = gt3GeetestUtils.getDialog();
        dialog.setOnDismissListener(dialogInterface -> reForgetPwdPage());
        dialog.setOnCancelListener(dialogInterface -> reForgetPwdPage());
    }

    @Override
    public void sendEmailSuccess() {
        gt3GeetestUtils.gt3TestFinish();
        SecondTask.startTask(1);
    }

    @Override
    public void sendEmailError(String str) {
        gt3GeetestUtils.gt3Dismiss();
        Utils.showMessage(str);
    }

    @Override
    public void forgetPwdNextSuccess() {
        llForgetPwdOneParent.setVisibility(View.GONE);
        llForgetPwdTwoParent.setVisibility(View.VISIBLE);
    }

    @Override
    public void forgetPwdCompleteSuccess() {
        //成功
        ToastUtils.show(Utils.getResourceString(R.string.she_zhi_mi_ma_cheng_gong));
        reForgetPwdPage();
        finish();
    }

    @Override
    public void forgetPwdCompleteError(String str) {
        Utils.showMessage(str);
        ToastUtils.show(Utils.getResourceString(R.string.she_zhi_mi_ma_shi_ba));
        reForgetPwdPage();
        reSetPwdPage();
    }

    @Override
    public void confirmResetPwdSuccess() {
        ToastUtils.show(Utils.getResourceString(R.string.she_zhi_mi_ma_cheng_gong));
        reSetPwdPage();
        finish();
    }

    @Override
    public void confirmResetPwdError(String message) {
        failMessage(message);
        reForgetPwdPage();
        reSetPwdPage();
    }

    @Override
    public void forgetPwdNextFail(String str) {
        Utils.showMessage(str);
    }

    @Override
    public void getPhoneCountrySuccess(ArrayList<PhoneCountryNumVO.DataBean> data) {
        countryList = data;
        for (PhoneCountryNumVO.DataBean dataBean : data) {
            if (dataBean.getName_en().equals("China")) {
                currCountry = dataBean;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (type == 1) {
            UTEXApplication.setToken(null);
            UTEXApplication.setUsername(null);
        }
    }
}
