package com.utex.widget.klinechart.chart.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.utex.R;
import com.utex.utils.Utils;
import com.utex.widget.klinechart.chart.BaseKChartView;
import com.utex.widget.klinechart.chart.EntityImpl.CandleImpl;
import com.utex.widget.klinechart.chart.EntityImpl.EMAImpl;
import com.utex.widget.klinechart.chart.impl.IChartDraw;

/**
 * ema
 * Created by Demon on 2017/10/17.
 */

public class EMADraw implements IChartDraw<EMAImpl> {

    private Paint mMa7Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mMa30Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint volPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float mMACDWidth = 0;

    public EMADraw(BaseKChartView view) {
        Context context = view.getContext();
//        mMa7Paint.setTextSize(28);
//        mMa30Paint.setTextSize(28);
//        volPaint.setTextSize(28);
        volPaint.setColor(Color.parseColor("#ffffff"));
    }

    @Override
    public void drawTranslated(@Nullable EMAImpl lastPoint, @NonNull EMAImpl curPoint, float lastX, float curX, @NonNull Canvas canvas, @NonNull BaseKChartView view, int position) {
        Log.e("EMA", "lastEMA7:" + lastPoint.getEma7() + "   lastEMA30:" + lastPoint.getEma30() + "   currEMA7:" + curPoint.getEma7() + " currEMA30:" + curPoint.getEma30());

        view.drawMainLine(canvas, mMa7Paint, lastX, (float) lastPoint.getEma7(), curX, (float) curPoint.getEma7());
        view.drawMainLine(canvas, mMa30Paint, lastX, (float) lastPoint.getEma30(), curX, (float) curPoint.getEma30());
    }

    @Override
    public void drawText(@NonNull Canvas canvas, @NonNull BaseKChartView view, int position, float x, float y) {

        String text = "";
        EMAImpl point = (EMAImpl) view.getItem(position);
        CandleImpl vol = (CandleImpl) view.getItem(position);
        y = 32;
        x += 20;
        if (view.lightType == 1) {
            volPaint.setColor(Color.parseColor("#5c5251"));
        } else {
            volPaint.setColor(Color.parseColor("#5c5251"));
        }

        text = Utils.getResourceString(R.string.liang) + ":" + view.formatValue(vol.getVolume()) + " ";
        canvas.drawText(text, x, y, volPaint);

        x += mMa7Paint.measureText(text);
        text = "EMA" + 7 + ":" + view.formatValue((float) point.getEma7()) + " ";
        canvas.drawText(text, x, y, mMa7Paint);

        x += mMa30Paint.measureText(text);
        text = "EMA" + 30 + ":" + view.formatValue((float) point.getEma30()) + " ";
        canvas.drawText(text, x, y, mMa30Paint);
    }

    @Override
    public float getMaxValue(EMAImpl point) {

        return (float) Math.max(point.getEma7(), point.getEma30());
    }

    @Override
    public float getMinValue(EMAImpl point) {
        return (float) Math.min(point.getEma7(), point.getEma30());
    }


//    @Override
//    public float getMaxValue(MACDImpl point) {
//        return Math.max(point.getMacd(), Math.max(point.getDea(), point.getDif()));
//    }
//
//    @Override
//    public float getMinValue(MACDImpl point) {
//        return 0;
//    }


    public void setMa7Color(int color) {
        mMa7Paint.setColor(color);
    }

    public void setMa30Color(int color) {
        mMa30Paint.setColor(color);
    }


    /**
     * 设置曲线宽度
     */
    public void setLineWidth(float width) {
        mMa7Paint.setStrokeWidth(width);
        mMa30Paint.setStrokeWidth(width);
        volPaint.setStrokeWidth(width);
    }

    /**
     * 设置文字大小
     */
    public void setTextSize(float textSize) {
        mMa7Paint.setTextSize(textSize);
        mMa30Paint.setTextSize(textSize);
        volPaint.setTextSize(textSize);
    }

}
