package com.utex.mvp.splash.presenter;

import com.utex.bean.TickerDo;
import com.utex.common.FiledConstants;
import com.utex.data.ApiService;
import com.utex.db.ExTickerDao;
import com.utex.mvp.splash.model.SplashModel;
import com.utex.mvp.splash.view.ISplashView;
import com.utex.mvp.splash.view.SplashActivity;
import com.utex.utils.SharedPreferencesUtil;

import java.util.List;

/**
 * Created by Demon on 2018/7/23.
 */
public class SplashPresenter implements ISplashPresenter {


    private final SplashModel iSplashModel;

    private final ApiService apiService;

    private final ISplashView iSplashView;

    public SplashPresenter(ApiService apiService, SplashActivity activity) {
        this.apiService = apiService;
        this.iSplashView = activity;
        this.iSplashModel = new SplashModel();
    }

    @Override
    public void getCoinListSuccess(List<TickerDo> data) {
        //插入修改数据库
        ExTickerDao.insertTicker(data);
        //修改自选
        String optionList = SharedPreferencesUtil.getString(FiledConstants.OPTION_COIN, "");
        String[] split = optionList.split(",");
        if (split != null) {
            for (int i = 0; i < split.length; i++) {
                ExTickerDao.changeOption(split[i]);
            }
        }

        iSplashView.goHome();
    }

    @Override
    public void getCoinListFail() {
        iSplashView.goHome();
    }

    @Override
    public void getCoinList() {
        iSplashModel.getCoinList(apiService, this);
    }
}
