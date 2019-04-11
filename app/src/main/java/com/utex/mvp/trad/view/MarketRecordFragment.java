package com.utex.mvp.trad.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utex.R;
import com.utex.base.BaseFragment;
import com.utex.core.AppComponent;
import com.utex.mvp.market.view.MarketAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Demon on 2018/5/18.
 */
public class MarketRecordFragment extends BaseFragment {

    @BindView(R.id.rv_market_record_list)
    RecyclerView rvMarketRecordList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.fragment_market_record, container, false);

        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvMarketRecordList.setAdapter(new MarketAdapter(0, null));
        rvMarketRecordList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }


}
