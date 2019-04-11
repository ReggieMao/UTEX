package com.utex.mvp.market.dagger2;

import com.utex.base.BaseModule;
import com.utex.data.ApiService;
import com.utex.mvp.market.presenter.IMarketPresenter;
import com.utex.mvp.market.presenter.MarketPresenter;
import com.utex.mvp.market.view.IMarketView;
import com.utex.mvp.market.view.MarketFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by admin on 2017/4/21.
 */
@Module
public class MarketModule extends BaseModule<MarketFragment, IMarketView> {

    public MarketModule(MarketFragment marketFragment) {
        super(marketFragment);
    }

    @Provides
    protected IMarketPresenter marketPresenter(ApiService apiService) {
        return new MarketPresenter(apiService, activity);
    }
}
