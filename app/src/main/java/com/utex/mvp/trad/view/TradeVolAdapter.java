package com.utex.mvp.trad.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.utex.R;
import com.utex.bean.TickerDo;
import com.utex.mvp.vol.bean.VolVo;
import com.utex.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Demon on 2018/7/17.
 */
public class TradeVolAdapter extends RecyclerView.Adapter<TradeVolAdapter.TradeVolAdapterViewHolder> {

    private TickerDo currTickerDo;
    private List<VolVo> data;
    private Context context;

    public TradeVolAdapter(TickerDo currTickerDo) {
        this.currTickerDo = currTickerDo;
    }

    @NonNull
    @Override
    public TradeVolAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trade_vol, parent, false);
        context = inflate.getContext();
        return new TradeVolAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull TradeVolAdapterViewHolder holder, int position) {
        VolVo volVo = data.get(position);
        holder.tvTradeVolAmount.setText(volVo.getAmount());
        holder.tvTradeVolPrice.setText(Utils.getScientificCountingMethodAfterData(Double.valueOf(volVo.getPrice()), Utils.getTickerShowScale(currTickerDo)));

        try {
            if (volVo.getTime().contains(".")) {
                holder.tvTradeVolTime.setText(Utils.long2String(Long.parseLong(volVo.getTime().split("\\.")[0]) * 1000, 5));
            } else {
                holder.tvTradeVolTime.setText(Utils.long2String(Long.parseLong(volVo.getTime()) * 1000, 5));
            }
        } catch (Exception e) {
            holder.tvTradeVolTime.setText(Utils.long2String(System.currentTimeMillis(), 5));
        }

        switch (volVo.getType()) {
            case "buy":
                holder.tvTradeVolPrice.setTextColor(Utils.getResourceColor(context, R.color.f50b577));
                break;
            case "sell":
                holder.tvTradeVolPrice.setTextColor(Utils.getResourceColor(context, R.color.fee6a5e));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return Utils.checkList(data);
    }

    public void setData(List<VolVo> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public List<VolVo> getData() {
        return data;
    }

    class TradeVolAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_trade_vol_time)
        TextView tvTradeVolTime;

        @BindView(R.id.tv_trade_vol_price)
        TextView tvTradeVolPrice;

        @BindView(R.id.tv_trade_vol_amount)
        TextView tvTradeVolAmount;

        public TradeVolAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
