package com.utex.mvp.trad.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.utex.R;
import com.utex.bean.TickerDo;
import com.utex.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Demon on 2018/7/10.
 */
public class TradAdapter extends RecyclerView.Adapter<TradAdapter.TradAdapterViewHolder> {

    private List<TickerDo> tickerByCoinCode;

    @NonNull
    @Override
    public TradAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trad_sel, parent, false);
        return new TradAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull TradAdapterViewHolder holder, int position) {

        if (tickerByCoinCode.get(position).getStatus() == 1) {
            holder.tvAssettradName.setClickable(true);
        } else {
            holder.tvAssettradName.setClickable(false);
        }
        holder.tvAssettradName.setText(tickerByCoinCode.get(position).getCoin_market_code().replace("_", "/"));

    }

    public void setData(List<TickerDo> tickerByCoinCode) {
        this.tickerByCoinCode = tickerByCoinCode;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return Utils.checkList(tickerByCoinCode);
    }

    class TradAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_asset_trad_name)
        TextView tvAssettradName;

        public TradAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.tv_asset_trad_name})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_asset_trad_name:
                    if (tradSelListener != null) {
                        tradSelListener.onclick(String.valueOf(tvAssettradName.getText()));
                    }
                    break;
            }
        }
    }

    private TradSelListener tradSelListener;

    public void setTradSelListener(TradSelListener tradSelListener) {
        this.tradSelListener = tradSelListener;
    }

    public interface TradSelListener {
        void onclick(String text);
    }

}
