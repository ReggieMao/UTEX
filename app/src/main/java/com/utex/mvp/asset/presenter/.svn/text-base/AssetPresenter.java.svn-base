package com.utex.mvp.asset.presenter;

import com.utex.data.ApiService;
import com.utex.db.ExTickerDao;
import com.utex.mvp.asset.bean.Asset;
import com.utex.mvp.asset.model.AssetModel;
import com.utex.mvp.asset.model.IAssetModel;
import com.utex.mvp.asset.view.AssetFragment;
import com.utex.mvp.asset.view.IAssetView;
import com.utex.utils.MarketSocketPackUtils;

/**
 * Created by Demon on 2018/6/1.
 */
public class AssetPresenter implements IAssetPresenter {

    private ApiService apiService;

    IAssetModel iAssetModel;

    IAssetView iAssetView;

    public AssetPresenter(ApiService apiService, AssetFragment activity) {
        iAssetModel = new AssetModel();
        this.apiService = apiService;
        iAssetView = activity;
    }

    @Override
    public void getAssetList() {
        iAssetModel.getAssetList(apiService, this);
    }

    @Override
    public void getAssetListSuccess(Asset asset) {
        //设置顶部资产
        iAssetView.setTopBtc(asset.getData().getBtc_asset());
        //设置 ListView数据
        iAssetView.setExpandableListViewData(asset.getData().getAsset_user_list());

    }

    @Override
    public void getAssetListFail() {
        iAssetView.getAssetListFail();
    }

    @Override
    public void getCoinData() {
        MarketSocketPackUtils.todaySubscribe(ExTickerDao.getShowTickers(), 5);
    }

    @Override
    public void cannelCoinData() {
        MarketSocketPackUtils.todayUnSubscribe(5);
    }
}
