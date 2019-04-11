package com.utex.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.utex.common.SDUrl;
import com.uuzuche.lib_zxing.DisplayUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 截图操作
 * Created by Shall on 2015-07-22.
 */
public class ScreenshotUtils {

    private final static String FILE_SAVEPATH = SDUrl.picPath;
    public static String pathfile = "";
    public static int h = 0;

    /**
     * 截取scrollview的屏幕
     *
     * @param scrollView
     * @return
     */
    public static Bitmap getBitmapByView(ScrollView scrollView, int in) {
        int h = 0;
        Bitmap bitmap = null;
        // 获取scrollview实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            if (in == 0) {
//                scrollView.getChildAt(i).setBackgroundColor(0xff000000);
            }
            if (in == 1) {
//                scrollView.getChildAt(i).setBackgroundResource(R.drawable.task_bac);
            }
        }

        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getMeasuredWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }

    /**
     * 截取scrollview的屏幕
     *
     * @param scrollView
     * @return
     */
    public static Bitmap getBitmapByView(LinearLayout scrollView, int in) {
        int h = 0;
        Bitmap bitmap = null;
        // 获取scrollview实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            if (in == 0) {
//                scrollView.getChildAt(i).setBackgroundColor(0xff000000);
            }
            if (in == 1) {
//                scrollView.getChildAt(i).setBackgroundResource(R.drawable.task_bac);
            }
        }

        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getMeasuredWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }


    /**
     * 截取scrollview的屏幕
     *
     * @param scrollView
     * @return
     */
    public static Bitmap getBitmapByView(RelativeLayout scrollView, int in) {
        int h = 0;
        Bitmap bitmap = null;
        // 获取scrollview实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            if (in == 0) {
//                scrollView.getChildAt(i).setBackgroundColor(0xff000000);
            }
            if (in == 1) {
//                scrollView.getChildAt(i).setBackgroundResource(R.drawable.task_bac);
            }
        }

        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getMeasuredWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }

    /**
     * 截取scrollview的屏幕
     *
     * @param scrollView
     * @return
     */
    public static Bitmap getBitmapByView(FrameLayout scrollView, int in) {
        int h = 0;
        Bitmap bitmap = null;
        // 获取scrollview实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            if (in == 0) {
//                scrollView.getChildAt(i).setBackgroundColor(0xff000000);
            }
            if (in == 1) {
//                scrollView.getChildAt(i).setBackgroundResource(R.drawable.task_bac);
            }
        }

        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getMeasuredWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }


    /**
     * 截取scrollview的屏幕
     *
     * @param height     传入要截取的高度
     * @param scrollView
     * @return
     */
    public static Bitmap getBitmapByView(ScrollView scrollView, int in, int height) {
        int h = height;
        // 创建对应大小的bitmap
        Bitmap bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }


    /**
     * 压缩图片
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
        while (baos.toByteArray().length / 1024 > 500) {
            // 重置baos
            baos.reset();
            // 这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);

            // 每次都减少10
            if (options > 20) {
                options -= 10;
            } else {
                options -= 2;
            }

            Log.e("TAG", "压缩：" + options + "---大小:" + baos.toByteArray().length / 1024);
        }

        // 把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        // 把ByteArrayInputStream数据生成图片
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);


        return bitmap;
    }


    public static Bitmap getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    /**
     * 压缩图片
     *
     * @param image
     * @param foreImageUrl
     * @return
     */
    public static Bitmap compressImage(Bitmap image, String foreImageUrl) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
        while (baos.toByteArray().length / 1024 > 500) {
            // 重置baos
            baos.reset();
            // 这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);

            // 每次都减少10
            if (options > 20) {
                options -= 10;
            } else {
                options -= 2;
            }

            Log.e("TAG", "压缩：" + options + "---大小:" + baos.toByteArray().length / 1024);
        }

        // 把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        // 把ByteArrayInputStream数据生成图片
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);


        File file = new File(foreImageUrl);
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    /**
     * 三张图片拼接
     */
    public static Bitmap toConformBitmap(Bitmap head, Bitmap kebiao, Bitmap san, int px) {

        int headWidth = head.getWidth();
        int kebianwidth = kebiao.getWidth();
        int fotwid = san.getWidth();

        int headHeight = head.getHeight();
        int kebiaoheight = kebiao.getHeight();
        int footerheight = san.getHeight();
        //生成三个图片合并大小的Bitmap
        Bitmap newbmp = Bitmap.createBitmap(kebianwidth, headHeight + kebiaoheight + footerheight + px / 2, Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newbmp);

        cv.drawBitmap(head, 0, 0, null);// 在 0，0坐标开始画入headBitmap

        //因为手机不同图片的大小的可能小了 就绘制白色的界面填充剩下的界面
        if (headWidth < kebianwidth) {
            System.out.println("绘制头");
            Bitmap ne = Bitmap.createBitmap(kebianwidth - headWidth, headHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(ne);
            canvas.drawColor(Color.WHITE);
            cv.drawBitmap(ne, headWidth, 0, null);
        }

        cv.drawBitmap(kebiao, 0, headHeight, null);// 在 0，headHeight坐标开始

        cv.drawBitmap(san, 0, headHeight + kebiaoheight + px / 2, null);// 在 0，headHeight + kebiaoheight坐标开始

        //因为手机不同图片的大小的可能小了 就绘制白色的界面填充剩下的界面
        if (fotwid < kebianwidth) {
            System.out.println("绘制");
            Bitmap ne = Bitmap.createBitmap(kebianwidth - fotwid, footerheight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(ne);
            canvas.drawColor(Color.WHITE);
            cv.drawBitmap(ne, fotwid, headHeight + kebiaoheight, null);
        }
        cv.save(Canvas.ALL_SAVE_FLAG);// 保存
        cv.restore();// 存储
        //回收
        head.recycle();
        kebiao.recycle();
        san.recycle();
        return newbmp;
    }

    /**
     * 两张图片拼接
     */
    public static Bitmap toConformBitmapTwo(Bitmap head, Bitmap kebiao, int px) {
        int headWidth = head.getWidth();
        int kebianwidth = kebiao.getWidth();

        int headHeight = head.getHeight();
        int kebiaoheight = kebiao.getHeight();

        //生成两个图片合并大小的Bitmap
        Bitmap newbmp = Bitmap.createBitmap(kebianwidth, headHeight + kebiaoheight, Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newbmp);

        cv.drawBitmap(head, 0, 0, null);// 在 0，0坐标开始画入headBitmap

        //因为手机不同图片的大小的可能小了 就绘制白色的界面填充剩下的界面
        if (headWidth < kebianwidth) {
            System.out.println("绘制头");
            Bitmap ne = Bitmap.createBitmap(kebianwidth - headWidth, headHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(ne);
            canvas.drawColor(Color.WHITE);
            cv.drawBitmap(ne, headWidth, 0, null);
        }
        cv.drawBitmap(kebiao, 0, headHeight + px, null);// 在 0，headHeight坐标开始

        cv.save(Canvas.ALL_SAVE_FLAG);// 保存
        cv.restore();// 存储
        //回收
        head.recycle();
        kebiao.recycle();
        return newbmp;
    }

    /**
     * 保存到sdcard
     *
     * @param b
     * @return
     */
    public static String savePic(Bitmap b, String path, String fname) {
        File outfile = new File(path);  //文件夹
        // 如果文件不存在，则创建一个新文件
        if (!outfile.isDirectory()) {
            try {
                outfile.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fname);
            if (null != fos) {
                b.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fname;
    }

    public static Bitmap getBitmapByView(View view) {
        Bitmap bitmap11 = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas11 = new Canvas(bitmap11);
        view.draw(canvas11);
        return Bitmap.createBitmap(bitmap11, 0, 0, view.getWidth(), view.getHeight());
    }

    public static File saveBitmap(String filePath, Bitmap mBitmap) {
        File f = new File(filePath);
        try {
            if (f.exists()) {
                f.delete();
            }

            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }

    /**
     * 图片上画文字
     *
     * @param bitmap
     * @param text   文字内容
     * @param textX  文字X坐标
     * @param textY  文字Y坐标
     * @return Bitmap
     */
    public static Bitmap drawTextAtBitmap(Context context, Bitmap bitmap, String text, float textX, float textY, Resources r) {
        int x = bitmap.getWidth();
        int y = bitmap.getHeight();

        // 创建一个和原图同样大小的位图
        Bitmap newbit = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(newbit);

        Paint paint = new Paint();

        // 在原始位置0，0插入原图
        canvas.drawBitmap(bitmap, 0, 0, paint);
//        paint.setColor(r.getColor(Color.WHITE));
        paint.setTextSize(DisplayUtil.dip2px(context, 50));

        // 在原图指定位置写上字
        canvas.drawText(text, textX, textY, paint);

        canvas.save(Canvas.ALL_SAVE_FLAG);

        // 存储
        canvas.restore();

        return newbit;
    }

    /**
     * 更改图片大小 资源文件中的
     *
     * @param id            路径
     * @param displayWidth  需要显示的宽度
     * @param displayHeight 需要显示的高度
     * @return Bitmap
     */
    public static Bitmap decodeBitmap(int id, int displayWidth, int displayHeight, Resources r) {
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inJustDecodeBounds = true;
        // op.inJustDecodeBounds = true;表示我们只读取Bitmap的宽高等信息，不读取像素。
        Bitmap bmp = BitmapFactory.decodeResource(r, id, op);
        // op.outWidth表示的是图像真实的宽度
        // op.inSamplySize 表示的是缩小的比例
        // op.inSamplySize = 4,表示缩小1/4的宽和高，1/16的像素，android认为设置为2是最快的。
        // 获取比例大小
        int wRatio = (int) Math.ceil(op.outWidth / (float) displayWidth);
        int hRatio = (int) Math.ceil(op.outHeight / (float) displayHeight);
        // 如果超出指定大小，则缩小相应的比例
        if (wRatio > 1 && hRatio > 1) {
            if (wRatio > hRatio) {
                // 如果太宽，我们就缩小宽度到需要的大小，注意，高度就会变得更加的小。
                op.inSampleSize = wRatio;
            } else {
                op.inSampleSize = hRatio;
            }
        }
        op.inJustDecodeBounds = false;
        bmp = BitmapFactory.decodeResource(r, id, op);
        // 从原Bitmap创建一个给定宽高的Bitmap
        return Bitmap.createScaledBitmap(bmp, displayWidth, displayHeight, true);
    }


    /**
     * 更改图片大小 更改本地文件中的
     *
     * @param id            路径
     * @param displayWidth  需要显示的宽度
     * @param displayHeight 需要显示的高度
     * @return Bitmap
     */
    public static Bitmap decodeBitmap(String id, int displayWidth, int displayHeight, Resources r) {
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inJustDecodeBounds = true;
        // op.inJustDecodeBounds = true;表示我们只读取Bitmap的宽高等信息，不读取像素。
        Bitmap bmp = BitmapFactory.decodeFile(id, op);
        // op.outWidth表示的是图像真实的宽度
        // op.inSamplySize 表示的是缩小的比例
        // op.inSamplySize = 4,表示缩小1/4的宽和高，1/16的像素，android认为设置为2是最快的。
        // 获取比例大小
        int wRatio = (int) Math.ceil(op.outWidth / (float) displayWidth);
        int hRatio = (int) Math.ceil(op.outHeight / (float) displayHeight);
        // 如果超出指定大小，则缩小相应的比例
        if (wRatio > 1 && hRatio > 1) {
            if (wRatio > hRatio) {
                // 如果太宽，我们就缩小宽度到需要的大小，注意，高度就会变得更加的小。
                op.inSampleSize = wRatio;
            } else {
                op.inSampleSize = hRatio;
            }
        }
        op.inJustDecodeBounds = false;
        bmp = BitmapFactory.decodeFile(id, op);
        // 从原Bitmap创建一个给定宽高的Bitmap
        return Bitmap.createScaledBitmap(bmp, displayWidth, displayHeight, true);
    }


    /**
     * 更改图片大小 更改本地文件中的
     *
     * @param bitmap        路径
     * @param displayWidth  需要显示的宽度
     * @param displayHeight 需要显示的高度
     * @return Bitmap
     */
    public static Bitmap decodeBitmap(Bitmap bitmap, int displayWidth, int displayHeight, Resources r) {
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inJustDecodeBounds = true;
        // op.inJustDecodeBounds = true;表示我们只读取Bitmap的宽高等信息，不读取像素。
        Bitmap bmp = bitmap;
        // op.outWidth表示的是图像真实的宽度
        // op.inSamplySize 表示的是缩小的比例
        // op.inSamplySize = 4,表示缩小1/4的宽和高，1/16的像素，android认为设置为2是最快的。
        // 获取比例大小
        int wRatio = (int) Math.ceil(op.outWidth / (float) displayWidth);
        int hRatio = (int) Math.ceil(op.outHeight / (float) displayHeight);
        // 如果超出指定大小，则缩小相应的比例
        if (wRatio > 1 && hRatio > 1) {
            if (wRatio > hRatio) {
                // 如果太宽，我们就缩小宽度到需要的大小，注意，高度就会变得更加的小。
                op.inSampleSize = wRatio;
            } else {
                op.inSampleSize = hRatio;
            }
        }
        op.inJustDecodeBounds = false;
        bmp = bitmap;
        // 从原Bitmap创建一个给定宽高的Bitmap
        return Bitmap.createScaledBitmap(bmp, displayWidth, displayHeight, true);
    }


    /**
     * 旋转图片
     *
     * @param angle
     * @param bitmap
     * @return
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


}
