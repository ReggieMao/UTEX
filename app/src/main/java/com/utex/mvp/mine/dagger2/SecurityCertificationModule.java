package com.utex.mvp.mine.dagger2;

import com.utex.base.BaseModule;
import com.utex.data.ApiService;
import com.utex.mvp.mine.presenter.ISecurityCertificationPresenter;
import com.utex.mvp.mine.presenter.SecurityCertificationPresenter;
import com.utex.mvp.mine.view.ISecurityCertificationView;
import com.utex.mvp.mine.view.SetCenterActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by admin on 2017/4/21.
 */
@Module
public class SecurityCertificationModule extends BaseModule<SetCenterActivity, ISecurityCertificationView> {

    public SecurityCertificationModule(SetCenterActivity setCenterActivity) {
        super(setCenterActivity);
    }

    @Provides
    protected ISecurityCertificationPresenter bindPagePresenter(ApiService apiService) {
        return new SecurityCertificationPresenter(apiService, activity);
    }

}
