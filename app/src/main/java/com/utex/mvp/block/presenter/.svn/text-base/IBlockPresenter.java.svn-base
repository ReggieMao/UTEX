package com.utex.mvp.block.presenter;

/**
 * Created by Demon on 2018/7/3.
 */
public interface IBlockPresenter {
    void getDepositAddress(String coinCode);

    void getDepositAddressSuccess(String deposit_address, String deposit_tips, String tag);

    void getWithdrawalAddress(String coinCode);

    void getWithdrawalAddressSuccess(String withdrawal_tips, String withdrawal_fee, Boolean withdrawal_switch, Double withdrawal_min_amount, boolean tag);

    void sendWithdrawalEmail(String coinCode);

    void sendWithdrawalSms(String coinCode);

    void sendWithdrawalSuccess();

    void withdrawalSuccess();

    void fail(String str);

    void withdrawal(String coinCode, double amount, String withdrawalAddress, String emailCode, String telCode, String googleCode, String tag);

    void sendEmailSuccess();

    void sendTelSuccess();

    void transfer(int assetId, double amount, String address);

    void transferSuccess();

}
