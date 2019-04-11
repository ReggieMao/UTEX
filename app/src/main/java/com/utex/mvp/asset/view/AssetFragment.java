package com.utex.mvp.asset.view;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.umeng.analytics.MobclickAgent;
import com.utex.R;
import com.utex.base.BaseFragment;
import com.utex.bean.TickerDo;
import com.utex.common.FiledConstants;
import com.utex.common.UMConstants;
import com.utex.core.AppComponent;
import com.utex.core.UTEXApplication;
import com.utex.db.ExUserDao;
import com.utex.mvp.asset.bean.Asset;
import com.utex.mvp.asset.dagger2.AssetModule;
import com.utex.mvp.asset.dagger2.DaggerAssetComponent;
import com.utex.mvp.asset.presenter.IAssetPresenter;
import com.utex.mvp.home.bean.TickerData;
import com.utex.mvp.user.view.LoginActivity;
import com.utex.recevier.TickerReceiver;
import com.utex.utils.SharedPreferencesUtil;
import com.utex.utils.Utils;
import com.utex.widget.WaveSideBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Demon on 2018/5/31.
 * 资产
 */
public class AssetFragment extends BaseFragment implements IAssetView, TickerReceiver.TickerListener {

    @Inject
    IAssetPresenter iAssetPresenter;

    @BindView(R.id.elv_asset_list)
    ExpandableListView elvAssetList;

    @BindView(R.id.tv_asset_price)
    TextView tvAssetPrice;

    @BindView(R.id.tv_asset_platform_price)
    TextView tvAssetPlatFormPrice;

    @BindView(R.id.cb_asset_is_hide)
    CheckBox cbAssetIsHide;

    @BindView(R.id.wsb_asset_bg)
    WaveSideBar wsbAssetBg;

    @BindView(R.id.ll_page_empty)
    LinearLayout llPageEmpty;

    @BindView(R.id.iv_asset_hide)
    ImageView ivAssetHide;

    private ExpandableListAdapter expandableListAdapter;

    private List<Asset.DataBean.AssetUserListBean> allAssetUserList;

    private List<Asset.DataBean.AssetUserListBean> assetUserList;

    private boolean isCurrPage;

    private boolean isHide;


    private static boolean isHideTotalPrice;

    private TickerReceiver tickerReceiver;

    private List<TickerDo> tickerDos;

    private double btcAsset;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.fragment_asset, container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tickerReceiver = new TickerReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.asset");
        getContext().registerReceiver(tickerReceiver, intentFilter);
        tickerReceiver.setTickerListener(this);

        //只展开一个group的实现方法
        elvAssetList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            private int sign;

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                if (sign == -1) {
                    // 展开被选的group
                    elvAssetList.expandGroup(groupPosition);
                    // 设置被选中的group置于顶端
                    elvAssetList.setSelectedGroup(groupPosition);
                    sign = groupPosition;
                } else if (sign == groupPosition) {
                    elvAssetList.collapseGroup(sign);
                    sign = -1;
                } else {
                    elvAssetList.collapseGroup(sign);
                    // 展开被选的group
                    elvAssetList.expandGroup(groupPosition);
                    // 设置被选中的group置于顶端
                    elvAssetList.setSelectedGroup(groupPosition);
                    sign = groupPosition;
                }
                return true;
            }
        });

        elvAssetList.setGroupIndicator(null);

        cbAssetIsHide.setOnCheckedChangeListener((compoundButton, b) -> {
            if (assetUserList != null) {
                assetUserList.clear();

                MobclickAgent.onEvent(getContext(), UMConstants.ASSET_HIDDEN_NO_MONEY_COIN);

                if (b) {
                    for (Asset.DataBean.AssetUserListBean assetUserListBean : allAssetUserList) {
                        if (Double.parseDouble(assetUserListBean.getTotal_fund()) > 0) {
                            assetUserList.add(assetUserListBean);
                        }
                    }
                } else {
                    assetUserList = new ArrayList<>(allAssetUserList);
                }

                expandableListAdapter.setData(assetUserList);
                expandableListAdapter.notifyDataSetChanged();
            }

            isHide = b;

        });

        wsbAssetBg.setOnSelectIndexItemListener(index -> {
            Log.e("TAG", index);
            if (assetUserList != null) {
                for (int i = 0; i < assetUserList.size(); i++) {
                    String coin_code = assetUserList.get(i).getCoin_code();
                    String substring = coin_code.substring(0, 1);
                    if (index.toLowerCase().equals(substring.toLowerCase())) {
                        elvAssetList.setSelectedGroup(i);
                        break;
                    }
                }
            }
        });

    }

    @OnClick({R.id.tv_asset_price, R.id.tv_asset_platform_price, R.id.iv_asset_hide})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_asset_hide:
                if (UTEXApplication.getLoginUser() != null) {
                    if (isHideTotalPrice) {
                        //显示
                        isHideTotalPrice = false;
                        ivAssetHide.setImageResource(R.drawable.property_icon_show);
                        setTopBtc(btcAsset);
                    } else {
                        //隐藏
                        isHideTotalPrice = true;
                        ivAssetHide.setImageResource(R.drawable.property_icon_hide);
                        tvAssetPlatFormPrice.setText("****");
                        tvAssetPrice.setText("*******");
                    }
                }
                break;
            case R.id.tv_asset_price:
            case R.id.tv_asset_platform_price:
                if (UTEXApplication.getLoginUser() == null) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivityForResult(intent, 199);
                    MobclickAgent.onEvent(getContext(), UMConstants.NOLOGIN_PUSH_LOGIN_PAGE_ACTION);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 199 && resultCode == 200) {
            if (UTEXApplication.getLoginUser() != null) {
                iAssetPresenter.getAssetList();
            } else {
                tvAssetPrice.setText(Utils.getResourceString(R.string.qing_deng_lu));
                tvAssetPlatFormPrice.setText(Utils.getResourceString(R.string.qing_deng_lu_hou_cha_kan));
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isCurrPage) {
            if (ExUserDao.query() != null) {
                iAssetPresenter.getAssetList();
                ivAssetHide.setVisibility(View.VISIBLE);
            } else {
                tvAssetPrice.setText(Utils.getResourceString(R.string.qing_deng_lu));
                tvAssetPlatFormPrice.setText(Utils.getResourceString(R.string.qing_deng_lu_hou_cha_kan));
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCurrPage = isVisibleToUser;
        if (isVisibleToUser) {
            if (UTEXApplication.getLoginUser() != null) {
                iAssetPresenter.getAssetList();
                iAssetPresenter.getCoinData();
                new Handler()
                        .postDelayed(() -> iAssetPresenter.cannelCoinData(), 500);
                ivAssetHide.setVisibility(View.VISIBLE);
            } else {
                tvAssetPrice.setText(Utils.getResourceString(R.string.qing_deng_lu));
                tvAssetPlatFormPrice.setText(Utils.getResourceString(R.string.qing_deng_lu_hou_cha_kan));
                llPageEmpty.setVisibility(View.VISIBLE);
                elvAssetList.setVisibility(View.GONE);
                ivAssetHide.setVisibility(View.GONE);
            }

        } else {

        }
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerAssetComponent
                .builder()
                .appComponent(appComponent)
                .assetModule(new AssetModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void setExpandableListViewData
            (List<Asset.DataBean.AssetUserListBean> newAssetUserList) {
        this.allAssetUserList = newAssetUserList;
        this.assetUserList = new ArrayList<>(allAssetUserList);

        expandableListAdapter = new ExpandableListAdapter();
        expandableListAdapter.setData(this.assetUserList);
        elvAssetList.setAdapter(expandableListAdapter);

        if (expandableListAdapter.getGroupCount() != 0) {
            llPageEmpty.setVisibility(View.GONE);
            elvAssetList.setVisibility(View.VISIBLE);
        } else {
            llPageEmpty.setVisibility(View.VISIBLE);
            elvAssetList.setVisibility(View.GONE);
        }

        if (this.assetUserList != null) {
            this.assetUserList.clear();
            if (isHide) {
                for (Asset.DataBean.AssetUserListBean assetUserListBean : allAssetUserList) {
                    if (Double.parseDouble(assetUserListBean.getTotal_fund()) > 0) {
                        assetUserList.add(assetUserListBean);
                    }
                }
            } else {
                assetUserList = new ArrayList<>(allAssetUserList);
            }

            expandableListAdapter.setData(assetUserList);
            expandableListAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void setTopBtc(double btc_asset) {
        this.btcAsset = btc_asset;

        if (isHideTotalPrice) {
            tvAssetPlatFormPrice.setText("****");
            tvAssetPrice.setText("*******");
        } else {
            if (btc_asset > 10) {
                tvAssetPrice.setText(Utils.eliminateZero(Double.parseDouble(Utils.getScientificCountingMethodAfterData(btc_asset, 4))) + " GZH");
            } else {
                tvAssetPrice.setText(Utils.eliminateZero(Double.parseDouble(Utils.getScientificCountingMethodAfterData(btc_asset, 8))) + " GZH");
            }

            double rate = UTEXApplication.getRate("gzh");
            double v = Double.parseDouble(Utils.getScientificCountingMethodAfterData(btc_asset * rate, 8));
            tvAssetPlatFormPrice.setText("≈" + Utils.eliminateZero(v) + SharedPreferencesUtil.getString(FiledConstants.PALTFORM_VALUAT, "USD"));
        }

    }

    @Override
    public void getAssetListFail() {
        if (!tvAssetPrice.getText().toString().equals(Utils.getResourceString(R.string.qing_deng_lu))) {
            tvAssetPrice.setText("");
            tvAssetPlatFormPrice.setText("");
        }
    }

    /**
     * 获取到的数据
     */
    @Override
    public void onReceive(String json) {
        if (tickerDos == null) {
            return;
        }

        try {
            JSONObject jsonObject = JSON.parseObject(json, JSONObject.class);
            JSONArray params = jsonObject.getJSONArray("params");
            if (params != null && params.size() > 0) {
                String coinCode = params.getString(0);
                JSONObject object = params.getJSONObject(1);
                TickerData tickerData = JSON.parseObject(JSON.toJSONString(object), TickerData.class);
                for (TickerDo tickerDo : tickerDos) {
                    if (coinCode.equals(tickerDo.getCoin_market_code())) {
                        tickerDo.setResultBean(tickerData);
                        break;
                    }
                }
            }
            expandableListAdapter.notifyDataSetInvalidated();
        } catch (Exception e) {
            Log.e("TAG", json);
            e.printStackTrace();
        }

    }
}
