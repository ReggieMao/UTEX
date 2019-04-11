package com.utex.mvp.search.model;

import com.alibaba.fastjson.JSONObject;
import com.utex.core.UTEXApplication;
import com.utex.data.ApiService;
import com.utex.mvp.search.presenter.ISearchPresenter;
import com.utex.mvp.trad.bean.OptionalDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Demon on 2018/7/9.
 */
public class SearchModel implements ISearchModel {

    @Override
    public void addOption(ApiService apiService, String coinMarketCode, Long id, final ISearchPresenter iSearchPresenter) {
        apiService.addOptional(new OptionalDTO(UTEXApplication.getLoginUser().getUid(), String.valueOf(id), coinMarketCode))
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            iSearchPresenter.addOptionalSuccess();
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {

                    }
                });

    }

    @Override
    public void removeOption(ApiService apiService, String coinMarketCode, final ISearchPresenter iSearchPresenter) {
        apiService.cannelOptional(coinMarketCode)
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            iSearchPresenter.cannelOptionalSuccess();
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {

                    }
                });
    }
}
