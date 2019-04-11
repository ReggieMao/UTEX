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
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.utex.R;
import com.utex.bean.TickerDo;
import com.utex.common.Constants;
import com.utex.mvp.home.view.HomeFragment;
import com.utex.mvp.hometab.bean.JumpEvent;
import com.utex.mvp.trad.view.TradAdapter;
import com.utex.utils.SharedPreferencesUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Demon on 2018/6/26.
 */
public class TradPopupWindow {

    private final int parentLayoutRes;
    Context context;

    private PopupWindow popupWindow;

    View popupWindowView;

    @BindView(R.id.iv_asset_trad_close)
    ImageView ivAssetTradClose;

    @BindView(R.id.rv_asset_trad_list)
    RecyclerView rvassetTradList;
    private TradAdapter tradAdapter;

    public TradPopupWindow(Context context, int parentLayoutRes) {
        this.context = context;
        this.parentLayoutRes = parentLayoutRes;
        initPopupWindow(R.layout.view_asset_trad);
    }

    /**
     * 初始化
     *
     * @param layoutRes
     */
    public void initPopupWindow(int layoutRes) {
        popupWindowView = LayoutInflater.from(context).inflate(layoutRes, null);
        ButterKnife.bind(this, popupWindowView);
        tradAdapter = new TradAdapter();
        rvassetTradList.setAdapter(tradAdapter);
        rvassetTradList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        tradAdapter.setTradSelListener(new TradAdapter.TradSelListener() {
            @Override
            public void onclick(String text) {
                SharedPreferencesUtil.putString(Constants.TRAD_TICKER, text.replace("/", "_"));
                EventBus.getDefault().post(new JumpEvent(1));
                HomeFragment.isHomePage = false;
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }

            }
        });

        popupWindow = new PopupWindow(popupWindowView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

//        popupWindow.setAnimationStyle(R.style.BottompSelectAnimationShow);
        // 菜单背景色。加了一点透明度
        ColorDrawable dw = new ColorDrawable(0x6600000);
        popupWindow.setBackgroundDrawable(dw);


        popupWindow.setOnDismissListener(new popupDismissListener());

        popupWindowView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }

                return false;
            }
        });
    }


    public void show() {
        try {
            // 设置背景半透明
            backgroundAlpha(0.7f);
            // 注意：这里的 R.layout.activity_main，不是固定的。你想让这个popupwindow盖在哪个界面上面。就写哪个界面的布局。这里以主界面为例
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

    public void setData(List<TickerDo> tickerByCoinCode) {
        if (tradAdapter != null) {
            tradAdapter.setData(tickerByCoinCode);
        }
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


    public ImageView getIvAssetTradClose() {
        return ivAssetTradClose;
    }

    public void setIvAssetTradClose(ImageView ivAssetTradClose) {
        this.ivAssetTradClose = ivAssetTradClose;
    }

}

