package com.utex.mvp.user.view;

import org.json.JSONObject;

/**
 * Created by Demon on 2018/6/2.
 */
public interface IEmailRegisterView {
    void getEmailGTCodeSuccess(JSONObject jsonObject);

    void sendEmailSuccess();

    void sendEmailError(Throwable t);

    void registerSuccess();

    void registerFail(String message);

    void getGTCodeFail();

}
