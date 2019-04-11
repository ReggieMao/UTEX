package com.utex.mvp.mine.dagger2;

import com.utex.base.BaseModule;
import com.utex.data.ApiService;
import com.utex.mvp.mine.presenter.ILoginValidationPresenter;
import com.utex.mvp.mine.presenter.LoginValidationPresenter;
import com.utex.mvp.mine.view.ILoginValidationView;
import com.utex.mvp.mine.view.LoginValidationSettingActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by admin on 2017/4/21.
 */
@Module
public class LoginValidationModule extends BaseModule<LoginValidationSettingActivity, ILoginValidationView> {

    public LoginValidationModule(LoginValidationSettingActivity loginValidationSettingActivity) {
        super(loginValidationSettingActivity);
    }

    @Provides
    protected ILoginValidationPresenter loginValidationPresenter(ApiService apiService) {
        return new LoginValidationPresenter(apiService, activity);
    }
}
