package com.utex.mvp.block.model;

import com.alibaba.fastjson.JSONObject;
import com.utex.data.ApiService;
import com.utex.mvp.block.bean.WithdrawalDTO;
import com.utex.mvp.block.presenter.IBlockPresenter;
import com.utex.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Demon on 2018/7/3.
 */
public class BlockModel implements IBlockModel {

    @Override
    public void getDepositAddress(ApiService apiService, String coinCode, final IBlockPresenter iBlockPresenter) {
        apiService.getDepositAddress(coinCode)
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            JSONObject data = response.body().getJSONObject("data");
                            String deposit_address = data.getString("deposit_address");
                            String deposit_tips = data.getString("deposit_tips");
                            String tag = data.getString("tag");
                            iBlockPresenter.getDepositAddressSuccess(deposit_address, deposit_tips, tag);
                        } else {
                            String str = Utils.doErrorBody(response.errorBody());
                            iBlockPresenter.fail(str);
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        iBlockPresenter.fail("");
                    }
                });
    }

    @Override
    public void getWithdrawalAddress(ApiService apiService, String coinCode, final IBlockPresenter iBlockPresenter) {
        apiService.getWithdrawalAddress(coinCode)
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            JSONObject data = response.body().getJSONObject("data");
                            String withdrawal_tips = data.getString("withdrawal_tips");
                            String withdrawal_fee = data.getString("withdrawal_fee");
                            Boolean withdrawal_switch = data.getBoolean("withdrawal_switch");
                            Double withdrawal_min_amount = data.getDouble("withdrawal_min_amount");
                            boolean tag = data.getBoolean("tag");
                            iBlockPresenter.getWithdrawalAddressSuccess(withdrawal_tips, withdrawal_fee, withdrawal_switch, withdrawal_min_amount, tag);
                        } else {
                            String str = Utils.doErrorBody(response.errorBody());
                            iBlockPresenter.fail(str);
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        iBlockPresenter.fail("");
                    }
                });
    }

    @Override
    public void sendWithdrawalEmail(ApiService apiService, String coinCode, final IBlockPresenter iBlockPresenter) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("coin_name", coinCode);

        apiService.sendWithdrawalEmail(jsonObject)
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            iBlockPresenter.sendEmailSuccess();
                        } else {
                            String str = Utils.doErrorBody(response.errorBody());
                            iBlockPresenter.fail(str);
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        iBlockPresenter.fail("");
                    }
                });
    }

    @Override
    public void sendWithdrawalSms(ApiService apiService, String coinCode, final IBlockPresenter iBlockPresenter) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("coin_name", coinCode);

        apiService.sendWithdrawalSms(jsonObject)
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            iBlockPresenter.sendTelSuccess();
                        } else {
                            String str = Utils.doErrorBody(response.errorBody());
                            iBlockPresenter.fail(str);
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        iBlockPresenter.fail("");
                    }
                });
    }

    @Override
    public void withdrawal(ApiService apiService, WithdrawalDTO withdrawalDTO, final IBlockPresenter iBlockPresenter) {
        apiService.withdrawal(withdrawalDTO)
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            iBlockPresenter.withdrawalSuccess();
                        } else {
                            String str = Utils.doErrorBody(response.errorBody());
                            iBlockPresenter.fail(str);
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        iBlockPresenter.fail("");
                    }
                });


    }

    @Override
    public void transfer(ApiService apiService, int assetId, double amount, String address, IBlockPresenter iBlockPresenter) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("to_user", Integer.parseInt(address));
        jsonObject.put("asset_id", assetId);
        jsonObject.put("amount", String.valueOf(amount));
        apiService.transfer(jsonObject)
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            iBlockPresenter.transferSuccess();
                        } else {
                            String s = Utils.doErrorBody(response.errorBody());
                            iBlockPresenter.fail(s);
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {

                    }
                });
    }

}
