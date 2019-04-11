package com.utex.utils;

import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.utex.bean.TestDo;
import com.utex.bean.TickerDo;
import com.utex.common.WebSocketConstants;
import com.utex.mvp.marketdetail.bean.MiTabParamVo;
import com.utex.recevier.TickerReceiver;
import com.quintar.IMarketAidlInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Demon on 2018/12/1.
 * 集成socket 所有socket都通过此类进行访问service
 * type
 * 0 首页
 * 1 2 交易页面
 * 3 详情
 * 4 不返回
 * 5 资产
 */
public class MarketSocketPackUtils {

    public final static int TODAY_SUBSCRIBE_ID = 999999;

    public final static int DEPTH_SUBSCRIBE_ID = 99998;

    public final static int VOl_SUBSCRIBE_ID = 99997;

    public static final int KLINE_QUERY_ID = 999996;

    public static final int KLINE_SUBSCRIBE_ID = 999995;


    private static Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            String json = (String) message.obj;
            int type = message.arg1;
            try {
                mMarketAidl.send(json, type);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return true;
        }
    });


    private static IMarketAidlInterface mMarketAidl;
    private static List<String> marketList;
    private static TickerReceiver mTickerReceiver;

    public static void init(IMarketAidlInterface marketAidl, TickerReceiver tickerReceiver) {
        marketList = new ArrayList();
        mMarketAidl = marketAidl;
        mTickerReceiver = tickerReceiver;
    }

    /**
     * 发送 币种订阅
     *
     * @param tickerDos
     */
    public static void todaySubscribe(List<TickerDo> tickerDos, int type) {
        if (mMarketAidl != null && marketList != null) {
            if (tickerDos == null || tickerDos.size() == 0) {
                return;
            }
            for (TickerDo tickerDo : tickerDos) {
                marketList.add(tickerDo.getCoin_market_code());
            }
            TestDo subscribe = new TestDo();
            //设置999999 代表订阅的,我
            subscribe.setId(TODAY_SUBSCRIBE_ID);
            subscribe.setMethod(WebSocketConstants.TODAY_SUBSCRIBE);
            TestDo.Param param = new TestDo.Param();
            param.setMarketlist(marketList);
            subscribe.setParams(param);
            String json = JSON.toJSONString(subscribe);
            Message obtain = Message.obtain();
            obtain.obj = json;
            obtain.arg1 = type;
            handler.removeCallbacksAndMessages(null);
            handler.sendMessageDelayed(obtain, 500);
            marketList.clear();
        }
    }

    /**
     * 发送 K线订阅
     */
    public static void kLineSubscribe(TickerDo currTicker, MiTabParamVo currTime, int type) {
        if (mMarketAidl != null) {

            TestDo kLine2 = new TestDo();
            kLine2.setId(KLINE_SUBSCRIBE_ID);
            kLine2.setMethod(WebSocketConstants.K_LINE_SUBSCRIBE);

            TestDo.Param kLineParam2 = new TestDo.Param();
            kLineParam2.setMarket(currTicker.getCoin_market_code());
            kLineParam2.setPeriod(currTime.getNumber());

            kLine2.setParams(kLineParam2);
            String str2 = JSON.toJSONString(kLine2);

            try {
                mMarketAidl.send(str2, type);
            } catch (RemoteException e) {
                Log.e("TAG", e.getMessage());
            }
        }
    }

    /**
     * 取消 订阅 k线
     */
    public static void kLineUnSubscribe(int type) {
        if (mMarketAidl != null) {
            TestDo subscribe = new TestDo();
            subscribe.setId(MarketSocketPackUtils.KLINE_SUBSCRIBE_ID);
            subscribe.setMethod(WebSocketConstants.K_LINE_UNSUBSCRIBE);
            TestDo.Param param = new TestDo.Param();
            subscribe.setParams(param);
            String json = JSON.toJSONString(subscribe);
            try {
                mMarketAidl.send(json, type);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 取消 订阅 币种
     */
    public static void todayUnSubscribe(int type) {
        if (mMarketAidl != null && marketList != null) {
            TestDo subscribe = new TestDo();
            //设置999999 代表订阅的
            subscribe.setId(TODAY_SUBSCRIBE_ID);
            subscribe.setMethod(WebSocketConstants.TODAY_UNSUBSCRIBE);
            TestDo.Param param = new TestDo.Param();
            param.setMarketlist(marketList);
            subscribe.setParams(param);
            String json = JSON.toJSONString(subscribe);
            try {
                mMarketAidl.send(json, type);
                marketList.clear();
            } catch (RemoteException e) {
                Log.e("TAG", e.getMessage());
            }
        }
    }

    /**
     * 发送 币种查询
     *
     * @param tickerDos
     */
    public static void todayQuery(List<TickerDo> tickerDos, int type) {
        if (mMarketAidl != null) {
            for (TickerDo tickerDo : tickerDos) {
                //请求币种数据
                TestDo coinData = new TestDo();
                //设置999999 代表订阅的,我
                coinData.setId(tickerDo.getId().intValue());
                coinData.setMethod(WebSocketConstants.TODAY_QUERY);
                TestDo.Param coinDataParam = new TestDo.Param();
                coinDataParam.setMarket(tickerDo.getCoin_market_code());
                coinData.setParams(coinDataParam);
                String coinDatajson = JSON.toJSONString(coinData);

                try {
                    mMarketAidl.send(coinDatajson, type);
                } catch (RemoteException e) {
                    Log.e("TAG", e.getMessage());
                }
            }
        }
    }

    /**
     * 发送 币种 深度订阅
     */
    public static void depthSubscribe(String depthMerge, TickerDo currTickerDo, int type) {
        if (mMarketAidl != null) {
            TestDo testDo = new TestDo();
            testDo.setId(DEPTH_SUBSCRIBE_ID);
            testDo.setMethod(WebSocketConstants.DEEPTH_SUBSCRIBE);
            TestDo.Param param = new TestDo.Param();
            param.setLimit(20);
            param.setPrec(depthMerge);
            param.setMarket(currTickerDo.getCoin_market_code());
            testDo.setParams(param);
            String json = JSON.toJSONString(testDo);
            try {
                mMarketAidl.send(json, type);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 取消 订阅深度
     */
    public static void depthUnSubscribe(int type) {
        if (mMarketAidl != null) {
            TestDo subscribe = new TestDo();
            subscribe.setId(MarketSocketPackUtils.DEPTH_SUBSCRIBE_ID);
            subscribe.setMethod(WebSocketConstants.DEEPTH_UNSUBSCRIBE);
            TestDo.Param param = new TestDo.Param();
            subscribe.setParams(param);
            String json = JSON.toJSONString(subscribe);
            try {
                mMarketAidl.send(json, type);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 发送 币种 成交量订阅
     */
    public static void volSubscribe(TickerDo currTickerDo, int type) {
        if (mMarketAidl != null) {
            TestDo volDo = new TestDo();
            volDo.setId(VOl_SUBSCRIBE_ID);
            volDo.setMethod(WebSocketConstants.VOL_SUBSCRIBE);
            TestDo.Param volParam = new TestDo.Param();
            ArrayList<String> strings = new ArrayList<>();
            strings.add(currTickerDo.getCoin_market_code());
            volParam.setMarketlist(strings);
            volDo.setParams(volParam);
            String volJson = JSON.toJSONString(volDo);
            try {
                mMarketAidl.send(volJson, type);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 取消 订阅成交量
     */
    public static void volUnSubscribe(int type) {
        if (mMarketAidl != null) {
            TestDo volDo = new TestDo();
            volDo.setId(MarketSocketPackUtils.VOl_SUBSCRIBE_ID);
            volDo.setMethod(WebSocketConstants.VOL_UNSUBSCRIBE);
            TestDo.Param volParam = new TestDo.Param();
            volDo.setParams(volParam);
            String volJson = JSON.toJSONString(volDo);
            try {
                mMarketAidl.send(volJson, type);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送 K线查询
     */
    public static void kLineQuery(TickerDo currTicker, MiTabParamVo currTime, int type) {
        if (mMarketAidl != null) {
            //K线，请求
            TestDo kLine = new TestDo();
            kLine.setId(MarketSocketPackUtils.KLINE_QUERY_ID);
            kLine.setMethod(WebSocketConstants.K_LINE);
            TestDo.Param kLineParam = new TestDo.Param();
            kLineParam.setMarket(currTicker.getCoin_market_code());
            kLineParam.setPeriod(currTime.getNumber());
            //结束时间 当前时间
            long endTime = System.currentTimeMillis() / 1000;
            //计算分钟
            int number = currTime.getNumber() * 300;
            //开始时间 结束时间-300条数据的时间
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, 0 - number); //向前走一天
            Date date = calendar.getTime();
            long startTime = date.getTime() / 1000;

            kLineParam.setStarttime(startTime);
            kLineParam.setEndtime(endTime);

            kLine.setParams(kLineParam);
            String str = JSON.toJSONString(kLine);

            try {
                mMarketAidl.send(str, type);
            } catch (RemoteException e) {
                Log.e("TAG", e.getMessage());
            }
        }
    }


    private static BindHomeReceive mBindHomeReceive;

    /**
     * 绑定
     */
    public static void bindHomeReceive(BindHomeReceive bindHomeReceive) {
        mBindHomeReceive = bindHomeReceive;
    }

    interface BindHomeReceive {

        void bindHomeReceive();

    }

}
