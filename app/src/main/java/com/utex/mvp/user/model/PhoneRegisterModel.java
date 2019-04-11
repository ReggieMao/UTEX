package com.utex.mvp.user.model;

import com.alibaba.fastjson.JSONObject;
import com.utex.data.ApiService;
import com.utex.mvp.mine.bean.PhoneCountryNumVO;
import com.utex.mvp.user.bean.GTCodeVO;
import com.utex.mvp.user.bean.GtCodeDTO;
import com.utex.mvp.user.bean.SendCodeDTO;
import com.utex.mvp.user.bean.UserDTO;
import com.utex.mvp.user.bean.UserVO;
import com.utex.mvp.user.presenter.IPhoneRegisterPresenter;
import com.utex.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Demon on 2018/6/2.
 */
public class PhoneRegisterModel implements IPhoneRegisterModel {

    @Override
    public void getPhoneCountry(ApiService apiService, IPhoneRegisterPresenter iPhoneRegisterPresenter) {
        apiService.getPhoneCountry().enqueue(new Callback<PhoneCountryNumVO>() {
            @Override
            public void onResponse(Call<PhoneCountryNumVO> call, Response<PhoneCountryNumVO> response) {
                if (response.code() == 200) {
                    iPhoneRegisterPresenter.getPhoneCountrySuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<PhoneCountryNumVO> call, Throwable t) {

            }
        });
    }

    @Override
    public void getGTCode(ApiService apiService, String phoneNumber, IPhoneRegisterPresenter iPhoneRegisterPresenter) {
        GtCodeDTO gtCodeDTO = new GtCodeDTO(phoneNumber);

        apiService.getGTCode(gtCodeDTO)
                .enqueue(new Callback<GTCodeVO>() {
                    @Override
                    public void onResponse(Call<GTCodeVO> call, Response<GTCodeVO> response) {
                        if (response.code() == 200) {
                            iPhoneRegisterPresenter.getGTCodeSuccess(response.body());
                        } else {
                            String errMessage = Utils.doErrorBody(response.errorBody());
                            iPhoneRegisterPresenter.failMessage(errMessage);
                        }
                    }

                    @Override
                    public void onFailure(Call<GTCodeVO> call, Throwable t) {
                        iPhoneRegisterPresenter.failMessage("");
                    }
                });
    }

    @Override
    public void sendEmail(ApiService apiService, SendCodeDTO sendCodeDTO, IPhoneRegisterPresenter iPhoneRegisterPresenter) {
        apiService.sendCode(sendCodeDTO)
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            iPhoneRegisterPresenter.sendEmailSuccess();
                        } else {
                            String errMessage = Utils.doErrorBody(response.errorBody());
                            iPhoneRegisterPresenter.failMessage(errMessage);

                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        iPhoneRegisterPresenter.failMessage("");
                    }
                });
    }

    @Override
    public void register(ApiService apiService, UserDTO registerDTO, IPhoneRegisterPresenter iPhoneRegisterPresenter) {

        apiService.register(registerDTO).enqueue(new Callback<UserVO>() {
            @Override
            public void onResponse(Call<UserVO> call, Response<UserVO> response) {
                if (response.code() == 200) {
                    //注册成功
                    iPhoneRegisterPresenter.registerSuccess();
                } else {
                    //注册失败
                    String errMessage = Utils.doErrorBody(response.errorBody());
                    iPhoneRegisterPresenter.failMessage(errMessage);
                }

            }

            @Override
            public void onFailure(Call<UserVO> call, Throwable t) {

            }
        });
    }
}
