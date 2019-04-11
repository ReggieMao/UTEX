package com.utex.mvp.mine.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.utex.R;
import com.utex.common.FiledConstants;
import com.utex.utils.BubbleUtils;
import com.utex.utils.SharedPreferencesUtil;
import com.utex.utils.Utils;
import com.github.ihsg.patternlocker.UTEXHitCellView;
import com.github.ihsg.patternlocker.UTEXLockerLinkedLineView;
import com.github.ihsg.patternlocker.UTEXNormalCellView;
import com.github.ihsg.patternlocker.OnPatternChangeListener;
import com.github.ihsg.patternlocker.PatternLockerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 用户 图形录入界面
 */
public class ImageEntryActivity extends AppCompatActivity {

    @BindView(R.id.tv_image_entry_tip)
    TextView tvImaegEntryTip;

    @BindView(R.id.pattern_lock_view)
    PatternLockerView patternLockView;

    private int count = 0;

    private String firstStr;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            patternLockView.updateStatus(false);
            patternLockView.clearHitState();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_entry);
        ButterKnife.bind(this);

        UTEXNormalCellView UTEXNormalCellView = new UTEXNormalCellView();
        UTEXNormalCellView.setNormalColor(Utils.getResourceColor(this, R.color.b66398155));
        patternLockView.setNormalCellView(UTEXNormalCellView);

        UTEXLockerLinkedLineView UTEXLockerLinkedLineView = new UTEXLockerLinkedLineView();
        UTEXLockerLinkedLineView.setNormalColor(Utils.getResourceColor(this, R.color.b19398155));
        UTEXLockerLinkedLineView.setLineWidth(BubbleUtils.dp2px(5));
        UTEXLockerLinkedLineView.setErrorColor(Utils.getResourceColor(this, R.color.b19ee6a5e));
        patternLockView.setLinkedLineView(UTEXLockerLinkedLineView);

        UTEXHitCellView UTEXHitCellView = new UTEXHitCellView();
        UTEXHitCellView.setLineWidth(BubbleUtils.dp2px(2));
        UTEXHitCellView.setHitColor(Utils.getResourceColor(this, R.color.f398155));
        UTEXHitCellView.setErrorColor(Utils.getResourceColor(this, R.color.fee6a5e));
        patternLockView.setHitCellView(UTEXHitCellView);

        patternLockView.setOnPatternChangedListener(new OnPatternChangeListener() {

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
                Log.e("TAG", hitList.toString());
                if (hitList.size() < 6) {
                    //提示 不得小于6
                    view.updateStatus(true);
                    handler.sendEmptyMessageDelayed(0, 600);
                    tvImaegEntryTip.setText(Utils.getResourceString(R.string.lian_jie_shu_liang_bu_de_xiao_yu_liu));
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
                        tvImaegEntryTip.setText(Utils.getResourceString(R.string.zai_ci_lian_jie_que_ren_tu_xing_zheng_que));
                    } else {
                        if (str.equals(firstStr)) {
                            //正确
                            SharedPreferencesUtil.putString(FiledConstants.IMAGE_UNLOCKING, str);
                            setResult(200);
                            finish();
                        } else {
                            //第二次不正确
                            count = 0;
                            firstStr = "";
                            tvImaegEntryTip.setText(Utils.getResourceString(R.string.liang_ci_tu_xing_bu_tong));
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

    @Override
    protected void onDestroy() {
        setResult(200);
        super.onDestroy();
    }

    @OnClick({R.id.iv_toolbar_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_left:
                finish();
                break;
        }
    }
}
