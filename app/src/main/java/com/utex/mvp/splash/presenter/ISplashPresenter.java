package com.utex.mvp.splash.presenter;

import com.utex.bean.TickerDo;

import java.util.List;

/**
 * Created by Demon on 2018/7/23.
 */
public interface ISplashPresenter {
    void getCoinListSuccess(List<TickerDo> data);

    void getCoinListFail();

    void getCoinList();
}
