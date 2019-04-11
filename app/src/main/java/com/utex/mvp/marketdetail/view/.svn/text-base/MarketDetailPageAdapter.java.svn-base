package com.utex.mvp.marketdetail.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.utex.base.BaseFragment;
import com.utex.utils.Utils;

import java.util.List;

/**
 * Created by Demon on 2018/5/21.
 */
public class MarketDetailPageAdapter extends FragmentPagerAdapter {

    List<BaseFragment> fragments;

    public MarketDetailPageAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return Utils.checkList(fragments);
    }
}
