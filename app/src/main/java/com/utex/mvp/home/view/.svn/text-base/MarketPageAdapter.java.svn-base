package com.utex.mvp.home.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.utex.base.BaseFragment;

import java.util.List;

/**
 * Created by Demon on 2018/5/18.
 */
public class MarketPageAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> fragments;

    public MarketPageAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
