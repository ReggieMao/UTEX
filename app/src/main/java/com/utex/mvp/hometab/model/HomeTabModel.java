package com.utex.mvp.hometab.model;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.utex.bean.UserDO;
import com.utex.core.UTEXApplication;
import com.utex.data.ApiService;
import com.utex.db.ExUserDao;
import com.utex.mvp.hometab.bean.CheckAppVersionDTO;
import com.utex.mvp.hometab.bean.CheckAppVersionVO;
import com.utex.mvp.hometab.presenter.IHomeTabPresenter;
import com.utex.utils.APKVersionCodeUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Demon on 2018/8/6.
 */
public class HomeTabModel implements IHomeTabModel {

    @Override
    public void checkAppVersion(ApiService apiService, final IHomeTabPresenter iHomeTabPresenter) {
        UserDO loginUser = ExUserDao.query();
        CheckAppVersionDTO checkAppVersionDTO = null;
        
        if (loginUser != null) {
            checkAppVersionDTO = new CheckAppVersionDTO(APKVersionCodeUtils.getVerName(), 0, loginUser.getToken(), loginUser.getUsername());
        } else {
            checkAppVersionDTO = new CheckAppVersionDTO(APKVersionCodeUtils.getVerName(), 0, null, null);
        }

        Log.d("d", "checkAppVersion getVersion_number: " + checkAppVersionDTO.getVersion_number());
        Log.d("d", "checkAppVersion getType: " + checkAppVersionDTO.getType());
        Log.d("d", "checkAppVersion getToken: " + checkAppVersionDTO.getToken());
        Log.d("d", "checkAppVersion getUsername: " + checkAppVersionDTO.getUsername());
        apiService.checkAppVersion(checkAppVersionDTO)
                .enqueue(new Callback<CheckAppVersionVO>() {
                    @Override
                    public void onResponse(Call<CheckAppVersionVO> call, Response<CheckAppVersionVO> response) {
                        if (response.code() == 200) {
                            CheckAppVersionVO.DataBean data = response.body().getData();
                            if (data.isIs_token_expired()) {
                                //已过期
                                ExUserDao.clearUser();
                            }

                            iHomeTabPresenter.checkAppVersionSuccess(data);
                        } else {
                            iHomeTabPresenter.checkAppVersionFail();
                        }
                    }

                    @Override
                    public void onFailure(Call<CheckAppVersionVO> call, Throwable t) {
                        iHomeTabPresenter.checkAppVersionFail();
                    }
                });

    }

    @Override
    public void getExchangeRateList(ApiService apiService, IHomeTabPresenter iHomeTabPresenter) {
        apiService.getExchangeRateList()
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            JSONObject data = response.body().getJSONObject("data");
                            UTEXApplication.setRate(data);
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {

                    }
                });
    }

}
