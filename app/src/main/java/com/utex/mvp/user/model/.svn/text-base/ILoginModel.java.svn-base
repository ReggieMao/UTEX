package com.utex.mvp.user.model;

import com.utex.data.ApiService;
import com.utex.mvp.user.bean.LoginPreDTO;
import com.utex.mvp.user.presenter.ILoginPresenter;

/**
 * Created by Demon on 2018/8/3.
 */
public interface ILoginModel {
    void requestToken(ApiService apiService, ILoginPresenter iLoginPresenter);

    void getUserInfo(ApiService apiService, ILoginPresenter iLoginPresenter);

    void getGTCode(ApiService apiService, String account, ILoginPresenter iLoginPresenter);

    void loginPre(ApiService apiService, LoginPreDTO sendCodeDTO, ILoginPresenter iLoginPresenter);

    void login(ApiService apiService, String username, String user_type, String passCode, ILoginPresenter iLoginPresenter);

    void sendLoginCode(ApiService apiService, ILoginPresenter iLoginPresenter);

    void getOption(ApiService apiService, ILoginPresenter iLoginPresenter);
}
