package com.utex.mvp.mine.model;

import com.utex.data.ApiService;
import com.utex.mvp.mine.bean.DistributeRecordVO;
import com.utex.mvp.mine.presenter.IDistributeRecordPresenter;
import com.utex.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Demon on 2018/11/26.
 */
public class DistributeRecordModel implements IDistributeRecordModel {
    @Override
    public void getDistributeRecordData(ApiService apiService, int page, IDistributeRecordPresenter distributeRecordPresenter) {
        apiService.getDistributeRecordData(page)
                .enqueue(new Callback<DistributeRecordVO>() {
                    @Override
                    public void onResponse(Call<DistributeRecordVO> call, Response<DistributeRecordVO> response) {
                        if (response.code() == 200) {
                            distributeRecordPresenter.getDistributeRecordDataSuccess(response.body().getData());
                        } else {
                            String errMessage = Utils.doErrorBody(response.errorBody());
                            distributeRecordPresenter.failMessage(errMessage);
                        }
                    }

                    @Override
                    public void onFailure(Call<DistributeRecordVO> call, Throwable t) {

                    }
                });
    }
}
