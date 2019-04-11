package com.utex.mvp.user.presenter;

import com.utex.mvp.user.bean.SendCodeDTO;
import com.utex.mvp.user.bean.GTCodeVO;

/**
 * Created by Demon on 2018/7/23.
 */
public interface IActivatePresenter {
    void getGTCode(String email);

    void getGTCodeSuccess(GTCodeVO body);

    void sendEmail(SendCodeDTO sendCodeDTO);

    void sendEmailSuccess();

    void activate(String email, String code);

    void activateSuccess();


    void iActivateFail();
}
