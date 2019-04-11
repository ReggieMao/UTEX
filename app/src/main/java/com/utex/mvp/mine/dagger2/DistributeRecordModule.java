package com.utex.mvp.mine.dagger2;

import com.utex.base.BaseModule;
import com.utex.data.ApiService;
import com.utex.mvp.mine.presenter.DistributeRecordPresenter;
import com.utex.mvp.mine.presenter.IDistributeRecordPresenter;
import com.utex.mvp.mine.view.DistributeRecordActivity;
import com.utex.mvp.mine.view.IDistributeRecordView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by admin on 2017/4/21.
 */
@Module
public class DistributeRecordModule extends BaseModule<DistributeRecordActivity, IDistributeRecordView> {

    public DistributeRecordModule(DistributeRecordActivity distributeRecordActivity) {
        super(distributeRecordActivity);
    }

    @Provides
    protected IDistributeRecordPresenter distributeRecordPresenter(ApiService apiService) {
        return new DistributeRecordPresenter(apiService, activity);
    }
}
