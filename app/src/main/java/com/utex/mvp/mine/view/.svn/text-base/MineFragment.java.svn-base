package com.utex.mvp.mine.view;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.utex.R;
import com.utex.base.BaseFragment;
import com.utex.bean.UserDO;
import com.utex.common.Constants;
import com.utex.common.FiledConstants;
import com.utex.common.SDUrl;
import com.utex.common.UMConstants;
import com.utex.core.AppComponent;
import com.utex.core.UTEXApplication;
import com.utex.mvp.hometab.bean.CheckAppVersionVO;
import com.utex.mvp.hometab.view.HomeTabActivity;
import com.utex.mvp.order.view.OrderActivity;
import com.utex.mvp.user.view.LoginActivity;
import com.utex.mvp.web.WebActivity;
import com.utex.recevier.DownloadAppReceiver;
import com.utex.service.DownLoadService;
import com.utex.utils.DialogUtils;
import com.utex.utils.SharedPreferencesUtil;
import com.utex.utils.Utils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Demon on 2018/5/21.
 * 我的
 */
public class MineFragment extends BaseFragment implements DownloadAppReceiver.TickerListener {

    private static final int LANGUAGE = 901;

    @BindView(R.id.tv_mine_name)
    TextView tvMineName;

    @BindView(R.id.tv_mine_uid)
    TextView tvMineUid;

    @BindView(R.id.tv_mine_version)
    TextView tvMineVersion;

    static boolean isAppClick = true;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DownloadAppReceiver downLoadService = new DownloadAppReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.download.app2");
        getActivity().registerReceiver(downLoadService, intentFilter);
        downLoadService.setTickerListener(this);


        Drawable drawable = null;

        try {
            if (!UTEXApplication.getNewVersionData().isIs_new_version()) {
                drawable = getResources().getDrawable(R.drawable.shape_green_circle);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            }
            tvMineVersion.setCompoundDrawables(drawable, null, null, null);
        } catch (Exception e) {
            tvMineVersion.setCompoundDrawables(drawable, null, null, null);
        }

        tvMineVersion.setText("V" + Utils.getVersionCode(getContext()));

    }

    @Override
    public void onResume() {
        super.onResume();
        setUserInfo();
    }

    private void setUserInfo() {
        UserDO query = UTEXApplication.getLoginUser();
        if (query != null) {
            String account = query.getUsername();
            if (account.length() > 13) {
                String substring = account.substring(0, 3);
                String substring2 = account.substring((account.length() - 6), account.length());
                tvMineName.setText(substring + "****" + substring2);
//                tvMineName.setText(account);
            } else if (account.length() == 11) {
                String substring = account.substring(0, 3);
                String substring2 = account.substring((account.length() - 4), account.length());
                tvMineName.setText(substring + "****" + substring2);
//                tvMineName.setText(account);
            } else {
                tvMineName.setText(account);
            }
            tvMineUid.setText("UID：" + String.valueOf(query.getUuid()));
            tvMineName.setClickable(false);
            tvMineUid.setClickable(false);
        } else {
            tvMineName.setText(Utils.getResourceString(R.string.qing_deng_lu));
            tvMineUid.setText(Utils.getResourceString(R.string.huan_ying_lai_dao_exnow));
            tvMineName.setClickable(true);
            tvMineUid.setClickable(true);
        }

//        tvFragmentValuationType.setText(SharedPreferencesUtil.getString(FiledConstants.PALTFORM_VALUAT.toUpperCase(), "USD"));
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @OnClick({R.id.tv_mine_name, R.id.tv_mine_uid, R.id.rl_mine_order, R.id.rl_mine_money_record,
            R.id.rl_mine_submit_work, R.id.rl_mine_security_certification, R.id.rl_mine_distribute_record,
            R.id.rl_mine_identity_authentication, R.id.iv_mine_light_dark, R.id.rl_mine_problem, R.id.rl_mine_notice,
            R.id.rl_mine_about_us, R.id.rl_mine_version_update, R.id.rl_mine_rate_explain, R.id.rl_mine_acceptance, R.id.rl_mine_acceptance1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_mine_distribute_record:
                //分发记录
                if (UTEXApplication.getLoginUser() != null) {
                    Intent distributeRecord = new Intent(getContext(), DistributeRecordActivity.class);
                    startActivity(distributeRecord);
                } else {
                    startLoginPage();
                }
                break;
            case R.id.rl_mine_acceptance:
                //法币承兑
                Intent acceptance = new Intent(getContext(), AcceptanceActivity.class);
                acceptance.putExtra(AcceptanceActivity.TYPE, 0);
                startActivity(acceptance);
                break;
            case R.id.rl_mine_acceptance1:
                //MRY承兑
                Intent acceptance1 = new Intent(getContext(), AcceptanceActivity.class);
                acceptance1.putExtra(AcceptanceActivity.TYPE, 1);
                startActivity(acceptance1);
                break;
            case R.id.rl_mine_rate_explain:
                //费率说明
                Intent rateExplain = new Intent(getContext(), RateExplainActivity.class);

//                Intent rateExplain = new Intent(getContext(), WebActivity.class);
//                int position = SharedPreferencesUtil.getInteger(Constants.SP_LANGUGE_POSITION);
//                if (position == 0) {
//                    rateExplain.putExtra(FiledConstants.LINK_URL, ApiServiceModule.RATE_EXPLAIN_ZH);
//                } else {
//                    rateExplain.putExtra(FiledConstants.LINK_URL, ApiServiceModule.RATE_EXPLAIN_EN);
//                }

                startActivity(rateExplain);
                break;
            case R.id.rl_mine_version_update:
                //更新APP
                if (UTEXApplication.getNewVersionData() == null || !isAppClick) {
                    return;
                }
                CheckAppVersionVO.DataBean data = UTEXApplication.getNewVersionData();

                DialogUtils.updateNewVersionDialog(getContext(), data.getVersion_number(), "11.22", data.getDescription(), new DialogUtils.OnResultListener() {
                    @Override
                    public void onOK() {
                        isAppClick = false;
                        Intent intent = new Intent(getContext(), DownLoadService.class);
                        intent.putExtra(FiledConstants.DOWN_ADDRESS, UTEXApplication.getNewVersionData().getDownload());
                        intent.putExtra(FiledConstants.TYPE, 2);
                        getActivity().startService(intent);
                    }
                });

//                DialogUtils dialogUtils = new DialogUtils(getContext(), R.layout.dialog_update_app);
//                dialogUtils.show();
//                dialogUtils.getInstance().getWindow().setLayout(DensityUtil.dp2px(300), LinearLayout.LayoutParams.WRAP_CONTENT);
//
//                View viewApp = dialogUtils.getView();
//                TextView tvAppVersion = viewApp.findViewById(R.id.tv_app_version);
//                TextView tvAppDescription = viewApp.findViewById(R.id.tv_app_description);
//                ImageView ivUpdateAppClose = viewApp.findViewById(R.id.iv_update_app_close);
//                TextView tvAppDown = viewApp.findViewById(R.id.tv_app_down);
//
//                tvAppVersion.setText("V" + data.getVersion_number());
//                String str = data.getDescription();
//                str = str.replaceAll(";", "\n");
//                tvAppDescription.setText(str);
//                ivUpdateAppClose.setOnClickListener(view1 -> {
//                    dialogUtils.dismiss();
//                });
//                tvAppDown.setOnClickListener(view12 -> {
//                    isAppClick = false;
//                    dialogUtils.dismiss();
//                    Intent intent = new Intent(getContext(), DownLoadService.class);
//                    intent.putExtra(FiledConstants.DOWN_ADDRESS, UTEXApplication.getNewVersionData().getDownload());
//                    intent.putExtra(FiledConstants.TYPE, 2);
//                    getActivity().startService(intent);
//                });
                MobclickAgent.onEvent(getActivity(), UMConstants.MINE_SETTING_CHECK_VERSION);
                break;
            case R.id.rl_mine_about_us:
                //关于我们
                Intent aboutUsWeb = new Intent(getContext(), AboutUsActivity.class);
                startActivity(aboutUsWeb);
                break;
            case R.id.rl_mine_notice:
                //公告中心
                String notice = null;
//                if (UTEXApplication.getLoginUser() != null) {
//                    notice = ApiServiceModule.ZENDESK_LOGIN + UTEXApplication.getUsername() + File.separator + UTEXApplication.getUid() + File.separator + UTEXApplication.getToken() +
//                            "/?return_to=" + ApiServiceModule.BASE_ZENDASK + Utils.getZendaskLangugeCountry() + ApiServiceModule.NOTICK_CENTER + "&platform=app";
//                } else {
//                    notice = ApiServiceModule.BASE_ZENDASK + Utils.getZendaskLangugeCountry() + ApiServiceModule.NOTICK_CENTER;
//                }
                notice = "https://utexsupport.zendesk.com/hc/zh-cn/categories/360001250994-%E5%85%AC%E5%91%8A%E4%B8%AD%E5%BF%83";
                Intent noticeWeb = new Intent(getContext(), WebActivity.class);
                noticeWeb.putExtra(FiledConstants.LINK_URL, notice);
                startActivity(noticeWeb);
                break;
            case R.id.rl_mine_problem:
                //帮助
                String help = null;
//                if (UTEXApplication.getLoginUser() != null) {
//                    help = ApiServiceModule.ZENDESK_LOGIN + UTEXApplication.getUsername() + File.separator + UTEXApplication.getUid() + File.separator + UTEXApplication.getToken() +
//                            "/?return_to=" + ApiServiceModule.BASE_ZENDASK + Utils.getZendaskLangugeCountry() + ApiServiceModule.COMMON_PROBLEM + "&platform=app";
//                } else {
//                    help = ApiServiceModule.BASE_ZENDASK + Utils.getZendaskLangugeCountry() + ApiServiceModule.COMMON_PROBLEM;
//                }
                help = "https://utexsupport.zendesk.com/hc/zh-cn/categories/360001251054-%E5%B8%AE%E5%8A%A9%E4%B8%AD%E5%BF%83";
                Intent web = new Intent(getContext(), WebActivity.class);
                web.putExtra(FiledConstants.LINK_URL, help);
                startActivity(web);
                break;
            case R.id.iv_mine_light_dark:
                //切换亮暗色
                int isSWitchFrist = SharedPreferencesUtil.getInteger(FiledConstants.LIGHT_DARK, 1);

                switch (isSWitchFrist) {
                    case 1:
                        SharedPreferencesUtil.putInteger(FiledConstants.LIGHT_DARK, 2);
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        break;
                    case 2:
                        SharedPreferencesUtil.putInteger(FiledConstants.LIGHT_DARK, 1);
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        break;
                }

                Intent intent = new Intent(getContext(), HomeTabActivity.class);
                intent.putExtra(Constants.DARK_LIGHT_SWITCH, 1);
                startActivity(intent);

                Intent homeTab = getActivity().getIntent();
                homeTab.putExtra(Constants.DARK_LIGHT_SWITCH, 1);
                getActivity().finish();

                getActivity().overridePendingTransition(R.anim.start_anim, R.anim.out_anim);

                break;
            case R.id.tv_mine_name:
            case R.id.tv_mine_uid:
                //跳转到 登陆界面
                startLoginPage();
                break;
            case R.id.rl_mine_order:
                if (UTEXApplication.getLoginUser() != null) {
                    MobclickAgent.onEvent(getContext(), UMConstants.MINE_ORDER_BTN);
                    Intent order = new Intent(getContext(), OrderActivity.class);
                    getContext().startActivity(order);
                } else {
                    //跳转到 登陆界面
                    startLoginPage();
                }
                break;
            case R.id.rl_mine_money_record:
                if (UTEXApplication.getLoginUser() != null) {
                    MobclickAgent.onEvent(getContext(), UMConstants.MINE_CHONGTI_BTN);
                    Intent moneyRecord = new Intent(getContext(), MoneyOperateRecordActivity.class);
                    getContext().startActivity(moneyRecord);
                } else {
                    //跳转到 登陆界面
                    startLoginPage();
                }
                break;
            case R.id.rl_mine_submit_work:
                //跳入 提交工单
                String workList = null;
//                if (UTEXApplication.getLoginUser() != null) {
//                    workList = ApiServiceModule.ZENDESK_LOGIN + UTEXApplication.getUsername() + File.separator + UTEXApplication.getUid() + File.separator + UTEXApplication.getToken() +
//                            "/?return_to=" + ApiServiceModule.BASE_ZENDASK + Utils.getZendaskLangugeCountry() + ApiServiceModule.SUBMIT_WORK_ORDER + "&platform=app";
//                } else {
//                    workList = ApiServiceModule.BASE_ZENDASK + Utils.getZendaskLangugeCountry() + ApiServiceModule.SUBMIT_WORK_ORDER;
//                }
                workList = "https://utexsupport.zendesk.com/hc/zh-cn/requests/new";
                Log.e("TAG", workList);

                MobclickAgent.onEvent(getContext(), UMConstants.MINE_WORKORDER_BTN);

                Intent submitOrder = new Intent(getContext(), WebActivity.class);
                submitOrder.putExtra(FiledConstants.LINK_URL, workList);
                startActivity(submitOrder);
                break;
            case R.id.rl_mine_security_certification:
                //安全认证
                Intent securityCertification = new Intent(getContext(), SetCenterActivity.class);
                getContext().startActivity(securityCertification);
                break;
            case R.id.rl_mine_identity_authentication:
                //身份认证
                UserDO user = UTEXApplication.getLoginUser();
                if (user != null) {
                    if (user.getUser_level() > 1) {
                        Intent identitySuccessActivity = new Intent(getContext(), IdentitySuccessActivity.class);
                        identitySuccessActivity.putExtra(FiledConstants.TYPE, 1);
                        getContext().startActivity(identitySuccessActivity);
                    } else {
                        if (user.getChecking_level() == 2) {
                            Intent identitySuccessActivity = new Intent(getContext(), IdentitySuccessActivity.class);
                            getContext().startActivity(identitySuccessActivity);
                        } else {
                            MobclickAgent.onEvent(getContext(), UMConstants.MINE_ACCOUNT_VAIDATION_BTN);
                            Intent identityAuthentication = new Intent(getContext(), IdentityAuthenticationSelectActivity.class);
                            getContext().startActivity(identityAuthentication);
                        }
                    }
                } else {
                    //              跳转到 登陆界面
                    startLoginPage();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            boolean hasInstallPermission = getContext().getPackageManager().canRequestPackageInstalls();
            if (hasInstallPermission) {
                startInstallApp();
            }
        }
    }

    private void startInstallApp() {
        Uri contentUri = Uri.fromFile(new File(SDUrl.apkPath + "exnow.apk"));
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //兼容7.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");

                //兼容8.0
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    boolean hasInstallPermission = getContext().getPackageManager().canRequestPackageInstalls();
                    if (!hasInstallPermission) {
                        setAllowedInstallationApp();
                        return;
                    }
                }
            } else {
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            if (getContext().getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
                startActivity(intent);
            }
        } catch (Throwable e) {
            e.printStackTrace();
//                DataEmbeddingUtil.dataEmbeddingAPPUpdate(e.toString());
//                CommonUtils.makeEventToast(MyApplication.getContext(), MyApplication.getContext().getString(R.string.download_hint), false);
        }
    }

    private void setAllowedInstallationApp() {
        //注意这个是8.0新API
        Uri packageURI = Uri.parse("package:" + getContext().getPackageName());
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        startActivityForResult(intent, 198);
    }


    @Override
    public void onReceive() {
        isAppClick = true;
        startInstallApp();
    }

    private void startLoginPage() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        MobclickAgent.onEvent(getContext(), UMConstants.NOLOGIN_PUSH_LOGIN_PAGE_ACTION);
    }
}