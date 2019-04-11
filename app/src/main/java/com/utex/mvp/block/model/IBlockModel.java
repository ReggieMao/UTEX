package com.utex.mvp.block.model;

import com.utex.data.ApiService;
import com.utex.mvp.block.bean.WithdrawalDTO;
import com.utex.mvp.block.presenter.IBlockPresenter;

/**
 * Created by Demon on 2018/7/3.
 */
public interface IBlockModel {

    void getDepositAddress(ApiService apiService, String coinCode, IBlockPresenter iBlockPresenter);

    void getWithdrawalAddress(ApiService apiService, String coinCode, IBlockPresenter iBlockPresenter);

    void sendWithdrawalEmail(ApiService apiService, String coinCode, IBlockPresenter iBlockPresenter);

    void sendWithdrawalSms(ApiService apiService, String coinCode, IBlockPresenter iBlockPresenter);

    void withdrawal(ApiService apiService, WithdrawalDTO withdrawalDTO, IBlockPresenter iBlockPresenter);

    void transfer(ApiService apiService, int assetId, double amount, String address, IBlockPresenter iBlockPresenter);

}
