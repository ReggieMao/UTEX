package com.utex.widget.klinechart.chart.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.utex.R;
import com.utex.utils.Utils;
import com.utex.widget.klinechart.chart.BaseKChartView;
import com.utex.widget.klinechart.chart.EntityImpl.CandleImpl;
import com.utex.widget.klinechart.chart.impl.IChartDraw;


/**
 * MA实现类
 * MA7
 * MA30
 * Created by Demon on 2017/10/17.
 */

public class MADraw implements IChartDraw<CandleImpl> {

    private final Context mContext;
    private Paint mMA7Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mMA30Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint volPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public MADraw(BaseKChartView view) {
        Context context = view.getContext();
        mContext = context;
//        mMA7Paint.setTextSize(28);
//        mMA30Paint.setTextSize(28);
//        volPaint.setTextSize(28);


        volPaint.setColor(Color.parseColor("#ffffff"));
    }


    @Override
    public void drawTranslated(@Nullable CandleImpl lastPoint, @NonNull CandleImpl curPoint, float lastX, float curX, @NonNull Canvas canvas, @NonNull BaseKChartView view, int position) {
        view.drawMainLine(canvas, mMA7Paint, lastX, lastPoint.getMA7Price(), curX, curPoint.getMA7Price());
        view.drawMainLine(canvas, mMA30Paint, lastX, lastPoint.getMA30Price(), curX, curPoint.getMA30Price());
    }

    @Override
    public void drawText(@NonNull Canvas canvas, @NonNull BaseKChartView view, int position, float x, float y) {
        String text = "";
        CandleImpl point = (CandleImpl) view.getItem(position);
        y = 32;
        x += 20;

        if (view.lightType == 1) {
            volPaint.setColor(Color.parseColor("#5c5251"));
        } else {
            volPaint.setColor(Color.parseColor("#5c5251"));
        }

        text = Utils.getResourceString(R.string.liang) + ":" + view.formatValue(point.getVolume()) + " ";
        canvas.drawText(text, x, y, volPaint);

        x += mMA7Paint.measureText(text);
        text = "MA" + 7 + ":" + view.formatValue(point.getMA7Price()) + " ";
        canvas.drawText(text, x, y, mMA7Paint);

        x += mMA30Paint.measureText(text);
        text = "MA" + 30 + ":" + view.formatValue(point.getMA30Price()) + " ";
        canvas.drawText(text, x, y, mMA30Paint);


    }

    @Override
    public float getMaxValue(CandleImpl point) {
        return Math.max(Math.max(point.getHighPrice(), point.getMA7Price()), point.getMA30Price());
    }

    @Override
    public float getMinValue(CandleImpl point) {
        return Math.min(Math.min(point.getHighPrice(), point.getMA7Price()), point.getMA30Price());

    }


    /**
     * 设置MA7颜色
     */
    public void setMA7Color(int color) {
        mMA7Paint.setColor(color);
    }

    /**
     * 设置MA30颜色
     */
    public void setMA30Color(int color) {
        mMA30Paint.setColor(color);
    }


    /**
     * 设置曲线宽度
     */
    public void setLineWidth(float width) {
        mMA7Paint.setStrokeWidth(width);
        mMA30Paint.setStrokeWidth(width);
        volPaint.setStrokeWidth(width);
    }

    /**
     * 设置文字大小
     */
    public void setTextSize(float textSize) {
        mMA7Paint.setTextSize(textSize);
        mMA30Paint.setTextSize(textSize);
        volPaint.setTextSize(textSize);
    }
}
