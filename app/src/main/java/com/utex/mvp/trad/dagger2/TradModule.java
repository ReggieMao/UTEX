package com.utex.mvp.trad.dagger2;

import com.utex.base.BaseModule;
import com.utex.data.ApiService;
import com.utex.mvp.trad.presenter.ITradPresenter;
import com.utex.mvp.trad.presenter.TradPresenter;
import com.utex.mvp.trad.view.ITradView;
import com.utex.mvp.trad.view.TradFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by admin on 2017/4/21.
 */
@Module
public class TradModule extends BaseModule<TradFragment, ITradView> {

    public TradModule(TradFragment tradFragment) {
        super(tradFragment);
    }

    @Provides
    protected ITradPresenter tradPresenter(ApiService apiService) {
        return new TradPresenter(apiService, activity);
    }
}
