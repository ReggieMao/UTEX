package com.utex.mvp.mine.view;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.utex.R;
import com.utex.base.BaseActivity;
import com.utex.common.FiledConstants;
import com.utex.core.AppComponent;
import com.utex.mvp.mine.dagger2.DaggerWithdrawalDetailComponent;
import com.utex.mvp.mine.dagger2.WithdrawalDetailModule;
import com.utex.mvp.mine.presenter.IWithdrawalDetailPresenter;
import com.utex.mvp.order.bean.DepositWithdrawalVO;
import com.utex.utils.ArithmeticUtil;
import com.utex.utils.Utils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WithdrawalDetailActivity extends BaseActivity implements IWithdrawalDetailView {

    @Inject
    IWithdrawalDetailPresenter iWithdrawalDetailPresenter;

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;

    @BindView(R.id.tv_withdrawal_name)
    TextView tvWithdrawalName;

    @BindView(R.id.tv_withdrawal_amount)
    TextView tvWithdrawalAmount;

    @BindView(R.id.tv_withdrawal_status)
    TextView tvWithdrawalStatus;

    @BindView(R.id.tv_withdrawal_time)
    TextView tvWithdrawalTime;

    @BindView(R.id.rl_withdrawal_1_parent)
    RelativeLayout rlWithdrawal1Parent;

    @BindView(R.id.rl_withdrawal_2_parent)
    RelativeLayout rlWithdrawal2Parent;

    @BindView(R.id.tv_withdrawal_address)
    TextView tvWithdrawalAddress;

    @BindView(R.id.tv_withdrawal_txid)
    TextView tvWithdrawalTxid;

    @BindView(R.id.tv_withdrawal_fee)
    TextView tvWithdrawalFee;

    @BindView(R.id.rl_withdrawal_detail_fee_parent)
    RelativeLayout rlWithdrawalDetailFeeParent;

    @BindView(R.id.tv_withdrawal_detail_block_explorer)
    TextView tvWithdrawalDetailBlockExplorer;

    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    private DepositWithdrawalVO.DataBean dataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal_detail);
        ButterKnife.bind(this);
        int type = getIntent().getIntExtra(FiledConstants.TYPE, 1);

        dataBean = (DepositWithdrawalVO.DataBean) getIntent().getSerializableExtra(FiledConstants.BEAN);

        switch (type) {
            case 1:
                tvToolbarTitle.setText(Utils.getResourceString(R.string.chong_zhi_xiang_qing));
                rlWithdrawalDetailFeeParent.setVisibility(View.GONE);
                tvWithdrawalAmount.setText(String.valueOf(dataBean.getAmount()));
                break;
            case 2:
                tvToolbarTitle.setText(Utils.getResourceString(R.string.ti_xian_xiang_qing));
                tvWithdrawalFee.setText(String.valueOf(ArithmeticUtil.sub(dataBean.getAmount(), dataBean.getActual_amount())));
                tvWithdrawalAmount.setText(String.valueOf(dataBean.getActual_amount()));
                break;
        }

        tvWithdrawalName.setText(dataBean.getCoin_code().toUpperCase());

        String str = null;

        switch (dataBean.getStatus()) {
            case 1:
                str = Utils.getResourceString(R.string.wei_shen_he);
                tvToolbarRight.setVisibility(View.VISIBLE);
                rlWithdrawal2Parent.setVisibility(View.GONE);
                tvWithdrawalStatus.setTextColor(Utils.getResourceColor(WithdrawalDetailActivity.this, R.color.f398155));
                break;
            case 2:
                if (type == 1) {
                    //充值  未审核
                    str = Utils.getResourceString(R.string.wei_shen_he);
                    rlWithdrawal2Parent.setVisibility(View.GONE);
                    tvWithdrawalStatus.setTextColor(Utils.getResourceColor(WithdrawalDetailActivity.this, R.color.fee6a5e));
                } else {
                    str = Utils.getResourceString(R.string.yi_shen_he);
                    rlWithdrawal2Parent.setVisibility(View.GONE);
                    tvWithdrawalStatus.setTextColor(Utils.getResourceColor(WithdrawalDetailActivity.this, R.color.f50b577));
                }
                break;
            case 3:
                if (type == 1) {
                    //充值  已到账
                    str = Utils.getResourceString(R.string.yi_dao_zhang);
                    tvWithdrawalStatus.setTextColor(Utils.getResourceColor(WithdrawalDetailActivity.this, R.color.f50b577));
                    tvWithdrawalTxid.setText(dataBean.getTx_id());
                } else {
                    //提现
                    str = Utils.getResourceString(R.string.shen_he_shi_bai);
                    rlWithdrawal2Parent.setVisibility(View.GONE);
                    tvWithdrawalStatus.setTextColor(Utils.getResourceColor(WithdrawalDetailActivity.this, R.color.fee6a5e));
                    tvWithdrawalTxid.setText(dataBean.getTxid());
                }
                break;
            case 4:
                str = Utils.getResourceString(R.string.yi_fa_song);
                rlWithdrawal2Parent.setVisibility(View.VISIBLE);
                tvWithdrawalTxid.setText(dataBean.getTxid());
                tvWithdrawalStatus.setTextColor(Utils.getResourceColor(WithdrawalDetailActivity.this, R.color.f398155));
                break;
            case 5:
                str = Utils.getResourceString(R.string.yi_che_xiao);
                rlWithdrawal2Parent.setVisibility(View.GONE);
                tvWithdrawalStatus.setTextColor(Utils.getResourceColor(WithdrawalDetailActivity.this, R.color.bd4d6d5_33ffffff));
                tvToolbarRight.setVisibility(View.GONE);
                break;
        }

        tvWithdrawalStatus.setText(str);

        tvWithdrawalAddress.setText(dataBean.getWithdrawal_address());

        if (type == 1) {
            //充值
            rlWithdrawal1Parent.setVisibility(View.GONE);
//            iWithdrawalDetailPresenter.getCoinWalletAddress(dataBean.getCoin_code());
            tvWithdrawalTime.setText(Utils.long2String(dataBean.getUpdate_time(), 3));
        } else {
            tvWithdrawalTime.setText(Utils.long2String(dataBean.getUpdateTime(), 3));
        }

        if (!TextUtils.isEmpty(dataBean.getBlock_explorer())) {
            tvWithdrawalDetailBlockExplorer.setVisibility(View.VISIBLE);
            tvWithdrawalDetailBlockExplorer.setTag(dataBean.getBlock_explorer());
        } else {
            tvWithdrawalDetailBlockExplorer.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.iv_toolbar_left, R.id.tv_withdrawal_wallet_copy, R.id.tv_withdrawal_detail_copy, R.id.tv_withdrawal_detail_block_explorer,
            R.id.tv_toolbar_right})
    public void onClick(View view) {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        switch (view.getId()) {
            case R.id.tv_toolbar_right:
                //取消提现
                iWithdrawalDetailPresenter.cannelWithdrawal(dataBean.getId());

                break;
            case R.id.tv_withdrawal_detail_block_explorer:
                //跳入网页
                try {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(String.valueOf(tvWithdrawalDetailBlockExplorer.getTag()).replace("{}", tvWithdrawalTxid.getText().toString()));
                    intent.setData(content_url);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("TAG", "错误");
                }
                break;
            case R.id.iv_toolbar_left:
                finish();
                break;
            case R.id.tv_withdrawal_wallet_copy:
                // 从API11开始android推荐使用android.content.ClipboardManager
                // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
                // 将文本内容放到系统剪贴板里。
                cm.setText(tvWithdrawalAddress.getText());
                Toast.makeText(this, Utils.getResourceString(R.string.fu_zhi_cheng_gong), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_withdrawal_detail_copy:
                // 从API11开始android推荐使用android.content.ClipboardManager
                // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
                // 将文本内容放到系统剪贴板里。
                cm.setText(tvWithdrawalTxid.getText());
                Toast.makeText(this, Utils.getResourceString(R.string.fu_zhi_cheng_gong), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerWithdrawalDetailComponent
                .builder()
                .appComponent(appComponent)
                .withdrawalDetailModule(new WithdrawalDetailModule(this))
                .build()
                .inject(this);
    }

    public void getDepositAddressSuccess(String deposit_address) {
        if (deposit_address != null) {
            tvWithdrawalAddress.setText(deposit_address);
        }
    }

    @Override
    public void cannelWithdrawalSuccess() {
        setResult(200);
        finish();
    }
}
