package com.utex.mvp.user.model;

import com.utex.data.ApiService;
import com.utex.mvp.user.bean.SendCodeDTO;
import com.utex.mvp.user.presenter.IActivatePresenter;

/**
 * Created by Demon on 2018/7/23.
 */
public interface IActivateModel {
    void getGTCode(ApiService apiService, String email, IActivatePresenter iActivatePresenter);

    void sendEmail(ApiService apiService, SendCodeDTO sendCodeDTO, IActivatePresenter iActivatePresenter);

    void activate(ApiService apiService, String email, String code, IActivatePresenter iActivatePresenter);
}
