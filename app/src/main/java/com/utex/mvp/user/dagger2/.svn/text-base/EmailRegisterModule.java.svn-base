package com.utex.mvp.user.dagger2;

import com.utex.base.BaseModule;
import com.utex.data.ApiService;
import com.utex.mvp.user.presenter.EmailRegisterPresenter;
import com.utex.mvp.user.presenter.IEmailRegisterPresenter;
import com.utex.mvp.user.view.EmailRegisterFragment;
import com.utex.mvp.user.view.IEmailRegisterView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by admin on 2017/4/21.
 */
@Module
public class EmailRegisterModule extends BaseModule<EmailRegisterFragment, IEmailRegisterView> {

    public EmailRegisterModule(EmailRegisterFragment emailRegisterFragment) {
        super(emailRegisterFragment);
    }

    @Provides
    protected IEmailRegisterPresenter registerPresenter(ApiService apiService) {
        return new EmailRegisterPresenter(apiService, activity);
    }
}
