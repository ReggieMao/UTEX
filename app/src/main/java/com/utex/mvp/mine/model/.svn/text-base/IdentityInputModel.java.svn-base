package com.utex.mvp.mine.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.utex.data.ApiService;
import com.utex.mvp.mine.bean.IdentitySumbitDTO;
import com.utex.mvp.mine.presenter.IIdentityInputPresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Demon on 2018/7/19.
 */
public class IdentityInputModel implements IIdentityInputModel {


    @Override
    public void getAppOSSParam(ApiService apiService, final IIdentityInputPresenter iIdentityInputPresenter) {
//        /app/oss
        apiService.getAppOSSParam()
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            String data = response.body().getString("data");
                            JSONObject jsonObject = JSON.parseObject(data, JSONObject.class);
                            String expiration = jsonObject.getString("Expiration");
                            String accessKeyId = jsonObject.getString("AccessKeyId");
                            String securityToken = jsonObject.getString("SecurityToken");
                            String accessKeySecret = jsonObject.getString("AccessKeySecret");

                            iIdentityInputPresenter.getAppOSSParamSuccess(accessKeyId, securityToken, accessKeySecret, expiration);
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {

                    }
                });
    }

    @Override
    public void otherCountrySumbit(ApiService apiService, List<String> images, String info1, String info2, String info3, String info4, final IIdentityInputPresenter iIdentityInputPresenter) {
        IdentitySumbitDTO identitySumbitDTO = new IdentitySumbitDTO();
        identitySumbitDTO.setLast_name(info1);
        identitySumbitDTO.setFirst_name(info1);
        
        identitySumbitDTO.setGender(info2);

        identitySumbitDTO.setCountry(info3);
        identitySumbitDTO.setId_number(info4);
        identitySumbitDTO.setFront_end(images.get(0));
        identitySumbitDTO.setBack_end(images.get(1));
        identitySumbitDTO.setCustom_end(images.get(2));
        apiService.otherCountrySumbit(identitySumbitDTO)
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            iIdentityInputPresenter.identitySuccess();
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {

                    }
                });
    }

    @Override
    public void chinaSumbit(ApiService apiService, List<String> images, String info1, String info2, final IIdentityInputPresenter iIdentityInputPresenter) {
        IdentitySumbitDTO identitySumbitDTO = new IdentitySumbitDTO();
        identitySumbitDTO.setName(info1);
        identitySumbitDTO.setId_number(info2);
        identitySumbitDTO.setFront_end(images.get(0));
        identitySumbitDTO.setBack_end(images.get(1));
        identitySumbitDTO.setCustom_end(images.get(2));
        apiService.chinaSumbit(identitySumbitDTO)
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            iIdentityInputPresenter.identitySuccess();
                        } else {
                            iIdentityInputPresenter.identityFail();
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        iIdentityInputPresenter.identityFail();
                    }
                });
    }
}
