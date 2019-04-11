package com.utex.mvp.splash.dagger2;

import com.utex.base.BaseModule;
import com.utex.data.ApiService;
import com.utex.mvp.splash.presenter.ISplashPresenter;
import com.utex.mvp.splash.presenter.SplashPresenter;
import com.utex.mvp.splash.view.ISplashView;
import com.utex.mvp.splash.view.SplashActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by admin on 2017/4/21.
 */
@Module
public class SplashModule extends BaseModule<SplashActivity, ISplashView> {

    public SplashModule(SplashActivity splashActivity) {
        super(splashActivity);
    }

    @Provides
    protected ISplashPresenter splashPresenter(ApiService apiService) {
        return new SplashPresenter(apiService, activity);
    }
}
