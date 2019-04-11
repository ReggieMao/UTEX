package com.utex.mvp.market.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.utex.R;
import com.utex.bean.TickerDo;
import com.utex.common.Constants;
import com.utex.common.FiledConstants;
import com.utex.core.UTEXApplication;
import com.utex.mvp.home.bean.TickerData;
import com.utex.mvp.marketdetail.view.MarketDetailActivity;
import com.utex.utils.SharedPreferencesUtil;
import com.utex.utils.Utils;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Demon on 2018/5/18.
 */
public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.MarketAdapterBodyViewHolder> {

    /**
     * 0 跳入详情
     * 1 监听选择
     */
    private final int type;

    private final String market;

    private List<TickerDo> tickerDos;
    private Context context;

    public MarketAdapter(int type, String market) {
        this.type = type;
        this.market = market;
    }

    public void setData(List<TickerDo> list) {
        this.tickerDos = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MarketAdapterBodyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate;
        if (type == 0)
            inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_market_body1, parent, false);
        else
            inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_market_body, parent, false);
        context = inflate.getContext();
        Log.e("TAG", market);
        return new MarketAdapterBodyViewHolder(inflate, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull MarketAdapterBodyViewHolder holder, int position) {
        TickerDo tickerDo = tickerDos.get(position);
        TickerData resultBean = tickerDo.getResultBean();
        int showScale = Integer.parseInt(tickerDo.getMoney_decimal()) - Integer.parseInt(tickerDo.getAmount_decimal());
        switch (type) {
            case 0:
                holder.rlMarketBodyParent.setVisibility(View.VISIBLE);
                holder.rlMarket2Parent.setVisibility(View.GONE);
                String[] split = tickerDo.getCoin_market_alias().toUpperCase().split("_");

                if (tickerDo.getType() == 1 || Utils.getResourceString(R.string.zi_xuan).equals(market)) {
                    //限时
                    split[0] = split[0] + "/";
                    Utils.setFontSpan(holder.tvTickerCode, split, new Integer[]{R.color.b2a2a2a_ffffff, R.color.b999999_e6ffffff});
                } else {
                    holder.tvTickerCode.setText(split[0]);
                }

                if (resultBean != null) {
                    holder.tvMarketBodyVol.setText(Utils.getResourceString(R.string.ershisi_h_liang) + " " + resultBean.getVolume());
                    holder.tvMarketDefaultPrice.setText(Utils.getScientificCountingMethodAfterData(Double.valueOf(resultBean.getLast()), showScale));
                    if (!"0.00000000".equals(holder.tvMarketDefaultPrice.getText().toString())) {
                        SharedPreferencesUtil.putString(FiledConstants.GZH_MARKET_PRICE, holder.tvMarketDefaultPrice.getText().toString());
                    }

                    double rate = UTEXApplication.getRate(split[1]);
                    if (rate == 0) {
                        holder.tvMarketPlatformPrice.setVisibility(View.GONE);
                    } else {
                        holder.tvMarketPlatformPrice.setText("≈" + Utils.getScientificCountingMethodAfterData(Double.valueOf(resultBean.getLast()) * rate, showScale) + " " + SharedPreferencesUtil.getString(FiledConstants.PALTFORM_VALUAT, "USD"));
                    }

                    holder.tvMarketBodyRate.setVisibility(View.VISIBLE);
                    DecimalFormat df = new DecimalFormat("######0.00");
                    double v = (Double.parseDouble(resultBean.getLast()) - Double.parseDouble(resultBean.getOpen())) / Double.parseDouble(resultBean.getOpen());
                    if (v > 0) {
                        //涨
                        holder.tvMarketBodyRate.setText("+" + df.format(v * 100) + "%");
                        holder.tvMarketBodyRate.setBackgroundResource(R.drawable.shape_market_body_up_bg);
                    } else if (TextUtils.isEmpty(resultBean.getOpen()) || Double.parseDouble(resultBean.getOpen()) == 0) {
                        holder.tvMarketBodyRate.setText("+0.00%");
                        holder.tvMarketBodyRate.setBackgroundResource(R.drawable.shape_market_body_zero_bg);
                    } else {
                        //跌
                        holder.tvMarketBodyRate.setText(df.format(v * 100) + "%");
                        holder.tvMarketBodyRate.setBackgroundResource(R.drawable.shape_market_body_down_bg);
                    }
                } else {
                    holder.tvMarketBodyRate.setVisibility(View.GONE);
                }

                Utils.setMonospaceFont(holder.tvTickerCode);
                Utils.setMonospaceFont(holder.tvMarketDefaultPrice);
                Utils.setMonospaceFont(holder.tvMarketBodyRate);
                break;
            case 1:
                String[] split1 = tickerDo.getCoin_market_code().toUpperCase().split("_");
                split1[0] = split1[0] + "/";
                Utils.setFontSpan(holder.tvMarketCoinName, split1, new Integer[]{R.color.b2a2a2a_ffffff, R.color.b999999_e6ffffff});

                try {
                    holder.rlMarketBodyParent.setVisibility(View.GONE);
                    holder.rlMarket2Parent.setVisibility(View.VISIBLE);

                    double v = (Double.parseDouble(resultBean.getLast()) - Double.parseDouble(resultBean.getOpen())) / Double.parseDouble(resultBean.getOpen());
                    DecimalFormat df = new DecimalFormat("######0.00");
                    if (v > 0) {
                        //涨
                        holder.tvMarketRate.setText("+" + df.format(v * 100) + "%");
                        holder.tvMarketRate.setTextColor(Utils.getResourceColor(context, R.color.f50b577));
                        holder.tvMarketPrice.setTextColor(Utils.getResourceColor(context, R.color.f50b577));
                    } else if (TextUtils.isEmpty(resultBean.getOpen()) || Double.parseDouble(resultBean.getOpen()) == 0) {
                        holder.tvMarketRate.setText("+0.00%");
                        holder.tvMarketRate.setTextColor(Utils.getResourceColor(context, R.color.bafb3b0_42ffffff));
                        holder.tvMarketPrice.setTextColor(Utils.getResourceColor(context, R.color.bafb3b0_42ffffff));
                    } else {
                        //跌
                        holder.tvMarketRate.setText(df.format(v * 100) + "%");
                        holder.tvMarketRate.setTextColor(Utils.getResourceColor(context, R.color.fee6a5e));
                        holder.tvMarketPrice.setTextColor(Utils.getResourceColor(context, R.color.fee6a5e));
                    }

                    holder.tvMarketPrice.setText(Utils.getScientificCountingMethodAfterData(Double.valueOf(resultBean.getLast()), showScale));


//                    holder.tvMarketCoinName.setText(tickerDo.getCoin_market_code().replace("_", "/").toUpperCase());
                } catch (Exception e) {
//                    Log.e("TAG", "报错啦：" + position);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return Utils.checkList(tickerDos);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class MarketAdapterBodyViewHolder extends RecyclerView.ViewHolder {

        private final int position;

        @BindView(R.id.rl_market_body_parent)
        RelativeLayout rlMarketBodyParent;

        @BindView(R.id.tv_ticker_code)
        TextView tvTickerCode;

        @BindView(R.id.tv_makret_body_vol)
        TextView tvMarketBodyVol;

        @BindView(R.id.tv_market_defalue_price)
        TextView tvMarketDefaultPrice;

        @BindView(R.id.tv_market_platform_price)
        TextView tvMarketPlatformPrice;

        @BindView(R.id.tv_market_body_rate)
        TextView tvMarketBodyRate;

        @BindView(R.id.rl_market_2_parent)
        RelativeLayout rlMarket2Parent;

        @BindView(R.id.tv_market_coin_name)
        TextView tvMarketCoinName;

        @BindView(R.id.tv_market_price)
        TextView tvMarketPrice;

        @BindView(R.id.tv_market_rate)
        TextView tvMarketRate;

        public MarketAdapterBodyViewHolder(View itemView, int viewType) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.position = viewType;
        }

        @OnClick({R.id.rl_market_body_parent, R.id.rl_market_2_parent})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rl_market_2_parent:
                case R.id.rl_market_body_parent:
                    //跳入详情
                    if (tickerDos.get(position).getStatus() == 2) {
                        return;
                    }
                    
                    switch (type) {
                        case 0:
                            Intent intent = new Intent(view.getContext(), MarketDetailActivity.class);
                            intent.putExtra(Constants.COIN_CODE, tickerDos.get(position).getCoin_market_code());
                            view.getContext().startActivity(intent);
                            break;
                        case 1:
                            if (itemOnclickListener != null) {
                                itemOnclickListener.onclick(tickerDos.get(position).getCoin_market_code());
                            }
                            break;
                    }
                    break;
            }
        }
    }

    private ItemOnclickListener itemOnclickListener;

    interface ItemOnclickListener {
        void onclick(String coinCode);
    }

    public void setItemOnclickListener(ItemOnclickListener itemOnclickListener) {
        this.itemOnclickListener = itemOnclickListener;
    }

}
