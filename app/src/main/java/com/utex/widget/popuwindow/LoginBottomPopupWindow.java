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
public class LoginBottomPopupWindow {

    Context context;

//    private PopupWindow popupWindow;

    View popupWindowView;

    @BindView(R.id.tv_login_second_verification_title)
    TextView tvLoginSecondVerificationTitle;

    @BindView(R.id.tv_login_second_verification_tip)
    TextView tvLoginSecondVerificationTip;

    @BindView(R.id.tv_login_second_verification_second)
    TextView tvLoginSecondVerificationSecond;

    @BindView(R.id.tv_login_second_verification_complete)
    TextView tvLoginSecondVerificationComplete;

    @BindView(R.id.et_login_second_verification_code)
    EditText etLoginSecondVerificatiionCode;

    @BindView(R.id.tv_login_second_verification_cannel)
    TextView tvLoginSecondVerificatiionCannel;

    @BindView(R.id.tv_login_second_verification_account)
    TextView tvLoginSecondVerificationAccount;


    private LoginBottomListener loginBottomListener;

    private final DialogUtils dialogUtils;

    public LoginBottomPopupWindow(Context context, int parentLayoutRes) {
        this.context = context;
        dialogUtils = new DialogUtils(context, R.layout.view_login_second_verification);
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
//        // 注意：这里的 R.layout.activity_main，不是固定的。你想让这个popupwindow盖在哪个界面上面。就写哪个界面的布局。这里以主界面为例
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

    public TextView getTvLoginSecondVerificationTitle() {
        return tvLoginSecondVerificationTitle;
    }

    public TextView getTvLoginSecondVerificationTip() {
        return tvLoginSecondVerificationTip;
    }

    public TextView getTvLoginSecondVerificationSecond() {
        return tvLoginSecondVerificationSecond;
    }

    public TextView getTvLoginSecondVerificationComplete() {
        return tvLoginSecondVerificationComplete;
    }

    public EditText getEtLoginSecondVerificatiionCode() {
        return etLoginSecondVerificatiionCode;
    }

    public TextView getTvLoginSecondVerificatiionCannel() {
        return tvLoginSecondVerificatiionCannel;
    }

    public View getPopupWindowView() {
        return popupWindowView;
    }

    public void setPopupWindowView(View popupWindowView) {
        this.popupWindowView = popupWindowView;
    }

    public TextView getTvLoginSecondVerificationAccount() {
        return tvLoginSecondVerificationAccount;
    }

    public void setTvLoginSecondVerificationAccount(TextView tvLoginSecondVerificationAccount) {
        this.tvLoginSecondVerificationAccount = tvLoginSecondVerificationAccount;
    }

}

