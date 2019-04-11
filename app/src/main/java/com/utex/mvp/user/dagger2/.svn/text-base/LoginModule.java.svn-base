package com.utex.mvp.user.dagger2;

import com.utex.base.BaseModule;
import com.utex.data.ApiService;
import com.utex.mvp.user.presenter.ILoginPresenter;
import com.utex.mvp.user.presenter.LoginPresenter;
import com.utex.mvp.user.view.ILoginView;
import com.utex.mvp.user.view.LoginActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by admin on 2017/4/21.
 */
@Module
public class LoginModule extends BaseModule<LoginActivity, ILoginView> {

    public LoginModule(LoginActivity loginActivity) {
        super(loginActivity);
    }

    @Provides
    protected ILoginPresenter userPresenter(ApiService apiService) {
        return new LoginPresenter(apiService, activity);
    }
}
