package com.utex.mvp.user.presenter;

import com.utex.mvp.mine.bean.PhoneCountryNumVO;
import com.utex.mvp.user.bean.SendCodeDTO;
import com.utex.mvp.user.bean.GTCodeVO;
import com.utex.mvp.user.bean.UserDTO;

import java.util.ArrayList;

/**
 * Created by Demon on 2018/6/2.
 */
public interface IPhoneRegisterPresenter {
    void getPhoneCountry();

    void getPhoneCountrySuccess(ArrayList<PhoneCountryNumVO.DataBean> data);

    void getGTCode(String phoneNumber);

    void failMessage(String errMessage);

    void getGTCodeSuccess(GTCodeVO body);

    void sendEmail(SendCodeDTO sendCodeDTO);

    void sendEmailSuccess();

    void register(UserDTO registerDTO);

    void registerSuccess();

}
