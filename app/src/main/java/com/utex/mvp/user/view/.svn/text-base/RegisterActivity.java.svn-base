package com.utex.mvp.user.view;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.utex.R;
import com.utex.base.BaseActivity;
import com.utex.core.AppComponent;
import com.utex.mvp.user.bean.UserVO;
import com.utex.mvp.user.dagger2.DaggerRegisterComponent;
import com.utex.mvp.user.dagger2.RegisterModule;
import com.utex.mvp.user.presenter.IRegisterPresenter;
import com.utex.utils.Utils;

import org.json.JSONObject;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, IRegisterView {

    @Inject
    IRegisterPresenter iRegisterPresenter;

    @BindView(R.id.fl_register_body_parent)
    FrameLayout flResgiterBodyParent;

    @BindView(R.id.rn_user_tab)
    RadioGroup rnUserTab;

    @BindView(R.id.tv_user_go_login)
    TextView tvUserGoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        iRegisterPresenter.setFragment(this, R.id.fl_register_body_parent);
        rnUserTab.setOnCheckedChangeListener(this);


        Utils.setFontSpan(tvUserGoLogin, new String[]{Utils.getResourceString(R.string.yi_you_zhang_hao),
                        Utils.getResourceString(R.string.deng_lu)}, R.color.b999999_66ffffff,
                R.color.f398155);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerRegisterComponent
                .builder()
                .appComponent(appComponent)
                .registerModule(new RegisterModule(this))
                .build()
                .inject(this);
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        int page = 0;
        switch (i) {
            case R.id.rb_register_phone:
                page = 0;
//                MobclickAgent.onEvent(this, UMConstants.LOGIN_PAGE_EMIL_LOGIN_BTN);
                break;
            case R.id.rb_register_email:
                page = 1;
//                MobclickAgent.onEvent(this, UMConstants.LOGIN_PAGE_REGISTER_BTN);
                break;
        }
        iRegisterPresenter.switchPage(page);
    }

    @OnClick({R.id.tv_user_go_login, R.id.iv_user_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user_back:
                finish();
                break;
            case R.id.tv_user_go_login:
                finish();
                break;
        }
    }

    @Override
    public void getEmailGTCodeSuccess(JSONObject jsonObject) {

    }

    @Override
    public void sendEmailSuccess() {

    }

    @Override
    public void sendEmailError(Throwable t) {

    }

    @Override
    public void registerSuccess(Response<UserVO> response) {

    }

    @Override
    public void registerFail(String message) {

    }

    @Override
    public void getGTCodeFail() {

    }
}
