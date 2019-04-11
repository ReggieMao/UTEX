package com.utex.common;

/**
 * Created by admin on 2017/4/17.
 */

public class Constants {

    //白天黑夜模式是否开启
    public static final String KEY_NIGHT_MODE = "KEY_NIGHT_MODE";

    /**
     * 是否第一次登录
     */
    public static final int IS_FIRST_LOGIN = 1;

    /**
     * 密码 正则表达式
     */
    public static final String REGEX = "(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$";

    public static final String DARK_LIGHT_SWITCH = "DARK_LIGHT_SWITCH";

    /**
     * 語言類型
     * 1 英语
     * 2 繁体中文
     * 3 简体中文
     */
    public static final String LANGUAGE_TYPE = "LANGUAGE_TYPE";

    /**
     * 是否切换颜色
     */
    public static final String IS_SWITCH_FIRST = "IS_SWITCH_FIRST";

    /**
     * 交易界面 当前显示的市场
     */
    public static final String TRAD_TICKER = "trad_ticker";

    /**
     * 用户token
     */
    public static final String TOKEN = "token";

    /**
     * 币种code
     */
    public static final String COIN_CODE = "coin_code";

    /**
     * K显示时间
     */
    public static final String SP_K_TIME = "k_time";

    /**
     * 语言索引
     */
    public static final String SP_LANGUGE_POSITION = "languge_position";

    /**
     * 计价方式
     */
    public static final String SP_VALUAT_POSITION = "valuat_position";

    /**
     * 验证方式
     */
    public static final String USER_TYPE = "user_type";

    /**
     * 国家手机号
     */
    public static final String SP_COUNTRY_PHONE_NUM_POSITION = "sp_country_phone_num_position";

    /**
     * 分享路径
     */
    public static final String SHARE_PATH = "share_PATH";

    /**
     * 登录若没有设置快捷登录，永远不再提示
     * 0  显示
     * 1  不再显示
     */
    public static final String FOREVER_NOT_SHOW = "forever_not_show";
}
