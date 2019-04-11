package com.utex.mvp.home.presenter;

import android.os.Bundle;

import com.utex.base.BaseFragment;
import com.utex.bean.TickerDo;
import com.utex.common.FiledConstants;
import com.utex.data.ApiService;
import com.utex.db.ExTickerDao;
import com.utex.mvp.home.bean.BannerVo;
import com.utex.mvp.home.bean.NoticeVo;
import com.utex.mvp.home.model.HomeModel;
import com.utex.mvp.home.model.IHomeModel;
import com.utex.mvp.home.view.IHomeView;
import com.utex.mvp.market.view.MarketFragment;
import com.utex.utils.MarketSocketPackUtils;
import com.utex.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Demon on 2018/5/17.
 */

public class HomePresenter implements IHomePresenter {

    private ApiService apiService;

    private IHomeModel iHomeModel;

    private IHomeView iHomeView;

    private List<BaseFragment> fragments;

    public HomePresenter(ApiService apiService, IHomeView iHomeView) {
        this.apiService = apiService;
        iHomeModel = new HomeModel();
        this.iHomeView = iHomeView;
    }

    @Override
    public void getCoinList() {
//        iHomeModel.getCoinList(apiService, this);
        setCoin();
    }

    @Override
    public void getCoinListSuccess(List<TickerDo> data) {
        //插入修改数据库
        ExTickerDao.insertTicker(data);
        //修改自选
        String optionList = SharedPreferencesUtil.getString(FiledConstants.OPTION_COIN, "");
        String[] split = optionList.split(",");
        if (split != null) {
            for (int i = 0; i < split.length; i++) {
                ExTickerDao.changeOption(split[i]);
            }
        }
        setCoin();
    }

    private void setCoin() {
        //查询，获取交易区
        ArrayList<String> market = ExTickerDao.getMarket(true);
        fragments = new ArrayList<>();

        for (String str : market) {
            Bundle arges = new Bundle();
            arges.putCharSequence(FiledConstants.MARKET_NAME, str);
            MarketFragment marketFragment = new MarketFragment();
            marketFragment.setArguments(arges);
            fragments.add(marketFragment);
        }

        iHomeView.setMarketTitle(market);

        iHomeView.setPage(fragments);
    }


    @Override
    public void getBanners() {
        String countryId = SharedPreferencesUtil.getString(FiledConstants.SP_LANGUGE_ABBREVIATION, "tw");
        if (!"zh".equals(countryId)) {
            countryId = "en";
        }

        iHomeModel.getBanners(apiService, countryId, this);
    }

    @Override
    public void getBannersSuccess(List<BannerVo.DataBean> data) {
        iHomeView.setBanners(data);
    }

    @Override
    public void getNotice() {
        String countryId = SharedPreferencesUtil.getString(FiledConstants.SP_LANGUGE_ABBREVIATION, "tw");
        if (!"zh".equals(countryId)) {
            countryId = "en";
        }
        iHomeModel.getNotice(apiService, countryId, this);
    }

    @Override
    public void getNoticeSuccess(List<NoticeVo.DataBean> data) {
        iHomeView.getNoticeSuccess(data);
    }

    @Override
    public List<BaseFragment> getFragments() {
        return fragments;
    }

    @Override
    public void getCoinListFail() {
        setCoin();
    }

    @Override
    public void getMarketData(int currPage) {
        if (getFragments() != null && getFragments().size() > 0) {
            //请求第1个
            MarketFragment fragment = (MarketFragment) getFragments().get(currPage);
            MarketSocketPackUtils.todaySubscribe(fragment.getTickerDos(), 0);
        }
    }

    @Override
    public void cannelMarketDataSubscribe() {
        MarketSocketPackUtils.todayUnSubscribe(0);
    }
}
