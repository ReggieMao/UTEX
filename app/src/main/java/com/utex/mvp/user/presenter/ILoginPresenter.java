package com.utex.mvp.user.presenter;

import android.content.Context;

import com.utex.mvp.user.bean.LoginPreDTO;
import com.utex.mvp.user.bean.GTCodeVO;
import com.utex.mvp.user.bean.OptionVO;
import com.utex.mvp.user.bean.UserVO;

import java.util.List;

/**
 * Created by Demon on 2018/6/1.
 */
public interface ILoginPresenter {
    void setFragments(Context context, int resource);


    void switchPage(int page);

    void requestToken();

    void requestTokenSuccess();

    void requestTokenFail();

    void getUserInfo();

    void getUserInfoSuccess();

    void getUserInfoFail();

    void getGTCode(String account);

    void getGTCodeSuccess(GTCodeVO body);

    void loginPre(LoginPreDTO sendCodeDTO);

    void loginPreSuccess(UserVO body);

    void loginPreError(String str);

    void loginSuccess(String token);

    void loginError(String str);

    void sendLoginCodeSuccess();

    void sendLoginCodeError(String str);

    void getOption();

    void getOptionSuccess(List<OptionVO.DataBean> data);

    void getOptionError();

}
