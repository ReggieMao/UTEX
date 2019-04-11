package com.utex.mvp.mine.model;

import com.alibaba.fastjson.JSONObject;
import com.utex.core.UTEXApplication;
import com.utex.data.ApiService;
import com.utex.mvp.mine.presenter.ISecurityCertificationPresenter;
import com.utex.mvp.user.bean.UserDTO;
import com.utex.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Demon on 2018/8/6.
 */
public class SecurityCertificationModel implements ISecurityCertificationModel {

    @Override
    public void checkPassword(ApiService apiService, String pwd, final ISecurityCertificationPresenter iSecurityCertificationPresenter) {
        UserDTO useDTO = new UserDTO();
        useDTO.setPassword(Utils.md5(pwd));
        useDTO.setUsername(UTEXApplication.getUsername());
        apiService.checkPassword(useDTO)
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            iSecurityCertificationPresenter.checkPasswordSuccess();
                        } else {
                            iSecurityCertificationPresenter.checkPasswordFail();
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        iSecurityCertificationPresenter.checkPasswordFail();
                    }
                });
    }

}
