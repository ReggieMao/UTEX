package com.utex.mvp.mine.presenter;

/**
 * Created by Demon on 2018/9/10.
 */
public interface IWithdrawalDetailPresenter {
    void getCoinWalletAddress(String coin_code);


    void getDepositAddressSuccess(String deposit_address, String deposit_tips, String tag);

    void fail(String str);

    void cannelWithdrawal(int id);

    void cannelWithdrawalSuccess();

}
