package com.utex.utils;


import com.utex.mvp.marketdetail.bean.KLineEntity;

import java.util.List;

/**
 * 数据辅助类 计算macd rsi等
 * Created by Demon on 2016/11/26.
 */

public class CalculationKLine {
    /**
     * 计算RSI
     *
     * @param datas
     */
    public static void calculateRSI(List<KLineEntity> datas) {
        try {
            float rsi1 = 0;
            float rsi2 = 0;
            float rsi3 = 0;
            float rsi1ABSEma = 0;
            float rsi2ABSEma = 0;
            float rsi3ABSEma = 0;
            float rsi1MaxEma = 0;
            float rsi2MaxEma = 0;
            float rsi3MaxEma = 0;


            for (int i = 0; i < datas.size(); i++) {
                KLineEntity point = datas.get(i);
                final float closePrice = point.getClosePrice();
                if (i == 0) {
                    rsi1 = 0;
                    rsi2 = 0;
                    rsi3 = 0;
                    rsi1ABSEma = 0;
                    rsi2ABSEma = 0;
                    rsi3ABSEma = 0;
                    rsi1MaxEma = 0;
                    rsi2MaxEma = 0;
                    rsi3MaxEma = 0;
                } else {
                    float Rmax = Math.max(0, closePrice - datas.get(i - 1).getClosePrice());
                    float RAbs = Math.abs(closePrice - datas.get(i - 1).getClosePrice());
                    rsi1MaxEma = (Rmax + (6 - 1) * rsi1MaxEma) / 6;
                    rsi1ABSEma = (RAbs + (6 - 1) * rsi1ABSEma) / 6;

                    rsi2MaxEma = (Rmax + (12 - 1) * rsi2MaxEma) / 12;
                    rsi2ABSEma = (RAbs + (12 - 1) * rsi2ABSEma) / 12;

                    rsi3MaxEma = (Rmax + (24 - 1) * rsi3MaxEma) / 24;
                    rsi3ABSEma = (RAbs + (24 - 1) * rsi3ABSEma) / 24;

                    rsi1 = (rsi1MaxEma / rsi1ABSEma) * 100;
                    rsi2 = (rsi2MaxEma / rsi2ABSEma) * 100;
                    rsi3 = (rsi3MaxEma / rsi3ABSEma) * 100;
                }
                point.rsi1 = rsi1;
                point.rsi2 = rsi2;
                point.rsi3 = rsi3;
            }
        } catch (Exception e) {

        }

    }

    /**
     * 计算kdj
     *
     * @param datas
     */
    public static void calculateKDJ(List<KLineEntity> datas) {
        try {
            float k = 0;
            float d = 0;


            for (int i = 0; i < datas.size(); i++) {
                KLineEntity point = datas.get(i);
                final float closePrice = point.getClosePrice();
                int startIndex = i - 9;
                if (startIndex < 0) {
                    startIndex = 0;
                }
                float max9 = Float.MIN_VALUE;
                float min9 = Float.MAX_VALUE;
                for (int index = startIndex; index <= i; index++) {
                    max9 = Math.max(max9, datas.get(index).getHighPrice());
                    min9 = Math.min(min9, datas.get(index).getLowPrice());
                }

                float rsv;
                if (max9 - min9 == 0) {
                    rsv = 100f * (closePrice - min9) / 100;
                } else {
                    rsv = 100f * (closePrice - min9) / (max9 - min9);
                }

                if (i == 0) {
                    k = rsv;
                    d = rsv;
                } else {
                    k = (rsv + 2f * k) / 3;
                    d = (k + 2f * d) / 3;
                }
                point.k = k;
                point.d = d;
                point.j = 3 * k - 2 * d;
            }
        } catch (Exception e) {

        }

    }

    /**
     * 计算macd
     *
     * @param datas
     */
    public static void calculateMACD(List<KLineEntity> datas) {
        try {
            float ema12 = 0;
            float ema26 = 0;
            float dif = 0;
            float dea = 0;
            float macd = 0;


            for (int i = 0; i < datas.size(); i++) {
                KLineEntity point = datas.get(i);
                final float closePrice = point.getClosePrice();

                if (i == 0) {
                    ema12 = closePrice;
                    ema26 = closePrice;
                } else {
//                EMA（12） = 前一日EMA（12） X 11/13 + 今日收盘价 X 2/13
//                EMA（26） = 前一日EMA（26） X 25/27 + 今日收盘价 X 2/27
                    //短期
                    ema12 = ema12 * (12 - 1) / (12 + 1) + closePrice * 2f / (12 + 1);
                    //长期
                    ema26 = ema26 * (26 - 1) / (26 + 1) + closePrice * 2f / (26 + 1);
                }
//            DIF = EMA（12） - EMA（26） 。
//            今日DEA = （前一日DEA X 8/10 + 今日DIF X 2/10）
//            用（DIF-DEA）*2即为MACD柱状图。

                dif = ema12 - ema26;
                //m
                dea = dea * (9 - 1) / (9 + 1) + dif * 2f / (9 + 1);
                macd = (dif - dea) * 2f;
                point.dif = dif;
                point.dea = dea;
                point.macd = macd;
            }
        } catch (Exception e) {

        }

    }

    /**
     * 计算 BOLL 需要在计算ma之后进行
     *
     * @param datas
     */
    public static void calculateBOLL(List<KLineEntity> datas) {
        try {
            for (int i = 0; i < datas.size(); i++) {
                KLineEntity point = datas.get(i);
                final float closePrice = point.getClosePrice();
                if (i == 0) {
                    point.mb = closePrice;
                    point.up = Float.NaN;
                    point.dn = Float.NaN;
                } else {
                    int n = 20;
                    //下方判断，原来是20
                    if (i < 20) {
                        n = i + 1;
                    }
                    float md = 0;
                    for (int j = i - n + 1; j <= i; j++) {
                        float c = datas.get(j).getClosePrice();
                        float m = point.getMA20Price();
                        float value = c - m;
                        md += value * value;
                    }
                    md = md / (n - 1);
                    md = (float) Math.sqrt(md);
                    point.mb = point.getMA20Price();
                    point.up = point.mb + 2 * md;
                    point.dn = point.mb - 2 * md;
                }
            }
        } catch (Exception e) {

        }


    }

    /**
     * 计算ma
     *
     * @param datas
     */
    public static void calculateMA(List<KLineEntity> datas) {
        try {
            float ma5 = 0;
            float ma10 = 0;
            float ma20 = 0;
            float ma7 = 0;
            float ma30 = 0;


            for (int i = 0; i < datas.size(); i++) {
                KLineEntity point = datas.get(i);
                final float closePrice = point.getClosePrice();

                ma5 += closePrice;
                ma10 += closePrice;
                ma20 += closePrice;
                ma7 += closePrice;
                ma30 += closePrice;

                if (i >= 5) {
                    ma5 -= datas.get(i - 5).getClosePrice();
                    point.MA5Price = ma5 / 5f;
                } else {
                    point.MA5Price = ma5 / (i + 1f);
                }

                if (i >= 7) {
                    ma7 -= datas.get(i - 7).getClosePrice();
                    point.Ma7Price = ma7 / 7;
                } else {
                    point.Ma7Price = ma7 / (i + 1f);
                }


                if (i >= 30) {
                    ma30 -= datas.get(i - 30).getClosePrice();
                    point.Ma30Price = ma30 / 30;
                } else {
                    point.Ma30Price = ma30 / (i + 1f);
                }


                if (i >= 10) {
                    ma10 -= datas.get(i - 10).getClosePrice();
                    point.MA10Price = ma10 / 10f;
                } else {
                    point.MA10Price = ma10 / (i + 1f);
                }


                if (i >= 20) {
                    ma20 -= datas.get(i - 20).getClosePrice();
                    point.MA20Price = ma20 / 20f;
                } else {
                    point.MA20Price = ma20 / (i + 1f);
                }


            }
        } catch (Exception e) {

        }

    }

    /**
     * 获取成交量的平均值
     *
     * @param datas
     */
    public static void calculateVolMA(List<KLineEntity> datas) {
        try {
            float valMa7 = 0;
            float valMa30 = 0;

            for (int i = 0; i < datas.size(); i++) {
                KLineEntity point = datas.get(i);
                final float vloume = point.getVolume();

                valMa7 += vloume;
                valMa30 += vloume;

                if (i >= 7) {
                    valMa7 -= datas.get(i - 7).getVolume();
                    point.VolMa7 = valMa7 / 7f;
                } else {
                    point.VolMa7 = valMa7 / (i + 1f);
                }

                if (i >= 30) {
                    valMa30 -= datas.get(i - 30).getVolume();
                    point.VolMa30 = valMa30 / 30f;
                } else {
                    point.VolMa30 = valMa30 / (i + 1f);
                }
            }
        } catch (Exception e) {

        }

    }

    /**
     * 计算EMA
     *
     * @param datas
     */
    public static void calculateEMA(List<KLineEntity> datas) {
        try {

            double k7 = 2.0 / (7 + 1.0);
            double k30 = 2.0 / (30 + 1.0);

            double ema7 = datas.get(0).getClosePrice();
            double ema30 = datas.get(0).getClosePrice();

            for (int i = 0; i < datas.size(); i++) {
                KLineEntity kLineEntity = datas.get(i);
                //第二天以后，当天收盘价乘以系数再加上昨天EMA乘以系数 -1
                ema7 = kLineEntity.getClosePrice() * k7 + ema7 * (1 - k7);
                ema30 = kLineEntity.getClosePrice() * k30 + ema30 * (1 - k30);
                kLineEntity.ema7 = ema7;
                kLineEntity.ema30 = ema30;
            }
        } catch (Exception e) {

        }

    }

    /**
     * 计算MA BOLL RSI KDJ MACD
     */
    public static void calculate(List<KLineEntity> datas, int index) {
        switch (index) {
            case 1:
                calculateMA(datas);
                break;
            case 2:
                calculateEMA(datas);
                break;
            case 3:
                calculateBOLL(datas);
                break;
            case 4:
                calculateVolMA(datas);
                break;
            case 5:
                calculateMACD(datas);
                break;
            case 6:
                calculateKDJ(datas);
                break;
            case 7:
                calculateRSI(datas);
                break;
        }


    }
}
