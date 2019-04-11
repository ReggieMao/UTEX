package com.utex.widget.klinechart.chart.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.utex.R;
import com.utex.utils.Utils;
import com.utex.widget.klinechart.chart.BaseKChartView;
import com.utex.widget.klinechart.chart.EntityImpl.CandleImpl;
import com.utex.widget.klinechart.chart.impl.IChartDraw;


/**
 * macd实现类
 * Created by Demon on 2017/10/17.
 */

public class VolumeDraw implements IChartDraw<CandleImpl> {

    private Paint mRedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mGreenPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Paint mMa7Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mMa30Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint volPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float mMACDWidth = 0;

    public VolumeDraw(BaseKChartView view) {
        Context context = view.getContext();
        mRedPaint.setColor(ContextCompat.getColor(context, R.color.bf55151));
        mGreenPaint.setColor(ContextCompat.getColor(context, R.color.b10c970));
        volPaint.setTextSize(28);
        volPaint.setColor(Color.parseColor("#ffffff"));
    }

    @Override
    public void drawTranslated(@Nullable CandleImpl lastPoint, @NonNull CandleImpl curPoint, float lastX, float curX, @NonNull Canvas canvas, @NonNull BaseKChartView view, int position) {
        drawVolume(canvas, view, curX, curPoint.getMacd(), curPoint.getVolume(), curPoint.getClosePrice(), curPoint.getOpenPrice());
        view.drawChildLine(canvas, mMa7Paint, lastX, lastPoint.getVolMA7Price(), curX, curPoint.getVolMA7Price());
        view.drawChildLine(canvas, mMa30Paint, lastX, lastPoint.getVolMA30Price(), curX, curPoint.getVolMA30Price());
    }

    @Override
    public void drawText(@NonNull Canvas canvas, @NonNull BaseKChartView view, int position, float x, float y) {
//        mMa30Paint.setTextSize(28);
//        mMa7Paint.setTextSize(28);
        if (view.lightType == 1) {
            volPaint.setColor(Color.parseColor("#5c5251"));
        } else {
            volPaint.setColor(Color.parseColor("#5c5251"));
        }

        y += 8;
        x += 20;
        String text = "";
        CandleImpl point = (CandleImpl) view.getItem(position);
        text = Utils.getResourceString(R.string.liang) + ":" + view.formatValue(point.getVolume()) + " ";
        canvas.drawText(text, x, y, volPaint);

        x += mMa7Paint.measureText(text);
        text = "MA7:" + view.formatValue(point.getVolMA7Price()) + " ";
        canvas.drawText(text, x, y, mMa7Paint);

        x += mMa30Paint.measureText(text);
        text = "MA30:" + view.formatValue(point.getVolMA30Price()) + " ";
        canvas.drawText(text, x, y, mMa30Paint);
    }

    @Override
    public float getMaxValue(CandleImpl point) {
        return Math.max(Math.max(point.getVolume(), point.getVolMA7Price()), point.getVolMA30Price());
    }

    @Override
    public float getMinValue(CandleImpl point) {
//        return Math.min(Math.min(point.getVolume(), point.getVolMA7Price()), point.getVolMA30Price());
        return 0;
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

    /**
     * 画macd
     *
     * @param canvas
     * @param x
     * @param macd
     * @param volume
     * @param closePrice
     * @param openPrice
     */
    private void drawVolume(Canvas canvas, BaseKChartView view, float x, float macd, float volume, float closePrice, float openPrice) {
        float volumeShow = view.getChildY(volume - mVolumeMin);
        float r = mMACDWidth / 2;
        float zeroy = view.getChildY(0);

        if (closePrice > openPrice) {
            canvas.drawRect(x - r, volumeShow, x + r, zeroy, mGreenPaint);
        } else {
            canvas.drawRect(x - r, volumeShow, x + r, zeroy, mRedPaint);
        }
    }


    /**
     * 设置MACD的宽度
     *
     * @param MACDWidth
     */
    public void setMACDWidth(float MACDWidth) {
        mMACDWidth = MACDWidth;
    }

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
    }

    /**
     * 设置文字大小
     */
    public void setTextSize(float textSize) {
        mMa7Paint.setTextSize(textSize);
        mMa30Paint.setTextSize(textSize);
        volPaint.setTextSize(textSize);
    }

    private float mVolumeMin = Float.MAX_VALUE;

    private float mVolumeMax = Float.MIN_VALUE;

    public void setVolumeMin(float volumeMin) {
        mVolumeMin = volumeMin;
    }

    public void setVolumeMax(float volumeMax) {
        mVolumeMax = volumeMax;
    }

    public float getmVolumeMin() {
        return mVolumeMin;
    }

    public float getmVolumeMax() {
        return mVolumeMax;
    }
}
