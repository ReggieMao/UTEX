package com.utex.mvp.user.dagger2;

import com.utex.base.BaseComponent;
import com.utex.core.ActivityScope;
import com.utex.core.AppComponent;
import com.utex.mvp.user.presenter.IPhoneRegisterPresenter;
import com.utex.mvp.user.view.PhoneRegisterFragment;

import dagger.Component;

/**
 * Created by admin on 2017/4/21.
 */
@ActivityScope
@Component(modules = PhoneRegisterModule.class, dependencies = AppComponent.class)
public interface PhoneRegisterComponent extends BaseComponent<PhoneRegisterFragment, IPhoneRegisterPresenter> {

}
