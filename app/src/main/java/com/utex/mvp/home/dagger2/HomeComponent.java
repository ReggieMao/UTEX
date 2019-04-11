package com.utex.mvp.home.dagger2;

import com.utex.base.BaseComponent;
import com.utex.core.ActivityScope;
import com.utex.core.AppComponent;
import com.utex.mvp.home.presenter.IHomePresenter;
import com.utex.mvp.home.view.HomeFragment;

import dagger.Component;

/**
 * Created by admin on 2017/4/21.
 */
@ActivityScope
@Component(modules = HomeModule.class, dependencies = AppComponent.class)
public interface HomeComponent extends BaseComponent<HomeFragment, IHomePresenter> {

}
