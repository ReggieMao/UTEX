package com.utex.mvp.mine.presenter;

import com.utex.data.ApiService;
import com.utex.mvp.mine.bean.WithdrawalFeeVo;
import com.utex.mvp.mine.model.IRateExplainModel;
import com.utex.mvp.mine.model.RateExplainModel;
import com.utex.mvp.mine.view.IRateExplainView;
import com.utex.mvp.mine.view.RateExplainActivity;

import java.util.List;

/**
 * Created by Demon on 2018/11/1.
 */
public class RateExplainPresenter implements IRateExplainPresenter {

    private IRateExplainView iRateExplainView;

    private ApiService apiService;

    private IRateExplainModel iRateExplainModel;

    public RateExplainPresenter(ApiService apiService, RateExplainActivity activity) {
        this.apiService = apiService;
        this.iRateExplainView = activity;
        iRateExplainModel = new RateExplainModel();

    }

    @Override
    public void getRateData() {
        iRateExplainModel.getRateData(apiService, this);
    }

    @Override
    public void getRateDataSuccess(List<WithdrawalFeeVo.DataBean> data) {
        iRateExplainView.getRateDataSuccess(data);
    }
}
