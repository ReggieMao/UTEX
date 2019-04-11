package com.utex.mvp.user.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.utex.R;
import com.utex.base.BaseFragment;
import com.utex.common.FiledConstants;
import com.utex.common.UMConstants;
import com.utex.core.AppComponent;
import com.utex.data.ApiServiceModule;
import com.utex.listener.ETtextChangeListener;
import com.utex.mvp.user.bean.SendCodeDTO;
import com.utex.mvp.user.bean.GtCodeTypeEnum;
import com.utex.mvp.user.bean.UserDTO;
import com.utex.mvp.user.dagger2.DaggerEmailRegisterComponent;
import com.utex.mvp.user.dagger2.EmailRegisterModule;
import com.utex.mvp.user.presenter.IEmailRegisterPresenter;
import com.utex.mvp.web.WebActivity;
import com.utex.task.SecondTask;
import com.utex.utils.SharedPreferencesUtil;
import com.utex.utils.ToastUtils;
import com.utex.utils.Utils;
import com.geetest.sdk.Bind.GT3GeetestBindListener;
import com.geetest.sdk.Bind.GT3GeetestUtilsBind;
import com.geetest.sdk.Bind.O0000O0o;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Demon on 2018/6/1.
 */
public class EmailRegisterFragment extends BaseFragment implements View.OnTouchListener, IEmailRegisterView, SecondTask.SecondListener {

    @Inject
    IEmailRegisterPresenter iEmailRegisterPresenter;

    @BindView(R.id.et_register_email)
    EditText etRegisterEmail;

    @BindView(R.id.et_register_code)
    EditText etRegisterCode;

    @BindView(R.id.tv_register_second)
    TextView tvRegisterSecond;

    @BindView(R.id.et_register_pwd)
    EditText etRegisterPwd;

    @BindView(R.id.et_register_inviter_uid)
    EditText etRegisterInviterUid;

    @BindView(R.id.tv_register_item)
    TextView tvRegisterItem;

    @BindView(R.id.tv_register)
    TextView tvRegister;

    @BindView(R.id.animation_register)
    LottieAnimationView animationRegister;

    @BindView(R.id.rl_register_parent)
    RelativeLayout rlRegisterParent;

    private boolean isPwdShow;

    /**
     * 极验 状态
     * 0 验证码
     * 1 注册
     */
    private Integer status;

    private GT3GeetestUtilsBind gt3GeetestUtils;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gt3GeetestUtils = new GT3GeetestUtilsBind(getContext());
        gt3GeetestUtils.getISonto();
        gt3GeetestUtils.setDialogTouch(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_register_email, container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Utils.setFontSpan(tvRegisterItem, new String[]{Utils.getResourceString(R.string.zhu_ce_ji_biao_shi_tong_yi),
                Utils.getResourceString(R.string.exnow_fu_wu_tiao_kuan)}, R.color.b999999_e6ffffff, R.color.f398155);

        etRegisterPwd.setOnTouchListener(this);
        SecondTask.setListener(this);

        etRegisterEmail.addTextChangedListener(new ETtextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                super.onTextChanged(charSequence, i, i1, i2);
                isEdTextEmpty();

                Drawable bottomDrawable = getResources().getDrawable(R.drawable.shape_login_et_bottom_line_bg);
                bottomDrawable.setBounds(0, 0, bottomDrawable.getMinimumWidth(), bottomDrawable.getMinimumHeight()); //设置边界

                if (TextUtils.isEmpty(etRegisterEmail.getText())) {
                    etRegisterEmail.setCompoundDrawables(null, null, null, bottomDrawable);
                } else {
                    Drawable drawable = getResources().getDrawable(R.drawable.my_icon_ycx);

                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                    etRegisterEmail.setCompoundDrawables(null, null, drawable, bottomDrawable);//画在右边
                }
            }
        });

        etRegisterCode.addTextChangedListener(new ETtextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                super.onTextChanged(charSequence, i, i1, i2);
                isEdTextEmpty();
            }
        });

        etRegisterPwd.addTextChangedListener(new ETtextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                super.onTextChanged(charSequence, i, i1, i2);
                isEdTextEmpty();
            }
        });

        etRegisterEmail.setOnTouchListener((view, motionEvent) -> {
            Drawable drawable = etRegisterEmail.getCompoundDrawables()[2];
            //如果右边没有图片，不再处理
            if (drawable == null)
                return false;
            //如果不是按下事件，不再处理
            if (motionEvent.getAction() != MotionEvent.ACTION_UP)
                return false;
            if (motionEvent.getX() > etRegisterEmail.getWidth()
                    - etRegisterEmail.getPaddingRight()
                    - drawable.getIntrinsicWidth()) {

                etRegisterEmail.setText("");
            }
            return false;
        });

        etRegisterInviterUid.setOnFocusChangeListener((view, b) -> {
            if (b) {
                autoScrollView(rlRegisterParent, etRegisterInviterUid);
            } else {
                clearAutoScrollView(rlRegisterParent);
            }
        });


    }

    /**
     * @param root 最外层的View
     * @param scrollToView 不想被遮挡的View,会移动到这个Veiw的可见位置
     */
    private int scrollToPosition = 0;

    private void autoScrollView(final View root, final View scrollToView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    private void clearAutoScrollView(final View root) {
        root.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            Rect rect = new Rect();

            //获取root在窗体的可视区域
            rlRegisterParent.getWindowVisibleDisplayFrame(rect);

            //获取root在窗体的不可视区域高度(被遮挡的高度)
            int rootInvisibleHeight = rlRegisterParent.getRootView().getHeight() - rect.bottom;

            //若不可视区域高度大于150，则键盘显示
            if (rootInvisibleHeight > 150) {

                //获取scrollToView在窗体的坐标,location[0]为x坐标，location[1]为y坐标
                int[] location = new int[2];
                etRegisterInviterUid.getLocationInWindow(location);

                //计算root滚动高度，使scrollToView在可见区域的底部
                int scrollHeight = (location[1] + etRegisterInviterUid.getHeight()) - rect.bottom;

                //注意，scrollHeight是一个相对移动距离，而scrollToPosition是一个绝对移动距离
                scrollToPosition += scrollHeight;

            } else {
                //键盘隐藏
                scrollToPosition = 0;
            }
            rlRegisterParent.scrollTo(0, scrollToPosition);

        }
    };

    private void isEdTextEmpty() {
        if (TextUtils.isEmpty(etRegisterEmail.getText()) ||
                TextUtils.isEmpty(etRegisterCode.getText()) ||
                TextUtils.isEmpty(etRegisterPwd.getText())) {
            //若有一个为空则是灰色 不可点击
            tvRegister.setBackgroundColor(Utils.getResourceColor(getContext(), R.color.bd4d6d5_33ffffff));
            tvRegister.setClickable(false);
        } else {
            tvRegister.setClickable(true);
            tvRegister.setBackgroundResource(R.drawable.login_selector);
        }
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerEmailRegisterComponent
                .builder()
                .appComponent(appComponent)
                .emailRegisterModule(new EmailRegisterModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.tv_register, R.id.tv_register_second, R.id.tv_register_item})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register_second:
                //邮箱验证码
                if (!TextUtils.isEmpty(etRegisterEmail.getText())) {
                    status = 0;
                    iEmailRegisterPresenter.getGTCode(etRegisterEmail.getText().toString());
                    gt3GeetestUtils.showLoadingDialog(getContext(), null);


                    MobclickAgent.onEvent(getContext(), UMConstants.REGISTER_SEND_CODE_BTN);
                } else {
                    registerFail("9000");
                }
                break;
            case R.id.tv_register:
                //注册，直接注册
                tvRegister.setClickable(false);
                if (TextUtils.isEmpty(etRegisterCode.getText())) {
                    registerFail("9001");
                    return;
                }

                if (!Utils.checkPwd(etRegisterPwd.getText().toString())) {
                    registerFail("1101");
                    return;
                }

                status = 1;
                iEmailRegisterPresenter.getGTCode(etRegisterEmail.getText().toString());
                tvRegister.setText("");
                animationRegister.setVisibility(View.VISIBLE);

                MobclickAgent.onEvent(getContext(), UMConstants.REGISTER_VIEW_RIGISTER_BTN);
                break;
            case R.id.tv_register_item:
                //注册条款
                String tw = SharedPreferencesUtil.getString(FiledConstants.SP_LANGUGE_ABBREVIATION, "tw");

                Intent web = new Intent(getContext(), WebActivity.class);
//                if ("zh".equals(tw)) {
//                    web.putExtra(FiledConstants.LINK_URL, ApiServiceModule.USER_REGISTER_ITEM);
//                } else {
//                    web.putExtra(FiledConstants.LINK_URL, ApiServiceModule.USER_REGISTER_ITEM + "-en" + ".html");
//                }
                web.putExtra(FiledConstants.LINK_URL, ApiServiceModule.USER_REGISTER_ITEM);

                startActivity(web);
                MobclickAgent.onEvent(getContext(), UMConstants.REGISTER_SHOW_EXNOW_PRIVACY_POLICY);
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        // et.getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
        Drawable drawable = etRegisterPwd.getCompoundDrawables()[2];
        //如果右边没有图片，不再处理
        if (drawable == null)
            return false;
        //如果不是按下事件，不再处理
        if (motionEvent.getAction() != MotionEvent.ACTION_UP)
            return false;
        if (motionEvent.getX() > etRegisterPwd.getWidth()
                - etRegisterPwd.getPaddingRight()
                - drawable.getIntrinsicWidth()) {

            Drawable line = getResources().getDrawable(R.drawable.shape_login_et_bottom_line_bg);
            line.setBounds(0, 0, line.getMinimumWidth(), line.getMinimumHeight());

            MobclickAgent.onEvent(getContext(), UMConstants.REGISTER_PASSWORD_SHOW_OR_NOT_WYW_BTN);
            if (isPwdShow) {
                //如果选中，显示密码
                etRegisterPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                isPwdShow = false;
                Drawable show = getResources().getDrawable(R.drawable.login_icon_show);
                show.setBounds(0, 0, show.getMinimumWidth(), show.getMinimumHeight());
                etRegisterPwd.setCompoundDrawables(null, null, show, line);
            } else {
                //否则隐藏密码
                etRegisterPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                isPwdShow = true;
                Drawable show = getResources().getDrawable(R.drawable.login_icon_hide);
                show.setBounds(0, 0, show.getMinimumWidth(), show.getMinimumHeight());

                etRegisterPwd.setCompoundDrawables(null, null, show, line);
            }
        }
        return false;
    }

    /**
     * 注册页面重置
     */
    private void registerPageReset() {
        animationRegister.setVisibility(View.GONE);
        tvRegister.setText(Utils.getResourceString(R.string.zhu_ce));
        tvRegister.setClickable(true);
    }

    @Override
    public void getEmailGTCodeSuccess(JSONObject jsonObject) {
        gt3GeetestUtils.gtSetApi1Json(jsonObject);
        gt3GeetestUtils.getGeetest(getContext(), null, null, null, new GT3GeetestBindListener() {
            public void gt3DialogOnError(String error) {
                Log.e("TAG", error);
                gt3GeetestUtils.gt3TestClose();
                registerPageReset();
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
                        switch (EmailRegisterFragment.this.status) {
                            case 0:
                                //邮箱验证码，倒计时手动记录
                                SendCodeDTO sendCodeDTO = new SendCodeDTO();
                                sendCodeDTO.setChallenge(res_json.getString("geetest_challenge"));
                                sendCodeDTO.setValidate(res_json.getString("geetest_validate"));
                                sendCodeDTO.setSeccode(res_json.getString("geetest_seccode"));
                                sendCodeDTO.setCode_type(GtCodeTypeEnum.REGISTER.getCodeType());
                                sendCodeDTO.setStatus(1);
                                sendCodeDTO.setUsername(etRegisterEmail.getText().toString());
                                sendCodeDTO.setCarrier(SendCodeDTO.LOGIN_TYPE_EMAIL);

                                iEmailRegisterPresenter.sendEmail(sendCodeDTO);
                                break;
                            case 1:
                                //注册
                                UserDTO registerDTO = new UserDTO();
                                registerDTO.setUsername(etRegisterEmail.getText().toString());
                                registerDTO.setPassword(Utils.md5(etRegisterPwd.getText().toString()));
                                registerDTO.setRe_password(registerDTO.getPassword());
                                registerDTO.setStatus(1);
                                registerDTO.setChallenge(res_json.getString("geetest_challenge"));
                                registerDTO.setValidate(res_json.getString("geetest_validate"));
                                registerDTO.setSeccode(res_json.getString("geetest_seccode"));

                                registerDTO.setPass_code(etRegisterCode.getText().toString());
                                if (!TextUtils.isEmpty(etRegisterInviterUid.getText())) {
                                    registerDTO.setInvitor_id(etRegisterInviterUid.getText().toString());
                                }
                                registerDTO.setUser_from("Android");
                                registerDTO.setRegister_type(UserDTO.EMAIL);
                                iEmailRegisterPresenter.register(registerDTO);
                                break;
                        }
                    } catch (Exception e) {
                        gt3GeetestUtils.gt3TestClose();
                        registerPageReset();
                    }

                } else {
                    //失败
                    gt3GeetestUtils.gt3TestClose();
                    registerPageReset();
                }
            }
        });

        O0000O0o dialog = gt3GeetestUtils.getDialog();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                registerPageReset();
            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                registerPageReset();
            }
        });
    }

    @Override
    public void sendEmailSuccess() {
        gt3GeetestUtils.gt3TestFinish();
        // 去读秒
        SecondTask.startTask(0);
    }

    @Override
    public void sendEmailError(Throwable t) {
        gt3GeetestUtils.gt3TestClose();
    }

    @Override
    public void registerSuccess() {
        ToastUtils.show(Utils.getResourceString(R.string.zhu_ce_cheng_gong));
        getActivity().finish();
        MobclickAgent.onEvent(getContext(), UMConstants.NOLOGIN_PUSH_LOGIN_PAGE_ACTION);
    }

    @Override
    public void registerFail(String message) {
        if (message == null) {
            message = Utils.getResourceString(R.string.shi_bai);
        }
        Utils.showMessage(message);
        gt3GeetestUtils.gt3Dismiss();
        registerPageReset();
    }

    @Override
    public void getGTCodeFail() {
        gt3GeetestUtils.gt3TestClose();
        registerPageReset();
    }

    @Override
    public void upDateView(int second) {
        if (tvRegisterSecond != null) {
            //读秒监听
            if (second > 0) {
                tvRegisterSecond.setText("(" + String.valueOf(second) + ")s" + Utils.getResourceString(R.string.chong_xin_fa_song));
                tvRegisterSecond.setTextColor(Utils.getResourceColor(getContext(), R.color.bcccccc_4cffffff));
                tvRegisterSecond.setClickable(false);
            } else {
                //重新发送
                tvRegisterSecond.setText(Utils.getResourceString(R.string.fa_song_yan_zheng_ma));
                tvRegisterSecond.setTextColor(Utils.getResourceColor(getContext(), R.color.f398155));
                tvRegisterSecond.setClickable(true);
            }
        }
    }

    @Override
    public void upDateForgetPwdView(int second) {

    }

    @Override
    public void upDateActivateView(int second) {

    }

    @Override
    public void upDateBindPhoneView(int second) {

    }
}