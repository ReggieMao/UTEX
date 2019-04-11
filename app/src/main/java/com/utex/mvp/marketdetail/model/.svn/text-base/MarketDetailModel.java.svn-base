package com.utex.mvp.marketdetail.model;

import com.alibaba.fastjson.JSONObject;
import com.utex.core.UTEXApplication;
import com.utex.data.ApiService;
import com.utex.mvp.marketdetail.bean.CoinBaseVO;
import com.utex.mvp.marketdetail.presenter.IMarketDetailPresenter;
import com.utex.mvp.trad.bean.OptionalDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Demon on 2018/7/2.
 */
public class MarketDetailModel implements IMarketDetailModel {

    @Override
    public void getCoinInfo(ApiService apiService, String coin, final IMarketDetailPresenter iMarketDetailPresenter) {
        apiService.getCoinInfo(coin).enqueue(new Callback<CoinBaseVO>() {
            @Override
            public void onResponse(Call<CoinBaseVO> call, Response<CoinBaseVO> response) {
                if (response.code() == 200) {
                    iMarketDetailPresenter.getCoinInfoSuccess(response.body());
                } else {

                }
            }

            @Override
            public void onFailure(Call<CoinBaseVO> call, Throwable t) {

            }
        });
    }

    @Override
    public void addOptional(ApiService apiService, String coinMarketCode, Long id, final IMarketDetailPresenter iMarketDetailPresenter) {
        apiService.addOptional(new OptionalDTO(UTEXApplication.getLoginUser().getUid(), String.valueOf(id), coinMarketCode))
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            iMarketDetailPresenter.addOptionalSuccess();
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {

                    }
                });
    }

    @Override
    public void cannelOptional(ApiService apiService, String coinMarketCode, final IMarketDetailPresenter iMarketDetailPresenter) {
        apiService.cannelOptional(coinMarketCode)
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            iMarketDetailPresenter.cannelOptionalSuccess();
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {

                    }
                });
    }

}
