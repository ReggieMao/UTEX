package com.utex.widget.popuwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.utex.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Demon on 2018/6/26.
 * 跳出指纹认证
 */
public class FingerprintAuthenticationPopupWindow {

    @BindView(R.id.animation_view)
    LottieAnimationView animationView;

    @BindView(R.id.tv_cannel)
    TextView tvCannel;

    private final int parentLayoutRes;
    Context context;

    private PopupWindow popupWindow;

    View popupWindowView;

    private PopuwindowListener popuwindowListener;

    public FingerprintAuthenticationPopupWindow(Context context, int parentLayoutRes) {
        this.context = context;
        this.parentLayoutRes = parentLayoutRes;
        initPopupWindow(R.layout.view_fingerprint_authentication);
    }

    /**
     * 初始化
     *
     * @param layoutRes
     */
    public void initPopupWindow(int layoutRes) {
        popupWindowView = LayoutInflater.from(context).inflate(layoutRes, null);
        ButterKnife.bind(this, popupWindowView);

        popupWindow = new PopupWindow(popupWindowView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        // 菜单背景色。加了一点透明度
        ColorDrawable dw = new ColorDrawable(0x6600000);
        popupWindow.setBackgroundDrawable(dw);


        tvCannel.setFilterTouchesWhenObscured(false);

        tvCannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                if (popuwindowListener != null) {
                    popuwindowListener.onDismiss();
                }
            }
        });

        popupWindow.setOnDismissListener(new popupDismissListener());
//
//        popupWindowView.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (popupWindow != null && popupWindow.isShowing()) {
////                    popupWindow.dismiss();
//                }
//                backgroundAlpha(1f);
//                return false;
//            }
//        });
    }


    public void show() {
        try {
            // 设置背景半透明
            backgroundAlpha(0.7f);
            // 注意：这里的 R.layout.activity_main，不是固定的。你想让这个popupwindow盖在哪个界面上面。就写哪个界面的布局。这里以主界面为例
            popupWindow.setFocusable(false);
            popupWindow.setOutsideTouchable(false);
            popupWindow.showAtLocation(LayoutInflater.from(context).inflate(parentLayoutRes, null),
                    Gravity.CENTER, 0, 0);
        } catch (Exception e) {

        }
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        ((Activity) context).getWindow().setAttributes(lp);
    }

    class popupDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            backgroundAlpha(1f);

        }
    }

    public PopupWindow getPopupWindowView() {
        return popupWindow;
    }

    public void dimss() {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    public void setListener(PopuwindowListener loginBottomListener) {
        this.popuwindowListener = loginBottomListener;
    }

    public interface PopuwindowListener {

        void onDismiss();
    }
}

