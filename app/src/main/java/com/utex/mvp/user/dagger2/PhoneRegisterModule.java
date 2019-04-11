package com.utex.mvp.user.dagger2;

import com.utex.base.BaseModule;
import com.utex.data.ApiService;
import com.utex.mvp.user.presenter.IPhoneRegisterPresenter;
import com.utex.mvp.user.presenter.PhoneRegisterPresenter;
import com.utex.mvp.user.view.IPhoneRegisterView;
import com.utex.mvp.user.view.PhoneRegisterFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by admin on 2017/4/21.
 */
@Module
public class PhoneRegisterModule extends BaseModule<PhoneRegisterFragment, IPhoneRegisterView> {

    public PhoneRegisterModule(PhoneRegisterFragment phoneRegisterFragment) {
        super(phoneRegisterFragment);
    }

    @Provides
    protected IPhoneRegisterPresenter loginPresenter(ApiService apiService) {
        return new PhoneRegisterPresenter(apiService, activity);
    }
}
