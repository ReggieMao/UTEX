package com.utex.mvp.user.view;

import org.json.JSONObject;

/**
 * Created by Demon on 2018/7/23.
 */
public interface IActivateView {
    void getGTCodeSuccess(JSONObject jsonObject);

    void sendEmailSuccess();

    void activateSuccess();

    void iActivateFail();
}

