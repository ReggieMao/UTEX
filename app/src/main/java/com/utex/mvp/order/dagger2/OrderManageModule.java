package com.utex.mvp.order.dagger2;

import com.utex.base.BaseModule;
import com.utex.data.ApiService;
import com.utex.mvp.order.presenter.IOrderManagePresenter;
import com.utex.mvp.order.presenter.OrderManagePresenter;
import com.utex.mvp.order.view.IOrderManageView;
import com.utex.mvp.order.view.OrderManageFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by admin on 2017/4/21.
 */
@Module
public class OrderManageModule extends BaseModule<OrderManageFragment, IOrderManageView> {

    public OrderManageModule(OrderManageFragment orderManageFragment) {
        super(orderManageFragment);
    }

    @Provides
    protected IOrderManagePresenter orderManagePresenter(ApiService apiService) {
        return new OrderManagePresenter(apiService, activity);
    }
}
