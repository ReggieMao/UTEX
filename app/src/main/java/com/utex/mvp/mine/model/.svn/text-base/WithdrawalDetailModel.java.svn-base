package com.utex.mvp.mine.model;

import com.alibaba.fastjson.JSONObject;
import com.utex.data.ApiService;
import com.utex.mvp.mine.presenter.IWithdrawalDetailPresenter;
import com.utex.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Demon on 2018/9/10.
 */
public class WithdrawalDetailModel implements IWithdrawalDetailModel {


    @Override
    public void getCoinWalletAddress(ApiService apiService, String coinCode, final IWithdrawalDetailPresenter iWithdrawalDetailPresenter) {
        apiService.getDepositAddress(coinCode)
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            JSONObject data = response.body().getJSONObject("data");
                            String deposit_address = data.getString("deposit_address");
                            String deposit_tips = data.getString("deposit_tips");
                            String tag = data.getString("tag");
                            iWithdrawalDetailPresenter.getDepositAddressSuccess(deposit_address, deposit_tips, tag);
                        } else {
                            String str = Utils.doErrorBody(response.errorBody());
                            iWithdrawalDetailPresenter.fail(str);
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        iWithdrawalDetailPresenter.fail("");
                    }
                });
    }

    @Override
    public void cannelWithdrawal(ApiService apiService, int id, final IWithdrawalDetailPresenter iWithdrawalDetailPresenter) {
        apiService.cannelWithdrawal(String.valueOf(id))
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            iWithdrawalDetailPresenter.cannelWithdrawalSuccess();
                        } else {
                            String s = Utils.doErrorBody(response.errorBody());
                            iWithdrawalDetailPresenter.fail(s);
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        iWithdrawalDetailPresenter.fail("");
                    }
                });
    }
}
