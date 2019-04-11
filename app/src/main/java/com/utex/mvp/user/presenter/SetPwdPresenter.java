package com.utex.mvp.user.presenter;

import android.util.Log;

import com.utex.core.UTEXApplication;
import com.utex.data.ApiService;
import com.utex.mvp.mine.bean.PhoneCountryNumVO;
import com.utex.mvp.user.bean.SendCodeDTO;
import com.utex.mvp.user.bean.GTCodeVO;
import com.utex.mvp.user.bean.UserDTO;
import com.utex.mvp.user.bean.UserVerifyVO;
import com.utex.mvp.user.model.ISetPwdModel;
import com.utex.mvp.user.model.SetPwdModel;
import com.utex.mvp.user.view.ISetPwdView;
import com.utex.mvp.user.view.SetPwdActivity;
import com.utex.utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Demon on 2018/7/11.
 */
public class SetPwdPresenter implements ISetPwdPresenter {

    private ApiService apiService;

    private ISetPwdView iSetPwdView;

    private ISetPwdModel iSetPwdModel;

    public SetPwdPresenter(ApiService apiService, SetPwdActivity activity) {
        this.apiService = apiService;
        this.iSetPwdView = activity;
        iSetPwdModel = new SetPwdModel();
    }

    @Override
    public void getGTCode(String email) {
        iSetPwdModel.getGTCode(apiService, email, this);
    }

    @Override
    public void getGTCodeSuccess(GTCodeVO body) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("success", 1);
            jsonObject.put("challenge", body.getData().getChallenge());
            jsonObject.put("gt", body.getData().getGt());
            jsonObject.put("new_captcha", true);
            iSetPwdView.getGTCodeSuccess(jsonObject);
        } catch (Exception e) {
            Log.e("TAG", "cause:" + e.getMessage());
        }
    }

    @Override
    public void sendEmail(SendCodeDTO sendCodeDTO) {
        iSetPwdModel.sendEmail(apiService, sendCodeDTO, this);
    }

    @Override
    public void sendEmailSuccess() {
        iSetPwdView.sendEmailSuccess();
    }

    @Override
    public void sendEmailError(String str) {
        iSetPwdView.sendEmailError(str);
    }

    @Override
    public void forgetPwdNext(String email, String code) {
        UTEXApplication.setUsername(email);
        iSetPwdModel.forgetPwdNext(apiService, email, code, this);
    }

    @Override
    public void forgetPwdNextSuccess(String token) {
        UTEXApplication.setToken(token);
        iSetPwdView.forgetPwdNextSuccess();
    }

    @Override
    public void forgetPwdComplete(UserVerifyVO.DataBean user, String pwd, String code) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(Utils.md5(pwd));
        userDTO.setRe_password(Utils.md5(pwd));
        userDTO.setPass_code(code);

        if (user.getUsername().contains("@")) {
            userDTO.setCarrier(UserDTO.EMAIL);
        } else {
            userDTO.setCarrier(UserDTO.TEL);
        }

        iSetPwdModel.forgetPwdComplete(apiService, userDTO, this);
    }

    @Override
    public void forgetPwdCompleteSuccess() {
        iSetPwdView.forgetPwdCompleteSuccess();
    }

    @Override
    public void forgetPwdCompleteError(String str) {
        iSetPwdView.forgetPwdCompleteError(str);
    }

    @Override
    public void confirmResetPwd(String oldPwd, String newPwd, String confirmNewPwd) {
        if (!newPwd.equals(confirmNewPwd)) {

            return;
        }
        iSetPwdModel.confirmResetPwd(apiService, oldPwd, newPwd, confirmNewPwd, this);

    }

    @Override
    public void confirmResetPwdSuccess() {
        iSetPwdView.confirmResetPwdSuccess();
    }

    @Override
    public void confirmResetPwdError(String message) {
        iSetPwdView.confirmResetPwdError(message);
    }

    @Override
    public void forgetPwdNextFail(String str) {
        iSetPwdView.forgetPwdNextFail(str);
    }

    @Override
    public void getCountery() {
        iSetPwdModel.getCountery(apiService, this);
    }

    @Override
    public void getPhoneCountrySuccess(ArrayList<PhoneCountryNumVO.DataBean> data) {
        iSetPwdView.getPhoneCountrySuccess(data);
    }

    @Override
    public void checkUser(UserDTO account) {
        iSetPwdModel.checkUser(apiService, account, this);
    }

    @Override
    public void failMessage(String s) {
        iSetPwdView.failMessage(s);
    }

    @Override
    public void checkUserSuccess(UserVerifyVO.DataBean data) {
        iSetPwdView.checkUserSuccess(data);
    }
}
