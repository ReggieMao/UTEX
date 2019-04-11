package com.utex.mvp.user.presenter;

import android.util.Log;

import com.utex.data.ApiService;
import com.utex.mvp.user.bean.GTCodeVO;
import com.utex.mvp.user.bean.SendCodeDTO;
import com.utex.mvp.user.bean.UserDTO;
import com.utex.mvp.user.model.EmailRegisterModel;
import com.utex.mvp.user.model.IEmailRegisterModel;
import com.utex.mvp.user.view.EmailRegisterFragment;
import com.utex.mvp.user.view.IEmailRegisterView;

/**
 * Created by Demon on 2018/11/20.
 */
public class EmailRegisterPresenter implements IEmailRegisterPresenter {

    private IEmailRegisterView iEmailRegisterView;

    private ApiService apiService;

    private IEmailRegisterModel iEmailRegisterModel;

    public EmailRegisterPresenter(ApiService apiService, EmailRegisterFragment emailRegisterFragment) {
        this.apiService = apiService;
        this.iEmailRegisterView = emailRegisterFragment;
        iEmailRegisterModel = new EmailRegisterModel();
    }

    @Override
    public void getGTCode(String email) {
        iEmailRegisterModel.getGTCode(apiService, email, this);
    }


    @Override
    public void sendEmail(SendCodeDTO sendCodeDTO) {
        iEmailRegisterModel.sendEmail(apiService, sendCodeDTO, this);
    }

    @Override
    public void register(UserDTO registerDTO) {
        iEmailRegisterModel.register(apiService, registerDTO, this);
    }

    @Override
    public void getGTCodeSuccess(GTCodeVO body) {
        try {
            org.json.JSONObject jsonObject = new org.json.JSONObject();
            jsonObject.put("success", 1);
            jsonObject.put("challenge", body.getData().getChallenge());
            jsonObject.put("gt", body.getData().getGt());
            jsonObject.put("new_captcha", true);
            iEmailRegisterView.getEmailGTCodeSuccess(jsonObject);
        } catch (Exception e) {
            Log.e("TAG", "cause:" + e.getMessage());
        }
    }

    @Override
    public void getGTCodeFail() {
        iEmailRegisterView.getGTCodeFail();
    }

    @Override
    public void sendEmailSuccess() {
        iEmailRegisterView.sendEmailSuccess();
    }

    @Override
    public void sendEmailError(Throwable t) {
        iEmailRegisterView.sendEmailError(t);
    }


    @Override
    public void registerFail(String message) {
        iEmailRegisterView.registerFail(message);
    }


    @Override
    public void registerSuccess() {
        iEmailRegisterView.registerSuccess();
    }


}
