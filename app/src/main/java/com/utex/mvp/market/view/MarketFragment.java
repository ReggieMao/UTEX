package com.utex.mvp.market.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.utex.R;
import com.utex.base.BaseFragment;
import com.utex.bean.TickerDo;
import com.utex.common.FiledConstants;
import com.utex.core.AppComponent;
import com.utex.db.ExTickerDao;
import com.utex.mvp.home.bean.TickerData;
import com.utex.mvp.market.dagger2.DaggerMarketComponent;
import com.utex.mvp.market.dagger2.MarketModule;
import com.utex.mvp.market.presenter.IMarketPresenter;
import com.utex.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Demon on 2018/5/18.
 */
public class MarketFragment extends BaseFragment implements IMarketView {

    @Inject
    IMarketPresenter iMarketPresenter;

    @BindView(R.id.rv_market_list)
    RecyclerView rvMarketList;

    @BindView(R.id.tv_market_head_vol)
    TextView tvMarketHeadVol;

    @BindView(R.id.tv_market_head_price)
    TextView tvMarketHeadPrice;

    @BindView(R.id.tv_market_head_rate)
    TextView tvMarketHeadRate;

    @BindView(R.id.rl_market_head_parent)
    RelativeLayout rlMarketHeadParent;

    @BindView(R.id.ll_page_empty)
    LinearLayout ivPageEmpty;

    /**
     * 0 默认
     * 1 成交量升序
     * 2 成交量降序
     * 3 价格升序
     * 4 价格降序
     * 5 涨跌幅升序
     * 6 涨跌幅降序
     */
    private int currStatus;

    /**
     * 交易区
     */
    private String market;

    private MarketAdapter marketAdapter;

    private List<TickerDo> tickerDos;

    /**
     * 0 跳入详情
     * 1 返回点击
     */
    private int type;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.fragment_market, container, false);
        ButterKnife.bind(this, inflate);

        Bundle arguments = getArguments();
        CharSequence charSequence = arguments.getCharSequence(FiledConstants.MARKET_NAME);
        type = arguments.getInt(FiledConstants.TYPE, 0);
        if (charSequence != null) {
            market = String.valueOf(charSequence);
        }
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!TextUtils.isEmpty(market)) {
            //查询 交易去下币种
            tickerDos = ExTickerDao.getTickerByMarket(market);
            checkListSize();

            marketAdapter = new MarketAdapter(type, market);


            rvMarketList.setAdapter(marketAdapter);
            rvMarketList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            marketAdapter.setData(tickerDos);
            marketAdapter.setItemOnclickListener(coinCode -> {
                if (marketLisntener != null) {
                    marketLisntener.onclick(coinCode);
                }
            });

            switch (type) {
                case 0:
                    rlMarketHeadParent.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    rlMarketHeadParent.setVisibility(View.GONE);
                    break;
            }
        } else {
            ivPageEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void checkListSize() {
        if (Utils.checkList(tickerDos) != 0) {
            ivPageEmpty.setVisibility(View.GONE);
        } else {
            rvMarketList.setVisibility(View.GONE);
            ivPageEmpty.setVisibility(View.VISIBLE);
        }
    }

    private MarketLisntener marketLisntener;

    public void setMarketLisntener(MarketLisntener marketLisntener) {
        this.marketLisntener = marketLisntener;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCoin();
    }

    /**
     * 更新币种
     */
    public void updateCoin() {
        if (!TextUtils.isEmpty(market)) {
            //查询 交易去下币种
            if (tickerDos == null) {
                tickerDos = new ArrayList<>();
            }
            tickerDos.clear();
            tickerDos.addAll(ExTickerDao.getTickerByMarket(market));

            marketAdapter.setData(tickerDos);

            if (marketAdapter.getItemCount() != 0) {
                ivPageEmpty.setVisibility(View.GONE);
                rvMarketList.setVisibility(View.VISIBLE);
            } else {
                rvMarketList.setVisibility(View.GONE);
                ivPageEmpty.setVisibility(View.VISIBLE);
            }
        }
    }


    public interface MarketLisntener {
        void onclick(String coinCode);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMarketComponent
                .builder()
                .appComponent(appComponent)
                .marketModule(new MarketModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.tv_market_head_vol, R.id.tv_market_head_price, R.id.tv_market_head_rate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_market_head_rate:
                // 5  6
                switchSortStatus(5, 6);
                break;
            case R.id.tv_market_head_price:
                // 3  4
                switchSortStatus(3, 4);
                break;
            case R.id.tv_market_head_vol:
                // 1  2
                switchSortStatus(1, 2);
                break;
        }
    }

    /**
     * 切换排序状态
     */
    private void switchSortStatus(int asc, int desc) {
        if (currStatus == asc) {
            currStatus = desc;
        } else {
            currStatus = asc;
        }

        setAllSortN();
        iMarketPresenter.sorttickerList(tickerDos, currStatus);
        switch (currStatus) {
            case 1:
                Drawable volUp = getContext().getResources().getDrawable(R.drawable.home_icon_sorting_up);
                volUp.setBounds(0, 0, volUp.getMinimumWidth(), volUp.getMinimumHeight());
                tvMarketHeadVol.setCompoundDrawables(null, null, volUp, null);
                break;
            case 2:
                Drawable volDown = getContext().getResources().getDrawable(R.drawable.home_icon_sorting_down);
                volDown.setBounds(0, 0, volDown.getMinimumWidth(), volDown.getMinimumHeight());

                tvMarketHeadVol.setCompoundDrawables(null, null, volDown, null);
                break;
            case 3:
                Drawable priceUp = getContext().getResources().getDrawable(R.drawable.home_icon_sorting_up);
                priceUp.setBounds(0, 0, priceUp.getMinimumWidth(), priceUp.getMinimumHeight());
                tvMarketHeadPrice.setCompoundDrawables(null, null, priceUp, null);
                break;
            case 4:
                Drawable priceDown = getContext().getResources().getDrawable(R.drawable.home_icon_sorting_down);
                priceDown.setBounds(0, 0, priceDown.getMinimumWidth(), priceDown.getMinimumHeight());
                tvMarketHeadPrice.setCompoundDrawables(null, null, priceDown, null);
                break;
            case 5:
                Drawable rateUp = getContext().getResources().getDrawable(R.drawable.home_icon_sorting_up);
                rateUp.setBounds(0, 0, rateUp.getMinimumWidth(), rateUp.getMinimumHeight());
                tvMarketHeadRate.setCompoundDrawables(null, null, rateUp, null);
                break;
            case 6:
                Drawable rateDown = getContext().getResources().getDrawable(R.drawable.home_icon_sorting_down);
                rateDown.setBounds(0, 0, rateDown.getMinimumWidth(), rateDown.getMinimumHeight());
                tvMarketHeadRate.setCompoundDrawables(null, null, rateDown, null);
                break;
        }

        marketAdapter.notifyDataSetChanged();
    }

    public List<TickerDo> getTickerDos() {
        return tickerDos;
    }

    /**
     * 设置所有排序为n
     */
    private void setAllSortN() {
        Drawable drawable = getContext().getResources().getDrawable(R.drawable.home_icon_sorting_n);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvMarketHeadVol.setCompoundDrawables(null, null, drawable, null);
        tvMarketHeadPrice.setCompoundDrawables(null, null, drawable, null);
        tvMarketHeadRate.setCompoundDrawables(null, null, drawable, null);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && Utils.getResourceString(R.string.zi_xuan).equals(market)) {
            Log.e("TAG", "1111");
            updateCoin();
        }
    }

    /**
     * 获取到的数据
     *
     * @param json
     */
    public void onReceive(String json) {
        if (tickerDos == null) {
            return;
        }
        try {
            JSONObject jsonObject = JSON.parseObject(json, JSONObject.class);
            JSONArray params = jsonObject.getJSONArray("params");
            if (params != null && params.size() > 0) {
                String coinCode = params.getString(0);
                JSONObject object = params.getJSONObject(1);
                TickerData tickerData = JSON.parseObject(JSON.toJSONString(object), TickerData.class);
                for (TickerDo tickerDo : tickerDos) {
                    if (coinCode.equals(tickerDo.getCoin_market_code())) {
                        tickerDo.setResultBean(tickerData);
                        break;
                    }
                }
            }
            marketAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.e("TAG", json);
            e.printStackTrace();
        }

    }

    public void update() {
        //更新列表
        if (marketAdapter != null) {
            marketAdapter.notifyDataSetChanged();
        }
    }

}
