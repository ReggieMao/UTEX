package com.utex.mvp.mine.model;

import com.utex.data.ApiService;
import com.utex.mvp.mine.presenter.IBindingPagePresenter;
import com.utex.mvp.user.bean.SendCodeDTO;

/**
 * Created by Demon on 2018/7/25.
 */
public interface IBindingPageModel {

    void getGoogleBindInfo(ApiService apiService, IBindingPagePresenter iBindingPagePresenter);

    void getGTCode(ApiService apiService, String username, IBindingPagePresenter iBindingPagePresenter);

    void sendTelCode(ApiService apiService, SendCodeDTO sendCodeDTO, IBindingPagePresenter iBindingPagePresenter);

    void confirmBindPhone(ApiService apiService, String phone, String code, String countryCode, IBindingPagePresenter iBindingPagePresenter);

    void getPhoneCountry(ApiService apiService, IBindingPagePresenter iBindingPagePresenter);

    void bindGoogle(ApiService apiService, String secretKey, String code, IBindingPagePresenter iBindingPagePresenter);

    void bindEmail(ApiService apiService, String email, String code, IBindingPagePresenter iBindingPagePresenter);
}
