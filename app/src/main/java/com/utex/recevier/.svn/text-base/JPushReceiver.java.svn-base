//package com.exnow.recevier;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//
//import cn.jpush.android.service.PushService;
//
//public class JPushReceiver extends BroadcastReceiver {
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        Intent pushintent = new Intent(context, PushService.class);//启动极光推送的服务
//        context.startService(pushintent);
//
//        Log.e("hello", "我擦");
////        Bundle bundle = intent.getExtras();
////
////        if (intent.getAction().equals(JPushInterface.ACTION_NOTIFICATION_RECEIVED)) {
////
////            Log.e("jiguang", "接收到了通知");
////
////            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
////
////            String content = bundle.getString(JPushInterface.EXTRA_ALERT);
////
////            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
////
////            Log.e("jiguang", "标题:【" + title + "】，内容：【" + content + "】，附加参数:【" + extra + "】");
////        } else if (intent.getAction().equals(JPushInterface.ACTION_MESSAGE_RECEIVED)) {
////
////            Log.e("jiguang", "接收到了消息");
////
////            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
////
////            Log.e("jiguang", "接收到的消息是:【" + message + "】");
////        } else if (intent.getAction().equals(JPushInterface.ACTION_NOTIFICATION_OPENED)) {
////            try {
////                String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
////                JSONObject jsonObject = new Gson().fromJson(extra, JSONObject.class);
////                String type = jsonObject.getString("type");
////                String id = jsonObject.getString("id");
////
////                if (type.equals("information")) {
////                    Intent i = new Intent(context, InfoDetailActivity.class);
////                    Bundle information = new Bundle();
////                    information.putInt("id", Integer.parseInt(id));
////                    i.putExtras(bundle);
////                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                    context.startActivity(i);
////                } else if (type.equals("market")) {
////                    Intent i = new Intent(context, MarketDetailActivity.class);
////                    i.putExtra("exchangeName", jsonObject.getString("marketName"));
////                    i.putExtra("coinMarketId", jsonObject.getString("coinMarketId"));
////                    i.putExtra("market", jsonObject.getString("marketCode"));
////                    i.putExtra("coin", jsonObject.getString("coinMarketCode").toLowerCase());
////                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                    context.startActivity(i);
////
////                } else if (type.equals("notice")) {
////                    Intent i = new Intent(context, AdvertisementDetailActivity.class);
////                    i.putExtra(Constants.ID, Integer.parseInt(jsonObject.getString("id")));
////                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////
////                    String string = SharedPreferencesUtil.getString(Constants.GONGGAO_ALL, "");
////                    string += "," + jsonObject.getString("id");
////                    SharedPreferencesUtil.putString(Constants.GONGGAO_ALL, string);
////
////                    context.startActivity(i);
////                } else if (type.equals("message")) {
////                    //跳轉到行情的公告頁面
////                    Intent i = new Intent(context, HomeTabActivity.class);
////                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                    i.putExtra(Constants.TYPE, 1);
////                    context.startActivity(i);
////                } else {
////                    Intent i = new Intent(context, HomeTabActivity.class);
////                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                    context.startActivity(i);
////                }
////            } catch (Exception e) {
////                Intent i = new Intent(context, HomeTabActivity.class);
////                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                context.startActivity(i);
////            }
//
//
//    }
//}
