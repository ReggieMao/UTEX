package com.utex.mvp.trad.view;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

/**
 * Created by Demon on 2018/8/15.
 */
public class ETFilter implements InputFilter {

    private final int scale;

    private final double max_amount;

    public ETFilter(int scale, double max_amount) {
        this.scale = scale;
        this.max_amount = max_amount;
    }

    /**
     * @param source 新输入的
     * @param start
     * @param end
     * @param dest   原来的
     * @param dstart
     * @param dend
     * @return
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {

        try {
            if (source.equals(" ") || (scale == 0 && ".".equals(source)) || (TextUtils.isEmpty(dest) && source.equals("."))) {
                return "";
            } else if (Double.parseDouble((dest + source.toString())) > max_amount) {
//            若大于则拦截
                return "";
            } else {
                if (String.valueOf(dest).contains(".") && !TextUtils.isEmpty(source)) {

                    int pointIndex = String.valueOf(dest).indexOf(".");
                    if (dstart <= pointIndex) {
                        return source;
                    }

                    //包含小数点
                    String[] split = String.valueOf(dest).split("\\.");

                    int i = split.length == 1 ? 0 : split[1].length();

                    if (i + 1 <= scale) {
                        return source;
                    } else {
                        return "";
                    }
                } else {
                    return source;
                }
            }
        } catch (Exception e) {
            return "";
        }
    }
}
