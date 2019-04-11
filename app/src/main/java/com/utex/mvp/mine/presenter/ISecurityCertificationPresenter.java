package com.utex.mvp.mine.presenter;

/**
 * Created by Demon on 2018/8/6.
 */
public interface ISecurityCertificationPresenter {
    void checkPassword(int res);

    void checkPasswordFail();

    void checkPasswordSuccess();

}
