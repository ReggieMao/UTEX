package com.utex.mvp.trad.presenter;

import com.utex.base.BaseFragment;
import com.utex.bean.TickerDo;
import com.utex.mvp.asset.bean.Asset;
import com.utex.mvp.trad.bean.Entrust;

import java.util.List;

import retrofit2.Response;

/**
 * Created by Demon on 2018/5/30.
 */
public interface ITradPresenter {

    void setCap();

    void setCoinList();

    void getCurrMarketEntrust(String coinMarketCode);

    void getCurrMarketEntrustSuccess(List<Entrust.DataBean> data);

    void getAssetList();

    void getAssetListSuccess(Response<Asset> response);

    void setStatus(int status);

    void setDirection(int direction);

    void submitTrad(String coin_market_code, String amount, String goodsNumber);

    void submitDirection0Success();

    void submitDirection1Success();

    int getStatus();

    void cannelOrder(Entrust.DataBean dataBean);

    void cannelOrderSuccess();

    void addOptional(String coinMarketCode, Long id);

    void addOptionalSuccess();

    void cannelOptional(String coinMarketCode);

    void cannelOptionalSuccess();

    void submitDirectionError(String message);

    void jumpDepthSelPopwindow(int res, String depth_merge);

    void requestFail(String s);

    int getDirection();

    void getAllCoinData(List<BaseFragment> fragments);

    void getCurrPageMarketData(String depthMerge, TickerDo currTickerDo);

}
