package com.utex.mvp.user.model;

import com.utex.data.ApiService;
import com.utex.mvp.user.bean.SendCodeDTO;
import com.utex.mvp.user.bean.UserDTO;
import com.utex.mvp.user.presenter.ISetPwdPresenter;

/**
 * Created by Demon on 2018/7/11.
 */
public interface ISetPwdModel {
    void getGTCode(ApiService apiService, String email, ISetPwdPresenter iSetPwdPresenter);

    void sendEmail(ApiService apiService, SendCodeDTO sendCodeDTO, ISetPwdPresenter iSetPwdPresenter);

    void forgetPwdNext(ApiService apiService, String email, String code, ISetPwdPresenter iSetPwdPresenter);

    void confirmResetPwd(ApiService apiService, String oldPwd, String newPwd, String confirmNewPwd, ISetPwdPresenter iSetPwdPresenter);

    void getCountery(ApiService apiService, ISetPwdPresenter iSetPwdPresenter);

    void checkUser(ApiService apiService, UserDTO account, ISetPwdPresenter iSetPwdPresenter);

    void forgetPwdComplete(ApiService apiService, UserDTO userDTO, ISetPwdPresenter iSetPwdPresenter);
}
