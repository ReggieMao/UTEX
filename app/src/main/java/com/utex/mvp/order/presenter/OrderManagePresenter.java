package com.utex.mvp.order.presenter;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.utex.R;
import com.utex.data.ApiService;
import com.utex.db.ExTickerDao;
import com.utex.mvp.order.bean.DepositWithdrawalVO;
import com.utex.mvp.order.bean.OrderManageVO;
import com.utex.mvp.order.bean.StationVO;
import com.utex.mvp.order.bean.VolVO;
import com.utex.mvp.order.model.IOrderManageModel;
import com.utex.mvp.order.model.OrderManageModel;
import com.utex.mvp.order.view.IOrderManageView;
import com.utex.mvp.order.view.OrderManageFragment;
import com.utex.utils.BubbleUtils;
import com.utex.utils.Utils;
import com.utex.widget.popuwindow.MineFilterPopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Demon on 2018/6/27.
 */
public class OrderManagePresenter implements IOrderManagePresenter {

    private OrderManageFragment fragment;

    private ApiService apiService;

    private IOrderManageView iOrderManageView;

    private final IOrderManageModel iOrderManageModel;
    private MineFilterPopupWindow mineFilterPopupWindow;

    public OrderManagePresenter(ApiService apiService, OrderManageFragment activity) {
        this.apiService = apiService;
        this.iOrderManageView = activity;
        this.fragment = activity;
        iOrderManageModel = new OrderManageModel();
    }

    @Override
    public void getData(int type, String market, Integer status, boolean isHide, int page, int pageSize) {
        switch (type) {
            case 0:
                //当前委托
                iOrderManageModel.getCurrEntrust(apiService, market, status, isHide, page, this);
                break;
            case 1:
                //历史委托
                iOrderManageModel.getHostoryEntrust(apiService, market, status, isHide, page, this);
                break;
            case 2:
                //我的成交
                iOrderManageModel.getMineVol(apiService, isHide, market, status, page, pageSize, this);
                break;
            case 3:
                //充值记录
                iOrderManageModel.getDepositList(apiService, market, status, page, this);
                break;
            case 4:
                //提现记录
                iOrderManageModel.getWithdrawalList(apiService, market, status, page, this);
                break;
            case 5:
                //站内记录
                iOrderManageModel.getStationList(apiService, market, status, page, this);
                break;
        }
    }

    @Override
    public void getCurrEntrustSuccess(List<OrderManageVO.DataBean> data) {
        iOrderManageView.getCurrEntrustSuccess(data);
    }

    @Override
    public void getHostoryEntrustSuccess(List<OrderManageVO.DataBean> data) {
        iOrderManageView.getHostoryEntrustSuccess(data);
    }

    @Override
    public void getMineVolSuccess(List<VolVO.DataBean> data) {
        iOrderManageView.getMineVolSuccess(data);
    }

    @Override
    public void showMarket(TextView resView) {
        mineFilterPopupWindow = new MineFilterPopupWindow(fragment.getContext(), resView);
        LinearLayout llCoinSelector = mineFilterPopupWindow.getLlCoinSelector();

        ArrayList<String> market = ExTickerDao.getMarket(false);

        for (String str : market) {
            TextView tv = new TextView(resView.getContext());
            tv.setText(str);
            tv.setBackgroundResource(R.drawable.order_coin_selector);
            tv.setTextColor(Utils.getResourceColor(resView.getContext(), R.color.b333333_ffffff));
            tv.setTextSize(BubbleUtils.sp2px(5f));
            tv.setGravity(Gravity.CENTER_VERTICAL);
            tv.setPadding(BubbleUtils.dp2px(8f), 0, 0, 0);
            tv.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            tv.setHeight(BubbleUtils.dp2px(36f));
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView text = (TextView) view;
                    iOrderManageView.setBaseText(text.getText().toString());
                    if (mineFilterPopupWindow != null) {
                        mineFilterPopupWindow.dimss();
                    }
                }
            });
            llCoinSelector.addView(tv);
        }

    }

    @Override
    public void getDepositListSuccess(List<DepositWithdrawalVO.DataBean> data) {
        iOrderManageView.getDepositListSuccess(data);
    }

    @Override
    public void getWithdrawalListSuccess(List<DepositWithdrawalVO.DataBean> data) {
        iOrderManageView.getWithdrawalListSuccess(data);
    }

    @Override
    public void cannelOrder(String user_order_id, String market, int user_id) {
        iOrderManageModel.cannelOrder(apiService, user_order_id, market, user_id, this);
    }

    @Override
    public void cannelOrderSuccess(String user_order_id) {
        if (isCannelCurrPage && index == 0) {
            Utils.showMessage(Utils.getResourceString(R.string.dang_qian_ye_wei_tuo_che_xiao_cheng_gong));
            isCannelCurrPage = true;
        } else if (!isCannelCurrPage) {
            Utils.showMessage(Utils.getResourceString(R.string.che_xiao_cheng_gong));
        }

        index--;
        iOrderManageView.cannelCurrPageOrderSuccess(user_order_id);
    }

    @Override
    public void getStationListSuccess(List<StationVO.DataBean> data) {
        iOrderManageView.getStationListSuccess(data);
    }

    @Override
    public void requestFail(String str) {
        index--;
        Utils.showMessage(str);
    }

    int index = 0;

    boolean isCannelCurrPage;

    @Override
    public void cannelCurrPageOrder(List<OrderManageVO.DataBean> entrust) {
        isCannelCurrPage = true;
        index = entrust.size() - 1;
        for (OrderManageVO.DataBean dataBean : entrust) {
            iOrderManageModel.cannelOrder(apiService, dataBean.getUser_order_id(), dataBean.getMarket(), dataBean.getUser_id(), this);
        }
    }


}
