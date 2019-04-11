package com.utex.mvp.order.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.utex.R;
import com.utex.common.FiledConstants;
import com.utex.core.UTEXApplication;
import com.utex.db.ExTickerDao;
import com.utex.mvp.mine.view.WithdrawalDetailActivity;
import com.utex.mvp.order.bean.DepositWithdrawalVO;
import com.utex.mvp.order.bean.OrderManageVO;
import com.utex.mvp.order.bean.StationVO;
import com.utex.mvp.order.bean.VolVO;
import com.utex.utils.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Demon on 2018/6/5.
 */
public class OrderManageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    /**
     * 0 当前
     * 1 历史
     * 2 我的成交
     */
    private int type;

    private List<OrderManageVO.DataBean> entrustData;

    private List<VolVO.DataBean> volData;

    private List<DepositWithdrawalVO.DataBean> depositWithdrawalData;

    private List<StationVO.DataBean> stationData;

    private Context context;

    public OrderManageAdapter(int type) {
        this.type = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = null;
        switch (type) {
            case 0:
            case 2:
                inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_manage_curr, parent, false);
                context = inflate.getContext();
                return new OrderManageCurrAdapterViewHolder(inflate, viewType);
            case 1:
                inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_manage_history, parent, false);
                context = inflate.getContext();
                return new OrderManageHistoryAdapterViewHolder(inflate);
            case 3:
            case 4:
            case 5:
                inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_manage_money, parent, false);
                context = inflate.getContext();
                return new OrderManageMoneyOperateAdapterViewHolder(inflate, viewType);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (type) {
            case 0:
                OrderManageVO.DataBean curr = entrustData.get(position);
                String[] currSplit = curr.getMarket().split("_");
                OrderManageCurrAdapterViewHolder orderManageCurrAdapterViewHolder = (OrderManageCurrAdapterViewHolder) holder;

                boolean isBtc2 = false;
                switch (curr.getSide()) {
                    case 1:
                        orderManageCurrAdapterViewHolder.tvOrderManageDirectionText.setText(Utils.getResourceString(R.string.sell));
                        orderManageCurrAdapterViewHolder.tvOrderManageDirectionText.setTextColor(Utils.getResourceColor(context, R.color.fee6a5e));
                        isBtc2 = "gzh".equals(currSplit[1]);
                        break;
                    case 2:
                        orderManageCurrAdapterViewHolder.tvOrderManageDirectionText.setText(Utils.getResourceString(R.string.buy));
                        orderManageCurrAdapterViewHolder.tvOrderManageDirectionText.setTextColor(Utils.getResourceColor(context, R.color.f50b577));
                        isBtc2 = "gzh".equals(currSplit[0]);
                        break;
                }
                Utils.setFontSpan(orderManageCurrAdapterViewHolder.tvOrderManageMarket, new String[]{curr.getMarket().toUpperCase().split("_")[0], "/" + curr.getMarket().toUpperCase().split("_")[1]}, new Integer[]{R.color.b333333_ffffff, R.color.b666666_99ffffff});

                orderManageCurrAdapterViewHolder.tvOrderManagePrice.setText(curr.getPrice());
                orderManageCurrAdapterViewHolder.tvOrderManagePriceLabel.setText(Utils.getResourceString(R.string.jia_ge) + "(" + currSplit[1].toUpperCase() + ")");
                orderManageCurrAdapterViewHolder.tvOrderManageAmount.setText(String.valueOf(curr.getAmount()));
                orderManageCurrAdapterViewHolder.tvOrderManageAmountLabel.setText(Utils.getResourceString(R.string.shu_liang) + "(" + currSplit[0].toUpperCase() + ")");
                orderManageCurrAdapterViewHolder.tvOrderManageActual.setText(String.valueOf(curr.getDeal_money()));
                orderManageCurrAdapterViewHolder.tvOrderManageActualLabel.setText(Utils.getResourceString(R.string.shi_ji_cheng_jiao) + "(" + currSplit[1].toUpperCase() + ")");
                orderManageCurrAdapterViewHolder.tvOrderManageTime.setText(Utils.long2String(curr.getUpdate_time(), 3));
                orderManageCurrAdapterViewHolder.tvOrderManageActualLabel.setText(Utils.getResourceString(R.string.shi_ji_cheng_jiao));

                if (position > 0) {
                    orderManageCurrAdapterViewHolder.viewTopLine.setVisibility(View.GONE);
                }

                break;
            case 2:
                //我的成交
                VolVO.DataBean currVol = volData.get(position);
                String[] volSplit = currVol.getMarket().split("_");
                OrderManageCurrAdapterViewHolder volHolder = (OrderManageCurrAdapterViewHolder) holder;
                String str = null;
                switch (currVol.getSide()) {
                    case 1:
                        volHolder.tvOrderManageDirectionText.setText(Utils.getResourceString(R.string.sell));
                        volHolder.tvOrderManageDirectionText.setTextColor(Utils.getResourceColor(context, R.color.fee6a5e));
                        str = currVol.getMarket().split("_")[1];
                        break;
                    case 2:
                        volHolder.tvOrderManageDirectionText.setText(Utils.getResourceString(R.string.buy));
                        volHolder.tvOrderManageDirectionText.setTextColor(Utils.getResourceColor(context, R.color.f50b577));
                        str = currVol.getMarket().split("_")[0];
                        break;
                }

                volHolder.tvOrderManageCannel.setText(Utils.getResourceString(R.string.shou_xu_fei) + currVol.getFee() + str.toUpperCase());
                volHolder.tvOrderManageCannel.setTextColor(Utils.getResourceColor(context, R.color.bafb3b0_42ffffff));

                Utils.setFontSpan(volHolder.tvOrderManageMarket, new String[]{currVol.getMarket().toUpperCase().split("_")[0], "/" + currVol.getMarket().toUpperCase().split("_")[1]}, new Integer[]{R.color.b333333_ffffff, R.color.b666666_99ffffff});

                volHolder.tvOrderManageMarket.setText(currVol.getMarket().replace("_", "/").toUpperCase());

                volHolder.tvOrderManagePrice.setText(currVol.getPrice());
                volHolder.tvOrderManagePriceLabel.setText(Utils.getResourceString(R.string.jia_ge) + "(" + volSplit[1].toUpperCase() + ")");

                volHolder.tvOrderManageAmount.setText(String.valueOf(currVol.getAmount()));
                volHolder.tvOrderManageAmountLabel.setText(Utils.getResourceString(R.string.shu_liang) + "(" + volSplit[0].toUpperCase() + ")");


                String value = String.valueOf(Double.parseDouble(currVol.getPrice()) * Double.parseDouble(currVol.getAmount()));


                volHolder.tvOrderManageActual.setText(Utils.getScientificCountingMethodAfterData(Double.valueOf(value), Integer.valueOf(ExTickerDao.getTickerByCode(currVol.getMarket()).getMoney_decimal())));

                volHolder.tvOrderManageActualLabel.setText(Utils.getResourceString(R.string.zong_e) + "(" + volSplit[1].toUpperCase() + ")");
                volHolder.tvOrderManageTime.setText(Utils.long2String(currVol.getTime(), 3));
                if (position > 0) {
                    volHolder.viewTopLine.setVisibility(View.GONE);
                }
                break;
            case 1:
                //历史
                OrderManageVO.DataBean hostory = entrustData.get(position);
                String[] hostorySplit = hostory.getMarket().split("_");
                OrderManageHistoryAdapterViewHolder orderManageHistoryAdapterViewHolder = (OrderManageHistoryAdapterViewHolder) holder;

                boolean isBtc = false;
                switch (hostory.getSide()) {
                    case 1:
                        orderManageHistoryAdapterViewHolder.tvOrderManageDirectionText.setText(Utils.getResourceString(R.string.sell));
                        orderManageHistoryAdapterViewHolder.tvOrderManageDirectionText.setTextColor(Utils.getResourceColor(context, R.color.fee6a5e));

                        isBtc = "gzh".equals(hostory.getMarket().split("_")[1]);

                        break;
                    case 2:
                        orderManageHistoryAdapterViewHolder.tvOrderManageDirectionText.setText(Utils.getResourceString(R.string.buy));
                        orderManageHistoryAdapterViewHolder.tvOrderManageDirectionText.setTextColor(Utils.getResourceColor(context, R.color.f50b577));
                        isBtc = "gzh".equals(hostory.getMarket().split("_")[0]);
                        break;
                }

                switch (hostory.getState()) {
                    case 0:
                        orderManageHistoryAdapterViewHolder.tvOrdermanageCannel.setText(Utils.getResourceString(R.string.xia_dan_wei_que_ren));
                        break;
                    case 1:
                        orderManageHistoryAdapterViewHolder.tvOrdermanageCannel.setText(Utils.getResourceString(R.string.xia_dan_cheng_gong));
                        break;
                    case 2:
                        orderManageHistoryAdapterViewHolder.tvOrdermanageCannel.setText(Utils.getResourceString(R.string.bu_fen_cheng_jiao));
                        break;
                    case 3:
                        orderManageHistoryAdapterViewHolder.tvOrdermanageCannel.setText(Utils.getResourceString(R.string.yi_che_dan));
                        orderManageHistoryAdapterViewHolder.tvOrdermanageCannel.setTextColor(Utils.getResourceColor(context, R.color.bd4d6d5_33ffffff));
                        break;
                    case 4:
                        orderManageHistoryAdapterViewHolder.tvOrdermanageCannel.setText(Utils.getResourceString(R.string.yi_cheng_jiao));
                        orderManageHistoryAdapterViewHolder.tvOrdermanageCannel.setTextColor(Utils.getResourceColor(context, R.color.b999999_e6ffffff));
                        break;
                }

                Utils.setFontSpan(orderManageHistoryAdapterViewHolder.tvOrderManageMarket, new String[]{hostory.getMarket().toUpperCase().split("_")[0], "/" + hostory.getMarket().toUpperCase().split("_")[1]}, new Integer[]{R.color.b333333_ffffff, R.color.b666666_99ffffff});


                if (TextUtils.isEmpty(hostory.getPrice()) || TextUtils.isEmpty(hostory.getPrice().trim())) {
                    orderManageHistoryAdapterViewHolder.tvOrderManagePrice.setText("---");
                } else {
                    orderManageHistoryAdapterViewHolder.tvOrderManagePrice.setText(hostory.getPrice());
                }

                DecimalFormat decimalFormat = new DecimalFormat("###################.###########");

                orderManageHistoryAdapterViewHolder.tvOrderManagePriceLabel.setText(Utils.getResourceString(R.string.jia_ge) + "(" + hostorySplit[1].toUpperCase() + ")");

                orderManageHistoryAdapterViewHolder.tvOrderManageAmount.setText(String.valueOf(hostory.getAmount()));
                orderManageHistoryAdapterViewHolder.tvOrderManageAmountLabel.setText(Utils.getResourceString(R.string.shu_liang) + "(" + hostorySplit[0].toUpperCase() + ")");


                if (TextUtils.isEmpty(hostory.getDeal_stock()) || Double.parseDouble(hostory.getDeal_stock()) == 0) {
                    orderManageHistoryAdapterViewHolder.tvOrderManageAverage.setText("0");
                } else {
                    orderManageHistoryAdapterViewHolder.tvOrderManageAverage.setText(decimalFormat.format(Double.parseDouble(Utils.getScientificCountingMethodAfterData(Double.parseDouble(hostory.getDeal_money()) / Double.parseDouble(hostory.getDeal_stock()), Utils.getTickerShowScale(ExTickerDao.getTickerByCode(hostory.getMarket()))))));
                }
                orderManageHistoryAdapterViewHolder.tvOrderManageAverageLabel.setText(Utils.getResourceString(R.string.jun_jia) + "(" + hostorySplit[1].toUpperCase() + ")");

                orderManageHistoryAdapterViewHolder.tvOrderManageDeal.setText(String.valueOf(hostory.getDeal_stock()));
                orderManageHistoryAdapterViewHolder.tvOrderManageDealLabel.setText(Utils.getResourceString(R.string.yi_cheng_jiao) + "(" + hostorySplit[0].toUpperCase() + ")");

                orderManageHistoryAdapterViewHolder.tvOrderManageTotalPrice.setText(decimalFormat.format(Double.parseDouble(Utils.getScientificCountingMethodAfterData(Double.parseDouble(hostory.getDeal_money()),
                        Integer.valueOf(ExTickerDao.getTickerByCode(hostory.getMarket()).getMoney_decimal())))));

                orderManageHistoryAdapterViewHolder.tvOrderManageTotalPriceLabel.setText(Utils.getResourceString(R.string.zong_e) + "(" + hostorySplit[1].toUpperCase() + ")");

                orderManageHistoryAdapterViewHolder.tvOrderManageTime.setText(Utils.long2String(hostory.getUpdate_time(), 3));

                if (position > 0) {
                    orderManageHistoryAdapterViewHolder.viewTopLine.setVisibility(View.GONE);
                }

                switch (hostory.getT()) {
                    case 1:
                        //限价
                        orderManageHistoryAdapterViewHolder.tvOrderHistoryStatus.setText(Utils.getResourceString(R.string.xian));
                        orderManageHistoryAdapterViewHolder.tvOrderHistoryStatus.setBackgroundColor(Utils.getResourceColor(context, R.color.f6AA07F));
                        orderManageHistoryAdapterViewHolder.ivOrderHistoryStatusRight.setImageResource(R.drawable.my_icon_xjjy);
                        break;
                    case 2:
                        //市价
                        if (hostory.getSide() == 2) {
                            orderManageHistoryAdapterViewHolder.tvOrderManageAmountLabel.setText(Utils.getResourceString(R.string.shu_liang) + "(" + hostorySplit[1].toUpperCase() + ")");
                        }
                        orderManageHistoryAdapterViewHolder.tvOrderHistoryStatus.setText(Utils.getResourceString(R.string.shi));
                        orderManageHistoryAdapterViewHolder.tvOrderHistoryStatus.setBackgroundColor(Utils.getResourceColor(context, R.color.ff1c073));
                        orderManageHistoryAdapterViewHolder.ivOrderHistoryStatusRight.setImageResource(R.drawable.my_icon_sjjy);
                        break;
                }

                break;
            case 3:
            case 4:
                DepositWithdrawalVO.DataBean dataBean = depositWithdrawalData.get(position);

                OrderManageMoneyOperateAdapterViewHolder orderManageMoneyOperateAdapterViewHolder = (OrderManageMoneyOperateAdapterViewHolder) holder;

                orderManageMoneyOperateAdapterViewHolder.tvOrderManageMoneyCoinName.setText(dataBean.getCoin_code().toUpperCase());

                orderManageMoneyOperateAdapterViewHolder.tvOrderManageMoneyId.setVisibility(View.GONE);

//                if (position != 0) {
//                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                    lp.setMargins(0, 0, 0, 0);
//                    orderManageMoneyOperateAdapterViewHolder.rlOrderManageParent.setLayoutParams(lp);
//                }

                switch (dataBean.getStatus()) {
                    case 1:
                        if (type == 3) {
                            //已到账
                            orderManageMoneyOperateAdapterViewHolder.tvOrderManageMoneyStatus.setText(Utils.getResourceString(R.string.yi_dao_zhang));
                            orderManageMoneyOperateAdapterViewHolder.tvOrderManageMoneyStatus.setTextColor(Utils.getResourceColor(context, R.color.f398155));
                            orderManageMoneyOperateAdapterViewHolder.tvOrderManageMoneyAmount.setText(String.valueOf(dataBean.getAmount()));
                        } else {
                            //未审核
                            orderManageMoneyOperateAdapterViewHolder.tvOrderManageMoneyStatus.setText(Utils.getResourceString(R.string.dai_shen_he));
                            orderManageMoneyOperateAdapterViewHolder.tvOrderManageMoneyStatus.setTextColor(Utils.getResourceColor(context, R.color.f398155));
                            orderManageMoneyOperateAdapterViewHolder.tvOrderManageMoneyAmount.setText(String.valueOf(dataBean.getAmount()));
                        }
                        break;
                    case 2:
                        if (type == 3) {
                            //未到账
                            orderManageMoneyOperateAdapterViewHolder.tvOrderManageMoneyStatus.setText(Utils.getResourceString(R.string.wei_que_ren));
                            orderManageMoneyOperateAdapterViewHolder.tvOrderManageMoneyStatus.setTextColor(Utils.getResourceColor(context, R.color.beeab45));
                            orderManageMoneyOperateAdapterViewHolder.tvOrderManageMoneyAmount.setText(String.valueOf(dataBean.getAmount()));
                        } else {
                            //已审核
                            orderManageMoneyOperateAdapterViewHolder.tvOrderManageMoneyStatus.setText(Utils.getResourceString(R.string.yi_shen_he));
                            orderManageMoneyOperateAdapterViewHolder.tvOrderManageMoneyStatus.setTextColor(Utils.getResourceColor(context, R.color.f50b577));
                            orderManageMoneyOperateAdapterViewHolder.tvOrderManageMoneyAmount.setText(String.valueOf(dataBean.getAmount()));
                        }
                        break;
                    case 3:
                        //审核失败
                        if (type == 3) {
                            //已到账
                            orderManageMoneyOperateAdapterViewHolder.tvOrderManageMoneyStatus.setText(Utils.getResourceString(R.string.yi_dao_zhang));
                            orderManageMoneyOperateAdapterViewHolder.tvOrderManageMoneyStatus.setTextColor(Utils.getResourceColor(context, R.color.f50b577));
                            orderManageMoneyOperateAdapterViewHolder.tvOrderManageMoneyAmount.setText(String.valueOf(dataBean.getAmount()));
                        } else {
                            orderManageMoneyOperateAdapterViewHolder.tvOrderManageMoneyStatus.setText(Utils.getResourceString(R.string.shen_he_shi_bai));
                            orderManageMoneyOperateAdapterViewHolder.tvOrderManageMoneyStatus.setTextColor(Utils.getResourceColor(context, R.color.fee6a5e));
                            orderManageMoneyOperateAdapterViewHolder.tvOrderManageMoneyAmount.setText(String.valueOf(dataBean.getAmount()));
                        }
                        break;
                    case 4:
                        //已发送
                        orderManageMoneyOperateAdapterViewHolder.tvOrderManageMoneyStatus.setText(Utils.getResourceString(R.string.yi_fa_song));
                        orderManageMoneyOperateAdapterViewHolder.tvOrderManageMoneyStatus.setTextColor(Utils.getResourceColor(context, R.color.f398155));
                        orderManageMoneyOperateAdapterViewHolder.tvOrderManageMoneyAmount.setText(String.valueOf(dataBean.getAmount()));
                        break;
                    case 5:
                        //已撤销
                        orderManageMoneyOperateAdapterViewHolder.tvOrderManageMoneyStatus.setText(Utils.getResourceString(R.string.yi_che_xiao));
                        orderManageMoneyOperateAdapterViewHolder.tvOrderManageMoneyStatus.setTextColor(Utils.getResourceColor(context, R.color.bd4d6d5_33ffffff));
                        orderManageMoneyOperateAdapterViewHolder.tvOrderManageMoneyAmount.setText(String.valueOf(dataBean.getAmount()));
                        break;
                }

                if (type == 3) {
                    orderManageMoneyOperateAdapterViewHolder.tvOrderManageMoneyTime.setText(Utils.long2String(dataBean.getUpdate_time(), 3));
                } else {
                    orderManageMoneyOperateAdapterViewHolder.tvOrderManageMoneyTime.setText(Utils.long2String(dataBean.getUpdateTime(), 3));
                }

                break;
            case 5:
                //站内记录
                StationVO.DataBean stationBean = stationData.get(position);
                OrderManageMoneyOperateAdapterViewHolder stationHolder = (OrderManageMoneyOperateAdapterViewHolder) holder;

                stationHolder.tvOrderManageMoneyCoinName.setText(stationBean.getCoin_code().toUpperCase());

                String status;
                if (UTEXApplication.getLoginUser().getUid() == stationBean.getFrom_user()) {
                    //转出
                    status = Utils.getResourceString(R.string.zhuan_chu);
                    stationHolder.tvOrderManageMoneyId.setText("UID[" + stationBean.getTo_user() + "]");
                    stationHolder.tvOrderManageMoneyAmount.setText("-" + Utils.getScientificCountingMethodAfterData(stationBean.getAmount(), 8));
                    stationHolder.tvOrderManageMoneyStatus.setTextColor(Utils.getResourceColor(context, R.color.fee6a5e));
                } else {
                    //转入
                    status = Utils.getResourceString(R.string.zhuan_ru);
                    stationHolder.tvOrderManageMoneyId.setText("UID[" + stationBean.getFrom_user() + "]");
                    stationHolder.tvOrderManageMoneyAmount.setText("+" + Utils.getScientificCountingMethodAfterData(stationBean.getAmount(), 8));
                    stationHolder.tvOrderManageMoneyStatus.setTextColor(Utils.getResourceColor(context, R.color.f50b577));
                }

                stationHolder.tvOrderManageMoneyStatus.setText(status);

                stationHolder.tvOrderManageMoneyTime.setText(Utils.long2String(stationBean.getCreate_time(), 3));
                stationHolder.tvOrderManageMoneyMore.setVisibility(View.GONE);

                break;
        }
    }

    @Override
    public int getItemCount() {
        switch (type) {
            case 0:
                return Utils.checkList(entrustData);
            case 2:
                return Utils.checkList(volData);
            case 1:
                return Utils.checkList(entrustData);
            case 3:
            case 4:
                return Utils.checkList(depositWithdrawalData);
            case 5:
                return Utils.checkList(stationData);
        }
        return 0;
    }

    public void setEntrustData(List<OrderManageVO.DataBean> entrustData, boolean isLoadMore) {
        if (isLoadMore) {
            if (this.entrustData == null) {
                this.entrustData = new ArrayList<>();
            }
            this.entrustData.addAll(entrustData);
        } else {
            this.entrustData = entrustData;
        }
        notifyDataSetChanged();
    }

    public void setVolData(List<VolVO.DataBean> data, boolean isLoadMore) {
        if (isLoadMore) {

            if (this.volData == null) {
                this.volData = new ArrayList<>();
            }
            this.volData.addAll(data);
        } else {
            this.volData = data;

        }

        notifyDataSetChanged();
    }

    public void setDepositWithdrawalData(List<DepositWithdrawalVO.DataBean> depositWithdrawalData, boolean isLoadMore) {
        if (isLoadMore) {
            if (this.depositWithdrawalData == null) {
                this.depositWithdrawalData = new ArrayList<>();
            }

            this.depositWithdrawalData.addAll(depositWithdrawalData);

        } else {
            this.depositWithdrawalData = depositWithdrawalData;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void removeItem() {
        if (entrustData != null && entrustData.size() > currEntrustPosition) {
            entrustData.remove(currEntrustPosition);
            notifyDataSetChanged();
        }
    }

    public void removeWithdrawal() {
        if (depositWithdrawalData != null && depositWithdrawalData.size() > currEntrustPosition) {
            depositWithdrawalData.remove(currEntrustPosition);
            notifyDataSetChanged();
        }
    }

    private int currEntrustPosition;

    public List<OrderManageVO.DataBean> getEntrust() {
        return entrustData;
    }

    public void removeItemCurrPage(String user_order_id) {
        if (entrustData != null) {
            for (OrderManageVO.DataBean dataBean : entrustData) {
                if (dataBean.getUser_order_id().equals(user_order_id)) {
                    entrustData.remove(dataBean);
                    break;
                }
            }
            notifyDataSetChanged();
        }
    }

    public void setStationList(List<StationVO.DataBean> data, boolean isLoadMore) {
        if (isLoadMore) {
            if (this.stationData == null) {
                this.stationData = new ArrayList<>();
            }
            this.stationData.addAll(data);
        } else {
            this.stationData = data;
        }

        notifyDataSetChanged();
    }

    class OrderManageCurrAdapterViewHolder extends RecyclerView.ViewHolder {

        private final int position;

        @BindView(R.id.tv_order_manage_direction_text)
        TextView tvOrderManageDirectionText;

        @BindView(R.id.tv_order_manage_market)
        TextView tvOrderManageMarket;

        @BindView(R.id.tv_order_manage_cannel)
        TextView tvOrderManageCannel;

        @BindView(R.id.tv_order_manage_price)
        TextView tvOrderManagePrice;

        @BindView(R.id.tv_order_manage_price_label)
        TextView tvOrderManagePriceLabel;

        @BindView(R.id.tv_order_manage_amount)
        TextView tvOrderManageAmount;

        @BindView(R.id.tv_order_manage_amount_label)
        TextView tvOrderManageAmountLabel;

        @BindView(R.id.tv_order_manage_actual)
        TextView tvOrderManageActual;

        @BindView(R.id.tv_order_manage_actual_label)
        TextView tvOrderManageActualLabel;

        @BindView(R.id.tv_order_manage_time)
        TextView tvOrderManageTime;

        @BindView(R.id.view_top_line)
        View viewTopLine;

        public OrderManageCurrAdapterViewHolder(View itemView, int position) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.position = position;
        }

        @OnClick({R.id.tv_order_manage_cannel})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_order_manage_cannel:
                    if (entrustData != null && orderClickListener != null) {
                        OrderManageVO.DataBean dataBean = entrustData.get(position);
                        currEntrustPosition = position;
                        orderClickListener.cannel(dataBean.getUser_order_id(), dataBean.getMarket(), dataBean.getUser_id());
                    }
                    break;
            }
        }
    }

    private OrderClickListener orderClickListener;

    public void setOrderClickListener(OrderClickListener orderClickListener) {
        this.orderClickListener = orderClickListener;
    }


    interface OrderClickListener {
        void cannel(String user_order_id, String market, int user_id);
    }

    class OrderManageHistoryAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_order_manage_direction_text)
        TextView tvOrderManageDirectionText;

        @BindView(R.id.tv_order_manage_price)
        TextView tvOrderManagePrice;

        @BindView(R.id.tv_order_manage_price_label)
        TextView tvOrderManagePriceLabel;

        @BindView(R.id.tv_order_manage_amount)
        TextView tvOrderManageAmount;

        @BindView(R.id.tv_order_manage_amount_label)
        TextView tvOrderManageAmountLabel;

        @BindView(R.id.tv_order_manage_average)
        TextView tvOrderManageAverage;

        @BindView(R.id.tv_order_manage_average_label)
        TextView tvOrderManageAverageLabel;

        @BindView(R.id.tv_order_manage_deal)
        TextView tvOrderManageDeal;

        @BindView(R.id.tv_order_manage_deal_label)
        TextView tvOrderManageDealLabel;

        @BindView(R.id.tv_order_manage_total_price)
        TextView tvOrderManageTotalPrice;

        @BindView(R.id.tv_order_manage_total_price_label)
        TextView tvOrderManageTotalPriceLabel;

        @BindView(R.id.tv_order_manage_time)
        TextView tvOrderManageTime;

        @BindView(R.id.tv_order_manage_cannel)
        TextView tvOrdermanageCannel;

        @BindView(R.id.view_top_line)
        View viewTopLine;

        @BindView(R.id.tv_order_manage_market)
        TextView tvOrderManageMarket;

        @BindView(R.id.tv_order_history_status)
        TextView tvOrderHistoryStatus;

        @BindView(R.id.iv_order_history_status_ritht)
        ImageView ivOrderHistoryStatusRight;

        public OrderManageHistoryAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    class OrderManageMoneyOperateAdapterViewHolder extends RecyclerView.ViewHolder {

        private final int position;

        @BindView(R.id.rl_order_manage_parent)
        RelativeLayout rlOrderManageParent;

        @BindView(R.id.tv_order_manage_coin_name)
        TextView tvOrderManageMoneyCoinName;

        @BindView(R.id.tv_order_manage_status)
        TextView tvOrderManageMoneyStatus;

        @BindView(R.id.tv_order_manage_money_amount)
        TextView tvOrderManageMoneyAmount;

        @BindView(R.id.pb_order_manage_money)
        ProgressBar pbOrderManageMoney;

        @BindView(R.id.tv_order_manage_money_time)
        TextView tvOrderManageMoneyTime;

        @BindView(R.id.tv_order_manage_money_more)
        TextView tvOrderManageMoneyMore;

        @BindView(R.id.tv_order_manage_money_id)
        TextView tvOrderManageMoneyId;

        public OrderManageMoneyOperateAdapterViewHolder(View itemView, int viewType) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.position = viewType;
        }

        @OnClick({R.id.rl_order_manage_parent})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rl_order_manage_parent:
                    if (type == 5) {
                        return;
                    }
                    Intent intent = new Intent(view.getContext(), WithdrawalDetailActivity.class);
                    DepositWithdrawalVO.DataBean dataBean = depositWithdrawalData.get(position);
                    intent.putExtra(FiledConstants.BEAN, dataBean);
                    switch (type) {
                        case 3:
                            //充值
                            intent.putExtra(FiledConstants.TYPE, 1);
                            break;
                        case 4:
                            //提现
                            intent.putExtra(FiledConstants.TYPE, 2);
                            break;
                    }

                    currEntrustPosition = position;
                    if (withdrawalListener != null) {
                        withdrawalListener.onclick(intent);
                    }
                    break;
            }
        }
    }

    private WithdrawalListener withdrawalListener;

    public void setWithdrawalListener(WithdrawalListener withdrawalListener) {
        this.withdrawalListener = withdrawalListener;
    }

    interface WithdrawalListener {
        void onclick(Intent intent);
    }

}
