package com.utex.mvp.user.view;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.utex.R;
import com.utex.base.BaseActivity;
import com.utex.bean.UserDO;
import com.utex.common.Constants;
import com.utex.common.FiledConstants;
import com.utex.common.UMConstants;
import com.utex.core.AppComponent;
import com.utex.db.ExUserDao;
import com.utex.listener.ETtextChangeListener;
import com.utex.mvp.user.bean.LoginPreDTO;
import com.utex.mvp.user.bean.SendCodeDTO;
import com.utex.mvp.user.bean.UserVO;
import com.utex.mvp.user.dagger2.DaggerLoginComponent;
import com.utex.mvp.user.dagger2.LoginModule;
import com.utex.mvp.user.presenter.ILoginPresenter;
import com.utex.utils.BubbleUtils;
import com.utex.utils.SecurityIdentificationUtils;
import com.utex.utils.SharedPreferencesUtil;
import com.utex.utils.ToastUtils;
import com.utex.utils.Utils;
import com.utex.widget.popuwindow.LoadingPopupWindow;
import com.geetest.sdk.Bind.GT3GeetestBindListener;
import com.geetest.sdk.Bind.GT3GeetestUtilsBind;
import com.geetest.sdk.Bind.O0000O0o;
import com.github.ihsg.patternlocker.UTEXHitCellView;
import com.github.ihsg.patternlocker.UTEXLockerLinkedLineView;
import com.github.ihsg.patternlocker.UTEXNormalCellView;
import com.github.ihsg.patternlocker.OnPatternChangeListener;
import com.github.ihsg.patternlocker.PatternLockerView;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements ILoginView, RadioGroup.OnCheckedChangeListener, View.OnTouchListener {

    @Inject
    ILoginPresenter iLoginPresenter;

    @BindView(R.id.pattern_lock_view)
    PatternLockerView patternLockView;

    @BindView(R.id.tv_user_email)
    TextView tvUserEmail;

    @BindView(R.id.ll_user_zhiwen_parent)
    LinearLayout llUserZhiwenParent;

    @BindView(R.id.rl_user_yz_parent)
    RelativeLayout rlUserYzParent;

    @BindView(R.id.ll_user_parent)
    RelativeLayout llUserParent;

    @BindView(R.id.et_login_email)
    EditText etLoginEmail;

    @BindView(R.id.et_login_pwd)
    EditText etLoginPwd;

    @BindView(R.id.tv_login)
    TextView tvLogin;

    @BindView(R.id.animation_login)
    LottieAnimationView animationLogin;

    @BindView(R.id.tv_user_go_register)
    TextView tvUserGoRegister;

    @BindView(R.id.img_loading)
    ImageView mImgLoading;

    private LoadingPopupWindow loadingPopupWindow;

    private boolean isPwdShow;
    private GT3GeetestUtilsBind gt3GeetestUtils;
    private boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        gt3GeetestUtils = new GT3GeetestUtilsBind(this);
        gt3GeetestUtils.getISonto();
        gt3GeetestUtils.setDialogTouch(false);

        loadingPopupWindow = new LoadingPopupWindow(this, R.layout.activity_login);
        setListener();
        UserDO query = ExUserDao.query();
        int type = SharedPreferencesUtil.getInteger(FiledConstants.HAS_SECURITY_IDENTIFICATION, 0);

        tvUserGoRegister.setText(Utils.getResourceString(R.string.mei_you_dui_shou_pan));
        Utils.setFontSpan(tvUserGoRegister, new String[]{Utils.getResourceString(R.string.mei_you_zhang_hao),
                        Utils.getResourceString(R.string.zhu_ce)}, R.color.b999999_66ffffff,
                R.color.f398155);

        int intExtra = getIntent().getIntExtra(FiledConstants.TYPE, 0);
        if (intExtra == 1) {
            ButterKnife.bind(this);
//            iLoginPresenter.setFragments(this, R.id.fl_home_body_parent);
//            rnUserTab.setOnCheckedChangeListener(this);
            rlUserYzParent.setVisibility(View.INVISIBLE);
            llUserParent.setVisibility(View.VISIBLE);
        } else {
            if (query != null && type > 0) {
                tvUserGoRegister.setVisibility(View.INVISIBLE);
                llUserParent.setVisibility(View.GONE);
                rlUserYzParent.setVisibility(View.VISIBLE);

                if (TextUtils.isEmpty(query.getEmail())) {
                    tvUserEmail.setText(query.getTel());
                } else {
                    tvUserEmail.setText(query.getEmail());
                }

                //有用户，也设置了指纹或者图形解锁登录
                switch (type) {
                    case 1:
                        //图形
                        llUserZhiwenParent.setVisibility(View.GONE);
                        patternLockView.setVisibility(View.VISIBLE);
                        MobclickAgent.onEvent(this, UMConstants.NOLOGIN_PUSH_GRAPHICS_VALIDATION_PAGE_ACTION);
                        break;
                    case 2:
                        //指纹
                        llUserZhiwenParent.setVisibility(View.VISIBLE);
                        patternLockView.setVisibility(View.GONE);
                        MobclickAgent.onEvent(this, UMConstants.NOLOGIN_PUSH_FINGER_VALIDATION_PAGE_ACTION);
                        new Handler().postDelayed(() -> {
                            SecurityIdentificationUtils.fingerprintShow(LoginActivity.this, R.layout.activity_login);
                            SecurityIdentificationUtils.setSecurityIdentificationListener(new SecurityIdentificationUtils.SecurityIdentificationListener() {
                                @Override
                                public void success() {
                                    //成功
                                    loadingPopupWindow.show();
                                    Utils.loadGif(LoginActivity.this, mImgLoading);
                                    iLoginPresenter.requestToken();
                                }

                                @Override
                                public void fail() {
                                    ToastUtils.show(Utils.getResourceString(R.string.zhi_wen_cuo_wu));
                                }
                            });
                        }, 800);
                        break;
                }
            } else {
                rlUserYzParent.setVisibility(View.INVISIBLE);
//                iLoginPresenter.setFragments(this, R.id.fl_home_body_parent);
//                rnUserTab.setOnCheckedChangeListener(this);
                llUserParent.setVisibility(View.VISIBLE);
            }
        }

        UTEXNormalCellView UTEXNormalCellView = new UTEXNormalCellView();
        UTEXNormalCellView.setNormalColor(Utils.getResourceColor(LoginActivity.this, R.color.b66398155));
        patternLockView.setNormalCellView(UTEXNormalCellView);

        UTEXLockerLinkedLineView UTEXLockerLinkedLineView = new UTEXLockerLinkedLineView();
        UTEXLockerLinkedLineView.setNormalColor(Utils.getResourceColor(LoginActivity.this, R.color.b19398155));
        UTEXLockerLinkedLineView.setLineWidth(BubbleUtils.dp2px(5));
        UTEXLockerLinkedLineView.setErrorColor(Utils.getResourceColor(LoginActivity.this, R.color.b19ee6a5e));
        patternLockView.setLinkedLineView(UTEXLockerLinkedLineView);

        UTEXHitCellView UTEXHitCellView = new UTEXHitCellView();
        UTEXHitCellView.setLineWidth(BubbleUtils.dp2px(2));
        UTEXHitCellView.setHitColor(Utils.getResourceColor(LoginActivity.this, R.color.f398155));
        UTEXHitCellView.setErrorColor(Utils.getResourceColor(LoginActivity.this, R.color.fee6a5e));
        patternLockView.setHitCellView(UTEXHitCellView);

        patternLockView.setOnPatternChangedListener(new OnPatternChangeListener() {

            /**
             * 开始绘制图案时（即手指按下触碰到绘画区域时）会调用该方法
             *
             * @param view
             */
            @Override
            public void onStart(PatternLockerView view) {

            }

            /**
             * 图案绘制改变时（即手指在绘画区域移动时）会调用该方法，请注意只有 @param hitList改变了才会触发此方法
             *
             * @param view
             * @param hitList
             */
            @Override
            public void onChange(PatternLockerView view, List<Integer> hitList) {

            }

            /**
             * 图案绘制完成时（即手指抬起离开绘画区域时）会调用该方法
             *
             * @param view
             * @param hitList
             */
            @Override
            public void onComplete(PatternLockerView view, List<Integer> hitList) {
                String str = "";
                for (Integer s : hitList) {
                    str += s + ",";
                }
                String string = SharedPreferencesUtil.getString(FiledConstants.IMAGE_UNLOCKING, "");
                if (!str.equals(string)) {
                    patternLockView.updateStatus(true);
                } else {
                    //验证通过
                    loadingPopupWindow.show();
                    Utils.loadGif(LoginActivity.this, mImgLoading);
                    iLoginPresenter.requestToken();
                }
            }

            /**
             * 已绘制的图案被清除时会调用该方法
             *
             * @param view
             */
            @Override
            public void onClear(PatternLockerView view) {

            }
        });
    }

    /**
     * 设置监听
     */
    private void setListener() {
        etLoginPwd.setOnTouchListener(this);

        etLoginEmail.addTextChangedListener(new ETtextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                super.onTextChanged(charSequence, i, i1, i2);
                isEdTextEmpty();

                Drawable bottomDrawable = getResources().getDrawable(R.drawable.shape_login_et_bottom_line_bg);
                bottomDrawable.setBounds(0, 0, bottomDrawable.getMinimumWidth(), bottomDrawable.getMinimumHeight()); //设置边界

                if (TextUtils.isEmpty(etLoginEmail.getText())) {
                    etLoginEmail.setCompoundDrawables(null, null, null, bottomDrawable);
                } else {
                    Drawable drawable = getResources().getDrawable(R.drawable.my_icon_ycx);

                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                    etLoginEmail.setCompoundDrawables(null, null, drawable, bottomDrawable);//画在右边
                }
            }

        });

        etLoginPwd.addTextChangedListener(new ETtextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                super.onTextChanged(charSequence, i, i1, i2);
                isEdTextEmpty();
            }
        });

        etLoginEmail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Drawable drawable = etLoginEmail.getCompoundDrawables()[2];
                //如果右边没有图片，不再处理
                if (drawable == null)
                    return false;
                //如果不是按下事件，不再处理
                if (motionEvent.getAction() != MotionEvent.ACTION_UP)
                    return false;
                if (motionEvent.getX() > etLoginEmail.getWidth()
                        - etLoginEmail.getPaddingRight()
                        - drawable.getIntrinsicWidth()) {

                    etLoginEmail.setText("");
                }
                return false;
            }
        });


        UserDO query = ExUserDao.query();
        if (query != null) {
            etLoginEmail.setText(query.getUsername());
        }
    }

    /**
     * 输入框为空 则是灰色 不可点击
     * <p>
     * 不为空则是有颜色 可点击
     */
    private void isEdTextEmpty() {
        if (TextUtils.isEmpty(etLoginEmail.getText()) ||
                TextUtils.isEmpty(etLoginPwd.getText())) {
            //若有一个为空则是灰色 不可点击
            tvLogin.setBackgroundColor(Utils.getResourceColor(this, R.color.bd4d6d5_33ffffff));
            tvLogin.setClickable(false);
        } else {
            tvLogin.setClickable(true);
            tvLogin.setBackgroundResource(R.drawable.login_selector);
        }

    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerLoginComponent
                .builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.iv_user_back, R.id.tv_user_switch_account, R.id.ll_user_zhiwen_parent,
            R.id.tv_user_go_register, R.id.tv_login_forget_pwd, R.id.tv_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                //登录,极验
                if (!TextUtils.isEmpty(etLoginEmail.getText())) {
                    iLoginPresenter.getGTCode(etLoginEmail.getText().toString());
                    gt3GeetestUtils.showLoadingDialog(this, null);
                    animationLogin.setVisibility(View.VISIBLE);
                    tvLogin.setText("");
                    tvLogin.setClickable(false);

                    MobclickAgent.onEvent(this, UMConstants.LOGIN_PAGE_LOGIN_BTN);
                }
                break;
            case R.id.tv_login_forget_pwd:
                //忘记密码
                Intent forget = new Intent(this, SetPwdActivity.class);
                forget.putExtra(FiledConstants.TYPE, 1);
                startActivity(forget);

                MobclickAgent.onEvent(this, UMConstants.LOGIN_PAGE_LOST_PASSWORD_BTN);
                break;
            case R.id.tv_user_go_register:
                //去注册界面
                Intent register = new Intent(this, RegisterActivity.class);
                startActivity(register);
                break;
            case R.id.iv_user_back:
                finish();
                break;
            case R.id.tv_user_switch_account:
                //切换账号
                tvUserGoRegister.setVisibility(View.VISIBLE);
                llUserParent.setVisibility(View.VISIBLE);
                rlUserYzParent.setVisibility(View.GONE);
                break;
            case R.id.ll_user_zhiwen_parent:
                //用户指纹，跳出指纹验证
                SecurityIdentificationUtils.fingerprintShow(this, R.layout.activity_login);
                SecurityIdentificationUtils.setSecurityIdentificationListener(new SecurityIdentificationUtils.SecurityIdentificationListener() {
                    @Override
                    public void success() {
                        //成功
                        loadingPopupWindow.show();
                        Utils.loadGif(LoginActivity.this, mImgLoading);
                        iLoginPresenter.requestToken();
                    }

                    @Override
                    public void fail() {
                        ToastUtils.show(Utils.getResourceString(R.string.zhi_wen_cuo_wu));
                    }
                });
                break;
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            SecurityIdentificationUtils.clear();
        }

        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
//        int page = 0;
//        switch (i) {
//            case R.id.rb_user_login:
//                page = 0;
//                MobclickAgent.onEvent(this, UMConstants.LOGIN_PAGE_EMIL_LOGIN_BTN);
//                break;
//            case R.id.rb_user_register:
//                page = 1;
//                MobclickAgent.onEvent(this, UMConstants.LOGIN_PAGE_REGISTER_BTN);
//                break;
//        }
//        iLoginPresenter.switchPage(page);
    }

    @Override
    public void requestTokenSuccess() {
        iLoginPresenter.getUserInfo();
    }

    @Override
    public void requestTokenFail() {
        loadingPopupWindow.dimss();
        mImgLoading.setVisibility(View.GONE);
        ExUserDao.clearUser();
        ToastUtils.show(Utils.getResourceString(R.string.deng_lu_guo_qi));
        setResult(200);
        finish();
    }

    @Override
    public void getUserInfoSuccess() {
        iLoginPresenter.getOption();
    }

    @Override
    public void getUserInfoFail() {
        loadingPopupWindow.dimss();
        mImgLoading.setVisibility(View.GONE);
        setResult(200);
        finish();
    }

    @Override
    public void getGTCodeSuccess(JSONObject jsonObject) {
        gt3GeetestUtils.gtSetApi1Json(jsonObject);
        gt3GeetestUtils.getGeetest(this, null, null, null, new GT3GeetestBindListener() {
            public void gt3DialogOnError(String error) {
                Log.e("TAG", error);
                gt3GeetestUtils.gt3TestClose();
                loginPageReset();
            }

            @Override
            public void gt3DialogReady() {
                super.gt3DialogReady();
                Log.e("TAG", "gt3DialogReady");
            }

            @Override
            public boolean gt3SetIsCustom() {
                Log.e("TAG", "gt3SetIsCustom");
                return true;
            }

            @Override
            public void gt3GetDialogResult(boolean status, String json) {
                super.gt3GetDialogResult(status, json);
                if (status) {
//                    gt3GeetestUtils.gt3TestFinish();
                    try {
                        JSONObject res_json = new JSONObject(json);

                        //登录第一步封装
                        LoginPreDTO loginPreDTO = new LoginPreDTO();
                        loginPreDTO.setUsername(etLoginEmail.getText().toString());
                        loginPreDTO.setPassword(Utils.md5(etLoginPwd.getText().toString()));
                        loginPreDTO.setChallenge(res_json.getString("geetest_challenge"));
                        loginPreDTO.setValidate(res_json.getString("geetest_validate"));
                        loginPreDTO.setSeccode(res_json.getString("geetest_seccode"));
                        loginPreDTO.setStatus(1);
                        loginPreDTO.setReturn_to("");
                        loginPreDTO.setLogin_from("app");

                        if (etLoginEmail.getText().toString().contains("@")) {
                            loginPreDTO.setLogin_type(SendCodeDTO.LOGIN_TYPE_EMAIL);
                        } else {
                            loginPreDTO.setLogin_type(SendCodeDTO.LOGIN_TYPE_TEL);
                        }

                        //进行登录
                        iLoginPresenter.loginPre(loginPreDTO);
                    } catch (Exception e) {
                        gt3GeetestUtils.gt3TestClose();
                        loginPageReset();
                    }
                } else {
                    //失败
                    gt3GeetestUtils.gt3TestClose();
                    loginPageReset();
                }
            }
        });
        O0000O0o dialog = gt3GeetestUtils.getDialog();
        dialog.setOnDismissListener(dialogInterface -> loginPageReset());
        dialog.setOnCancelListener(dialogInterface -> loginPageReset());
    }

    @Override
    public void loginPreError(String error) {
        if ("1004".equals(error)) {
            Intent intent = new Intent(this, ActivateActivity.class);
            intent.putExtra(FiledConstants.USERNAME, etLoginEmail.getText().toString());
            startActivity(intent);
        } else {
            Utils.showMessage(error);
        }
        gt3GeetestUtils.gt3TestClose();
    }

    @Override
    public void loginPreSuccess(UserVO body) {
        gt3GeetestUtils.gt3TestFinish();
        loadingPopupWindow.show();
        Utils.loadGif(LoginActivity.this, mImgLoading);
        //获取用户信息
        iLoginPresenter.getUserInfo();
    }

    @Override
    public void closeGt() {
        gt3GeetestUtils.gt3TestFinish();
    }

    @Override
    public void keyboardDiss() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void loginSuccess() {
        loadingPopupWindow.show();
        Utils.loadGif(LoginActivity.this, mImgLoading);
        isLogin = true;
    }

    @Override
    public void loginError(String message) {
        loadingPopupWindow.dimss();
        mImgLoading.setVisibility(View.GONE);
        loginPageReset();
        Utils.showMessage(message);
    }

    /**
     * 页面重置
     */
    @Override
    public void loginPageReset() {
        animationLogin.setVisibility(View.GONE);
        tvLogin.setText(Utils.getResourceString(R.string.deng_lu));
        tvLogin.setClickable(true);
    }

    @Override
    public void getOptionComplete() {
        new Handler()
                .postDelayed(() -> {
                    if (isLogin) {
                        SharedPreferencesUtil.putInteger(FiledConstants.HAS_SECURITY_IDENTIFICATION, 0);
                        SharedPreferencesUtil.putString(FiledConstants.IMAGE_UNLOCKING, "");
                    }

                    loadingPopupWindow.dimss();
                    mImgLoading.setVisibility(View.GONE);
                    //判断是否是未设置快捷登录
                    int has_security_identification = SharedPreferencesUtil.getInteger(FiledConstants.HAS_SECURITY_IDENTIFICATION, 0);
                    int integer = SharedPreferencesUtil.getInteger(Constants.FOREVER_NOT_SHOW, 0);
                    if (has_security_identification == 0 && integer == 0) {
                        //没有解锁
                        Intent quickLogin = new Intent(this, QuickLoginActivity.class);
                        startActivity(quickLogin);
                    }
                    setResult(200);
                    finish();
                }, 300);
    }

    @Override
    public void popwindowDiss() {
        loadingPopupWindow.dimss();
        mImgLoading.setVisibility(View.GONE);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
//        et.getCompoundDrawables() 得到一个长度为4的数组，分别表示左右上下四张图片
        Drawable drawable = etLoginPwd.getCompoundDrawables()[2];
        //如果右边没有图片，不再处理
        if (drawable == null)
            return false;
        //如果不是按下事件，不再处理
        if (motionEvent.getAction() != MotionEvent.ACTION_UP)
            return false;
        if (motionEvent.getX() > etLoginPwd.getWidth()
                - etLoginPwd.getPaddingRight()
                - drawable.getIntrinsicWidth()) {

            Drawable line = getResources().getDrawable(R.drawable.shape_login_et_bottom_line_bg);
            line.setBounds(0, 0, line.getMinimumWidth(), line.getMinimumHeight());
            if (isPwdShow) {
                //如果选中，显示密码
                etLoginPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                isPwdShow = false;
                Drawable show = getResources().getDrawable(R.drawable.login_icon_show);
                show.setBounds(0, 0, show.getMinimumWidth(), show.getMinimumHeight());
                etLoginPwd.setCompoundDrawables(null, null, show, line);
            } else {
                //否则隐藏密码
                etLoginPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                isPwdShow = true;
                Drawable show = getResources().getDrawable(R.drawable.login_icon_hide);
                show.setBounds(0, 0, show.getMinimumWidth(), show.getMinimumHeight());

                etLoginPwd.setCompoundDrawables(null, null, show, line);
            }
        }
        return false;
    }
}
