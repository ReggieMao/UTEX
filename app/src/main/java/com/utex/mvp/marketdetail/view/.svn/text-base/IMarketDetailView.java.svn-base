package com.utex.mvp.marketdetail.view;

import com.utex.mvp.marketdetail.bean.CoinBaseVO;
import com.utex.mvp.marketdetail.bean.MiTabParamVo;
import com.utex.mvp.marketdetail.bean.KLineEntity;

import java.util.List;

/**
 * Created by Demon on 2018/6/19.
 */
public interface IMarketDetailView {

    void setTime(List<MiTabParamVo> times, String hideTimes);

    void setData();

    void setKLineData(List<KLineEntity> tickerDatas);

    void setIndexData(List<MiTabParamVo> indexs);

    void getCoinInfoSuccess(CoinBaseVO body);

    void addOptionalSuccess();

    void cannelOptionalSuccess();

    void updateKLine(boolean isHasNew);

}
