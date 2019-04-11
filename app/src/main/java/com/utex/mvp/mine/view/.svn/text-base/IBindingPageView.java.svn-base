package com.utex.mvp.mine.view;

import com.utex.mvp.mine.bean.GoogleVO;
import com.utex.mvp.mine.bean.PhoneCountryNumVO;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Demon on 2018/7/25.
 */
public interface IBindingPageView {
    void getGoogleBindInfoSuccess(GoogleVO body);

    void getEmailGTCodeSuccess(JSONObject jsonObject);

    void sendTelCodeSuccess();

    void confirmBindPhoneSuccess();

    void confirmBindPhoneFail(String str);


    void getPhoneCountrySuccess(ArrayList<PhoneCountryNumVO.DataBean> data);

    void getGTCodeFail(String message);

    void bindGoogleSuccess();

    void bindGoogleFail();

    void bindEmailSuccess();

    void failMessage(String errMessage);
}
