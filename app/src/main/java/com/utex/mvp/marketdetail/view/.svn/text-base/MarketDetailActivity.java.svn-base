package com.utex.mvp.marketdetail.view;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.umeng.analytics.MobclickAgent;
import com.utex.R;
import com.utex.base.BaseActivity;
import com.utex.base.BaseFragment;
import com.utex.bean.TickerDo;
import com.utex.common.Constants;
import com.utex.common.FiledConstants;
import com.utex.common.UMConstants;
import com.utex.core.AppComponent;
import com.utex.core.UTEXApplication;
import com.utex.db.ExTickerDao;
import com.utex.mvp.depth.view.DepthFragment;
import com.utex.mvp.home.bean.TickerData;
import com.utex.mvp.home.view.HomeFragment;
import com.utex.mvp.hometab.bean.JumpEvent;
import com.utex.mvp.marketdetail.bean.CoinBaseVO;
import com.utex.mvp.marketdetail.bean.KLineEntity;
import com.utex.mvp.marketdetail.bean.MiTabParamVo;
import com.utex.mvp.marketdetail.dagger2.DaggerMarketDetailComponent;
import com.utex.mvp.marketdetail.dagger2.MarketDetailModule;
import com.utex.mvp.marketdetail.presenter.IMarketDetailPresenter;
import com.utex.mvp.sketch.view.SketchFragment;
import com.utex.mvp.vol.view.VolFragment;
import com.utex.recevier.TickerReceiver;
import com.utex.utils.BubbleUtils;
import com.utex.utils.MarketSocketPackUtils;
import com.utex.utils.SharedPreferencesUtil;
import com.utex.utils.Utils;
import com.utex.widget.klinechart.chart.KChartView;
import com.utex.widget.klinechart.chart.formatter.TimeFormatter;
import com.utex.widget.viewpager.FullScreenViewPager;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MarketDetailActivity extends BaseActivity implements IMarketDetailView, TickerReceiver.TickerListener {

    @Inject
    IMarketDetailPresenter iMarketDetailPresenter;

    @BindView(R.id.mi_market_detail_tab_title)
    MagicIndicator miMarketDetailTabTitle;

    @BindView(R.id.vp_market_detail_page)
    FullScreenViewPager vpMarketDetailPage;

    @BindView(R.id.mi_market_detail_tab_time_title)
    MagicIndicator miMarketDetailTabTimeTitle;

    @BindView(R.id.vp_market_detail_time_page)
    ViewPager vpMarketDetailTimePage;

    @BindView(R.id.rl_market_detail_top_parent)
    RelativeLayout rlMarketDetailTopParent;

    @BindView(R.id.tv_market_detail_title)
    TextView tvMarketDetailTitle;

    @BindView(R.id.tv_market_detail_price)
    TextView tvMarketDetailPrice;

    @BindView(R.id.tv_market_defalue_rise)
    TextView tvMarketDetailRise;

    @BindView(R.id.tv_market_detail_vol_num)
    TextView tvMarketDetailVolNum;

    @BindView(R.id.tv_market_detail_gao_num)
    TextView tvMarketDetailGaoNum;

    @BindView(R.id.tv_market_detail_kai_num)
    TextView tvMarketDetailKaiNum;

    @BindView(R.id.tv_market_detail_di_num)
    TextView tvMarketDetailDiNum;

    @BindView(R.id.tv_market_detail_shou_num)
    TextView tvMarketDetailShouNum;

    @BindView(R.id.kchart_view)
    KChartView kChartView;

    @BindView(R.id.mi_market_detail_tab_index_title)
    MagicIndicator miMarketDetailTabIndexTitle;

    @BindView(R.id.vp_market_detail_index_page)
    ViewPager vpMarketDetailIndexPage;

    @BindView(R.id.sv_market_detail_parent)
    ScrollView svMarketDetailParent;

    @BindView(R.id.iv_market_detail_optional)
    ImageView ivMarketDetailOptional;

    @BindView(R.id.tv_market_detail_plaftform_price)
    TextView tvMarketDetailPlatformPrice;

    private TickerDo currTicker;

    private List<MiTabParamVo> mTitleDataList;

    private TickerReceiver tickerReceiver;

    private Handler handler = new Handler((Handler.Callback)
            this::handleMessage);


    private KChartAdapter mAdapter;

    private List<BaseFragment> fragments;

    private MiTabParamVo currTime;

    private boolean isCurrPage = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_detail);
        ButterKnife.bind(this);

        tickerReceiver = new TickerReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.ticker_detail");
        registerReceiver(tickerReceiver, intentFilter);
        tickerReceiver.setTickerListener(this);

        String coinCode = getIntent().getStringExtra(Constants.COIN_CODE);
        currTicker = ExTickerDao.getTickerByCode(coinCode);
        tvMarketDetailTitle.setText(currTicker.getCoin_market_code().replace("_", "/").toUpperCase());

        mTitleDataList = new ArrayList<>();
        mTitleDataList.add(new MiTabParamVo(Utils.getResourceString(R.string.shen_du), 1));
        mTitleDataList.add(new MiTabParamVo(Utils.getResourceString(R.string.cheng_jiao), 2));
        mTitleDataList.add(new MiTabParamVo(Utils.getResourceString(R.string.jian_kuang), 3));
        init();
        initKline();

        if (currTicker.getOptional() == 1) {
            ivMarketDetailOptional.setAlpha(1f);
        } else {
            ivMarketDetailOptional.setAlpha(0.5f);
        }

    }

    private void initKline() {
        mAdapter = new KChartAdapter();
        kChartView.setAdapter(mAdapter);
        kChartView.setDateTimeFormatter(new TimeFormatter());
        kChartView.setGridRows(6);
        kChartView.setGridColumns(7);
        kChartView.setScale(Utils.getTickerShowScale(currTicker));
        kChartView.showLoading();
    }

    private void init() {
        //获取sp时间若存在则代表是不显示
        iMarketDetailPresenter.setTime();
        iMarketDetailPresenter.setIndex();

        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        TitleAdapter titleAdapter = new TitleAdapter(mTitleDataList, vpMarketDetailPage);
        commonNavigator.setAdapter(titleAdapter);
        miMarketDetailTabTitle.setNavigator(commonNavigator);
        ViewPagerHelper.bind(miMarketDetailTabTitle, vpMarketDetailPage);

        titleAdapter.setTitleAdapterListener(index -> {
//                svMarketDetailParent.invalidate();
        });

        fragments = new ArrayList<>();
        DepthFragment depthFragment = new DepthFragment();
        Bundle args = new Bundle();
        args.putString(FiledConstants.COIN_CODE, currTicker.getCoin_market_code());
        depthFragment.setArguments(args);
        fragments.add(depthFragment);
        fragments.add(new VolFragment());
        fragments.add(new SketchFragment());

        vpMarketDetailPage.setAdapter(new MarketDetailPageAdapter(getSupportFragmentManager(), fragments));
        vpMarketDetailPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.e("TAG", String.valueOf(position));
                vpMarketDetailPage.resetHeight(position);
                switch (position) {
                    case 0:
                        MobclickAgent.onEvent(MarketDetailActivity.this, UMConstants.KLINE_PAIR_INFO_DEPTH);
                        break;
                    case 1:
                        MobclickAgent.onEvent(MarketDetailActivity.this, UMConstants.KLINE_PAIR_INFO_DEAL);
                        break;
                    case 2:
                        MobclickAgent.onEvent(MarketDetailActivity.this, UMConstants.KLINE_PAIR_INFO_INROUDCE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vpMarketDetailPage.setOffscreenPageLimit(3);
        handler.sendEmptyMessageDelayed(2, 300);
        handler.sendEmptyMessageDelayed(0, 100);
        handler.sendEmptyMessageDelayed(3, 100);
        handler.sendEmptyMessageDelayed(4, 100);

        iMarketDetailPresenter.getCoinInfo(currTicker.getCoin_market_code().split("_")[0].toLowerCase());
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMarketDetailComponent
                .builder()
                .appComponent(appComponent)
                .marketDetailModule(new MarketDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isCurrPage = true;
    }

    @OnClick({R.id.iv_market_detail_back, R.id.iv_market_detail_optional, R.id.tv_market_detail_buy, R.id.tv_market_detail_sell, R.id.iv_market_detail_full})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_market_detail_full:
                //全屏
                MobclickAgent.onEvent(MarketDetailActivity.this, UMConstants.KLINE_SHOW_FULL_SCREEN);

                Intent intent = new Intent(getIntent());
                intent.setClass(this, MarketDetailFullActivity.class);
                startActivity(intent);
                isCurrPage = false;
                break;
            case R.id.iv_market_detail_back:
                finish();
                break;
            case R.id.iv_market_detail_optional:
                //自选
                MobclickAgent.onEvent(this, UMConstants.KLINE_COIN_PAIR_COLLECTION);
                if (UTEXApplication.getLoginUser() != null) {
                    if (currTicker.getOptional() == 1) {
                        iMarketDetailPresenter.cannelOptional(currTicker.getCoin_market_code());
                    } else {
                        iMarketDetailPresenter.addOptional(currTicker.getCoin_market_code(), currTicker.getId());
                    }
                } else {
                    if (currTicker.getOptional() == 1) {
                        cannelOptionalSuccess();
                    } else {
                        addOptionalSuccess();
                    }
//                    startLogin();
                }
                break;
            case R.id.tv_market_detail_buy:
                MobclickAgent.onEvent(this, UMConstants.KLINE_BOTTOM_BUY_BTN);

                SharedPreferencesUtil.putString(Constants.TRAD_TICKER, currTicker.getCoin_market_code());

                EventBus.getDefault().post(new JumpEvent(1));
                HomeFragment.isHomePage = false;

                new Handler().postDelayed(() -> {
                    setResult(200);
                    finish();
                }, 200);

                break;
            case R.id.tv_market_detail_sell:
                MobclickAgent.onEvent(this, UMConstants.kline_bottom_sell_btn);

                SharedPreferencesUtil.putString(Constants.TRAD_TICKER, currTicker.getCoin_market_code());
                EventBus.getDefault().post(new JumpEvent(2));
                HomeFragment.isHomePage = false;

                new Handler().postDelayed(() -> {
                    setResult(200);
                    finish();
                }, 200);


                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            iMarketDetailPresenter.volUnSubscribe();
            iMarketDetailPresenter.depthUnSubscribe();
            iMarketDetailPresenter.todayUnSubscribe();
            iMarketDetailPresenter.kLineUnSubscribe();

            if (tickerReceiver != null) {
                unregisterReceiver(tickerReceiver);
            }


        } catch (Exception e) {
            Log.e("TAG", e.getMessage());
        }

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

            times.add(new MiTabParamVo(Utils.getResourceString(R.string.fen_shi), 60));
            times.add(new MiTabParamVo(1 + Utils.getResourceString(R.string.fen_zhong), 60));
            times.add(new MiTabParamVo(3 + Utils.getResourceString(R.string.fen_zhong), 180));
            times.add(new MiTabParamVo(5 + Utils.getResourceString(R.string.fen_zhong), 300));
            times.add(new MiTabParamVo(15 + Utils.getResourceString(R.string.fen_zhong), 900));
            times.add(new MiTabParamVo(30 + Utils.getResourceString(R.string.fen_zhong), 1800));
            times.add(new MiTabParamVo(1 + Utils.getResourceString(R.string.xiao_shi), 3600));
            times.add(new MiTabParamVo(12 + Utils.getResourceString(R.string.xiao_shi), 12 * 3600));
            times.add(new MiTabParamVo(Utils.getResourceString(R.string.ri_xian), 24 * 3600));
            times.add(new MiTabParamVo(Utils.getResourceString(R.string.zhou_xian), 24 * 3600 * 7));
            times.add(new MiTabParamVo(Utils.getResourceString(R.string.yue_xian), 24 * 3600 * 30));

            if (currTime.getTitle().equals(Utils.getResourceString(R.string.fen_shi))) {
                MobclickAgent.onEvent(this, UMConstants.KLINE_TIME_SLOT_FENSHI);
            } else {
                switch (currTime.getNumber()) {
                    case 60:
                        MobclickAgent.onEvent(this, UMConstants.KLINE_TIME_SLOT_1MIN);
                        break;
                    case 180:
                        MobclickAgent.onEvent(this, UMConstants.KLINE_TIME_SLOT_3MIN);
                        break;
                    case 300:
                        MobclickAgent.onEvent(this, UMConstants.KLINE_TIME_SLOT_5MIN);
                        break;
                    case 900:
                        MobclickAgent.onEvent(this, UMConstants.KLINE_TIME_SLOT_15MIN);
                        break;
                    case 1800:
                        MobclickAgent.onEvent(this, UMConstants.KLINE_TIME_SLOT_30MIN);
                        break;
                    case 3600:
                        MobclickAgent.onEvent(this, UMConstants.KLINE_TIME_SLOT_1H);
                        break;
                    case 12 * 3600:
                        MobclickAgent.onEvent(this, UMConstants.KLINE_TIME_SLOT_12H);
                        break;
                    case 24 * 3600:
                        MobclickAgent.onEvent(this, UMConstants.KLINE_TIME_SLOT_DAY);
                        break;
                    case 24 * 3600 * 7:
                        MobclickAgent.onEvent(this, UMConstants.KLINE_TIME_SLOT_WEEK);
                        break;
                    case 24 * 3600 * 30:
                        MobclickAgent.onEvent(this, UMConstants.KLINE_TIME_SLOT_MONTH);
                        break;
                }
            }


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
        int showScale = Utils.getTickerShowScale(currTicker);

        tvMarketDetailPrice.setText(Utils.getScientificCountingMethodAfterData(Double.valueOf(resultBean.getLast()), showScale));

        double v = (Double.parseDouble(resultBean.getLast()) - Double.parseDouble(resultBean.getOpen())) / Double.parseDouble(resultBean.getOpen());
        DecimalFormat df = new DecimalFormat("######0.00");

        int isSWitchFrist = SharedPreferencesUtil.getInteger(FiledConstants.LIGHT_DARK, 1);

        switch (isSWitchFrist) {
            case 1:
                if (v >= 0) {
                    rlMarketDetailTopParent.setBackgroundResource(R.drawable.transaction_bg_rise);
                } else {
                    rlMarketDetailTopParent.setBackgroundResource(R.drawable.transaction_bg_falling);
                }
                break;
            case 2:
                rlMarketDetailTopParent.setBackgroundResource(R.drawable.transaction_bg_falling);
                break;
        }

        tvMarketDetailRise.setText(df.format(v * 100) + "%");

        double rate = UTEXApplication.getRate(currTicker.getCoin_market_code().split("_")[1]);
        tvMarketDetailPlatformPrice.setText("≈" + Utils.getScientificCountingMethodAfterData(Double.valueOf(resultBean.getLast()) * rate, showScale) + " " + SharedPreferencesUtil.getString(FiledConstants.PALTFORM_VALUAT, "USD"));

        tvMarketDetailVolNum.setText(resultBean.getVolume());
        tvMarketDetailGaoNum.setText(Utils.getScientificCountingMethodAfterData(Double.valueOf(resultBean.getHigh()), showScale));
        tvMarketDetailKaiNum.setText(Utils.getScientificCountingMethodAfterData(Double.valueOf(resultBean.getOpen()), showScale));
        tvMarketDetailDiNum.setText(Utils.getScientificCountingMethodAfterData(Double.valueOf(resultBean.getLow()), showScale));
        tvMarketDetailShouNum.setText(Utils.getScientificCountingMethodAfterData(Double.valueOf(resultBean.getLast()), showScale));
    }

    private List<KLineEntity> tickerDatas;

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
        CommonNavigator commonNavigator = new CommonNavigator(this);
        TitleAdapter titleAdapter = new TitleAdapter(indexs, vpMarketDetailIndexPage) {

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        };

        commonNavigator.setAdapter(titleAdapter);
        miMarketDetailTabIndexTitle.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerPadding(BubbleUtils.dp2px(10));
        titleContainer.setDividerDrawable(this.getResources().getDrawable(R.drawable.shape_vertical_line));

        ViewPagerHelper.bind(miMarketDetailTabIndexTitle, vpMarketDetailIndexPage);

        titleAdapter.setTitleAdapterListener(index -> {
            MobclickAgent.onEvent(MarketDetailActivity.this, "kline_index_" + indexs.get(index).getTitle().toLowerCase());

            kChartView.switchLine(indexs.get(index).getNumber());
        });

        List<BaseFragment> fragments = new ArrayList<>();
        for (MiTabParamVo a : indexs) {
            fragments.add(new PageFragment());
        }

        vpMarketDetailIndexPage.setAdapter(new MarketDetailPageAdapter(getSupportFragmentManager(), fragments));
        vpMarketDetailIndexPage.setCurrentItem(4);
    }


    @Override
    public void getCoinInfoSuccess(CoinBaseVO body) {
        SketchFragment fragment = (SketchFragment) fragments.get(2);
        fragment.setData(body.getData());
    }

    @Override
    public void addOptionalSuccess() {
        ExTickerDao.changeOption(currTicker.getCoin_market_code());
        ivMarketDetailOptional.setAlpha(1f);
    }

    @Override
    public void cannelOptionalSuccess() {
        ExTickerDao.changeOption(currTicker.getCoin_market_code());
        ivMarketDetailOptional.setAlpha(0.5f);
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
        if (!isCurrPage) {
            return;
        }
        JSONObject jsonObject = JSON.parseObject(json);
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
                iMarketDetailPresenter.setData();
//
//                tvTradHorizonntalPrice.setText(tickerData.getLast());
//                tvTradHorizonntalPlatform.setVisibility(View.GONE);
            }

//            HourData hourData = JSON.parseObject(json, HourData.class);
//            TickerData resultBean = hourData.getResult();

        } else if ("depth.update".equals(method)) {
            //买卖盘
            DepthFragment fragment = (DepthFragment) fragments.get(0);
            JSONArray params = jsonObject.getJSONArray("params");
            fragment.setData(params);
        } else if ("deals.update".equals(method)) {
            //成交
            VolFragment fragment = (VolFragment) fragments.get(1);
            JSONArray params = jsonObject.getJSONArray("params");
            fragment.setData(params);
        } else if (MarketSocketPackUtils.KLINE_QUERY_ID == id) {
            JSONArray result = jsonObject.getJSONArray("result");
            iMarketDetailPresenter.calculationKLine(result);
        } else if ("kline.update".equals(method)) {
            JSONArray result = jsonObject.getJSONArray("params");
            iMarketDetailPresenter.calculationKLineUpdate(result);
        } else if (MarketSocketPackUtils.DEPTH_SUBSCRIBE_ID == id) {

        } else if (MarketSocketPackUtils.VOl_SUBSCRIBE_ID == id) {

        }
    }

    private boolean handleMessage(Message message) {
        if (currTicker == null) {
            return true;
        }
        switch (message.what) {
            case 0:
                //当前币种
                iMarketDetailPresenter.coinSubscribe(currTicker);
                break;
            case 1:
                if (currTime == null) {
                    return true;
                }
                iMarketDetailPresenter.clearKLineData();

                iMarketDetailPresenter.KLineQuery(currTicker, currTime);
                break;
            case 2:
                vpMarketDetailPage.setObjectForPosition(fragments.get(0).getView(), 0);
                vpMarketDetailPage.setObjectForPosition(fragments.get(1).getView(), 1);
                vpMarketDetailPage.setObjectForPosition(fragments.get(2).getView(), 2);
                vpMarketDetailPage.resetHeight(0);
                break;
            case 3:
                //深度获取
                String[] split = currTicker.getDepth_merge().split(",");
                String value = "";
                for (String s : split) {
                    if (value.length() < s.length()) {
                        value = s;
                    }
                }
                iMarketDetailPresenter.depthSubscribe(value, currTicker);
                break;
            case 4:
                //成交
                iMarketDetailPresenter.volSubscribe(currTicker);
                break;
            case 5:
                //K线订阅
                iMarketDetailPresenter.kLineSubscribe(currTicker, currTime);
                break;
        }
        return true;

    }

}
