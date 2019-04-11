package com.utex.mvp.mine.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.utex.R;
import com.utex.mvp.mine.bean.WithdrawalFeeVo;
import com.utex.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Demon on 2018/11/1.
 */
public class RateExplainAdapter extends RecyclerView.Adapter<RateExplainAdapter.RateExplainAdapterViewHolder> {

    private final List<WithdrawalFeeVo.DataBean> data;

    public RateExplainAdapter(List<WithdrawalFeeVo.DataBean> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RateExplainAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rate_explain, parent, false);
        return new RateExplainAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RateExplainAdapterViewHolder holder, int position) {

        Glide
                .with(holder.ivItemRateExplain.getContext())
                .load(data.get(position).getCoin_logo())
                .into(holder.ivItemRateExplain);

        holder.tvRateExplainCoin.setText(data.get(position).getCoin_code());
        holder.tvRateExplainFee.setText(String.valueOf(data.get(position).getWithdrawal_fee()));
        holder.tvRateExplainNumber.setText(String.valueOf(data.get(position).getWithdrawal_min_amount()));
    }

    @Override
    public int getItemCount() {
        return Utils.checkList(data);
    }

    class RateExplainAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rl_rate_explain_parent)
        RelativeLayout rlRateExplainParent;

        @BindView(R.id.iv_item_rate_explain)
        ImageView ivItemRateExplain;

        @BindView(R.id.tv_rate_explain_coin)
        TextView tvRateExplainCoin;

        @BindView(R.id.tv_rate_explain_fee)
        TextView tvRateExplainFee;

        @BindView(R.id.tv_rate_explain_number)
        TextView tvRateExplainNumber;

        public RateExplainAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
