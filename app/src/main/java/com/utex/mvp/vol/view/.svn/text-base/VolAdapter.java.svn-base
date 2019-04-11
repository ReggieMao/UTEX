package com.utex.mvp.vol.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.utex.R;
import com.utex.mvp.vol.bean.VolVo;
import com.utex.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Demon on 2018/5/22.
 */
public class VolAdapter extends RecyclerView.Adapter<VolAdapter.VolAdapterViewHolder> {

    private List<VolVo> data;
    private Context context;

    @NonNull
    @Override
    public VolAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vol, parent, false);
        context = inflate.getContext();
        return new VolAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull VolAdapterViewHolder holder, int position) {
        VolVo volVo = data.get(position);

        switch (volVo.getType()) {
            case "buy":
                //买
                holder.tvVolDirection.setText(Utils.getResourceString(R.string.buy));
                holder.tvVolDirection.setTextColor(Utils.getResourceColor(context, R.color.f50b577));
                break;
            case "sell":
                //卖
                holder.tvVolDirection.setText(Utils.getResourceString(R.string.sell));
                holder.tvVolDirection.setTextColor(Utils.getResourceColor(context, R.color.fee6a5e));
                break;
        }
        try {
            if (volVo.getTime().contains(".")) {
                holder.tvVolTime.setText(Utils.long2String(Long.parseLong(volVo.getTime().split("\\.")[0]) * 1000L, 2));
            } else {
                holder.tvVolTime.setText(Utils.long2String(Long.parseLong(volVo.getTime()) * 1000L, 2));
            }
        } catch (Exception e) {
            holder.tvVolTime.setText(Utils.long2String(System.currentTimeMillis(), 2));
        }

        holder.tvVolPrice.setText(Utils.getScientificCountingMethodAfterData(Double.parseDouble(volVo.getPrice()), 8));

        holder.tvVolAmount.setText(volVo.getAmount());
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

    class VolAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_vol_time)
        TextView tvVolTime;

        @BindView(R.id.tv_vol_direction)
        TextView tvVolDirection;

        @BindView(R.id.tv_vol_amount)
        TextView tvVolAmount;

        @BindView(R.id.tv_vol_price)
        TextView tvVolPrice;

        public VolAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
