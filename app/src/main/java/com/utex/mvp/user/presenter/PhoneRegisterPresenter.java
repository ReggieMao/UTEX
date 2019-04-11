package com.utex.mvp.user.presenter;

import android.util.Log;
import android.widget.TextView;

import com.utex.data.ApiService;
import com.utex.mvp.mine.bean.PhoneCountryNumVO;
import com.utex.mvp.user.bean.SendCodeDTO;
import com.utex.mvp.user.bean.GTCodeVO;
import com.utex.mvp.user.bean.UserDTO;
import com.utex.mvp.user.bean.UserVO;
import com.utex.mvp.user.model.IPhoneRegisterModel;
import com.utex.mvp.user.model.PhoneRegisterModel;
import com.utex.mvp.user.view.IPhoneRegisterView;
import com.utex.mvp.user.view.PhoneRegisterFragment;

import java.util.ArrayList;

/**
 * Created by Demon on 2018/6/2.
 */
public class PhoneRegisterPresenter implements IPhoneRegisterPresenter {

    private PhoneRegisterFragment fragment;

    private ApiService apiService;

    private IPhoneRegisterView iPhoneRegisterView;

    private IPhoneRegisterModel iPhoneRegisterModel;

    private TextView tvLoginSecondVerificationSecond;

    private UserVO body;

    public PhoneRegisterPresenter(ApiService apiService, PhoneRegisterFragment activity) {
        this.apiService = apiService;
        iPhoneRegisterView = activity;
        iPhoneRegisterModel = new PhoneRegisterModel();
        this.fragment = activity;
    }

    @Override
    public void getPhoneCountry() {
        iPhoneRegisterModel.getPhoneCountry(apiService, this);
    }

    @Override
    public void getPhoneCountrySuccess(ArrayList<PhoneCountryNumVO.DataBean> data) {
        iPhoneRegisterView.getPhoneCountrySuccess(data);
    }

    @Override
    public void getGTCode(String phoneNumber) {
        iPhoneRegisterModel.getGTCode(apiService, phoneNumber, this);
    }

    @Override
    public void failMessage(String errMessage) {
        iPhoneRegisterView.failMessage(errMessage);
    }

    @Override
    public void getGTCodeSuccess(GTCodeVO body) {
        try {
            org.json.JSONObject jsonObject = new org.json.JSONObject();
            jsonObject.put("success", 1);
            jsonObject.put("challenge", body.getData().getChallenge());
            jsonObject.put("gt", body.getData().getGt());
            jsonObject.put("new_captcha", true);
            iPhoneRegisterView.getEmailGTCodeSuccess(jsonObject);
        } catch (Exception e) {
            Log.e("TAG", "cause:" + e.getMessage());
        }
    }

    @Override
    public void sendEmail(SendCodeDTO sendCodeDTO) {
        iPhoneRegisterModel.sendEmail(apiService, sendCodeDTO, this);
    }

    @Override
    public void sendEmailSuccess() {
        iPhoneRegisterView.sendEmailSuccess();
    }

    @Override
    public void register(UserDTO registerDTO) {
        iPhoneRegisterModel.register(apiService, registerDTO, this);
    }

    @Override
    public void registerSuccess() {
        iPhoneRegisterView.registerSuccess();
    }
}
