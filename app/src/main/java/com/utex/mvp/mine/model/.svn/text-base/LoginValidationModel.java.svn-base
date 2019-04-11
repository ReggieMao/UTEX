package com.utex.mvp.mine.model;

import com.alibaba.fastjson.JSONObject;
import com.utex.data.ApiService;
import com.utex.mvp.mine.bean.LoginVerificationDTO;
import com.utex.mvp.mine.presenter.ILoginValidationPresenter;
import com.utex.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Demon on 2018/7/25.
 */
public class LoginValidationModel implements ILoginValidationModel {

    @Override
    public void switchPhoneStatus(ApiService apiService, int status, final ILoginValidationPresenter iLoginValidationPresenter) {
        LoginVerificationDTO loginVerificationDTO = new LoginVerificationDTO();
        loginVerificationDTO.setTel_status(status);
        apiService.switchPhoneStatus(loginVerificationDTO)
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            iLoginValidationPresenter.switchPhoneStatusSuccess();
                        } else {
                            String s = Utils.doErrorBody(response.errorBody());
                            iLoginValidationPresenter.switchPhoneStatusFail(s);
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        iLoginValidationPresenter.switchPhoneStatusFail("");
                    }
                });

    }

    @Override
    public void switchGoogleStatus(ApiService apiService, int status, final ILoginValidationPresenter iLoginValidationPresenter) {
        LoginVerificationDTO loginVerificationDTO = new LoginVerificationDTO();
        loginVerificationDTO.setGoogle_status(status);
        apiService.switchGoogleStatus(loginVerificationDTO)
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            iLoginValidationPresenter.switchGoogleStatusSuccess();
                        } else {

                            String s = Utils.doErrorBody(response.errorBody());
                            iLoginValidationPresenter.switchGoogleStatusFail(s);

                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        iLoginValidationPresenter.switchGoogleStatusFail("");
                    }
                });
    }

    @Override
    public void switchEmailStatus(ApiService apiService, int status, ILoginValidationPresenter iLoginValidationPresenter) {
        LoginVerificationDTO loginVerificationDTO = new LoginVerificationDTO();
        loginVerificationDTO.setEmail_status(status);
        apiService.switchEmailStatus(loginVerificationDTO)
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            iLoginValidationPresenter.switchEmailStatusSuccess();
                        } else {
                            String s = Utils.doErrorBody(response.errorBody());
                            iLoginValidationPresenter.switchEmailStatusFail(s);
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        iLoginValidationPresenter.switchEmailStatusFail("");
                    }
                });
    }
}
