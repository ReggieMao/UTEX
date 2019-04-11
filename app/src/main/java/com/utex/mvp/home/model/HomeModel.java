package com.utex.mvp.home.model;

import com.utex.bean.TickerDo;
import com.utex.data.ApiService;
import com.utex.mvp.home.bean.BannerVo;
import com.utex.mvp.home.bean.CoinListVo;
import com.utex.mvp.home.bean.NoticeVo;
import com.utex.mvp.home.presenter.IHomePresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Demon on 2018/5/29.
 */
public class HomeModel implements IHomeModel {

    @Override
    public void getCoinList(ApiService apiService, final IHomePresenter iHomePresenter) {
        //获取币种列表
        apiService.getCoinList().enqueue(new Callback<CoinListVo>() {
            @Override
            public void onResponse(Call<CoinListVo> call, Response<CoinListVo> response) {
                if (response.code() == 200) {
                    List<TickerDo> data = response.body().getData();
                    iHomePresenter.getCoinListSuccess(data);
                } else {
                    iHomePresenter.getCoinListFail();
                }
            }

            @Override
            public void onFailure(Call<CoinListVo> call, Throwable t) {
                iHomePresenter.getCoinListFail();
            }
        });
    }

    @Override
    public void getBanners(ApiService apiService, String countryId, final IHomePresenter iHomePresenter) {
        apiService.getBanner(countryId)
                .enqueue(new Callback<BannerVo>() {
                    @Override
                    public void onResponse(Call<BannerVo> call, Response<BannerVo> response) {
                        if (response.code() == 200) {
                            iHomePresenter.getBannersSuccess(response.body().getData());
                        }
                    }

                    @Override
                    public void onFailure(Call<BannerVo> call, Throwable t) {

                    }
                });
    }

    @Override
    public void getNotice(ApiService apiService, String countryId, final IHomePresenter iHomePresenter) {
        apiService.getNotice(countryId).enqueue(new Callback<NoticeVo>() {
            @Override
            public void onResponse(Call<NoticeVo> call, Response<NoticeVo> response) {
                if (response.code() == 200) {
                    iHomePresenter.getNoticeSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<NoticeVo> call, Throwable t) {

            }
        });
    }

}
