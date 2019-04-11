package com.utex.mvp.user.model;

import com.utex.data.ApiService;
import com.utex.mvp.user.bean.SendCodeDTO;
import com.utex.mvp.user.bean.UserDTO;
import com.utex.mvp.user.presenter.IRegisterPresenter;

/**
 * Created by Demon on 2018/6/2.
 */
public interface IRegisterModel {
    void getGTCode(ApiService apiService, String email, IRegisterPresenter iRegisterPresenter);

    void sendEmail(ApiService apiService, SendCodeDTO sendCodeDTO, IRegisterPresenter iRegisterPresenter);


    void register(ApiService apiService, UserDTO registerDTO, IRegisterPresenter iRegisterPresenter);
}
