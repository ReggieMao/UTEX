package com.utex.mvp.trad.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.quintar.IMarketAidlInterface;
import com.umeng.analytics.MobclickAgent;
import com.utex.R;
import com.utex.base.BaseFragment;
import com.utex.bean.TickerDo;
import com.utex.bean.UserDO;
import com.utex.common.Constants;
import com.utex.common.FiledConstants;
import com.utex.common.UMConstants;
import com.utex.core.AppComponent;
import com.utex.core.UTEXApplication;
import com.utex.db.ExTickerDao;
import com.utex.listener.ETtextChangeListener;
import com.utex.mvp.home.bean.TickerData;
import com.utex.mvp.market.view.MarketFragment;
import com.utex.mvp.marketdetail.view.MarketDetailActivity;
import com.utex.mvp.marketdetail.view.MarketDetailPageAdapter;
import com.utex.mvp.order.view.OrderActivity;
import com.utex.mvp.trad.bean.Depth;
import com.utex.mvp.trad.bean.Entrust;
import com.utex.mvp.trad.bean.WebsocketEntrustVo;
import com.utex.mvp.trad.dagger2.DaggerTradComponent;
import com.utex.mvp.trad.dagger2.TradModule;
import com.utex.mvp.trad.presenter.ITradPresenter;
import com.utex.mvp.user.view.LoginActivity;
import com.utex.mvp.vol.bean.VolVo;
import com.utex.recevier.TickerReceiver;
import com.utex.service.OrderSocketService;
import com.utex.utils.ArithmeticUtil;
import com.utex.utils.BubbleUtils;
import com.utex.utils.DialogUtils;
import com.utex.utils.MarketSocketPackUtils;
import com.utex.utils.SharedPreferencesUtil;
import com.utex.utils.Utils;
import com.utex.widget.SnappingStepper;
import com.utex.widget.bar.BubbleConfigBuilder;
import com.utex.widget.bar.BubbleSeekBar;
import com.utex.widget.button.TradSwitchButton;
import com.utex.widget.smartrefresh.layout.SmartRefreshLayout;
import com.utex.widget.smartrefresh.layout.api.RefreshLayout;
import com.utex.widget.smartrefresh.layout.listener.OnRefreshListener;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Demon on 2018/5/18.
 * 交易
 */
public class TradFragment extends BaseFragment implements TickerReceiver.TickerListener, ITradView, OnRefreshListener {

    @Inject
    ITradPresenter iTradPresenter;

    @BindView(R.id.rv_tard_sell_handicap)
    RecyclerView rvTardSellHandicap;

    @BindView(R.id.rv_tard_buy_handicap)
    RecyclerView rvTardBuyHandicap;

    @BindView(R.id.rv_trad_cur_entrust_list)
    RecyclerView rvTradCurEntrustList;

    @BindView(R.id.dl_trad_parent)
    DrawerLayout dlTradParent;

    @BindView(R.id.vp_trad_left_page)
    ViewPager vpTardLeftPage;

    @BindView(R.id.mi_trad_left_tab)
    MagicIndicator miTradLeftTab;

    @BindView(R.id.tv_trad_rate)
    TextView tvTradRate;

    @BindView(R.id.tv_trad_coin_name)
    TextView tvTradCoinName;

    @BindView(R.id.tv_trad_horizonntal_platform)
    TextView tvTradHorizonntalPlatform;

    @BindView(R.id.tv_trad_horizonntal_price)
    TextView tvTradHorizonntalPrice;

    @BindView(R.id.rg_trad_horizontal_direction)
    RadioGroup rgTradHorizontalDirection;

    @BindView(R.id.rg_trad_horizontal_operation_direction)
    RadioGroup rgTradHorizontalOperationDirection;

    @BindView(R.id.tv_trad_horizontal_btn)
    TextView tvTradHorizontalBtn;

    @BindView(R.id.tv_trad_horizontal_operation_direction_2_text)
    TextView tvTradHorizontalOperationDirection2Text;

    @BindView(R.id.ss_trad_horizontal_add_sub)
    SnappingStepper ssTradHorizontalAddSub;

    @BindView(R.id.tv_trad_horizontal_available_fund)
    TextView tvTradHorizontalAvailableFund;

    @BindView(R.id.et_trad_amount)
    EditText etTradAmount;

    @BindView(R.id.tv_trad_total_price)
    TextView tvTradTotalPrice;

    @BindView(R.id.bsb_trad_progress)
    BubbleSeekBar bsbTradProgress;

    @BindView(R.id.tv_trad_amount_coin_name)
    TextView tvTradAmountCoinName;

    @BindView(R.id.tv_trad_horizontal_plaftfrom_price)
    TextView tvTradHorizontalPlaftfromPrice;

    @BindView(R.id.iv_trad_optional)
    ImageView ivTradOptional;

    @BindView(R.id.rl_trad_empty)
    RelativeLayout rlTradEmpty;

    @BindView(R.id.sb_trad_horizontal_switch)
    TradSwitchButton sbTradHorizontalSwitch;

    @BindView(R.id.ll_trade_depth_parent)
    LinearLayout llTradDepthParent;

    @BindView(R.id.rv_trade_vol_list)
    RecyclerView rvTradeVolList;

    @BindView(R.id.ll_trade_vol_parent)
    LinearLayout llTradeVolParent;

    @BindView(R.id.refresh_list)
    SmartRefreshLayout refreshList;

    @BindView(R.id.ll_horizontal_total_price_parent)
    LinearLayout llHorizontalTotalPriceParent;

    private List<String> mTitleDataList;

    private TickerReceiver tickerReceiver;

    private IMarketAidlInterface mOrderAidl;
    /**
     * 服务绑定
     */
    private ServiceConnection orderConn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mOrderAidl = IMarketAidlInterface.Stub.asInterface(service);

        }
    };


    private TickerDo currTickerDo;

    /**
     * 当前价钱
     */
    private String last;

    /**
     * 深度
     */
    private Depth depth;

    private MarketHandicapAdapter marketHandicapAskAdapter;

    private MarketHandicapAdapter marketHandicapBidAdapter;

    private BigDecimal availableFund;

    private Handler handler = new Handler((Handler.Callback) message -> {
        switch (message.what) {
            case 0:
                //设置深度
                iTradPresenter.setCap();

                //设置侧滑列表
                iTradPresenter.setCoinList();
                break;
        }

        return true;
    });

    private Map<String, Entrust.DataBean> entrustMap;

    private TradEntrustAdapter tradEntrustAdapter;

    private int currCannelOrderPosition;

    private boolean isNumberChange;

    private List<BaseFragment> fragments;

    /**
     * 是否第一次
     */
    private boolean isFirst;

    private String depthMerge;
    private Intent orderIntent;
    private TradeVolAdapter tradeVolAdapter;
    private List<VolVo> tradeVols;
    private boolean isBindConn;
    private boolean isBindOrderConn;
    private boolean isCurrPage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.fragment_trad, container, false);
        ButterKnife.bind(this, inflate);
        etTradAmount.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bindService();
        //从本地去数据，若为空则取表中第一个
        String codeMarketCode = SharedPreferencesUtil.getString(Constants.TRAD_TICKER, "");
        if (TextUtils.isEmpty(codeMarketCode)) {
            codeMarketCode = "gzh_usdt";
        }
        currTickerDo = ExTickerDao.getTickerByCode(codeMarketCode);

        if (currTickerDo == null) {
            return;
        }

        tradeVols = new ArrayList<>();


        refreshList.setOnRefreshListener(this);

        llTradDepthParent.setVisibility(View.VISIBLE);
        llTradeVolParent.setVisibility(View.GONE);
        rvTradeVolList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        tradeVolAdapter = new TradeVolAdapter(currTickerDo);
        rvTradeVolList.setAdapter(tradeVolAdapter);

        handler.sendEmptyMessageDelayed(0, 80);

        if (currTickerDo.getOptional() == 1) {
            ivTradOptional.setImageResource(R.drawable.transaction_icon_collect_sel);
        } else {
            ivTradOptional.setImageResource(R.drawable.transaction_icon_collect);
        }

        rgTradHorizontalDirection.check(R.id.rg_trad_horizontal_direction_buy);
        rgTradHorizontalOperationDirection.check(R.id.rg_trad_horizontal_operation_direction_1);

        UserDO query = UTEXApplication.getLoginUser();
        if (query == null) {
            tvTradHorizontalBtn.setText(Utils.getResourceString(R.string.deng_lu));
        }

        rgTradHorizontalOperationDirection.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.rg_trad_horizontal_operation_direction_1:
                    MobclickAgent.onEvent(getContext(), UMConstants.TRADE_DEAL_LIMIT_BILL_SWITCH);

                    llHorizontalTotalPriceParent.setVisibility(View.VISIBLE);
                    tvTradHorizontalPlaftfromPrice.setVisibility(View.VISIBLE);

                    tvTradHorizontalOperationDirection2Text.setVisibility(View.GONE);
                    ssTradHorizontalAddSub.setVisibility(View.VISIBLE);
                    iTradPresenter.setDirection(0);

                    tvTradAmountCoinName.setText(currTickerDo.getCoin_market_code().split("_")[0].toUpperCase());
                    tvTradAmountCoinName.setTextColor(getResources().getColor(R.color.bafb3b0_42ffffff));
                    break;
                case R.id.rg_trad_horizontal_operation_direction_2:
                    MobclickAgent.onEvent(getContext(), UMConstants.TRADE_DEAL_MARKER_BILL_SWITCH);

                    tvTradHorizontalPlaftfromPrice.setVisibility(View.INVISIBLE);
                    llHorizontalTotalPriceParent.setVisibility(View.INVISIBLE);

                    tvTradHorizontalOperationDirection2Text.setVisibility(View.VISIBLE);
                    ssTradHorizontalAddSub.setVisibility(View.GONE);
                    iTradPresenter.setDirection(1);
                    //清空已输入数量
                    etTradAmount.setText("");
                    //切换成市价，并且是买，则改变数量的币种，且字体颜色高亮
                    if (iTradPresenter.getStatus() == 2) {
                        tvTradAmountCoinName.setText(currTickerDo.getCoin_market_code().split("_")[1].toUpperCase());
                        tvTradAmountCoinName.setTextColor(getResources().getColor(R.color.green));
                    }
                    break;
            }

            updateAmountDecimal();
            iTradPresenter.getAssetList();
        });

        tvTradAmountCoinName.setText(currTickerDo.getCoin_market_code().split("_")[0].toUpperCase());
        tvTradAmountCoinName.setTextColor(getResources().getColor(R.color.bafb3b0_42ffffff));

        rgTradHorizontalDirection.setOnCheckedChangeListener((radioGroup, i) -> {
            BubbleConfigBuilder configBuilder = bsbTradProgress.getConfigBuilder();

            if (UTEXApplication.getLoginUser() != null) {
                switch (i) {
                    case R.id.rg_trad_horizontal_direction_buy:
                        MobclickAgent.onEvent(getContext(), UMConstants.TRADE_DEAL_BUY_VIEW_BUY_BTN);

                        tvTradHorizontalBtn.setText(Utils.getResourceString(R.string.buy));
                        tvTradHorizontalBtn.setBackgroundResource(R.drawable.trad_buy_btn_select);

                        iTradPresenter.setStatus(2);
                        iTradPresenter.getAssetList();

                        configBuilder.secondTrackColor(Utils.getResourceColor(getContext(), R.color.f50b577));
                        configBuilder.build();
                        bsbTradProgress.setBubbleColor(Utils.getResourceColor(getContext(), R.color.f50b577));
                        bsbTradProgress.setSecondTrackColor(Utils.getResourceColor(getContext(), R.color.f50b577));

                        switch (iTradPresenter.getDirection()) {
                            case 0:
                                //限价
                                tvTradAmountCoinName.setText(currTickerDo.getCoin_market_code().split("_")[0].toUpperCase());
                                tvTradAmountCoinName.setTextColor(getResources().getColor(R.color.bafb3b0_42ffffff));
                                break;
                            case 1:
                                //市价
                                tvTradAmountCoinName.setText(currTickerDo.getCoin_market_code().split("_")[1].toUpperCase());
                                tvTradAmountCoinName.setTextColor(getResources().getColor(R.color.green));
                                break;
                        }
                        break;
                    case R.id.rg_trad_horizontal_direction_sell:
                        MobclickAgent.onEvent(getContext(), UMConstants.TRADE_DEAL_SELL_VIEW_SELL_BTN);

                        tvTradHorizontalBtn.setText(Utils.getResourceString(R.string.sell));
                        tvTradHorizontalBtn.setBackgroundResource(R.drawable.trad_sell_btn_select);
                        iTradPresenter.setStatus(1);
                        iTradPresenter.getAssetList();

                        configBuilder.secondTrackColor(Utils.getResourceColor(getContext(), R.color.fee6a5e));
                        configBuilder.build();
                        bsbTradProgress.setBubbleColor(Utils.getResourceColor(getContext(), R.color.fee6a5e));
                        bsbTradProgress.setSecondTrackColor(Utils.getResourceColor(getContext(), R.color.fee6a5e));

                        tvTradAmountCoinName.setText(currTickerDo.getCoin_market_code().split("_")[0].toUpperCase());
                        tvTradAmountCoinName.setTextColor(getResources().getColor(R.color.bafb3b0_42ffffff));
                        break;
                }
            } else {
                switch (i) {
                    case R.id.rg_trad_horizontal_direction_buy:
                        tvTradHorizontalBtn.setText(Utils.getResourceString(R.string.deng_lu));
                        tvTradHorizontalBtn.setBackgroundResource(R.drawable.trad_buy_btn_select);
                        iTradPresenter.setStatus(2);
                        bsbTradProgress.setBubbleColor(Utils.getResourceColor(getContext(), R.color.f50b577));
                        bsbTradProgress.setSecondTrackColor(Utils.getResourceColor(getContext(), R.color.f50b577));
                        configBuilder.secondTrackColor(Utils.getResourceColor(getContext(), R.color.f50b577));
                        configBuilder.build();

                        switch (iTradPresenter.getDirection()) {
                            case 0:
                                //限价
                                tvTradAmountCoinName.setText(currTickerDo.getCoin_market_code().split("_")[0].toUpperCase());
                                tvTradAmountCoinName.setTextColor(getResources().getColor(R.color.bafb3b0_42ffffff));
                                break;
                            case 1:
                                //市价
                                tvTradAmountCoinName.setText(currTickerDo.getCoin_market_code().split("_")[1].toUpperCase());
                                tvTradAmountCoinName.setTextColor(getResources().getColor(R.color.green));
                                break;
                        }
                        break;
                    case R.id.rg_trad_horizontal_direction_sell:

                        tvTradHorizontalBtn.setText(Utils.getResourceString(R.string.deng_lu));
                        tvTradHorizontalBtn.setBackgroundResource(R.drawable.trad_sell_btn_select);
                        iTradPresenter.setStatus(1);
                        bsbTradProgress.setBubbleColor(Utils.getResourceColor(getContext(), R.color.fee6a5e));
                        bsbTradProgress.setSecondTrackColor(Utils.getResourceColor(getContext(), R.color.fee6a5e));
                        configBuilder.secondTrackColor(Utils.getResourceColor(getContext(), R.color.fee6a5e));
                        configBuilder.build();

                        tvTradAmountCoinName.setText(currTickerDo.getCoin_market_code().split("_")[0].toUpperCase());
                        tvTradAmountCoinName.setTextColor(getResources().getColor(R.color.bafb3b0_42ffffff));
                        break;
                }
            }

            updateAmountDecimal();
            setEtCap();
        });

        etTradAmount.addTextChangedListener(new ETtextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                super.onTextChanged(charSequence, i, i1, i2);
                try {
                    if (charSequence.toString().length() >= 2 && charSequence.toString().startsWith("0") && !charSequence.toString().contains(".")) {
                        etTradAmount.setText(charSequence.toString().substring(1));
                        etTradAmount.setSelection(1);
                    }

                    if (charSequence.toString().contains(".")) {
                        String[] split = charSequence.toString().split("\\.");
                        if (split.length == 2) {
                            if (split[0].length() >= 2 && split[0].toString().startsWith("0")) {
                                etTradAmount.setText(charSequence.toString().substring(1));
                                etTradAmount.setSelection(1);
                            }
                        }
                    }

                    if (TextUtils.isEmpty(etTradAmount.getText().toString())) {
                        tvTradTotalPrice.setText("0" + currTickerDo.getCoin_market_code().split("_")[1].toUpperCase());
                    } else {
                        int scale = 0;
                        switch (currTickerDo.getCoin_market_code().split("_")[1]) {
                            case "gzh":
                                scale = 6;
                                break;
                            case "eth":
                                scale = 4;
                                break;
                            case "usdt":
                                scale = 2;
                                break;
                        }
                        tvTradTotalPrice.setText(Utils.getScientificCountingMethodAfterData(Double.parseDouble(etTradAmount.getText().toString()) * ssTradHorizontalAddSub.getGoodsNumber(), scale) + currTickerDo.getCoin_market_code().split("_")[1].toUpperCase());
                    }

                    if (etTradAmount.hasFocus()) {
                        Log.e("amount", "has:" + etTradAmount.getText().toString());
                        //获取焦点
                        if (TextUtils.isEmpty(etTradAmount.getText().toString()) || Double.parseDouble(etTradAmount.getText().toString()) == 0) {
                            bsbTradProgress.setProgress(0);
                        } else if (Float.parseFloat(etTradAmount.getText().toString()) > bsbTradProgress.getMax()) {
                            bsbTradProgress.setProgress(bsbTradProgress.getMax());
                        } else {
                            bsbTradProgress.setProgress(Double.parseDouble(etTradAmount.getText().toString()));
                        }
                    } else {
                        //没有获取
                        Log.e("amount", "not");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        ssTradHorizontalAddSub.setUpdateGoodsNumberListener(new SnappingStepper.UpdateGoodsNumberListener() {
            @Override
            public void updateGoodsNumber(double number) {
                try {
                    if (iTradPresenter.getStatus() == 2) {
                        //买入
                        setAsset(availableFund.doubleValue(), currTickerDo.getCoin_market_code().split("_")[1]);
                    }

                    if (ssTradHorizontalAddSub.hasFocus()) {
                        int scale = 0;
                        switch (currTickerDo.getCoin_market_code().split("_")[1]) {
                            case "gzh":
                                scale = 6;
                                break;
                            case "eth":
                                scale = 4;
                                break;
                            case "usdt":
                                scale = 2;
                                break;
                        }

                        tvTradTotalPrice.setText(Utils.getScientificCountingMethodAfterData(Double.parseDouble(etTradAmount.getText().toString()) * number, scale) + currTickerDo.getCoin_market_code().split("_")[1].toUpperCase());
                    }
                } catch (Exception e) {
                    tvTradTotalPrice.setText("0");
                }

                double rate = UTEXApplication.getRate(currTickerDo.getCoin_market_code().split("_")[1]);
                String scientificCountingMethodAfterData = Utils.getScientificCountingMethodAfterData(number * rate, Utils.getTickerShowScale(currTickerDo));
                tvTradHorizontalPlaftfromPrice.setText("≈" + scientificCountingMethodAfterData + " " + SharedPreferencesUtil.getString(FiledConstants.PALTFORM_VALUAT, "USD"));
            }

            @Override
            public void update(double number) {
                try {
                    int scale = 0;
                    switch (currTickerDo.getCoin_market_code().split("_")[1]) {
                        case "gzh":
                            scale = 6;
                            break;
                        case "eth":
                            scale = 4;
                            break;
                        case "usdt":
                            scale = 2;
                            break;
                    }
                    tvTradTotalPrice.setText(Utils.getScientificCountingMethodAfterData(Double.parseDouble(etTradAmount.getText().toString()) * number, scale) + currTickerDo.getCoin_market_code().split("_")[1].toUpperCase());
                } catch (Exception e) {
                    tvTradTotalPrice.setText("0");
                }
            }
        });

        bsbTradProgress.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                if (fromUser && isNumberChange) {
                    if (progressFloat > bsbTradProgress.getMax()) {
                        etTradAmount.setText(Utils.getScientificCountingMethodAfterData(bsbTradProgress.getMax(), Integer.valueOf(currTickerDo.getAmount_decimal())));
                    } else {
                        etTradAmount.setText(Utils.getScientificCountingMethodAfterData(Double.valueOf(progressFloat), Integer.valueOf(currTickerDo.getAmount_decimal())));
                    }
                }

                Log.e("Demon", String.valueOf(progressFloat));
            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }
        });

        sbTradHorizontalSwitch.setOnStateChangedListener(new TradSwitchButton.OnStateChangedListener() {
            @Override
            public void toggleToOn(TradSwitchButton view) {
                //变成明细
                llTradeVolParent.setVisibility(View.VISIBLE);
                llTradDepthParent.setVisibility(View.GONE);
                MobclickAgent.onEvent(getContext(), UMConstants.TRADE_HANDICAP_DETAIL_SWITCH);
            }

            @Override
            public void toggleToOff(TradSwitchButton view) {
                //五档显示
                llTradeVolParent.setVisibility(View.GONE);
                llTradDepthParent.setVisibility(View.VISIBLE);
                MobclickAgent.onEvent(getContext(), UMConstants.TRADE_HANDICAP_FIVE_RANGE_SWITCH);
            }
        });
    }

    /**
     * 若 是市价 买入 则取的是money精度 其他则是amount精度
     */
    private void updateAmountDecimal() {
        if (iTradPresenter.getStatus() == 2 && iTradPresenter.getDirection() == 1) {
            //数量就是的精度取 交易区的精度
            etTradAmount.setFilters(new InputFilter[]{new ETFilter(Integer.parseInt(currTickerDo.getMoney_decimal()), Integer.MAX_VALUE)});
            tvTradAmountCoinName.setText(currTickerDo.getCoin_market_code().split("_")[1].toUpperCase());
            tvTradAmountCoinName.setTextColor(getResources().getColor(R.color.green));
        } else {
            //取 币种amount的精度
            etTradAmount.setFilters(new InputFilter[]{new ETFilter(Integer.parseInt(currTickerDo.getAmount_decimal()), currTickerDo.getMax_amount())});
            tvTradAmountCoinName.setText(currTickerDo.getCoin_market_code().split("_")[0].toUpperCase());
            tvTradAmountCoinName.setTextColor(getResources().getColor(R.color.bafb3b0_42ffffff));
        }
    }


    private void bindService() {
        //绑定行情服务
//        Intent intent = new Intent(getActivity(), TickerSocketService.class);
//        isBindConn = getContext().bindService(intent, conn, Context.BIND_AUTO_CREATE);

        //绑定订单服务
        orderIntent = new Intent(getActivity(), OrderSocketService.class);
        isBindOrderConn = getContext().bindService(orderIntent, orderConn, Context.BIND_AUTO_CREATE);

        //注册广播
        tickerReceiver = new TickerReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.ticker_trad");
        getContext().registerReceiver(tickerReceiver, intentFilter);
        tickerReceiver.setTickerListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isBindOrderConn) {
            getContext().unbindService(orderConn);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isCurrPage) {
            handler.postDelayed(() -> iTradPresenter.getCurrPageMarketData(depthMerge, currTickerDo), 80);
        }
    }

    /**
     * 整个页面刷新重新这是币种
     */
    public void resetPage() {
        if (mOrderAidl != null) {
            String json = "{\"id\":99,\"method\":\"server.ping\",\"params\":{}}";
            try {
                mOrderAidl.send(json, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        isFirst = true;
        //从本地去数据，若为空则取表中第一个
        String codeMarketCode = SharedPreferencesUtil.getString(Constants.TRAD_TICKER, null);
        if (codeMarketCode == null) {
            codeMarketCode = "gzh_usdt";
        }

        currTickerDo = ExTickerDao.getTickerByCode(codeMarketCode);

        if (currTickerDo != null) {
            int scale = Integer.parseInt(currTickerDo.getMoney_decimal()) - Integer.parseInt(currTickerDo.getAmount_decimal());
            ssTradHorizontalAddSub.setETLength(scale, currTickerDo.getMax_amount());
            bsbTradProgress.setScale(Integer.parseInt(currTickerDo.getAmount_decimal()));
            String[] split = currTickerDo.getDepth_merge().split(",");
            depthMerge = split[0];

            //设置数量过滤
            String amount_decimal = currTickerDo.getAmount_decimal();
            Log.e("TAG", amount_decimal);
            updateAmountDecimal();

            if (marketHandicapAskAdapter != null) {
                marketHandicapAskAdapter.setCurrTickerDo(currTickerDo);
            }
            if (marketHandicapBidAdapter != null) {
                marketHandicapBidAdapter.setCurrTickerDo(currTickerDo);
            }

            if (currTickerDo.getOptional() == 1) {
                ivTradOptional.setImageResource(R.drawable.transaction_icon_collect_sel);
            } else {
                ivTradOptional.setImageResource(R.drawable.transaction_icon_collect);
            }

            handler.postDelayed(() -> iTradPresenter.getCurrPageMarketData(depthMerge, currTickerDo), 80);
            if (UTEXApplication.getLoginUser() != null) {
                iTradPresenter.getAssetList();
                switch (iTradPresenter.getStatus()) {
                    case 2:
                        tvTradHorizontalBtn.setBackgroundResource(R.drawable.trad_buy_btn_select);
                        break;
                    case 1:
                        tvTradHorizontalBtn.setBackgroundResource(R.drawable.trad_sell_btn_select);
                        break;
                }

                if (currTickerDo != null) {
                    iTradPresenter.getCurrMarketEntrust(currTickerDo.getCoin_market_code());
                }
            } else {
                tvTradHorizontalBtn.setText(Utils.getResourceString(R.string.deng_lu));
                setAsset(0, currTickerDo.getCoin_market_code().split("_")[1].toUpperCase());
                tvTradHorizontalAvailableFund.setVisibility(View.INVISIBLE);
                BubbleConfigBuilder configBuilder = bsbTradProgress.getConfigBuilder();
                configBuilder.max(0f);
                configBuilder.min(0f);
                configBuilder.progress(0);
                configBuilder.sectionTextColor(Utils.getResourceColor(getContext(), R.color.transparent));
                configBuilder.build();
                isNumberChange = false;
            }

            updateOption();
        }

        iTradPresenter.getAllCoinData(fragments);

        BubbleConfigBuilder configBuilder = bsbTradProgress.getConfigBuilder();
        Log.e("Demon", String.valueOf(iTradPresenter.getStatus()) + "::::" + UTEXApplication.getLoginUser());
        if (UTEXApplication.getLoginUser() != null) {
            Log.e("Demon", String.valueOf(iTradPresenter.getStatus()));
            switch (iTradPresenter.getStatus()) {
                case 1:
                    tvTradHorizontalBtn.setText(Utils.getResourceString(R.string.sell));
                    tvTradHorizontalBtn.setBackgroundResource(R.drawable.trad_sell_btn_select);
                    iTradPresenter.setStatus(1);
                    iTradPresenter.getAssetList();

                    configBuilder.secondTrackColor(Utils.getResourceColor(getContext(), R.color.fee6a5e));
                    configBuilder.build();
                    bsbTradProgress.setBubbleColor(Utils.getResourceColor(getContext(), R.color.fee6a5e));
                    bsbTradProgress.setSecondTrackColor(Utils.getResourceColor(getContext(), R.color.fee6a5e));

                    tvTradAmountCoinName.setText(currTickerDo.getCoin_market_code().split("_")[0].toUpperCase());
                    tvTradAmountCoinName.setTextColor(getResources().getColor(R.color.bafb3b0_42ffffff));
                    break;
                case 2:
                    tvTradHorizontalBtn.setText(Utils.getResourceString(R.string.buy));
                    tvTradHorizontalBtn.setBackgroundResource(R.drawable.trad_buy_btn_select);
                    iTradPresenter.setStatus(2);
                    iTradPresenter.getAssetList();

                    configBuilder.secondTrackColor(Utils.getResourceColor(getContext(), R.color.f50b577));
                    configBuilder.build();
                    bsbTradProgress.setBubbleColor(Utils.getResourceColor(getContext(), R.color.f50b577));
                    bsbTradProgress.setSecondTrackColor(Utils.getResourceColor(getContext(), R.color.f50b577));

                    switch (iTradPresenter.getDirection()) {
                        case 0:
                            //限价
                            tvTradAmountCoinName.setText(currTickerDo.getCoin_market_code().split("_")[0].toUpperCase());
                            tvTradAmountCoinName.setTextColor(getResources().getColor(R.color.bafb3b0_42ffffff));
                            break;
                        case 1:
                            //市价
                            tvTradAmountCoinName.setText(currTickerDo.getCoin_market_code().split("_")[1].toUpperCase());
                            tvTradAmountCoinName.setTextColor(getResources().getColor(R.color.green));
                            break;
                    }
                    break;

            }
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isCurrPage = true;
            resetPage();
        } else {
            isCurrPage = false;
//            try {
//                if (isBindConn) {
//                    getContext().unbindService(conn);
//                }
//
//            } catch (Exception e) {
//                Log.e("TAG", e.getMessage());
//            }
        }
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerTradComponent
                .builder()
                .appComponent(appComponent)
                .tradModule(new TradModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.iv_trad_market_list, R.id.tv_trad_horizontal_btn, R.id.tv_trad_all_order,
            R.id.iv_trad_optional, R.id.iv_trad_collapse, R.id.iv_trad_market_detail, R.id.tv_trad_coin_name,
            R.id.tv_trad_horizonntal_depth_sel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_trad_coin_name:
            case R.id.iv_trad_market_list:
                //跳出左侧菜单
                if (fragments == null) {
                    return;
                }

                MobclickAgent.onEvent(getContext(), UMConstants.TRADE_SHOW_ALL_COIN_RANG_AND_LIST_BTN);

                handler.sendEmptyMessage(2);
                for (BaseFragment baseFragment : fragments) {
                    MarketFragment marketFragment = (MarketFragment) baseFragment;
                    marketFragment.update();
                }
                dlTradParent.openDrawer(Gravity.START);
                break;
            case R.id.tv_trad_horizontal_btn:
                if (UTEXApplication.getLoginUser() != null) {
                    //已登陆 限价或者市价交易，买入还是卖出
                    if (iTradPresenter.getDirection() == 0) {
                        //限价
                        if (ssTradHorizontalAddSub.getGoodsNumber() == 0) {
                            Utils.showMessage(Utils.getResourceString(R.string.jia_ge_bu_de_wei_kong));
                            return;
                        }
                    }

                    if (TextUtils.isEmpty(etTradAmount.getText()) ||
                            Double.parseDouble(etTradAmount.getText().toString()) == 0) {
                        Utils.showMessage(Utils.getResourceString(R.string.shu_liang_bu_de_wei_kong));
                        return;
                    }

                    if (iTradPresenter.getStatus() == 2 && iTradPresenter.getDirection() == 1) {//买入且市价
                        DialogUtils.buyDialog(getContext(), 0, "", getString(R.string.shi_jia_buy_tip), new DialogUtils.OnResultListener() {
                            @Override
                            public void onOK() {
                                iTradPresenter.submitTrad(currTickerDo.getCoin_market_code(), etTradAmount.getText().toString(), BigDecimal.valueOf(ssTradHorizontalAddSub.getGoodsNumber()).toPlainString());
                            }
                        });
                    } else if (iTradPresenter.getStatus() == 2 && iTradPresenter.getDirection() == 0) {//买入且限价
                        double marketPrice = "".equals(SharedPreferencesUtil.getString(FiledConstants.GZH_MARKET_PRICE, "")) ? 0 : Double.parseDouble(SharedPreferencesUtil.getString(FiledConstants.GZH_MARKET_PRICE, ""));
                        if (marketPrice != 0 && Double.parseDouble(BigDecimal.valueOf(ssTradHorizontalAddSub.getGoodsNumber()).toPlainString()) > marketPrice
                                && ((Double.parseDouble(BigDecimal.valueOf(ssTradHorizontalAddSub.getGoodsNumber()).toPlainString()) - marketPrice) / marketPrice) > 0.05) {
                            DialogUtils.buyDialog(getContext(), 1, BigDecimal.valueOf(ssTradHorizontalAddSub.getGoodsNumber()).toPlainString(), getString(R.string.xian_jia_buy_tip), new DialogUtils.OnResultListener() {
                                @Override
                                public void onOK() {
                                    iTradPresenter.submitTrad(currTickerDo.getCoin_market_code(), etTradAmount.getText().toString(), BigDecimal.valueOf(ssTradHorizontalAddSub.getGoodsNumber()).toPlainString());
                                }
                            });
                        } else
                            iTradPresenter.submitTrad(currTickerDo.getCoin_market_code(), etTradAmount.getText().toString(), BigDecimal.valueOf(ssTradHorizontalAddSub.getGoodsNumber()).toPlainString());
                    } else {
                        iTradPresenter.submitTrad(currTickerDo.getCoin_market_code(), etTradAmount.getText().toString(), BigDecimal.valueOf(ssTradHorizontalAddSub.getGoodsNumber()).toPlainString());
                    }
                } else {
                    //未登陆
                    startLogin();
                }
                break;
            case R.id.tv_trad_all_order:
                //跳转全部订单
                if (UTEXApplication.getLoginUser() != null) {
                    Intent intent = new Intent(getContext(), OrderActivity.class);
                    startActivity(intent);
                } else {
                    //未登陆
                    startLogin();
                }
                break;
            case R.id.iv_trad_optional:
                //添加自选
                MobclickAgent.onEvent(getContext(), UMConstants.TRADE_COLOCET_BTN);

                if (UTEXApplication.getLoginUser() != null) {
                    if (currTickerDo.getOptional() == 1) {
                        iTradPresenter.cannelOptional(currTickerDo.getCoin_market_code());
                    } else {
                        iTradPresenter.addOptional(currTickerDo.getCoin_market_code(), currTickerDo.getId());
                    }
                } else {
                    if (currTickerDo.getOptional() == 1) {
                        cannelOptionalSuccess();
                    } else {
                        addOptionalSuccess();
                    }
//                    startLogin();
                }
                break;
            case R.id.iv_trad_collapse:
                dlTradParent.closeDrawer(Gravity.START);
                break;
            case R.id.iv_trad_market_detail:
                MobclickAgent.onEvent(getContext(), UMConstants.TRADE_JUMP_KLINE_BTN);

                Intent marketDetail = new Intent(getContext(), MarketDetailActivity.class);
                marketDetail.putExtra(Constants.COIN_CODE, currTickerDo.getCoin_market_code());
                view.getContext().startActivity(marketDetail);
                break;
            case R.id.tv_trad_horizonntal_depth_sel:
                MobclickAgent.onEvent(getContext(), UMConstants.TRADE_HANDICAP_DEPTH_SWITCH);
                iTradPresenter.jumpDepthSelPopwindow(R.layout.fragment_trad, currTickerDo.getDepth_merge());
                break;
        }
    }

    private void startLogin() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivityForResult(intent, 301);
        MobclickAgent.onEvent(getContext(), UMConstants.NOLOGIN_PUSH_LOGIN_PAGE_ACTION);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 301 && resultCode == 200) {
            resetPage();
        }
    }

    private void setEtCap() {
        try {
            switch (iTradPresenter.getStatus()) {
                case 1:
                    //卖
                    List<List<String>> bids = depth.getBids();
                    ssTradHorizontalAddSub.setGoodsNumber(Double.parseDouble(bids.get(0).get(0)));
                    break;
                case 2:
                    //买
                    List<List<String>> asks = depth.getAsks();
                    ssTradHorizontalAddSub.setGoodsNumber(Double.parseDouble(asks.get(0).get(0)));
                    last = asks.get(0).get(0);
                    break;
            }
        } catch (Exception e) {
            ssTradHorizontalAddSub.setGoodsNumber(0);
        }

        try {
            tvTradTotalPrice.setText(Utils.double2ToString(Double.parseDouble(etTradAmount.getText().toString()) * ssTradHorizontalAddSub.getGoodsNumber()) + currTickerDo.getCoin_market_code().split("_")[1].toUpperCase());
        } catch (Exception e) {
            tvTradTotalPrice.setText("0");
        }
//        etTradAmount.setText("0");
    }

    @Override
    public void onReceive(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        Log.e("TAG", "websocket:" + json);
        String channel = jsonObject.getString("channel");
        String method = jsonObject.getString("method");
        String result = jsonObject.getString("result");
        if ("user.order".equals(channel)) {
            //这是当前订单
            WebsocketEntrustVo websocketEntrustVo = JSON.parseObject(json, WebsocketEntrustVo.class);
            Log.e("TAG", "order:" + json);

            if (websocketEntrustVo.getPayload().getState() == 3 || websocketEntrustVo.getPayload().getState() == 4) {
                return;
            } else {
                entrustMap.put(websocketEntrustVo.getPayload().getUser_order_id(), websocketEntrustVo.getPayload());
            }

            List<Entrust.DataBean> list = Utils.mapTransitionList(entrustMap);
            Collections.sort(list, (t1, t2) -> {
                if (t1 == null) {
                    return -1;
                }
                if (t2 == null) {
                    return 1;
                }

                if (t1.getUpdate_time() > t2.getUpdate_time()) {
                    return -1;
                } else {
                    return 1;
                }
            });

            tradEntrustAdapter.setData(list);
            if (tradEntrustAdapter.getItemCount() > 0) {
                rlTradEmpty.setVisibility(View.GONE);
            } else {
                rlTradEmpty.setVisibility(View.VISIBLE);
            }
        } else if ("today.update".equals(method)) {
            //今
            JSONArray params = jsonObject.getJSONArray("params");
            if (params != null && params.size() > 0) {
                JSONObject object = params.getJSONObject(1);
                TickerData tickerData = JSON.parseObject(JSON.toJSONString(object), TickerData.class);
                currTickerDo.setResultBean(tickerData);

                tvTradCoinName.setText(currTickerDo.getCoin_market_code().toUpperCase().replace("_", "/"));
                double v = (Double.parseDouble(tickerData.getLast()) - Double.parseDouble(tickerData.getOpen())) / Double.parseDouble(tickerData.getOpen());
                if (Double.parseDouble(tickerData.getOpen()) == 0) {
                    v = 0;
                }
                DecimalFormat df = new DecimalFormat("######0.00");
                if (v > 0) {
                    tvTradRate.setTextColor(Utils.getResourceColor(getContext(), R.color.f50b577));
                    tvTradRate.setText("+" + df.format(v * 100) + "%");
                    tvTradHorizonntalPrice.setTextColor(Utils.getResourceColor(getContext(), R.color.f50b577));
                } else if (v == 0) {
                    tvTradRate.setTextColor(Utils.getResourceColor(getContext(), R.color.bafb3b0_42ffffff));
                    tvTradRate.setText("+0.00%");
                } else {
                    tvTradRate.setTextColor(Utils.getResourceColor(getContext(), R.color.fee6a5e));
                    tvTradHorizonntalPrice.setTextColor(Utils.getResourceColor(getContext(), R.color.fee6a5e));
                    tvTradRate.setText(df.format(v * 100) + "%");
                }
                Utils.setMonospaceFont(tvTradRate);
                Utils.setMonospaceFont(tvTradCoinName);

                tvTradHorizonntalPrice.setText(Utils.getScientificCountingMethodAfterData(Double.valueOf(tickerData.getLast()), Utils.getTickerShowScale(currTickerDo)));
                tvTradHorizonntalPlatform.setVisibility(View.GONE);
            }

//            HourData hourData = JSON.parseObject(json, HourData.class);
//            TickerData resultBean = hourData.getResult();
        } else if ("depth.update".equals(method)) {
            //买卖盘
            JSONArray params = jsonObject.getJSONArray("params");
            Boolean aBoolean = params.getBoolean(0);
            Depth depth = JSON.parseObject(JSON.toJSONString(params.getJSONObject(1)), Depth.class);

            if (marketHandicapAskAdapter == null) {
                return;
            }

            if (aBoolean) {
                //全量推送
                this.depth = depth;
                if (depth != null) {
                    if (depth.getAsks() != null) {
//                        Collections.reverse(depth.getAsks());
                        marketHandicapAskAdapter.setData(depth.getAsks());
                    }
                    if (depth.getBids() != null) {
                        marketHandicapBidAdapter.setData(depth.getBids());
                    }
                }
                if (isFirst) {
                    isFirst = false;
                    setEtCap();
                }
            } else {
//              增量推送时，每一项的数量如果是正数，需要用新值覆盖旧值，如果是0，表示删除这一项
                if (depth != null && this.depth != null) {
                    List<List<String>> asks = depth.getAsks();
                    List<List<String>> bids = depth.getBids();
                    this.depth.setAsks(calculationDepth(2, asks));
                    this.depth.setBids(calculationDepth(1, bids));
                }

            }

            if (this.depth != null) {
                marketHandicapAskAdapter.setData(this.depth.getAsks());
                marketHandicapBidAdapter.setData(this.depth.getBids());
            }
        } else if ("pong".equals(result)) {
            return;
        } else if ("deals.update".equals(method)) {
            //成交
            JSONArray data = jsonObject.getJSONArray("params");
            Log.e("TAG", String.valueOf(data));
            JSONArray jsonArray = data.getJSONArray(1);

            if (tradeVols == null) {
                tradeVols = new ArrayList<>();
            }

            for (int i = 0; i < jsonArray.size(); i++) {
                VolVo vol = new VolVo();
                vol.setId(jsonArray.getJSONObject(i).getString("id"));
                vol.setAmount(jsonArray.getJSONObject(i).getString("amount"));
                vol.setPrice(jsonArray.getJSONObject(i).getString("price"));
                vol.setTime(jsonArray.getJSONObject(i).getString("time"));
                vol.setType(jsonArray.getJSONObject(i).getString("type"));

                if (tradeVolAdapter.getItemCount() == 0) {
                    tradeVols.add(vol);
                } else {
                    tradeVols.add(0, vol);
                }

            }
            if (tradeVols.size() > 12) {
                tradeVols = tradeVols.subList(0, 12);
            }

            if (tradeVolAdapter == null) {
                return;
            }

            tradeVolAdapter.setData(tradeVols);
        } else {
            try {
                JSONObject coin = JSON.parseObject(json, JSONObject.class);
                Integer id = coin.getInteger("id");

                if (id != MarketSocketPackUtils.DEPTH_SUBSCRIBE_ID && id != MarketSocketPackUtils.TODAY_SUBSCRIBE_ID) {
                    TickerData tickerData = JSON.parseObject(JSON.toJSONString(coin.getJSONObject("result")), TickerData.class);
                    for (BaseFragment baseFragment : fragments) {
                        MarketFragment marketFragment = (MarketFragment) baseFragment;
                        List<TickerDo> tickerDos = marketFragment.getTickerDos();
                        for (TickerDo tickerDo : tickerDos) {
                            if (id == tickerDo.getId().intValue()) {
                                tickerDo.setResultBean(tickerData);
                                break;
                            }
                        }
                    }
                }

            } catch (Exception e) {
                Log.e("TAG", json);
                e.printStackTrace();
            }
        }
    }

    /**
     * 1 买
     * 2 卖
     */
    private List<List<String>> calculationDepth(int type, List<List<String>> list) {
        List<List<String>> oldList = null;
        if (depth == null) {
            return null;
        }

        switch (type) {
            case 1:
                oldList = this.depth.getBids();
                break;
            case 2:
                oldList = this.depth.getAsks();
                break;
        }
        if (list != null && list.size() > 0) {
            //卖单增量推有数据
            for (List<String> strings : list) {
                if (oldList != null) {
                    for (List<String> a : oldList) {
                        String s = strings.get(0);
                        String s1 = a.get(0);
                        if (s.equals(s1)) {
                            //相等,赋值
                            oldList.remove(a);
                            break;
                        }
                    }
                }

                try {
                    if (Double.parseDouble(strings.get(1)) > 0) {
                        oldList.add(strings);
                    }
                } catch (Exception e) {
                    Log.e("TAG", e.getMessage());
                }
            }

            switch (type) {
                case 1:
                    //进行排序,哼，真麻烦
                    Collections.sort(oldList, (t1, t2) -> {
                        if (t1 == null) {
                            return 1;
                        }

                        if (t2 == null) {
                            return -1;
                        }

                        if (Double.parseDouble(t1.get(0)) > Double.parseDouble(t2.get(0))) {
                            return -1;
                        } else {
                            return 1;
                        }
                    });
                    break;
                case 2:
                    Collections.sort(oldList, (t1, t2) -> {
                        if (t1 == null) {
                            return -1;
                        }

                        if (t2 == null) {
                            return 1;
                        }

                        if (Double.parseDouble(t1.get(0)) <= Double.parseDouble(t2.get(0))) {
                            return -1;
                        } else {
                            return 1;
                        }
                    });
                    break;
            }


            if (oldList.size() > 5) {
                return oldList.subList(0, 5);
            }
        }
        return oldList;
    }

    @Override
    public void setCap() {
        marketHandicapAskAdapter = new MarketHandicapAdapter(2, currTickerDo);
        rvTardSellHandicap.setAdapter(marketHandicapAskAdapter);
        rvTardSellHandicap.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        marketHandicapAskAdapter.setHandicapClickListener(s -> {
            //卖
            ssTradHorizontalAddSub.setGoodsNumber(Double.parseDouble(s));

        });


        marketHandicapBidAdapter = new MarketHandicapAdapter(3, currTickerDo);
        rvTardBuyHandicap.setAdapter(marketHandicapBidAdapter);
        rvTardBuyHandicap.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        marketHandicapBidAdapter.setHandicapClickListener(s -> {
            //买
            ssTradHorizontalAddSub.setGoodsNumber(Double.parseDouble(s));
        });
    }

    @Override
    public void setCoinList(List<BaseFragment> fragments, ArrayList<String> mTitleDataList) {
        List<String> needS;
        List<BaseFragment> baseS;
        if (mTitleDataList.size() == 3) {
            needS = new ArrayList<>();
            needS.add(mTitleDataList.get(0));
            needS.add("usdt");
            needS.add("gzh");
        } else {
            needS = mTitleDataList;
        }

        if (mTitleDataList.get(1).equals("usdt")) {
            baseS = new ArrayList<>();
            baseS.add(fragments.get(0));
            baseS.add(fragments.get(1));
            baseS.add(fragments.get(2));
        } else {
            baseS = new ArrayList<>();
            baseS.add(fragments.get(0));
            baseS.add(fragments.get(2));
            baseS.add(fragments.get(1));
        }
//        this.mTitleDataList = mTitleDataList;
        this.mTitleDataList = needS;
        if (this.fragments == null) {
            this.fragments = baseS;
        } else {
            this.fragments.clear();
            this.fragments.addAll(baseS);
        }
//        if (this.fragments == null) {
//            this.fragments = fragments;
//        } else {
//            this.fragments.clear();
//            this.fragments.addAll(fragments);
//        }

        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return TradFragment.this.mTitleDataList == null ? 0 : TradFragment.this.mTitleDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Utils.getResourceColor(getContext(), R.color.b999999_66ffffff));
                colorTransitionPagerTitleView.setSelectedColor(Utils.getResourceColor(getContext(), R.color.f398155));
                colorTransitionPagerTitleView.setText(TradFragment.this.mTitleDataList.get(index).toUpperCase());
                Utils.setMonospaceFont(colorTransitionPagerTitleView);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        vpTardLeftPage.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(new Integer[]{Utils.getResourceColor(getContext(), R.color.f398155)});
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setLineHeight(BubbleUtils.dp2px(1.8f));
                return indicator;
            }
        });

        List<TickerDo> tickerByMarket = ExTickerDao.getTickerByMarket(Utils.getResourceString(R.string.zi_xuan));
        if (tickerByMarket == null || tickerByMarket.size() == 0) {
            new Handler()
                    .postDelayed(() -> vpTardLeftPage.setCurrentItem(1), 100);
        }
        miTradLeftTab.setNavigator(commonNavigator);
        ViewPagerHelper.bind(miTradLeftTab, vpTardLeftPage);
        vpTardLeftPage.setOffscreenPageLimit(3);
        vpTardLeftPage.setCurrentItem(0);
        vpTardLeftPage.setAdapter(new MarketDetailPageAdapter(getChildFragmentManager(), baseS));
//        vpTardLeftPage.setAdapter(new MarketDetailPageAdapter(getChildFragmentManager(), fragments));
    }

    @Override
    public void setCurEntrust(Map<String, Entrust.DataBean> dataMap, final List<Entrust.DataBean> data) {
        this.entrustMap = dataMap;
        tradEntrustAdapter = new TradEntrustAdapter();
        rvTradCurEntrustList.setAdapter(tradEntrustAdapter);
        tradEntrustAdapter.setData(data);
        rvTradCurEntrustList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        tradEntrustAdapter.setTradEntrustListener((dataBean, position) -> {
            //取消订单
            currCannelOrderPosition = position;
            iTradPresenter.cannelOrder(dataBean);
        });

        if (entrustMap == null || entrustMap.size() == 0) {
            rlTradEmpty.setVisibility(View.VISIBLE);
            rvTradCurEntrustList.setVisibility(View.GONE);
        } else {
            rvTradCurEntrustList.setVisibility(View.VISIBLE);
            rlTradEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public String getCurrtradTicker() {
        return currTickerDo.getCoin_market_code();
    }

    @Override
    public void setAsset(double available_fund, String coinCode) {
        this.availableFund = new BigDecimal(String.valueOf(available_fund));
        Log.e("liuhao", String.valueOf(available_fund));
        tvTradHorizontalAvailableFund.setVisibility(View.VISIBLE);

        int scale = Integer.parseInt(currTickerDo.getMoney_decimal()) - Integer.parseInt(currTickerDo.getAmount_decimal());

        String str;
        if ("gzh".equals(coinCode.toLowerCase())) {
            str = Utils.getStringFormatAmount(availableFund.doubleValue(), true);
        } else {
            str = Utils.getStringFormatAmount(availableFund.doubleValue(), false);
        }
        tvTradHorizontalAvailableFund.setText(Utils.getResourceString(R.string.ke_yong) + " " + Utils.eliminateZero(Double.parseDouble(str)) + coinCode.toUpperCase());

        BubbleConfigBuilder configBuilder = bsbTradProgress.getConfigBuilder();

        switch (iTradPresenter.getDirection()) {
            case 0:
                //限价
                switch (iTradPresenter.getStatus()) {
                    case 1:
                        //卖
//                float v = Float.parseFloat(Utils.getScientificCountingMethodAfterData(this.availableFund.floatValue(), scale));
                        configBuilder.max(availableFund.doubleValue());
                        break;
                    case 2:
                        //买, 可用除以当前价格
                        if (ssTradHorizontalAddSub.getGoodsNumber() == 0) {
                            configBuilder.max(0);
                        } else {
                            String scientificCountingMethodAfterData2 = Utils.getScientificCountingMethodAfterData(ArithmeticUtil.div(available_fund, ssTradHorizontalAddSub.getGoodsNumber()), Integer.valueOf(currTickerDo.getAmount_decimal()));
                            configBuilder.max(Double.parseDouble(scientificCountingMethodAfterData2));
                        }
                        break;
                }
                break;
            case 1:
                //市价
                configBuilder.max(availableFund.doubleValue());
                break;
        }

        if (configBuilder.getMax() == 0) {
            isNumberChange = false;
            configBuilder.sectionTextColor(Utils.getResourceColor(getContext(), R.color.transparent));
        } else {
            isNumberChange = true;
            configBuilder.sectionTextColor(Utils.getResourceColor(getContext(), R.color.bafb3b0_42ffffff));
        }

        configBuilder.progress(0);
        configBuilder.build();

    }

    @Override
    public void submitSuccess() {
        iTradPresenter.getAssetList();
        iTradPresenter.getCurrMarketEntrust(currTickerDo.getCoin_market_code());
        Utils.showMessage(Utils.getResourceString(R.string.wei_tuo_cheng_gong));
        if (tradEntrustAdapter.getItemCount() > 0) {
            rlTradEmpty.setVisibility(View.GONE);
        } else {
            rlTradEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void cannelOrderSuccess() {
        if (tradEntrustAdapter == null) {
            return;
        }
        entrustMap.remove(tradEntrustAdapter.getData().get(currCannelOrderPosition).getUser_order_id());
        tradEntrustAdapter.getData().remove(currCannelOrderPosition);
        tradEntrustAdapter.notifyDataSetChanged();
        Utils.showMessage(Utils.getResourceString(R.string.che_xiao_cheng_gong));

        if (tradEntrustAdapter.getItemCount() > 0) {
            rlTradEmpty.setVisibility(View.GONE);
        } else {
            rlTradEmpty.setVisibility(View.VISIBLE);
        }

        iTradPresenter.getAssetList();
    }

    @Override
    public void addOptionalSuccess() {
        ExTickerDao.changeOption(currTickerDo.getCoin_market_code());
        ivTradOptional.setImageResource(R.drawable.transaction_icon_collect_sel);
        updateOption();
    }

    @Override
    public void cannelOptionalSuccess() {
        //修改本地的自选
        ExTickerDao.changeOption(currTickerDo.getCoin_market_code());
        ivTradOptional.setImageResource(R.drawable.transaction_icon_collect);
        updateOption();
    }

    private void updateOption() {
        if (fragments == null) {
            return;
        }
        for (BaseFragment baseFragment : fragments) {
            MarketFragment marketFragment = (MarketFragment) baseFragment;
            marketFragment.updateCoin();
        }
    }

    @Override
    public void updateCoinMarketCode(String coinCode) {
        //更新交易币种
        SharedPreferencesUtil.putString(Constants.TRAD_TICKER, coinCode);
        handler.postDelayed(() -> resetPage(), 300);
        dlTradParent.closeDrawer(Gravity.START);
        etTradAmount.setText("");
    }

    @Override
    public void setMerageLimit(String s) {
        //设置档位
        depthMerge = s;
        handler.postDelayed(() -> iTradPresenter.getCurrPageMarketData(depthMerge, currTickerDo), 80);
    }

    /**
     * 设置方向
     *
     * @param status
     */
    public void setDirection(Integer status) {
        switch (status) {
            case 1:
                //买
                rgTradHorizontalDirection.check(R.id.rg_trad_horizontal_direction_buy);
                break;
            case 2:
                //卖
                rgTradHorizontalDirection.check(R.id.rg_trad_horizontal_direction_sell);
                break;
        }
//        resetPage();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        //刷新
        refreshList.finishRefresh();

        if (UTEXApplication.getLoginUser() != null) {
            //获取订单数据
            iTradPresenter.getCurrMarketEntrust(currTickerDo.getCoin_market_code());
        }

        handler.removeCallbacksAndMessages(null);
        //先发送词，然后进行socket重连
        handler.sendEmptyMessage(2);
        //后续才是真正发送
//        handler.sendEmptyMessageDelayed(0, 900);
        handler.postDelayed(() -> iTradPresenter.getCurrPageMarketData(depthMerge, currTickerDo), 900);
        handler.postDelayed(() -> iTradPresenter.getAllCoinData(fragments), 900);
    }
}

