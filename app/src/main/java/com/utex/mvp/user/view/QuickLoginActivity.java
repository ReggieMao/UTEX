package com.utex.mvp.user.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.utex.R;
import com.utex.common.Constants;
import com.utex.common.FiledConstants;
import com.utex.utils.BubbleUtils;
import com.utex.utils.SecurityIdentificationUtils;
import com.utex.utils.SharedPreferencesUtil;
import com.utex.utils.ToastUtils;
import com.utex.utils.Utils;
import com.github.ihsg.patternlocker.UTEXHitCellView;
import com.github.ihsg.patternlocker.UTEXLockerLinkedLineView;
import com.github.ihsg.patternlocker.UTEXNormalCellView;
import com.github.ihsg.patternlocker.OnPatternChangeListener;
import com.github.ihsg.patternlocker.PatternLockerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuickLoginActivity extends AppCompatActivity {

    private List<String> quinckLoginList;

    /**
     * 0 图案
     * 1 指纹
     */
    private int currSel;

    private int count = 0;


    @BindView(R.id.pv_quick_login)
    PatternLockerView pvQuickLogin;

    @BindView(R.id.tv_zhi_wen)
    TextView tvZhiWen;

    @BindView(R.id.cb_quick_login)
    CheckBox cbQuickLogin;

    @BindView(R.id.tv_look_other_login)
    TextView tvLookOtherLogin;

    @BindView(R.id.tv_quick_login_label)
    TextView tvQuickLoginLabel;

    @BindView(R.id.tv_quinck_login_click_set_label)
    TextView tvQuickLoginClickSetLabel;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pvQuickLogin.updateStatus(false);
            pvQuickLogin.clearHitState();
        }
    };
    private String firstStr;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_login);
        ButterKnife.bind(this);
        //检测当前手机拥有什么快捷登录方式
        pvQuickLogin.setVisibility(View.VISIBLE);
        tvZhiWen.setVisibility(View.GONE);
        tvQuickLoginLabel.setText(Utils.getResourceString(R.string.she_zhi_shou_shi_kuai_su_deng_lu));
        tvQuickLoginClickSetLabel.setText(Utils.getResourceString(R.string.qing_she_zhi_tu_xing));

        quinckLoginList = new ArrayList<>();
        quinckLoginList.add(Utils.getResourceString(R.string.tu_an_jie_suo));
        if (SecurityIdentificationUtils.isHardwareDetected()) {
            //有指纹,默认显示指纹
            quinckLoginList.add(Utils.getResourceString(R.string.zhi_wen_jie_suo));
        }

        cbQuickLogin.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                //永远不再显示
                SharedPreferencesUtil.putInteger(Constants.FOREVER_NOT_SHOW, 1);
            } else {
                //取消永远不再显示
                SharedPreferencesUtil.putInteger(Constants.FOREVER_NOT_SHOW, 0);
            }
        });

        UTEXNormalCellView UTEXNormalCellView = new UTEXNormalCellView();
        UTEXNormalCellView.setNormalColor(Utils.getResourceColor(this, R.color.b66398155));
        pvQuickLogin.setNormalCellView(UTEXNormalCellView);

        UTEXLockerLinkedLineView UTEXLockerLinkedLineView = new UTEXLockerLinkedLineView();
        UTEXLockerLinkedLineView.setNormalColor(Utils.getResourceColor(this, R.color.b19398155));
        UTEXLockerLinkedLineView.setLineWidth(BubbleUtils.dp2px(5));
        UTEXLockerLinkedLineView.setErrorColor(Utils.getResourceColor(this, R.color.b19ee6a5e));
        pvQuickLogin.setLinkedLineView(UTEXLockerLinkedLineView);

        UTEXHitCellView UTEXHitCellView = new UTEXHitCellView();
        UTEXHitCellView.setLineWidth(BubbleUtils.dp2px(2));
        UTEXHitCellView.setHitColor(Utils.getResourceColor(this, R.color.f398155));
        UTEXHitCellView.setErrorColor(Utils.getResourceColor(this, R.color.fee6a5e));
        pvQuickLogin.setHitCellView(UTEXHitCellView);

        pvQuickLogin.setOnPatternChangedListener(new OnPatternChangeListener() {

            /**
             * 开始绘制图案时（即手指按下触碰到绘画区域时）会调用该方法
             *
             * @param view
             */
            @Override
            public void onStart(PatternLockerView view) {

            }

            /**
             * 图案绘制改变时（即手指在绘画区域移动时）会调用该方法，请注意只有 @param hitList改变了才会触发此方法
             *
             * @param view
             * @param hitList
             */
            @Override
            public void onChange(PatternLockerView view, List<Integer> hitList) {

            }

            /**
             * 图案绘制完成时（即手指抬起离开绘画区域时）会调用该方法
             *
             * @param view
             * @param hitList
             */
            @Override
            public void onComplete(PatternLockerView view, List<Integer> hitList) {
                if (hitList.size() < 6) {
                    //提示 不得小于6
                    view.updateStatus(true);
                    handler.sendEmptyMessageDelayed(0, 600);
                    tvQuickLoginClickSetLabel.setText(Utils.getResourceString(R.string.lian_jie_shu_liang_bu_de_xiao_yu_liu));
                } else {
                    //保存
                    String str = "";
                    for (Integer s : hitList) {
                        str += s + ",";
                    }
                    if (count == 0) {
                        //首次
                        firstStr = str;
                        count = 1;
                        tvQuickLoginClickSetLabel.setText(Utils.getResourceString(R.string.zai_ci_lian_jie_que_ren_tu_xing_zheng_que));
                    } else {
                        if (str.equals(firstStr)) {
                            //正确
                            SharedPreferencesUtil.putString(FiledConstants.IMAGE_UNLOCKING, str);
                            SharedPreferencesUtil.putInteger(FiledConstants.HAS_SECURITY_IDENTIFICATION, 1);
                            finish();
                        } else {
                            //第二次不正确
                            count = 0;
                            firstStr = "";
                            tvQuickLoginClickSetLabel.setText(Utils.getResourceString(R.string.liang_ci_tu_xing_bu_tong));
                            view.updateStatus(true);
                            handler.sendEmptyMessageDelayed(0, 600);
                        }
                    }

                }
            }

            /**
             * 已绘制的图案被清除时会调用该方法
             *
             * @param view
             */
            @Override
            public void onClear(PatternLockerView view) {

            }
        });

    }

    @OnClick({R.id.tv_look_other_login, R.id.tv_quick_login_jump, R.id.tv_zhi_wen})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_zhi_wen:
                if (SecurityIdentificationUtils.isHardwareDetected()) {
                    //有模块
                    if (!SecurityIdentificationUtils.hasEnrolledFingerprints()) {
                        //没有指纹
                        SecurityIdentificationUtils.goSetFingerprintsPage(this);
                    } else {
                        SecurityIdentificationUtils.fingerprintShow(this, R.layout.activity_login);
                        SecurityIdentificationUtils.setSecurityIdentificationListener(new SecurityIdentificationUtils.SecurityIdentificationListener() {
                            @Override
                            public void success() {
                                //成功
                                SharedPreferencesUtil.putInteger(FiledConstants.HAS_SECURITY_IDENTIFICATION, 2);
                                finish();
                            }

                            @Override
                            public void fail() {
                                ToastUtils.show(Utils.getResourceString(R.string.zhi_wen_cuo_wu));
                            }
                        });
                    }
                }
                break;
            case R.id.tv_quick_login_jump:
                finish();
                break;
            case R.id.tv_look_other_login:
                switch (currSel) {
                    case 0:
                        //变成指纹
                        currSel = 1;
                        pvQuickLogin.setVisibility(View.GONE);
                        tvZhiWen.setVisibility(View.VISIBLE);
                        tvLookOtherLogin.setText(Utils.getResourceString(R.string.qie_huan_cheng_shou_shi_mi_ma));
                        tvQuickLoginLabel.setText(Utils.getResourceString(R.string.she_zhi_zhi_wen_kaui_su_deng_lu));
                        tvQuickLoginClickSetLabel.setText(Utils.getResourceString(R.string.dian_ji_zhi_wen_tu_an_jin_xing_she_zhi));
                        break;
                    case 1:
                        //变成图案
                        currSel = 0;
                        pvQuickLogin.setVisibility(View.VISIBLE);
                        tvZhiWen.setVisibility(View.GONE);
                        tvLookOtherLogin.setText(Utils.getResourceString(R.string.qie_huan_cheng_zhi_wen_jie_suo));
                        tvQuickLoginLabel.setText(Utils.getResourceString(R.string.she_zhi_shou_shi_kuai_su_deng_lu));
                        tvQuickLoginClickSetLabel.setText(Utils.getResourceString(R.string.qing_she_zhi_tu_xing));
                        break;
                }

                break;
        }
    }


}
