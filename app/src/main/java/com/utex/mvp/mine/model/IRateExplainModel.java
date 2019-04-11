package com.utex.mvp.mine.model;

import com.utex.data.ApiService;
import com.utex.mvp.mine.presenter.IRateExplainPresenter;

/**
 * Created by Demon on 2018/11/1.
 */
public interface IRateExplainModel {

    void getRateData(ApiService apiService, IRateExplainPresenter iRateExplainPresenter);

}
