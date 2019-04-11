package com.utex.widget.klinechart.chart;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.utex.R;
import com.utex.widget.klinechart.chart.draw.BOLLDraw;
import com.utex.widget.klinechart.chart.draw.EMADraw;
import com.utex.widget.klinechart.chart.draw.KDJDraw;
import com.utex.widget.klinechart.chart.draw.MACDDraw;
import com.utex.widget.klinechart.chart.draw.MADraw;
import com.utex.widget.klinechart.chart.draw.MainDraw;
import com.utex.widget.klinechart.chart.draw.RSIDraw;
import com.utex.widget.klinechart.chart.draw.VolumeDraw;


/**
 * k线图
 * Created by Demon on 2017/10/17.
 */
public class KChartView extends BaseKChartView {

    ProgressBar mProgressBar;
    private boolean isRefreshing = false;
    private boolean isLoadMoreEnd = false;
    private boolean mLastScrollEnable;
    private boolean mLastScaleEnable;

    private KChartRefreshListener mRefreshListener;

    private MACDDraw mMACDDraw;
    private BOLLDraw mBOLLDraw;
    private RSIDraw mRSIDraw;
    private MainDraw mMainDraw;
    private KDJDraw mKDJDraw;
    private MADraw mMADraw;
    private VolumeDraw mVolumeDraw;
    private EMADraw mEmaDraw;

    public KChartView(Context context) {
        this(context, null);
    }

    public KChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initAttrs(attrs);
    }

    private void initView() {
        mProgressBar = new ProgressBar(getContext());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(dp2px(50), dp2px(50));
        layoutParams.addRule(CENTER_IN_PARENT);
        addView(mProgressBar, layoutParams);
        mProgressBar.setVisibility(GONE);
        mMACDDraw = new MACDDraw(this);
        mKDJDraw = new KDJDraw(this);
        mRSIDraw = new RSIDraw(this);
        mBOLLDraw = new BOLLDraw(this);
        mMainDraw = new MainDraw(this);
        mMADraw = new MADraw(this);
        mVolumeDraw = new VolumeDraw(this);
        mEmaDraw = new EMADraw(this);

        //量
        addChildDraw("4", mVolumeDraw, false);
        //MACD
        addChildDraw("5", mMACDDraw, false);
        // KDJ
        addChildDraw("6", mKDJDraw, false);
        //Rsi
        addChildDraw("7", mRSIDraw, false);

        //MA
        addChildDraw("1", mMADraw, true);
        //EMA
        addChildDraw("2", mEmaDraw, true);
        //布林线
        addChildDraw("3", mBOLLDraw, true);

        setMainDraw(mMainDraw);
        setChildDraw(1);
        setChildDraw(5);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.KChartView);
        if (array != null) {
            try {
                //public
                setPointWidth(array.getDimension(R.styleable.KChartView_kc_point_width, getDimension(R.dimen.chart_point_width)));
                setTextSize(array.getDimension(R.styleable.KChartView_kc_text_size, getDimension(R.dimen.chart_text_size)));
                setTextColor(array.getColor(R.styleable.KChartView_kc_text_color, getColor(R.color.chart_text)));
                setLineWidth(array.getDimension(R.styleable.KChartView_kc_line_width, getDimension(R.dimen.chart_line_width)));
                setBackgroundColor(array.getColor(R.styleable.KChartView_kc_background_color, getColor(R.color.chart_background)));
                setSelectedLineColor(array.getColor(R.styleable.KChartView_kc_selected_line_color, getColor(R.color.chart_text)));
                setSelectedLineWidth(array.getDimension(R.styleable.KChartView_kc_selected_line_width, getDimension(R.dimen.chart_line_width)));
                setGridLineWidth(array.getDimension(R.styleable.KChartView_kc_grid_line_width, getDimension(R.dimen.chart_grid_line_width)));
                setGridLineColor(array.getColor(R.styleable.KChartView_kc_grid_line_color, getColor(R.color.chart_grid_line)));
                //macd
                setMACDWidth(array.getDimension(R.styleable.KChartView_kc_macd_width, getDimension(R.dimen.chart_candle_width)));
                setDIFColor(array.getColor(R.styleable.KChartView_kc_dif_color, getColor(R.color.bf2be44)));
                setDEAColor(array.getColor(R.styleable.KChartView_kc_dea_color, getColor(R.color.be775eb)));
                setMACDColor(array.getColor(R.styleable.KChartView_kc_macd_color, getColor(R.color.b00cc96)));
                //kdj
                setKColor(array.getColor(R.styleable.KChartView_kc_dif_color, getColor(R.color.b00cc96)));
                setDColor(array.getColor(R.styleable.KChartView_kc_dea_color, getColor(R.color.bf2be44)));
                setJColor(array.getColor(R.styleable.KChartView_kc_macd_color, getColor(R.color.be775eb)));
                //rsi
                setRSI1Color(array.getColor(R.styleable.KChartView_kc_dif_color, getColor(R.color.b00cc96)));
                setRSI2Color(array.getColor(R.styleable.KChartView_kc_dea_color, getColor(R.color.bf2be44)));
                setRSI3Color(array.getColor(R.styleable.KChartView_kc_macd_color, getColor(R.color.be775eb)));
                //boll
                setUpColor(array.getColor(R.styleable.KChartView_kc_dif_color, getColor(R.color.beb2813)));
                setMbColor(array.getColor(R.styleable.KChartView_kc_dea_color, getColor(R.color.b2d71ae)));
                setDnColor(array.getColor(R.styleable.KChartView_kc_macd_color, getColor(R.color.b227a22)));

                //main
                setMa5Color(array.getColor(R.styleable.KChartView_kc_dif_color, getColor(R.color.bbdb5a6)));
                setMa10Color(array.getColor(R.styleable.KChartView_kc_dea_color, getColor(R.color.be5871c)));

                setMa20Color(array.getColor(R.styleable.KChartView_kc_macd_color, getColor(R.color.chart_ma20)));

                setCandleWidth(array.getDimension(R.styleable.KChartView_kc_candle_width, getDimension(R.dimen.chart_candle_width)));
                setCandleLineWidth(array.getDimension(R.styleable.KChartView_kc_candle_line_width, getDimension(R.dimen.chart_candle_line_width)));
                setSelectorBackgroundColor(array.getColor(R.styleable.KChartView_kc_selector_background_color, getColor(R.color.chart_selector)));
                setSelectorTextSize(array.getDimension(R.styleable.KChartView_kc_selector_text_size, getDimension(R.dimen.chart_selector_text_size)));
                setCandleSolid(array.getBoolean(R.styleable.KChartView_kc_candle_solid, true));
                //tab
                mKChartTabView.setIndicatorColor(array.getColor(R.styleable.KChartView_kc_tab_indicator_color, getColor(R.color.chart_tab_indicator)));
                mKChartTabView.setBackgroundColor(array.getColor(R.styleable.KChartView_kc_tab_background_color, getColor(R.color.chart_tab_background)));
                ColorStateList colorStateList = array.getColorStateList(R.styleable.KChartView_kc_tab_text_color);
                if (colorStateList == null) {
                    mKChartTabView.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.tab_text_color_selector));
                } else {
                    mKChartTabView.setTextColor(colorStateList);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                array.recycle();
            }
        }
    }

    private float getDimension(@DimenRes int resId) {
        return getResources().getDimension(resId);
    }

    private int getColor(@ColorRes int resId) {
        return ContextCompat.getColor(getContext(), resId);
    }

    @Override
    public void onLeftSide() {
        showLoading();
    }

    @Override
    public void onRightSide() {
    }

    public void showLoading() {
        if (!isLoadMoreEnd && !isRefreshing) {
            isRefreshing = true;
            if (mProgressBar != null) {
                mProgressBar.setVisibility(View.VISIBLE);
            }
            if (mRefreshListener != null) {
                mRefreshListener.onLoadMoreBegin(this);
            }
            mLastScaleEnable = isScaleEnable();
            mLastScrollEnable = isScrollEnable();
            super.setScrollEnable(false);
            super.setScaleEnable(false);
        }
    }

    private void hideLoading() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }
        super.setScrollEnable(mLastScrollEnable);
        super.setScaleEnable(mLastScaleEnable);
    }

    /**
     * 刷新完成
     */
    public void refreshComplete() {
        isRefreshing = false;
        hideLoading();
    }

    /**
     * 刷新完成，没有数据
     */
    public void refreshEnd() {
        isLoadMoreEnd = true;
        isRefreshing = false;
        hideLoading();
    }

    /**
     * 重置加载更多
     */
    public void resetLoadMoreEnd() {
        isLoadMoreEnd = false;
    }

    /**
     * 设置当前价格
     *
     * @param price
     */
    public void setCurrPrice(String price) {
        this.currPrice = price;
        invalidate();
    }

    public String getCurrPrice() {
        return currPrice;
    }

    /**
     * 设置分时
     *
     * @param isMinutesHour
     */
    public void setMinuteshour(boolean isMinutesHour) {
        this.isMinutesHour = isMinutesHour;
    }

    /**
     * 设置是否竖屏
     *
     * @param isVerticalScreen
     */
    public void setisVerticalScreen(boolean isVerticalScreen) {
        this.isVerticalScreen = isVerticalScreen;
    }


    public interface KChartRefreshListener {
        /**
         * 加载更多
         *
         * @param chart
         */
        void onLoadMoreBegin(KChartView chart);
    }

    @Override
    public void setScaleEnable(boolean scaleEnable) {
        if (isRefreshing) {
            throw new IllegalStateException("请勿在刷新状态设置属性");
        }
        super.setScaleEnable(scaleEnable);

    }

    @Override
    public void setScrollEnable(boolean scrollEnable) {
        if (isRefreshing) {
            throw new IllegalStateException("请勿在刷新状态设置属性");
        }
        super.setScrollEnable(scrollEnable);
    }

    /**
     * 设置DIF颜色
     */
    public void setDIFColor(int color) {
        mMACDDraw.setDIFColor(color);
    }

    /**
     * 设置DEA颜色
     */
    public void setDEAColor(int color) {
        mMACDDraw.setDEAColor(color);
    }

    /**
     * 设置MACD颜色
     */
    public void setMACDColor(int color) {
        mMACDDraw.setMACDColor(color);
    }

    /**
     * 设置MACD的宽度
     *
     * @param MACDWidth
     */
    public void setMACDWidth(float MACDWidth) {
        mMACDDraw.setMACDWidth(MACDWidth);
        mVolumeDraw.setMACDWidth(MACDWidth);
    }

    /**
     * 设置up颜色
     */
    public void setUpColor(int color) {
        mBOLLDraw.setUpColor(color);
    }

    /**
     * 设置mb颜色
     *
     * @param color
     */
    public void setMbColor(int color) {
        mBOLLDraw.setMbColor(color);
    }

    /**
     * 设置dn颜色
     */
    public void setDnColor(int color) {
        mBOLLDraw.setDnColor(color);
    }

    /**
     * 设置K颜色
     */
    public void setKColor(int color) {
        mKDJDraw.setKColor(color);
    }

    /**
     * 设置D颜色
     */
    public void setDColor(int color) {
        mKDJDraw.setDColor(color);
    }

    /**
     * 设置J颜色
     */
    public void setJColor(int color) {
        mKDJDraw.setJColor(color);
    }

    /**
     * 设置ma5颜色
     *
     * @param color
     */
    public void setMa5Color(int color) {
        mMADraw.setMA7Color(color);
        mVolumeDraw.setMa7Color(color);
        mEmaDraw.setMa7Color(color);
    }

    /**
     * 设置ma10颜色
     *
     * @param color
     */
    public void setMa10Color(int color) {
        mMADraw.setMA30Color(color);
        mVolumeDraw.setMa30Color(color);
        mEmaDraw.setMa30Color(color);
    }

    /**
     * 设置ma20颜色
     *
     * @param color
     */
    public void setMa20Color(int color) {
        mMainDraw.setMa20Color(color);
    }

    /**
     * 设置选择器文字大小
     *
     * @param textSize
     */
    public void setSelectorTextSize(float textSize) {
        mMainDraw.setSelectorTextSize(textSize);
    }

    /**
     * 设置选择器背景
     *
     * @param color
     */
    public void setSelectorBackgroundColor(int color) {
        mMainDraw.setSelectorBackgroundColor(color);
    }

    /**
     * 设置蜡烛宽度
     *
     * @param candleWidth
     */
    public void setCandleWidth(float candleWidth) {
        mMainDraw.setCandleWidth(candleWidth);
    }

    /**
     * 设置蜡烛线宽度
     *
     * @param candleLineWidth
     */
    public void setCandleLineWidth(float candleLineWidth) {
        mMainDraw.setCandleLineWidth(candleLineWidth);
    }

    /**
     * 蜡烛是否空心
     */
    public void setCandleSolid(boolean candleSolid) {
        mMainDraw.setCandleSolid(candleSolid);
    }

    public void setRSI1Color(int color) {
        mRSIDraw.setRSI1Color(color);
    }

    public void setRSI2Color(int color) {
        mRSIDraw.setRSI2Color(color);
    }

    public void setRSI3Color(int color) {
        mRSIDraw.setRSI3Color(color);
    }

    @Override
    public void setTextSize(float textSize) {
        super.setTextSize(textSize);
        mMainDraw.setTextSize(textSize);
        mBOLLDraw.setTextSize(textSize);
        mRSIDraw.setTextSize(textSize);
        mMACDDraw.setTextSize(textSize);
        mKDJDraw.setTextSize(textSize);
        mVolumeDraw.setTextSize(textSize);
        mMADraw.setTextSize(textSize);
        mEmaDraw.setTextSize(textSize);
    }

    @Override
    public void setLineWidth(float lineWidth) {
        super.setLineWidth(lineWidth);
        mMainDraw.setLineWidth(lineWidth);
        mBOLLDraw.setLineWidth(lineWidth);
        mRSIDraw.setLineWidth(lineWidth);
        mMACDDraw.setLineWidth(lineWidth);
        mKDJDraw.setLineWidth(lineWidth);
        mVolumeDraw.setLineWidth(lineWidth);
        mMADraw.setLineWidth(lineWidth);
        mEmaDraw.setLineWidth(lineWidth);
    }

    @Override
    public void setTextColor(int color) {
        super.setTextColor(color);
        mMainDraw.setSelectorTextColor(color);
    }

    /**
     * 设置刷新监听
     */
    public void setRefreshListener(KChartRefreshListener refreshListener) {
        mRefreshListener = refreshListener;
    }

    /**
     * 切换线
     * Ma   :1
     * EMa  :2
     * Boll :3
     * 量   :4
     * MACD :5
     * KDJ  :6
     *
     * @param index
     */
    public void switchLine(long index) {
        setChildDraw((int) index);
    }


}
