package com.utex.widget.klinechart.chart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import com.utex.R;
import com.utex.utils.Utils;
import com.utex.widget.klinechart.chart.EntityImpl.KLineImpl;
import com.utex.widget.klinechart.chart.draw.MainDraw;
import com.utex.widget.klinechart.chart.draw.VolumeDraw;
import com.utex.widget.klinechart.chart.formatter.TimeFormatter;
import com.utex.widget.klinechart.chart.formatter.ValueFormatter;
import com.utex.widget.klinechart.chart.impl.IAdapter;
import com.utex.widget.klinechart.chart.impl.IChartDraw;
import com.utex.widget.klinechart.chart.impl.IDateTimeFormatter;
import com.utex.widget.klinechart.chart.impl.IValueFormatter;
import com.utex.widget.klinechart.utils.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * k线图
 * Created by Demon on 2017/10/17.
 */
public abstract class BaseKChartView extends ScrollAndScaleView {
    private int mChildDrawPosition = 0;

    private float mTranslateX = Float.MIN_VALUE;

    private int mHeight = 0;

    private int mWidth = 0;

    private int mTopPadding;

    private int mBottomPadding;

    private float mMainScaleY = 1;

    private float mChildScaleY = 1;

    private float mDataLen = 0;

    private float mMainMaxValue = Float.MAX_VALUE;

    private float mMainMinValue = Float.MIN_VALUE;

    private float mChildMaxValue = Float.MAX_VALUE;

    private float mChildMinValue = Float.MIN_VALUE;

    private int mStartIndex = 0;

    private int mStopIndex = 0;

    private int mGridRows = 4;

    private int mGridColumns = 4;

    private Paint mGridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Paint mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Paint mSelectedLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int mSelectedIndex;

    private IChartDraw mMainDraw;

    private IAdapter mAdapter;

    private DataSetObserver mDataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            mItemCount = getAdapter().getCount();
            notifyChanged();
        }

        @Override
        public void onInvalidated() {
            mItemCount = getAdapter().getCount();
            notifyChanged();
        }
    };

    //当前点的个数
    private int mItemCount;
    /**
     * 当前绘画的图
     */
    private IChartDraw mChildDraw;
    /**
     * 当前绘画的图
     */
    private IChartDraw mMainLineDraw;
    /**
     * 子视图要画的
     */
    private List<IChartDraw> mChildDraws = new ArrayList<>();
    /**
     * 主视图要画的线
     */
    private List<IChartDraw> mMainDraws = new ArrayList<>();

    private IValueFormatter mValueFormatter;
    private IDateTimeFormatter mDateTimeFormatter;

    protected KChartTabView mKChartTabView;

    private ValueAnimator mAnimator;

    private long mAnimationDuration = 500;

    private float mOverScrollRange = 0;

    private OnSelectedChangedListener mOnSelectedChangedListener = null;

    public Rect mMainRect;

    private Rect mTabRect;

    public Rect mChildRect;

    private float mLineWidth;
    private int mChildHeight;
    public String currPrice;
    /**
     * 亮度类型
     * 1 暗
     * 2 亮
     * 默认 2
     */
    public int lightType = 2;
    /**
     * 是否分时
     */
    public boolean isMinutesHour = false;
    /**
     * 是否竖屏
     */
    public boolean isVerticalScreen = true;

    private int centerHeight;
    private float mainHeight;
    private int showScale;

    public BaseKChartView(Context context) {
        super(context);
        init();
    }

    public BaseKChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseKChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        mDetector = new GestureDetectorCompat(getContext(), this);
        mScaleDetector = new ScaleGestureDetector(getContext(), this);
        mTopPadding = (int) getResources().getDimension(R.dimen.chart_top_padding);
        mBottomPadding = (int) getResources().getDimension(R.dimen.chart_bottom_padding);

        mKChartTabView = new KChartTabView(getContext());
//        addView(mKChartTabView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mKChartTabView.setOnTabSelectListener(new KChartTabView.TabSelectListener() {
            @Override
            public void onTabSelected(int type) {
                setChildDraw(type);
            }
        });

        mAnimator = ValueAnimator.ofFloat(0f, 1f);
        mAnimator.setDuration(mAnimationDuration);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
        initRect(w, h);
//        mKChartTabView.setTranslationY(mMainRect.bottom);
//        mKChartTabView.setTranslationY(0);
        setTranslateXFromScrollX(mScrollX);
    }

    private void initRect(int w, int h) {
        int mMainChildSpace = mKChartTabView.getMeasuredHeight();
        int displayHeight = h - mTopPadding - mMainChildSpace;
        mainHeight = (float) ((mHeight - mTopPadding - mKChartTabView.getMeasuredHeight()) * 0.73);

//        int mMainHeight = (int) (displayHeight * 0.743);
//        mChildHeight = (int) (displayHeight * 0.257f);

        int mMainHeight = (int) (displayHeight * 0.66);
        mChildHeight = (int) (displayHeight * 0.23f);
        centerHeight = (int) (displayHeight * 0.06);
        //主视图的高度
        mMainRect = new Rect(0, 42, 0, mTopPadding + mMainHeight);

        //副视图的高度
        mChildRect = new Rect(0, (int) (mainHeight + centerHeight), w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(mBackgroundPaint.getColor());
        if (mWidth == 0 || mMainRect.height() == 0 || mItemCount == 0) {
            return;
        }
        //计算当前显示区域
        calculateValue();

        canvas.save();
        canvas.scale(1, 1);

        //画表格
        drawGird(canvas);

        //画K线
        drawK(canvas);

        //画文字
        drawText(canvas);

        //绘制左上角数值
        drawValue(canvas, isLongPress ? mSelectedIndex : mStopIndex);

        canvas.restore();
    }


    public float getMainY(float value) {
        return (mMainMaxValue - value) * mMainScaleY + mMainRect.top;
    }

    public float getMainCandleY(float value) {
        return (mMainMaxValue - value) * (mMainScaleY + 400) + mMainRect.top;
    }

    public float getChildY(float value) {
        return (mChildMaxValue - value) * mChildScaleY + mainHeight + centerHeight;
    }

    /**
     * 解决text居中的问题
     */
    public float fixTextY(float y) {
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        return (y + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent);
    }


    /**
     * 画表格
     *
     * @param canvas
     */
    private void drawGird(Canvas canvas) {
        //-----------------------上方k线图------------------------

        mGridPaint.setColor(Utils.getResourceColor(getContext(), R.color.be3e6e4_29ffffff));

        mGridPaint.setStrokeWidth(1);
        //横向的grid
        float rowSpace = mMainRect.height() / mGridRows;
        for (int i = 0; i <= mGridRows - 1; i++) {
            canvas.drawLine(0, rowSpace * i + mMainRect.top, mWidth, rowSpace * i + mMainRect.top, mGridPaint);
        }

        canvas.drawLine(0, 2, mWidth, 2, mGridPaint);

        canvas.drawLine(0, mainHeight, mWidth, mainHeight, mGridPaint);

//        //-----------------------下方子图------------------------
        canvas.drawLine(0, mChildRect.top, mWidth, mChildRect.top, mGridPaint);
        canvas.drawLine(0, mChildRect.bottom, mWidth, mChildRect.bottom, mGridPaint);


        canvas.drawLine(0, 0, 1, mainHeight, mGridPaint);
        canvas.drawLine(0, mChildRect.top, 1, mChildRect.bottom, mGridPaint);

        canvas.drawLine(mWidth - 3, 0, mWidth - 2, mainHeight, mGridPaint);
        canvas.drawLine(mWidth - 3, mChildRect.top, mWidth - 2, mChildRect.bottom, mGridPaint);


//        //纵向的grid
//        float columnSpace = mWidth / mGridColumns;
//        for (int i = 0; i <= mGridColumns; i++) {
//            canvas.drawLine(columnSpace * i, mMainRect.top, columnSpace * i, mMainRect.bottom, mGridPaint);
//            canvas.drawLine(columnSpace * i, mChildRect.top, columnSpace * i, mChildRect.bottom, mGridPaint);
//        }
    }


    private float mShowMaxValueTextX;
    private float mShowminValueTextX;

    private boolean isDrawMin;
    private boolean isDrawMax;


    protected float mPointWidth = 6;


    private int currMaxPosition;
    private int currMinPosition;

    /**
     * 画k线图
     *
     * @param canvas
     */
    private void drawK(Canvas canvas) {
        //保存之前的平移，缩放
        canvas.save();
        canvas.translate(mTranslateX * mScaleX, 0);
        canvas.scale(mScaleX, 1);

        for (int i = mStartIndex; i <= mStopIndex; i++) {
            Object currentPoint = getItem(i);
            float currentPointX = getX(i);
            Object lastPoint = i == 0 ? currentPoint : getItem(i - 1);
            float lastX = i == 0 ? currentPointX : getX(i - 1);

            //蜡烛线必须画的
            if (mMainDraw != null) {
                mMainDraw.drawTranslated(lastPoint, currentPoint, lastX, currentPointX, canvas, this, i);
            }

            if (mMainLineDraw != null) {
                mMainLineDraw.drawTranslated(lastPoint, currentPoint, lastX, currentPointX, canvas, this, i);
            }

            if (mChildDraw != null) {
                mChildDraw.drawTranslated(lastPoint, currentPoint, lastX, currentPointX, canvas, this, i);
            }

            if (!isMinutesHour) {
                KLineImpl currLine = (KLineImpl) currentPoint;
                if (mShowMaxValueText == currLine.getHighPrice() && isDrawMax) {
                    currMaxPosition = i;
                    //最大值
                    isDrawMax = false;
                }

                if (mShowMinValueText == currLine.getLowPrice() && isDrawMin) {
                    currMinPosition = i;
                    //最小值
                    isDrawMin = false;
                }
            }
        }

        //画选择线,横坐标，纵坐标的线
        if (mSelectedIndex > mStopIndex || mSelectedIndex < mStartIndex) {
            isLongPress = false;
        }


        if (isLongPress) {
            mSelectedLinePaint.setStrokeWidth(1);
            KLineImpl point = (KLineImpl) getItem(mSelectedIndex);
            float x = getX(mSelectedIndex);
            float y = getMainCandleY(point.getClosePrice());
            //纵轴线
            canvas.drawLine(x, mMainRect.top, x, mMainRect.bottom, mSelectedLinePaint);
            //横轴线
            canvas.drawLine(-mTranslateX, y, -mTranslateX + mWidth / mScaleX, y, mSelectedLinePaint);
            //副试图区线
            canvas.drawLine(x, mChildRect.top, x, mChildRect.bottom, mSelectedLinePaint);
        }


        //还原 平移缩放
        canvas.restore();

//        if (currMaxPosition != -1) {
//            //最大值在Y轴位置
//            boolean leftScreen = translateXtoX(getX(currMinPosition)) < getChartWidth() / 2;
//            float x = (getX(currMaxPosition) - getX(mStartIndex)) * mScaleX;
//            float y = getMainCandleY(mShowMaxValueText);
//
//
//            if (!leftScreen) {
//                float sub = 0;
////                if (currMaxPosition == mAdapter.getCount() - 1) {
////                    sub = mPointWidth + 15;
////                }
//                x = mWidth - 1 - translateXtoX(getX((mStopIndex))) * mScaleX - translateXtoX(getX((currMinPosition))) * mScaleX;
//            }
//
//            drawMinAndMax(canvas, x, y, leftScreen, String.valueOf(mShowMaxValueText));
//        }
//
//        if (currMinPosition != -1) {
//            //最小值在Y轴位置
//            boolean leftScreen = translateXtoX(getX(currMinPosition)) < getChartWidth() / 2;
//            float x = (getX(currMinPosition) - getX(mStartIndex)) * mScaleX;
//            float y = getMainCandleY(mShowMinValueText);
//
//            if (!leftScreen) {
//                float sub = 0;
////                if (currMinPosition == mAdapter.getCount() - 1) {
////                    sub = mPointWidth + 15;
////                }mStopIndex -
//                x = mWidth - 1 - translateXtoX(getX((mStopIndex))) * mScaleX - translateXtoX(getX((currMinPosition))) * mScaleX;
//            }
//
//            drawMinAndMax(canvas, x, y, leftScreen, String.valueOf(mShowMinValueText));
//        }
        if (!isMinutesHour) {
            drawMinAndMax(canvas, true, translateXtoX(getX(currMaxPosition)) < getChartWidth() / 2);
            drawMinAndMax(canvas, false, translateXtoX(getX(currMinPosition)) < getChartWidth() / 2);
        }
    }

    /**
     * 画最大值最小值
     *
     * @param canvas
     */
    private void drawMinAndMax(Canvas canvas, float x, float y, boolean isLeftScreen, String value) {
        Paint paint = new Paint();
        Paint paintBg = new Paint();
        if (lightType == 1) {
            paint.setColor(Color.parseColor("#1c2129"));
            paintBg.setColor(Color.parseColor("#858b90"));
        } else {
            paint.setColor(Color.parseColor("#ffffff"));
            paintBg.setColor(Color.parseColor("#4c5f77"));
        }
        //线长度
        int lineWidth = 30;

        paint.setTextSize(28);

        Rect rect = new Rect();
        paint.getTextBounds(value, 0, value.length(), rect);
        //字高度
        float textHeight = (rect.bottom - rect.top);

        if (isLeftScreen) {
            //屏幕左侧显示
            canvas.drawLine(x, y, x + lineWidth, y, paintBg);
            x += lineWidth;
            canvas.drawRect(x, y - textHeight / 2 - 10, x + paint.measureText(value) + 15, y + textHeight / 2 + 10, paintBg);

            canvas.drawText(value, x + 8, y + textHeight / 2, paint);
        } else {
            //在屏幕右侧显示
            x -= lineWidth;
            canvas.drawLine(x, y, x + lineWidth, y, paintBg);

            x -= (paint.measureText(value) + 15);
            canvas.drawRect(x, y - textHeight / 2 - 10, x + paint.measureText(value) + 15, y + textHeight / 2 + 10, paintBg);

            canvas.drawText(value, x + 8, y + textHeight / 2, paint);
        }
    }


    /**
     * 画最大值最小值
     *
     * @param canvas
     */
    private void drawMinAndMax(Canvas canvas, boolean isHeight, boolean leftScreen) {
        String maxValue = Utils.getScientificCountingMethodAfterData(Double.valueOf(mShowMaxValueText), showScale);
        String minValue = Utils.getScientificCountingMethodAfterData(Double.valueOf(mShowMinValueText), showScale);

        Paint paint = new Paint();
        Paint paintBg = new Paint();
        if (lightType == 1) {
            paint.setColor(Color.parseColor("#1c2129"));
            paintBg.setColor(Color.parseColor("#858b90"));
        } else {
            paint.setColor(Color.parseColor("#ffffff"));
            paintBg.setColor(Color.parseColor("#4c5f77"));
        }

        paint.setTextSize(mTextPaint.getTextSize());

        //线长度
        int lineWidth = 30;


        Rect rect = new Rect();
        paint.getTextBounds(String.valueOf(mShowMaxValueText), 0, String.valueOf(mShowMaxValueText).length(), rect);
        //字高度
        float textHeight = (rect.bottom - rect.top);

        //最大值在Y轴位置
        float maxY = getMainCandleY(mShowMaxValueText);
        //最小值在Y轴位置
        float minY = getMainCandleY(mShowMinValueText);
        if (leftScreen) {
            //大于屏幕宽度的一半，数值与箭头在左边显示
            if (isHeight) {
                //最大值
                float x = mWidth - (mStopIndex - currMaxPosition) * mPointWidth * mScaleX + mPointWidth * mScaleX / 2;
                if (!isVerticalScreen && mScrollX < 0 && mStopIndex == mAdapter.getCount() - 1) {
//                横屏
                    x -= Math.abs(mScrollX) * mScaleX / 2;
                }


                //屏幕左侧显示
                canvas.drawLine(x, maxY, x + lineWidth, maxY, paintBg);
                x += lineWidth;
                canvas.drawRect(x, maxY - textHeight / 2 - 10, x + paint.measureText(maxValue) + 15, maxY + textHeight / 2 + 10, paintBg);

                canvas.drawText(maxValue, x + 8, maxY + textHeight / 2, paint);

//                canvas.drawText("← " + String.valueOf(mShowMaxValueText), x, maxY, paint);
            } else {
                //
                float x = mWidth - (mStopIndex - currMinPosition) * mPointWidth * mScaleX + mPointWidth * mScaleX / 2;
                if (!isVerticalScreen && mScrollX < 0 && mStopIndex == mAdapter.getCount() - 1) {
//                横屏
                    x -= Math.abs(mScrollX) * mScaleX / 2;
                }

                canvas.drawLine(x, minY, x + lineWidth, minY, paintBg);
                x += lineWidth;
                canvas.drawRect(x, minY - textHeight / 2 - 10, x + paint.measureText(minValue) + 15, minY + textHeight / 2 + 10, paintBg);

                canvas.drawText(minValue, x + 8, minY + textHeight / 2, paint);

//                canvas.drawText("← " + String.valueOf(mShowMinValueText), value, minY + 10, paint);
            }
        } else {
            //
            if (isHeight) {
                float sub = 0;
                if (currMaxPosition == mAdapter.getCount() - 1) {
                    sub = mPointWidth + 15;
                }
                float x = mWidth - 1 - (mStopIndex - currMaxPosition) * mPointWidth * mScaleX - lineWidth / 2;

//                - getTextWidth(paint, mShowMinValueText + " →") - mPointWidth * mScaleX * 0.4f - sub;

                if (!isVerticalScreen && mScrollX < 0 && mStopIndex == mAdapter.getCount() - 1) {
//                横屏
                    x -= Math.abs(mScrollX) * mScaleX / 2;
                }

                x -= lineWidth;
                canvas.drawLine(x, maxY, x + lineWidth, maxY, paintBg);

                x -= (paint.measureText(maxValue) + 15);
                canvas.drawRect(x, maxY - textHeight / 2 - 10, x + paint.measureText(maxValue) + 15, maxY + textHeight / 2 + 10, paintBg);


                canvas.drawText(maxValue, x + 8, maxY + textHeight / 2, paint);

//                canvas.drawText(String.valueOf(mShowMaxValueText) + " →", value, max, paint);
            } else {
                float sub = 0;
                if (currMinPosition == mAdapter.getCount() - 1) {
                    sub = mPointWidth + 15;
                }
                float x = mWidth - 1 - (mStopIndex - currMinPosition) * mPointWidth * mScaleX - lineWidth / 2;
//                - getTextWidth(paint, mShowMinValueText + " →") - mPointWidth * mScaleX * 0.4f - sub
                if (!isVerticalScreen && mScrollX < 0 && mStopIndex == mAdapter.getCount() - 1) {
//                横屏
                    x -= Math.abs(mScrollX) * mScaleX / 2;
                }

                x -= lineWidth;
                canvas.drawLine(x, minY, x + lineWidth, minY, paintBg);

                x -= (paint.measureText(minValue) + 15);
                canvas.drawRect(x, minY - textHeight / 2 - 10, x + paint.measureText(minValue) + 15, minY + textHeight / 2 + 10, paintBg);

                canvas.drawText(minValue, x + 8, minY + textHeight / 2, paint);

//                canvas.drawText(String.valueOf(mShowMinValueText) + " →", value, min + 10, paint);
            }
        }
    }


    /**
     * 画文字
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
//        mTextPaint.setTextSize(20);
        //
//
//        这里这里
//
//
//
//
        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        float textHeight = fm.descent - fm.ascent;
        float baseLine = (textHeight - fm.bottom - fm.top) / 2;
        //绘制位置的比例
        float proportion = 0.13f;

        if (!isVerticalScreen) {
            proportion = 0.07f;
        }

        //纵轴值
        float coordinateValue = mWidth - (mWidth * proportion);
        float coordinateValueX = mWidth - (mWidth * proportion);


        int color = mTextPaint.getColor();

        //绘制右侧纵轴显示部分
        canvas.drawRect(mWidth, 0, mWidth, mChildRect.bottom, mBackgroundPaint);

        mTextPaint.setColor(Utils.getResourceColor(getContext(), R.color.b666666_99ffffff));

        //绘制分隔细线
//        canvas.drawRect(mWidth, 0, mWidth + 1, mChildRect.bottom, mTextPaint);

        //绘制主区域于副区域的分割线
//        canvas.drawRect(0, mMainRect.bottom, mWidth, mMainRect.bottom + 1, mTextPaint);
//        mTextPaint.setColor(colors);

//        mTextPaint.setTextSize(28);
//
//
//        这里这里
//
//
//
//
        //--------------画上方k线图的值-------------
        if (mMainDraw != null) {
//            TransformationUtils.getStringFormat((double) mMainMaxValue, isBtc)
            canvas.drawText(Utils.getScientificCountingMethodAfterData(Double.valueOf(mMainMaxValue), showScale), coordinateValue, baseLine + mMainRect.top, mTextPaint);
//            canvas.drawText(TransformationUtils.getStringFormat((double) mMainMinValue, isBtc), coordinateValue, mMainRect.bottom - textHeight + baseLine - 8, mTextPaint);

            float rowValue = (mMainMaxValue - mMainMinValue) / mGridRows;
            float rowSpace = mMainRect.height() / mGridRows;
            for (int i = 1; i < mGridRows; i++) {
                String text = Utils.getScientificCountingMethodAfterData(Double.valueOf(rowValue * (mGridRows - i) + mMainMinValue), showScale);
//                TransformationUtils.getStringFormat((double) rowValue * (mGridRows - i) + mMainMinValue, isBtc);

                canvas.drawText(text, coordinateValue, fixTextY(rowSpace * i + mMainRect.top), mTextPaint);
            }
        }

        //--------------画下方子图的值-------------
        if (mChildDraw != null) {
            canvas.drawText(formatValue(mChildMaxValue), coordinateValue, mChildRect.top + 33, mTextPaint);
            canvas.drawText(formatValue(mChildMinValue), coordinateValue, mChildRect.bottom - 8, mTextPaint);
        }

        //--------------画当前值 ← -------------------
//        mTextPaint.setColor(Color.parseColor("#ffa500"));
//        if (!TextUtils.isEmpty(currPrice)) {
//            if (Float.parseFloat(currPrice) > mShowMaxValueText) {
//                //当前值大于最大值
//                canvas.drawText("←" + currPrice, mWidth - (mWidth * proportion), mMainRect.top, mTextPaint);
//            } else if (Float.parseFloat(currPrice) < mShowMinValueText) {
//                //当前值小于最小值
//                canvas.drawText("←" + currPrice, mWidth - (mWidth * proportion), mMainRect.bottom, mTextPaint);
//            } else {
//                canvas.drawText("←" + currPrice, mWidth - (mWidth * proportion), getMainCandleY(Float.parseFloat(currPrice)), mTextPaint);
//            }
//        }

        mTextPaint.setColor(Utils.getResourceColor(getContext(), R.color.b666666_99ffffff));
//        if (lightType == 1) {
//            mTextPaint.setColor(Color.parseColor("#909499"));
//        } else {
//            mTextPaint.setColor(Color.parseColor("#656565"));
//        }


        //--------------画时间---------------------
        float columnSpace = mWidth / mGridColumns;
        float y = mainHeight + centerHeight / 2;

        float startX = getX(mStartIndex) - mPointWidth / 2;
        float stopX = getX(mStopIndex) + mPointWidth / 2;

        float timeTextSize = mTextPaint.getTextSize();

        for (int i = 1; i < mGridColumns; i++) {
            float translateX = xToTranslateX(columnSpace * i);
            if (translateX >= startX && translateX <= stopX) {
                int index = indexOfTranslateX(translateX);
                String text = formatDateTime(mAdapter.getDate(index));
                canvas.drawText(text, columnSpace * i - mTextPaint.measureText(text) / 2, y + 5, mTextPaint);
            }
        }

        mTextPaint.setColor(color);

        float translateX = xToTranslateX(0);
        if (translateX >= startX && translateX <= stopX) {
            canvas.drawText(formatDateTime(getAdapter().getDate(mStartIndex)), 0, y + 5, mTextPaint);
        }
        translateX = xToTranslateX(mWidth);
        if (translateX >= startX && translateX <= stopX) {
            String text = formatDateTime(getAdapter().getDate(mStopIndex));
            canvas.drawText(text, mWidth - mTextPaint.measureText(text), y + 5, mTextPaint);
        }
        mTextPaint.setTextSize(timeTextSize);

        //点击后显示值
        if (isLongPress) {
            Paint framePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            framePaint.setColor(mTextPaint.getColor());
            framePaint.setStrokeWidth(2);
            framePaint.setStyle(Paint.Style.STROKE);

            KLineImpl point = (KLineImpl) getItem(mSelectedIndex);
            //显示的值
//            String text = TransformationUtils.getStringFormat((double) point.getClosePrice(), isBtc);
            //时间
//            String time = DateUtil.longTimeFormat.format(mAdapter.getDate(mSelectedIndex));
            //时间宽度
//            float timeWidth = mTextPaint.measureText(time);

            //时间在横坐标位置
//            float timeX = mWidth * separationCoefficient - (getX(mStopIndex) * mScaleX - getX(mSelectedIndex) * mScaleX);

//            timeX -= timeWidth / 2;


//            if (getX(mStartIndex) * mScaleX == 0) {
//                timeX -= 212 / mScaleX;
//            } else {
//                timeX -= mWidth * separationCoefficient - (getX(mStopIndex) * mScaleX - getX(mStartIndex) * mScaleX);
//            }

//            float timeX = mWidth - (mStopIndex - mSelectedIndex) * mPointWidth * mScaleX;
//            timeX -= timeWidth / 2 + 44;
//
//            float r = textHeight / 2;
//            float v3 = 0;
//
//            if (!isVerticalScreen && mScrollX < 0 && mStopIndex == mAdapter.getCount() - 1) {
////                横屏
//                timeX -= Math.abs(mScrollX) * mScaleX / 2;
//            }
//
//
//            y = getMainCandleY(point.getClosePrice());
//
//            float x = mWidth - mTextPaint.measureText(text);
//            //横坐标位置
//            canvas.drawRect(coordinateValueX, y - r, mWidth, y + r, mBackgroundPaint);
//            canvas.drawRect(coordinateValueX, y - r, mWidth, y + r, framePaint);
//            canvas.drawText(text, coordinateValueX + (mWidth - coordinateValueX) * 0.2f, fixTextY(y), mTextPaint);
//
//
//            canvas.drawRect(timeX - 5, mChildRect.bottom + baseLine - r - 5, timeX + timeWidth + 72, mChildRect.bottom + baseLine + 10, framePaint);
//            canvas.drawRect(timeX - 5, mChildRect.bottom + baseLine - r - 5, timeX + timeWidth + 72, mChildRect.bottom + baseLine + 10, mBackgroundPaint);
//
//            float textSize = mTextPaint.getTextSize();
//            mTextPaint.setTextSize(28);
//            canvas.drawText(time, timeX, mChildRect.bottom + baseLine + 6, mTextPaint);
//            mTextPaint.setTextSize(textSize);


//            KLineImpl point = (KLineImpl) getItem(mSelectedIndex);

            String time = DateUtil.longTimeFormat.format(mAdapter.getDate(mSelectedIndex));
            String openPrice = Utils.getResourceString(R.string.kai) + ":" + point.getOpenPrice();
            String closePrice = Utils.getResourceString(R.string.shou) + ":" + point.getClosePrice();
            String heightPrice = Utils.getResourceString(R.string.gao) + ":" + point.getHighPrice();
            String lowPrice = Utils.getResourceString(R.string.di) + ":" + point.getLowPrice();


            float x;
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.parseColor("#66000000"));

            Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            textPaint.setColor(Color.parseColor("#ffffff"));
            textPaint.setTextSize(26f);


            float width = textPaint.measureText(time) + 50;
            float height = 320;
            y = mMainRect.top;
            if (translateXtoX(getX(mSelectedIndex)) > getChartWidth() / 2) {
                x = 0;
                canvas.drawRoundRect(new RectF(x, y, x + width, y + height), 8, 8, paint);
            } else {
                x = mWidth - width;
                canvas.drawRect(x, y, mWidth, y + height, paint);
            }
            y = y + 40;
            x = x + 15;
            canvas.drawText(time, x, y, textPaint);
            y += 55;
            canvas.drawText(openPrice, x, y, textPaint);
            y += 55;
            canvas.drawText(closePrice, x, y, textPaint);
            y += 55;
            canvas.drawText(heightPrice, x, y, textPaint);
            y += 55;
            canvas.drawText(lowPrice, x, y, textPaint);

        }
    }

    /**
     * 获取字体宽度
     *
     * @param paint
     * @param str
     * @return
     */
    public static int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }

    /**
     * 画值
     *
     * @param canvas
     * @param position 显示某个点的值
     */
    private void drawValue(Canvas canvas, int position) {
        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        float textHeight = fm.descent - fm.ascent;
        float baseLine = (textHeight - fm.bottom - fm.top) / 2;

        if (position >= 0 && position < mItemCount) {
            if (mMainDraw != null) {
                float y = mMainRect.top + 24;
                float x = 0;
                mMainLineDraw.drawText(canvas, this, position, x, y);
            }
            if (mChildDraw != null) {
                float y = mChildRect.top + baseLine;
                float x = 0;
                mChildDraw.drawText(canvas, this, position, x, y);
            }
        }
    }

    public int dp2px(float dp) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public int sp2px(float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 格式化值
     */
    public String formatValue(float value) {
        if (getValueFormatter() == null) {
            setValueFormatter(new ValueFormatter());
        }
        return getValueFormatter().format(value);
    }

    /**
     * 重新计算并刷新线条
     */
    public void notifyChanged() {
        if (mItemCount != 0) {
            mDataLen = (mItemCount - 1) * mPointWidth;
            checkAndFixScrollX();
            setTranslateXFromScrollX(mScrollX);
        } else {
            setScrollX(0);
        }
        invalidate();
    }

    private void calculateSelectedX(float x) {
        mSelectedIndex = indexOfTranslateX(xToTranslateX(x));
        if (mSelectedIndex < mStartIndex) {
            mSelectedIndex = mStartIndex;
        }
        if (mSelectedIndex > mStopIndex) {
            mSelectedIndex = mStopIndex;
        }
    }

    @Override
    public void onLongPress(MotionEvent e) {
        super.onLongPress(e);
        int lastIndex = mSelectedIndex;
        calculateSelectedX(e.getX());
        if (lastIndex != mSelectedIndex) {
            onSelectedChanged(this, getItem(mSelectedIndex), mSelectedIndex);
        }
        invalidate();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        setTranslateXFromScrollX(mScrollX);
    }

    @Override
    protected void onScaleChanged(float scale, float oldScale) {
        checkAndFixScrollX();
        setTranslateXFromScrollX(mScrollX);
        super.onScaleChanged(scale, oldScale);
    }


    private float mShowMaxValueText;
    private float mShowMinValueText;

    /**
     * 计算当前的显示区域
     */
    private void calculateValue() {
        if (!isLongPress()) {
            mSelectedIndex = -1;
        }
        mMainMaxValue = Float.MIN_VALUE;
        mMainMinValue = Float.MAX_VALUE;
        mChildMaxValue = Float.MIN_VALUE;
        mChildMinValue = Float.MAX_VALUE;

        mStartIndex = indexOfTranslateX(xToTranslateX(0));
        mStopIndex = indexOfTranslateX(xToTranslateX(mWidth));

        mShowMaxValueText = Float.MIN_VALUE;
        mShowMinValueText = Float.MAX_VALUE;

        for (int i = mStartIndex; i <= mStopIndex; i++) {

            KLineImpl point = (KLineImpl) getItem(i);

            //主视图显示范围计算
            if (mMainDraw != null) {

                if (mShowMaxValueText != mMainMaxValue || mShowMaxValueText != mMainDraw.getMaxValue(point)) {
                    isDrawMax = true;
                }

                if (mShowMinValueText != mMainMinValue || mShowMinValueText != mMainDraw.getMinValue(point)) {
                    isDrawMin = true;
                }

                mShowMaxValueText = Math.max(mShowMaxValueText, mMainDraw.getMaxValue(point));
                mShowMinValueText = Math.min(mShowMinValueText, mMainDraw.getMinValue(point));

                mMainMaxValue = Math.max(mMainMaxValue, mMainDraw.getMaxValue(point));
                mMainMinValue = Math.min(mMainMinValue, mMainDraw.getMinValue(point));

                mMainMaxValue = Math.max(mMainMaxValue, mMainLineDraw.getMaxValue(point));
                mMainMinValue = Math.min(mMainMinValue, mMainLineDraw.getMinValue(point));
            }

            //副视图显示范围计算
            if (mChildDraw != null) {
                mChildMaxValue = Math.max(mChildMaxValue, mChildDraw.getMaxValue(point));
                mChildMinValue = Math.min(mChildMinValue, mChildDraw.getMinValue(point));

                if (mChildDrawPosition == 4) {
                    //当前是量,计算出最大值与最小值
//                    List<Float> volumeMinAndMax = getVolumeMinAndMax();
                    VolumeDraw volumeDraw = (VolumeDraw) mChildDraw;
                    volumeDraw.setVolumeMin(Math.min(volumeDraw.getmVolumeMin(), point.getVolume()));
                    volumeDraw.setVolumeMax(Math.max(volumeDraw.getmVolumeMax(), point.getVolume()));
                }

            }

        }

        float padding = (mMainMaxValue - mMainMinValue) * 0.05f;
        mMainMaxValue += padding;
        mMainMinValue -= padding;
        mMainScaleY = mMainRect.height() * 1f / (mMainMaxValue - mMainMinValue) - 400;
//        mMainScaleY = mMainRect.height() * 1f / (mMainMaxValue - mMainMinValue);
        mChildScaleY = mChildRect.height() * 1f / (mChildMaxValue - mChildMinValue);
        if (mAnimator.isRunning()) {
            float value = (float) mAnimator.getAnimatedValue();
            mStopIndex = mStartIndex + Math.round(value * (mStopIndex - mStartIndex));
        }
    }

    /**
     * 获得量的最小值
     */
    private List<Float> getVolumeMinAndMax() {
        float min = Float.MAX_VALUE;
        float max = Float.MIN_VALUE;
        for (int i = mStartIndex; i < mStopIndex; i++) {
            KLineImpl item = (KLineImpl) getItem(i);
            if (item.getVolume() < min) {
                min = item.getVolume();
            }

            if (item.getVolume() > max) {
                max = item.getVolume();
            }

        }
        List<Float> floats = new ArrayList<>();
        floats.add(min);
        floats.add(max);
        return floats;
    }

    /**
     * 获取平移的最小值
     *
     * @return
     */
    private float getMinTranslateX() {
        return -mDataLen + mWidth / mScaleX - mPointWidth / 2;
    }

    /**
     * 获取平移的最大值
     *
     * @return
     */
    private float getMaxTranslateX() {
        if (!isFullScreen()) {
            return getMinTranslateX();
        }
        return mPointWidth / 2;
    }

    @Override
    public int getMinScrollX() {
        return (int) -(mOverScrollRange / mScaleX);
    }

    public int getMaxScrollX() {
        return Math.round(getMaxTranslateX() - getMinTranslateX());
    }

    public int indexOfTranslateX(float translateX) {
        return indexOfTranslateX(translateX, 0, mItemCount - 1);
    }

    /**
     * 在主区域画线
     *
     * @param startX    开始点的横坐标
     * @param stopX     开始点的值
     * @param stopX     结束点的横坐标
     * @param stopValue 结束点的值
     */
    public void drawMainLine(Canvas canvas, Paint paint, float startX, float startValue, float stopX, float stopValue) {

        canvas.drawLine(startX, getMainCandleY(startValue), stopX, getMainCandleY(stopValue), paint);
    }

    /**
     * 在子区域画线
     *
     * @param startX     开始点的横坐标
     * @param startValue 开始点的值
     * @param stopX      结束点的横坐标
     * @param stopValue  结束点的值
     */
    public void drawChildLine(Canvas canvas, Paint paint, float startX, float startValue, float stopX, float stopValue) {
        canvas.drawLine(startX, getChildY(startValue), stopX, getChildY(stopValue), paint);
    }

    /**
     * 根据索引获取实体
     *
     * @param position 索引值
     * @return
     */
    public Object getItem(int position) {
        if (mAdapter != null) {
            if (position < mAdapter.getCount()) {
                return mAdapter.getItem(position);
            } else {
                return mAdapter.getItem(mAdapter.getCount() - 1);
            }
        } else {
            return null;
        }
    }

    /**
     * 根据索引索取x坐标
     *
     * @param position 索引值
     * @return
     */
    public float getX(int position) {
        return position * mPointWidth;
    }

    /**
     * 获取适配器
     *
     * @return
     */
    public IAdapter getAdapter() {
        return mAdapter;
    }

    /**
     * 主视图选中的索引
     */
    private int mainViewPosition = 1;
    /**
     * 副图选中的索引
     */
    private int childViewPosition = 4;

    /**
     * 切换线
     * Ma   :1
     * EMa  :2
     * Boll :3
     * <p>
     * 量   :4
     * MACD :5
     * KDJ  :6
     *
     * @param position
     */
    public void setChildDraw(int position) {
        switch (position) {
            case 1:
                //ma
                this.mMainLineDraw = mMainDraws.get(position - 1);
                mainViewPosition = 1;
                break;
            case 2:
                //EMa
                this.mMainLineDraw = mMainDraws.get(position - 1);
                mainViewPosition = 2;
                break;
            case 3:
                //BOll
                this.mMainLineDraw = mMainDraws.get(position - 1);
                mainViewPosition = 3;
                break;
            case 4:
                //量
                this.mChildDraw = mChildDraws.get(position - 4);
                childViewPosition = 4;
                break;
            case 5:
                //MACD
                this.mChildDraw = mChildDraws.get(position - 4);
                childViewPosition = 5;
                break;
            case 6:
                //KDJ
                this.mChildDraw = mChildDraws.get(position - 4);
                childViewPosition = 6;
                break;
            case 7:
                //RSI
                this.mChildDraw = mChildDraws.get(position - 4);
                childViewPosition = 7;
                break;
        }
        mChildDrawPosition = position;
        invalidate();
    }

    /**
     * 给子区域添加画图方法
     *
     * @param name      显示的文字标签
     * @param childDraw IChartDraw
     */
    public void addChildDraw(String name, IChartDraw childDraw, boolean isMain) {
        if (isMain) {
            mMainDraws.add(childDraw);
        } else {
            mChildDraws.add(childDraw);
        }
        mKChartTabView.addTab(name);
    }

    /**
     * scrollX 转换为 TranslateX
     *
     * @param scrollX
     */
    private void setTranslateXFromScrollX(int scrollX) {
        mTranslateX = scrollX + getMinTranslateX();
    }

    /**
     * 获取ValueFormatter
     *
     * @return
     */
    public IValueFormatter getValueFormatter() {
        return mValueFormatter;
    }

    /**
     * 设置ValueFormatter
     *
     * @param valueFormatter value格式化器
     */
    public void setValueFormatter(IValueFormatter valueFormatter) {
        this.mValueFormatter = valueFormatter;
    }

    /**
     * 获取DatetimeFormatter
     *
     * @return 时间格式化器
     */
    public IDateTimeFormatter getDateTimeFormatter() {
        return mDateTimeFormatter;
    }

    /**
     * 设置dateTimeFormatter
     *
     * @param dateTimeFormatter 时间格式化器
     */
    public void setDateTimeFormatter(IDateTimeFormatter dateTimeFormatter) {
        mDateTimeFormatter = dateTimeFormatter;
    }

    /**
     * 格式化时间
     *
     * @param date
     */
    public String formatDateTime(Date date) {
        if (getDateTimeFormatter() == null) {
            setDateTimeFormatter(new TimeFormatter());
        }
        return getDateTimeFormatter().format(date);
    }

    /**
     * 获取主区域的 IChartDraw
     *
     * @return IChartDraw
     */
    public IChartDraw getMainDraw() {
        return mMainDraw;
    }

    /**
     * 设置主区域的 IChartDraw
     *
     * @param mainDraw IChartDraw
     */
    public void setMainDraw(IChartDraw mainDraw) {
        mMainDraw = mainDraw;
    }

    /**
     * 二分查找当前值的index
     *
     * @return
     */
    public int indexOfTranslateX(float translateX, int start, int end) {
        if (end == start) {
            return start;
        }
        if (end - start == 1) {
            float startValue = getX(start);
            float endValue = getX(end);
            return Math.abs(translateX - startValue) < Math.abs(translateX - endValue) ? start : end;
        }
        int mid = start + (end - start) / 2;
        float midValue = getX(mid);
        if (translateX < midValue) {
            return indexOfTranslateX(translateX, start, mid);
        } else if (translateX > midValue) {
            return indexOfTranslateX(translateX, mid, end);
        } else {
            return mid;
        }
    }

    /**
     * 设置数据适配器
     */
    public void setAdapter(IAdapter adapter) {
        if (mAdapter != null && mDataSetObserver != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
        }
        mAdapter = adapter;
        if (mAdapter != null) {
            mAdapter.registerDataSetObserver(mDataSetObserver);
            mItemCount = mAdapter.getCount();
        } else {
            mItemCount = 0;
        }
        notifyChanged();
    }

    /**
     * 开始动画
     */
    public void startAnimation() {
        if (mAnimator != null) {
            mAnimator.start();
        }
    }

    /**
     * 设置动画时间
     */
    public void setAnimationDuration(long duration) {
        if (mAnimator != null) {
            mAnimator.setDuration(duration);
        }
    }

    /**
     * 设置表格行数
     */
    public void setGridRows(int gridRows) {
        if (gridRows < 1) {
            gridRows = 1;
        }
        mGridRows = gridRows;
    }

    /**
     * 设置表格列数
     */
    public void setGridColumns(int gridColumns) {
        if (gridColumns < 1) {
            gridColumns = 1;
        }
        mGridColumns = gridColumns;
    }

    /**
     * view中的x转化为TranslateX
     *
     * @param x
     * @return
     */
    public float xToTranslateX(float x) {
        return -mTranslateX + x / mScaleX;
    }

    /**
     * translateX转化为view中的x
     *
     * @param translateX
     * @return
     */
    public float translateXtoX(float translateX) {
        return (translateX + mTranslateX) * mScaleX;
    }

    /**
     * 获取上方padding
     */
    public float getTopPadding() {
        return mTopPadding;
    }

    /**
     * 获取图的宽度
     *
     * @return
     */
    public int getChartWidth() {
        return mWidth;
    }

    /**
     * 是否长按
     */
    public boolean isLongPress() {
        return isLongPress;
    }

    /**
     * 获取选择索引
     */
    public int getSelectedIndex() {
        return mSelectedIndex;
    }

    /**
     * 设置选择监听
     */
    public void setOnSelectedChangedListener(OnSelectedChangedListener l) {
        this.mOnSelectedChangedListener = l;
    }

    public void onSelectedChanged(BaseKChartView view, Object point, int index) {
        if (this.mOnSelectedChangedListener != null) {
            mOnSelectedChangedListener.onSelectedChanged(view, point, index);
        }
    }

    /**
     * 数据是否充满屏幕
     *
     * @return
     */
    public boolean isFullScreen() {
        return mDataLen >= mWidth / mScaleX;
    }

    /**
     * 设置超出右方后可滑动的范围
     */
    public void setOverScrollRange(float overScrollRange) {
        if (overScrollRange < 0) {
            overScrollRange = 0;
        }
        mOverScrollRange = overScrollRange;
    }

    /**
     * 设置上方padding
     *
     * @param topPadding
     */
    public void setTopPadding(int topPadding) {
        mTopPadding = topPadding;
    }

    /**
     * 设置下方padding
     *
     * @param bottomPadding
     */
    public void setBottomPadding(int bottomPadding) {
        mBottomPadding = bottomPadding;
    }

    /**
     * 设置表格线宽度
     */
    public void setGridLineWidth(float width) {
        mGridPaint.setStrokeWidth(width);
    }

    /**
     * 设置表格线颜色
     */
    public void setGridLineColor(int color) {
        mGridPaint.setColor(color);
    }

    /**
     * 设置选择线宽度
     */
    public void setSelectedLineWidth(float width) {
        mSelectedLinePaint.setStrokeWidth(width);
    }

    /**
     * 设置表格线颜色
     */
    public void setSelectedLineColor(int color) {
        mSelectedLinePaint.setColor(color);
    }

    /**
     * 设置文字颜色
     */
    public void setTextColor(int color) {
        mTextPaint.setColor(color);
    }

    /**
     * 设置文字大小
     */
    public void setTextSize(float textSize) {
        mTextPaint.setTextSize(textSize);
    }

    /**
     * 设置背景颜色
     */
    public void setBackgroundColor(int color) {
        mBackgroundPaint.setColor(color);
    }

    public void setLightType(int type) {
        this.lightType = type;
        MainDraw m = (MainDraw) mMainDraw;
        m.setLightType(lightType);
        if (lightType == 1) {
            //暗
            mTextPaint.setColor(Color.parseColor("#1c2129"));
        } else {
            //亮
            mTextPaint.setColor(Color.parseColor("#656565"));
        }
    }

    public void refresh() {
        onScrollChanged(mScrollX, 0, oldX, 0);
    }


    public void setScale(int scale) {
        this.showScale = scale;
    }
//
//    public void currency(String currency) {
//        if (!TextUtils.isEmpty(currency) && currency.endsWith("gzh")) {
//            isBtc = true;
//        } else {
//            isBtc = false;
//        }
//    }

    /**
     * 设置移动到最右边
     */
    public void scrollRight(int x) {
        scrollTo(x, 0);
    }


    /**
     * 选中点变化时的监听
     */
    public interface OnSelectedChangedListener {
        /**
         * 当选点中变化时
         *
         * @param view  当前view
         * @param point 选中的点
         * @param index 选中点的索引
         */
        void onSelectedChanged(BaseKChartView view, Object point, int index);

    }

    /**
     * 获取文字大小
     */
    public float getTextSize() {
        return mTextPaint.getTextSize();
    }

    /**
     * 获取曲线宽度
     */
    public float getLineWidth() {
        return mLineWidth;
    }

    /**
     * 设置曲线的宽度
     */
    public void setLineWidth(float lineWidth) {
        mLineWidth = lineWidth;
    }

    /**
     * 设置每个点的宽度
     */
    public void setPointWidth(float pointWidth) {
        mPointWidth = pointWidth;
    }

    public int getmChildHeight() {
        return mChildHeight;
    }


}
