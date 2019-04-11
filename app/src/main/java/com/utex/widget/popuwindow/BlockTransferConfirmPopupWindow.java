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
import com.utex.utils.BubbleUtils;
import com.utex.utils.DialogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Demon on 2018/6/26.
 */
public class BlockTransferConfirmPopupWindow {

    Context context;

    View popupWindowView;

    private LoginBottomListener loginBottomListener;

    private final DialogUtils dialogUtils;

    @BindView(R.id.tv_block_transfer_content)
    TextView tvBlockTransferContent;

    public BlockTransferConfirmPopupWindow(Context context) {
        this.context = context;
        dialogUtils = new DialogUtils(context, R.layout.view_block_transfer);
        dialogUtils.getInstance().getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindowView = dialogUtils.getView();
        ButterKnife.bind(this, popupWindowView);
        dialogUtils.getInstance().setOnDismissListener(dialogInterface -> {
        });
        Activity activity = (Activity) context;

        WindowManager m = activity.getWindowManager();
        Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        Window win = dialogUtils.getInstance().getWindow();
        win.setBackgroundDrawableResource(android.R.color.transparent);
        win.getDecorView().setPadding(0, 0, 0, 0);
        win.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogUtils.getInstance().getWindow().getAttributes();
        lp.width = (int) (d.getWidth() - BubbleUtils.dp2px(60)); //设置宽度
        dialogUtils.getInstance().getWindow().setAttributes(lp);

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
            backgroundAlpha(1f);
        }
    }

    public void dimss() {
        if (dialogUtils != null) {
            dialogUtils.dismiss();
        }
    }

    public void show() {
        if (dialogUtils != null) {
            dialogUtils.show();
        }
    }

    public TextView getTvBlockTransferContent() {
        return tvBlockTransferContent;
    }

    public void setLoginBottomListener(LoginBottomListener loginBottomListener) {
        this.loginBottomListener = loginBottomListener;
    }

    public interface LoginBottomListener {
        void confirmTransfer();
    }

    @OnClick({R.id.iv_block_transfer_close, R.id.tv_block_transfer_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_block_transfer_ok:
                if (loginBottomListener != null) {
                    loginBottomListener.confirmTransfer();
                }
                break;
            case R.id.iv_block_transfer_close:
                if (dialogUtils != null) {
                    dialogUtils.dismiss();
                }
                break;
        }
    }
}

