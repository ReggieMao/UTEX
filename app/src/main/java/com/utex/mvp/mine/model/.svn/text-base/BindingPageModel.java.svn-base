package com.utex.mvp.mine.model;

import com.alibaba.fastjson.JSONObject;
import com.utex.data.ApiService;
import com.utex.mvp.mine.bean.BindGoogleDTO;
import com.utex.mvp.mine.bean.BindTelDTO;
import com.utex.mvp.mine.bean.GoogleVO;
import com.utex.mvp.mine.bean.PhoneCountryNumVO;
import com.utex.mvp.mine.presenter.IBindingPagePresenter;
import com.utex.mvp.user.bean.GTCodeVO;
import com.utex.mvp.user.bean.GtCodeDTO;
import com.utex.mvp.user.bean.SendCodeDTO;
import com.utex.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Demon on 2018/7/25.
 */
public class BindingPageModel implements IBindingPageModel {

    @Override
    public void getGoogleBindInfo(ApiService apiService, final IBindingPagePresenter iBindingPagePresenter) {
        apiService.getGoogleBindInfo()
                .enqueue(new Callback<GoogleVO>() {
                    @Override
                    public void onResponse(Call<GoogleVO> call, Response<GoogleVO> response) {
                        if (response.code() == 200) {
                            iBindingPagePresenter.getGoogleBindInfoSuccess(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<GoogleVO> call, Throwable t) {

                    }
                });
    }

    @Override
    public void getGTCode(ApiService apiService, String username, final IBindingPagePresenter iBindingPagePresenter) {
        apiService.getGTCode(new GtCodeDTO(username))
                .enqueue(new Callback<GTCodeVO>() {
                    @Override
                    public void onResponse(Call<GTCodeVO> call, Response<GTCodeVO> response) {
                        if (response.code() == 200) {
                            iBindingPagePresenter.getGTCodeSuccess(response.body());
                        } else {
                            iBindingPagePresenter.getGTCodeFail("0");
                        }
                    }

                    @Override
                    public void onFailure(Call<GTCodeVO> call, Throwable t) {
                        iBindingPagePresenter.getGTCodeFail("0");
                    }
                });
    }

    @Override
    public void sendTelCode(ApiService apiService, SendCodeDTO sendCodeDTO, final IBindingPagePresenter iBindingPagePresenter) {
        apiService.sendCode(sendCodeDTO).enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (response.code() == 200) {
                    iBindingPagePresenter.sendTelCodeSuccess();
                } else {

                    String s = Utils.doErrorBody(response.errorBody());
                    iBindingPagePresenter.getGTCodeFail(s);
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                iBindingPagePresenter.getGTCodeFail("0");
            }
        });
    }

    @Override
    public void confirmBindPhone(ApiService apiService, String phone, String code, String countryCode, final IBindingPagePresenter iBindingPagePresenter) {
        apiService.confirmBindPhone(new BindTelDTO(phone, code, countryCode))
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            iBindingPagePresenter.confirmBindPhoneSuccess();
                        } else {
                            String str = Utils.doErrorBody(response.errorBody());
                            iBindingPagePresenter.confirmBindPhoneFail(str);
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        iBindingPagePresenter.confirmBindPhoneFail("");
                    }
                });
    }

    @Override
    public void getPhoneCountry(ApiService apiService, final IBindingPagePresenter iBindingPagePresenter) {
        apiService.getPhoneCountry().enqueue(new Callback<PhoneCountryNumVO>() {
            @Override
            public void onResponse(Call<PhoneCountryNumVO> call, Response<PhoneCountryNumVO> response) {
                if (response.code() == 200) {
                    iBindingPagePresenter.getPhoneCountrySuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<PhoneCountryNumVO> call, Throwable t) {

            }
        });
    }

    @Override
    public void bindGoogle(ApiService apiService, String secretKey, String code, final IBindingPagePresenter iBindingPagePresenter) {
        apiService.bindGoogle(new BindGoogleDTO(code, secretKey))
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            iBindingPagePresenter.bindGoogleSuccess();
                        } else {
                            String s = Utils.doErrorBody(response.errorBody());
                            iBindingPagePresenter.getGTCodeFail(s);
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        iBindingPagePresenter.getGTCodeFail("0");
                    }
                });
    }

    @Override
    public void bindEmail(ApiService apiService, String email, String code, IBindingPagePresenter iBindingPagePresenter) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", email);
        jsonObject.put("pass_code", code);
        apiService.bindEmail(jsonObject)
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            iBindingPagePresenter.bindEmailSuccess();
                        } else {
                            String errMessage = Utils.doErrorBody(response.errorBody());
                            iBindingPagePresenter.failMessage(errMessage);
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {

                    }
                });
    }

}
