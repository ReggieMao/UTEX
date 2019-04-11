package com.utex.mvp.mine.dagger2;

import com.utex.base.BaseModule;
import com.utex.data.ApiService;
import com.utex.mvp.mine.presenter.BindingPagePresenter;
import com.utex.mvp.mine.presenter.IBindingPagePresenter;
import com.utex.mvp.mine.view.BindingPageActivity;
import com.utex.mvp.mine.view.IBindingPageView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by admin on 2017/4/21.
 */
@Module
public class BindingPageModule extends BaseModule<BindingPageActivity, IBindingPageView> {

    public BindingPageModule(BindingPageActivity bindingPageActivity) {
        super(bindingPageActivity);
    }

    @Provides
    protected IBindingPagePresenter bindPagePresenter(ApiService apiService) {
        return new BindingPagePresenter(apiService, activity);
    }
}
