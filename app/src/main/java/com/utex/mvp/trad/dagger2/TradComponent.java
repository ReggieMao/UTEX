package com.utex.mvp.trad.dagger2;

import com.utex.base.BaseComponent;
import com.utex.core.ActivityScope;
import com.utex.core.AppComponent;
import com.utex.mvp.trad.presenter.ITradPresenter;
import com.utex.mvp.trad.view.TradFragment;

import dagger.Component;

/**
 * Created by admin on 2017/4/21.
 */
@ActivityScope
@Component(modules = TradModule.class, dependencies = AppComponent.class)
public interface TradComponent extends BaseComponent<TradFragment, ITradPresenter> {

}
