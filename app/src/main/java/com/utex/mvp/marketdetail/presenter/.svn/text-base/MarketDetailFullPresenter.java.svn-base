package com.utex.mvp.marketdetail.presenter;

import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.utex.R;
import com.utex.bean.TickerDo;
import com.utex.common.Constants;
import com.utex.data.ApiService;
import com.utex.mvp.home.bean.TickerData;
import com.utex.mvp.marketdetail.bean.KLineEntity;
import com.utex.mvp.marketdetail.bean.MiTabParamVo;
import com.utex.mvp.marketdetail.view.IMarketDetailFullView;
import com.utex.mvp.marketdetail.view.MarketDetailFullActivity;
import com.utex.utils.CalculationKLine;
import com.utex.utils.MarketSocketPackUtils;
import com.utex.utils.SharedPreferencesUtil;
import com.utex.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Demon on 2018/6/19.
 */
public class MarketDetailFullPresenter implements IMarketDetailFullPresenter {

    private ApiService apiService;

    private IMarketDetailFullView iMarketDetailView;
    private List<KLineEntity> allKlineEntity;

    public MarketDetailFullPresenter(ApiService apiService, MarketDetailFullActivity activity) {
        this.apiService = apiService;
        this.iMarketDetailView = activity;
    }

    @Override
    public void setTime() {
        String string = SharedPreferencesUtil.getString(Constants.SP_K_TIME, "");

        List<MiTabParamVo> times = new ArrayList<>();
        times.add(new MiTabParamVo(Utils.getResourceString(R.string.fen_shi), 60));
        times.add(new MiTabParamVo(1 + Utils.getResourceString(R.string.fen_zhong), 60));
        times.add(new MiTabParamVo(3 + Utils.getResourceString(R.string.fen_zhong), 180));
        times.add(new MiTabParamVo(5 + Utils.getResourceString(R.string.fen_zhong), 300));
        times.add(new MiTabParamVo(15 + Utils.getResourceString(R.string.fen_zhong), 900));
        times.add(new MiTabParamVo(30 + Utils.getResourceString(R.string.fen_zhong), 1800));
        times.add(new MiTabParamVo(1 + Utils.getResourceString(R.string.xiao_shi), 3600));
        times.add(new MiTabParamVo(12 + Utils.getResourceString(R.string.xiao_shi), 12 * 3600));
        times.add(new MiTabParamVo(Utils.getResourceString(R.string.ri_xian), 24 * 3600));
        times.add(new MiTabParamVo(Utils.getResourceString(R.string.zhou_xian), 24 * 3600 * 7));
        times.add(new MiTabParamVo(Utils.getResourceString(R.string.yue_xian), 24 * 3600 * 30));

        iMarketDetailView.setTime(times, string);
    }

    @Override
    public void setData() {
        iMarketDetailView.setData();
    }

    @Override
    public void calculationKLine(JSONArray kLine) {
        List<TickerData> tickerDatas = new ArrayList<>();

        JsonArray newJsonElements = new JsonArray();

        for (int i = 0; i < kLine.size(); i++) {
            JsonObject jsonObject = new JsonObject();
            JSONArray jsonArray = kLine.getJSONArray(i);
            jsonObject.addProperty("Date", jsonArray.getLong(0) * 1000);
            jsonObject.addProperty("Open", jsonArray.getString(1));
            jsonObject.addProperty("High", jsonArray.getString(2));
            jsonObject.addProperty("Low", jsonArray.getString(3));
            jsonObject.addProperty("Close", jsonArray.getString(4));
            jsonObject.addProperty("Volume", jsonArray.getString(5));
            newJsonElements.add(jsonObject);
        }

        allKlineEntity = getALL(newJsonElements);
        CalculationKLine.calculate(allKlineEntity, 1);
        CalculationKLine.calculate(allKlineEntity, 2);
        CalculationKLine.calculate(allKlineEntity, 3);
        CalculationKLine.calculate(allKlineEntity, 4);
        CalculationKLine.calculate(allKlineEntity, 5);
        CalculationKLine.calculate(allKlineEntity, 6);
        CalculationKLine.calculate(allKlineEntity, 7);
        iMarketDetailView.setKLineData(allKlineEntity);
    }


    @Override
    public void calculationKLineUpdate(JSONArray result) {
        //更新 K 线

        if (allKlineEntity == null || allKlineEntity.size() == 0) {
            return;
        }

        boolean isHasNew = false;
        for (int i = 0; i < result.size(); i++) {
            JSONArray jsonArray = result.getJSONArray(i);

            if (jsonArray.getLong(0) * 1000 == allKlineEntity.get(allKlineEntity.size() - 1).Date) {
                //旧的
                KLineEntity kLineEntity = allKlineEntity.get(allKlineEntity.size() - 1);
                kLineEntity.Open = Float.parseFloat(jsonArray.getString(1));
                kLineEntity.High = Float.parseFloat(jsonArray.getString(2));
                kLineEntity.Low = Float.parseFloat(jsonArray.getString(3));
                kLineEntity.Close = Float.parseFloat(jsonArray.getString(4));
                kLineEntity.Volume = Float.parseFloat(jsonArray.getString(5));
            } else {
                //新的
                Log.e("TAG", "有新的");
                KLineEntity kLineEntity = new KLineEntity();
                kLineEntity.Open = Float.parseFloat(jsonArray.getString(1));
                kLineEntity.High = Float.parseFloat(jsonArray.getString(2));
                kLineEntity.Low = Float.parseFloat(jsonArray.getString(3));
                kLineEntity.Close = Float.parseFloat(jsonArray.getString(4));
                kLineEntity.Volume = Float.parseFloat(jsonArray.getString(5));
                kLineEntity.Date = jsonArray.getLong(0) * 1000;
                allKlineEntity.add(kLineEntity);
                isHasNew = true;
            }
        }

        CalculationKLine.calculate(allKlineEntity, 1);
        CalculationKLine.calculate(allKlineEntity, 2);
        CalculationKLine.calculate(allKlineEntity, 3);
        CalculationKLine.calculate(allKlineEntity, 4);
        CalculationKLine.calculate(allKlineEntity, 5);
        CalculationKLine.calculate(allKlineEntity, 6);
        CalculationKLine.calculate(allKlineEntity, 7);

        iMarketDetailView.updateKLine(isHasNew);
    }

    @Override
    public void clearKLineData() {
        allKlineEntity = null;
    }

    @Override
    public void coinSubscribe(TickerDo currTicker) {
        ArrayList<TickerDo> arrayList = new ArrayList<>();
        arrayList.add(currTicker);
        MarketSocketPackUtils.todaySubscribe(arrayList, 3);
    }

    @Override
    public void KLineQuery(TickerDo currTicker, MiTabParamVo currTime) {
        MarketSocketPackUtils.kLineQuery(currTicker, currTime, 3);
    }

    @Override
    public void kLineSubscribe(TickerDo currTicker, MiTabParamVo currTime) {
        MarketSocketPackUtils.kLineSubscribe(currTicker, currTime, 3);
    }

    @Override
    public void setIndex() {
        //指标  vol macd kdj rsi ma ema boll
        List<MiTabParamVo> indexs = new ArrayList<>();
        indexs.add(new MiTabParamVo("MA", 1));
        indexs.add(new MiTabParamVo("EMA", 2));
        indexs.add(new MiTabParamVo("BOLL", 3));
        indexs.add(new MiTabParamVo("VOL", 4));
        indexs.add(new MiTabParamVo("MACD", 5));
        indexs.add(new MiTabParamVo("KDJ", 6));
        indexs.add(new MiTabParamVo("RSI", 7));

        iMarketDetailView.setIndexData(indexs);
    }

    public static List<KLineEntity> getALL(JsonArray jsonArray) {
        JsonArray bimaoData = jsonArray;

        String s = new Gson().toJson(bimaoData);
        final List<KLineEntity> data =
                new Gson().fromJson(s,
                        new TypeToken<List<KLineEntity>>() {
                        }.getType());
        return data;
    }


}
