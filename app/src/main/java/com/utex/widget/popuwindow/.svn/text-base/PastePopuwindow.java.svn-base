package com.utex.widget.popuwindow;

import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.utex.R;
import com.utex.utils.BubbleUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Demon on 2018/8/7.
 */
public class PastePopuwindow {

    Context context;

    private PopupWindow popupWindow;

    View popupWindowView;

    @BindView(R.id.rl_paste)
    RelativeLayout rlPaste;

    private PasteListener pasteListener;

    public PastePopuwindow(Context context, View parentLayoutRes) {
        this.context = context;
        initPopupWindow(R.layout.view_paste, parentLayoutRes);
    }

    /**
     * 初始化
     *
     * @param layoutRes
     * @param parentLayoutRes
     */
    public void initPopupWindow(int layoutRes, View parentLayoutRes) {
        popupWindowView = LayoutInflater.from(context).inflate(layoutRes, null);
        ButterKnife.bind(this, popupWindowView);

        popupWindow = new PopupWindow(popupWindowView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

//        popupWindow.setAnimationStyle(R.style.BottompSelectAnimationShow);
        // 菜单背景色。加了一点透明度
//        ColorDrawable dw = new ColorDrawable(0x6600000);
//        popupWindow.setBackgroundDrawable(dw);

        // 注意：这里的 R.layout.activity_main，不是固定的。你想让这个popupwindow盖在哪个界面上面。就写哪个界面的布局。这里以主界面为例
//        popupWindow.showAtLocation(LayoutInflater.from(context).inflate(parentLayoutRes, null),
//                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        popupWindow.showAsDropDown(parentLayoutRes, 0, BubbleUtils.dp2px(-parentLayoutRes.getHeight() / 2 - 20));
//        int[] location = new int[2];
//        parentLayoutRes.getLocationOnScreen(location);
//        popupWindow.showAtLocation(parentLayoutRes, Gravity.TOP, (location[0] + parentLayoutRes.getWidth() / 2) - popupWindowView.getMeasuredWidth() / 2, location[1] - popupWindowView.getMeasuredHeight());

        // 设置背景半透明
//        backgroundAlpha(0.7f);

        rlPaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard =
                        (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                CharSequence text = clipboard.getText();

                if (pasteListener != null && !TextUtils.isEmpty(text)) {
                    pasteListener.onclick(String.valueOf(text));
                }

                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }


            }
        });

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
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
//        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
//        lp.alpha = bgAlpha; // 0.0-1.0
//        ((Activity) context).getWindow().setAttributes(lp);
    }

    class popupDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
//            backgroundAlpha(1f);
        }
    }

    public void dimss() {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    public void setPasteListener(PasteListener pasteListener) {
        this.pasteListener = pasteListener;
    }

    public interface PasteListener {
        void onclick(String str);
    }
}
