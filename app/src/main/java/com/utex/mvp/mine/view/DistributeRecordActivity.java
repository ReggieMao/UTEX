package com.utex.mvp.mine.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.utex.R;
import com.utex.base.BaseActivity;
import com.utex.core.AppComponent;
import com.utex.mvp.mine.bean.DistributeRecordVO;
import com.utex.mvp.mine.dagger2.DaggerDistributeRecordComponent;
import com.utex.mvp.mine.dagger2.DistributeRecordModule;
import com.utex.mvp.mine.presenter.IDistributeRecordPresenter;
import com.utex.utils.Utils;
import com.utex.widget.smartrefresh.layout.SmartRefreshLayout;
import com.utex.widget.smartrefresh.layout.api.RefreshLayout;
import com.utex.widget.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 分发记录
 */
public class DistributeRecordActivity extends BaseActivity implements IDistributeRecordView, OnRefreshLoadMoreListener {

    @Inject
    IDistributeRecordPresenter iDistributeRecordPresenter;

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;

    @BindView(R.id.rv_distribute_record_list)
    RecyclerView rvDistributeRecordList;

    @BindView(R.id.refresh_list)
    SmartRefreshLayout refreshList;

    private DistributeRecordAdapter distributeRecordAdapter;

    private int page = 1;

    private boolean isLoadMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribute_record);
        ButterKnife.bind(this);

        tvToolbarTitle.setText(Utils.getResourceString(R.string.fen_fa_ji_lu));
        rvDistributeRecordList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        distributeRecordAdapter = new DistributeRecordAdapter();
        rvDistributeRecordList.setAdapter(distributeRecordAdapter);

        refreshList.setOnRefreshLoadMoreListener(this);

        iDistributeRecordPresenter.getDistributeRecordData(page);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerDistributeRecordComponent
                .builder()
                .appComponent(appComponent)
                .distributeRecordModule(new DistributeRecordModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.iv_toolbar_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_left:
                finish();
                break;
        }
    }

    @Override
    public void getDistributeRecordDataSuccess(List<DistributeRecordVO.DataBean> data) {
        distributeRecordAdapter.setData(data, isLoadMore);
        isLoadMore = false;
    }

    @Override
    public void failMessage(String errMessage) {
        Utils.showMessage(errMessage);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        isLoadMore = true;
        iDistributeRecordPresenter.getDistributeRecordData(page);
        refreshList.finishLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        iDistributeRecordPresenter.getDistributeRecordData(page);
        refreshList.finishRefresh();
    }
}
