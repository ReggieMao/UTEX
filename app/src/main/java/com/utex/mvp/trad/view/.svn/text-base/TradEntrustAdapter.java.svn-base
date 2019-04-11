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
import com.utex.db.ExTickerDao;
import com.utex.mvp.trad.bean.Entrust;
import com.utex.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Demon on 2018/5/21.
 */
public class TradEntrustAdapter extends RecyclerView.Adapter<TradEntrustAdapter.TradEntrustAdapterViewHolder> {

    private List<Entrust.DataBean> data;

    private TradEntrustListener tradEntrustListener;
    private Context context;


    @NonNull
    @Override
    public TradEntrustAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trad_entrust, parent, false);
        context = inflate.getContext();
        return new TradEntrustAdapterViewHolder(inflate, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull TradEntrustAdapterViewHolder holder, int position) {
        Entrust.DataBean dataBean = data.get(position);
        String market = dataBean.getMarket();
        TickerDo tickerByCode = ExTickerDao.getTickerByCode(market);
        String[] split = market.split("_");

        holder.tvTradEntrustAmount.setText(Utils.getScientificCountingMethodAfterData(dataBean.getAmount(), Integer.valueOf(tickerByCode.getAmount_decimal())));
        holder.tvTradEntrustPrice.setText(Utils.getScientificCountingMethodAfterData(dataBean.getPrice(), Utils.getTickerShowScale(tickerByCode)));
        holder.tvTradEntrustActualAmount.setText(Utils.eliminateZero(Double.parseDouble(Utils.getScientificCountingMethodAfterData(dataBean.getDeal_stock(), Utils.getTickerShowScale(tickerByCode)))));

        holder.tvTradEntrustPriceExplain.setText(Utils.getResourceString(R.string.jia_ge) + "(" + split[1] + ")");
        holder.tvTradEntrustAmountExplain.setText(Utils.getResourceString(R.string.shu_liang) + "(" + split[0] + ")");
        holder.tvTradEntrustActualAmountExplain.setText(Utils.getResourceString(R.string.shi_ji_cheng_jiao) + "(" + split[0] + ")");

        holder.tvTradEntrustTime.setText(Utils.long2String(dataBean.getUpdate_time(), 1));

        switch (dataBean.getSide()) {
            case 1:
                holder.tvTradEntrustDirection.setText(Utils.getResourceString(R.string.sell));
                holder.tvTradEntrustDirection.setTextColor(Utils.getResourceColor(context, R.color.fee6a5e));
                break;
            case 2:
                holder.tvTradEntrustDirection.setTextColor(Utils.getResourceColor(context, R.color.f50b577));
                holder.tvTradEntrustDirection.setText(Utils.getResourceString(R.string.buy));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return Utils.checkList(data);
    }

    public void setData(List<Entrust.DataBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class TradEntrustAdapterViewHolder extends RecyclerView.ViewHolder {

        private final int position;

        @BindView(R.id.tv_trad_entrust_direction)
        TextView tvTradEntrustDirection;

        @BindView(R.id.tv_trad_entrust_time)
        TextView tvTradEntrustTime;

        @BindView(R.id.tv_trad_entrust_price)
        TextView tvTradEntrustPrice;

        @BindView(R.id.tv_trad_entrust_amount)
        TextView tvTradEntrustAmount;

        @BindView(R.id.tv_trad_entrust_actual_amount)
        TextView tvTradEntrustActualAmount;

        @BindView(R.id.tv_trad_entrust_price_explain)
        TextView tvTradEntrustPriceExplain;

        @BindView(R.id.tv_trad_entrust_amount_explain)
        TextView tvTradEntrustAmountExplain;

        @BindView(R.id.tv_trad_entrust_actual_amount_explain)
        TextView tvTradEntrustActualAmountExplain;


        public TradEntrustAdapterViewHolder(View itemView, int viewType) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.position = viewType;
        }

        @OnClick({R.id.tv_trad_entrust_revoke})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_trad_entrust_revoke:
                    tradEntrustListener.cannelOrder(data.get(position), position);
                    break;
            }
        }


    }

    public List<Entrust.DataBean> getData() {
        return data;
    }

    public void setTradEntrustListener(TradEntrustListener tradEntrustListener) {
        this.tradEntrustListener = tradEntrustListener;
    }

    interface TradEntrustListener {
        void cannelOrder(Entrust.DataBean dataBean, int position);
    }
}
