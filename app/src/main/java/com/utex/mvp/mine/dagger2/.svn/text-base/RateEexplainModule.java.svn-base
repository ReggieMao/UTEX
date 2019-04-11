package com.utex.mvp.mine.dagger2;

import com.utex.base.BaseModule;
import com.utex.data.ApiService;
import com.utex.mvp.mine.presenter.IRateExplainPresenter;
import com.utex.mvp.mine.presenter.RateExplainPresenter;
import com.utex.mvp.mine.view.IRateExplainView;
import com.utex.mvp.mine.view.RateExplainActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by admin on 2017/4/21.
 */
@Module
public class RateEexplainModule extends BaseModule<RateExplainActivity, IRateExplainView> {

    public RateEexplainModule(RateExplainActivity rateExplainActivity) {
        super(rateExplainActivity);
    }

    @Provides
    protected IRateExplainPresenter irateExplainPresenter(ApiService apiService) {
        return new RateExplainPresenter(apiService, activity);
    }

}
