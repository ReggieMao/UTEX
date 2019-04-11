package com.utex.mvp.market.presenter;

import com.utex.bean.TickerDo;

import java.util.List;

/**
 * Created by Demon on 2018/5/30.
 */
public interface IMarketPresenter {
    void sorttickerList(List<TickerDo> tickerDos, int currStatus);
}
