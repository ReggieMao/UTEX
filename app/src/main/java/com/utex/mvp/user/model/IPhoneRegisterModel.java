package com.utex.mvp.user.model;

import com.utex.data.ApiService;
import com.utex.mvp.user.bean.SendCodeDTO;
import com.utex.mvp.user.bean.UserDTO;
import com.utex.mvp.user.presenter.IPhoneRegisterPresenter;

/**
 * Created by Demon on 2018/6/2.
 */
public interface IPhoneRegisterModel {
    void getPhoneCountry(ApiService apiService, IPhoneRegisterPresenter iPhoneRegisterPresenter);

    void getGTCode(ApiService apiService, String phoneNumber, IPhoneRegisterPresenter iPhoneRegisterPresenter);

    void sendEmail(ApiService apiService, SendCodeDTO sendCodeDTO, IPhoneRegisterPresenter iPhoneRegisterPresenter);

    void register(ApiService apiService, UserDTO registerDTO, IPhoneRegisterPresenter iPhoneRegisterPresenter);
}
