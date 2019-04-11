package com.utex.widget.klinechart.chart.impl;

/**
 * Value格式化接口
 * Created by Demon on 2017/10/17.
 */

public interface IValueFormatter {
    /**
     * 格式化value
     *
     * @param value 传入的value值
     * @return 返回字符串
     */
    String format(float value);
}
