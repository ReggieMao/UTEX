package com.utex.mvp.splash.model;

import com.utex.bean.TickerDo;
import com.utex.data.ApiService;
import com.utex.mvp.home.bean.CoinListVo;
import com.utex.mvp.splash.presenter.ISplashPresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Demon on 2018/7/23.
 */
public class SplashModel implements ISplashModel {

    @Override
    public void getCoinList(ApiService apiService, final ISplashPresenter iSplashPresenter) {
        //获取币种列表
        apiService.getCoinList().enqueue(new Callback<CoinListVo>() {
            @Override
            public void onResponse(Call<CoinListVo> call, Response<CoinListVo> response) {
                if (response.code() == 200) {
                    List<TickerDo> data = response.body().getData();
                    iSplashPresenter.getCoinListSuccess(data);
                } else {
                    iSplashPresenter.getCoinListFail();
                }

            }

            @Override
            public void onFailure(Call<CoinListVo> call, Throwable t) {
                iSplashPresenter.getCoinListFail();
            }
        });
    }


}
