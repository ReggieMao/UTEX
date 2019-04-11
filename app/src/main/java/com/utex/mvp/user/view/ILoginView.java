package com.utex.mvp.user.view;

import com.utex.mvp.user.bean.UserVO;

import org.json.JSONObject;

/**
 * Created by Demon on 2018/6/1.
 */
public interface ILoginView {
    void requestTokenSuccess();

    void requestTokenFail();

    void getUserInfoSuccess();

    void getUserInfoFail();

    void getGTCodeSuccess(JSONObject jsonObject);

    void loginPreError(String error);

    void loginPreSuccess(UserVO body);

    void closeGt();

    void keyboardDiss();

    void loginSuccess();

    void loginError(String message);

    void loginPageReset();

    void getOptionComplete();

    void popwindowDiss();
}
