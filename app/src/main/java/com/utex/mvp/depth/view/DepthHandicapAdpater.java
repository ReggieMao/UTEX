package com.utex.mvp.depth.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.utex.R;
import com.utex.bean.TickerDo;
import com.utex.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Demon on 2018/5/22.
 */
public class DepthHandicapAdpater extends RecyclerView.Adapter<DepthHandicapAdpater.DepthHandicapViewHolder> {

    private final TickerDo currTickerDo;
    /**
     * 1 买盘
     * 2 卖盘
     */
    private Integer status;
    private Context mContext;
    private int totalAmount;
    private List<List<String>> data;

    public DepthHandicapAdpater(Integer status, TickerDo currTickerDo) {
        this.status = status;
        this.currTickerDo = currTickerDo;
    }

    public void setData(List<List<String>> asks) {
        this.data = asks;
        calculationMax();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DepthHandicapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = null;
        switch (status) {
            case 1:
                inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_depth_ask, parent, false);
                break;
            case 2:
                inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_depth_bid, parent, false);
                break;
        }
        mContext = inflate.getContext();
        return new DepthHandicapViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull DepthHandicapViewHolder holder, int position) {
        String value = null;
        int scale = 0;
        switch (status) {
            case 1:
                //卖
                holder.pbMarketBg.setVisibility(View.VISIBLE);
                Drawable ask = mContext.getResources().getDrawable(R.drawable.ask_cat_market_detail_bg);
                holder.pbMarketBg.setProgressDrawable(ask);
                holder.pbMarketBg.setMax(totalAmount);
                holder.pbMarketBg.setProgress(getCurrProgress(position));


                value = data.get(position).get(0);
                scale = Integer.parseInt(currTickerDo.getMoney_decimal()) - Integer.parseInt(currTickerDo.getAmount_decimal());

                holder.tv1.setText(Utils.getScientificCountingMethodAfterData(Double.parseDouble(value), scale));
                holder.tv2.setText(amountFormat(data.get(position).get(1)));
                holder.tv3.setText(String.valueOf(position + 1));

                holder.tv1.setTextColor(Utils.getResourceColor(mContext, R.color.fee6a5e));
                holder.tv2.setTextColor(Utils.getResourceColor(mContext, R.color.b666666_99ffffff));
                holder.tv3.setTextColor(Utils.getResourceColor(mContext, R.color.fee6a5e));
                break;
            case 2:
                //买
                holder.pbMarketBg.setVisibility(View.VISIBLE);
                Drawable bid = mContext.getResources().getDrawable(R.drawable.bid_cat_bg);
                holder.pbMarketBg.setProgressDrawable(bid);

                holder.pbMarketBg.setMax(totalAmount);
                holder.pbMarketBg.setProgress(totalAmount - getCurrProgress(position));

                value = data.get(position).get(0);
                scale = Integer.parseInt(currTickerDo.getMoney_decimal()) - Integer.parseInt(currTickerDo.getAmount_decimal());

                holder.tv1.setText(String.valueOf(position + 1));
                holder.tv2.setText(amountFormat(data.get(position).get(1)));
                holder.tv3.setText(Utils.getScientificCountingMethodAfterData(Double.parseDouble(value), scale));

                holder.tv1.setTextColor(Utils.getResourceColor(mContext, R.color.f50b577));
                holder.tv2.setTextColor(Utils.getResourceColor(mContext, R.color.b666666_99ffffff));
                holder.tv3.setTextColor(Utils.getResourceColor(mContext, R.color.f50b577));
                break;
        }

    }

    private String amountFormat(String value) {
        double v = Double.parseDouble(value);
        String str;
        if (v > 1000) {
            str = Utils.eliminateZero(Double.parseDouble(Utils.getScientificCountingMethodAfterData(v / 1000.0, 2))) + "k";
        } else {
            str = value;
        }

        return str;
    }

    private int getCurrProgress(int position) {
        int amount = 0;
        if (data != null && data.size() > 0) {
            for (int i = 0; i <= position; i++) {
                List<String> strings = data.get(i);
                amount += (int) (Double.parseDouble(strings.get(1)) * 1000);
            }
        }
        return amount;
    }

    @Override
    public int getItemCount() {
        return Utils.checkList(data);
    }

    /**
     * 计算最大值
     */
    private void calculationMax() {
        totalAmount = 0;
        if (data != null && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                List<String> strings = data.get(i);
                totalAmount += (int) (Double.parseDouble(strings.get(1)) * 1000);
            }
        }

    }

    class DepthHandicapViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.pb_market_bg)
        ProgressBar pbMarketBg;

        @BindView(R.id.tv_1)
        TextView tv1;

        @BindView(R.id.tv_2)
        TextView tv2;

        @BindView(R.id.tv_3)
        TextView tv3;

        public DepthHandicapViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
