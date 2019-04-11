package com.utex.mvp.hometab.dagger2;

import com.utex.base.BaseComponent;
import com.utex.core.ActivityScope;
import com.utex.core.AppComponent;
import com.utex.mvp.hometab.presenter.IHomeTabPresenter;
import com.utex.mvp.hometab.view.HomeTabActivity;

import dagger.Component;

/**
 * Created by admin on 2017/4/21.
 */
@ActivityScope
@Component(modules = HomeTabModule.class, dependencies = AppComponent.class)
public interface HomeTabComponent extends BaseComponent<HomeTabActivity, IHomeTabPresenter> {

}
