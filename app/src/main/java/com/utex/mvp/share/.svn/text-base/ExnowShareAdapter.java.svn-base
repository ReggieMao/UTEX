package com.utex.mvp.share;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.utex.R;
import com.utex.common.SDUrl;
import com.utex.utils.BubbleUtils;
import com.utex.utils.Utils;
import com.utex.widget.popuwindow.LoadingPopupWindow;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Demon on 2018/10/16.
 */
public class ExnowShareAdapter extends RecyclerView.Adapter<ExnowShareAdapter.ExnowShareAdapterViewHolder> {

    private final UMShareListener mUmShareCall;
    private final String path;
    private final LoadingPopupWindow loadingPopupWindow;
    private View bottomView;
    private ImageView mImgLoading;

    public ExnowShareAdapter(UMShareListener umShareCall, String path, LoadingPopupWindow loadingPopupWindow, ImageView loading) {
        mUmShareCall = umShareCall;
        this.path = path;
        this.loadingPopupWindow = loadingPopupWindow;
        this.mImgLoading = loading;
    }

    @NonNull
    @Override
    public ExnowShareAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_share, parent, false);
        bottomView = LayoutInflater.from(parent.getContext()).inflate(R.layout.share_bottom, parent, false);
        return new ExnowShareAdapterViewHolder(inflate, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ExnowShareAdapterViewHolder holder, int position) {
        switch (position) {
            case 0:
                //微博
                holder.ivShareIcon.setImageResource(R.drawable.share_icon_weibo);
                holder.tvShareLabel.setText(Utils.getResourceString(R.string.wei_wo));
                break;
            case 1:
                //qq
                holder.ivShareIcon.setImageResource(R.drawable.share_icon_qq);
                holder.tvShareLabel.setText(Utils.getResourceString(R.string.qq));
                break;
            case 2:
                //QQ空间
                holder.ivShareIcon.setImageResource(R.drawable.share_icon_qzone);
                holder.tvShareLabel.setText(Utils.getResourceString(R.string.qq_kong_jian));
                break;
            case 3:
                //微信
                holder.ivShareIcon.setImageResource(R.drawable.share_icon_wechat);
                holder.tvShareLabel.setText(Utils.getResourceString(R.string.wei_xin));
                break;
            case 4:
                //微信 朋友圈
                holder.ivShareIcon.setImageResource(R.drawable.share_icon_circle);
                holder.tvShareLabel.setText(Utils.getResourceString(R.string.wei_xin_peng_you_quan));
                break;
//            case 5:
//                //facebook
//                holder.ivShareIcon.setImageResource(R.drawable.share_icon_fb);
//                holder.tvShareLabel.setText(Utils.getResourceString(R.string.facebook));
//                break;
//            case 6:
//                //twitter
//                holder.ivShareIcon.setImageResource(R.drawable.share_icon_twitter);
//                holder.tvShareLabel.setText(Utils.getResourceString(R.string.twitter));
//                break;
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ExnowShareAdapterViewHolder extends RecyclerView.ViewHolder {

        private int position;

        @BindView(R.id.iv_share_icon)
        ImageView ivShareIcon;

        @BindView(R.id.tv_share_label)
        TextView tvShareLabel;

        public ExnowShareAdapterViewHolder(View itemView, int viewType) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.position = viewType;
        }

        @OnClick({R.id.ll_share_parent})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ll_share_parent:
                    click(view, position);
                    new Handler()
                            .postDelayed(() -> {
                                loadingPopupWindow.show();
                                mImgLoading.setVisibility(View.GONE);
                            }, 300);
                    break;
            }
        }
    }

    private void click(View view, int position) {
        Bitmap bottomBitmap = getViewBitmap(bottomView);

        Bitmap topBitmap = BitmapFactory.decodeFile(path);

        Bitmap bitmap = mergeBitmap_TB(topBitmap, bottomBitmap, false);
        String newPath = saveBitmap(view.getContext(), bitmap);
        bitmap = BitmapFactory.decodeFile(newPath);
        switch (position) {
            case 0:
                //微博
                new ShareAction((Activity) view.getContext())
                        .setPlatform(SHARE_MEDIA.SINA)//传入平台
                        .withMedia(new UMImage(view.getContext(), bitmap))
                        .setCallback(mUmShareCall)//回调监听器
                        .share();
                break;
            case 1:
                //qq
                new ShareAction((Activity) view.getContext())
                        .setPlatform(SHARE_MEDIA.QQ)//传入平台
                        .withMedia(new UMImage(view.getContext(), bitmap))
                        .setCallback(mUmShareCall)//回调监听器
                        .share();
                break;
            case 2:
                //QQ空间
                new ShareAction((Activity) view.getContext())
                        .setPlatform(SHARE_MEDIA.QZONE)//传入平台
                        .withMedia(new UMImage(view.getContext(), bitmap))
                        .setCallback(mUmShareCall)//回调监听器
                        .share();
                break;
            case 3:
                //微信
                new ShareAction((Activity) view.getContext())
                        .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                        .withMedia(new UMImage(view.getContext(), bitmap))
                        .setCallback(mUmShareCall)//回调监听器
                        .share();
                break;
            case 4:
                //微信 朋友圈
                new ShareAction((Activity) view.getContext())
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)//传入平台
                        .withMedia(new UMImage(view.getContext(), bitmap))
                        .setCallback(mUmShareCall)//回调监听器
                        .share();
                break;
            case 5:
                //facebook
                new ShareAction((Activity) view.getContext())
                        .setPlatform(SHARE_MEDIA.FACEBOOK)//传入平台
                        .withMedia(new UMImage(view.getContext(), bitmap))
                        .setCallback(mUmShareCall)//回调监听器
                        .share();
                break;
            case 6:
                //twitter
                new ShareAction((Activity) view.getContext())
                        .setPlatform(SHARE_MEDIA.TWITTER)//传入平台
                        .withMedia(new UMImage(view.getContext(), bitmap))
                        .setCallback(mUmShareCall)//回调监听器
                        .share();
                break;
        }
    }

    /**
     * 把两个位图覆盖合成为一个位图，上下拼接
     *
     * @param isBaseMax 是否以高度大的位图为准，true则小图等比拉伸，false则大图等比压缩
     * @return
     */
    public static Bitmap mergeBitmap_TB(Bitmap topBitmap, Bitmap bottomBitmap, boolean isBaseMax) {

        if (topBitmap == null || topBitmap.isRecycled()
                || bottomBitmap == null || bottomBitmap.isRecycled()) {
//            JDLog.logError(TAG, "topBitmap=" + topBitmap + ";bottomBitmap=" + bottomBitmap);
            return null;
        }

        int width = 0;
        if (isBaseMax) {
            width = topBitmap.getWidth() > bottomBitmap.getWidth() ? topBitmap.getWidth() : bottomBitmap.getWidth();
        } else {
            width = topBitmap.getWidth() < bottomBitmap.getWidth() ? topBitmap.getWidth() : bottomBitmap.getWidth();
        }

        Bitmap tempBitmapT = topBitmap;
        Bitmap tempBitmapB = bottomBitmap;

        if (topBitmap.getWidth() != width) {
//            tempBitmapT = Bitmap.createScaledBitmap(topBitmap, width , (int) (topBitmap.getHeight() * 1f / topBitmap.getWidth() * width), false);
            tempBitmapT = Bitmap.createScaledBitmap(topBitmap, width, (int) (topBitmap.getHeight() * 1f / topBitmap.getWidth() * width), false);
        } else if (bottomBitmap.getWidth() != width) {
            tempBitmapB = Bitmap.createScaledBitmap(bottomBitmap, width, (int) (bottomBitmap.getHeight() * 1f / bottomBitmap.getWidth() * width), false);
        }

        tempBitmapT = Bitmap.createScaledBitmap(topBitmap, BubbleUtils.dp2px(303), BubbleUtils.dp2px(538), false);

        int height = tempBitmapT.getHeight() + tempBitmapB.getHeight() + BubbleUtils.dp2px(40);

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.parseColor("#f7f7fa"));
        Rect topRect = new Rect(BubbleUtils.dp2px(10), BubbleUtils.dp2px(20), tempBitmapT.getWidth(), tempBitmapT.getHeight());
        Rect bottomRect = new Rect(0, 0, tempBitmapB.getWidth(), tempBitmapB.getHeight());

        Rect bottomRectT = new Rect(0, tempBitmapT.getHeight() + BubbleUtils.dp2px(40), width, height);

        int i = (width - tempBitmapT.getWidth()) / 2;

        canvas.drawBitmap(tempBitmapT, i, BubbleUtils.dp2px(20), null);
        canvas.drawBitmap(tempBitmapB, bottomRect, bottomRectT, null);
        return bitmap;
    }

    /**
     * 随机生产文件名
     *
     * @return
     */
    private static String generateFileName() {
        return UUID.randomUUID().toString();
    }

    /**
     * 保存bitmap到本地
     *
     * @param context
     * @param mBitmap
     * @return
     */
    public static String saveBitmap(Context context, Bitmap mBitmap) {
        String savePath;
        File filePic;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            savePath = SDUrl.picPath;
        } else {
            savePath = context.getApplicationContext().getFilesDir()
                    .getAbsolutePath()
                    + SDUrl.picPath;
        }
        try {
            filePic = new File(savePath + generateFileName() + ".jpg");
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return filePic.getAbsolutePath();
    }


    private Bitmap getViewBitmap(View addViewContent) {

        addViewContent.setDrawingCacheEnabled(true);

        addViewContent.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        addViewContent.layout(0, 0,
                addViewContent.getMeasuredWidth(),
                addViewContent.getMeasuredHeight());

        addViewContent.buildDrawingCache();
        Bitmap cacheBitmap = addViewContent.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        return bitmap;
    }

}
