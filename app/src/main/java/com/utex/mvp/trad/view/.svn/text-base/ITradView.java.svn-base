package com.utex.mvp.trad.view;

import com.utex.base.BaseFragment;
import com.utex.mvp.trad.bean.Entrust;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Demon on 2018/5/30.
 */
public interface ITradView {
    void setCap();

    void setCoinList(List<BaseFragment> fragments, ArrayList<String> mTitleDataList);

    void setCurEntrust(Map<String, Entrust.DataBean> data, List<Entrust.DataBean> dataBeans);

    String getCurrtradTicker();

    void setAsset(double available_fund, String s);

    void submitSuccess();

    void cannelOrderSuccess();

    void addOptionalSuccess();

    void cannelOptionalSuccess();

    void updateCoinMarketCode(String coinCode);

    void setMerageLimit(String s);
}
