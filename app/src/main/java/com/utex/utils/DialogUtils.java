package com.utex.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.utex.R;

/**
 * Created by Demon on 2017/5/11.
 * 用来自定义Dialog
 */

public class DialogUtils {

    private Context mContext;

    public interface OnResultListener {
        public void onOK();
    }

    private AlertDialog dialog;
    private View view;

    public DialogUtils(Context context, int resoureId) {
        mContext = context;

        initDialog(resoureId);
    }

    public DialogUtils(Context context, int dialog_activity, int dialog) {
        mContext = context;

        initDialog(dialog_activity, dialog);
    }

    private void initDialog(int resoureId, int dialogstyle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, dialogstyle);
        view = LayoutInflater.from(mContext).inflate(resoureId, null);
        builder.setView(view);
        dialog = builder.create();
    }

    private void initDialog(int resoureId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        view = LayoutInflater.from(mContext).inflate(resoureId, null);
        builder.setView(view);

        dialog = builder.create();
    }


    public View getView() {
        return view;
    }

    public void show() {
        if (dialog != null) {
            dialog.show();
        }
    }

    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public AlertDialog getInstance() {
        return dialog;
    }

    /**
     * 版本更新提示
     */
    public static void updateNewVersionDialog(Context context, String versionSr, String sizeSr, String description, final OnResultListener onResListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_update_app, null);
        final PopupWindow mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(context.getResources().getDrawable(android.R.color.transparent));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.update();
        mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        LinearLayout layout = view.findViewById(R.id.layout);
        LinearLayout layout1 = view.findViewById(R.id.layout1);
        ImageView close = view.findViewById(R.id.iv_update_app_close);
        TextView down = view.findViewById(R.id.tv_app_down);
        TextView version = view.findViewById(R.id.tv_app_version);
        TextView size = view.findViewById(R.id.tv_app_size);
        TextView update = view.findViewById(R.id.tv_app_description);

        version.setText(context.getString(R.string.new_version) + "V" + versionSr);
        size.setText(context.getString(R.string.new_version1) + sizeSr + "M");
        update.setText(context.getString(R.string.new_version2) + description.replaceAll(";", "\n"));

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "click sub layout");
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResListener.onOK();
                mPopupWindow.dismiss();
            }
        });
    }

    /**
     * 买入提示
     */
    public static void buyDialog(Context context, int type, String price, String content, final OnResultListener onResListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_buy, null);
        final PopupWindow mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(context.getResources().getDrawable(android.R.color.transparent));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.update();
        mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        TextView title = view.findViewById(R.id.tv_title);
        TextView cancel = view.findViewById(R.id.tv_cancel);
        TextView sure = view.findViewById(R.id.tv_sure);

        if (type == 0)
            title.setText(content);
        else
            title.setText(content.replace("{}", price));
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onResListener.onOK();
                mPopupWindow.dismiss();
            }
        });
    }

}
