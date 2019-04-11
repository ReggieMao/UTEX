package com.utex.mvp.user.view;

import com.utex.mvp.mine.bean.PhoneCountryNumVO;
import com.utex.mvp.user.bean.UserVerifyVO;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Demon on 2018/7/11.
 */
public interface ISetPwdView {
    void getGTCodeSuccess(JSONObject jsonObject);

    void sendEmailSuccess();

    void sendEmailError(String str);

    void forgetPwdNextSuccess();

    void forgetPwdCompleteSuccess();

    void forgetPwdCompleteError(String str);

    void confirmResetPwdSuccess();

    void confirmResetPwdError(String message);

    void forgetPwdNextFail(String str);

    void getPhoneCountrySuccess(ArrayList<PhoneCountryNumVO.DataBean> data);

    void failMessage(String s);

    void checkUserSuccess(UserVerifyVO.DataBean data);
}
