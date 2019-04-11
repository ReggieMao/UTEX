package com.utex.mvp.order.model;

import com.utex.data.ApiService;
import com.utex.mvp.order.presenter.IOrderManagePresenter;

/**
 * Created by Demon on 2018/6/27.
 */
public interface IOrderManageModel {
    void getCurrEntrust(ApiService apiService, String market, Integer status, boolean isHide, int page, IOrderManagePresenter iOrderManagePresenter);

    void getHostoryEntrust(ApiService apiService, String market, Integer status, boolean isHide, int page, IOrderManagePresenter iOrderManagePresenter);

    void getMineVol(ApiService apiService, boolean isHide, String market, Integer status, int page, int pageSize, IOrderManagePresenter iOrderManagePresenter);

    void getDepositList(ApiService apiService, String market, Integer status, int page, IOrderManagePresenter iOrderManagePresenter);

    void getWithdrawalList(ApiService apiService, String market, Integer status, int page, IOrderManagePresenter iOrderManagePresenter);

    void cannelOrder(ApiService apiService, String user_order_id, String market, int user_id, IOrderManagePresenter iOrderManagePresenter);

    void getStationList(ApiService apiService, String market, Integer status, int page, IOrderManagePresenter iOrderManagePresenter);

}
