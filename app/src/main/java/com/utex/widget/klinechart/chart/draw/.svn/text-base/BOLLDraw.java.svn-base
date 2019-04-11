package com.utex.widget.klinechart.chart.draw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.utex.R;
import com.utex.utils.Utils;
import com.utex.widget.klinechart.chart.BaseKChartView;
import com.utex.widget.klinechart.chart.EntityImpl.BOLLImpl;
import com.utex.widget.klinechart.chart.EntityImpl.CandleImpl;
import com.utex.widget.klinechart.chart.impl.IChartDraw;

/**
 * BOLL实现类
 * Created by Demon on 2017/10/17.
 */

public class BOLLDraw implements IChartDraw<BOLLImpl> {

    private Paint mUpPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mMbPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mDnPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint volPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public BOLLDraw(BaseKChartView view) {
//        volPaint.setTextSize(28);
        volPaint.setColor(Color.parseColor("#ffffff"));
    }

    @Override
    public void drawTranslated(@Nullable BOLLImpl lastPoint, @NonNull BOLLImpl curPoint, float lastX, float curX, @NonNull Canvas canvas, @NonNull BaseKChartView view, int position) {
//        view.drawChildLine(canvas, mUpPaint, lastX, lastPoint.getUp(), curX, curPoint.getUp());
//        view.drawChildLine(canvas, mMbPaint, lastX, lastPoint.getMb(), curX, curPoint.getMb());
//        view.drawChildLine(canvas, mDnPaint, lastX, lastPoint.getDn(), curX, curPoint.getDn());
        view.drawMainLine(canvas, mUpPaint, lastX, lastPoint.getUp(), curX, curPoint.getUp());
        view.drawMainLine(canvas, mMbPaint, lastX, lastPoint.getMb(), curX, curPoint.getMb());
        view.drawMainLine(canvas, mDnPaint, lastX, lastPoint.getDn(), curX, curPoint.getDn());
    }

    @Override
    public void drawText(@NonNull Canvas canvas, @NonNull BaseKChartView view, int position, float x, float y) {
        String text = "";
        BOLLImpl point = (BOLLImpl) view.getItem(position);
        CandleImpl vol = (CandleImpl) view.getItem(position);
        y = 32;
        x += 20;
        if (view.lightType == 1) {
            volPaint.setColor(Color.parseColor("#5c5251"));
        } else {
            volPaint.setColor(Color.parseColor("#5c5251"));
        }

        text = Utils.getResourceString(R.string.liang)+":" + view.formatValue(vol.getVolume()) + "";
        canvas.drawText(text, x, y, volPaint);

        x += mUpPaint.measureText(text);
        text = "UP:" + view.formatValue(point.getUp()) + " ";
        canvas.drawText(text, x, y, mUpPaint);

        x += mUpPaint.measureText(text);
        text = "MB:" + view.formatValue(point.getMb()) + " ";
        canvas.drawText(text, x, y, mMbPaint);

        x += mMbPaint.measureText(text);
        text = "DN:" + view.formatValue(point.getDn()) + " ";
        canvas.drawText(text, x, y, mDnPaint);
    }

    @Override
    public float getMaxValue(BOLLImpl point) {

        if (Float.isNaN(point.getUp())) {
            return point.getMb();
        }
        return point.getUp();

    }

    @Override
    public float getMinValue(BOLLImpl point) {
        if (Float.isNaN(point.getDn())) {
            return point.getMb();
        }
        return point.getDn();
    }

    /**
     * 设置up颜色
     */
    public void setUpColor(int color) {
        mUpPaint.setColor(color);
    }

    /**
     * 设置mb颜色
     *
     * @param color
     */
    public void setMbColor(int color) {
        mMbPaint.setColor(color);
    }

    /**
     * 设置dn颜色
     */
    public void setDnColor(int color) {
        mDnPaint.setColor(color);
    }

    /**
     * 设置曲线宽度
     */
    public void setLineWidth(float width) {
        mUpPaint.setStrokeWidth(width);
        mMbPaint.setStrokeWidth(width);
        mDnPaint.setStrokeWidth(width);
        volPaint.setStrokeWidth(width);
    }

    /**
     * 设置文字大小
     */
    public void setTextSize(float textSize) {
        mUpPaint.setTextSize(textSize);
        mMbPaint.setTextSize(textSize);
        mDnPaint.setTextSize(textSize);
        volPaint.setTextSize(textSize);
    }
}
