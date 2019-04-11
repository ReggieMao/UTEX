package com.utex.mvp.trad.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import butterknife.OnClick;

/**
 * Created by Demon on 2018/5/18.
 */
public class MarketHandicapAdapter extends RecyclerView.Adapter<MarketHandicapAdapter.MarketHandicapAdapterViewHolder> {

    private TickerDo currTickerDo;

    private List<List<String>> data;

    private int status;

    private Context mContext;

    private int totalAmount;

    private HandicapClickListener handicapClickListener;

    public MarketHandicapAdapter(int status, TickerDo currTickerDo) {
        this.status = status;
        this.currTickerDo = currTickerDo;
    }

    public void setData(List<List<String>> asks) {
        this.data = asks;
        count = 0;
        calculationMax();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MarketHandicapAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_market_handicap, parent, false);

        return new MarketHandicapAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MarketHandicapAdapterViewHolder holder, int position) {
        String value = null;
        int scale = 0;
        switch (status) {
            case 0:
                //卖
                holder.pbMarketBg.setVisibility(View.VISIBLE);
                Drawable ask = mContext.getResources().getDrawable(R.drawable.ask_cat_market_detail_bg);
                holder.pbMarketBg.setProgressDrawable(ask);
                holder.tvMarketNum.setTextColor(Utils.getResourceColor(mContext, R.color.fee6a5e));
                holder.pbMarketBg.setMax(totalAmount);
                holder.pbMarketBg.setProgress(getCurrProgress(position));
                holder.tvMarketPrice.setText(data.get(position).get(1));
                holder.tvMarketPrice.setTextColor(Utils.getResourceColor(mContext, R.color.b666666_99ffffff));

                value = data.get(position).get(0);
                scale = Integer.parseInt(currTickerDo.getMoney_decimal()) - Integer.parseInt(currTickerDo.getAmount_decimal());
                holder.tvMarketNum.setText(Utils.getScientificCountingMethodAfterData(Double.parseDouble(value), scale));


                holder.tvMarketAmount.setText(String.valueOf(position + 1));
                holder.tvMarketAmount.setTextColor(Utils.getResourceColor(mContext, R.color.fee6a5e));
                break;
            case 1:
                //买
                holder.pbMarketBg.setVisibility(View.VISIBLE);
                Drawable bid = mContext.getResources().getDrawable(R.drawable.bid_cat_bg);
                holder.pbMarketBg.setProgressDrawable(bid);
                holder.tvMarketNum.setText(String.valueOf(position + 1));
                holder.tvMarketPrice.setTextColor(Utils.getResourceColor(mContext, R.color.b666666_99ffffff));
                holder.tvMarketNum.setTextColor(Utils.getResourceColor(mContext, R.color.f50b577));
                holder.pbMarketBg.setMax(totalAmount);
                holder.pbMarketBg.setProgress(totalAmount - getCurrProgress(position));
                holder.tvMarketPrice.setText(data.get(position).get(1));

                value = data.get(position).get(0);
                scale = Integer.parseInt(currTickerDo.getMoney_decimal()) - Integer.parseInt(currTickerDo.getAmount_decimal());
                holder.tvMarketAmount.setText(Utils.getScientificCountingMethodAfterData(Double.parseDouble(value), scale));

                holder.tvMarketAmount.setTextColor(Utils.getResourceColor(mContext, R.color.f50b577));
                break;
            case 2:
                //卖
                if (data != null) {
                    if ((data.size() == 1 && position != 4) ||
                            (data.size() == 2 && position < 3) ||
                            (data.size() == 3 && position < 2) ||
                            (data.size() == 4 && position < 1) ||
                            (data.size() == 0)) {

                        count++;
                        holder.pbMarketBg.setVisibility(View.GONE);
                        holder.tvMarketAmount.setText("");
                        holder.tvMarketNum.setText("");
                        holder.tvMarketPrice.setText("");
                        return;
                    }
                    holder.pbMarketBg.setVisibility(View.VISIBLE);
                    setSellData(holder, value, scale, position);
                }
                break;
            case 3:
                holder.pbMarketBg.setVisibility(View.VISIBLE);
                Drawable bid2 = mContext.getResources().getDrawable(R.drawable.bid_cat_bg);
                holder.pbMarketBg.setProgressDrawable(bid2);
                holder.tvMarketNum.setText(String.valueOf(position + 1));
                holder.tvMarketPrice.setTextColor(Utils.getResourceColor(mContext, R.color.f50b577));
                holder.tvMarketNum.setTextColor(Utils.getResourceColor(mContext, R.color.f50b577));
                holder.pbMarketBg.setMax(totalAmount);
                holder.pbMarketBg.setProgress(totalAmount - getCurrProgress(position));

                value = data.get(position).get(0);
                scale = Integer.parseInt(currTickerDo.getMoney_decimal()) - Integer.parseInt(currTickerDo.getAmount_decimal());
                Log.e("Demon", String.valueOf(scale));
                String scientificCountingMethodAfterData2 = Utils.getScientificCountingMethodAfterData(Double.parseDouble(value), scale);
                holder.tvMarketPrice.setText(scientificCountingMethodAfterData2);

                String str;
                double v = Double.parseDouble(data.get(position).get(1));
                if (v > 1000) {
                    str = Utils.getScientificCountingMethodAfterData(v / 1000.0, 2) + "k";
                } else {
                    str = data.get(position).get(1);
                }
                holder.tvMarketAmount.setText(str);
                break;
        }
    }

    int count;

    /**
     * 设置卖的数据
     *
     * @param holder
     * @param value
     * @param scale
     * @param position
     */
    private void setSellData(MarketHandicapAdapterViewHolder holder, String value, int scale,
                             int position) {
        Log.e("TAG", position + "::" + count + "::" + data.size() + "::" + (data.size() - (position - count) - 1));
        if (data != null && data.size() > 0) {
            List<String> strings = data.get(data.size() - (position - count) - 1);
            Drawable ask2 = mContext.getResources().getDrawable(R.drawable.ask_cat_bg);
            holder.pbMarketBg.setProgressDrawable(ask2);
            holder.tvMarketPrice.setTextColor(Utils.getResourceColor(mContext, R.color.fee6a5e));
            holder.tvMarketNum.setTextColor(Utils.getResourceColor(mContext, R.color.fee6a5e));
            holder.pbMarketBg.setMax(totalAmount);
            holder.pbMarketBg.setProgress(totalAmount - getCurrProgress(data.size() - (position - count) - 1));
            holder.tvMarketNum.setText(String.valueOf(getItemCount() - position));

            value = strings.get(0);

            scale = Integer.parseInt(currTickerDo.getMoney_decimal()) - Integer.parseInt(currTickerDo.getAmount_decimal());

            String scientificCountingMethodAfterData21 = Utils.getScientificCountingMethodAfterData(Double.parseDouble(value), scale);

            holder.tvMarketPrice.setText(scientificCountingMethodAfterData21);


            String str;
            double v = Double.parseDouble(strings.get(1));
            if (v > 1000) {
                str = Utils.getScientificCountingMethodAfterData(v / 1000.0, 2) + "k";
            } else {
                str = strings.get(1);
            }
            holder.tvMarketAmount.setText(str);
        }

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

    @Override
    public int getItemCount() {
        if (status == 2) {
            return 5;
        } else {
            return Utils.checkList(data);
        }
    }

    class MarketHandicapAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.pb_market_bg)
        ProgressBar pbMarketBg;

        @BindView(R.id.tv_market_num)
        TextView tvMarketNum;

        @BindView(R.id.tv_market_price)
        TextView tvMarketPrice;

        @BindView(R.id.tv_market_amount)
        TextView tvMarketAmount;

        public MarketHandicapAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.rl_market_handicap_parent})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rl_market_handicap_parent:
                    if (handicapClickListener != null) {
                        handicapClickListener.click(tvMarketPrice.getText().toString());
                    }
                    break;
            }
        }


    }

    public TickerDo getCurrTickerDo() {
        return currTickerDo;
    }

    public void setCurrTickerDo(TickerDo currTickerDo) {
        this.currTickerDo = currTickerDo;
    }


    interface HandicapClickListener {
        void click(String s);
    }

    void setHandicapClickListener(HandicapClickListener handicapClickListener) {
        this.handicapClickListener = handicapClickListener;
    }

}
