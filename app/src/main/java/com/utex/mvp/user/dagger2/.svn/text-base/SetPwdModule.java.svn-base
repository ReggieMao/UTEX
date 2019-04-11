package com.utex.mvp.user.dagger2;

import com.utex.base.BaseModule;
import com.utex.data.ApiService;
import com.utex.mvp.user.presenter.ISetPwdPresenter;
import com.utex.mvp.user.presenter.SetPwdPresenter;
import com.utex.mvp.user.view.ISetPwdView;
import com.utex.mvp.user.view.SetPwdActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by admin on 2017/4/21.
 */
@Module
public class SetPwdModule extends BaseModule<SetPwdActivity, ISetPwdView> {

    public SetPwdModule(SetPwdActivity setPwdActivity) {
        super(setPwdActivity);
    }

    @Provides
    protected ISetPwdPresenter setPwdPresenter(ApiService apiService) {
        return new SetPwdPresenter(apiService, activity);
    }
}
