package com.utex.mvp.search.dagger2;

import com.utex.base.BaseModule;
import com.utex.data.ApiService;
import com.utex.mvp.search.presenter.ISearchPresenter;
import com.utex.mvp.search.presenter.SearchPresenter;
import com.utex.mvp.search.view.ISearchView;
import com.utex.mvp.search.view.SearchActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by admin on 2017/4/21.
 */
@Module
public class SearchModule extends BaseModule<SearchActivity, ISearchView> {

    public SearchModule(SearchActivity searchActivity) {
        super(searchActivity);
    }

    @Provides
    protected ISearchPresenter searchPresenter(ApiService apiService) {
        return new SearchPresenter(apiService, activity);
    }
}
