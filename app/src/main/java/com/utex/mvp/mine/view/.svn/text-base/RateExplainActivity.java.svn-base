package com.utex.mvp.mine.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.utex.R;
import com.utex.base.BaseActivity;
import com.utex.common.FiledConstants;
import com.utex.core.AppComponent;
import com.utex.core.UTEXApplication;
import com.utex.mvp.mine.bean.WithdrawalFeeVo;
import com.utex.mvp.mine.dagger2.DaggerRateEexplainComponent;
import com.utex.mvp.mine.dagger2.RateEexplainModule;
import com.utex.mvp.mine.presenter.IRateExplainPresenter;
import com.utex.mvp.web.WebActivity;
import com.utex.utils.Utils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RateExplainActivity extends BaseActivity implements IRateExplainView {

    @Inject
    IRateExplainPresenter iRateExplainPresenter;

    @BindView(R.id.rv_rate_explain_list)
    RecyclerView rvRateExplainList;

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolBartitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_explain);
        ButterKnife.bind(this);

        iRateExplainPresenter.getRateData();
        tvToolBartitle.setText(Utils.getResourceString(R.string.fei_lv_shuo_ming));
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerRateEexplainComponent
                .builder()
                .appComponent(appComponent)
                .rateEexplainModule(new RateEexplainModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.iv_toolbar_left, R.id.tv_hui_yuan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_left:
                finish();
                break;
            case R.id.tv_hui_yuan:
                //申请会员
                String notice = "";
                switch (UTEXApplication.getCurrLanguage()) {
                    case 0: //简体
                    case 2: //繁体
                        notice = "http://qgso1h3i8wz7zf3b.mikecrm.com/yDLelSX";
                        break;
                    case 1: //英文
                        notice = "http://qgso1h3i8wz7zf3b.mikecrm.com/0VKmeYt";
                        break;
                }
                Intent noticeWeb = new Intent(this, WebActivity.class);
                noticeWeb.putExtra(FiledConstants.LINK_URL, notice);
                startActivity(noticeWeb);
                break;
        }
    }

    @Override
    public void getRateDataSuccess(List<WithdrawalFeeVo.DataBean> data) {
        rvRateExplainList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvRateExplainList.setAdapter(new RateExplainAdapter(data));
    }
}
