package com.utex.mvp.mine.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.utex.R;
import com.utex.base.BaseActivity;
import com.utex.common.Constants;
import com.utex.common.FiledConstants;
import com.utex.core.AppComponent;
import com.utex.mvp.mine.bean.PhoneCountryNumVO;
import com.utex.utils.SharedPreferencesUtil;
import com.utex.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SysSetLanguageValuationActivity extends BaseActivity {

    @BindView(R.id.rv_sys_set_lanaguage_valuation_list)
    RecyclerView rvSysSetLanaguageValuationList;

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    private int position;
    private int type;
    private ArrayList<PhoneCountryNumVO.DataBean> serializableExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sys_set);
        ButterKnife.bind(this);

        type = getIntent().getIntExtra(FiledConstants.TYPE, 0);

        List<String> data = new ArrayList<>();

        position = 0;

        switch (type) {
            case 0:
                tvToolbarTitle.setText(Utils.getResourceString(R.string.yu_yan_she_zhi));

                data.add(Utils.getResourceString(R.string.jian_ti_zhong_wen));
                data.add(Utils.getResourceString(R.string.ying_yu));
                data.add(Utils.getResourceString(R.string.fan_ti_zhong_wen));
//                data.add(Utils.getResourceString(R.string.ri_yu));

                position = SharedPreferencesUtil.getInteger(Constants.SP_LANGUGE_POSITION, 2);

                break;
            case 1:
                tvToolbarTitle.setText(Utils.getResourceString(R.string.ji_jia_fang_shi));

                data.add(Utils.getResourceString(R.string.mei_yuan));
                data.add(Utils.getResourceString(R.string.ren_min_bi));
                data.add(Utils.getResourceString(R.string.han_yuan));
                data.add(Utils.getResourceString(R.string.ri_yuan));
                data.add(Utils.getResourceString(R.string.o_yuan));
                data.add(Utils.getResourceString(R.string.lu_bu));
                data.add(Utils.getResourceString(R.string.ying_bang));

                position = SharedPreferencesUtil.getInteger(Constants.SP_VALUAT_POSITION, 0);
                break;
            case 2:
                //选择手机号
                serializableExtra = (ArrayList<PhoneCountryNumVO.DataBean>) getIntent().getSerializableExtra(FiledConstants.COUNTRYLIST);

                if (serializableExtra == null) {
                    serializableExtra = new ArrayList<>();
                }

                for (PhoneCountryNumVO.DataBean a : serializableExtra) {
                    data.add(a.getName_en() + "," + a.getArea_code());
                }

                break;
        }

        rvSysSetLanaguageValuationList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        SysSetLanguageValuationAdapter sysSetLanguageValuationAdapter = new SysSetLanguageValuationAdapter(data, position, type);

        sysSetLanguageValuationAdapter.setOnclickListener(position -> {
            Intent intent = new Intent();
            intent.putExtra("country", serializableExtra.get(position));
            setResult(200, intent);
            finish();
        });
        rvSysSetLanaguageValuationList.setAdapter(sysSetLanguageValuationAdapter);
    }

    @OnClick({R.id.iv_toolbar_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_left:
                finish();
                break;
        }
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

}
