package com.utex.mvp.home.dagger2;

import com.utex.base.BaseModule;
import com.utex.data.ApiService;
import com.utex.mvp.home.presenter.HomePresenter;
import com.utex.mvp.home.presenter.IHomePresenter;
import com.utex.mvp.home.view.HomeFragment;
import com.utex.mvp.home.view.IHomeView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by admin on 2017/4/21.
 */
@Module
public class HomeModule extends BaseModule<HomeFragment, IHomeView> {

    public HomeModule(HomeFragment homeFragment) {
        super(homeFragment);
    }

    @Provides
    protected IHomePresenter homePresenter(ApiService apiService) {
        return new HomePresenter(apiService, activity);
    }
}
