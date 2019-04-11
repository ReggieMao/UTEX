package com.utex.mvp.user.model;

import com.alibaba.fastjson.JSONObject;
import com.utex.data.ApiService;
import com.utex.mvp.user.bean.GTCodeVO;
import com.utex.mvp.user.bean.GtCodeDTO;
import com.utex.mvp.user.bean.SendCodeDTO;
import com.utex.mvp.user.bean.UserDTO;
import com.utex.mvp.user.bean.UserVO;
import com.utex.mvp.user.presenter.IRegisterPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Demon on 2018/6/2.
 */
public class RegisterModel implements IRegisterModel {

    @Override
    public void getGTCode(ApiService apiService, String email, final IRegisterPresenter iRegisterPresenter) {
        GtCodeDTO gtCodeDTO = new GtCodeDTO(email);

        apiService.getGTCode(gtCodeDTO)
                .enqueue(new Callback<GTCodeVO>() {
                    @Override
                    public void onResponse(Call<GTCodeVO> call, Response<GTCodeVO> response) {
                        if (response.code() == 200) {
                            iRegisterPresenter.getGTCodeSuccess(response.body());
                        } else {
                            iRegisterPresenter.getGTCodeFail();
                        }
                    }

                    @Override
                    public void onFailure(Call<GTCodeVO> call, Throwable t) {
                        iRegisterPresenter.getGTCodeFail();
                    }
                });
    }

    @Override
    public void sendEmail(ApiService apiService, SendCodeDTO sendCodeDTO, final IRegisterPresenter iRegisterPresenter) {
        apiService.sendCode(sendCodeDTO)
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        iRegisterPresenter.sendEmailSuccess(response);
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        iRegisterPresenter.sendEmailError(t);
                    }
                });
    }

    @Override
    public void register(ApiService apiService, UserDTO registerDTO, final IRegisterPresenter iRegisterPresenter) {
        registerDTO.setUser_from("Android");
        apiService.register(registerDTO).enqueue(new Callback<UserVO>() {
            @Override
            public void onResponse(Call<UserVO> call, Response<UserVO> response) {
                iRegisterPresenter.registerSuccess(response);
            }

            @Override
            public void onFailure(Call<UserVO> call, Throwable t) {

            }
        });
    }
}
