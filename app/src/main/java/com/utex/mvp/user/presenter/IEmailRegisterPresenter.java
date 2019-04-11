package com.utex.mvp.user.presenter;

import com.utex.mvp.user.bean.GTCodeVO;
import com.utex.mvp.user.bean.SendCodeDTO;
import com.utex.mvp.user.bean.UserDTO;

/**
 * Created by Demon on 2018/11/20.
 */
public interface IEmailRegisterPresenter {
    void getGTCode(String email);

    void sendEmail(SendCodeDTO sendCodeDTO);

    void register(UserDTO registerDTO);

    void getGTCodeSuccess(GTCodeVO body);

    void getGTCodeFail();

    void sendEmailSuccess();

    void sendEmailError(Throwable t);

    void registerSuccess();

    void registerFail(String message);
}
