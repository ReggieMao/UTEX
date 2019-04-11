package com.utex.mvp.mine.presenter;

import com.utex.mvp.mine.bean.DistributeRecordVO;

import java.util.List;

/**
 * Created by Demon on 2018/11/26.
 */
public interface IDistributeRecordPresenter {
    void getDistributeRecordData(int page);

    void getDistributeRecordDataSuccess(List<DistributeRecordVO.DataBean> data);

    void failMessage(String errMessage);
}
