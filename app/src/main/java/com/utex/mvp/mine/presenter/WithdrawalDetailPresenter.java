package com.utex.mvp.mine.presenter;

import com.utex.R;
import com.utex.data.ApiService;
import com.utex.mvp.mine.model.IWithdrawalDetailModel;
import com.utex.mvp.mine.model.WithdrawalDetailModel;
import com.utex.mvp.mine.view.WithdrawalDetailActivity;
import com.utex.utils.ToastUtils;
import com.utex.utils.Utils;

/**
 * Created by Demon on 2018/9/10.
 */
public class WithdrawalDetailPresenter implements IWithdrawalDetailPresenter {

    private final ApiService apiService;

    private final WithdrawalDetailActivity iwthdrawalDetailView;

    private IWithdrawalDetailModel iWithdrawalDetailModel;

    public WithdrawalDetailPresenter(ApiService apiService, WithdrawalDetailActivity activity) {
        this.apiService = apiService;
        this.iwthdrawalDetailView = activity;
        this.iWithdrawalDetailModel = new WithdrawalDetailModel();
    }

    @Override
    public void getCoinWalletAddress(String coin_code) {
        iWithdrawalDetailModel.getCoinWalletAddress(apiService, coin_code, this);
    }

    @Override
    public void getDepositAddressSuccess(String deposit_address, String deposit_tips, String tag) {
        iwthdrawalDetailView.getDepositAddressSuccess(deposit_address);
    }

    @Override
    public void fail(String str) {
        Utils.showMessage(str);
    }

    @Override
    public void cannelWithdrawal(int id) {
        iWithdrawalDetailModel.cannelWithdrawal(apiService, id, this);
    }

    @Override
    public void cannelWithdrawalSuccess() {
        ToastUtils.show(Utils.getResourceString(R.string.yi_che_xiao));
        iwthdrawalDetailView.cannelWithdrawalSuccess();
    }
}
