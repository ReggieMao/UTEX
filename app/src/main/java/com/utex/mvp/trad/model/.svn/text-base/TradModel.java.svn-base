package com.utex.mvp.trad.model;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.utex.core.UTEXApplication;
import com.utex.data.ApiService;
import com.utex.mvp.asset.bean.Asset;
import com.utex.mvp.trad.bean.CannelOrderDto;
import com.utex.mvp.trad.bean.Entrust;
import com.utex.mvp.trad.bean.OptionalDTO;
import com.utex.mvp.trad.bean.OrderDTo;
import com.utex.mvp.trad.presenter.ITradPresenter;
import com.utex.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Demon on 2018/6/4.
 */
public class TradModel implements ITradModel {

    @Override
    public void getCurrMarketEntrust(ApiService apiService, String coinMarketCode, final ITradPresenter iTradPresenter) {
        apiService.getCurrMarketEntrust(1, null, coinMarketCode)
                .enqueue(new Callback<Entrust>() {
                    @Override
                    public void onResponse(Call<Entrust> call, Response<Entrust> response) {
                        try {
                            iTradPresenter.getCurrMarketEntrustSuccess(response.body().getData());
                        } catch (Exception e) {
                            Log.e("TAG", "error");
                        }
                    }

                    @Override
                    public void onFailure(Call<Entrust> call, Throwable t) {
                        Log.e("TAG", t.toString());
                    }
                });
    }

    @Override
    public void getAssetList(ApiService apiService, final ITradPresenter iTradPresenter) {
        apiService.getAssetList()
                .enqueue(new Callback<Asset>() {
                    @Override
                    public void onResponse(Call<Asset> call, Response<Asset> response) {
                        iTradPresenter.getAssetListSuccess(response);
                    }

                    @Override
                    public void onFailure(Call<Asset> call, Throwable t) {

                    }
                });
    }

    @Override
    public void submitDirection0(ApiService apiService, String coin_market_code, String amount, int status, String price, final ITradPresenter iTradPresenter) {
        apiService.submitDirection0(new OrderDTo(coin_market_code, amount, String.valueOf(status), price))
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            iTradPresenter.submitDirection0Success();
                        } else {
                            String str = Utils.doErrorBody(response.errorBody());
                            iTradPresenter.submitDirectionError(str);
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        iTradPresenter.submitDirectionError("");
                    }
                });
    }

    @Override
    public void submitDirection1(ApiService apiService, String coin_market_code, String amount, int status, final ITradPresenter iTradPresenter) {
        apiService.submitDirection1(new OrderDTo(coin_market_code, amount, String.valueOf(status), null))
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            iTradPresenter.submitDirection1Success();
                        } else {
                            String str = Utils.doErrorBody(response.errorBody());
                            iTradPresenter.submitDirectionError(str);
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        iTradPresenter.submitDirectionError("");

                    }
                });
    }

    @Override
    public void cannelOrder(ApiService apiService, int uid, String market, String user_order_id, final ITradPresenter iTradPresenter) {
        apiService.cannelOrder(new CannelOrderDto(uid, market, user_order_id))
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            //撤销成功
                            iTradPresenter.cannelOrderSuccess();
                        } else {
                            //撤销失败
                            String s = Utils.doErrorBody(response.errorBody());
                            iTradPresenter.requestFail(s);
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        iTradPresenter.requestFail("");
                    }
                });
    }

    @Override
    public void addOptional(ApiService apiService, String coinMarketCode, Long id, final ITradPresenter iTradPresenter) {
        apiService.addOptional(new OptionalDTO(UTEXApplication.getLoginUser().getUid(), String.valueOf(id), coinMarketCode))
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            iTradPresenter.addOptionalSuccess();
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {

                    }
                });
    }

    @Override
    public void cannelOptional(ApiService apiService, String coinMarketCode, final ITradPresenter iTradPresenter) {
        apiService.cannelOptional(coinMarketCode)
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            iTradPresenter.cannelOptionalSuccess();
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {

                    }
                });
    }
}
