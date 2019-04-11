package com.utex.mvp.search.presenter;

/**
 * Created by Demon on 2018/7/9.
 */
public interface ISearchPresenter {
    void addOption(String coin_market_code, Long id);

    void removeOption(String coin_market_code);

    void addOptionalSuccess();

    void cannelOptionalSuccess();
}
