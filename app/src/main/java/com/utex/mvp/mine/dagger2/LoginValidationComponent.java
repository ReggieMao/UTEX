package com.utex.mvp.mine.dagger2;

import com.utex.base.BaseComponent;
import com.utex.core.ActivityScope;
import com.utex.core.AppComponent;
import com.utex.mvp.mine.presenter.ILoginValidationPresenter;
import com.utex.mvp.mine.view.LoginValidationSettingActivity;

import dagger.Component;

/**
 * Created by admin on 2017/4/21.
 */
@ActivityScope
@Component(modules = LoginValidationModule.class, dependencies = AppComponent.class)
public interface LoginValidationComponent extends BaseComponent<LoginValidationSettingActivity, ILoginValidationPresenter> {

}
