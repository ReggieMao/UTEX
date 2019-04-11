package com.utex.mvp.block.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.utex.R;
import com.utex.base.BaseActivity;
import com.utex.bean.UserDO;
import com.utex.common.FiledConstants;
import com.utex.common.SDUrl;
import com.utex.common.UMConstants;
import com.utex.core.AppComponent;
import com.utex.core.UTEXApplication;
import com.utex.data.ApiServiceModule;
import com.utex.listener.ETtextChangeListener;
import com.utex.mvp.block.dagger2.BlockModule;
import com.utex.mvp.block.dagger2.DaggerBlockComponent;
import com.utex.mvp.block.presenter.IBlockPresenter;
import com.utex.mvp.mine.view.MoneyOperateRecordActivity;
import com.utex.mvp.mine.view.WithdrawalDetailActivity;
import com.utex.mvp.order.bean.DepositWithdrawalVO;
import com.utex.mvp.user.view.LoginActivity;
import com.utex.mvp.web.WebActivity;
import com.utex.utils.ArithmeticUtil;
import com.utex.utils.ToastUtils;
import com.utex.utils.Utils;
import com.utex.widget.popuwindow.BlockTransferConfirmPopupWindow;
import com.utex.widget.popuwindow.WithdrawalVerificationPopupWindow;
import com.umeng.analytics.MobclickAgent;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yanzhenjie.permission.AndPermission;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 1 充值
 * 2 提现
 */
public class BlockActivity extends BaseActivity implements IBlockView {

    private static final int REQUEST_CODE = 1;

    @Inject
    IBlockPresenter iBlockPresenter;

    @BindView(R.id.tv_page_title)
    TextView tvPageTitle;

    @BindView(R.id.tv_page_tip)
    TextView tvPageTip;

    private TextView tvPageAddress;

    private int type;

    /**
     * 是否可以提现 默认不可以
     */
    private Boolean isWithdrawal;
    private Bitmap image;
    private String coinCode;
    private EditText etPageAddress;

    /**
     * 最小提现数量
     */
    private double withdrawalMinAmount;
    private EditText etPageAmount;
    private TextView tvPageArriveMoney;
    private TextView tvPageWithdrawMoney;
    private String withdrawalFee = "0";

    private int emailSecond = 60;
    private int smsSecond = 60;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    if (tvEmailSecond == null) {
                        return false;
                    }

                    tvEmailSecond.setText("(" + String.valueOf(emailSecond) + ")s" + Utils.getResourceString(R.string.chong_xin_fa_song));
                    tvEmailSecond.setTextColor(Utils.getResourceColor(BlockActivity.this, R.color.bd4d6d5_33ffffff));

                    emailSecond--;
                    if (emailSecond > 0) {
                        handler.sendEmptyMessageDelayed(0, 1000);
                    } else {
                        emailSecond = 60;
                        tvEmailSecond.setText(Utils.getResourceString(R.string.fa_song_yan_zheng_ma));
                        tvEmailSecond.setTextColor(Utils.getResourceColor(BlockActivity.this, R.color.f50b577));
                    }
                    break;
                case 1:
                    if (tvSmsSecond == null) {
                        return false;
                    }

                    tvSmsSecond.setText("(" + String.valueOf(smsSecond) + ")s" + Utils.getResourceString(R.string.chong_xin_fa_song));
                    tvSmsSecond.setTextColor(Utils.getResourceColor(BlockActivity.this, R.color.bd4d6d5_33ffffff));

                    smsSecond--;
                    if (smsSecond > 0) {
                        handler.sendEmptyMessageDelayed(1, 1000);
                    } else {
                        smsSecond = 60;
                        tvSmsSecond.setText(Utils.getResourceString(R.string.fa_song_yan_zheng_ma));
                        tvSmsSecond.setTextColor(Utils.getResourceColor(BlockActivity.this, R.color.f50b577));
                    }
                    break;
            }
            return false;
        }
    });

    private TextView tvEmailSecond;
    private TextView tvSmsSecond;

    private EditText etPageTag;
    private TextView tvPageCnc;
    private RelativeLayout rlDepositPageCncParent;
    private RelativeLayout rlDepositPageTagParent;
    private TextView tvDepositCncAddress;
    private TextView tvDepositCncTag;
    private TextView tvDepositPageTag;
    private boolean isBtc;
    private int showScale;
    private RadioGroup rgWithdrawalDiection;
    private int widthdrawalStatus;
    private TextView tvWidthdrawalPageAllCoin;
    private double availableFund;
    private String withdrawalTips;
    private TextView tvPageFee;
    private boolean tag;
    private int assetId;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getIntExtra(FiledConstants.TYPE, 1);
        coinCode = getIntent().getStringExtra(FiledConstants.COIN_CODE);
        showScale = getIntent().getIntExtra(FiledConstants.SHOW_SCALE, 0);
        assetId = getIntent().getIntExtra(FiledConstants.ASSET_ID, 0);
        isBtc = false;

        if ("gzh".equals(coinCode)) {
            isBtc = true;
        }

        switch (type) {
            case 1:
                setContentView(R.layout.activity_deposit_page);
                ButterKnife.bind(this);
                iBlockPresenter.getDepositAddress(coinCode);
                tvPageTitle.setText(coinCode.toUpperCase() + Utils.getResourceString(R.string.chong_zhi));

                tvPageAddress = findViewById(R.id.tv_page_address);
                tvPageCnc = findViewById(R.id.tv_page_cnc);

                rlDepositPageCncParent = findViewById(R.id.rl_deposit_page_cnc_parent);
                rlDepositPageTagParent = findViewById(R.id.rl_deposit_page_tag_parent);
                tvDepositCncAddress = findViewById(R.id.tv_deposit_cnc_address);
                tvDepositCncTag = findViewById(R.id.tv_deposit_cnc_tag);
                tvDepositPageTag = findViewById(R.id.tv_deposit_page_tag);


                findViewById(R.id.tv_page_copy).setOnClickListener(view -> {
                    // 从API11开始android推荐使用android.content.ClipboardManager
                    // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    cm.setText(tvPageAddress.getText());
                    Toast.makeText(BlockActivity.this, Utils.getResourceString(R.string.fu_zhi_cheng_gong), Toast.LENGTH_SHORT).show();
                    MobclickAgent.onEvent(BlockActivity.this, UMConstants.ASSET_DEPOSIT_COPY_ADDRESS_BTTN);
                });

                findViewById(R.id.tv_page_save_qrcode).setOnClickListener(view -> {
                    if ("cnc".equals(coinCode.toLowerCase())) {
                        //是cnc跳入一个h5
                        Intent intent = new Intent(BlockActivity.this, WebActivity.class);
                        intent.putExtra(FiledConstants.LINK_URL, ApiServiceModule.CNC_DEPOSIT_URL);
                        startActivity(intent);
                    } else {
                        boolean b = AndPermission.hasPermissions(BlockActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        if (b) {
                            MobclickAgent.onEvent(BlockActivity.this, UMConstants.ASSET_DEPOSIT_SAVE_QRCODE_PHOTO);
                            saveBitmapFile(image);
                        } else {
                            Utils.getResourceString(R.string.qing_qu_shou_ji_she_zhi_zhong_da_kai_quan_xian);
                        }
                    }
                });
                if ("cnc".equals(coinCode.toLowerCase())) {
                    TextView tv = findViewById(R.id.tv_page_save_qrcode);
                    tv.setText(Utils.getResourceString(R.string.yi_cheng_dui));
                }
                break;
            case 2:
                setContentView(R.layout.activity_withdrawal_page);
                ButterKnife.bind(this);
                tvPageTitle.setText(coinCode.toUpperCase() + Utils.getResourceString(R.string.ti_xian));
                iBlockPresenter.getWithdrawalAddress(coinCode);

                availableFund = Double.parseDouble(getIntent().getStringExtra(FiledConstants.AVAILABLE_FUND));

                etPageAddress = findViewById(R.id.et_page_address);
                etPageAmount = findViewById(R.id.et_page_amount);
                tvPageArriveMoney = findViewById(R.id.tv_page_arrive_money);
                etPageTag = findViewById(R.id.et_page_tag);
                rgWithdrawalDiection = findViewById(R.id.rg_withdrawal_direction);
                tvWidthdrawalPageAllCoin = findViewById(R.id.tv_withdrawal_page_all_coin);
                tvPageFee = findViewById(R.id.tv_page_fee);


                rgWithdrawalDiection.setOnCheckedChangeListener((group, checkedId) -> {
                    Drawable drawableRight = getResources().getDrawable(
                            R.drawable.property_icon_sys);
                    Drawable drawableBottom = getResources().getDrawable(
                            R.drawable.shape_login_et_bottom_line_bg);
                    etPageAmount.setHint(Utils.getResourceString(R.string.shu_liang));
                    etPageAddress.setText("");
                    etPageAmount.setText("");
                    etPageTag.setText("");

                    switch (checkedId) {
                        case R.id.rg_withdrawal_coin:
                            //提币
                            tvPageFee.setVisibility(View.VISIBLE);
                            widthdrawalStatus = 0;

                            etPageAddress.setHint(Utils.getResourceString(R.string.ti_bi_di_zhi));

                            if (tag) {
                                if ("cnc".equals(coinCode) || " eos".equals(coinCode)) {
                                    etPageTag.setHint("MEMO(" + Utils.getResourceString(R.string.xuan_tian) + ")");
                                } else {
                                    etPageTag.setHint("TAG(" + Utils.getResourceString(R.string.xuan_tian) + ")");
                                }
                                etPageTag.setVisibility(View.VISIBLE);
                            } else {
                                etPageTag.setVisibility(View.GONE);
                            }

                            etPageAddress.setCompoundDrawablesWithIntrinsicBounds(null,
                                    null, drawableRight, drawableBottom);

                            if (!TextUtils.isEmpty(withdrawalTips)) {
                                if (Build.VERSION.SDK_INT >= 24) {
                                    tvPageTip.setText(Html.fromHtml(withdrawalTips, Html.FROM_HTML_MODE_COMPACT));
                                } else {
                                    tvPageTip.setText(Html.fromHtml(withdrawalTips));
                                }
                            }

                            Utils.setFontSpan(tvPageFee, new String[]{Utils.getResourceString(R.string.fei_lv) + "：", Utils.getScientificCountingMethodAfterData(Double.valueOf(withdrawalFee), showScale) + " " + coinCode.toUpperCase()},
                                    new Integer[]{R.color.b999999_e6ffffff, R.color.b333333_ffffff});

                            tvPageArriveMoney.setText(Utils.getResourceString(R.string.dao_zhang) + " 0" + " " + coinCode.toUpperCase());
                            break;
                        case R.id.rg_withdrawal_transfer:
                            //转账
                            widthdrawalStatus = 1;

                            etPageAddress.setCompoundDrawablesWithIntrinsicBounds(null,
                                    null, null, drawableBottom);
                            etPageAddress.setHint(Utils.getResourceString(R.string.dui_fang_uid));
                            etPageTag.setVisibility(View.GONE);
                            tvPageFee.setVisibility(View.GONE);

                            Utils.setFontSpan(tvPageFee, new String[]{Utils.getResourceString(R.string.fei_lv) + "：", Utils.getScientificCountingMethodAfterData(Double.valueOf(withdrawalFee), showScale) + " " + coinCode.toUpperCase()},
                                    new Integer[]{R.color.b999999_e6ffffff, R.color.b333333_ffffff});

                            tvPageArriveMoney.setText(Utils.getResourceString(R.string.dao_zhang) + " 0" + " " + coinCode.toUpperCase());

                            String str = getString(R.string.zhan_nei_zhuan_zhang_tip);
                            if (UTEXApplication.isZhLanguage()) {
                                str = "<b>【提币】与【站内转账】的区别是什么？</b><p>提币：有手续费；到账需要区块确认</p><p>站内转账：无手续费；无需区块确认，快速到账</p><b>温馨提示：</b><p>1.站内转账是指您可以将币种转至\"UTEX\"的其他账户中</p><P>2.站内转账一经提交，无法撤销</P>";
                            } else {
                                str = "<b>What's the difference between withdrawal and in-station transfer?</b><p>Currency withdrawal: there is a handling fee; the arrival of the account requires block confirmation</p><p>In-station transfer: no handling fee; no block confirmation, fast arrival</p><b>Reminder：</b><p>1. In-station transfer means that you can transfer currency to other UTEX accounts.</p><P>2.Once the transfer is submitted, it cannot be revoked.</P>";
                            }


                            if (Build.VERSION.SDK_INT >= 24) {
                                tvPageTip.setText(Html.fromHtml(str, Html.FROM_HTML_MODE_COMPACT));
                            } else {
                                tvPageTip.setText(Html.fromHtml(str));
                            }

                            break;
                    }
                });

                if (UTEXApplication.getLoginUser().getTransfer_switch()) {
                    //开启
                    rgWithdrawalDiection.setVisibility(View.VISIBLE);
                } else {
                    //关闭
                    rgWithdrawalDiection.setVisibility(View.GONE);

                }

                tvWidthdrawalPageAllCoin.setOnClickListener(v -> {
                    etPageAmount.setText(String.valueOf(availableFund));
                });

                etPageAddress.setOnTouchListener((view, motionEvent) -> {
                    // et.getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
                    Drawable drawable = etPageAddress.getCompoundDrawables()[2];
                    //如果右边没有图片，不再处理
                    if (drawable == null)
                        return false;
                    //如果不是按下事件，不再处理
                    if (motionEvent.getAction() != MotionEvent.ACTION_UP)
                        return false;
                    if (motionEvent.getX() > etPageAddress.getWidth()
                            - etPageAddress.getPaddingRight()
                            - drawable.getIntrinsicWidth()) {

                        boolean b = AndPermission.hasPermissions(BlockActivity.this, Manifest.permission.CAMERA);
                        if (b) {
                            MobclickAgent.onEvent(BlockActivity.this, UMConstants.ASSET_WITHDRAW_QR_CODE);
                            //跳出二维码
                            Intent intent = new Intent(BlockActivity.this, CaptureActivity.class);
                            startActivityForResult(intent, REQUEST_CODE);
                        } else {
                            ToastUtils.show(Utils.getResourceString(R.string.qing_qu_shou_ji_she_zhi_zhong_da_kai_quan_xian));
                        }
                    }
                    return false;
                });

                tvPageWithdrawMoney = findViewById(R.id.tv_page_withdraw_money);
                tvPageWithdrawMoney.setOnClickListener(view -> {

                    MobclickAgent.onEvent(BlockActivity.this, UMConstants.ASSET_WITHDRAW_BOTTOM_WITHDRAW_BTN);

                    if (TextUtils.isEmpty(etPageAmount.getText())) {
                        ToastUtils.show(Utils.getResourceString(R.string.qing_shu_ru_shu_liang));
                        return;
                    }


                    double doubleExtra = Double.parseDouble(getIntent().getStringExtra(FiledConstants.AVAILABLE_FUND));

                    if (doubleExtra - Double.parseDouble(etPageAmount.getText().toString()) < 0) {
                        Toast.makeText(BlockActivity.this, Utils.getResourceString(R.string.ti_bi_shu_liang_bu_de_da_yu) + doubleExtra, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    switch (widthdrawalStatus) {
                        case 0:
                            //提币
                            if (Double.parseDouble(etPageAmount.getText().toString()) < withdrawalMinAmount) {
                                Toast.makeText(BlockActivity.this, Utils.getResourceString(R.string.ti_bi_shu_liang_bu_de_xiao_yu) + withdrawalMinAmount, Toast.LENGTH_SHORT).show();
                                return;
                            }

                            final WithdrawalVerificationPopupWindow loginBottomPopupWindow = new WithdrawalVerificationPopupWindow(BlockActivity.this, R.layout.fragment_register_phone);
                            tvEmailSecond = loginBottomPopupWindow.getTvWithdrawalVerificationSecond();
                            tvSmsSecond = loginBottomPopupWindow.getTvWithdrawalSmsVerificationSecond();

                            UserDO query = UTEXApplication.getLoginUser();

                            if (query.getEmail_status()) {
                                //开启邮箱验证
//                        loginBottomPopupWindow.getEtWithdrawalEmailVerificationCode()
                                loginBottomPopupWindow.getTvWithdrawalEmailVerifcationAccount().setText(query.getEmail());
                            } else {
                                loginBottomPopupWindow.getEtWithdrawalEmailVerificationCode().setVisibility(View.GONE);
                                loginBottomPopupWindow.getTvWithdrawalEmailVerifcationAccount().setVisibility(View.GONE);
                                loginBottomPopupWindow.getTvWithdrawalVerificationSecond().setVisibility(View.GONE);
                            }

                            if (query.getTel_status()) {
                                //开启电话验证
                                loginBottomPopupWindow.getTvWithdrawalSmsVerificationAccount().setText(query.getTel());
                            } else {
                                loginBottomPopupWindow.getTvWithdrawalSmsVerificationAccount().setVisibility(View.GONE);
                                loginBottomPopupWindow.getEtWithdrawalSmsVerificationCode().setVisibility(View.GONE);
                                loginBottomPopupWindow.getTvWithdrawalSmsVerificationSecond().setVisibility(View.GONE);
                            }

                            if (query.getGoogle_status()) {
                                //开启谷歌验证
                                loginBottomPopupWindow.getTvWithdrawalGoogleVerificationAccount().setText(Utils.getResourceString(R.string.gu_ge_yan_zheng_ma));
                            } else {
                                loginBottomPopupWindow.getTvWithdrawalGoogleVerificationAccount().setVisibility(View.GONE);
                                loginBottomPopupWindow.getEtWithdrawalGoogleVerificationCode().setVisibility(View.GONE);
                            }

                            tvEmailSecond.setOnClickListener(view1 -> iBlockPresenter.sendWithdrawalEmail(coinCode));

                            tvSmsSecond.setOnClickListener(view14 -> iBlockPresenter.sendWithdrawalSms(coinCode));

                            loginBottomPopupWindow.getTvWithdrawalCannel().setOnClickListener(view12 -> loginBottomPopupWindow.dimss());
                            loginBottomPopupWindow.getTvWithdrawalComplete()
                                    .setOnClickListener(view13 -> {
                                        //完成，进行提现
                                        String emailCode = TextUtils.isEmpty(loginBottomPopupWindow.getEtWithdrawalEmailVerificationCode().getText()) ? null : loginBottomPopupWindow.getEtWithdrawalEmailVerificationCode().getText().toString();

                                        String telCode = TextUtils.isEmpty(loginBottomPopupWindow.getEtWithdrawalSmsVerificationCode().getText()) ? null : loginBottomPopupWindow.getEtWithdrawalSmsVerificationCode().getText().toString();

                                        String googleCode = TextUtils.isEmpty(loginBottomPopupWindow.getEtWithdrawalGoogleVerificationCode().getText()) ? null : loginBottomPopupWindow.getEtWithdrawalGoogleVerificationCode().getText().toString();

                                        if (TextUtils.isEmpty(emailCode) && TextUtils.isEmpty(telCode) && TextUtils.isEmpty(googleCode)) {
                                            ToastUtils.show(Utils.getResourceString(R.string.qing_tian_xie_yan_zheng_ma));
                                            return;
                                        }

                                        iBlockPresenter.withdrawal(coinCode, Double.parseDouble(etPageAmount.getText().toString()),
                                                etPageAddress.getText().toString(), emailCode, telCode, googleCode
                                                , TextUtils.isEmpty(etPageTag.getText()) ? null : etPageTag.getText().toString());

                                        new Handler()
                                                .postDelayed(() -> loginBottomPopupWindow.dimss(), 300);
                                    });
                            break;
                        case 1:
                            BlockTransferConfirmPopupWindow blockTransferConfirmPopupWindow = new BlockTransferConfirmPopupWindow(this);
                            blockTransferConfirmPopupWindow.getTvBlockTransferContent().setText(Utils.getResourceString(R.string.nin_de_zhuan_zhang_uid) + ":" +
                                    etPageAddress.getText().toString() + "," + Utils.getResourceString(R.string.qing_nin_zi_xi_he_dui_nin_shu_ru_de_uid) + "\n" + Utils.getResourceString(R.string.zhuan_zhang_shu_liang) + ":" +
                                    etPageAmount.getText().toString() + "\n" + Utils.getResourceString(R.string.zhan_nei_zhuan_zhang_yi_jing_fa_chu_wu_fa_che_xiao));

                            blockTransferConfirmPopupWindow.setLoginBottomListener(() -> {
                                blockTransferConfirmPopupWindow.dimss();
                                iBlockPresenter.transfer(assetId, Double.parseDouble(etPageAmount.getText().toString()),
                                        etPageAddress.getText().toString());
                            });

                            blockTransferConfirmPopupWindow.show();
                            break;
                    }


                });

                etPageAmount.addTextChangedListener(new ETtextChangeListener() {
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        super.onTextChanged(charSequence, i, i1, i2);

                        if (!TextUtils.isEmpty(etPageAddress.getText()) && !TextUtils.isEmpty(charSequence)) {
                            tvPageWithdrawMoney.setClickable(true);
                            tvPageWithdrawMoney.setBackgroundResource(R.drawable.login_selector);
                        } else {
                            tvPageWithdrawMoney.setClickable(false);
                            tvPageWithdrawMoney.setBackgroundColor(Utils.getResourceColor(BlockActivity.this, R.color.bd4d6d5_33ffffff));
                        }

                        double amount = 0;

                        try {
                            amount = Double.parseDouble(etPageAmount.getText().toString());
                        } catch (Exception e) {

                        }

                        if (UTEXApplication.getLoginUser().getTransfer_switch()) {
                            tvPageArriveMoney.setText(Utils.getResourceString(R.string.dao_zhang) + " " + Utils.getScientificCountingMethodAfterData(amount, showScale) + " " + coinCode.toUpperCase());
                        } else {

                            if (amount - Double.parseDouble(withdrawalFee) > 0) {
                                tvPageArriveMoney.setText(Utils.getResourceString(R.string.dao_zhang) + " " + Utils.getScientificCountingMethodAfterData(amount - Double.parseDouble(withdrawalFee), showScale) + " " + coinCode.toUpperCase());
                            } else {
                                tvPageArriveMoney.setText(Utils.getResourceString(R.string.dao_zhang) + " 0" + " " + coinCode.toUpperCase());
                            }
                        }
                    }
                });

                etPageAddress.addTextChangedListener(new ETtextChangeListener() {
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        super.onTextChanged(charSequence, i, i1, i2);
                        if (!TextUtils.isEmpty(etPageAmount.getText()) && !TextUtils.isEmpty(charSequence)) {
                            tvPageWithdrawMoney.setClickable(true);
                            tvPageWithdrawMoney.setBackgroundResource(R.drawable.login_selector);
                        } else {
                            tvPageWithdrawMoney.setClickable(false);
                            tvPageWithdrawMoney.setBackgroundColor(Utils.getResourceColor(BlockActivity.this, R.color.bd4d6d5_33ffffff));
                        }
                    }
                });

                tvPageArriveMoney.setText(Utils.getResourceString(R.string.dao_zhang) + " 0" + " " + coinCode.toUpperCase());

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    etPageAddress.setText(result);
//                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(BlockActivity.this, Utils.getResourceString(R.string.jie_xi_er_wei_ma_shi_bai), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    /**
     * 保存bitmap
     *
     * @param bitmap
     */
    public void saveBitmapFile(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }

        ////重复保存时，覆盖原同名图片
        File file = new File(SDUrl.picPath + coinCode + "QrCode" + System.currentTimeMillis() + ".jpg");//将要保存图片的路径和图片名称
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

    @OnClick({R.id.iv_page_back, R.id.tv_page_deposit_withdrawal_log})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_page_back:
                finish();
                break;
            case R.id.tv_page_deposit_withdrawal_log:
                if (UTEXApplication.getLoginUser() != null) {
                    MobclickAgent.onEvent(this, UMConstants.ASSET_WITHDRAW_MORE_RECORD_BTN);
                    Intent moneyRecord = new Intent(this, MoneyOperateRecordActivity.class);
                    startActivity(moneyRecord);
                } else {
                    //跳转到 登陆界面
                    startLoginPage();
                }
                break;
        }
    }

    private void startLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        MobclickAgent.onEvent(this, UMConstants.NOLOGIN_PUSH_LOGIN_PAGE_ACTION);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBlockComponent
                .builder()
                .appComponent(appComponent)
                .blockModule(new BlockModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void getDepositAddressSuccess(String deposit_address, String deposit_tips, String tag) {
        tvPageAddress.setText(deposit_address);

        if (!TextUtils.isEmpty(deposit_tips)) {
            if (Build.VERSION.SDK_INT >= 24) {
                tvPageTip.setText(Html.fromHtml(deposit_tips, Html.FROM_HTML_MODE_COMPACT));
            } else {
                tvPageTip.setText(Html.fromHtml(deposit_tips));
            }
        }

        rlDepositPageCncParent = findViewById(R.id.rl_deposit_page_cnc_parent);
        rlDepositPageTagParent = findViewById(R.id.rl_deposit_page_tag_parent);
        tvDepositCncAddress = findViewById(R.id.tv_deposit_cnc_address);
        tvDepositCncTag = findViewById(R.id.tv_deposit_cnc_tag);

        ImageView imageView = findViewById(R.id.iv_deposit_page_qrcode);

        if (TextUtils.isEmpty(tag)) {
            rlDepositPageCncParent.setVisibility(View.GONE);
            rlDepositPageTagParent.setVisibility(View.GONE);
            image = CodeUtils.createImage(deposit_address, imageView.getWidth(), imageView.getHeight(), null);
            imageView.setImageBitmap(image);
            tvPageCnc.setVisibility(View.GONE);
            tvDepositPageTag.setText("TAG");
        } else {
            if ("cnc".equals(coinCode.toLowerCase())) {
                tvPageCnc.setVisibility(View.VISIBLE);

                if (this != null && !this.isFinishing()) {
                    Glide
                            .with(this)
                            .load(R.drawable.pic_cnc)
                            .into(imageView);
                }

                tvDepositPageTag.setText("MEMO");
            } else {
                image = CodeUtils.createImage(deposit_address, imageView.getWidth(), imageView.getHeight(), null);
                imageView.setImageBitmap(image);
                tvPageCnc.setVisibility(View.GONE);
                tvDepositPageTag.setText("TAG");
            }

            tvPageAddress.setVisibility(View.GONE);
            rlDepositPageCncParent.setVisibility(View.VISIBLE);
            rlDepositPageTagParent.setVisibility(View.VISIBLE);
            tvDepositCncAddress.setText(deposit_address);
            tvDepositCncTag.setText(tag);
        }
    }

    @Override
    public void getWithdrawalAddressSuccess(String withdrawal_tips, String withdrawal_fee, Boolean withdrawal_switch, Double withdrawal_min_amount, boolean tag) {
        TextView tvPageAvailable = findViewById(R.id.tv_page_available);
        this.withdrawalFee = withdrawal_fee;
        this.isWithdrawal = withdrawal_switch;
        this.withdrawalMinAmount = withdrawal_min_amount;
        this.withdrawalTips = withdrawal_tips;
        this.tag = tag;
        Utils.setFontSpan(tvPageAvailable, new String[]{Utils.getResourceString(R.string.ke_yong) + "：", Utils.getScientificCountingMethodAfterData(Double.valueOf(getIntent().getStringExtra(FiledConstants.AVAILABLE_FUND)), showScale) + " " + coinCode.toUpperCase()},
                new Integer[]{R.color.b999999_e6ffffff, R.color.b333333_ffffff});

        Utils.setFontSpan(tvPageFee, new String[]{Utils.getResourceString(R.string.fei_lv) + "：", Utils.getScientificCountingMethodAfterData(Double.valueOf(withdrawal_fee), showScale) + " " + coinCode.toUpperCase()},
                new Integer[]{R.color.b999999_e6ffffff, R.color.b333333_ffffff});

        if (Build.VERSION.SDK_INT >= 24) {
            tvPageTip.setText(Html.fromHtml(withdrawal_tips, Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvPageTip.setText(Html.fromHtml(withdrawal_tips));
        }

        if (tag) {
            if ("cnc".equals(coinCode) || " eos".equals(coinCode)) {
                etPageTag.setHint("MEMO(" + Utils.getResourceString(R.string.xuan_tian) + ")");
            } else {
                etPageTag.setHint("TAG(" + Utils.getResourceString(R.string.xuan_tian) + ")");
            }
            etPageTag.setVisibility(View.VISIBLE);
        } else {
            etPageTag.setVisibility(View.GONE);
        }
    }

    @Override
    public void sendWithdrawalSuccess() {
        handler.sendEmptyMessage(0);
    }

    @Override
    public void withdrawalSuccess() {
        //可用减少
        double doubleExtra = Double.parseDouble(getIntent().getStringExtra(FiledConstants.AVAILABLE_FUND));
        double amount = Double.parseDouble(etPageAmount.getText().toString());
        TextView tvPageAvailable = findViewById(R.id.tv_page_available);
        tvPageAvailable.setText(Utils.getResourceString(R.string.ke_yong) + ":" + Utils.getScientificCountingMethodAfterData(doubleExtra - amount, showScale));

        getIntent().putExtra(FiledConstants.AVAILABLE_FUND, String.valueOf(doubleExtra - amount));

        DepositWithdrawalVO.DataBean dataBean = new DepositWithdrawalVO.DataBean();
        dataBean.setActual_amount(ArithmeticUtil.sub(etPageAmount.getText().toString(), withdrawalFee).doubleValue());
        dataBean.setAmount(Double.parseDouble(etPageAmount.getText().toString()));
        dataBean.setStatus(1);
        dataBean.setWithdrawal_address(etPageAddress.getText().toString());
        dataBean.setUpdateTime(System.currentTimeMillis());
        dataBean.setCoin_code(coinCode);


        Intent intent = new Intent(this, WithdrawalDetailActivity.class);
        intent.putExtra(FiledConstants.BEAN, dataBean);
        intent.putExtra(FiledConstants.TYPE, 2);
        startActivity(intent);

        etPageAmount.setText("");
        etPageAddress.setText("");
    }

    @Override
    public void sendEmailSuccess() {
        handler.sendEmptyMessage(0);
    }

    @Override
    public void sendTelSuccess() {
        handler.sendEmptyMessage(1);
    }

    @Override
    public void transferSuccess() {
        double doubleExtra = Double.parseDouble(getIntent().getStringExtra(FiledConstants.AVAILABLE_FUND));
        double amount = Double.parseDouble(etPageAmount.getText().toString());
        TextView tvPageAvailable = findViewById(R.id.tv_page_available);
        tvPageAvailable.setText(Utils.getResourceString(R.string.ke_yong) + ":" + Utils.getScientificCountingMethodAfterData(doubleExtra - amount, showScale));

        getIntent().putExtra(FiledConstants.AVAILABLE_FUND, String.valueOf(doubleExtra - amount));

        ToastUtils.show(Utils.getResourceString(R.string.zhuan_zhang_cheng_gong));

        etPageAmount.setText("");
        etPageAddress.setText("");
    }
}
