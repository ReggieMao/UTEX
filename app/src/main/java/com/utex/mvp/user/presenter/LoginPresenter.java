package com.utex.mvp.user.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.utex.R;
import com.utex.bean.UserDO;
import com.utex.common.Constants;
import com.utex.common.UMConstants;
import com.utex.core.UTEXApplication;
import com.utex.data.ApiService;
import com.utex.db.ExTickerDao;
import com.utex.db.ExUserDao;
import com.utex.mvp.user.bean.LoginPreDTO;
import com.utex.mvp.user.bean.GTCodeVO;
import com.utex.mvp.user.bean.OptionVO;
import com.utex.mvp.user.bean.UserVO;
import com.utex.mvp.user.model.ILoginModel;
import com.utex.mvp.user.model.LoginModel;
import com.utex.mvp.user.view.EmailRegisterFragment;
import com.utex.mvp.user.view.ILoginView;
import com.utex.mvp.user.view.LoginActivity;
import com.utex.mvp.user.view.PhoneRegisterFragment;
import com.utex.utils.FragmentSwitchUtils;
import com.utex.utils.SharedPreferencesUtil;
import com.utex.utils.Utils;
import com.utex.widget.popuwindow.LoginBottomPopupWindow;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Demon on 2018/6/1.
 */
public class LoginPresenter implements ILoginPresenter {


    private ApiService apiService;

    private ILoginView iLoginView;
    private FragmentSwitchUtils fragmentSwitchUtils;

    private ILoginModel iLoginModel;
    private UserVO body;
    private TextView tvLoginSecondVerificationSecond;

    private int second = 60;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (tvLoginSecondVerificationSecond == null) {
                return;
            }

            tvLoginSecondVerificationSecond.setText("(" + String.valueOf(second) + ")s" + Utils.getResourceString(R.string.chong_xin_fa_song));
            tvLoginSecondVerificationSecond.setTextColor(Utils.getResourceColor((Context) iLoginView, R.color.b999999_e6ffffff));

            second--;
            if (second > 0) {
                tvLoginSecondVerificationSecond.setClickable(false);
                handler.sendEmptyMessageDelayed(0, 1000);
            } else {
                tvLoginSecondVerificationSecond.setClickable(true);
                tvLoginSecondVerificationSecond.setText(Utils.getResourceString(R.string.fa_song_yan_zheng_ma));
                tvLoginSecondVerificationSecond.setTextColor(Utils.getResourceColor((Context) iLoginView, R.color.f398155));
            }

        }
    };

    public LoginPresenter(ApiService apiService, LoginActivity activity) {
        this.apiService = apiService;
        this.iLoginView = activity;
        iLoginModel = new LoginModel();
    }

    @Override
    public void setFragments(Context context, int resource) {
        List<FragmentSwitchUtils.FragmentSwitchBean> fragments = new ArrayList<>();
        fragments.add(new FragmentSwitchUtils.FragmentSwitchBean(new PhoneRegisterFragment(), "login"));
        fragments.add(new FragmentSwitchUtils.FragmentSwitchBean(new EmailRegisterFragment(), "register"));

        fragmentSwitchUtils = new FragmentSwitchUtils(context, fragments, resource);
        fragmentSwitchUtils.switchFragment(0);
    }

    @Override
    public void switchPage(int page) {
        if (fragmentSwitchUtils != null) {
            fragmentSwitchUtils.switchFragment(page);
        }
    }

    @Override
    public void requestToken() {
        iLoginModel.requestToken(apiService, this);
    }

    @Override
    public void requestTokenSuccess() {
        iLoginView.requestTokenSuccess();
    }

    @Override
    public void requestTokenFail() {
        iLoginView.requestTokenFail();
    }

    @Override
    public void getUserInfo() {
        iLoginModel.getUserInfo(apiService, this);
    }

    @Override
    public void getUserInfoSuccess() {
//        ExUserDao.insertUser(body.getData());
//        UTEXApplication.setLoginUser(query);
//        UTEXApplication.setToken(query.getToken());
//        UTEXApplication.setUsername(query.getEmail());

        UserDO query = ExUserDao.query();
        iLoginView.getUserInfoSuccess();
        MobclickAgent.onProfileSignIn(query.getUuid());
    }

    @Override
    public void getUserInfoFail() {
        iLoginView.getUserInfoFail();
    }

    @Override
    public void getGTCode(String account) {
        iLoginModel.getGTCode(apiService, account, this);
    }

    @Override
    public void getGTCodeSuccess(GTCodeVO body) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("success", 1);
            jsonObject.put("challenge", body.getData().getChallenge());
            jsonObject.put("gt", body.getData().getGt());
            jsonObject.put("new_captcha", true);
            iLoginView.getGTCodeSuccess(jsonObject);
        } catch (Exception e) {
            Log.e("TAG", "cause:" + e.getMessage());
        }
    }

    @Override
    public void loginPre(LoginPreDTO sendCodeDTO) {
        iLoginModel.loginPre(apiService, sendCodeDTO, this);
    }

    @Override
    public void loginPreSuccess(UserVO body) {
        this.body = body;
        Log.e("TAG", String.valueOf(body.getData()));
        UTEXApplication.setLoginUser(body.getData());

        SharedPreferencesUtil.putString(Constants.USER_TYPE, body.getData().getUser_type());

        iLoginView.closeGt();
        //有二级认证，去判断邮箱还是谷歌，跳出下方输入框
        final LoginBottomPopupWindow loginBottomPopupWindow = new LoginBottomPopupWindow((Context) iLoginView, R.layout.activity_login);
        tvLoginSecondVerificationSecond = loginBottomPopupWindow.getTvLoginSecondVerificationSecond();
        switch (body.getData().getUser_type()) {
            case "tel":
                Log.e("TAG", "tel");
                //验证码，先自动发一次验证码
//                iLoginModel.sendLoginCode(apiService, this);
                loginBottomPopupWindow.getTvLoginSecondVerificationTip().setText(Utils.getResourceString(R.string.nin_yi_kai_qi_shou_ji_yan_zheng_qing_tian_xie_yan_zheng_ma));
                loginBottomPopupWindow.getTvLoginSecondVerificationSecond().setVisibility(View.VISIBLE);
                loginBottomPopupWindow.getEtLoginSecondVerificatiionCode().setHint(Utils.getResourceString(R.string.duan_xin_yan_zheng_ma));
                loginBottomPopupWindow.getTvLoginSecondVerificationAccount().setText(body.getData().getTel());
                break;
            case "google":
                Log.e("TAG", "google");
                //谷歌，用户自己输入不用管
                loginBottomPopupWindow.getTvLoginSecondVerificationTip().setText(Utils.getResourceString(R.string.nin_yi_kai_qi_gu_ge_yan_zheng_qing_tian_xie_yan_zheng_ma));
                loginBottomPopupWindow.getTvLoginSecondVerificationSecond().setVisibility(View.GONE);
                loginBottomPopupWindow.getEtLoginSecondVerificatiionCode().setHint(Utils.getResourceString(R.string.gu_ge_yan_zheng_ma));
                loginBottomPopupWindow.getTvLoginSecondVerificationAccount().setText(Utils.getResourceString(R.string.gu_ge_yan_zheng_ma));
                break;
            case "email":
                //邮箱 验证码
                Log.e("TAG", "email");
                loginBottomPopupWindow.getTvLoginSecondVerificationTip().setText(Utils.getResourceString(R.string.ru_guo_chang_shi_jian_wei_shou_dao_qing_chang_shi_la_ji_xiang));
                loginBottomPopupWindow.getTvLoginSecondVerificationSecond().setVisibility(View.VISIBLE);
                loginBottomPopupWindow.getEtLoginSecondVerificatiionCode().setHint(Utils.getResourceString(R.string.you_xiang_yanzheng_ma));
                loginBottomPopupWindow.getTvLoginSecondVerificationAccount().setText(body.getData().getEmail());
                break;
        }

        loginBottomPopupWindow.getTvLoginSecondVerificationComplete().setOnClickListener(view -> {
            switch (body.getData().getUser_type()) {
                case "tel":
                    //验证码，先自动发一次验证码
                    MobclickAgent.onEvent(view.getContext(), UMConstants.LOGIN_GOOGLE_CODE_DONE_BTN);
                    break;
                case "google":
                    //谷歌，用户自己输入不用管
                    MobclickAgent.onEvent(view.getContext(), UMConstants.LOGIN_PHONE_CODE_DONE_BTN);
                    break;
            }

            iLoginView.keyboardDiss();


            //去调登陆接口
            iLoginModel.login(apiService,
                    body.getData().getUsername(),
                    body.getData().getUser_type(),
                    loginBottomPopupWindow.getEtLoginSecondVerificatiionCode().getText().toString(),
                    LoginPresenter.this);
            loginBottomPopupWindow.dimss();
        });

        loginBottomPopupWindow.getTvLoginSecondVerificationSecond().setOnClickListener(view -> {
            iLoginModel.sendLoginCode(apiService, LoginPresenter.this);
            MobclickAgent.onEvent(view.getContext(), UMConstants.LOGIN_PHONE_CODE_SEND_BTN);
        });

        loginBottomPopupWindow.getTvLoginSecondVerificatiionCannel().setOnClickListener(view -> {
            iLoginView.loginPageReset();
            loginBottomPopupWindow.dimss();
        });

        loginBottomPopupWindow.setLoginBottomListener(new LoginBottomPopupWindow.LoginBottomListener() {
            @Override
            public void onclick(int type) {

            }

            @Override
            public void onDismiss() {
                UTEXApplication.clearUser();
            }
        });
    }

    @Override
    public void loginPreError(String error) {
        iLoginView.loginPreError(error);
    }

    @Override
    public void loginSuccess(String token) {
        body.setToken(token);
        body.getData().setToken(token);
        UTEXApplication.setLoginUser(body.getData());
        iLoginView.loginSuccess();
        getUserInfo();
    }

    @Override
    public void loginError(String message) {
        iLoginView.loginError(message);

    }

    @Override
    public void sendLoginCodeSuccess() {
        second = 60;
        handler.sendEmptyMessageDelayed(0, 0);
    }

    @Override
    public void sendLoginCodeError(String str) {
        iLoginView.loginError(str);
    }

    @Override
    public void getOption() {
        iLoginModel.getOption(apiService, this);

    }

    @Override
    public void getOptionSuccess(List<OptionVO.DataBean> data) {
        ExTickerDao.clearOption();
        if (data != null) {
            for (OptionVO.DataBean dataBean : data) {
                ExTickerDao.changeOption(dataBean.getCoin_market_code());
            }
        }

        iLoginView.getOptionComplete();
    }

    @Override
    public void getOptionError() {
        iLoginView.getOptionComplete();
        iLoginView.popwindowDiss();
    }
}
