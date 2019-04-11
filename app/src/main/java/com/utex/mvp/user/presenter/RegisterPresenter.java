package com.utex.mvp.user.presenter;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.utex.data.ApiService;
import com.utex.mvp.user.bean.SendCodeDTO;
import com.utex.mvp.user.bean.GTCodeVO;
import com.utex.mvp.user.bean.UserDTO;
import com.utex.mvp.user.bean.UserVO;
import com.utex.mvp.user.model.IRegisterModel;
import com.utex.mvp.user.model.RegisterModel;
import com.utex.mvp.user.view.EmailRegisterFragment;
import com.utex.mvp.user.view.RegisterActivity;
import com.utex.mvp.user.view.IRegisterView;
import com.utex.mvp.user.view.PhoneRegisterFragment;
import com.utex.utils.FragmentSwitchUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * Created by Demon on 2018/6/2.
 */
public class RegisterPresenter implements IRegisterPresenter {

    private IRegisterView iRegisterView;

    private ApiService apiService;

    private IRegisterModel iRegisterModel;
    private FragmentSwitchUtils fragmentSwitchUtils;

    public RegisterPresenter(ApiService apiService, RegisterActivity activity) {
        this.apiService = apiService;
        this.iRegisterView = activity;
        iRegisterModel = new RegisterModel();
    }

    @Override
    public void getGTCode(String email) {
        iRegisterModel.getGTCode(apiService, email, this);
    }

    @Override
    public void getGTCodeSuccess(GTCodeVO body) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("success", 1);
            jsonObject.put("challenge", body.getData().getChallenge());
            jsonObject.put("gt", body.getData().getGt());
            jsonObject.put("new_captcha", true);
            iRegisterView.getEmailGTCodeSuccess(jsonObject);
        } catch (Exception e) {
            Log.e("TAG", "cause:" + e.getMessage());
        }
    }

    @Override
    public void sendEmail(SendCodeDTO sendCodeDTO) {
        iRegisterModel.sendEmail(apiService, sendCodeDTO, this);
    }

    @Override
    public void sendEmailSuccess(Response<com.alibaba.fastjson.JSONObject> response) {
        if (response.code() == 200) {
            iRegisterView.sendEmailSuccess();
        } else {
            iRegisterView.sendEmailError(null);
            try {
                String string = response.errorBody().string();
                com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(string, com.alibaba.fastjson.JSONObject.class);
                Integer message = jsonObject.getInteger("message");
                iRegisterView.registerFail(String.valueOf(message));
            } catch (IOException e) {
                Log.e("TAG", "cause :" + e.getMessage());
            }
        }
    }

    @Override
    public void sendEmailError(Throwable t) {
        iRegisterView.sendEmailError(t);
    }

    @Override
    public void register(UserDTO registerDTO) {
        iRegisterModel.register(apiService, registerDTO, this);
    }

    @Override
    public void getGTCodeFail() {
        iRegisterView.getGTCodeFail();
    }

    @Override
    public void setFragment(RegisterActivity registerActivity, int resource) {
        List<FragmentSwitchUtils.FragmentSwitchBean> fragments = new ArrayList<>();
        fragments.add(new FragmentSwitchUtils.FragmentSwitchBean(new PhoneRegisterFragment(), "phone"));
        fragments.add(new FragmentSwitchUtils.FragmentSwitchBean(new EmailRegisterFragment(), "email"));

        fragmentSwitchUtils = new FragmentSwitchUtils(registerActivity, fragments, resource);
        fragmentSwitchUtils.switchFragment(0);
    }

    @Override
    public void switchPage(int page) {
        if (fragmentSwitchUtils != null) {
            fragmentSwitchUtils.switchFragment(page);
        }
    }

    @Override
    public void registerSuccess(Response<UserVO> response) {
        Integer message = null;
        try {
            String string = response.errorBody().string();
            com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(string, com.alibaba.fastjson.JSONObject.class);
            message = jsonObject.getInteger("message");
        } catch (Exception e) {
            Log.e("TAG", "cause :" + e.getMessage());
        }
        if (response.code() == 200) {
            iRegisterView.registerSuccess(response);
        } else {
            iRegisterView.registerFail(String.valueOf(message));
        }
    }
}
