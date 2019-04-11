package com.utex.mvp.home.presenter;

import com.utex.base.BaseFragment;
import com.utex.bean.TickerDo;
import com.utex.mvp.home.bean.BannerVo;
import com.utex.mvp.home.bean.NoticeVo;

import java.util.List;

/**
 * Created by Demon on 2018/5/17.
 */

public interface IHomePresenter {

    void getCoinList();

    void getCoinListSuccess(List<TickerDo> data);

    void getBanners();

    void getBannersSuccess(List<BannerVo.DataBean> data);

    void getNotice();

    void getNoticeSuccess(List<NoticeVo.DataBean> data);

    List<BaseFragment> getFragments();

    void getCoinListFail();

    void getMarketData(int currPage);

    void cannelMarketDataSubscribe();

}
