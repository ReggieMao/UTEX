package com.utex.widget.klinechart.chart.formatter;


import com.utex.widget.klinechart.chart.impl.IDateTimeFormatter;
import com.utex.widget.klinechart.utils.DateUtil;

import java.util.Date;

/**
 * 时间格式化器
 * Created by Demon on 2017/10/17.
 */

public class TimeFormatter implements IDateTimeFormatter {
    @Override
    public String format(Date date) {
        if (date == null) {
            return "";
        }
        return DateUtil.shortTimeFormat.format(date);
    }
}
