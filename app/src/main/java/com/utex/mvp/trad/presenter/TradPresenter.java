package com.utex.mvp.trad.presenter;

import android.content.Context;
import android.os.Bundle;

import com.utex.base.BaseFragment;
import com.utex.bean.TickerDo;
import com.utex.bean.UserDO;
import com.utex.common.FiledConstants;
import com.utex.core.UTEXApplication;
import com.utex.data.ApiService;
import com.utex.db.ExTickerDao;
import com.utex.mvp.asset.bean.Asset;
import com.utex.mvp.market.view.MarketFragment;
import com.utex.mvp.trad.bean.Entrust;
import com.utex.mvp.trad.model.ITradModel;
import com.utex.mvp.trad.model.TradModel;
import com.utex.mvp.trad.view.ITradView;
import com.utex.mvp.trad.view.TradFragment;
import com.utex.utils.MarketSocketPackUtils;
import com.utex.utils.Utils;
import com.utex.widget.popuwindow.TradDepthSelPopupWindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

/**
 * Created by Demon on 2018/5/30.
 */
public class TradPresenter implements ITradPresenter {

    private Context context;

    private ApiService apiService;

    private ITradView iTradView;

    private ITradModel iTradModel;

    /**
     * 1 卖出
     * 2 买入
     */
    private int status = 2;

    /**
     * 0 限价
     * 1 市价
     */
    private int direction;

    public TradPresenter(ApiService apiService, TradFragment activity) {
        this.apiService = apiService;
        this.iTradView = activity;
        iTradModel = new TradModel();
        context = activity.getContext();
    }

    @Override
    public void setCap() {
        iTradView.setCap();
    }

    @Override
    public void setCoinList() {
        ArrayList<String> mTitleDataList = new ArrayList<>();

        ArrayList<String> market = ExTickerDao.getMarket(true);
        ArrayList<BaseFragment> fragments = new ArrayList<>();
        for (String str : market) {
            Bundle arges = new Bundle();
            arges.putCharSequence(FiledConstants.MARKET_NAME, str);
            arges.putInt(FiledConstants.TYPE, 1);

            MarketFragment marketFragment = new MarketFragment();
            marketFragment.setArguments(arges);

            fragments.add(marketFragment);
            mTitleDataList.add(str);
            marketFragment.setMarketLisntener(new MarketFragment.MarketLisntener() {
                @Override
                public void onclick(String coinCode) {
                    iTradView.updateCoinMarketCode(coinCode);
                }
            });

        }
        iTradView.setCoinList(fragments, mTitleDataList);
    }

    @Override
    public void getCurrMarketEntrust(String coinMarketCode) {
        iTradModel.getCurrMarketEntrust(apiService, coinMarketCode, this);
    }

    @Override
    public void getCurrMarketEntrustSuccess(List<Entrust.DataBean> data) {
        //放入map里，诶，没招，前面要根据这个来插数据，不然宝宝还要写for循环
        Map<String, Entrust.DataBean> dataBeanMap = new HashMap<>();
        for (Entrust.DataBean dataBean : data) {
            dataBeanMap.put(dataBean.getUser_order_id(), dataBean);
        }

        iTradView.setCurEntrust(dataBeanMap, data);
    }

    @Override
    public void getAssetList() {
        iTradModel.getAssetList(apiService, this);
    }

    @Override
    public void getAssetListSuccess(Response<Asset> response) {
        if (response == null || response.body() == null || response.body().getData() == null) {
            return;
        }
        String[] split = iTradView.getCurrtradTicker().split("_");
        List<Asset.DataBean.AssetUserListBean> asset_user_list = response.body().getData().getAsset_user_list();
        for (Asset.DataBean.AssetUserListBean assetUserListBean : asset_user_list) {

            switch (status) {
                case 2:
                    //买入
                    if (split[1].toLowerCase().equals(assetUserListBean.getCoin_code().toLowerCase())) {
                        //获取可用的数量
                        iTradView.setAsset(Double.parseDouble(assetUserListBean.getAvailable_fund()), split[1]);
                        break;
                    }
                    break;
                case 1:
                    //卖出
                    if (split[0].toLowerCase().equals(assetUserListBean.getCoin_code().toLowerCase())) {
                        //获取可用的数量
                        iTradView.setAsset(Double.parseDouble(assetUserListBean.getAvailable_fund()), split[0]);
                        break;
                    }
                    break;
            }
        }
    }

    @Override
    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    public void submitTrad(String coin_market_code, String amount, String price) {
        switch (direction) {
            case 0:
                //限价交易
                iTradModel.submitDirection0(apiService, coin_market_code, amount, status, price, this);
                break;
            case 1:
                //市价交易
                iTradModel.submitDirection1(apiService, coin_market_code, amount, status, this);
                break;
        }
    }

    @Override
    public void submitDirection0Success() {
        iTradView.submitSuccess();
    }

    @Override
    public void submitDirection1Success() {
        iTradView.submitSuccess();
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public void cannelOrder(Entrust.DataBean dataBean) {
        UserDO query = UTEXApplication.getLoginUser();
        iTradModel.cannelOrder(apiService, query.getUid(), dataBean.getMarket(), dataBean.getUser_order_id(), this);
    }

    @Override
    public void cannelOrderSuccess() {
        iTradView.cannelOrderSuccess();
    }

    @Override
    public void addOptional(String coinMarketCode, Long id) {
        iTradModel.addOptional(apiService, coinMarketCode, id, this);
    }

    @Override
    public void addOptionalSuccess() {
        iTradView.addOptionalSuccess();
    }

    @Override
    public void cannelOptional(String coinMarketCode) {
        iTradModel.cannelOptional(apiService, coinMarketCode, this);
    }

    @Override
    public void cannelOptionalSuccess() {
        iTradView.cannelOptionalSuccess();
    }

    @Override
    public void submitDirectionError(String message) {
        Utils.showMessage(message);
    }

    @Override
    public void jumpDepthSelPopwindow(int res, String depth_merge) {
        List<String> strings = Arrays.asList(depth_merge.split(","));
        TradDepthSelPopupWindow tradDepthSelPopupWindow = new TradDepthSelPopupWindow(context, res, strings);
        tradDepthSelPopupWindow.setLoginBottomListener(s -> iTradView.setMerageLimit(s));
    }

    @Override
    public void requestFail(String s) {
        Utils.showMessage(s);
    }

    @Override
    public int getDirection() {
        return direction;
    }

    @Override
    public void getAllCoinData(List<BaseFragment> fragments) {
        if (fragments == null) {
            return;
        }
        for (BaseFragment baseFragment : fragments) {
            MarketFragment marketFragment = (MarketFragment) baseFragment;

            List<TickerDo> tickerDos = marketFragment.getTickerDos();

            if (tickerDos == null) {
                marketFragment.updateCoin();
            }

            if (tickerDos == null) {
                return;
            }

            MarketSocketPackUtils.todayQuery(tickerDos, 1);
        }
    }

    @Override
    public void getCurrPageMarketData(String depthMerge, TickerDo currTickerDo) {
        List<TickerDo> list = new ArrayList<>();
        list.add(currTickerDo);
        MarketSocketPackUtils.todaySubscribe(list, 1);
        MarketSocketPackUtils.depthSubscribe(depthMerge, currTickerDo, 1);
        MarketSocketPackUtils.volSubscribe(currTickerDo, 1);
    }


}
