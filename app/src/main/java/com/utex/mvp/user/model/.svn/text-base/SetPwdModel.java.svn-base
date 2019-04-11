package com.utex.mvp.user.model;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.utex.core.UTEXApplication;
import com.utex.data.ApiService;
import com.utex.mvp.mine.bean.PhoneCountryNumVO;
import com.utex.mvp.user.bean.GTCodeVO;
import com.utex.mvp.user.bean.GtCodeDTO;
import com.utex.mvp.user.bean.SendCodeDTO;
import com.utex.mvp.user.bean.UserDTO;
import com.utex.mvp.user.bean.UserVerifyVO;
import com.utex.mvp.user.presenter.ISetPwdPresenter;
import com.utex.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Demon on 2018/7/11.
 */
public class SetPwdModel implements ISetPwdModel {

    @Override
    public void getGTCode(ApiService apiService, String email, final ISetPwdPresenter iSetPwdPresenter) {
        GtCodeDTO gtCodeDTO = new GtCodeDTO(email);

        apiService.getGTCode(gtCodeDTO).enqueue(new Callback<GTCodeVO>() {
            @Override
            public void onResponse(Call<GTCodeVO> call, Response<GTCodeVO> response) {
                if (response.code() == 200) {
                    iSetPwdPresenter.getGTCodeSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<GTCodeVO> call, Throwable t) {

            }
        });
    }

    @Override
    public void sendEmail(ApiService apiService, SendCodeDTO sendCodeDTO, final ISetPwdPresenter iSetPwdPresenter) {
        apiService.sendCode(sendCodeDTO)
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            iSetPwdPresenter.sendEmailSuccess();
                        } else {
                            String str = Utils.doErrorBody(response.errorBody());

                            iSetPwdPresenter.sendEmailError(str);
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        iSetPwdPresenter.sendEmailError("");
                    }
                });
    }

    @Override
    public void forgetPwdNext(ApiService apiService, String email, String code, final ISetPwdPresenter iSetPwdPresenter) {
        UserDTO userDTO = new UserDTO(email, code);
        if (email.contains("@")) {
            userDTO.setCode_carrier(UserDTO.EMAIL);
        } else {
            userDTO.setCode_carrier(UserDTO.TEL);
        }
        apiService.forgetPwdNext(userDTO)
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            String token = response.body().getString("token");
                            iSetPwdPresenter.forgetPwdNextSuccess(token);
                        } else {
                            String str = Utils.doErrorBody(response.errorBody());
                            iSetPwdPresenter.forgetPwdNextFail(str);
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        Log.e("TAG", "error" + t.getMessage());
                        iSetPwdPresenter.forgetPwdNextFail("");

                    }
                });
    }

    @Override
    public void forgetPwdComplete(ApiService apiService, UserDTO userDTO, final ISetPwdPresenter iSetPwdPresenter) {
        apiService.forgetPwdComplete(userDTO)
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            iSetPwdPresenter.forgetPwdCompleteSuccess();
                        } else {
                            String str = Utils.doErrorBody(response.errorBody());

                            iSetPwdPresenter.forgetPwdCompleteError(str);
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        iSetPwdPresenter.forgetPwdCompleteError("");
                    }
                });
    }

    @Override
    public void confirmResetPwd(ApiService apiService, String oldPwd, String newPwd, String confirmNewPwd, final ISetPwdPresenter iSetPwdPresenter) {
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword(Utils.md5(oldPwd));
        userDTO.setNew_password(Utils.md5(newPwd));
        userDTO.setRe_new_password(Utils.md5(confirmNewPwd));
        apiService.confirmResetPwd(userDTO).enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (response.code() == 200) {
                    iSetPwdPresenter.confirmResetPwdSuccess();
                } else {
                    String message = Utils.doErrorBody(response.errorBody());
                    iSetPwdPresenter.confirmResetPwdError(message);
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                iSetPwdPresenter.confirmResetPwdError("");
            }
        });

    }

    @Override
    public void getCountery(ApiService apiService, ISetPwdPresenter iSetPwdPresenter) {
        apiService.getPhoneCountry().enqueue(new Callback<PhoneCountryNumVO>() {
            @Override
            public void onResponse(Call<PhoneCountryNumVO> call, Response<PhoneCountryNumVO> response) {
                if (response.code() == 200) {
                    iSetPwdPresenter.getPhoneCountrySuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<PhoneCountryNumVO> call, Throwable t) {

            }
        });
    }

    @Override
    public void checkUser(ApiService apiService, UserDTO account, ISetPwdPresenter iSetPwdPresenter) {
        apiService.checkUser(account)
                .enqueue(new Callback<UserVerifyVO>() {
                    @Override
                    public void onResponse(Call<UserVerifyVO> call, Response<UserVerifyVO> response) {
                        if (response.code() == 200) {
                            UTEXApplication.setToken(response.body().getToken());
                            UTEXApplication.setUsername(response.body().getData().getUsername());
                            iSetPwdPresenter.checkUserSuccess(response.body().getData());
                        } else {
                            String s = Utils.doErrorBody(response.errorBody());
                            iSetPwdPresenter.failMessage(s);
                        }
                    }

                    @Override
                    public void onFailure(Call<UserVerifyVO> call, Throwable t) {

                    }
                });
    }

}
