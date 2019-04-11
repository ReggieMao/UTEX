package com.utex.widget.klinechart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.utex.R;
import com.utex.mvp.depth.bean.Depth;
import com.utex.utils.BubbleUtils;
import com.utex.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Demon on 2017/10/17.
 * 深度图
 * 横坐标 价格
 * 纵坐标 量
 * 其实吧，这玩意儿我是真不愿意写啊，你说这h5有了，还让我写原生，宝宝心累啊。
 */

public class DepthMap extends View {

    private int width;

    //折线图的左边距
    private float XPoint = 0;
    //折线图的高
    private float YPoint = 100;
    //折线图的横坐标拉伸系数
    private float XScale = 15; // 刻度长度

    private List<Float> leftPoints;

    private ArrayList<Float> rightPoints;


    /**
     * 最大值
     */
    private float max;

    private List<List<String>> bids;
    private List<List<String>> asks;
    private int showScale;


    public DepthMap(Context context) {
        this(context, null);
    }

    public DepthMap(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DepthMap(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;

        YPoint = h - BubbleUtils.dp2px(16);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            XPoint = 0;

            if (leftPoints == null || leftPoints.size() == 0 || rightPoints == null || rightPoints.size() == 0 || (leftPoints.size() - 2) == 0 || (rightPoints.size() - 2) == 0) {
                return;
            }

            //画图
            XScale = (width / 2) / (leftPoints.size() - 2);
            drawLeftDepthMap(canvas);
            XScale = (width - XPoint) / (rightPoints.size() - 2);
            drawRightDepthMap(canvas);
//            if (dataListener != null) {
//                dataListener.switchBotton(2, rightPoints.get(0), rightPoints.get(0));
//            }
            //画值
            drawText(canvas);
            //绘制买单 卖单
            drawBuySell(canvas);
        } catch (Exception e) {

        }
    }

    private void drawBuySell(Canvas canvas) {
        Paint fontPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fontPaint.setColor(Utils.getResourceColor(getContext(), R.color.f50b577));
        fontPaint.setTextSize(BubbleUtils.sp2px(10));

        int height = getHeight() / 12;
        int width = getWidth();
        Rect rect = new Rect();
        fontPaint.getTextBounds(Utils.getResourceString(R.string.buy_dan), 0, Utils.getResourceString(R.string.buy_dan).length(), rect);
        int textHeight = rect.height();
        int textWidth = rect.width();

        //买单
        canvas.drawRoundRect(new RectF(BubbleUtils.dp2px(4), height - textHeight / 2 - BubbleUtils.dp2px(4), BubbleUtils.dp2px(10) + textWidth, height + textHeight / 2), 5, 5, fontPaint);
        fontPaint.setColor(Utils.getResourceColor(getContext(), R.color.fffffff));
        canvas.drawText(Utils.getResourceString(R.string.buy_dan), BubbleUtils.dp2px(7), height + textHeight / 2 - BubbleUtils.dp2px(3), fontPaint);

        fontPaint.setColor(Utils.getResourceColor(getContext(), R.color.fee6a5e));
        canvas.drawRoundRect(new RectF(width - textWidth - BubbleUtils.dp2px(10), height - textHeight / 2 - BubbleUtils.dp2px(4), width - BubbleUtils.dp2px(4), height + textHeight / 2), 5, 5, fontPaint);
        fontPaint.setColor(Utils.getResourceColor(getContext(), R.color.fffffff));
        canvas.drawText(Utils.getResourceString(R.string.sell_dan), width - textWidth - BubbleUtils.dp2px(7), height + textHeight / 2 - BubbleUtils.dp2px(3), fontPaint);

    }

    private void drawText(Canvas canvas) {
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Utils.getResourceColor(getContext(), R.color.b666666_99ffffff));
        textPaint.setTextSize(BubbleUtils.sp2px(9));

        float val = max / 5;
        float heitht = YPoint / 5;
        for (int i = 0; i < 5; i++) {
            if (i != 0) {
                Rect rect = new Rect();
                textPaint.getTextBounds(String.valueOf(max - i * val), 0, String.valueOf(max - i * val).length(), rect);
                int width = rect.width();
                canvas.drawText(String.valueOf(max - i * val), getWidth() - width - BubbleUtils.dp2px(4), i * heitht, textPaint);
            }
        }

//        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        textPaint.setColor(Utils.getResourceColor(getContext(), R.color.b666666_99ffffff));
//        textPaint.setTextSize(BubbleUtils.sp2px(9));

        //画值
        String value = String.valueOf(Utils.getScientificCountingMethodAfterData((Double.parseDouble(asks.get(0).get(0)) + Double.parseDouble(bids.get(0).get(0))) / 2, showScale));
        float width = textPaint.measureText(value);

        canvas.drawText(value, XPoint - width / 2, YPoint + BubbleUtils.dp2px(11), textPaint);

        value = Utils.getScientificCountingMethodAfterData(Double.valueOf(bids.get(0).get(0)), showScale);
        canvas.drawText(value, 0, YPoint + BubbleUtils.dp2px(11), textPaint);

        value = Utils.getScientificCountingMethodAfterData(Double.valueOf(asks.get(asks.size() - 1).get(0)), showScale);
        Rect rect = new Rect();
        textPaint.getTextBounds(value, 0, value.length(), rect);

        canvas.drawText(value, getWidth() - rect.width() * 1.1f, YPoint + BubbleUtils.dp2px(11), textPaint);
    }

    /**
     * 画左边图
     *
     * @param canvas
     */
    private void drawLeftDepthMap(Canvas canvas) {
//        canvas.drawLines(leftPointArray, leftLinePaint);
        Paint paint = new Paint();
        paint.setColor(Utils.getResourceColor(getContext(), R.color.f50b577));
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true); // 去锯齿
        paint.setDither(true);

        Paint paint2 = new Paint();
        paint2.setColor(Utils.getResourceColor(getContext(), R.color.f50b577));
        paint2.setStyle(Paint.Style.FILL);
        paint2.setAntiAlias(true);
        paint2.setAlpha(51);
        paint2.setDither(true);

        if (leftPoints.size() > 1) {
            Path path = new Path();
            Path path2 = new Path();
            path.moveTo(XPoint, YPoint);
            path2.moveTo(XPoint, YPoint);
            for (int i = 0; i < leftPoints.size() - 1; i++) {
                path.lineTo(i * XScale, getY(leftPoints.get(i)));
                path2.lineTo(i * XScale, getY(leftPoints.get(i)));
                XPoint = i * XScale;
            }
            path.lineTo(XPoint, YPoint);
            path2.lineTo(XPoint, YPoint);
            canvas.drawPath(path, paint);
            canvas.drawPath(path2, paint2);
        }
    }

    /**
     * 画右边图
     *
     * @param canvas
     */
    private void drawRightDepthMap(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Utils.getResourceColor(getContext(), R.color.fee6a5e));
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true); // 去锯齿

        Paint paint2 = new Paint();
        paint2.setColor(Utils.getResourceColor(getContext(), R.color.fee6a5e));
        paint2.setStyle(Paint.Style.FILL);
        paint2.setAlpha(51);
        paint2.setAntiAlias(true);
        if (rightPoints.size() > 1) {
            Path path = new Path();
            Path path2 = new Path();
            path.moveTo(XPoint, getY(rightPoints.get(0)));
            path2.moveTo(XPoint, getY(rightPoints.get(0)));
            for (int i = 0; i < rightPoints.size(); i++) {
                path.lineTo(XPoint + i * XScale, getY(rightPoints.get(i)));
                path2.lineTo(XPoint + i * XScale, getY(rightPoints.get(i)));
            }
            path.lineTo(width, YPoint);
            path2.lineTo(width, YPoint);
            canvas.drawPath(path, paint);
            canvas.drawPath(path2, paint2);
        }
    }

    /**
     * 设置点
     *
     * @param data
     */
    public void setPoint(Depth data) {
        doData(data);

        invalidate();
    }

    /**
     * 做数据
     *
     * @param data
     */
    private void doData(Depth data) {
        //开始处理数据了昂，数据处理的好，咱才能写的好
        bids = data.getBids();

        asks = data.getAsks();

        leftPoints = new ArrayList<>();

        rightPoints = new ArrayList<>();

        //写点
        try {
            for (int i = 0; i < bids.size(); i++) {
                if (i == 0) {
                    leftPoints.add(Float.parseFloat(bids.get(i).get(1)));
                } else {
                    leftPoints.add(leftPoints.get(i - 1) + (Float.parseFloat(bids.get(i).get(1))));
                }
            }

            for (int i = 0; i < asks.size(); i++) {
                if (i == 0) {
                    rightPoints.add(Float.parseFloat(asks.get(i).get(1)));
                } else {
                    rightPoints.add((rightPoints.get(i - 1) + Float.parseFloat(asks.get(i).get(1))));
                }
            }

            if (leftPoints.get(leftPoints.size() - 1) >= rightPoints.get(rightPoints.size() - 1)) {
                max = leftPoints.get(leftPoints.size() - 1);
            } else {
                max = rightPoints.get(rightPoints.size() - 1);
            }
//            min = 0;

            Log.e("TAG", width + "");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //        反转
            Collections.reverse(leftPoints);
            leftPoints.add(0f);
            rightPoints.add(0, 0f);
            invalidate();
        }

    }

    /**
     * 获得Y轴坐标
     *
     * @param curr
     * @return
     */
    public float getY(float curr) {
        float value = YPoint - curr * (YPoint * 1.0f / max);
        return value;
    }

    public void setShowScale(int showScale) {
        this.showScale = showScale;
    }

    public int getShowScale() {
        return showScale;
    }

    public interface DepathMapListener {
        void switchBotton(int type, float price, float price2);
    }

}
