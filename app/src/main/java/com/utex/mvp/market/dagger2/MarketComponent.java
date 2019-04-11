package com.utex.mvp.market.dagger2;

import com.utex.base.BaseComponent;
import com.utex.core.ActivityScope;
import com.utex.core.AppComponent;
import com.utex.mvp.market.presenter.IMarketPresenter;
import com.utex.mvp.market.view.MarketFragment;

import dagger.Component;

/**
 * Created by admin on 2017/4/21.
 */
@ActivityScope
@Component(modules = MarketModule.class, dependencies = AppComponent.class)
public interface MarketComponent extends BaseComponent<MarketFragment, IMarketPresenter> {

}
