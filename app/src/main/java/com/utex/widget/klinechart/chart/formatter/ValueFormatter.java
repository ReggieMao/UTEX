package com.utex.widget.klinechart.chart.formatter;


import com.utex.widget.klinechart.chart.impl.IValueFormatter;

/**
 * Value格式化类
 * Created by Demon on 2017/10/17.
 */

public class ValueFormatter implements IValueFormatter {
    @Override
    public String format(float value) {
        return String.format("%.2f", value);
    }
}
