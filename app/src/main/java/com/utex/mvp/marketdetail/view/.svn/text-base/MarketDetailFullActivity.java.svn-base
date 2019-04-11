package com.utex.mvp.marketdetail.view;

import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.utex.R;
import com.utex.base.BaseActivity;
import com.utex.base.BaseFragment;
import com.utex.bean.TickerDo;
import com.utex.common.Constants;
import com.utex.common.FiledConstants;
import com.utex.core.AppComponent;
import com.utex.core.UTEXApplication;
import com.utex.db.ExTickerDao;
import com.utex.mvp.home.bean.TickerData;
import com.utex.mvp.marketdetail.bean.KLineEntity;
import com.utex.mvp.marketdetail.bean.MiTabParamVo;
import com.utex.mvp.marketdetail.dagger2.DaggerMarketDetailFullComponent;
import com.utex.mvp.marketdetail.dagger2.MarketDetailFullModule;
import com.utex.mvp.marketdetail.presenter.IMarketDetailFullPresenter;
import com.utex.recevier.TickerReceiver;
import com.utex.utils.MarketSocketPackUtils;
import com.utex.utils.SharedPreferencesUtil;
import com.utex.utils.Utils;
import com.utex.widget.klinechart.chart.KChartView;
import com.utex.widget.klinechart.chart.formatter.TimeFormatter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 全屏
 */
public class MarketDetailFullActivity extends BaseActivity implements IMarketDetailFullView, TickerReceiver.TickerListener {

    @Inject
    IMarketDetailFullPresenter iMarketDetailFullPresenter;

    @BindView(R.id.mi_market_detail_tab_time_title)
    MagicIndicator miMarketDetailTabTimeTitle;

    @BindView(R.id.vp_market_detail_time_page)
    ViewPager vpMarketDetailTimePage;

    @BindView(R.id.kchart_view)
    KChartView kChartView;

    @BindView(R.id.tv_market_detail_full_title)
    TextView tvMarketDetailFullTitle;

    @BindView(R.id.tv_market_detail_full_price)
    TextView tvMarketDetailFullPrice;

    @BindView(R.id.tv_market_detail_full_rise)
    TextView tvMarketDetailFullRise;

    @BindView(R.id.tv_market_detail_full_plaftform_price)
    TextView tvMarketDetailFullPlatFormPrice;

    @BindView(R.id.tv_market_detail_full_gao)
    TextView tvMarketDetailFullGao;

    @BindView(R.id.tv_market_detail_full_di)
    TextView tvMarketDetailFullDi;

    @BindView(R.id.tv_market_detail_full_hour)
    TextView tvMarketDetailFullHour;

    @BindView(R.id.rv_market_detail_full_index)
    RecyclerView rvMarketDetailFullIndex;

    private TickerDo currTicker;

    private TickerReceiver tickerReceiver;

    private Handler handler = new Handler((Handler.Callback)
            this::handleMessage);

    private KChartAdapter mAdapter;

    private MiTabParamVo currTime;
    private List<KLineEntity> tickerDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == 1) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        setContentView(R.layout.activity_market_detail_full);
        ButterKnife.bind(this);

        tickerReceiver = new TickerReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.ticker_detail");
        registerReceiver(tickerReceiver, intentFilter);
        tickerReceiver.setTickerListener(MarketDetailFullActivity.this);

        String coinCode = getIntent().getStringExtra(Constants.COIN_CODE);
        currTicker = ExTickerDao.getTickerByCode(coinCode);
        tvMarketDetailFullTitle.setText(currTicker.getCoin_market_code().replace("_", "/").toUpperCase());

        init();
        initKline();

    }

    private void initKline() {
        mAdapter = new KChartAdapter();
        kChartView.setAdapter(mAdapter);
        kChartView.setDateTimeFormatter(new TimeFormatter());
        kChartView.setGridRows(6);
        kChartView.setGridColumns(7);
        kChartView.setScale(Utils.getTickerShowScale(currTicker));
        kChartView.showLoading();
        kChartView.setisVerticalScreen(false);
    }

    private void init() {
        //获取sp时间若存在则代表是不显示
        iMarketDetailFullPresenter.setTime();
        iMarketDetailFullPresenter.setIndex();
        handler.sendEmptyMessageDelayed(0, 100);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMarketDetailFullComponent
                .builder()
                .appComponent(appComponent)
                .marketDetailFullModule(new MarketDetailFullModule(this))
                .build()
                .inject(this);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        return super.onKeyUp(keyCode, event);
    }

    @OnClick({R.id.iv_market_detail_full_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_market_detail_full_close:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        try {
            if (tickerReceiver != null) {
                unregisterReceiver(tickerReceiver);
            }


        } catch (Exception e) {
            Log.e("TAG", e.getMessage());
        }

        super.onDestroy();
    }

    @Override
    public void setTime(final List<MiTabParamVo> times, String hideTimes) {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        TitleAdapter titleAdapter = new TitleAdapter(times, vpMarketDetailTimePage);
        commonNavigator.setAdapter(titleAdapter);
        miMarketDetailTabTimeTitle.setNavigator(commonNavigator);
        ViewPagerHelper.bind(miMarketDetailTabTimeTitle, vpMarketDetailTimePage);

        titleAdapter.setTitleAdapterListener(index -> {
            //去请求新的数据 获取相应的K线
            currTime = times.get(index);
            handler.sendEmptyMessageDelayed(1, 100);
            handler.sendEmptyMessageDelayed(5, 100);

        });

        List<BaseFragment> fragments = new ArrayList<>();
        for (MiTabParamVo a : times) {
            fragments.add(new PageFragment());
        }

        vpMarketDetailTimePage.setAdapter(new MarketDetailPageAdapter(getSupportFragmentManager(), fragments));
        vpMarketDetailTimePage.setCurrentItem(4);
        currTime = times.get(4);
        handler.sendEmptyMessageDelayed(1, 100);
        handler.sendEmptyMessageDelayed(5, 100);
    }

    @Override
    public void setData() {
        TickerData resultBean = currTicker.getResultBean();
        tvMarketDetailFullPrice.setText(resultBean.getLast());

        int showScale = Utils.getTickerShowScale(currTicker);

        double v = (Double.parseDouble(resultBean.getLast()) - Double.parseDouble(resultBean.getOpen())) / Double.parseDouble(resultBean.getOpen());
        DecimalFormat df = new DecimalFormat("######0.00");

        tvMarketDetailFullRise.setText(df.format(v * 100) + "%");
        tvMarketDetailFullPrice.setText(Utils.getScientificCountingMethodAfterData(Double.valueOf(resultBean.getLast()), showScale));
        tvMarketDetailFullHour.setText("24H " + Utils.getScientificCountingMethodAfterData(Double.valueOf(resultBean.getVolume()), showScale));
        tvMarketDetailFullGao.setText(Utils.getResourceString(R.string.gao) + " " + Utils.getScientificCountingMethodAfterData(Double.valueOf(resultBean.getHigh()), showScale));
        tvMarketDetailFullDi.setText(Utils.getResourceString(R.string.di) + " " + Utils.getScientificCountingMethodAfterData(Double.valueOf(resultBean.getLow()), showScale));


        double rate = UTEXApplication.getRate(currTicker.getCoin_market_code().split("_")[1]);
        tvMarketDetailFullPlatFormPrice.setText("≈" + Utils.getScientificCountingMethodAfterData(Double.valueOf(resultBean.getLast()) * rate, showScale) + " " + SharedPreferencesUtil.getString(FiledConstants.PALTFORM_VALUAT, "USD"));
    }

    @Override
    public void setKLineData(List<KLineEntity> tickerDatas) {
        this.tickerDatas = tickerDatas;
        kChartView.scrollRight(-271);
        mAdapter.addFooterData(tickerDatas);
        kChartView.startAnimation();
        kChartView.refreshEnd();

        kChartView.switchLine(1);
        kChartView.switchLine(5);
    }

    @Override
    public void setIndexData(final List<MiTabParamVo> indexs) {

        rvMarketDetailFullIndex.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        MarketDetailFullINdexAdapter marketDetailFullINdexAdapter = new MarketDetailFullINdexAdapter(indexs);
        rvMarketDetailFullIndex.setAdapter(marketDetailFullINdexAdapter);
        marketDetailFullINdexAdapter.setMarketDetailFullINdexAdapterListener(new MarketDetailFullINdexAdapter.MarketDetailFullINdexAdapterListener() {
            @Override
            public void onClick(int position) {
                kChartView.switchLine(indexs.get(position).getNumber());
            }
        });
    }

    @Override
    public void updateKLine(boolean isHasNew) {

        if (isHasNew) {
            ArrayList<KLineEntity> kLineEntities = new ArrayList<>();
            kLineEntities.add(tickerDatas.get(tickerDatas.size() - 1));
            mAdapter.upDataFooterData(kLineEntities);
        }

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onReceive(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        Log.e("TAG", "websocket:" + json);
        String channel = jsonObject.getString("channel");
        String method = jsonObject.getString("method");
        int id = jsonObject.getIntValue("id");
        if ("user.order".equals(channel)) {
        } else if ("today.update".equals(method)) {
            //今
            JSONArray params = jsonObject.getJSONArray("params");
            if (params != null && params.size() > 0) {
                JSONObject object = params.getJSONObject(1);
                TickerData tickerData = JSON.parseObject(JSON.toJSONString(object), TickerData.class);
                currTicker.setResultBean(tickerData);
                iMarketDetailFullPresenter.setData();
            }
        } else if ("depth.update".equals(method)) {
        } else if ("deals.update".equals(method)) {
        } else if ("kline.update".equals(method)) {
            JSONArray result = jsonObject.getJSONArray("params");
            iMarketDetailFullPresenter.calculationKLineUpdate(result);
        } else if (MarketSocketPackUtils.KLINE_QUERY_ID == id) {
            JSONArray result = jsonObject.getJSONArray("result");
            iMarketDetailFullPresenter.calculationKLine(result);
        } else if (MarketSocketPackUtils.DEPTH_SUBSCRIBE_ID == id) {
        } else if (MarketSocketPackUtils.VOl_SUBSCRIBE_ID == id) {
        }
    }

    private boolean handleMessage(Message message) {
        if (currTicker == null) {
            return false;
        }
        switch (message.what) {
            case 0:
                //当前币种
                iMarketDetailFullPresenter.coinSubscribe(currTicker);
                break;
            case 1:
                if (currTime == null) {
                    return true;
                }
                iMarketDetailFullPresenter.clearKLineData();

                iMarketDetailFullPresenter.KLineQuery(currTicker, currTime);
                break;
            case 5:
                //K线订阅
                iMarketDetailFullPresenter.kLineSubscribe(currTicker, currTime);
                break;
        }

        return false;
    }
}
