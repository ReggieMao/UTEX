package com.utex.mvp.hometab.dagger2;

import com.utex.base.BaseModule;
import com.utex.data.ApiService;
import com.utex.mvp.hometab.presenter.HomeTabTabPresenter;
import com.utex.mvp.hometab.presenter.IHomeTabPresenter;
import com.utex.mvp.hometab.view.HomeTabActivity;
import com.utex.mvp.hometab.view.IHomeTabView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by admin on 2017/4/21.
 */
@Module
public class HomeTabModule extends BaseModule<HomeTabActivity, IHomeTabView> {

    public HomeTabModule(HomeTabActivity homeTabActivity) {
        super(homeTabActivity);
    }

    @Provides
    protected IHomeTabPresenter homePresenter(ApiService apiService) {
        return new HomeTabTabPresenter(apiService,activity);
    }
}
