package com.utex.mvp.marketdetail.dagger2;

import com.utex.base.BaseModule;
import com.utex.data.ApiService;
import com.utex.mvp.marketdetail.presenter.IMarketDetailPresenter;
import com.utex.mvp.marketdetail.presenter.MarketDetailPresenter;
import com.utex.mvp.marketdetail.view.IMarketDetailView;
import com.utex.mvp.marketdetail.view.MarketDetailActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by admin on 2017/4/21.
 */
@Module
public class MarketDetailModule extends BaseModule<MarketDetailActivity, IMarketDetailView> {

    public MarketDetailModule(MarketDetailActivity marketDetailActivity) {
        super(marketDetailActivity);
    }

    @Provides
    protected IMarketDetailPresenter marketPresenter(ApiService apiService) {
        return new MarketDetailPresenter(apiService, activity);
    }
}
