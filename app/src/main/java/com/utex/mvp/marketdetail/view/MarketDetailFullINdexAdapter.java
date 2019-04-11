package com.utex.mvp.marketdetail.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.utex.R;
import com.utex.mvp.marketdetail.bean.MiTabParamVo;
import com.utex.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Demon on 2018/7/18.
 */
public class MarketDetailFullINdexAdapter extends RecyclerView.Adapter<MarketDetailFullINdexAdapter.MarketDetailFullINdexAdapterViewHolder> {

    private final List<MiTabParamVo> indexs;

    private int currPosition = 4;
    private Context context;

    public MarketDetailFullINdexAdapter(List<MiTabParamVo> indexs) {
        this.indexs = indexs;
    }

    @NonNull
    @Override
    public MarketDetailFullINdexAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_market_detail_full_index, parent, false);
        context = inflate.getContext();
        return new MarketDetailFullINdexAdapterViewHolder(inflate, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull MarketDetailFullINdexAdapterViewHolder holder, int position) {
        if (currPosition == position) {
            holder.tvIndexName.setTextColor(Utils.getResourceColor(context, R.color.f398155));
        } else {
            holder.tvIndexName.setTextColor(Utils.getResourceColor(context, R.color.b999999_e6ffffff));
        }

        holder.tvIndexName.setText(indexs.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return Utils.checkList(indexs);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class MarketDetailFullINdexAdapterViewHolder extends RecyclerView.ViewHolder {

        private final int position;

        @BindView(R.id.tv_index_name)
        TextView tvIndexName;

        public MarketDetailFullINdexAdapterViewHolder(View itemView, int viewType) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.position = viewType;
        }

        @OnClick({R.id.tv_index_name})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_index_name:
                    currPosition = position;
                    if (marketDetailFullINdexAdapterListener != null) {
                        marketDetailFullINdexAdapterListener.onClick(position);
                    }
                    notifyDataSetChanged();
                    break;
            }
        }
    }

    private MarketDetailFullINdexAdapterListener marketDetailFullINdexAdapterListener;

    public void setMarketDetailFullINdexAdapterListener(MarketDetailFullINdexAdapterListener marketDetailFullINdexAdapterListener) {
        this.marketDetailFullINdexAdapterListener = marketDetailFullINdexAdapterListener;
    }

    interface MarketDetailFullINdexAdapterListener {
        void onClick(int position);
    }

}
