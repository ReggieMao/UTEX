package com.utex.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.widget.ImageView;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.utex.R;
import com.utex.bean.TickerDo;
import com.utex.common.FiledConstants;
import com.utex.core.UTEXApplication;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.ResponseBody;

/**
 * Created by Demon on 2017/5/19.
 * 杂工具类，包含很多很多工具哦
 */

public class Utils {

    /**
     * md5加密
     */
    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取版本号
     */
    public static String getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 检查list
     */
    public static Integer checkList(List list) {
        return list == null ? 0 : list.size();
    }

    public static String getJSONString(JSONObject object, String key) {
        try {
            if (TextUtils.isEmpty(object.getString(key))) {
                return "0";
            } else {
                return object.getString(key);
            }
        } catch (Exception e) {
            return "0";
        }
    }

    /**
     * double转String,保留小数点后两位
     */
    public static String double2ToString(double num) {
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(num);
    }

    public static long string2long(String str, int type) {
        SimpleDateFormat simpleDateFormat = null;

        switch (type) {
            case 0:
                simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
                break;
            case 1:
                simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");

                break;
        }

        try {
            return simpleDateFormat.parse(str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 设置字体加粗
     */
    public static void setMonospaceFont(TextView textView) {
        textView.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
    }

    /**
     * 设置textView一句话多个字体
     */
    public static void setFontSpan(TextView textView, String[] strings, Integer... colors) {
        if (strings == null || colors == null || textView == null) {
            return;
        }

        if (strings.length != colors.length) {
            return;
        }

        String text = "";
        for (String str : strings) {
            text += str;
        }

        //设置字体前景色
        SpannableString msp = new SpannableString(text);

        int curPosition = 0;
        for (int i = 0; i < strings.length; i++) {
            msp.setSpan(new ForegroundColorSpan(Utils.getResourceColor(textView.getContext(), colors[i])), curPosition, curPosition + strings[i].length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            curPosition = i + strings[i].length();
        }
        textView.setText(msp);
    }

    /**
     * 处理错误请求
     */
    public static String doErrorBody(ResponseBody responseBody) {
        try {
            JSONObject jsonObject = JSON.parseObject(responseBody.string(), JSONObject.class);
            String message = jsonObject.getString("message");
            return message;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 显示错误提示
     */
    public static void showMessage(String str) {
        switch (str) {
            case "1110":
                str = Utils.getResourceString(R.string.yuan_mi_ma_cuo_wu);
                break;
            case "9007":
                str = Utils.getResourceString(R.string.xin_mi_ma_bu_de_yu_jiu_mi_ma_xiang_tong);
                break;
            case "1208":
                str = Utils.getResourceString(R.string.er_ci_yan_zheng_zhi_shao_kai_qi_yi_ge);
                break;
            case "9006":
                str = Utils.getResourceString(R.string.liang_ci_mi_ma_bu_tong);
                break;
            case "1104":
                str = Utils.getResourceString(R.string.ri_qi_ge_shi_bu_zheng_que);
                break;
            case "1102":
                str = Utils.getResourceString(R.string.shou_ji_ge_shi_bu_zheng_que);
                break;
            case "0":
                str = Utils.getResourceString(R.string.can_shu_cuo_wu);
                break;
            case "1106":
                str = Utils.getResourceString(R.string.shu_ru_bu_neng_wei_kong);
                break;
            case "1007":
                str = Utils.getResourceString(R.string.dong_jie_shen_he);
                break;
            case "1112":
                str = Utils.getResourceString(R.string.shou_ji_hao_yi_cun_zai);
                break;
            case "2007":
                str = Utils.getResourceString(R.string.gu_ge_mi_yao_cuo_wu);
                break;
            case "1002":
                str = getResourceString(R.string.yong_hu_shang_wei_zhu_ce);
                break;
            case "2002":
                str = getResourceString(R.string.yan_zheng_ge_shi_bu_zheng_que);
                break;
            case "2003":
                str = getResourceString(R.string.yan_zheng_ma_cuo_wu);
                break;
            case "3002":
                str = Utils.getResourceString(R.string.deng_lu_guo_qi);
                break;
            case "1207":
                str = Utils.getResourceString(R.string.yao_qing_ren_bu_cun_zai);
                break;
            case "1001":
                str = Utils.getResourceString(R.string.yong_hu_yi_cun_zai);
                break;
            case "1101":
                str = Utils.getResourceString(R.string.qing_que_ren_mi_ma_zai_ba_dao_shiliu_zi_mu_shu_zi);
                break;
            case "1103":
                str = Utils.getResourceString(R.string.email_bu_zheng_que);
                break;
            case "1105":
                str = Utils.getResourceString(R.string.shu_ru_zi_fu_tai_chang);
                break;
            case "1201":
                str = Utils.getResourceString(R.string.yong_hu_bu_cun_zai);
                break;
            case "2001":
                str = Utils.getResourceString(R.string.yan_zheng_ma_lei_xing_cuo_wu);
                break;
            case "2004":
                str = Utils.getResourceString(R.string.yan_zheng_ma_fa_song_guo_yu_ping_fan);
                break;
            case "9000":
                str = Utils.getResourceString(R.string.qing_shu_ru_you_xiang_zhang_hao);
                break;
            case "9001":
                str = Utils.getResourceString(R.string.yan_zheng_ma_bu_de_wei_kong);
                break;
            case "9002":
                str = Utils.getResourceString(R.string.qing_shu_ru_shou_ji_hao);
                break;
            case "1005":
                str = Utils.getResourceString(R.string.zhang_hao_yi_dong_jie);
                break;
            case "1111":
                str = Utils.getResourceString(R.string.yong_hu_ming_huo_mi_ma_cuo_wu);
                break;
            case "5001":
                str = Utils.getResourceString(R.string.fu_wu_qi_cuo_wu);
                break;
            case "1004":
                //进入请激活页面
                str = Utils.getResourceString(R.string.qing_ji_huo_zhang_hao);
                break;
            case "3003":
                str = Utils.getResourceString(R.string.liu_shi_miao_hou_chong_shi);
                break;
            case "AS01":
                str = Utils.getResourceString(R.string.jia_ge_bu_neng_wei_kong);
                break;
            case "AS02":
                str = Utils.getResourceString(R.string.yong_hu_deng_lu_yan_zheng_shi_bai);
                break;
            case "AS03":
                str = Utils.getResourceString(R.string.xia_dan_shi_bai);
                break;
            case "AS04":
                str = Utils.getResourceString(R.string.can_shu_yan_zheng_shi_bai);
                break;
            case "AS05":
                str = Utils.getResourceString(R.string.zi_chan_yu_e_bu_zu);
                break;
            case "AS06":
                str = Utils.getResourceString(R.string.ding_dan_fu_wu_yi_chang);
                break;
            case "AS07":
                str = Utils.getResourceString(R.string.yong_hu_id_bu_cun_zai);
                break;
            case "AS08":
                str = Utils.getResourceString(R.string.yong_hu_yi_yong_you_gai_zi_chan_bu_neng_zhuang_jian_xin_zi_chan);
                break;
            case "AS09":
                str = Utils.getResourceString(R.string.yong_hu_wu_fa_chuang_jian_zi_chan);
                break;
            case "AS10":
                str = Utils.getResourceString(R.string.chong_zhi_di_zhi_sheng_cheng_fu_wu_qi_chang);
                break;
            case "AS11":
                str = Utils.getResourceString(R.string.gai_bi_bu_neng_chong_zhi_huo_ti_xian);
                break;
            case "AS12":
                str = Utils.getResourceString(R.string.wei_tian_xie_tag);
                break;
            case "AS13":
                str = Utils.getResourceString(R.string.jiao_yi_guan_bi);
                break;
            case "AS14":
                str = Utils.getResourceString(R.string.gai_jiao_yi_dui_shang_wei_kai_tong_jiao_yi);
                break;
            case "AS15":
                str = Utils.getResourceString(R.string.ding_dan_shu_liang_bu_neng_xiao_yu_zui_xiao_xia_dan_shu);
                break;
            case "AS16":
                str = Utils.getResourceString(R.string.bi_zhong_bu_cun_zai);
                break;
            case "AS17":
                str = Utils.getResourceString(R.string.ti_xian_shu_liang_bu_neng_xiao_yu_zui_xiao_ti_xian_e_du);
                break;
            case "AS18":
                str = Utils.getResourceString(R.string.ti_xian_e_du_chao_guo_ri_ti_xian_e_du);
                break;
            case "AS19":
                str = Utils.getResourceString(R.string.qing_chong_xin_deng_lu);
                break;
            case "AS20":
                str = Utils.getResourceString(R.string.ti_xian_di_zhi_you_wu);
                break;
            case "AS21":
                str = Utils.getResourceString(R.string.duan_xin_fu_wu_yi_chang);
                break;
            case "AS22":
                str = Utils.getResourceString(R.string.ding_dan_bu_cun_zai);
                break;
            case "AS24":
                str = Utils.getResourceString(R.string.yan_zheng_ma_cuo_wu);
                break;
            case "ME01":
                str = Utils.getResourceString(R.string.fu_wu_chao_shi);
                break;
            case "ME02":
                str = Utils.getResourceString(R.string.cuo_wu_de_qing_qiu);
                break;
            case "ME03":
                str = Utils.getResourceString(R.string.nei_bu_cuo_wu);
                break;
            case "ME04":
                str = Utils.getResourceString(R.string.fang_fa_wei_fa_xian);
                break;
            case "ME05":
                str = Utils.getResourceString(R.string.bu_ke_yong_can_shu);
                break;
            case "ME06":
                str = Utils.getResourceString(R.string.fu_wu_bu_ke_yong);
                break;
            case "ME09":
                str = Utils.getResourceString(R.string.yong_hu_bu_pi_pei_ding_dan);
                break;
            case "ME10":
                str = Utils.getResourceString(R.string.wei_fa_xian_ding_dan);
                break;
            case "ME11":
                str = Utils.getResourceString(R.string.shu_liang_tai_xiao);
                break;
            case "ME12":
                str = Utils.getResourceString(R.string.mei_you_dui_shou_pan);
                break;
        }
        ToastUtils.show(str);
    }

    /**
     * 获取币种显示位数
     */
    public static int getTickerShowScale(TickerDo currTicker) {
        if (currTicker != null) {
            return Integer.parseInt(currTicker.getMoney_decimal()) - Integer.parseInt(currTicker.getAmount_decimal());
        } else {
            return 0;
        }
    }

    public static String eliminateZero(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("##########################.############################");
        return decimalFormat.format(value);
    }

    /**
     * 获取国家
     */
    public static String getZendaskLangugeCountry() {
        String country = SharedPreferencesUtil.getString(FiledConstants.SP_LANGUGE_ABBREVIATION, "tw");
        switch (country) {
            case "zh":
                return "zh-cn";
            case "jp":
            case "tw":
            case "en":
                return "en-us";
        }
        return country;
    }

    /**
     * Glide加载GIF
     */
    public static void loadGif(Context context, ImageView imageView) {
        Glide.with(context).load(R.drawable.loading).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable drawable, Transition<? super Drawable> transition) {
                if (drawable instanceof GifDrawable) {
                    GifDrawable gifDrawable = (GifDrawable) drawable;
                    gifDrawable.setLoopCount(2);
                    imageView.setImageDrawable(drawable);
                    gifDrawable.start();
                }
            }
        });
    }

    /**
     * 线程 调起照相机
     */
    public static class CameraThread implements Runnable {

        String mFilename, mFilePath;

        Activity activity;

        public CameraThread(String fileName, String filePath, Activity activity) {
            mFilename = fileName;
            mFilePath = filePath;
            this.activity = activity;
        }

        @Override
        public void run() {
            File parent = new File(mFilePath);
            if (!parent.exists()) {
                parent.mkdirs();
            }
            File pic = new File(parent, mFilename);
            if (!pic.exists()) {
                try {
                    pic.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(pic));
            activity.startActivityForResult(camera, Activity.DEFAULT_KEYS_DIALER);
        }
    }

    /**
     * 日期转换
     * 转换格式:2017.02.12
     */
    public static String long2String(long time, int status) {
        SimpleDateFormat simpleDateFormat = null;

        switch (status) {
            case 0:
                simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
                break;
            case 1:
                simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
                break;
            case 2:
                simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                break;
            case 3:
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                break;
            case 4:
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                break;
            case 5:
                simpleDateFormat = new SimpleDateFormat("HH:mm");
                break;
        }

        if (simpleDateFormat != null) {
            return simpleDateFormat.format(new Date(time));
        } else {
            return null;
        }
    }

    /**
     * 获取bks文件的sslsocketfactory
     */
    public static SSLSocketFactory getSSLSocketFactory(Context context) {
        final String CLIENT_TRUST_PASSWORD = "16761123";//信任证书密码，该证书默认密码是123456
        final String CLIENT_AGREEMENT = "TLS";//使用协议
        final String CLIENT_TRUST_KEYSTORE = "BKS";
        SSLContext sslContext = null;
        try {
            //取得SSL的SSLContext实例
            sslContext = SSLContext.getInstance(CLIENT_AGREEMENT);
            //取得TrustManagerFactory的X509密钥管理器实例
            TrustManagerFactory trustManager = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            //取得BKS密库实例
            KeyStore tks = KeyStore.getInstance(CLIENT_TRUST_KEYSTORE);
//            InputStream is = context.getResources().openRawResource(R.raw.quintar);
//            try {
//                tks.load(is, CLIENT_TRUST_PASSWORD.toCharArray());
//            } finally {
//                is.close();
//            }
            //初始化密钥管理器
            trustManager.init(tks);
            //初始化SSLContext
            sslContext.init(null, trustManager.getTrustManagers(), null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("SslContextFactory", e.getMessage());
        }
        return sslContext.getSocketFactory();
    }

    /**
     * 查询 url
     */
    public static List<String> getTeacherUrlList(String managers) {
        List<String> ls = new ArrayList<String>();
        Pattern pattern = Pattern
                .compile("((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?");
        Matcher matcher = pattern.matcher(managers);
        while (matcher.find()) {
            ls.add(matcher.group());
        }
        return ls;
    }

    /**
     * 获取资源文件中的值
     */
    public static String getResourceString(int resId) {
        if (UTEXApplication.getHomeActivity() != null) {
            return UTEXApplication.getHomeActivity().getString(resId);
        } else {
            return UTEXApplication.getInstance().getString(resId);
        }
    }

    /**
     * 获取资源文件中的值
     */
    public static int getResourceColor(Context context, int resId) {
        try {
            return ContextCompat.getColor(context, resId);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * map转list
     */
    public static List mapTransitionList(Map map) {
        List list = new ArrayList();
        Iterator iter = map.entrySet().iterator();  //获得map的Iterator
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            list.add(entry.getValue());
        }
        return list;
    }


    /**
     * 解决科学技术法问题
     */
    public static String getScientificCountingMethodAfterData(Double aDouble, Integer scale) {
        BigDecimal bigDecimal = new BigDecimal(aDouble.toString());
        bigDecimal = bigDecimal.setScale(scale, BigDecimal.ROUND_DOWN);
        return bigDecimal.stripTrailingZeros().toPlainString();
    }

    /**
     * 检查密码是否符合规则
     * 8-16字母数字
     */
    public static boolean checkPwd(String str) {
        if (!TextUtils.isEmpty(str)) {
            //正则判断密码规则
            String strPattern = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
            Pattern p = Pattern.compile(strPattern);
            Matcher m = p.matcher(str);
            if (m.matches()) {
                return true;
            } else {
                return false;
            }

        }

        return false;
    }

    /**
     * 传入价钱
     */
    public static String getStringFormatAmount(Double money, boolean isBtc) {
        String format;
        if (isBtc) {
            //GZH
            format = "%.8f";
        } else {
            if (money > 100) {
                format = "%.2f";
            } else if (money > 10) {
                format = "%.3f";
            } else if (money > 1) {
                format = "%.4f";
            } else if (money > 1e-3) {
                format = "%.5f";
            } else {
                format = "%.6f";
            }
        }
        return String.format(format, money);
    }

}
