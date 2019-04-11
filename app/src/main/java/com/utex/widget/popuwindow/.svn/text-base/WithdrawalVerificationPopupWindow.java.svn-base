package com.utex.widget.popuwindow;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.utex.R;
import com.utex.utils.DialogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Demon on 2018/6/26.
 */
public class WithdrawalVerificationPopupWindow {

    Context context;

//    private PopupWindow popupWindow;

    @BindView(R.id.tv_withdrawal_verification_title)
    TextView tvWithdrawalVerificationTitle;

    @BindView(R.id.tv_withdrawal_verification_tip)
    TextView tvWithdrawalVerificationTip;

    @BindView(R.id.tv_withdrawal_email_verification_account)
    TextView tvWithdrawalEmailVerifcationAccount;

    @BindView(R.id.et_withdrawal_email_verification_code)
    EditText etWithdrawalEmailVerificationCode;

    @BindView(R.id.tv_withdrawal_verification_second)
    TextView tvWithdrawalVerificationSecond;

    @BindView(R.id.tv_withdrawal_sms_verification_account)
    TextView tvWithdrawalSmsVerificationAccount;

    @BindView(R.id.et_withdrawal_sms_verification_code)
    EditText etWithdrawalSmsVerificationCode;

    @BindView(R.id.tv_withdrawal_sms_verification_second)
    TextView tvWithdrawalSmsVerificationSecond;

    @BindView(R.id.tv_withdrawal_google_verification_account)
    TextView tvWithdrawalGoogleVerificationAccount;

    @BindView(R.id.et_withdrawal_google_verification_code)
    EditText etWithdrawalGoogleVerificationCode;

    @BindView(R.id.tv_withdrawal_complete)
    TextView tvWithdrawalComplete;

    @BindView(R.id.tv_withdrawal_cannel)
    TextView tvWithdrawalCannel;

    View popupWindowView;


    private LoginBottomListener loginBottomListener;
    private final DialogUtils dialogUtils;

    public WithdrawalVerificationPopupWindow(Context context, int parentLayoutRes) {
        this.context = context;
        dialogUtils = new DialogUtils(context, R.layout.view_withdrawal_verification);
        dialogUtils.getInstance().getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindowView = dialogUtils.getView();
        ButterKnife.bind(this, popupWindowView);
        dialogUtils.getInstance().setOnDismissListener(dialogInterface -> {
            if (loginBottomListener != null) {
                loginBottomListener.onDismiss();
            }
        });
        Activity activity = (Activity) context;


        WindowManager m = activity.getWindowManager();
        Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        Window win = dialogUtils.getInstance().getWindow();
        win.setBackgroundDrawableResource(android.R.color.transparent);
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = d.getWidth();
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.y = 0;
        win.setGravity(Gravity.BOTTOM);
        win.setAttributes(lp);

        dialogUtils.show();
    }

    /**
     * 初始化
     */
    public void initPopupWindow() {
//        popupWindowView = LayoutInflater.from(context).inflate(layoutRes, null);

//        popupWindow = new PopupWindow(popupWindowView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

//        popupWindow.setAnimationStyle(R.style.BottompSelectAnimationShow);
        // 菜单背景色。加了一点透明度
//        ColorDrawable dw = new ColorDrawable(0x6600000);
//        popupWindow.setBackgroundDrawable(dw);
//        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//
//        popupWindow.showAtLocation(LayoutInflater.from(context).inflate(parentLayoutRes, null),
//                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        // 设置背景半透明
//        backgroundAlpha(0.7f);

//        popupWindow.setOnDismissListener(new popupDismissListener());
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {

        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        if (bgAlpha == 1) {
            ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            ((Activity) context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        ((Activity) context).getWindow().setAttributes(lp);
    }

    class popupDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            if (loginBottomListener != null) {
                loginBottomListener.onDismiss();
            }
            backgroundAlpha(1f);
        }
    }

    public void dimss() {
        if (dialogUtils != null) {
            dialogUtils.dismiss();
        }
    }

    public void setLoginBottomListener(LoginBottomListener loginBottomListener) {
        this.loginBottomListener = loginBottomListener;
    }

    public interface LoginBottomListener {
        void onclick(int type);

        void onDismiss();
    }

    public TextView getTvWithdrawalVerificationTitle() {
        return tvWithdrawalVerificationTitle;
    }

    public void setTvWithdrawalVerificationTitle(TextView tvWithdrawalVerificationTitle) {
        this.tvWithdrawalVerificationTitle = tvWithdrawalVerificationTitle;
    }

    public TextView getTvWithdrawalVerificationTip() {
        return tvWithdrawalVerificationTip;
    }

    public void setTvWithdrawalVerificationTip(TextView tvWithdrawalVerificationTip) {
        this.tvWithdrawalVerificationTip = tvWithdrawalVerificationTip;
    }

    public TextView getTvWithdrawalEmailVerifcationAccount() {
        return tvWithdrawalEmailVerifcationAccount;
    }

    public void setTvWithdrawalEmailVerifcationAccount(TextView tvWithdrawalEmailVerifcationAccount) {
        this.tvWithdrawalEmailVerifcationAccount = tvWithdrawalEmailVerifcationAccount;
    }

    public EditText getEtWithdrawalEmailVerificationCode() {
        return etWithdrawalEmailVerificationCode;
    }

    public void setEtWithdrawalEmailVerificationCode(EditText etWithdrawalEmailVerificationCode) {
        this.etWithdrawalEmailVerificationCode = etWithdrawalEmailVerificationCode;
    }

    public TextView getTvWithdrawalVerificationSecond() {
        return tvWithdrawalVerificationSecond;
    }

    public void setTvWithdrawalVerificationSecond(TextView tvWithdrawalVerificationSecond) {
        this.tvWithdrawalVerificationSecond = tvWithdrawalVerificationSecond;
    }

    public TextView getTvWithdrawalSmsVerificationAccount() {
        return tvWithdrawalSmsVerificationAccount;
    }

    public void setTvWithdrawalSmsVerificationAccount(TextView tvWithdrawalSmsVerificationAccount) {
        this.tvWithdrawalSmsVerificationAccount = tvWithdrawalSmsVerificationAccount;
    }

    public EditText getEtWithdrawalSmsVerificationCode() {
        return etWithdrawalSmsVerificationCode;
    }

    public void setEtWithdrawalSmsVerificationCode(EditText etWithdrawalSmsVerificationCode) {
        this.etWithdrawalSmsVerificationCode = etWithdrawalSmsVerificationCode;
    }

    public TextView getTvWithdrawalSmsVerificationSecond() {
        return tvWithdrawalSmsVerificationSecond;
    }

    public void setTvWithdrawalSmsVerificationSecond(TextView tvWithdrawalSmsVerificationSecond) {
        this.tvWithdrawalSmsVerificationSecond = tvWithdrawalSmsVerificationSecond;
    }

    public TextView getTvWithdrawalGoogleVerificationAccount() {
        return tvWithdrawalGoogleVerificationAccount;
    }

    public void setTvWithdrawalGoogleVerificationAccount(TextView tvWithdrawalGoogleVerificationAccount) {
        this.tvWithdrawalGoogleVerificationAccount = tvWithdrawalGoogleVerificationAccount;
    }

    public EditText getEtWithdrawalGoogleVerificationCode() {
        return etWithdrawalGoogleVerificationCode;
    }

    public void setEtWithdrawalGoogleVerificationCode(EditText etWithdrawalGoogleVerificationCode) {
        this.etWithdrawalGoogleVerificationCode = etWithdrawalGoogleVerificationCode;
    }

    public TextView getTvWithdrawalComplete() {
        return tvWithdrawalComplete;
    }

    public void setTvWithdrawalComplete(TextView tvWithdrawalComplete) {
        this.tvWithdrawalComplete = tvWithdrawalComplete;
    }

    public TextView getTvWithdrawalCannel() {
        return tvWithdrawalCannel;
    }

    public void setTvWithdrawalCannel(TextView tvWithdrawalCannel) {
        this.tvWithdrawalCannel = tvWithdrawalCannel;
    }
}

