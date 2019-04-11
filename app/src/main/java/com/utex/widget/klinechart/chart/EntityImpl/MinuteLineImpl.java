package com.utex.widget.klinechart.chart.EntityImpl;

import java.util.Date;

/**
 * Created by Demon on 2017/10/17.
 */

public interface MinuteLineImpl {

    /**
     * @return 获取均价
     */
    float getAvgPrice();

    /**
     * @return 获取成交价
     */
    float getPrice();

    Date getDate();
}
