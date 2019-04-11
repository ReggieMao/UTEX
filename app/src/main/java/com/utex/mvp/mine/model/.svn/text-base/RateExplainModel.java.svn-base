package com.utex.mvp.mine.model;

import com.utex.data.ApiService;
import com.utex.mvp.mine.bean.WithdrawalFeeVo;
import com.utex.mvp.mine.presenter.IRateExplainPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Demon on 2018/11/1.
 */
public class RateExplainModel implements IRateExplainModel {


    @Override
    public void getRateData(ApiService apiService, IRateExplainPresenter iRateExplainPresenter) {
        apiService.getRateData().enqueue(new Callback<WithdrawalFeeVo>() {
            @Override
            public void onResponse(Call<WithdrawalFeeVo> call, Response<WithdrawalFeeVo> response) {
                if (response.code() == 200) {
                    iRateExplainPresenter.getRateDataSuccess(response.body().getData());
                } else {

                }
            }

            @Override
            public void onFailure(Call<WithdrawalFeeVo> call, Throwable t) {

            }
        });
    }
}
