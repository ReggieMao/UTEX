package com.utex.mvp.asset.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.utex.R;
import com.utex.bean.TickerDo;
import com.utex.bean.UserDO;
import com.utex.common.FiledConstants;
import com.utex.common.UMConstants;
import com.utex.core.UTEXApplication;
import com.utex.db.ExTickerDao;
import com.utex.mvp.asset.bean.Asset;
import com.utex.mvp.block.view.BlockActivity;
import com.utex.mvp.mine.view.IdentityAuthenticationSelectActivity;
import com.utex.mvp.mine.view.IdentitySuccessActivity;
import com.utex.utils.BubbleUtils;
import com.utex.utils.DialogUtils;
import com.utex.utils.SharedPreferencesUtil;
import com.utex.utils.Utils;
import com.utex.widget.popuwindow.TradPopupWindow;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Demon on 2018/6/1.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private List<Asset.DataBean.AssetUserListBean> assetUserList;


    public ExpandableListAdapter() {
    }

    @Override
    public int getGroupCount() {
        return Utils.checkList(assetUserList);
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return assetUserList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return "exnow";
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_asset_group, viewGroup, false);
        }
        Context context = viewGroup.getContext();


        TextView tvGroupAvailable = view.findViewById(R.id.tv_group_available);
        TextView tvGroupPrice = view.findViewById(R.id.tv_group_price);
        View line = view.findViewById(R.id.group_line);
        TextView tvGroupName = view.findViewById(R.id.tv_group_name);
        TextView tvGroupFrozen = view.findViewById(R.id.tv_group_frozen);

        Asset.DataBean.AssetUserListBean asset = assetUserList.get(i);

        DecimalFormat decimalFormat = new DecimalFormat("###################.###########");

        String coin_code = asset.getCoin_code();
        try {
            double rate = 0;
            if ("usdt".equals(coin_code) || "gzh".equals(coin_code) || "eth".equals(coin_code)) {
                coin_code = "≈0";
                rate = UTEXApplication.getRate(asset.getCoin_code());
                coin_code = "≈" + decimalFormat.format(Double.parseDouble(Utils.getScientificCountingMethodAfterData(Double.parseDouble(asset.getTotal_fund()) * rate, asset.getShow_decimal())));
            } else {
                List<TickerDo> tickerByCoinCode = ExTickerDao.getTickerByCoinCode(coin_code);
                coin_code = "≈0";
                if (Utils.checkList(tickerByCoinCode) > 0) {
                    for (TickerDo tickerDo : tickerByCoinCode) {
                        if (tickerDo.getStatus() == 1 && tickerDo.getResultBean() != null) {
                            //选中
                            String[] split = tickerDo.getCoin_market_code().split("_");
                            rate = UTEXApplication.getRate(split[1]);
                            coin_code = "≈" + decimalFormat.format(Double.parseDouble(Utils.getScientificCountingMethodAfterData(Double.parseDouble(asset.getTotal_fund()) * Double.parseDouble(tickerDo.getResultBean().getLast()) * rate, asset.getShow_decimal())));
                            if (!"≈0".equals(coin_code)) {
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {

        }

        boolean isBtc = false;
        if ("gzh".equals(coin_code.toLowerCase())) {
            isBtc = true;
        }

        tvGroupName.setText(asset.getCoin_code().toUpperCase());

        String value = decimalFormat.format(Double.valueOf(Utils.getStringFormatAmount(Double.valueOf(asset.getTotal_fund()), isBtc)));
        String total = value + "\n " + coin_code + SharedPreferencesUtil.getString(FiledConstants.PALTFORM_VALUAT, "USD");

        SpannableString price = new SpannableString(total);

        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(BubbleUtils.sp2px(14));
        ForegroundColorSpan span = new ForegroundColorSpan(Utils.getResourceColor(context, R.color.b666666_99ffffff));
        price.setSpan(sizeSpan, 0, value.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        price.setSpan(span, 0, value.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        sizeSpan = new AbsoluteSizeSpan(BubbleUtils.sp2px(11));
        span = new ForegroundColorSpan(Utils.getResourceColor(context, R.color.b999999_e6ffffff));
        price.setSpan(sizeSpan, value.length() + 1, total.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        price.setSpan(span, value.length() + 1, total.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tvGroupPrice.setText(price);

        value = decimalFormat.format(Double.parseDouble(Utils.getStringFormatAmount(Double.valueOf(asset.getAvailable_fund()), isBtc)));
        total = value + " " + Utils.getResourceString(R.string.ke_yong);
        price = new SpannableString(total);
        sizeSpan = new AbsoluteSizeSpan(BubbleUtils.sp2px(14));
        span = new ForegroundColorSpan(Utils.getResourceColor(context, R.color.b666666_99ffffff));
        price.setSpan(sizeSpan, 0, value.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        price.setSpan(span, 0, value.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        sizeSpan = new AbsoluteSizeSpan(BubbleUtils.sp2px(12));
        span = new ForegroundColorSpan(Utils.getResourceColor(context, R.color.b999999_e6ffffff));
        price.setSpan(sizeSpan, value.length() + 1, total.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        price.setSpan(span, value.length() + 1, total.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tvGroupAvailable.setText(price);

        value = decimalFormat.format(Double.valueOf(Utils.getStringFormatAmount(Double.valueOf(asset.getFrozen_fund()), isBtc)));
        total = value + " " + Utils.getResourceString(R.string.dong_jie);
        price = new SpannableString(total);
        sizeSpan = new AbsoluteSizeSpan(BubbleUtils.sp2px(14));
        span = new ForegroundColorSpan(Utils.getResourceColor(context, R.color.b666666_99ffffff));
        price.setSpan(sizeSpan, 0, value.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        price.setSpan(span, 0, value.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        sizeSpan = new AbsoluteSizeSpan(BubbleUtils.sp2px(12));
        span = new ForegroundColorSpan(Utils.getResourceColor(context, R.color.b999999_e6ffffff));
        price.setSpan(sizeSpan, value.length() + 1, total.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        price.setSpan(span, value.length() + 1, total.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        tvGroupFrozen.setText(price);


        if (b) {
            line.setVisibility(View.GONE);
        } else {
            line.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public View getChildView(final int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_asset_child, viewGroup, false);
        } else {

        }

        Button btAssetDeposit = view.findViewById(R.id.bt_asset_deposit);
        Button assetWithdrawal = view.findViewById(R.id.bt_asset_withdrawal);
        Button assetTrad = view.findViewById(R.id.bt_asset_trad);

        View finalView = view;
        btAssetDeposit.setOnClickListener(view13 -> {
            MobclickAgent.onEvent(finalView.getContext(), UMConstants.ASSET_LIST_SHOW_FOR_DESPOSIT_BTN);

            String coin_code = assetUserList.get(i).getCoin_code().toLowerCase();

            if ("cnc".equals(coin_code) || "gusd".equals(coin_code)) {
                UserDO user = UTEXApplication.getLoginUser();
                if (user != null && user.getUser_level() > 1) {

                    Intent intent = new Intent(view13.getContext(), BlockActivity.class);
                    intent.putExtra(FiledConstants.TYPE, 1);
                    intent.putExtra(FiledConstants.COIN_CODE, assetUserList.get(i).getCoin_code());
                    intent.putExtra(FiledConstants.SHOW_SCALE, assetUserList.get(i).getShow_decimal());
                    intent.putExtra(FiledConstants.ASSET_ID, assetUserList.get(i).getAsset_id());
                    view13.getContext().startActivity(intent);
                } else {
                    DialogUtils dialogUtils = new DialogUtils(view13.getContext(), R.layout.non_identity_authentication_dialog);
                    TextView tvClose = dialogUtils.getView().findViewById(R.id.tv_close);
                    TextView tvBtn = dialogUtils.getView().findViewById(R.id.tv_btn);
                    tvClose.setOnClickListener(view14 -> dialogUtils.dismiss());
                    tvBtn.setOnClickListener(view15 -> {
                        if (user.getChecking_level() == 2) {
                            Intent identitySuccessActivity = new Intent(view13.getContext(), IdentitySuccessActivity.class);
                            view13.getContext().startActivity(identitySuccessActivity);
                        } else {
                            MobclickAgent.onEvent(view13.getContext(), UMConstants.MINE_ACCOUNT_VAIDATION_BTN);
                            Intent identityAuthentication = new Intent(view13.getContext(), IdentityAuthenticationSelectActivity.class);
                            view13.getContext().startActivity(identityAuthentication);
                        }
                        dialogUtils.dismiss();
                    });
                    dialogUtils.show();
                }
            } else {

                Intent intent = new Intent(view13.getContext(), BlockActivity.class);
                intent.putExtra(FiledConstants.TYPE, 1);
                intent.putExtra(FiledConstants.COIN_CODE, assetUserList.get(i).getCoin_code());
                intent.putExtra(FiledConstants.SHOW_SCALE, assetUserList.get(i).getShow_decimal());
                view13.getContext().startActivity(intent);
            }


        });

        View finalView1 = view;
        assetWithdrawal.setOnClickListener(view12 -> {
            MobclickAgent.onEvent(finalView.getContext(), UMConstants.ASSET_LIST_SHOW_FOR_WITHDRAW_BTN);

            String coin_code = assetUserList.get(i).getCoin_code().toLowerCase();

            if ("cnc".equals(coin_code) || "gusd".equals(coin_code)) {
                UserDO user = UTEXApplication.getLoginUser();
                if (user != null && user.getUser_level() > 1) {
                    Intent intent = new Intent(view12.getContext(), BlockActivity.class);
                    intent.putExtra(FiledConstants.TYPE, 2);
                    intent.putExtra(FiledConstants.COIN_CODE, assetUserList.get(i).getCoin_code());
                    intent.putExtra(FiledConstants.AVAILABLE_FUND, assetUserList.get(i).getAvailable_fund());
                    intent.putExtra(FiledConstants.SHOW_SCALE, assetUserList.get(i).getShow_decimal());
                    intent.putExtra(FiledConstants.ASSET_ID, assetUserList.get(i).getAsset_id());
                    view12.getContext().startActivity(intent);
                } else {
                    DialogUtils dialogUtils = new DialogUtils(finalView1.getContext(), R.layout.non_identity_authentication_dialog);
                    TextView tvClose = dialogUtils.getView().findViewById(R.id.tv_close);
                    TextView tvBtn = dialogUtils.getView().findViewById(R.id.tv_btn);
                    tvClose.setOnClickListener(view14 -> dialogUtils.dismiss());
                    tvBtn.setOnClickListener(view15 -> {
                        if (user.getChecking_level() == 2) {
                            Intent identitySuccessActivity = new Intent(finalView1.getContext(), IdentitySuccessActivity.class);
                            finalView1.getContext().startActivity(identitySuccessActivity);
                        } else {
                            MobclickAgent.onEvent(finalView1.getContext(), UMConstants.MINE_ACCOUNT_VAIDATION_BTN);
                            Intent identityAuthentication = new Intent(finalView1.getContext(), IdentityAuthenticationSelectActivity.class);
                            finalView1.getContext().startActivity(identityAuthentication);
                        }
                        dialogUtils.dismiss();
                    });
                    dialogUtils.show();
                }
            } else {
                Intent intent = new Intent(view12.getContext(), BlockActivity.class);
                intent.putExtra(FiledConstants.TYPE, 2);
                intent.putExtra(FiledConstants.COIN_CODE, assetUserList.get(i).getCoin_code());
                intent.putExtra(FiledConstants.AVAILABLE_FUND, assetUserList.get(i).getAvailable_fund());
                intent.putExtra(FiledConstants.SHOW_SCALE, assetUserList.get(i).getShow_decimal());
                intent.putExtra(FiledConstants.ASSET_ID, assetUserList.get(i).getAsset_id());
                view12.getContext().startActivity(intent);
            }
        });

        assetTrad.setOnClickListener(view1 -> {
            MobclickAgent.onEvent(finalView.getContext(), UMConstants.ASSET_LIST_SHOW_FOR_TRADE_BTN);

            //跳出一个popuwindow
            TradPopupWindow tradPopupWindow = new TradPopupWindow(view1.getContext(), R.layout.fragment_trad);
            String coinCode = assetUserList.get(i).getCoin_code();
            List<TickerDo> tickerByCoinCode = ExTickerDao.getAssetTickerByCoinCode(coinCode);
            tradPopupWindow.setData(tickerByCoinCode);
            tradPopupWindow.show();
        });

        Drawable withdrawal = assetWithdrawal.getResources().getDrawable(R.drawable.property_icon_tx_n);
        withdrawal.setBounds(0, 0, withdrawal.getMinimumWidth(), withdrawal.getMinimumHeight());

        Drawable deposit = assetWithdrawal.getResources().getDrawable(R.drawable.property_icon_cz_n);
        deposit.setBounds(0, 0, deposit.getMinimumWidth(), deposit.getMinimumHeight());

        Drawable withdrawalEnable = assetWithdrawal.getResources().getDrawable(R.drawable.property_icon_tx_uc);
        withdrawalEnable.setBounds(0, 0, deposit.getMinimumWidth(), deposit.getMinimumHeight());

        Drawable depositEnable = assetWithdrawal.getResources().getDrawable(R.drawable.property_icon_cz_uc);
        depositEnable.setBounds(0, 0, withdrawal.getMinimumWidth(), withdrawal.getMinimumHeight());


        switch (assetUserList.get(i).getOpen_type()) {
            case 1:
                assetWithdrawal.setClickable(true);
                btAssetDeposit.setClickable(true);
                assetWithdrawal.setCompoundDrawables(withdrawal, null, null, null);
                btAssetDeposit.setCompoundDrawables(deposit, null, null, null);
                btAssetDeposit.setTextColor(Utils.getResourceColor(view.getContext(), R.color.f398155));
                assetWithdrawal.setTextColor(Utils.getResourceColor(view.getContext(), R.color.f398155));

                break;
            case 2:
                assetWithdrawal.setClickable(false);
                assetWithdrawal.setCompoundDrawables(withdrawalEnable, null, null, null);
                assetWithdrawal.setTextColor(Utils.getResourceColor(view.getContext(), R.color.bd4d6d5_42ffffff));
                break;
            case 3:
                btAssetDeposit.setClickable(false);
                btAssetDeposit.setCompoundDrawables(depositEnable, null, null, null);
                btAssetDeposit.setTextColor(Utils.getResourceColor(view.getContext(), R.color.bd4d6d5_42ffffff));

                break;
            case 4:
                assetWithdrawal.setClickable(false);
                btAssetDeposit.setClickable(false);

                assetWithdrawal.setCompoundDrawables(withdrawalEnable, null, null, null);
                btAssetDeposit.setCompoundDrawables(depositEnable, null, null, null);
                btAssetDeposit.setTextColor(Utils.getResourceColor(view.getContext(), R.color.bd4d6d5_42ffffff));
                assetWithdrawal.setTextColor(Utils.getResourceColor(view.getContext(), R.color.bd4d6d5_42ffffff));

                break;
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    public void setData(List<Asset.DataBean.AssetUserListBean> assetUserList) {
        this.assetUserList = assetUserList;
    }

}
