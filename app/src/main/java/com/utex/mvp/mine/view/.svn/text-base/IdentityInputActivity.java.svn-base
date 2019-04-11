package com.utex.mvp.mine.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.utex.R;
import com.utex.base.BaseActivity;
import com.utex.bean.UserDO;
import com.utex.common.FiledConstants;
import com.utex.common.SDUrl;
import com.utex.core.AppComponent;
import com.utex.core.UTEXApplication;
import com.utex.db.ExUserDao;
import com.utex.mvp.mine.dagger2.DaggerIdentityInputComponent;
import com.utex.mvp.mine.dagger2.IdentityInputModule;
import com.utex.mvp.mine.presenter.IIdentityInputPresenter;
import com.utex.utils.ScreenshotUtils;
import com.utex.utils.SharedPreferencesUtil;
import com.utex.utils.ToastUtils;
import com.utex.utils.UriToPathUtil;
import com.utex.utils.Utils;
import com.utex.widget.popuwindow.GenderSelectionPopupWindow;
import com.utex.widget.popuwindow.IdentityBottomPopupWindow;
import com.utex.widget.popuwindow.IdentityHelpPopupWindow;
import com.utex.widget.popuwindow.LoadingPopupWindow;
import com.yanzhenjie.permission.AndPermission;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IdentityInputActivity extends BaseActivity implements IIdentityInputView {

    private static final int IMAGE_REQUEST_CODE = 2;

    @Inject
    IIdentityInputPresenter iIdentityInputPresenter;

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;

    @BindView(R.id.iv_identity_title)
    ImageView ivIdentityTitle;

    @BindView(R.id.et_identity_1)
    EditText etIdentity1;

    @BindView(R.id.et_identity_2)
    EditText etIdentity2;

    @BindView(R.id.et_identity_3)
    EditText etIdentity3;

    @BindView(R.id.et_identity_4)
    EditText etIdentity4;

    @BindView(R.id.iv_identity_picture_other_1)
    ImageView ivIdentityPictureOther1;

    @BindView(R.id.iv_identity_picture_other_2)
    ImageView ivIdentityPictureOther2;

    @BindView(R.id.iv_identity_picture_other_3)
    ImageView ivIdentityPictureOther3;

    @BindView(R.id.iv_identity_album_other_1)
    ImageView ivIdentityAlbumOther1;

    @BindView(R.id.iv_identity_album_other_2)
    ImageView ivIdentityAlbumOther2;

    @BindView(R.id.iv_identity_album_other_3)
    ImageView ivIdentityAlbumOther3;

    @BindView(R.id.ll_identity_input_photo_orther_parent)
    LinearLayout llIdentityInputPhotoOrtherParent;

    @BindView(R.id.ll_identity_input_photo_zh_parent)
    LinearLayout llIdentityInputPhotozhParent;

    @BindView(R.id.iv_identity_picture_zh_1)
    ImageView ivIdentityPictureZh1;

    @BindView(R.id.iv_identity_picture_zh_2)
    ImageView ivIdentityPictureZh2;

    @BindView(R.id.iv_identity_picture_zh_3)
    ImageView ivIdentityPictureZh3;

    @BindView(R.id.iv_identity_album_zh_1)
    ImageView ivIdentityAlbumZh1;

    @BindView(R.id.iv_identity_album_zh_2)
    ImageView ivIdentityAlbumZh2;

    @BindView(R.id.iv_identity_album_zh_3)
    ImageView ivIdentityAlbumZh3;

    @BindView(R.id.rl_identity_other)
    RelativeLayout rlIdentityOther;

    @BindView(R.id.rl_identity_zh)
    RelativeLayout rlIdentityZh;

    @BindView(R.id.iv_toolbar_right)
    ImageView ivToolbarRight;

    @BindView(R.id.iv_identity_del_other_1)
    ImageView ivIdentityDelOther1;

    @BindView(R.id.iv_identity_enlarge_other_1)
    ImageView ivIdentityEnlargeOther1;

    @BindView(R.id.iv_identity_del_other_2)
    ImageView ivIdentityDelOther2;

    @BindView(R.id.iv_identity_enlarge_other_2)
    ImageView ivIdentityEnlargeOther2;

    @BindView(R.id.iv_identity_del_other_3)
    ImageView ivIdentityDelOther3;

    @BindView(R.id.iv_identity_enlarge_other_3)
    ImageView ivIdentityEnlargeOther3;

    @BindView(R.id.iv_identity_del_zh_1)
    ImageView ivIdentityDelZh1;

    @BindView(R.id.iv_identity_enlarge_zh_1)
    ImageView ivIdentityEnlargeZh1;

    @BindView(R.id.iv_identity_del_zh_2)
    ImageView ivIdentityDelZh2;

    @BindView(R.id.iv_identity_enlarge_zh_2)
    ImageView ivIdentityEnlargeZh2;

    @BindView(R.id.iv_identity_del_zh_3)
    ImageView ivIdentityDelZh3;

    @BindView(R.id.iv_identity_enlarge_zh_3)
    ImageView ivIdentityEnlargeZh3;

    @BindView(R.id.img_loading)
    ImageView mImgLoading;

    private IdentityBottomPopupWindow identityBottomPopupWindow;

    private String path1;

    private String path2;

    private String path3;

    /**
     * 0 其他
     * 1 中国
     */
    private int type;
    private IdentityHelpPopupWindow identityHelpPopupWindow;
    private int status;

    private Bitmap bitmap1;
    private Bitmap bitmap2;
    private Bitmap bitmap3;
    private String currFileName;
    private LoadingPopupWindow loadingPopupWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_input);
        ButterKnife.bind(this);

        tvToolbarTitle.setText(R.string.shen_fen_ren_zheng);
        ivToolbarRight.setVisibility(View.VISIBLE);

        type = getIntent().getIntExtra(FiledConstants.TYPE, 0);

        boolean isCn = false;
        String tw = SharedPreferencesUtil.getString(FiledConstants.SP_LANGUGE_ABBREVIATION, "tw");
        if ("zh".equals(tw) || "tw".equals(tw)) {
            isCn = true;
        }

        switch (type) {
            case 0:
                //其他
                if (isCn) {
                    ivIdentityTitle.setImageResource(R.drawable.my_other_cn);
                } else {
                    ivIdentityTitle.setImageResource(R.drawable.my_other_en);
                }

                rlIdentityOther.setVisibility(View.VISIBLE);
                rlIdentityZh.setVisibility(View.GONE);

                llIdentityInputPhotozhParent.setVisibility(View.GONE);
                llIdentityInputPhotoOrtherParent.setVisibility(View.VISIBLE);

                etIdentity2.setCursorVisible(false);
                etIdentity2.setFocusable(false);
                etIdentity2.setFocusableInTouchMode(false);
                etIdentity2.setOnClickListener(view -> {
                    final GenderSelectionPopupWindow genderSelectionPopupWindow = new GenderSelectionPopupWindow(IdentityInputActivity.this, R.layout.activity_identity_input);
                    genderSelectionPopupWindow.getTvGenderNan().setOnClickListener(view1 -> {
                        etIdentity2.setText(Utils.getResourceString(R.string.nan));
                        genderSelectionPopupWindow.dimss();
                    });

                    genderSelectionPopupWindow.getTvGenderNv().setOnClickListener(view12 -> {
                        etIdentity2.setText(Utils.getResourceString(R.string.nv));
                        genderSelectionPopupWindow.dimss();
                    });

                });
                break;
            case 1:
                //中国
                if (isCn) {
                    ivIdentityTitle.setImageResource(R.drawable.my_china_cn);
                } else {
                    ivIdentityTitle.setImageResource(R.drawable.my_china_en);
                }

                etIdentity2.setHint(Utils.getResourceString(R.string.you_xiao_shen_fen_zheng_hao));
                etIdentity3.setVisibility(View.GONE);
                etIdentity4.setVisibility(View.GONE);
                rlIdentityOther.setVisibility(View.GONE);
                rlIdentityZh.setVisibility(View.VISIBLE);

                llIdentityInputPhotozhParent.setVisibility(View.VISIBLE);
                llIdentityInputPhotoOrtherParent.setVisibility(View.GONE);
                break;
        }

        identityBottomPopupWindow = new IdentityBottomPopupWindow(this, R.layout.activity_identity_input);
        identityHelpPopupWindow = new IdentityHelpPopupWindow(this, R.layout.activity_identity_input);

        identityBottomPopupWindow.getTvIdentityBottomShot().setOnClickListener(v -> {
            currFileName = System.currentTimeMillis() + ".jpg";
            Utils.CameraThread cameraThread = new Utils.CameraThread(currFileName, SDUrl.picPath, IdentityInputActivity.this);
            cameraThread.run();
            identityBottomPopupWindow.dimss();
        });

        identityBottomPopupWindow.getTvIdentityBottomAlbum().setOnClickListener(view -> {
            //相册
            boolean b = AndPermission.hasPermissions(IdentityInputActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
            if (b) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_REQUEST_CODE);
                identityBottomPopupWindow.dimss();
            } else {
                ToastUtils.show(Utils.getResourceString(R.string.qing_qu_shou_ji_she_zhi_zhong_da_kai_quan_xian));
            }
        });

        new Handler()
                .postDelayed(() -> identityHelpPopupWindow.show(), 300);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //在相册里面选择好相片之后调回到现在的这个activity中
        String path = null;
        switch (requestCode) {
            case IMAGE_REQUEST_CODE://这里的requestCode是我自己设置的，就是确定返回到那个Activity的标志
                if (resultCode == RESULT_OK) {//resultcode是setResult里面设置的code值
                    try {
                        path = UriToPathUtil.getPath(this, data.getData());
                        Log.e("TAG", path);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case Activity.DEFAULT_KEYS_DIALER:
                path = SDUrl.picPath + currFileName;
                Log.e("TAG", path);
                break;
        }

        if (TextUtils.isEmpty(path)) {
            return;
        }
        Bitmap bitmap;
        try {
            bitmap = ScreenshotUtils.getimage(path);
        } catch (Exception e) {
            return;
        }
        path = SDUrl.picPath + System.currentTimeMillis() + ".jpg";
        ScreenshotUtils.saveBitmap(path, bitmap);

        ImageView imageView = null;
        ImageView imageView1 = null;
        ImageView del = null;
        ImageView enlarge = null;
        switch (status) {
            case 1:
                imageView = type == 1 ? ivIdentityPictureZh1 : ivIdentityPictureOther1;
                imageView1 = type == 1 ? ivIdentityAlbumZh1 : ivIdentityAlbumOther1;
                del = type == 1 ? ivIdentityDelZh1 : ivIdentityDelOther1;
                enlarge = type == 1 ? ivIdentityEnlargeZh1 : ivIdentityEnlargeOther1;
                path1 = path;  //获取照片路径
                break;
            case 2:
                imageView = type == 1 ? ivIdentityPictureZh2 : ivIdentityPictureOther2;
                imageView1 = type == 1 ? ivIdentityAlbumZh2 : ivIdentityAlbumOther2;
                del = type == 1 ? ivIdentityDelZh2 : ivIdentityDelOther2;
                enlarge = type == 1 ? ivIdentityEnlargeZh2 : ivIdentityEnlargeOther2;
                path2 = path;  //获取照片路径
                break;
            case 3:
                imageView = type == 1 ? ivIdentityPictureZh3 : ivIdentityPictureOther3;
                imageView1 = type == 1 ? ivIdentityAlbumZh3 : ivIdentityAlbumOther3;
                del = type == 1 ? ivIdentityDelZh3 : ivIdentityDelOther3;
                enlarge = type == 1 ? ivIdentityEnlargeZh3 : ivIdentityEnlargeOther3;
                path3 = path;  //获取照片路径
                break;
        }

        if (imageView != null) {
            Glide.with(IdentityInputActivity.this)
                    .load(bitmap)
                    .into(imageView);
        }

        if (imageView1 != null) {
            imageView1.setVisibility(View.GONE);
        }

        if (del != null) {
            del.setVisibility(View.VISIBLE);
        }

        if (enlarge != null) {
            enlarge.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerIdentityInputComponent
                .builder()
                .appComponent(appComponent)
                .identityInputModule(new IdentityInputModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.iv_toolbar_left, R.id.iv_identity_album_other_1,
            R.id.iv_identity_album_other_2, R.id.iv_identity_album_other_3, R.id.iv_identity_album_zh_1,
            R.id.iv_identity_album_zh_2, R.id.iv_identity_album_zh_3,
            R.id.tv_identity_confirm, R.id.iv_toolbar_right, R.id.iv_identity_del_other_1, R.id.iv_identity_del_other_2, R.id.iv_identity_del_other_3,
            R.id.iv_identity_del_zh_1, R.id.iv_identity_del_zh_2, R.id.iv_identity_del_zh_3, R.id.iv_identity_enlarge_other_1, R.id.iv_identity_enlarge_other_2,
            R.id.iv_identity_enlarge_other_3, R.id.iv_identity_enlarge_zh_1, R.id.iv_identity_enlarge_zh_2, R.id.iv_identity_enlarge_zh_3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_right:
                identityHelpPopupWindow.show();
                break;
            case R.id.iv_toolbar_left:
                finish();
                break;
            case R.id.iv_identity_album_other_1:
            case R.id.iv_identity_album_zh_1:
                //跳出popwindow相机
                status = 1;
                identityBottomPopupWindow.show();
                break;
            case R.id.iv_identity_album_other_2:
            case R.id.iv_identity_album_zh_2:
                status = 2;
                identityBottomPopupWindow.show();
                break;
            case R.id.iv_identity_album_other_3:
            case R.id.iv_identity_album_zh_3:
                status = 3;
                identityBottomPopupWindow.show();
                break;
            case R.id.tv_identity_confirm:
                //提交,上传图标
                if (path1 == null || path2 == null || path3 == null ||
                        TextUtils.isEmpty(etIdentity1.getText()) ||
                        TextUtils.isEmpty(etIdentity2.getText())) {
                    ToastUtils.show(Utils.getResourceString(R.string.identity_not_complete));
                    return;
                }

                if (type == 0 && ((TextUtils.isEmpty(etIdentity3.getText()) ||
                        TextUtils.isEmpty(etIdentity4.getText())))) {
                    ToastUtils.show(Utils.getResourceString(R.string.identity_not_complete));
                    return;
                }

                loadingPopupWindow = new LoadingPopupWindow(this, R.layout.activity_identity_input);
                loadingPopupWindow.show();
                Utils.loadGif(IdentityInputActivity.this, mImgLoading);


                iIdentityInputPresenter.uploadPicture(path1, path2, path3, etIdentity1.getText().toString(),
                        etIdentity2.getText().toString(), etIdentity3.getText().toString(), etIdentity4.getText().toString(), type);
                break;
            case R.id.iv_identity_enlarge_zh_1:
            case R.id.iv_identity_enlarge_other_1:
                jumpImage(path1);
                break;
            case R.id.iv_identity_enlarge_zh_2:
            case R.id.iv_identity_enlarge_other_2:
                jumpImage(path2);
                break;
            case R.id.iv_identity_enlarge_zh_3:
            case R.id.iv_identity_enlarge_other_3:
                jumpImage(path3);
                break;
            case R.id.iv_identity_del_other_1:
                ivIdentityAlbumOther1.setVisibility(View.VISIBLE);
                ivIdentityPictureOther1.setImageResource(R.drawable.my_sfrz_pic_hzfm);
                ivIdentityDelOther1.setVisibility(View.GONE);
                ivIdentityEnlargeOther1.setVisibility(View.GONE);
                break;
            case R.id.iv_identity_del_other_2:
                ivIdentityAlbumOther2.setVisibility(View.VISIBLE);
                ivIdentityPictureOther2.setImageResource(R.drawable.my_sfrz_pic_hzxx);
                ivIdentityDelOther2.setVisibility(View.GONE);
                ivIdentityEnlargeOther2.setVisibility(View.GONE);
                break;
            case R.id.iv_identity_del_other_3:
                ivIdentityAlbumOther3.setVisibility(View.VISIBLE);
                ivIdentityPictureOther3.setImageResource(R.drawable.my_sfrz_pic_hzqz);
                ivIdentityDelOther3.setVisibility(View.GONE);
                ivIdentityEnlargeOther3.setVisibility(View.GONE);
                break;
            case R.id.iv_identity_del_zh_1:
                ivIdentityAlbumZh1.setVisibility(View.VISIBLE);
                ivIdentityPictureZh1.setImageResource(R.drawable.my_sfrz_pic_sfzm);
                ivIdentityDelZh1.setVisibility(View.GONE);
                ivIdentityEnlargeZh1.setVisibility(View.GONE);
                break;
            case R.id.iv_identity_del_zh_2:
                ivIdentityAlbumZh2.setVisibility(View.VISIBLE);
                ivIdentityPictureZh2.setImageResource(R.drawable.my_sfrz_pic_sffm);
                ivIdentityDelZh2.setVisibility(View.GONE);
                ivIdentityEnlargeZh2.setVisibility(View.GONE);
                break;
            case R.id.iv_identity_del_zh_3:
                ivIdentityAlbumZh3.setVisibility(View.VISIBLE);
                ivIdentityPictureZh3.setImageResource(R.drawable.my_sfrz_pic_sfqz);
                ivIdentityDelZh3.setVisibility(View.GONE);
                ivIdentityEnlargeZh3.setVisibility(View.GONE);
                break;
        }
    }

    private void jumpImage(String path) {
        Intent intent = new Intent(IdentityInputActivity.this, PictureActivity.class);
        intent.putExtra("path", path);
        startActivity(intent);
    }

    @Override
    public void identitySuccess() {
        if (loadingPopupWindow != null) {
            loadingPopupWindow.dimss();
            mImgLoading.setVisibility(View.GONE);
        }

        UserDO loginUser = UTEXApplication.getLoginUser();
        loginUser.setChecking_level(2);
        ExUserDao.insertUser(loginUser);
        Intent success = new Intent(this, IdentitySuccessActivity.class);
        startActivity(success);
        finish();
    }

    @Override
    public void identityFail() {
        if (loadingPopupWindow != null) {
            loadingPopupWindow.dimss();
            mImgLoading.setVisibility(View.GONE);
        }
        ToastUtils.show(Utils.getResourceString(R.string.shen_he_shi_bai));
    }
}
