package com.utex.widget.popuwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.utex.R;
import com.utex.utils.BubbleUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Demon on 2018/6/26.
 */
public class MineFilterPopupWindow {

    Context context;

    private PopupWindow popupWindow;

    View popupWindowView;

    @BindView(R.id.ll_coin_selector)
    LinearLayout llCoinSelector;

    private LoginBottomListener loginBottomListener;

    public MineFilterPopupWindow(Context context, TextView resView) {
        this.context = context;
        initPopupWindow(R.layout.view_coin_selector, resView);
    }

    /**
     * 初始化
     *
     * @param layoutRes
     * @param parentLayoutRes
     */
    public void initPopupWindow(int layoutRes, TextView parentLayoutRes) {
        popupWindowView = LayoutInflater.from(context).inflate(layoutRes, null);
        ButterKnife.bind(this, popupWindowView);

        popupWindow = new PopupWindow(popupWindowView, BubbleUtils.dp2px(145.8f), WindowManager.LayoutParams.WRAP_CONTENT, true);

//        popupWindow.setAnimationStyle(R.style.BottompSelectAnimationShow);
        // 菜单背景色。加了一点透明度
        ColorDrawable dw = new ColorDrawable(0x6600000);
        popupWindow.setBackgroundDrawable(dw);

        // 注意：这里的 R.layout.activity_main，不是固定的。你想让这个popupwindow盖在哪个界面上面。就写哪个界面的布局。这里以主界面为例
        popupWindow.showAsDropDown(parentLayoutRes);

        // 设置背景半透明
        backgroundAlpha(1f);

        popupWindow.setOnDismissListener(new popupDismissListener());

//        popupWindowView.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (popupWindow != null && popupWindow.isShowing()) {
//                    popupWindow.dismiss();
//                    popupWindow = null;
//                }
//
//                return false;
//            }
//        });
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
    }

    public LinearLayout getLlCoinSelector() {
        return llCoinSelector;
    }
}

