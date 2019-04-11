package com.utex.mvp.mine.view;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.utex.R;
import com.utex.common.Constants;
import com.utex.common.FiledConstants;
import com.utex.core.UTEXApplication;
import com.utex.utils.SharedPreferencesUtil;
import com.utex.utils.Utils;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Demon on 2018/6/7.
 */
public class SysSetLanguageValuationAdapter extends RecyclerView.Adapter<SysSetLanguageValuationAdapter.SysSetLanguageValuationAdapterViewHolder> {


    private final List<String> data;

    private final int type;

    private int currPosition;

    public SysSetLanguageValuationAdapter(List<String> list, int position, int type) {
        this.data = list;
        this.currPosition = position;
        this.type = type;
    }

    @NonNull
    @Override
    public SysSetLanguageValuationAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_language_valuation, parent, false);
        return new SysSetLanguageValuationAdapterViewHolder(inflate, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull SysSetLanguageValuationAdapterViewHolder holder, int position) {

        Drawable leftDrawable = holder.rlViewParent.getContext().getResources().getDrawable(R.drawable.icon_ch);

        switch (type) {
            case 0:

                switch (position) {
                    case 0:
                        //简体中文
                        leftDrawable = holder.rlViewParent.getContext().getResources().getDrawable(R.drawable.icon_ch);
                        break;
                    case 1:
                        //英语
                        leftDrawable = holder.rlViewParent.getContext().getResources().getDrawable(R.drawable.icon_english);
                        break;
                    case 2:
                        //繁体中文
                        leftDrawable = holder.rlViewParent.getContext().getResources().getDrawable(R.drawable.icon_ch);
                        break;
                    case 3:
                        //日语
                        leftDrawable = holder.rlViewParent.getContext().getResources().getDrawable(R.drawable.icon_jp);
                        break;
                }
                break;
            case 1:
                switch (position) {
                    case 0:
                        leftDrawable = holder.rlViewParent.getContext().getResources().getDrawable(R.drawable.icon_usdt);
                        break;
                    case 1:
                        leftDrawable = holder.rlViewParent.getContext().getResources().getDrawable(R.drawable.icon_cny);
                        break;
                    case 2:
                        leftDrawable = holder.rlViewParent.getContext().getResources().getDrawable(R.drawable.icon_krw);
                        break;
                    case 3:
                        leftDrawable = holder.rlViewParent.getContext().getResources().getDrawable(R.drawable.icon_jry);
                        break;
                    case 4:
                        leftDrawable = holder.rlViewParent.getContext().getResources().getDrawable(R.drawable.icon_eur);
                        break;
                    case 5:
                        leftDrawable = holder.rlViewParent.getContext().getResources().getDrawable(R.drawable.icon_rub);
                        break;
                    case 6:
                        leftDrawable = holder.rlViewParent.getContext().getResources().getDrawable(R.drawable.icon_gbp);
                        break;
                }
                break;
        }

        if (type != 2) {
            leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
            if (currPosition == position) {
                Drawable rightDrawable = holder.rlViewParent.getContext().getResources().getDrawable(R.drawable.icon_choose);
                rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());

                holder.tvViewName.setCompoundDrawables(leftDrawable, null, rightDrawable, null);
            } else {
                holder.tvViewName.setCompoundDrawables(leftDrawable, null, null, null);
            }
            holder.tvViewName.setText(data.get(position));
        } else {
            String[] split = data.get(position).split(",");
            holder.tvViewName.setText(split[0] + "(+" + split[1] + ")");
            holder.tvViewName.setCompoundDrawables(null, null, null, null);

            String string = SharedPreferencesUtil.getString(Constants.SP_COUNTRY_PHONE_NUM_POSITION, "+86");
            if (data.get(position).contains(string)) {
                Drawable rightDrawable = holder.rlViewParent.getContext().getResources().getDrawable(R.drawable.icon_choose);
                rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                holder.tvViewName.setCompoundDrawables(leftDrawable, null, rightDrawable, null);
            } else {
                holder.tvViewName.setCompoundDrawables(leftDrawable, null, null, null);
            }
        }
    }

    @Override
    public int getItemCount() {
        return Utils.checkList(data);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private OnclickListener onclickListener;

    interface OnclickListener {
        void onClick(int position);
    }

    public void setOnclickListener(OnclickListener onclickListener) {
        this.onclickListener = onclickListener;
    }

    class SysSetLanguageValuationAdapterViewHolder extends RecyclerView.ViewHolder {

        private int position;

        @BindView(R.id.rl_view_parent)
        RelativeLayout rlViewParent;

        @BindView(R.id.tv_view_name)
        TextView tvViewName;

        public SysSetLanguageValuationAdapterViewHolder(View itemView, int viewType) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.position = viewType;
        }

        @OnClick({R.id.rl_view_parent})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rl_view_parent:
                    currPosition = position;
                    switch (type) {
                        case 0:
                            Configuration config = view.getResources().getConfiguration();//获得设置对象

                            switch (position) {
                                case 0:
                                    //简体中文
                                    SharedPreferencesUtil.putString(FiledConstants.SP_LANGUGE_ABBREVIATION, "zh");
                                    config.locale = Locale.CHINA;
                                    break;
                                case 1:
                                    //英语
                                    SharedPreferencesUtil.putString(FiledConstants.SP_LANGUGE_ABBREVIATION, "en");
                                    config.locale = Locale.US;
                                    break;
                                case 2:
                                    //繁体中文
                                    SharedPreferencesUtil.putString(FiledConstants.SP_LANGUGE_ABBREVIATION, "tw");
                                    config.locale = Locale.TAIWAN;
                                    break;
                                case 3:
                                    //日语
                                    SharedPreferencesUtil.putString(FiledConstants.SP_LANGUGE_ABBREVIATION, "jp");
                                    config.locale = Locale.JAPAN;
                                    break;
                            }
                            SharedPreferencesUtil.putInteger(Constants.SP_LANGUGE_POSITION, position);

                            Resources resources = UTEXApplication.getInstance().getResources();//获得res资源对象
                            DisplayMetrics dm = resources.getDisplayMetrics();//获得屏幕参数：主要是分辨率，像素等。
                            resources.updateConfiguration(config, dm);

                            Activity activity = (Activity) view.getContext();
                            activity.recreate();

                            activity.finish();
                            break;
                        case 1:
                            String str = data.get(position);
                            str = str.substring(0, str.indexOf("("));
                            SharedPreferencesUtil.putString(FiledConstants.PALTFORM_VALUAT, str);
                            SharedPreferencesUtil.putInteger(Constants.SP_VALUAT_POSITION, position);
                            break;
                        case 2:
                            String s = data.get(position);
                            String[] split = s.split(",");
                            SharedPreferencesUtil.putString(Constants.SP_COUNTRY_PHONE_NUM_POSITION, split[1]);
                            onclickListener.onClick(position);
                            break;
                    }

                    notifyDataSetChanged();
                    break;
            }
        }
    }

}
