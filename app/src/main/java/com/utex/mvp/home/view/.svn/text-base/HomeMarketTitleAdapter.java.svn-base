package com.utex.mvp.home.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.utex.R;
import com.utex.bean.TickerDo;
import com.utex.common.FiledConstants;
import com.utex.common.UMConstants;
import com.utex.db.ExTickerDao;
import com.utex.utils.SharedPreferencesUtil;
import com.utex.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Demon on 2018/5/21.
 */
public class HomeMarketTitleAdapter extends RecyclerView.Adapter<HomeMarketTitleAdapter.HomeMarketTitleAdapterViewHolder> {

    private List<String> titles;

    private Integer currIndex;

    private HomeMarketTitleAdapterListener homeMarketTitleAdapterListener;
    private Context context;

    public HomeMarketTitleAdapter(List<String> titles) {
        this.titles = titles;
        List<TickerDo> tickerByMarket = ExTickerDao.getTickerByMarket(Utils.getResourceString(R.string.zi_xuan));
        if (tickerByMarket != null && tickerByMarket.size() > 0) {
            currIndex = 0;
        } else {
            currIndex = 1;
        }
    }

    @NonNull
    @Override
    public HomeMarketTitleAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_market_title, parent, false);
        context = inflate.getContext();
        return new HomeMarketTitleAdapterViewHolder(inflate, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeMarketTitleAdapterViewHolder holder, int position) {
        if (titles.get(position).equals(Utils.getResourceString(R.string.zi_xuan)) || titles.get(position).equals(Utils.getResourceString(R.string.xian_shi))) {
            holder.tvHomeMarketTitleName.setText(titles.get(position));
        } else {
            holder.tvHomeMarketTitleName.setText(titles.get(position).toUpperCase());
        }

        if (position == 0) {
            holder.viewMarketTitleStart.setVisibility(View.VISIBLE);
        } else if (position == getItemCount() - 1) {
            holder.viewMarketTitleEnd.setVisibility(View.VISIBLE);
        }


//        Utils.setMonospaceFont(holder.tvHomeMarketTitleName);

        if (position == currIndex) {
            holder.tvHomeMarketTitleName.setTextColor(Utils.getResourceColor(context, R.color.fffffff));
            holder.tvHomeMarketTitleName.setBackground(holder.itemView.getContext().getResources().getDrawable(R.drawable.home_title_true_bg));
            if (SharedPreferencesUtil.getInteger(FiledConstants.LIGHT_DARK, 1) == 1) {
                holder.ivHomeMarketTitleBg.setVisibility(View.VISIBLE);
            } else {
                holder.ivHomeMarketTitleBg.setVisibility(View.INVISIBLE);
            }
        } else {
            holder.ivHomeMarketTitleBg.setVisibility(View.INVISIBLE);
            holder.tvHomeMarketTitleName.setTextColor(Utils.getResourceColor(context, R.color.b333333_e6ffffff));

            holder.tvHomeMarketTitleName.setBackground(holder.itemView.getContext().getResources().getDrawable(R.drawable.home_title_false_bg));
        }

    }

    @Override
    public int getItemCount() {
        return Utils.checkList(titles);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /**
     * 设置当前页
     *
     * @param position
     */
    public void setCurrItem(int position) {
        this.currIndex = position;
        notifyDataSetChanged();
    }

    class HomeMarketTitleAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_home_market_title_name)
        TextView tvHomeMarketTitleName;

        @BindView(R.id.iv_home_market_title_bg)
        ImageView ivHomeMarketTitleBg;

        @BindView(R.id.view_market_title_start)
        View viewMarketTitleStart;

        @BindView(R.id.view_market_title_end)
        View viewMarketTitleEnd;

        private Integer position;

        public HomeMarketTitleAdapterViewHolder(View itemView, int position) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.position = position;
        }

        @OnClick({R.id.tv_home_market_title_name})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_home_market_title_name:
                    currIndex = position;
                    if (homeMarketTitleAdapterListener != null) {
                        homeMarketTitleAdapterListener.click(currIndex);
                    }

                    String string = tvHomeMarketTitleName.getText().toString();
                    if (string.equals(Utils.getResourceString(R.string.zi_xuan))) {
                        MobclickAgent.onEvent(view.getContext(), UMConstants.HOME_OPTIONAL_RANGE__CLICK);
                    } else if (string.equals(Utils.getResourceString(R.string.xian_shi))) {
                        MobclickAgent.onEvent(view.getContext(), UMConstants.HOME_LIMITED_RANGE_CLICK);
                    } else {
                        MobclickAgent.onEvent(view.getContext(), "home_" + string.toLowerCase() + "_range_click");
                    }


                    notifyDataSetChanged();
                    break;
            }
        }
    }

    interface HomeMarketTitleAdapterListener {
        void click(Integer currIndex);
    }

    public void setClickListener(HomeMarketTitleAdapterListener homeMarketTitleAdapterListener) {
        this.homeMarketTitleAdapterListener = homeMarketTitleAdapterListener;
    }
}
