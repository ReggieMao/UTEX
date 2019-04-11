package com.utex.widget.popuwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.utex.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Demon on 2018/6/26.
 */
public class GenderSelectionPopupWindow {

    Context context;

    private PopupWindow popupWindow;

    View popupWindowView;

    private LoginBottomListener loginBottomListener;

    @BindView(R.id.tv_gender_nan)
    TextView tvGenderNan;

    @BindView(R.id.tv_gender_nv)
    TextView tvGenderNv;

    @BindView(R.id.tv_gender_cannel)
    TextView tvGenderCannel;

    public GenderSelectionPopupWindow(Context context, int parentLayoutRes) {
        this.context = context;
        initPopupWindow(R.layout.view_gender_select, parentLayoutRes);
    }

    /**
     * 初始化
     *
     * @param layoutRes
     * @param parentLayoutRes
     */
    public void initPopupWindow(int layoutRes, int parentLayoutRes) {
        popupWindowView = LayoutInflater.from(context).inflate(layoutRes, null);
        ButterKnife.bind(this, popupWindowView);

        popupWindow = new PopupWindow(popupWindowView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setAnimationStyle(R.style.BottompSelectAnimationShow);
        // 菜单背景色。加了一点透明度
        ColorDrawable dw = new ColorDrawable(0x6600000);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        // 注意：这里的 R.layout.activity_main，不是固定的。你想让这个popupwindow盖在哪个界面上面。就写哪个界面的布局。这里以主界面为例
        popupWindow.showAtLocation(LayoutInflater.from(context).inflate(parentLayoutRes, null),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        // 设置背景半透明
        backgroundAlpha(0.7f);

        popupWindow.setOnDismissListener(new popupDismissListener());

        popupWindowView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }

                return false;
            }
        });

        tvGenderCannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
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
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    public void setLoginBottomListener(LoginBottomListener loginBottomListener) {
        this.loginBottomListener = loginBottomListener;
    }

    public interface LoginBottomListener {
        void onclick(int type);

        void onDismiss();
    }

    public TextView getTvGenderNan() {
        return tvGenderNan;
    }

    public void setTvGenderNan(TextView tvGenderNan) {
        this.tvGenderNan = tvGenderNan;
    }

    public TextView getTvGenderNv() {
        return tvGenderNv;
    }

    public void setTvGenderNv(TextView tvGenderNv) {
        this.tvGenderNv = tvGenderNv;
    }
}

