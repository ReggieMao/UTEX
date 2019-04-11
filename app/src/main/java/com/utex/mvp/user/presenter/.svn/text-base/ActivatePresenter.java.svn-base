package com.utex.mvp.user.presenter;

import android.util.Log;

import com.utex.data.ApiService;
import com.utex.mvp.user.bean.SendCodeDTO;
import com.utex.mvp.user.bean.GTCodeVO;
import com.utex.mvp.user.model.ActivateModel;
import com.utex.mvp.user.model.IActivateModel;
import com.utex.mvp.user.view.ActivateActivity;
import com.utex.mvp.user.view.IActivateView;

import org.json.JSONObject;

/**
 * Created by Demon on 2018/7/23.
 */
public class ActivatePresenter implements IActivatePresenter {

    private ApiService apiService;

    private IActivateView iActivateView;

    private IActivateModel iActivateModel;

    public ActivatePresenter(ApiService apiService, ActivateActivity activity) {
        this.apiService = apiService;
        this.iActivateView = activity;
        iActivateModel = new ActivateModel();
    }

    @Override
    public void getGTCode(String email) {
        iActivateModel.getGTCode(apiService, email, this);

    }

    @Override
    public void getGTCodeSuccess(GTCodeVO body) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("success", 1);
            jsonObject.put("challenge", body.getData().getChallenge());
            jsonObject.put("gt", body.getData().getGt());
            jsonObject.put("new_captcha", true);
            iActivateView.getGTCodeSuccess(jsonObject);
        } catch (Exception e) {
            Log.e("TAG", "cause:" + e.getMessage());
        }
    }

    @Override
    public void sendEmail(SendCodeDTO sendCodeDTO) {
        iActivateModel.sendEmail(apiService, sendCodeDTO, this);
    }

    @Override
    public void sendEmailSuccess() {
        iActivateView.sendEmailSuccess();
    }

    @Override
    public void activate(String email, String code) {
        iActivateModel.activate(apiService, email, code, this);

    }

    @Override
    public void activateSuccess() {
        iActivateView.activateSuccess();
    }

    @Override
    public void iActivateFail() {
        iActivateView.iActivateFail();
    }
}
