package com.utex.mvp.search.presenter;

import com.utex.data.ApiService;
import com.utex.mvp.search.model.ISearchModel;
import com.utex.mvp.search.model.SearchModel;
import com.utex.mvp.search.view.ISearchView;
import com.utex.mvp.search.view.SearchActivity;

/**
 * Created by Demon on 2018/7/9.
 */
public class SearchPresenter implements ISearchPresenter {

    private ApiService apiService;

    private ISearchView iSearchView;

    private ISearchModel iSearchModel;

    public SearchPresenter(ApiService apiService, SearchActivity activity) {
        this.apiService = apiService;
        this.iSearchView = activity;
        iSearchModel = new SearchModel();
    }


    @Override
    public void addOption(String coinMarketCode, Long id) {
        iSearchModel.addOption(apiService, coinMarketCode, id, this);
    }

    @Override
    public void removeOption(String coinMarketCode) {
        iSearchModel.removeOption(apiService, coinMarketCode, this);
    }

    @Override
    public void addOptionalSuccess() {
        iSearchView.addOptionalSuccess();
    }

    @Override
    public void cannelOptionalSuccess() {
        iSearchView.cannelOptionalSuccess();
    }
}
