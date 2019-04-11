package com.utex.recevier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Demon on 2018/5/30.
 */
public class TickerReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String json = intent.getStringExtra("json");
        if (tickerListener != null) {
            tickerListener.onReceive(json);
        }

    }

    TickerListener tickerListener;

    public void setTickerListener(TickerListener tickerListener) {
        this.tickerListener = tickerListener;
    }

    public interface TickerListener {
        void onReceive(String json);
    }
}
