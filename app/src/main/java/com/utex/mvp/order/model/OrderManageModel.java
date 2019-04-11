package com.utex.mvp.order.model;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.utex.data.ApiService;
import com.utex.mvp.order.bean.DepositWithdrawalVO;
import com.utex.mvp.order.bean.OrderManageVO;
import com.utex.mvp.order.bean.StationVO;
import com.utex.mvp.order.bean.VolVO;
import com.utex.mvp.order.presenter.IOrderManagePresenter;
import com.utex.mvp.trad.bean.CannelOrderDto;
import com.utex.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Demon on 2018/6/27.
 */
public class OrderManageModel implements IOrderManageModel {

    @Override
    public void getCurrEntrust(ApiService apiService, String market, Integer status, boolean isHide, int page, final IOrderManagePresenter iOrderManagePresenter) {
        int state;
        if (isHide) {
            state = 3;
        } else {
            state = 1;
        }
        if (market == null || TextUtils.isEmpty(market.trim()) || "_".equals(market)) {
            market = null;
        }

        apiService.getCurrEntrust(state, market, status, page)
                .enqueue(new Callback<OrderManageVO>() {
                    @Override
                    public void onResponse(Call<OrderManageVO> call, Response<OrderManageVO> response) {
                        if (response.code() == 200) {
                            iOrderManagePresenter.getCurrEntrustSuccess(response.body().getData());
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<OrderManageVO> call, Throwable t) {

                    }
                });
    }

    @Override
    public void getHostoryEntrust(ApiService apiService, String market, Integer status, boolean isHide, int page, final IOrderManagePresenter iOrderManagePresenter) {
        int state;
        if (isHide) {
            state = 3;
        } else {
            state = 2;
        }

        if (market == null || TextUtils.isEmpty(market.trim()) || "_".equals(market)) {
            market = null;
        }

        apiService.getHostoryEntrust(state, market, status, page)
                .enqueue(new Callback<OrderManageVO>() {
                    @Override
                    public void onResponse(Call<OrderManageVO> call, Response<OrderManageVO> response) {
                        if (response.code() == 200) {
                            iOrderManagePresenter.getHostoryEntrustSuccess(response.body().getData());
                        } else {
                            Log.e("TAG", "aaaaa");
                        }
                    }

                    @Override
                    public void onFailure(Call<OrderManageVO> call, Throwable t) {
                        Log.e("TAG", "aaaaa");
                    }
                });
    }

    @Override
    public void getMineVol(ApiService apiService, boolean isHide, String market, Integer status, int page, int pageSize, final IOrderManagePresenter iOrderManagePresenter) {
        if (market == null || TextUtils.isEmpty(market.trim()) || "_".equals(market)) {
            market = null;
        }

        apiService.getMineVol(market, status, page, pageSize).enqueue(new Callback<VolVO>() {
            @Override
            public void onResponse(Call<VolVO> call, Response<VolVO> response) {
                if (response.code() == 200) {
                    iOrderManagePresenter.getMineVolSuccess(response.body().getData());
                } else {
                    Log.e("TAG", "aaaaa");
                }
            }

            @Override
            public void onFailure(Call<VolVO> call, Throwable t) {
                Log.e("TAG", "aaaaa");

            }
        });
    }

    @Override
    public void getDepositList(ApiService apiService, String market, Integer status, int page, final IOrderManagePresenter iOrderManagePresenter) {
        apiService.getDepositList(status, market, page)
                .enqueue(new Callback<DepositWithdrawalVO>() {
                    @Override
                    public void onResponse(Call<DepositWithdrawalVO> call, Response<DepositWithdrawalVO> response) {
                        if (response.code() == 200) {
                            iOrderManagePresenter.getDepositListSuccess(response.body().getData());
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<DepositWithdrawalVO> call, Throwable t) {

                    }
                });
    }

    @Override
    public void getWithdrawalList(ApiService apiService, String market, Integer status, int page, final IOrderManagePresenter iOrderManagePresenter) {
        apiService.getWithdrawalList(status, market, page)
                .enqueue(new Callback<DepositWithdrawalVO>() {
                    @Override
                    public void onResponse(Call<DepositWithdrawalVO> call, Response<DepositWithdrawalVO> response) {
                        if (response.code() == 200) {
                            iOrderManagePresenter.getWithdrawalListSuccess(response.body().getData());
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<DepositWithdrawalVO> call, Throwable t) {

                    }
                });
    }

    @Override
    public void cannelOrder(ApiService apiService, String user_order_id, String market, int user_id, final IOrderManagePresenter iOrderManagePresenter) {
        apiService.cannelOrder(new CannelOrderDto(user_id, market, user_order_id))
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            //撤销成功
                            iOrderManagePresenter.cannelOrderSuccess(user_order_id);
                        } else {
                            //撤销失败
                            String str = Utils.doErrorBody(response.errorBody());
                            iOrderManagePresenter.requestFail(str);
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        iOrderManagePresenter.requestFail("");
                    }
                });
    }

    @Override
    public void getStationList(ApiService apiService, String market, Integer status, int page, IOrderManagePresenter iOrderManagePresenter) {
        apiService.getStationList(page, status, market)
                .enqueue(new Callback<StationVO>() {
                    @Override
                    public void onResponse(Call<StationVO> call, Response<StationVO> response) {
                        if (response.code() == 200) {
                            iOrderManagePresenter.getStationListSuccess(response.body().getData());
                        } else {
                            String s = Utils.doErrorBody(response.errorBody());
                            iOrderManagePresenter.requestFail(s);
                        }
                    }

                    @Override
                    public void onFailure(Call<StationVO> call, Throwable t) {

                    }
                });
    }

}
