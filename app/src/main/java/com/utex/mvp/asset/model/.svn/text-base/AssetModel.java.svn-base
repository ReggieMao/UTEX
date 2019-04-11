package com.utex.mvp.asset.model;

import android.util.Log;

import com.utex.data.ApiService;
import com.utex.mvp.asset.bean.Asset;
import com.utex.mvp.asset.presenter.IAssetPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Demon on 2018/6/1.
 */
public class AssetModel implements IAssetModel {

    @Override
    public void getAssetList(ApiService apiService, final IAssetPresenter iAssetPresenter) {
        apiService.getAssetList()
                .enqueue(new Callback<Asset>() {
                    @Override
                    public void onResponse(Call<Asset> call, Response<Asset> response) {
                        if (response.code() == 200) {
                            iAssetPresenter.getAssetListSuccess(response.body());
                        } else {
                            iAssetPresenter.getAssetListFail();
                        }
                    }

                    @Override
                    public void onFailure(Call<Asset> call, Throwable t) {
                        Log.e("TAG", String.valueOf(t));
                        iAssetPresenter.getAssetListFail();
                    }
                });
    }

}
