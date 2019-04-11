package com.utex.mvp.mine.dagger2;

import com.utex.base.BaseModule;
import com.utex.data.ApiService;
import com.utex.mvp.mine.presenter.IWithdrawalDetailPresenter;
import com.utex.mvp.mine.presenter.WithdrawalDetailPresenter;
import com.utex.mvp.mine.view.IWithdrawalDetailView;
import com.utex.mvp.mine.view.WithdrawalDetailActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by admin on 2017/4/21.
 */
@Module
public class WithdrawalDetailModule extends BaseModule<WithdrawalDetailActivity, IWithdrawalDetailView> {

    public WithdrawalDetailModule(WithdrawalDetailActivity withdrawalDetailActivity) {
        super(withdrawalDetailActivity);
    }

    @Provides
    protected IWithdrawalDetailPresenter iWithdrawalDetailPresenter(ApiService apiService) {
        return new WithdrawalDetailPresenter(apiService, activity);
    }

}
