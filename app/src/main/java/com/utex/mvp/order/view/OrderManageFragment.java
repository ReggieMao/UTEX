package com.utex.mvp.order.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.utex.R;
import com.utex.base.BaseFragment;
import com.utex.common.FiledConstants;
import com.utex.core.AppComponent;
import com.utex.mvp.order.bean.DepositWithdrawalVO;
import com.utex.mvp.order.bean.OrderManageVO;
import com.utex.mvp.order.bean.StationVO;
import com.utex.mvp.order.bean.VolVO;
import com.utex.mvp.order.dagger2.DaggerOrderManageComponent;
import com.utex.mvp.order.dagger2.OrderManageModule;
import com.utex.mvp.order.presenter.IOrderManagePresenter;
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
 * Created by Demon on 2018/6/5.
 */
public class OrderManageFragment extends BaseFragment implements IOrderManageView {

    @Inject
    IOrderManagePresenter iOrderManagePresenter;

    @BindView(R.id.rv_order_list)
    RecyclerView rvOrderList;

    @BindView(R.id.rl_mine_filter_parent)
    RelativeLayout rlMineFilterParent;

    @BindView(R.id.et_filter_coin)
    EditText etFilterCoin;

    @BindView(R.id.et_filter_base)
    EditText etFilterBase;

    @BindView(R.id.rb_mine_filter_1)
    RadioButton rbMineFilter1;

    @BindView(R.id.iv_mine_filter_1)
    ImageView ivMineFilter1;

    @BindView(R.id.rb_mine_filter_2)
    RadioButton rbMineFilter2;

    @BindView(R.id.iv_mine_filter_2)
    ImageView ivMineFilter2;

    @BindView(R.id.rb_mine_filter_3)
    RadioButton rbMineFilter3;

    @BindView(R.id.iv_mine_filter_3)
    ImageView ivMineFilter3;

    @BindView(R.id.cb_mine_filter_hide)
    CheckBox cbMineFilterHide;

    @BindView(R.id.tv_mine_filter_reset)
    TextView tvMineFilterReset;

    @BindView(R.id.tv_mine_filter_confirm)
    TextView tvMineFilterConfirm;

    @BindView(R.id.rl_mine_money_filter_parent)
    RelativeLayout rlMineMoneyFilterParent;

    @BindView(R.id.et_mine_money_filter_coin)
    EditText etMineMoneyFilterCoin;

    @BindView(R.id.rb_mine_money_filter_1)
    RadioButton rbMineMoneyFilter1;

    @BindView(R.id.rb_mine_money_filter_2)
    RadioButton rbMineMoneyFilter2;

    @BindView(R.id.rb_mine_money_filter_3)
    RadioButton rbMineMoneyFilter3;

    @BindView(R.id.rb_mine_money_filter_4)
    RadioButton rbMineMoneyFilter4;

    @BindView(R.id.rb_mine_money_filter_5)
    RadioButton rbMineMoneyFilter5;

    @BindView(R.id.rb_mine_money_filter_6)
    RadioButton rbMineMoneyFilter6;

    @BindView(R.id.iv_mine_money_filter_1)
    ImageView ivMineMoneyFilter1;

    @BindView(R.id.iv_mine_money_filter_2)
    ImageView ivMineMoneyFilter2;

    @BindView(R.id.iv_mine_money_filter_3)
    ImageView ivMineMoneyFilter3;

    @BindView(R.id.iv_mine_money_filter_4)
    ImageView ivMineMoneyFilter4;

    @BindView(R.id.iv_mine_money_filter_5)
    ImageView ivMineMoneyFilter5;

    @BindView(R.id.iv_mine_money_filter_6)
    ImageView ivMineMoneyFilter6;

    @BindView(R.id.ll_mine_money_filter_2_parent)
    LinearLayout llMineMoneyFilter2Parent;

    @BindView(R.id.refresh_list)
    SmartRefreshLayout smartRefreshLayout;

    @BindView(R.id.ll_page_empty)
    LinearLayout llPageEmpty;

    @BindView(R.id.tv_order_bach_cannel_order)
    TextView tvOrderBachCannelOrder;

    /**
     * 0 当前委托
     * 1 历史委托
     * 2 我的成交
     * 3 充值记录
     * 4 提现记录
     * 5 站内记录
     */
    private int type;

    private String market;

    /**
     * <p>
     * 委单
     * null 全部
     * 1 卖
     * 2 买
     * </p>
     * <p>
     * 提
     * null 全部
     * 1 待审核
     * 2 已审核
     * 3 已发送
     * 4 已撤销
     * 5 审核失败
     * </p>
     * <p>
     * 充
     * null 全部
     * 1 确认中
     * 2 已到账
     * </p>
     */
    private Integer status;

    private OrderManageAdapter orderManageAdapter;

    private int page = 1;

    private int pageSize = 10;

    private boolean isLoadMore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, inflate);
        Bundle arguments = getArguments();
        type = arguments.getInt(FiledConstants.ORDER_TYPE);

        if (type == 2) {
            cbMineFilterHide.setVisibility(View.GONE);
        } else if (type == 3) {
            llMineMoneyFilter2Parent.setVisibility(View.GONE);
        }

        if (type == 0) {
            tvOrderBachCannelOrder.setVisibility(View.VISIBLE);
        } else {
            tvOrderBachCannelOrder.setVisibility(View.GONE);
        }


        etFilterBase.setKeyListener(null);

        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvOrderList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        orderManageAdapter = new OrderManageAdapter(type);
        rvOrderList.setAdapter(orderManageAdapter);
        iOrderManagePresenter.getData(type, market, status, false, page, pageSize);
        switch (type) {
            case 0:
                cbMineFilterHide.setVisibility(View.GONE);
                orderManageAdapter.setOrderClickListener((user_order_id, market, user_id) -> iOrderManagePresenter.cannelOrder(user_order_id, market, user_id));

                break;
            case 3:
                rbMineMoneyFilter2.setText(Utils.getResourceString(R.string.que_ren_zhong));
                rbMineMoneyFilter3.setText(Utils.getResourceString(R.string.yi_dao_zhang));
                orderManageAdapter.setWithdrawalListener(intent -> startActivityForResult(intent, 199));
                break;
            case 4:
                orderManageAdapter.setWithdrawalListener(intent -> startActivityForResult(intent, 199));
                break;
            case 5:
                rbMineMoneyFilter2.setText(Utils.getResourceString(R.string.zhuan_chu));
                rbMineMoneyFilter3.setText(Utils.getResourceString(R.string.zhuan_ru));
                rbMineMoneyFilter4.setVisibility(View.GONE);
                rbMineMoneyFilter5.setVisibility(View.GONE);
                rbMineMoneyFilter6.setVisibility(View.GONE);
                break;
        }

        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                加载更多
                Log.e("TAG", "加载更多");
                boolean isHide = cbMineFilterHide.isChecked();

                page++;
                iOrderManagePresenter.getData(type, market, status, isHide, page, pageSize);
                isLoadMore = true;
                smartRefreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //刷新
                Log.e("TAG", "刷新");
                boolean isHide = cbMineFilterHide.isChecked();

                page = 1;
                iOrderManagePresenter.getData(type, market, status, isHide, page, pageSize);
                smartRefreshLayout.finishRefresh();
            }
        });
    }

    @OnClick({R.id.rl_mine_filter_parent, R.id.tv_mine_filter_confirm, R.id.et_filter_base, R.id.ll_mine_filter_parent,
            R.id.tv_mine_filter_reset, R.id.rb_mine_filter_1, R.id.rb_mine_filter_2, R.id.rb_mine_filter_3, R.id.ll_mine_money_filter_parent,
            R.id.rl_mine_money_filter_parent, R.id.tv_mine_money_filter_reset, R.id.tv_mine_money_filter_confirm,
            R.id.rb_mine_money_filter_1, R.id.rb_mine_money_filter_2, R.id.rb_mine_money_filter_3, R.id.rb_mine_money_filter_4,
            R.id.rb_mine_money_filter_5, R.id.rb_mine_money_filter_6, R.id.iv_mine_money_filter_1, R.id.iv_mine_money_filter_2,
            R.id.iv_mine_money_filter_3, R.id.iv_mine_money_filter_4, R.id.iv_mine_money_filter_5, R.id.iv_mine_money_filter_6,
            R.id.tv_order_bach_cannel_order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_order_bach_cannel_order:
                //撤销当前页面订单
                List<OrderManageVO.DataBean> entrust = orderManageAdapter.getEntrust();
                if (entrust != null && entrust.size() > 0) {
                    iOrderManagePresenter.cannelCurrPageOrder(entrust);
                }
                break;
            case R.id.ll_mine_money_filter_parent:
            case R.id.ll_mine_filter_parent:
                break;
            case R.id.rl_mine_money_filter_parent:
            case R.id.rl_mine_filter_parent:
                hideFilterPopuWindow();
                break;
            case R.id.tv_mine_filter_confirm:
                //币种
                page = 1;
                market = etFilterCoin.getText().toString() + "_" + etFilterBase.getText().toString();
                //是否隐藏已取消
                boolean isHide = cbMineFilterHide.isChecked();

                iOrderManagePresenter.getData(type, market, status, isHide, page, pageSize);
                hideFilterPopuWindow();
                break;
            case R.id.tv_mine_filter_reset:
                page = 1;
                market = null;
                status = 1;
                cbMineFilterHide.setChecked(false);
                iOrderManagePresenter.getData(type, market, status, false, page, pageSize);

                rbMineFilter1.setChecked(true);
                rbMineFilter2.setChecked(false);
                rbMineFilter3.setChecked(false);
                ivMineFilter1.setVisibility(View.VISIBLE);
                ivMineFilter2.setVisibility(View.GONE);
                ivMineFilter3.setVisibility(View.GONE);
                status = null;

                etFilterBase.setText("");
                etFilterCoin.setText("");
                cbMineFilterHide.setChecked(false);
                hideFilterPopuWindow();
                break;
            case R.id.rb_mine_filter_1:
                //全部
                rbMineFilter1.setChecked(true);
                rbMineFilter2.setChecked(false);
                rbMineFilter3.setChecked(false);
                ivMineFilter1.setVisibility(View.VISIBLE);
                ivMineFilter2.setVisibility(View.GONE);
                ivMineFilter3.setVisibility(View.GONE);
                status = null;
                break;
            case R.id.rb_mine_filter_2:
                //买入
                rbMineFilter1.setChecked(false);
                rbMineFilter2.setChecked(true);
                rbMineFilter3.setChecked(false);
                ivMineFilter1.setVisibility(View.GONE);
                ivMineFilter2.setVisibility(View.VISIBLE);
                ivMineFilter3.setVisibility(View.GONE);
                status = 2;
                break;
            case R.id.rb_mine_filter_3:
                //卖出
                rbMineFilter1.setChecked(false);
                rbMineFilter2.setChecked(false);
                rbMineFilter3.setChecked(true);
                ivMineFilter1.setVisibility(View.GONE);
                ivMineFilter2.setVisibility(View.GONE);
                ivMineFilter3.setVisibility(View.VISIBLE);
                status = 1;
                break;
            case R.id.et_filter_base:
                //跳出popwindow
                iOrderManagePresenter.showMarket(etFilterBase);
                break;
            case R.id.tv_mine_money_filter_confirm:
                page = 1;
                market = etMineMoneyFilterCoin.getText().toString();
                iOrderManagePresenter.getData(type, market, status, false, page, pageSize);
                hideFilterPopuWindow();
                break;
            case R.id.tv_mine_money_filter_reset:
                page = 1;
                etMineMoneyFilterCoin.setText("");
                market = null;
                status = null;
                iOrderManagePresenter.getData(type, market, status, false, page, pageSize);
                showMoneyStatus(0);
                hideFilterPopuWindow();
                break;
            case R.id.rb_mine_money_filter_1:
                showMoneyStatus(0);
                break;
            case R.id.rb_mine_money_filter_2:
                showMoneyStatus(1);
                break;
            case R.id.rb_mine_money_filter_3:
                showMoneyStatus(2);
                break;
            case R.id.rb_mine_money_filter_4:
                showMoneyStatus(3);
                break;
            case R.id.rb_mine_money_filter_5:
                showMoneyStatus(4);
                break;
            case R.id.rb_mine_money_filter_6:
                showMoneyStatus(5);
                break;
        }
    }

    private void showMoneyStatus(int i) {
        hideAllStatus();
        status = i;
        switch (i) {
            case 0:
                status = null;
                rbMineMoneyFilter1.setChecked(true);
                ivMineMoneyFilter1.setVisibility(View.VISIBLE);
                break;
            case 1:
                if (type == 3) {
                    status = 2;
                }
                rbMineMoneyFilter2.setChecked(true);
                ivMineMoneyFilter2.setVisibility(View.VISIBLE);
                break;
            case 2:
                if (type == 3) {
                    status = 3;
                }
                rbMineMoneyFilter3.setChecked(true);
                ivMineMoneyFilter3.setVisibility(View.VISIBLE);
                break;
            case 3:
                status = 4;
                rbMineMoneyFilter4.setChecked(true);
                ivMineMoneyFilter4.setVisibility(View.VISIBLE);
                break;
            case 4:
                status = 5;
                rbMineMoneyFilter5.setChecked(true);
                ivMineMoneyFilter5.setVisibility(View.VISIBLE);
                break;
            case 5:
                status = 3;
                rbMineMoneyFilter6.setChecked(true);
                ivMineMoneyFilter6.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 充提 清除状态
     */
    private void hideAllStatus() {
        rbMineMoneyFilter1.setChecked(false);
        rbMineMoneyFilter2.setChecked(false);
        rbMineMoneyFilter3.setChecked(false);
        rbMineMoneyFilter4.setChecked(false);
        rbMineMoneyFilter5.setChecked(false);
        rbMineMoneyFilter6.setChecked(false);
        ivMineMoneyFilter1.setVisibility(View.GONE);
        ivMineMoneyFilter2.setVisibility(View.GONE);
        ivMineMoneyFilter3.setVisibility(View.GONE);
        ivMineMoneyFilter4.setVisibility(View.GONE);
        ivMineMoneyFilter5.setVisibility(View.GONE);
        ivMineMoneyFilter6.setVisibility(View.GONE);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerOrderManageComponent
                .builder()
                .appComponent(appComponent)
                .orderManageModule(new OrderManageModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void getCurrEntrustSuccess(List<OrderManageVO.DataBean> data) {
        orderManageAdapter.setEntrustData(data, isLoadMore);
        isLoadMore = false;

        isDataNull();
    }

    private void isDataNull() {
        if (orderManageAdapter != null && orderManageAdapter.getItemCount() == 0) {
            llPageEmpty.setVisibility(View.VISIBLE);
            tvOrderBachCannelOrder.setVisibility(View.GONE);
        } else {
            if (type == 0) {
                tvOrderBachCannelOrder.setVisibility(View.VISIBLE);
            }
            llPageEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public void getHostoryEntrustSuccess(List<OrderManageVO.DataBean> data) {
        orderManageAdapter.setEntrustData(data, isLoadMore);
        isLoadMore = false;
        isDataNull();
    }

    @Override
    public void getMineVolSuccess(List<VolVO.DataBean> data) {
        orderManageAdapter.setVolData(data, isLoadMore);
        isLoadMore = false;
        isDataNull();
    }

    @Override
    public void setBaseText(String text) {
        etFilterBase.setText(text);
    }

    @Override
    public void getDepositListSuccess(List<DepositWithdrawalVO.DataBean> data) {
        orderManageAdapter.setDepositWithdrawalData(data, isLoadMore);
        isLoadMore = false;
        isDataNull();
    }

    @Override
    public void getWithdrawalListSuccess(List<DepositWithdrawalVO.DataBean> data) {
        orderManageAdapter.setDepositWithdrawalData(data, isLoadMore);
        isLoadMore = false;
        isDataNull();
    }

    @Override
    public void cannelOrderSuccess() {
        orderManageAdapter.removeItem();
    }

    @Override
    public void cannelCurrPageOrderSuccess(String user_order_id) {
        //撤销当前页订单成功
        orderManageAdapter.removeItemCurrPage(user_order_id);
        isDataNull();
    }

    @Override
    public void getStationListSuccess(List<StationVO.DataBean> data) {
        orderManageAdapter.setStationList(data, isLoadMore);
        isLoadMore = false;
        isDataNull();
    }

    /**
     * 跳出 过滤器
     */
    public void showFilterPopuWindow() {
        switch (type) {
            case 0:
            case 1:
            case 2:
                rlMineFilterParent.setVisibility(View.VISIBLE);
                break;
            case 3:
            case 4:
            case 5:
                rlMineMoneyFilterParent.setVisibility(View.VISIBLE);
                break;
        }

        if (popuWindowListener != null) {
            popuWindowListener.showPopuWindow();
        }
    }

    public void hideFilterPopuWindow() {
        switch (type) {
            case 0:
            case 1:
            case 2:
                rlMineFilterParent.setVisibility(View.GONE);
                break;
            case 3:
            case 4:
            case 5:
                rlMineMoneyFilterParent.setVisibility(View.GONE);
                break;
        }
        if (popuWindowListener != null) {
            popuWindowListener.hidePopuWindow();
        }
    }

    private PopuWindowListener popuWindowListener;

    public void setPopuWindowListener(PopuWindowListener popuWindowListener) {
        this.popuWindowListener = popuWindowListener;
    }

    public interface PopuWindowListener {
        void showPopuWindow();

        void hidePopuWindow();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 199 && resultCode == 200) {
            page = 1;
            iOrderManagePresenter.getData(type, market, status, false, page, pageSize);
        }
    }
}
