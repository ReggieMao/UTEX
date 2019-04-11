package com.utex.mvp.mine.model;

import com.utex.data.ApiService;
import com.utex.mvp.mine.presenter.IIdentityInputPresenter;

import java.util.List;

/**
 * Created by Demon on 2018/7/19.
 */
public interface IIdentityInputModel {
    void getAppOSSParam(ApiService apiService, IIdentityInputPresenter iIdentityInputPresenter);

    void otherCountrySumbit(ApiService apiService, List<String> images, String info1, String info2, String info3, String info4, IIdentityInputPresenter iIdentityInputPresenter);

    void chinaSumbit(ApiService apiService, List<String> images, String info1, String info2, IIdentityInputPresenter iIdentityInputPresenter);
}
