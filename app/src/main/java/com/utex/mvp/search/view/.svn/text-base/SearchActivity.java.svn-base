package com.utex.mvp.search.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.utex.R;
import com.utex.base.BaseActivity;
import com.utex.bean.TickerDo;
import com.utex.core.AppComponent;
import com.utex.core.UTEXApplication;
import com.utex.db.ExTickerDao;
import com.utex.listener.ETtextChangeListener;
import com.utex.mvp.search.dagger2.DaggerSearchComponent;
import com.utex.mvp.search.dagger2.SearchModule;
import com.utex.mvp.search.presenter.ISearchPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity implements ISearchView, SearchAdapter.SearchOnclickListener {

    @Inject
    ISearchPresenter iSearchPresenter;

    @BindView(R.id.tv_search_cannel)
    TextView tvSearchCannel;

    @BindView(R.id.rv_search_list)
    RecyclerView rvSearchList;

    @BindView(R.id.et_search)
    EditText etSearch;

    private List<TickerDo> tickers;

    private SearchAdapter searchAdapter;

    private TickerDo currTicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        tickers = ExTickerDao.getShowTickers();

        etSearch.addTextChangedListener(new ETtextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                super.onTextChanged(charSequence, i, i1, i2);
                tickers = ExTickerDao.searchTickers(String.valueOf(charSequence));
                searchAdapter.setData(tickers);
            }
        });

        rvSearchList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        searchAdapter = new SearchAdapter(tickers);
        rvSearchList.setAdapter(searchAdapter);

        searchAdapter.setSearchOnclickListener(this);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSearchComponent
                .builder()
                .appComponent(appComponent)
                .searchModule(new SearchModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.tv_search_cannel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search_cannel:
                finish();
                break;
        }
    }

    @Override
    public void addOption(TickerDo tickerDo) {
        //添加自选
        this.currTicker = tickerDo;
        if (UTEXApplication.getLoginUser() != null) {
            iSearchPresenter.addOption(tickerDo.getCoin_market_code(), tickerDo.getId());
        } else {
            addOptionalSuccess();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 199 && resultCode == 200) {
            finish();
        }
    }

    @Override
    public void removeOption(TickerDo tickerDo) {
        //移除自选
        this.currTicker = tickerDo;
        if (UTEXApplication.getLoginUser() != null) {
            iSearchPresenter.removeOption(tickerDo.getCoin_market_code());
        } else {
            cannelOptionalSuccess();
        }
    }

    @Override
    public void addOptionalSuccess() {
        ExTickerDao.changeOption(currTicker.getCoin_market_code());
        searchAdapter.notifyDataSetChanged();
    }

    @Override
    public void cannelOptionalSuccess() {
        ExTickerDao.changeOption(currTicker.getCoin_market_code());
        searchAdapter.notifyDataSetChanged();
    }
}
