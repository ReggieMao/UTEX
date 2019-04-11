package com.utex.mvp.marketdetail.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;

import com.utex.R;
import com.utex.mvp.marketdetail.bean.MiTabParamVo;
import com.utex.utils.Utils;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.List;

/**
 * Created by Demon on 2018/6/19.
 */
public class TitleAdapter extends CommonNavigatorAdapter {

    public List<MiTabParamVo> mTitleDataList;

    private ViewPager vpMarketDetailPage;


    public TitleAdapter(List<MiTabParamVo> mTitleDataList, ViewPager vpMarketDetailPage) {
        this.mTitleDataList = mTitleDataList;
        this.vpMarketDetailPage = vpMarketDetailPage;
    }

    @Override
    public int getCount() {
        return mTitleDataList == null ? 0 : mTitleDataList.size();
    }

    @Override
    public IPagerTitleView getTitleView(Context context, final int index) {
        ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);

        float v = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 5, context.getResources().getDisplayMetrics());

        colorTransitionPagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        colorTransitionPagerTitleView.setNormalColor(Utils.getResourceColor(context, R.color.b999999_e6ffffff));
        colorTransitionPagerTitleView.setSelectedColor(Utils.getResourceColor(context, R.color.f398155));
        colorTransitionPagerTitleView.setText("  " + mTitleDataList.get(index).getTitle() + "  ");

        colorTransitionPagerTitleView.setOnClickListener(view -> {
            vpMarketDetailPage.setCurrentItem(index);
            if (titleAdapterListener != null) {
                titleAdapterListener.click(index);
            }
        });

        return colorTransitionPagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        LinePagerIndicator indicator = new LinePagerIndicator(context);
        indicator.setColors(new Integer[]{Utils.getResourceColor(context, R.color.f398155)});
        indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
        return indicator;
    }

    public TitleAdapterListener titleAdapterListener;

    public void setTitleAdapterListener(TitleAdapterListener titleAdapterListener) {
        this.titleAdapterListener = titleAdapterListener;
    }

    interface TitleAdapterListener {
        void click(int index);
    }
}
