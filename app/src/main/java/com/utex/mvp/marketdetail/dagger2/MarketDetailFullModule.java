package com.utex.mvp.marketdetail.dagger2;

import com.utex.base.BaseModule;
import com.utex.data.ApiService;
import com.utex.mvp.marketdetail.presenter.IMarketDetailFullPresenter;
import com.utex.mvp.marketdetail.presenter.MarketDetailFullPresenter;
import com.utex.mvp.marketdetail.view.IMarketDetailFullView;
import com.utex.mvp.marketdetail.view.MarketDetailFullActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by admin on 2017/4/21.
 */
@Module
public class MarketDetailFullModule extends BaseModule<MarketDetailFullActivity, IMarketDetailFullView> {

    public MarketDetailFullModule(MarketDetailFullActivity marketDetailFullActivity) {
        super(marketDetailFullActivity);
    }

    @Provides
    protected IMarketDetailFullPresenter marketPresenter(ApiService apiService) {
        return new MarketDetailFullPresenter(apiService, activity);
    }
}
