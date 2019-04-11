package com.utex.mvp.mine.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.utex.R;
import com.utex.base.BaseActivity;
import com.utex.common.FiledConstants;
import com.utex.core.AppComponent;
import com.utex.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AcceptanceActivity extends BaseActivity {

    public static final String TYPE = "type";
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolBarTitle;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_jieshao)
    TextView tvJieshao;
    @BindView(R.id.step1)
    TextView tvStep1;
    @BindView(R.id.step2)
    TextView tvStep2;
    @BindView(R.id.step3)
    TextView tvStep3;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.ll2)
    LinearLayout ll2;
    @BindView(R.id.view1_1)
    View view1_1;
    @BindView(R.id.view1_2)
    View view1_2;
    @BindView(R.id.view2_1)
    View view2_1;
    @BindView(R.id.view2_2)
    View view2_2;
    @BindView(R.id.view3_1)
    View view3_1;
    @BindView(R.id.view3_2)
    View view3_2;
    private int mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceptance);
        ButterKnife.bind(this);
        mType = getIntent().getIntExtra(TYPE, 0);
        if (mType == 0) {
            tvToolBarTitle.setText(Utils.getResourceString(R.string.fa_bi_cheng_dui));
            tvTitle.setText(Utils.getResourceString(R.string.fa_bi_cheng_dui));
            tvJieshao.setText(Utils.getResourceString(R.string.fa_bi_kuai_jie_dui_huan_gusd_cnc_geng_an_quan));
            tvStep1.setText(Utils.getResourceString(R.string.tian_jia_guan_fang_cehng_dui_label));
            tvStep2.setText(Utils.getResourceString(R.string.zhuan_zhang_dui_huan_label));
            tvStep3.setText(Utils.getResourceString(R.string.que_ren_dao_zhan_label));
            ll1.setVisibility(View.VISIBLE);
            ll2.setVisibility(View.GONE);
            view1_2.setVisibility(View.GONE);
            view2_2.setVisibility(View.GONE);
            view3_2.setVisibility(View.GONE);
        } else {
            tvToolBarTitle.setText(Utils.getResourceString(R.string.fa_bi_cheng_dui1));
            tvTitle.setText(Utils.getResourceString(R.string.fa_bi_cheng_dui1));
            tvJieshao.setText(Utils.getResourceString(R.string.fa_bi_kuai_jie_dui_huan_gusd_cnc_geng_an_quan1));
            tvStep1.setText(Utils.getResourceString(R.string.tian_jia_guan_fang_cehng_dui_label1));
            tvStep2.setText(Utils.getResourceString(R.string.zhuan_zhang_dui_huan_label1));
            tvStep3.setText(Utils.getResourceString(R.string.que_ren_dao_zhan_label1));
            ll1.setVisibility(View.GONE);
            ll2.setVisibility(View.VISIBLE);
            view1_1.setVisibility(View.GONE);
            view2_1.setVisibility(View.GONE);
            view3_1.setVisibility(View.GONE);
        }
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @OnClick({R.id.iv_toolbar_left, R.id.tv_acceptance_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_acceptance_btn:
                //立即承兑
                Intent intent = new Intent(this, WeChatActivity.class);
                intent.putExtra(FiledConstants.TYPE, mType == 0 ? 1 : 2);
                startActivity(intent);
                break;
            case R.id.iv_toolbar_left:
                finish();
                break;
        }
    }
}
