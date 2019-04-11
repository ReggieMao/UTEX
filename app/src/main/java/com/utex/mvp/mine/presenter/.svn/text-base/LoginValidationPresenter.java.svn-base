package com.utex.mvp.mine.presenter;

import com.utex.data.ApiService;
import com.utex.mvp.mine.model.ILoginValidationModel;
import com.utex.mvp.mine.model.LoginValidationModel;
import com.utex.mvp.mine.view.ILoginValidationView;
import com.utex.mvp.mine.view.LoginValidationSettingActivity;

/**
 * Created by Demon on 2018/7/25.
 */
public class LoginValidationPresenter implements ILoginValidationPresenter {

    private ApiService apiService;

    private ILoginValidationView iLoginValidationView;

    private ILoginValidationModel iLoginValidationModel;

    public LoginValidationPresenter(ApiService apiService, LoginValidationSettingActivity activity) {
        this.apiService = apiService;
        this.iLoginValidationView = activity;
        iLoginValidationModel = new LoginValidationModel();
    }

    @Override
    public void switchVerification(int type, boolean checked) {
        int status = 0;
        if (checked) {
            status = 1;
        } else {
            status = 0;
        }

        switch (type) {
            case 1:
                //手机
                iLoginValidationModel.switchPhoneStatus(apiService, status, this);
                break;
            case 2:
                //google
                iLoginValidationModel.switchGoogleStatus(apiService, status, this);
                break;
            case 3:
                //邮箱开启
                iLoginValidationModel.switchEmailStatus(apiService, status, this);
                break;
        }
    }

    @Override
    public void switchPhoneStatusSuccess() {
        iLoginValidationView.switchStatusSuccess();
    }

    @Override
    public void switchPhoneStatusFail(String message) {
        iLoginValidationView.switchStatusFail(message);
    }

    @Override
    public void switchGoogleStatusSuccess() {
        iLoginValidationView.switchStatusSuccess();
    }

    @Override
    public void switchGoogleStatusFail(String message) {
        iLoginValidationView.switchStatusFail(message);
    }

    @Override
    public void switchEmailStatusSuccess() {
        iLoginValidationView.switchStatusSuccess();
    }

    @Override
    public void switchEmailStatusFail(String message) {
        iLoginValidationView.switchStatusFail(message);
    }
}
