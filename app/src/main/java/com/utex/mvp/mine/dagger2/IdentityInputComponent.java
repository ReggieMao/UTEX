package com.utex.mvp.mine.dagger2;

import com.utex.base.BaseComponent;
import com.utex.core.ActivityScope;
import com.utex.core.AppComponent;
import com.utex.mvp.mine.presenter.IIdentityInputPresenter;
import com.utex.mvp.mine.view.IdentityInputActivity;

import dagger.Component;

/**
 * Created by admin on 2017/4/21.
 */
@ActivityScope
@Component(modules = IdentityInputModule.class, dependencies = AppComponent.class)
public interface IdentityInputComponent extends BaseComponent<IdentityInputActivity, IIdentityInputPresenter> {

}
