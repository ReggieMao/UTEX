package com.utex.widget.popuwindow;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.utex.R;
import com.utex.utils.DialogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Demon on 2018/6/26.
 * 快捷登录弹框
 */
public class OtherQuickLoginWindow {

    Context context;

    View popupWindowView;

    @BindView(R.id.tv_other_quinck_login_1)
    TextView tvOtherQuinckLogin1;

    @BindView(R.id.tv_other_quinck_login_2)
    TextView tvOtherQuinckLogin2;

    @BindView(R.id.tv_other_quinck_login_3)
    TextView tvOtherQuinckLogin3;

    @BindView(R.id.tv_other_quinck_login_cannel)
    TextView tvOtherQuinckLoginCannel;


    private LoginBottomListener loginBottomListener;
    private final DialogUtils dialogUtils;

    public OtherQuickLoginWindow(Context context) {
        this.context = context;
        dialogUtils = new DialogUtils(context, R.layout.view_other_quinck_login);
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

    public TextView getTvOtherQuinckLogin1() {
        return tvOtherQuinckLogin1;
    }

    public void setTvOtherQuinckLogin1(TextView tvOtherQuinckLogin1) {
        this.tvOtherQuinckLogin1 = tvOtherQuinckLogin1;
    }

    public TextView getTvOtherQuinckLogin2() {
        return tvOtherQuinckLogin2;
    }

    public void setTvOtherQuinckLogin2(TextView tvOtherQuinckLogin2) {
        this.tvOtherQuinckLogin2 = tvOtherQuinckLogin2;
    }

    public TextView getTvOtherQuinckLogin3() {
        return tvOtherQuinckLogin3;
    }

    public void setTvOtherQuinckLogin3(TextView tvOtherQuinckLogin3) {
        this.tvOtherQuinckLogin3 = tvOtherQuinckLogin3;
    }

    public TextView getTvOtherQuinckLoginCannel() {
        return tvOtherQuinckLoginCannel;
    }

    public void setTvOtherQuinckLoginCannel(TextView tvOtherQuinckLoginCannel) {
        this.tvOtherQuinckLoginCannel = tvOtherQuinckLoginCannel;
    }

    public void setLoginBottomListener(LoginBottomListener loginBottomListener) {
        this.loginBottomListener = loginBottomListener;
    }

    public DialogUtils getDialogUtils() {
        return dialogUtils;
    }

    public interface LoginBottomListener {
        void onclick(int type);

        void onDismiss();
    }

    public View getPopupWindowView() {
        return popupWindowView;
    }

    public void setPopupWindowView(View popupWindowView) {
        this.popupWindowView = popupWindowView;
    }
}

