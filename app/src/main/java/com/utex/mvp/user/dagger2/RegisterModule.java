package com.utex.mvp.user.dagger2;

import com.utex.base.BaseModule;
import com.utex.data.ApiService;
import com.utex.mvp.user.presenter.IRegisterPresenter;
import com.utex.mvp.user.presenter.RegisterPresenter;
import com.utex.mvp.user.view.IRegisterView;
import com.utex.mvp.user.view.RegisterActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by admin on 2017/4/21.
 */
@Module
public class RegisterModule extends BaseModule<RegisterActivity, IRegisterView> {

    public RegisterModule(RegisterActivity registerActivity) {
        super(registerActivity);
    }

    @Provides
    protected IRegisterPresenter registerPresenter(ApiService apiService) {
        return new RegisterPresenter(apiService, activity);
    }
}
