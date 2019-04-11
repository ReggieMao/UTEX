package com.utex.common;

/**
 * Created by Demon on 2018/5/30.
 */
public class WebSocketConstants {

    /**
     * 24小时交易数据,包括当前的，用于拉数据
     */
    public static final String TODAY_QUERY = "today.query";

    /**
     * 订阅 实时数据
     */
    public static final String TODAY_SUBSCRIBE = "today.subscribe";


    /**
     * 取消订阅 实时数据
     */
    public static final String TODAY_UNSUBSCRIBE = "today.unsubscribe";

    /**
     * 深度
     */
    public static final String DEEPTH_QUERY = "depth.query";

    /**
     * 订阅 深度
     */
    public static final String DEEPTH_SUBSCRIBE = "depth.subscribe";

    /**
     * K线 请求
     */
    public static final String K_LINE = "kline.query";

    /**
     * 订阅 成交量
     */
    public static final String VOL_SUBSCRIBE = "deals.subscribe";

    /**
     * 取消订阅 成交量
     */
    public static final String VOL_UNSUBSCRIBE = "deals.unsubscribe";

    /**
     * 取消订阅 深度
     */
    public static final String DEEPTH_UNSUBSCRIBE = "depth.unsubscribe";

    /**
     * 订阅 K线
     */
    public static final String K_LINE_SUBSCRIBE = "kline.subscribe";

    /**
     * 取消订阅
     */
    public static final String K_LINE_UNSUBSCRIBE = "kline.unsubscribe";
}
