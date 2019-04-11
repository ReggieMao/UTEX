package com.utex.mvp.user.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.utex.R;
import com.utex.base.BaseActivity;
import com.utex.common.FiledConstants;
import com.utex.core.AppComponent;
import com.utex.core.UTEXApplication;
import com.utex.db.ExUserDao;
import com.utex.mvp.user.bean.SendCodeDTO;
import com.utex.mvp.user.dagger2.ActivateModule;
import com.utex.mvp.user.dagger2.DaggerActivateComponent;
import com.utex.mvp.user.presenter.IActivatePresenter;
import com.utex.task.SecondTask;
import com.utex.utils.ToastUtils;
import com.utex.utils.Utils;
import com.utex.widget.CodeInputView;
import com.geetest.sdk.Bind.GT3GeetestBindListener;
import com.geetest.sdk.Bind.GT3GeetestUtilsBind;

import org.json.JSONObject;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivateActivity extends BaseActivity implements IActivateView {

    @Inject
    IActivatePresenter iActivatePresenter;

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;

    @BindView(R.id.tv_activate_send)
    TextView tvActivateSend;

    @BindView(R.id.civ_activate)
    CodeInputView civActivate;

    private String email;

    private GT3GeetestUtilsBind gt3GeetestUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate);
        ButterKnife.bind(this);

        gt3GeetestUtils = new GT3GeetestUtilsBind(this);
        gt3GeetestUtils.getISonto();
        gt3GeetestUtils.setDialogTouch(false);

        email = getIntent().getStringExtra(FiledConstants.USERNAME);

        tvToolbarTitle.setText(Utils.getResourceString(R.string.zhang_hao_ji_huo));

        SecondTask.setListener(new SecondTask.SecondListener() {
            @Override
            public void upDateView(int second) {

            }

            @Override
            public void upDateForgetPwdView(int second) {

            }

            @Override
            public void upDateActivateView(int second) {
                if (tvActivateSend != null) {
                    //读秒监听
                    if (second > 0) {
                        tvActivateSend.setText("(" + String.valueOf(second) + ")s" + Utils.getResourceString(R.string.chong_xin_fa_song));
                        tvActivateSend.setTextColor(Utils.getResourceColor(ActivateActivity.this, R.color.bcccccc_4cffffff));
                        tvActivateSend.setClickable(false);
                    } else {
                        //重新发送
                        tvActivateSend.setText(Utils.getResourceString(R.string.fa_song_yan_zheng_ma));
                        tvActivateSend.setTextColor(Utils.getResourceColor(ActivateActivity.this, R.color.f398155));
                        tvActivateSend.setClickable(true);
                    }
                }
            }

            @Override
            public void upDateBindPhoneView(int second) {

            }
        });

        civActivate.setCodeInputViewListener(new CodeInputView.CodeInputViewListener() {
            @Override
            public void activate(String code) {
                iActivatePresenter.activate(email, code);
            }
        });
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerActivateComponent
                .builder()
                .appComponent(appComponent)
                .activateModule(new ActivateModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.iv_toolbar_left, R.id.tv_activate_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_left:
                finish();
                break;
            case R.id.tv_activate_send:
                //发送验证码
                iActivatePresenter.getGTCode(email);
                break;
        }
    }

    @Override
    public void getGTCodeSuccess(JSONObject jsonObject) {
        gt3GeetestUtils.gtSetApi1Json(jsonObject);
        gt3GeetestUtils.getGeetest(this, null, null, null, new GT3GeetestBindListener() {
            public void gt3DialogOnError(String error) {
                Log.e("TAG", error);
                gt3GeetestUtils.gt3TestClose();
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
                    gt3GeetestUtils.gt3TestFinish();
                    try {
                        JSONObject res_json = new JSONObject(json);
                        //邮箱验证码，倒计时手动记录
                        SendCodeDTO sendCodeDTO = new SendCodeDTO();
                        sendCodeDTO.setChallenge(res_json.getString("geetest_challenge"));
                        sendCodeDTO.setValidate(res_json.getString("geetest_validate"));
                        sendCodeDTO.setSeccode(res_json.getString("geetest_seccode"));
//                        sendCodeDTO.setPassword(Utils.md5(etLoginPwd.getText().toString()));
                        sendCodeDTO.setStatus(1);
                        sendCodeDTO.setReturn_to("");
                        sendCodeDTO.setUsername(email);
                        iActivatePresenter.sendEmail(sendCodeDTO);
                    } catch (Exception e) {
                        gt3GeetestUtils.gt3TestClose();
                    }
                } else {
                    //失败
                    gt3GeetestUtils.gt3TestClose();
                }
            }
        });
    }

    @Override
    public void sendEmailSuccess() {
        gt3GeetestUtils.gt3TestFinish();
        SecondTask.startTask(2);
    }

    @Override
    public void activateSuccess() {
        //请求用户信息,并finish
        UTEXApplication.clearUser();
        ExUserDao.clearUser();
//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);
        finish();
    }

    @Override
    public void iActivateFail() {
        civActivate.clearData();
        ToastUtils.show(Utils.getResourceString(R.string.yan_zheng_ma_cuo_wu));
    }
}
