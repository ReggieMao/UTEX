package com.utex.mvp.mine.presenter;

import com.utex.mvp.mine.bean.WithdrawalFeeVo;

import java.util.List;

/**
 * Created by Demon on 2018/11/1.
 */
public interface IRateExplainPresenter {
    void getRateData();

    void getRateDataSuccess(List<WithdrawalFeeVo.DataBean> data);

}
