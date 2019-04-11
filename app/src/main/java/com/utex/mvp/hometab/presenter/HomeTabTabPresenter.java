package com.utex.mvp.hometab.presenter;

import android.util.Log;

import com.utex.base.BaseFragment;
import com.utex.core.UTEXApplication;
import com.utex.data.ApiService;
import com.utex.mvp.asset.view.AssetFragment;
import com.utex.mvp.home.view.HomeFragment;
import com.utex.mvp.hometab.bean.CheckAppVersionVO;
import com.utex.mvp.hometab.model.HomeTabModel;
import com.utex.mvp.hometab.model.IHomeTabModel;
import com.utex.mvp.hometab.view.HomeTabActivity;
import com.utex.mvp.hometab.view.IHomeTabView;
import com.utex.mvp.mine.view.MineFragment;
import com.utex.mvp.trad.view.TradFragment;
import com.utex.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Demon on 2018/5/17.
 */

public class HomeTabTabPresenter implements IHomeTabPresenter {

    private final HomeTabActivity activity;

    private ApiService apiService;

    private IHomeTabView iHomeTabView;

    private IHomeTabModel iHomeTabModel;

    public HomeTabTabPresenter(ApiService apiService, HomeTabActivity activity) {
        this.apiService = apiService;
        this.iHomeTabView = activity;
        this.activity = activity;
        this.iHomeTabModel = new HomeTabModel();
    }

    @Override
    public void initFragment() {
        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new TradFragment());
        fragments.add(new AssetFragment());
        fragments.add(new MineFragment());

        iHomeTabView.setPage(fragments);
    }

    @Override
    public void checkAppVersion() {
        //检查app 版本,
        iHomeTabModel.checkAppVersion(apiService, this);
    }

    @Override
    public void checkAppVersionFail() {

    }

    @Override
    public void checkAppVersionSuccess(final CheckAppVersionVO.DataBean data) {
        Log.d("d", "checkAppVersionSuccess isIs_new_version: " + data.isIs_new_version());
        Log.d("d", "checkAppVersionSuccess getIs_update: " + data.getIs_update());
        Log.d("d", "checkAppVersionSuccess isIs_token_expired: " + data.isIs_token_expired());
        Log.d("d", "checkAppVersionSuccess getVersion_number: " + data.getVersion_number());
        Log.d("d", "checkAppVersionSuccess getDescription: " + data.getDescription());
        Log.d("d", "checkAppVersionSuccess getDownload: " + data.getDownload());
        if (!data.isIs_new_version()) {
            UTEXApplication.setNewData(data);

            DialogUtils.updateNewVersionDialog(activity, data.getVersion_number(), "11.22", data.getDescription(), new DialogUtils.OnResultListener() {
                @Override
                public void onOK() {
                    iHomeTabView.updateApp(data.getDownload());
                }
            });
//            DialogUtils dialogUtils = new DialogUtils(activity, R.layout.dialog_update_app);
//            dialogUtils.show();
//            dialogUtils.getInstance().getWindow().setLayout(DensityUtil.dp2px(300), LinearLayout.LayoutParams.WRAP_CONTENT);
//
//            View view = dialogUtils.getView();
//            TextView tvAppVersion = view.findViewById(R.id.tv_app_version);
//            TextView tvAppDescription = view.findViewById(R.id.tv_app_description);
//            ImageView ivUpdateAppClose = view.findViewById(R.id.iv_update_app_close);
//            TextView tvAppDown = view.findViewById(R.id.tv_app_down);
//
//            tvAppVersion.setText("V" + data.getVersion_number());
//
//            String str = data.getDescription();
//
//            str = str.replaceAll(";", "\n");
//
//            tvAppDescription.setText(str);
//            ivUpdateAppClose.setOnClickListener(view1 -> {
//                dialogUtils.dismiss();
//            });
//
//            if (data.getIs_update() == 1) {
//                dialogUtils.getInstance().setCancelable(false);
//                ivUpdateAppClose.setVisibility(View.GONE);
//            }
//
//            tvAppDown.setOnClickListener(view12 -> {
//                //下载app
//                if (data.getIs_update() == 0) {
//                    dialogUtils.dismiss();
//                }
//                iHomeTabView.updateApp(data.getDownload());
//            });
        }
    }

    @Override
    public void getExchangeRateList() {
        iHomeTabModel.getExchangeRateList(apiService, this);
    }

}
