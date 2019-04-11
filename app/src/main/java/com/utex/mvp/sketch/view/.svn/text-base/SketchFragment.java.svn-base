package com.utex.mvp.sketch.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.utex.R;
import com.utex.base.BaseFragment;
import com.utex.core.AppComponent;
import com.utex.mvp.marketdetail.bean.CoinBaseVO;
import com.utex.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Demon on 2018/5/21.
 */
public class SketchFragment extends BaseFragment {

    @BindView(R.id.tv_sketch_name)
    TextView tvSketchName;

    @BindView(R.id.tv_sketch_detail)
    TextView tvSketchDetail;

    @BindView(R.id.tv_sketch_issue_time)
    TextView tvSketchIssueTime;

    @BindView(R.id.tv_sketch_issue_total)
    TextView tvSketchIssueTotal;

    @BindView(R.id.tv_sketch_total)
    TextView tvSketchTotal;

    @BindView(R.id.tv_sketch_raise_the_price)
    TextView tvSketchRaiseThePrice;

    @BindView(R.id.tv_sketch_white_paper)
    TextView tvSketchWhitePaper;

    @BindView(R.id.tv_sketch_official_network)
    TextView tvSketchOfficialNetWork;

    @BindView(R.id.tv_sketch_block_address)
    TextView tvSketchBlockAddress;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_sketch, container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @OnClick({R.id.tv_sketch_block_address, R.id.tv_sketch_official_network, R.id.tv_sketch_white_paper})
    public void onClick(View view) {
        Uri uri = null;
        switch (view.getId()) {
            case R.id.tv_sketch_white_paper:
                uri = Uri.parse(tvSketchWhitePaper.getText().toString());
                break;
            case R.id.tv_sketch_block_address:
                uri = Uri.parse(tvSketchBlockAddress.getText().toString());
                break;
            case R.id.tv_sketch_official_network:
                uri = Uri.parse(tvSketchOfficialNetWork.getText().toString());
                break;
        }
        if (uri != null && !TextUtils.isEmpty(uri.toString()) && !"- -".equals(uri.toString())) {
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

    }

    public void setData(CoinBaseVO.DataBean data) {
        if (data == null || tvSketchName == null) {
            return;
        }

        tvSketchName.setText(data.getCoin_name() + "(" + data.getCoin_code().toUpperCase() + ")");
        if (Build.VERSION.SDK_INT >= 24) {
            tvSketchDetail.setText(Html.fromHtml(data.getCoin_introduction(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvSketchDetail.setText(Html.fromHtml(data.getCoin_introduction()));
        }
        tvSketchDetail.setGravity(Gravity.LEFT);

        tvSketchIssueTime.setText(TextUtils.isEmpty(Utils.long2String(data.getIssue_time(), 4)) ? "- -" : Utils.long2String(data.getIssue_time(), 4));
        tvSketchIssueTotal.setText(TextUtils.isEmpty(data.getTotal_amount()) ? "- -" : data.getTotal_amount());
        tvSketchTotal.setText(TextUtils.isEmpty(data.getCirculation()) ? "- -" : data.getCirculation());
        tvSketchRaiseThePrice.setText(TextUtils.isEmpty(String.valueOf(data.getToken_price())) ? "- -" : String.valueOf(data.getToken_price()));
        tvSketchWhitePaper.setText(TextUtils.isEmpty(data.getWhite_paper()) ? "- -" : data.getWhite_paper());
        tvSketchOfficialNetWork.setText(TextUtils.isEmpty(data.getOfficial_website()) ? "- -" : data.getOfficial_website());
        tvSketchBlockAddress.setText(TextUtils.isEmpty(data.getBlock_explorer()) ? "- -" : data.getBlock_explorer());
    }

}
