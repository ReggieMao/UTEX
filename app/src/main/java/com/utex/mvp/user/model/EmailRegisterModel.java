package com.utex.mvp.user.model;

import com.alibaba.fastjson.JSONObject;
import com.utex.data.ApiService;
import com.utex.mvp.user.bean.GTCodeVO;
import com.utex.mvp.user.bean.GtCodeDTO;
import com.utex.mvp.user.bean.SendCodeDTO;
import com.utex.mvp.user.bean.UserDTO;
import com.utex.mvp.user.bean.UserVO;
import com.utex.mvp.user.presenter.IEmailRegisterPresenter;
import com.utex.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Demon on 2018/11/20.
 */
public class EmailRegisterModel implements IEmailRegisterModel {

    @Override
    public void getGTCode(ApiService apiService, String email, final IEmailRegisterPresenter iEmailRegisterPresenter) {
        GtCodeDTO gtCodeDTO = new GtCodeDTO(email);

        apiService.getGTCode(gtCodeDTO)
                .enqueue(new Callback<GTCodeVO>() {
                    @Override
                    public void onResponse(Call<GTCodeVO> call, Response<GTCodeVO> response) {
                        if (response.code() == 200) {
                            iEmailRegisterPresenter.getGTCodeSuccess(response.body());
                        } else {
                            iEmailRegisterPresenter.getGTCodeFail();
                        }
                    }

                    @Override
                    public void onFailure(Call<GTCodeVO> call, Throwable t) {
                        iEmailRegisterPresenter.getGTCodeFail();
                    }
                });
    }

    @Override
    public void sendEmail(ApiService apiService, SendCodeDTO sendCodeDTO, final IEmailRegisterPresenter iEmailRegisterPresenter) {
        apiService.sendCode(sendCodeDTO)
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            iEmailRegisterPresenter.sendEmailSuccess();
                        } else {
                            String errMessage = Utils.doErrorBody(response.errorBody());
                            iEmailRegisterPresenter.registerFail(errMessage);
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        iEmailRegisterPresenter.sendEmailError(t);
                    }
                });
    }

    @Override
    public void register(ApiService apiService, UserDTO registerDTO, final IEmailRegisterPresenter iEmailRegisterPresenter) {
        apiService.register(registerDTO).enqueue(new Callback<UserVO>() {
            @Override
            public void onResponse(Call<UserVO> call, Response<UserVO> response) {
                if (response.code() == 200) {
                    //注册成功
                    iEmailRegisterPresenter.registerSuccess();
                } else {
                    //注册失败
                    String errMessage = Utils.doErrorBody(response.errorBody());
                    iEmailRegisterPresenter.registerFail(errMessage);
                }

            }

            @Override
            public void onFailure(Call<UserVO> call, Throwable t) {

            }
        });
    }
}
