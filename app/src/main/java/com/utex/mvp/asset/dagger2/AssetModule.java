package com.utex.mvp.asset.dagger2;

import com.utex.base.BaseModule;
import com.utex.data.ApiService;
import com.utex.mvp.asset.presenter.AssetPresenter;
import com.utex.mvp.asset.presenter.IAssetPresenter;
import com.utex.mvp.asset.view.AssetFragment;
import com.utex.mvp.asset.view.IAssetView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by admin on 2017/4/21.
 */
@Module
public class AssetModule extends BaseModule<AssetFragment, IAssetView> {

    public AssetModule(AssetFragment assetFragment) {
        super(assetFragment);
    }

    @Provides
    protected IAssetPresenter assetPresenter(ApiService apiService) {
        return new AssetPresenter(apiService, activity);
    }
}
