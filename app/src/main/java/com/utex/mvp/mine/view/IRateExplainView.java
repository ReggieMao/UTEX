package com.utex.mvp.mine.view;

import com.utex.mvp.mine.bean.WithdrawalFeeVo;

import java.util.List;

/**
 * Created by Demon on 2018/11/1.
 */
public interface IRateExplainView {
    void getRateDataSuccess(List<WithdrawalFeeVo.DataBean> data);

}
