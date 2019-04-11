package com.utex.mvp.order.view;

import com.utex.mvp.order.bean.DepositWithdrawalVO;
import com.utex.mvp.order.bean.OrderManageVO;
import com.utex.mvp.order.bean.StationVO;
import com.utex.mvp.order.bean.VolVO;

import java.util.List;

/**
 * Created by Demon on 2018/6/27.
 */
public interface IOrderManageView {

    void getCurrEntrustSuccess(List<OrderManageVO.DataBean> data);

    void getHostoryEntrustSuccess(List<OrderManageVO.DataBean> data);

    void getMineVolSuccess(List<VolVO.DataBean> data);

    void setBaseText(String text);

    void getDepositListSuccess(List<DepositWithdrawalVO.DataBean> data);

    void getWithdrawalListSuccess(List<DepositWithdrawalVO.DataBean> data);

    void cannelOrderSuccess();

    void cannelCurrPageOrderSuccess(String user_order_id);

    void getStationListSuccess(List<StationVO.DataBean> data);

}
