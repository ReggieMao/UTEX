package com.utex.mvp.mine.presenter;

import com.utex.data.ApiService;
import com.utex.mvp.mine.bean.DistributeRecordVO;
import com.utex.mvp.mine.model.DistributeRecordModel;
import com.utex.mvp.mine.model.IDistributeRecordModel;
import com.utex.mvp.mine.view.DistributeRecordActivity;
import com.utex.mvp.mine.view.IDistributeRecordView;

import java.util.List;

/**
 * Created by Demon on 2018/11/26.
 */
public class DistributeRecordPresenter implements IDistributeRecordPresenter {

    private IDistributeRecordModel iDistributeRecordModel;

    private ApiService apiService;

    private IDistributeRecordView iDistributeRecordView;

    public DistributeRecordPresenter(ApiService apiService, DistributeRecordActivity activity) {
        iDistributeRecordModel = new DistributeRecordModel();
        this.apiService = apiService;
        iDistributeRecordView = activity;
    }

    @Override
    public void getDistributeRecordData(int page) {
        iDistributeRecordModel.getDistributeRecordData(apiService, page, this);
    }

    @Override
    public void getDistributeRecordDataSuccess(List<DistributeRecordVO.DataBean> data) {
        iDistributeRecordView.getDistributeRecordDataSuccess(data);
    }

    @Override
    public void failMessage(String errMessage) {
        iDistributeRecordView.failMessage(errMessage);

    }
}
