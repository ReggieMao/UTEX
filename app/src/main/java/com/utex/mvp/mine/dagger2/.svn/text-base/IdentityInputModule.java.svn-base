package com.utex.mvp.mine.dagger2;

import com.utex.base.BaseModule;
import com.utex.data.ApiService;
import com.utex.mvp.mine.presenter.IIdentityInputPresenter;
import com.utex.mvp.mine.presenter.IdentityInputPresenter;
import com.utex.mvp.mine.view.IIdentityInputView;
import com.utex.mvp.mine.view.IdentityInputActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by admin on 2017/4/21.
 */
@Module
public class IdentityInputModule extends BaseModule<IdentityInputActivity, IIdentityInputView> {

    public IdentityInputModule(IdentityInputActivity identityInputActivity) {
        super(identityInputActivity);
    }

    @Provides
    protected IIdentityInputPresenter identityInputPresenter(ApiService apiService) {
        return new IdentityInputPresenter(apiService, activity);
    }
}
