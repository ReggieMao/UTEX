package com.utex.mvp.user.presenter;

import com.alibaba.fastjson.JSONObject;
import com.utex.mvp.user.bean.SendCodeDTO;
import com.utex.mvp.user.bean.GTCodeVO;
import com.utex.mvp.user.bean.UserDTO;
import com.utex.mvp.user.bean.UserVO;
import com.utex.mvp.user.view.RegisterActivity;

import retrofit2.Response;

/**
 * Created by Demon on 2018/6/2.
 */
public interface IRegisterPresenter {

    void getGTCode(String email);

    void getGTCodeSuccess(GTCodeVO body);

    void sendEmail(SendCodeDTO sendCodeDTO);

    void sendEmailSuccess(Response<JSONObject> response);

    void sendEmailError(Throwable t);

    void registerSuccess(Response<UserVO> response);

    void register(UserDTO registerDTO);

    void getGTCodeFail();

    void setFragment(RegisterActivity registerActivity, int resource);

    void switchPage(int page);
}
