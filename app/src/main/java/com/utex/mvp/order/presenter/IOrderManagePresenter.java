package com.utex.mvp.order.presenter;

import android.widget.TextView;

import com.utex.mvp.order.bean.DepositWithdrawalVO;
import com.utex.mvp.order.bean.OrderManageVO;
import com.utex.mvp.order.bean.StationVO;
import com.utex.mvp.order.bean.VolVO;

import java.util.List;

/**
 * Created by Demon on 2018/6/27.
 */
public interface IOrderManagePresenter {

    void getData(int type, String market, Integer status, boolean isHide, int page, int pageSize);

    void getCurrEntrustSuccess(List<OrderManageVO.DataBean> data);

    void getHostoryEntrustSuccess(List<OrderManageVO.DataBean> data);

    void getMineVolSuccess(List<VolVO.DataBean> data);

    void showMarket(TextView resView);

    void getDepositListSuccess(List<DepositWithdrawalVO.DataBean> data);

    void getWithdrawalListSuccess(List<DepositWithdrawalVO.DataBean> data);

    void cannelOrder(String user_order_id, String market, int user_id);

    void requestFail(String str);

    void cannelCurrPageOrder(List<OrderManageVO.DataBean> entrust);

    void cannelOrderSuccess(String user_order_id);

    void getStationListSuccess(List<StationVO.DataBean> data);

}
