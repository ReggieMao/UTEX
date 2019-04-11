package com.utex.mvp.user.view;

import com.utex.mvp.user.bean.UserVO;

import org.json.JSONObject;

import retrofit2.Response;

/**
 * Created by Demon on 2018/6/2.
 */
public interface IRegisterView {
    void getEmailGTCodeSuccess(JSONObject jsonObject);

    void sendEmailSuccess();

    void sendEmailError(Throwable t);

    void registerSuccess(Response<UserVO> response);

    void registerFail(String message);

    void getGTCodeFail();

}
