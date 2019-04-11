package com.utex.mvp.mine.view;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.utex.R;
import com.utex.base.BaseActivity;
import com.utex.common.FiledConstants;
import com.utex.common.SDUrl;
import com.utex.core.AppComponent;
import com.utex.utils.ToastUtils;
import com.utex.utils.Utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WeChatActivity extends BaseActivity {


    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;

    @BindView(R.id.iv_we_chat_qr)
    ImageView ivWeChatQr;

    @BindView(R.id.tv_we_chat_number)
    TextView tvWeChatNumber;

    @BindView(R.id.tv_name)
    TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_chat);
        ButterKnife.bind(this);

        int type = getIntent().getIntExtra(FiledConstants.TYPE, 0);

        if (type == 1) {
            tvToolbarTitle.setText(Utils.getResourceString(R.string.yi_cheng_dui_guan_fang_wei_xin));
            ivWeChatQr.setDrawingCacheEnabled(true);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.chengdui_wechat);
            ivWeChatQr.setImageBitmap(bitmap);
            tvWeChatNumber.setText(Utils.getResourceString(R.string.wei_xin_hao_wangxs139));
            tvName.setText(Utils.getResourceString(R.string.zhong_hui_da_xin));
        } else if (type == 2) {
            tvToolbarTitle.setText(Utils.getResourceString(R.string.yi_cheng_dui_guan_fang_wei_xin1));
            ivWeChatQr.setDrawingCacheEnabled(true);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.guanfang_wechat);
            ivWeChatQr.setImageBitmap(bitmap);
            tvWeChatNumber.setText(Utils.getResourceString(R.string.wei_xin_hao_exnow_support));
        } else {
            tvToolbarTitle.setText(Utils.getResourceString(R.string.guan_fang_wei_xin));
            ivWeChatQr.setDrawingCacheEnabled(true);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.guanfang_wechat);
            ivWeChatQr.setImageBitmap(bitmap);
            tvWeChatNumber.setText(Utils.getResourceString(R.string.wei_xin_hao_exnow_support));
        }
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @OnClick({R.id.tv_we_chat_copy, R.id.tv_we_chat_save_qr, R.id.iv_toolbar_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_left:
                finish();
                break;
            case R.id.tv_we_chat_copy:
                int intExtra = getIntent().getIntExtra(FiledConstants.TYPE, 0);
                String str = "";
                switch (intExtra) {
                    case 0:
                        str = "utex";
                        break;
                    case 1:
                        str = "yichengdui0001";
                        break;
                    case 2:
                        str = "utex";
                        break;
                }
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(str);
                ToastUtils.show(Utils.getResourceString(R.string.fu_zhi_cheng_gong));
                break;
            case R.id.tv_we_chat_save_qr:
                saveBitmapFile(ivWeChatQr.getDrawingCache());
                break;
        }
    }

    /**
     * 保存bitmap
     */
    public void saveBitmapFile(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }

        ////重复保存时，覆盖原同名图片
        File file = new File(SDUrl.picPath + "WeChatQrCode" + System.currentTimeMillis() + ".jpg");//将要保存图片的路径和图片名称
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            Toast.makeText(this, Utils.getResourceString(R.string.bao_cun_cheng_gong), Toast.LENGTH_SHORT).show();

            // 发送广播，通知刷新图库的显示
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

