package com.utex.mvp.block.dagger2;

import com.utex.base.BaseModule;
import com.utex.data.ApiService;
import com.utex.mvp.block.presenter.BlockPresenter;
import com.utex.mvp.block.presenter.IBlockPresenter;
import com.utex.mvp.block.view.BlockActivity;
import com.utex.mvp.block.view.IBlockView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by admin on 2017/4/21.
 */
@Module
public class BlockModule extends BaseModule<BlockActivity, IBlockView> {

    public BlockModule(BlockActivity blockActivity) {
        super(blockActivity);
    }

    @Provides
    protected IBlockPresenter assetPresenter(ApiService apiService) {
        return new BlockPresenter(apiService, activity);
    }
}
