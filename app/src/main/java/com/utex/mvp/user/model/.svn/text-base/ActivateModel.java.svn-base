package com.utex.mvp.user.model;

import com.alibaba.fastjson.JSONObject;
import com.utex.data.ApiService;
import com.utex.mvp.user.bean.GTCodeVO;
import com.utex.mvp.user.bean.GtCodeDTO;
import com.utex.mvp.user.bean.SendCodeDTO;
import com.utex.mvp.user.bean.UserDTO;
import com.utex.mvp.user.presenter.IActivatePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Demon on 2018/7/23.
 */
public class ActivateModel implements IActivateModel {

    @Override
    public void getGTCode(ApiService apiService, String email, final IActivatePresenter iActivatePresenter) {
        GtCodeDTO gtCodeDTO = new GtCodeDTO();
        gtCodeDTO.setUsername(email);
        apiService.getGTCode(gtCodeDTO)
                .enqueue(new Callback<GTCodeVO>() {
                    @Override
                    public void onResponse(Call<GTCodeVO> call, Response<GTCodeVO> response) {
                        if (response.code() == 200) {
                            iActivatePresenter.getGTCodeSuccess(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<GTCodeVO> call, Throwable t) {

                    }
                });
    }

    @Override
    public void sendEmail(ApiService apiService, SendCodeDTO sendCodeDTO, final IActivatePresenter iActivatePresenter) {
        apiService.sendActivateEmail(sendCodeDTO)
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            iActivatePresenter.sendEmailSuccess();
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {

                    }
                });
    }

    @Override
    public void activate(ApiService apiService, String email, String code, final IActivatePresenter iActivatePresenter) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(email);
        userDTO.setPass_code(code);
        apiService.activate(userDTO)
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            iActivatePresenter.activateSuccess();
                        } else {
                            iActivatePresenter.iActivateFail();
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        iActivatePresenter.iActivateFail();
                    }
                });
    }

}
