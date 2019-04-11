package com.utex.mvp.mine.presenter;

import android.util.Log;

import com.utex.core.UTEXApplication;
import com.utex.data.ApiService;
import com.utex.mvp.mine.bean.GoogleVO;
import com.utex.mvp.mine.bean.PhoneCountryNumVO;
import com.utex.mvp.mine.model.BindingPageModel;
import com.utex.mvp.mine.model.IBindingPageModel;
import com.utex.mvp.mine.view.BindingPageActivity;
import com.utex.mvp.mine.view.IBindingPageView;
import com.utex.mvp.user.bean.SendCodeDTO;
import com.utex.mvp.user.bean.GTCodeVO;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Demon on 2018/7/25.
 */
public class BindingPagePresenter implements IBindingPagePresenter {

    private ApiService apiService;

    private IBindingPageView iBindingPageView;

    private IBindingPageModel iBindingPageModel;

    public BindingPagePresenter(ApiService apiService, BindingPageActivity activity) {
        this.apiService = apiService;
        this.iBindingPageView = activity;
        iBindingPageModel = new BindingPageModel();
    }

    @Override
    public void getGoogleBindInfo() {
        iBindingPageModel.getGoogleBindInfo(apiService, this);
    }

    @Override
    public void getGoogleBindInfoSuccess(GoogleVO body) {
        iBindingPageView.getGoogleBindInfoSuccess(body);
    }

    @Override
    public void getGTCode() {
        iBindingPageModel.getGTCode(apiService, UTEXApplication.getUsername(), this);
    }

    @Override
    public void getGTCodeSuccess(GTCodeVO body) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("success", 1);
            jsonObject.put("challenge", body.getData().getChallenge());
            jsonObject.put("gt", body.getData().getGt());
            jsonObject.put("new_captcha", true);
            iBindingPageView.getEmailGTCodeSuccess(jsonObject);
        } catch (Exception e) {
            Log.e("TAG", "cause:" + e.getMessage());
        }
    }

    @Override
    public void sendTelCode(SendCodeDTO sendCodeDTO) {
        iBindingPageModel.sendTelCode(apiService, sendCodeDTO, this);
    }

    @Override
    public void sendTelCodeSuccess() {
        iBindingPageView.sendTelCodeSuccess();
    }

    @Override
    public void confirmBindPhone(String countryCode, String phone, String code) {
        iBindingPageModel.confirmBindPhone(apiService, phone, code, countryCode, this);
    }

    @Override
    public void confirmBindPhoneSuccess() {
        iBindingPageView.confirmBindPhoneSuccess();
    }

    @Override
    public void confirmBindPhoneFail(String str) {
        iBindingPageView.confirmBindPhoneFail(str);
    }

    @Override
    public void getPhoneCountry() {
        iBindingPageModel.getPhoneCountry(apiService, this);
    }

    @Override
    public void getPhoneCountrySuccess(ArrayList<PhoneCountryNumVO.DataBean> data) {
        iBindingPageView.getPhoneCountrySuccess(data);
    }

    @Override
    public void getGTCodeFail(String message) {
        iBindingPageView.getGTCodeFail(message);
    }

    @Override
    public void bindGoogle(String secretKey, String code) {
        //秘钥 code
        iBindingPageModel.bindGoogle(apiService, secretKey, code, this);
    }

    @Override
    public void bindGoogleSuccess() {
        iBindingPageView.bindGoogleSuccess();
    }

    @Override
    public void bindGoogleFail() {
        iBindingPageView.bindGoogleFail();
    }

    @Override
    public void bindEmail(String email, String code) {
        iBindingPageModel.bindEmail(apiService, email, code, this);
    }

    @Override
    public void bindEmailSuccess() {
        iBindingPageView.bindEmailSuccess();
    }

    @Override
    public void failMessage(String errMessage) {
        iBindingPageView.failMessage(errMessage);
    }
}
