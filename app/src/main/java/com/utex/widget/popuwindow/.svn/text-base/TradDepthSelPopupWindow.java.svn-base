package com.utex.widget.popuwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.utex.R;
import com.utex.mvp.trad.view.DepthSelAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Demon on 2018/6/26.
 */
public class TradDepthSelPopupWindow {

    Context context;

    private PopupWindow popupWindow;

    View popupWindowView;
    private LoginBottomListener loginBottomListener;

    @BindView(R.id.rv_trad_depth_list)
    RecyclerView rvTradDepthList;
    private DepthSelAdapter depthSelAdapter;

    public TradDepthSelPopupWindow(Context context, int parentLayoutRes, List<String> strings) {
        this.context = context;
        initPopupWindow(R.layout.view_trad_depth_sel, parentLayoutRes, strings);
    }

    /**
     * 初始化
     *
     * @param layoutRes
     * @param parentLayoutRes
     * @param strings
     */
    public void initPopupWindow(int layoutRes, int parentLayoutRes, List<String> strings) {
        popupWindowView = LayoutInflater.from(context).inflate(layoutRes, null);
        ButterKnife.bind(this, popupWindowView);

        popupWindow = new PopupWindow(popupWindowView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setAnimationStyle(R.style.BottompSelectAnimationShow);
        // 菜单背景色。加了一点透明度
        ColorDrawable dw = new ColorDrawable(0x6600000);
        popupWindow.setBackgroundDrawable(dw);

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

        depthSelAdapter = new DepthSelAdapter(strings);
        rvTradDepthList.setAdapter(depthSelAdapter);
        rvTradDepthList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        depthSelAdapter.setDepthSelAdapterViewListener(new DepthSelAdapter.DepthSelAdapterViewListener() {
            @Override
            public void onClick(String s) {
                loginBottomListener.onclick(s);
                dimss();
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
        void onclick(String s);
    }

}

