package com.utex.mvp.mine.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.utex.data.ApiService;
import com.utex.mvp.mine.model.IIdentityInputModel;
import com.utex.mvp.mine.model.IdentityInputModel;
import com.utex.mvp.mine.view.IIdentityInputView;
import com.utex.mvp.mine.view.IdentityInputActivity;
import com.utex.utils.OssUploadFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Demon on 2018/7/19.
 */
public class IdentityInputPresenter implements IIdentityInputPresenter {

    private ApiService apiService;

    private IIdentityInputView iIdentityInputView;

    private IIdentityInputModel iIdentityInputModel;

    private Context context;

    private String path1;

    private String path2;

    private String path3;

    private String info1;

    private String info2;

    private String info3;

    private String info4;

    private int type;

    private List<String> images = new ArrayList<>();

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (images.size() == 3) {
                // Go 去访问身份认证接口

                switch (type) {
                    case 0:
                        //其他
                        iIdentityInputModel.otherCountrySumbit(apiService, images, info1, info2, info3, info4, IdentityInputPresenter.this);
                        break;
                    case 1:
                        //中国
                        iIdentityInputModel.chinaSumbit(apiService, images, info1, info2, IdentityInputPresenter.this);
                        break;
                }

            }
        }
    };


    public IdentityInputPresenter(ApiService apiService, IdentityInputActivity activity) {
        this.apiService = apiService;
        this.iIdentityInputView = activity;
        iIdentityInputModel = new IdentityInputModel();
        this.context = activity;
    }

    @Override
    public void uploadPicture(String path1, String path2, String path3, String info1, String info2, String info3, String info4, int type) {
        this.info1 = info1;
        this.info2 = info2;
        this.info3 = info3;
        this.info4 = info4;
        this.type = type;
        images.clear();
        this.path1 = path1;
        this.path2 = path2;
        this.path3 = path3;
        iIdentityInputModel.getAppOSSParam(apiService, this);
    }

    @Override
    public void getAppOSSParamSuccess(String accessKeyId, String securityToken, String accessKeySecret, String expiration) {
        OssUploadFile ossFile = new OssUploadFile();
        ossFile.init(context, accessKeyId, accessKeySecret, securityToken);

        ossFile.setOssUploadFileListener(new OssUploadFile.OssUploadFileListener() {
            @Override
            public void onSuccess(String fileName) {
                //上传成
                images.add(fileName);
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onFailure() {
                //上传失败
                images.clear();
            }
        });

        ossFile.upLoadFile(path1);
        ossFile.upLoadFile(path2);
        ossFile.upLoadFile(path3);
    }

    @Override
    public void identitySuccess() {
        iIdentityInputView.identitySuccess();
    }

    @Override
    public void identityFail() {
        iIdentityInputView.identityFail();
    }

}
