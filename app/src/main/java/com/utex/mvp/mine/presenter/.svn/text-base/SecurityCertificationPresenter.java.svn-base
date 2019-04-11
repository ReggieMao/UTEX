package com.utex.mvp.mine.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.utex.R;
import com.utex.data.ApiService;
import com.utex.mvp.mine.model.ISecurityCertificationModel;
import com.utex.mvp.mine.model.SecurityCertificationModel;
import com.utex.mvp.mine.view.ISecurityCertificationView;
import com.utex.mvp.mine.view.SetCenterActivity;
import com.utex.utils.Utils;
import com.utex.widget.popuwindow.LoginBottomPopupWindow;

/**
 * Created by Demon on 2018/8/6.
 */
public class SecurityCertificationPresenter implements ISecurityCertificationPresenter {

    private ApiService apiService;

    private ISecurityCertificationView iSecurityCertificationView;

    private Context context;

    private ISecurityCertificationModel iSecurityCertificationModel;

    private LoginBottomPopupWindow loginBottomPopupWindow;

    public SecurityCertificationPresenter(ApiService apiService, SetCenterActivity activity) {
        this.apiService = apiService;
        this.iSecurityCertificationView = activity;
        this.context = activity;
        this.iSecurityCertificationModel = new SecurityCertificationModel();
    }

    @Override
    public void checkPassword(int res) {
        //检查密码
        loginBottomPopupWindow = new LoginBottomPopupWindow(context, res);
        TextView tvLoginSecondVerificationTitle = loginBottomPopupWindow.getTvLoginSecondVerificationTitle();
        TextView tvLoginSecondVerificationTip = loginBottomPopupWindow.getTvLoginSecondVerificationTip();
        EditText etLoginSecondVerificatiionCode = loginBottomPopupWindow.getEtLoginSecondVerificatiionCode();
        TextView tvLoginSecondVerificationSecond = loginBottomPopupWindow.getTvLoginSecondVerificationSecond();
        tvLoginSecondVerificationTitle.setText(Utils.getResourceString(R.string.jie_suo_qie_huan_yan_zheng));
        tvLoginSecondVerificationTip.setVisibility(View.GONE);
        etLoginSecondVerificatiionCode.setHint(Utils.getResourceString(R.string.mi_ma));
        tvLoginSecondVerificationSecond.setVisibility(View.GONE);

        loginBottomPopupWindow.getTvLoginSecondVerificationComplete().setOnClickListener(view -> {
            loginBottomPopupWindow.dimss();

            if (!TextUtils.isEmpty(loginBottomPopupWindow.getEtLoginSecondVerificatiionCode().getText())) {
                iSecurityCertificationModel.checkPassword(apiService,
                        loginBottomPopupWindow.getEtLoginSecondVerificatiionCode().getText().toString(),
                        SecurityCertificationPresenter.this);
            }
            loginBottomPopupWindow.setLoginBottomListener(null);
            loginBottomPopupWindow.dimss();

            iSecurityCertificationView.keyboardDiss();
        });

        loginBottomPopupWindow.getTvLoginSecondVerificatiionCannel().setOnClickListener(view -> loginBottomPopupWindow.dimss());

        loginBottomPopupWindow.setLoginBottomListener(new LoginBottomPopupWindow.LoginBottomListener() {
            @Override
            public void onclick(int type) {

            }

            @Override
            public void onDismiss() {
                iSecurityCertificationView.popupWindowDismiss();
            }
        });

    }

    @Override
    public void checkPasswordFail() {
        iSecurityCertificationView.checkPasswordFail();
    }

    @Override
    public void checkPasswordSuccess() {
        iSecurityCertificationView.checkPasswordSuccess();
    }
}
