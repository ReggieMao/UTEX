package com.utex.mvp.user.presenter;

import com.utex.mvp.mine.bean.PhoneCountryNumVO;
import com.utex.mvp.user.bean.GTCodeVO;
import com.utex.mvp.user.bean.SendCodeDTO;
import com.utex.mvp.user.bean.UserDTO;
import com.utex.mvp.user.bean.UserVerifyVO;

import java.util.ArrayList;

/**
 * Created by Demon on 2018/7/11.
 */
public interface ISetPwdPresenter {
    void getGTCode(String email);

    void getGTCodeSuccess(GTCodeVO body);

    void sendEmail(SendCodeDTO sendCodeDTO);

    void sendEmailSuccess();

    void sendEmailError(String str);

    void forgetPwdNext(String email, String code);

    void forgetPwdNextSuccess(String body);

    void forgetPwdComplete(UserVerifyVO.DataBean user, String pwd, String code);

    void forgetPwdCompleteSuccess();

    void forgetPwdCompleteError(String str);

    void confirmResetPwd(String oldPwd, String newPwd, String confirmNewPwd);

    void confirmResetPwdSuccess();


    void confirmResetPwdError(String message);

    void forgetPwdNextFail(String str);

    void getCountery();

    void getPhoneCountrySuccess(ArrayList<PhoneCountryNumVO.DataBean> data);

    void checkUser(UserDTO account);

    void failMessage(String s);

    void checkUserSuccess(UserVerifyVO.DataBean data);
}
