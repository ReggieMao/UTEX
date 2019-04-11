package com.utex.mvp.marketdetail.dagger2;

import com.utex.base.BaseComponent;
import com.utex.core.ActivityScope;
import com.utex.core.AppComponent;
import com.utex.mvp.marketdetail.presenter.IMarketDetailFullPresenter;
import com.utex.mvp.marketdetail.view.MarketDetailFullActivity;

import dagger.Component;

/**
 * Created by admin on 2017/4/21.
 */
@ActivityScope
@Component(modules = MarketDetailFullModule.class, dependencies = AppComponent.class)
public interface MarketDetailFullComponent extends BaseComponent<MarketDetailFullActivity, IMarketDetailFullPresenter> {

}
