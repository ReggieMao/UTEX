package com.utex.mvp.order.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.utex.R;
import com.utex.base.BaseActivity;
import com.utex.base.BaseFragment;
import com.utex.common.FiledConstants;
import com.utex.core.AppComponent;
import com.utex.mvp.marketdetail.view.MarketDetailPageAdapter;
import com.utex.utils.Utils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderActivity extends BaseActivity implements ViewPager.OnPageChangeListener {


    @BindView(R.id.mi_order_tab)
    MagicIndicator miOrderTab;

    @BindView(R.id.vp_order_page)
    ViewPager vpOrderPage;

    @BindView(R.id.tv_order_filter)
    TextView tvOrderFilter;

    @BindView(R.id.tv_order_title)
    TextView tvOrderTitle;

    private List<String> mTitleDataList;

    private int currPagePosition;

    private List<BaseFragment> fragments;

    /**
     * 0 不显示
     * 1 显示
     */
    private int currStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);

        tvOrderTitle.setText(Utils.getResourceString(R.string.ding_dan_guan_li));

        mTitleDataList = new ArrayList<>();
        mTitleDataList.add(Utils.getResourceString(R.string.dang_qian_wei_tuo));
        mTitleDataList.add(Utils.getResourceString(R.string.li_shi_wei_tuo));
        mTitleDataList.add(Utils.getResourceString(R.string.wo_de_cheng_jiao));

        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mTitleDataList == null ? 0 : mTitleDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Utils.getResourceColor(OrderActivity.this, R.color.b999999_e6ffffff));
                colorTransitionPagerTitleView.setSelectedColor(Utils.getResourceColor(OrderActivity.this, R.color.f398155));
                colorTransitionPagerTitleView.setText(mTitleDataList.get(index));
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        vpOrderPage.setCurrentItem(index);
                        OrderManageFragment fragment = (OrderManageFragment) fragments.get(currPagePosition);
                        fragment.hideFilterPopuWindow();
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(new Integer[]{Utils.getResourceColor(OrderActivity.this, R.color.f398155)});
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                return indicator;
            }
        });

        miOrderTab.setNavigator(commonNavigator);
        ViewPagerHelper.bind(miOrderTab, vpOrderPage);

        Bundle args = new Bundle();

        fragments = new ArrayList<>();

        OrderManageFragment curr = new OrderManageFragment();
        args.putInt(FiledConstants.ORDER_TYPE, 0);
        curr.setArguments(args);
        fragments.add(curr);

        OrderManageFragment history = new OrderManageFragment();
        args = new Bundle();
        args.putInt(FiledConstants.ORDER_TYPE, 1);
        history.setArguments(args);
        fragments.add(history);

        OrderManageFragment deal = new OrderManageFragment();
        args = new Bundle();
        args.putInt(FiledConstants.ORDER_TYPE, 2);
        deal.setArguments(args);
        fragments.add(deal);

        vpOrderPage.setAdapter(new MarketDetailPageAdapter(getSupportFragmentManager(), fragments));

        vpOrderPage.addOnPageChangeListener(this);

        curr.setPopuWindowListener(new OrderManageFragment.PopuWindowListener() {
            @Override
            public void showPopuWindow() {
                Drawable show = getResources().getDrawable(R.drawable.my_icon_filter_sel);
                show.setBounds(0, 0, show.getMinimumWidth(), show.getMinimumHeight());
                tvOrderFilter.setCompoundDrawables(show, null, null, null);
                tvOrderFilter.setTextColor(Utils.getResourceColor(OrderActivity.this, R.color.f398155));
                currStatus = 1;
            }

            @Override
            public void hidePopuWindow() {
                Drawable hide = getResources().getDrawable(R.drawable.my_icon_filter);
                hide.setBounds(0, 0, hide.getMinimumWidth(), hide.getMinimumHeight());
                tvOrderFilter.setCompoundDrawables(hide, null, null, null);
                tvOrderFilter.setTextColor(Utils.getResourceColor(OrderActivity.this, R.color.b999999_e6ffffff));
                currStatus = 0;
            }
        });
    }

    @OnClick({R.id.tv_order_filter, R.id.ll_order_parent, R.id.vp_order_page, R.id.tb_order_bar, R.id.iv_order_back})
    public void onClick(View view) {
        OrderManageFragment fragment = (OrderManageFragment) fragments.get(currPagePosition);
        switch (view.getId()) {
            case R.id.tv_order_filter:
                //跳出过滤 条件
                switch (currStatus) {
                    case 0:
                        fragment.showFilterPopuWindow();
                        break;
                    case 1:
                        fragment.hideFilterPopuWindow();
                        break;
                }
                break;
            case R.id.tb_order_bar:
                fragment.hideFilterPopuWindow();
                break;
            case R.id.iv_order_back:
                finish();
                break;
        }
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.currPagePosition = position;
        OrderManageFragment fragment = (OrderManageFragment) fragments.get(currPagePosition);
        fragment.setPopuWindowListener(new OrderManageFragment.PopuWindowListener() {
            @Override
            public void showPopuWindow() {
                Drawable show = getResources().getDrawable(R.drawable.my_icon_filter_sel);
                show.setBounds(0, 0, show.getMinimumWidth(), show.getMinimumHeight());
                tvOrderFilter.setCompoundDrawables(show, null, null, null);
                tvOrderFilter.setTextColor(Utils.getResourceColor(OrderActivity.this, R.color.f398155));
                currStatus = 1;
            }

            @Override
            public void hidePopuWindow() {
                Drawable hide = getResources().getDrawable(R.drawable.my_icon_filter);
                hide.setBounds(0, 0, hide.getMinimumWidth(), hide.getMinimumHeight());
                tvOrderFilter.setCompoundDrawables(hide, null, null, null);
                tvOrderFilter.setTextColor(Utils.getResourceColor(OrderActivity.this, R.color.b999999_e6ffffff));
                currStatus = 0;
            }
        });
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
