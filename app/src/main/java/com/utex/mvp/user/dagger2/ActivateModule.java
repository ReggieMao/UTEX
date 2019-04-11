package com.utex.mvp.user.dagger2;

import com.utex.base.BaseModule;
import com.utex.data.ApiService;
import com.utex.mvp.user.presenter.ActivatePresenter;
import com.utex.mvp.user.presenter.IActivatePresenter;
import com.utex.mvp.user.view.ActivateActivity;
import com.utex.mvp.user.view.IActivateView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by admin on 2017/4/21.
 */
@Module
public class ActivateModule extends BaseModule<ActivateActivity, IActivateView> {

    public ActivateModule(ActivateActivity activateActivity) {
        super(activateActivity);
    }

    @Provides
    protected IActivatePresenter acruvarePresenter(ApiService apiService) {
        return new ActivatePresenter(apiService, activity);
    }
}
