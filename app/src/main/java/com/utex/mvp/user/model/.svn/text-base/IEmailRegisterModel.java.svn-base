package com.utex.mvp.user.model;

import com.utex.data.ApiService;
import com.utex.mvp.user.bean.SendCodeDTO;
import com.utex.mvp.user.bean.UserDTO;
import com.utex.mvp.user.presenter.IEmailRegisterPresenter;

/**
 * Created by Demon on 2018/11/20.
 */
public interface IEmailRegisterModel {
    void getGTCode(ApiService apiService, String email, IEmailRegisterPresenter iEmailRegisterPresenter);

    void sendEmail(ApiService apiService, SendCodeDTO sendCodeDTO, IEmailRegisterPresenter iEmailRegisterPresenter);

    void register(ApiService apiService, UserDTO registerDTO, IEmailRegisterPresenter iEmailRegisterPresenter);
}
