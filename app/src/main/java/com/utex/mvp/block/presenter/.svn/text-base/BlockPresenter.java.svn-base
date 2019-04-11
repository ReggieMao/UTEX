package com.utex.mvp.block.presenter;

import android.text.TextUtils;

import com.utex.data.ApiService;
import com.utex.mvp.block.bean.WithdrawalDTO;
import com.utex.mvp.block.model.BlockModel;
import com.utex.mvp.block.model.IBlockModel;
import com.utex.mvp.block.view.BlockActivity;
import com.utex.mvp.block.view.IBlockView;
import com.utex.utils.Utils;

/**
 * Created by Demon on 2018/7/3.
 */
public class BlockPresenter implements IBlockPresenter {


    private ApiService apiService;

    private IBlockView iBlockView;

    private IBlockModel iBlockModel;

    public BlockPresenter(ApiService apiService, BlockActivity activity) {
        this.apiService = apiService;
        iBlockView = activity;
        iBlockModel = new BlockModel();
    }

    @Override
    public void getDepositAddress(String coinCode) {
        iBlockModel.getDepositAddress(apiService, coinCode, this);
    }

    @Override
    public void getDepositAddressSuccess(String deposit_address, String deposit_tips, String tag) {
        iBlockView.getDepositAddressSuccess(deposit_address, deposit_tips, tag);
    }

    @Override
    public void getWithdrawalAddress(String coinCode) {
        iBlockModel.getWithdrawalAddress(apiService, coinCode, this);
    }

    @Override
    public void getWithdrawalAddressSuccess(String withdrawal_tips, String withdrawal_fee, Boolean withdrawal_switch, Double withdrawal_min_amount, boolean tag) {
        if (TextUtils.isEmpty(withdrawal_tips)) {
            withdrawal_tips = "";
        }
        iBlockView.getWithdrawalAddressSuccess(withdrawal_tips, withdrawal_fee, withdrawal_switch, withdrawal_min_amount, tag);
    }

    @Override
    public void sendWithdrawalEmail(String coinCode) {
        iBlockModel.sendWithdrawalEmail(apiService, coinCode, this);
    }

    @Override
    public void sendWithdrawalSms(String coinCode) {
        iBlockModel.sendWithdrawalSms(apiService, coinCode, this);
    }

    @Override
    public void sendWithdrawalSuccess() {
        iBlockView.sendWithdrawalSuccess();
    }

    @Override
    public void withdrawalSuccess() {
        iBlockView.withdrawalSuccess();
    }

    @Override
    public void fail(String str) {
        Utils.showMessage(str);
    }

    @Override
    public void withdrawal(String coinCode, double amount, String withdrawalAddress, String emailCode, String telCode, String googleCode, String tagAddress) {
        WithdrawalDTO withdrawalDTO = new WithdrawalDTO(coinCode, amount, withdrawalAddress, emailCode, telCode, googleCode);
        if (!TextUtils.isEmpty(tagAddress)) {
            withdrawalDTO.setTag(tagAddress);
        }
        iBlockModel.withdrawal(apiService, withdrawalDTO, this);
    }

    @Override
    public void sendEmailSuccess() {
        iBlockView.sendEmailSuccess();
    }

    @Override
    public void sendTelSuccess() {
        iBlockView.sendTelSuccess();

    }

    @Override
    public void transfer(int assetId, double amount, String address) {
        iBlockModel.transfer(apiService, assetId, amount, address, this);
    }

    @Override
    public void transferSuccess() {
        iBlockView.transferSuccess();
    }

}
