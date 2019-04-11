package com.utex.mvp.home.view;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.umeng.analytics.MobclickAgent;
import com.utex.R;
import com.utex.base.BaseFragment;
import com.utex.bean.TickerDo;
import com.utex.common.UMConstants;
import com.utex.core.AppComponent;
import com.utex.db.ExTickerDao;
import com.utex.mvp.home.bean.BannerVo;
import com.utex.mvp.home.bean.NoticeVo;
import com.utex.mvp.home.dagger2.DaggerHomeComponent;
import com.utex.mvp.home.dagger2.HomeModule;
import com.utex.mvp.home.presenter.IHomePresenter;
import com.utex.mvp.market.view.MarketFragment;
import com.utex.mvp.search.view.SearchActivity;
import com.utex.recevier.TickerReceiver;
import com.utex.utils.Utils;
import com.utex.widget.smartrefresh.layout.SmartRefreshLayout;
import com.utex.widget.smartrefresh.layout.api.RefreshLayout;
import com.utex.widget.smartrefresh.layout.listener.OnRefreshListener;
import com.utex.widget.viewpager.ADTextView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Demon on 2018/5/17.
 * 首页
 */
public class HomeFragment extends BaseFragment implements IHomeView, AppBarLayout.OnOffsetChangedListener, ImageLoaderInterface<ImageView>, TickerReceiver.TickerListener, OnRefreshListener {

    public static boolean isHomePage = true;

    @BindView(R.id.al_home_appbar)
    AppBarLayout alHomeAppbar;

    @BindView(R.id.tb_title)
    Toolbar tbTitle;

    @BindView(R.id.b_home_banner)
    Banner bHomeBanner;

    @BindView(R.id.ctl_home_ct)
    CollapsingToolbarLayout ctlHomect;

    @BindView(R.id.vp_home_page)
    ViewPager vpHomePage;

    @BindView(R.id.vv_vertical_banner)
    ADTextView vvVerticalBanner;

    @BindView(R.id.rv_home_market_title)
    RecyclerView rvHomeMarketTitle;

    @BindView(R.id.refresh_list)
    SmartRefreshLayout refreshList;

    private HomeMarketTitleAdapter homeMarketTitleAdapter;

    @Inject
    IHomePresenter iHomePresenter;

    public int currPage;

    private TickerReceiver tickerReceiver;

    private List<BaseFragment> fragments;

    private List<NoticeVo.DataBean> data;

    private List<BannerVo.DataBean> bannerData;
    private boolean needSwitch = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tickerReceiver = new TickerReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.ticker");
        getContext().registerReceiver(tickerReceiver, intentFilter);

//        MarketSocketPackUtils.bindHomeReceive();

        tickerReceiver.setTickerListener(this);

        refreshList.setOnRefreshListener(this);

        //请求获取币种
        iHomePresenter.getCoinList();

        alHomeAppbar.addOnOffsetChangedListener(this);

        //获取Banner图
        iHomePresenter.getBanners();

        //获取公告
        iHomePresenter.getNotice();

    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerHomeComponent
                .builder()
                .appComponent(appComponent)
                .homeModule(new HomeModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.iv_home_search, R.id.tv_home_tool_bar_search})
    public void viewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_home_tool_bar_search:
            case R.id.iv_home_search:
                //搜索
                Intent search = new Intent(getContext(), SearchActivity.class);
                getActivity().startActivity(search);
                MobclickAgent.onEvent(getContext(), UMConstants.HOME_SEARCH_BTN);
                break;
        }
    }

    /**
     * toolbar 渐变显示
     */
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        tbTitle.setAlpha(Math.abs(verticalOffset) * 1.0f / (ctlHomect.getHeight() - tbTitle.getHeight()));
    }

    /**
     * 设置banner
     */
    @Override
    public void setBanners(List<BannerVo.DataBean> data) {
        this.bannerData = data;
        //设置banner样式
        bHomeBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        bHomeBanner.setImageLoader(this);
        //设置图片集合
        bHomeBanner.setImages(data);
        //设置banner动画效果
        bHomeBanner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
//        bHomeBanner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        bHomeBanner.isAutoPlay(true);
//        bHomeBanner.setBannerTitles(titles);
        //设置轮播时间
        bHomeBanner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        bHomeBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        bHomeBanner.start();
    }

    /**
     * 设置 ViewPager页面
     */
    @Override
    public void setPage(final List<BaseFragment> fragments) {
        List<BaseFragment> needS;
        if (needSwitch) {
            needS = new ArrayList<>();
            needS.add(fragments.get(0));
            needS.add(fragments.get(2));
            needS.add(fragments.get(1));
        } else
            needS = fragments;
        this.fragments = needS;
//        this.fragments = fragments;
        vpHomePage.setAdapter(new MarketPageAdapter(getFragmentManager(), needS));
//        vpHomePage.setAdapter(new MarketPageAdapter(getFragmentManager(), fragments));

        vpHomePage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currPage = position;
                homeMarketTitleAdapter.setCurrItem(position);
                iHomePresenter.getMarketData(currPage);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        List<TickerDo> tickerByMarket = ExTickerDao.getTickerByMarket(Utils.getResourceString(R.string.zi_xuan));
        if (tickerByMarket == null || tickerByMarket.size() == 0) {
            new Handler().postDelayed(() -> vpHomePage.setCurrentItem(1), 100);

        }
    }


    /**
     * 设置title
     */
    @Override
    public void setMarketTitle(ArrayList<String> market) {
        List<String> needS;
        if (market.size() == 3) {
            needS = new ArrayList<>();
            needS.add(market.get(0));
            needS.add("usdt");
            needS.add("gzh");
            if (market.get(1).equals("usdt"))
                needSwitch = false;
            else
                needSwitch = true;
        } else {
            needS = market;
        }
        homeMarketTitleAdapter = new HomeMarketTitleAdapter(needS);
//        homeMarketTitleAdapter = new HomeMarketTitleAdapter(market);
        rvHomeMarketTitle.setAdapter(homeMarketTitleAdapter);
        rvHomeMarketTitle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        homeMarketTitleAdapter.setClickListener(vpHomePage::setCurrentItem);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //可见
            updatOptionData();
            new Handler()
                    .postDelayed(() -> {
                        if (iHomePresenter != null) {
                            iHomePresenter.getMarketData(currPage);
                        }
                    }, 500);
        } else {
            //不可见
            if (iHomePresenter != null) {
                iHomePresenter.cannelMarketDataSubscribe();
            }
        }
    }

    private void updatOptionData() {
        if (fragments != null) {
            for (BaseFragment baseFragment : fragments) {
                MarketFragment marketFragment = (MarketFragment) baseFragment;
                marketFragment.updateCoin();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (tickerReceiver != null) {
            iHomePresenter.cannelMarketDataSubscribe();
            getContext().unregisterReceiver(tickerReceiver);
            tickerReceiver = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isHomePage) {
            updatOptionData();
            iHomePresenter.getMarketData(currPage);
        } else {
            isHomePage = true;
        }
//        vvVerticalBanner.setTipList(data);
    }

    @Override
    public void getNoticeSuccess(final List<NoticeVo.DataBean> data) {
        this.data = data;

        vvVerticalBanner.setInterval(3000);
        vvVerticalBanner.init(data, (textView, index) -> textView.setOnClickListener(v -> {
            //跳转浏览器
            try {
                MobclickAgent.onEvent(getContext(), UMConstants.HOME_NOTICE_CLICK);

                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(data.get(index).getLink());
                Log.d("HomeFragment", "getNoticeSuccess: " + content_url);
                intent.setData(content_url);
                startActivity(intent);
            } catch (Exception e) {
                Log.e("TAG", "错误");
            }
        }));
    }

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        final BannerVo.DataBean object = (BannerVo.DataBean) path;
        Glide.with(context).load(object.getUrl()).into(imageView);
        imageView.setOnClickListener(view -> {
            //跳转浏览器
            try {
                MobclickAgent.onEvent(context, UMConstants.HOME_BANNER_CLICK);
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(object.getLink());
                Log.d("HomeFragment", "displayImage: " + content_url);
                intent.setData(content_url);
                startActivity(intent);
            } catch (Exception e) {
                Log.e("TAG", "错误");
            }
        });
    }

    @Override
    public ImageView createImageView(Context context) {
        ImageView imageView = new ImageView(context);
        return imageView;
    }

    @Override
    public void onReceive(String json) {
        Log.e("KLine", json);
        if (iHomePresenter.getFragments() != null) {
            MarketFragment marketFragment = (MarketFragment) iHomePresenter.getFragments().get(currPage);
            marketFragment.onReceive(json);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        //刷新页面
        refreshList.finishRefresh();

        if (bannerData == null || bannerData.size() == 0) {
            iHomePresenter.getBanners();
            iHomePresenter.getNotice();
        }

        iHomePresenter.getMarketData(currPage);
    }
}
