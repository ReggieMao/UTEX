package com.utex.mvp.mine.presenter;

import com.utex.mvp.mine.bean.GoogleVO;
import com.utex.mvp.mine.bean.PhoneCountryNumVO;
import com.utex.mvp.user.bean.SendCodeDTO;
import com.utex.mvp.user.bean.GTCodeVO;

import java.util.ArrayList;

/**
 * Created by Demon on 2018/7/25.
 */
public interface IBindingPagePresenter {
    void getGoogleBindInfo();

    void getGoogleBindInfoSuccess(GoogleVO body);

    void getGTCode();

    void getGTCodeSuccess(GTCodeVO body);

    void sendTelCode(SendCodeDTO sendCodeDTO);

    void sendTelCodeSuccess();

    void confirmBindPhone(String countryCode, String phoneNumber, String code);

    void confirmBindPhoneSuccess();

    void confirmBindPhoneFail(String str);

    void getPhoneCountry();

    void getPhoneCountrySuccess(ArrayList<PhoneCountryNumVO.DataBean> data);

    void getGTCodeFail(String message);

    void bindGoogle(String secretKey, String code);

    void bindGoogleSuccess();

    void bindGoogleFail();

    void bindEmail(String email, String code);

    void bindEmailSuccess();

    void failMessage(String errMessage);
}
