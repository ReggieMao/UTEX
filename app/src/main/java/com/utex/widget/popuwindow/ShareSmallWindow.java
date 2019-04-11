package com.utex.widget.popuwindow;

import android.app.Activity;
import android.content.Context;
import android.view.View;
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
public class ShareSmallWindow {

    Context context;

    View popupWindowView;

    @BindView(R.id.ll_share_small_parent)
    TextView tvOtherQuinckLogin1;

    @BindView(R.id.iv_share_small_icon)
    TextView tvOtherQuinckLogin2;

    private LoginBottomListener loginBottomListener;
    private final DialogUtils dialogUtils;

    public ShareSmallWindow(Context context) {
        this.context = context;
        dialogUtils = new DialogUtils(context, R.layout.view_share_small_window);
        dialogUtils.getInstance().getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindowView = dialogUtils.getView();
        ButterKnife.bind(this, popupWindowView);
        dialogUtils.getInstance().setOnDismissListener(dialogInterface -> {
            if (loginBottomListener != null) {
                loginBottomListener.onDismiss();
            }
        });

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

