package com.utex.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.utex.core.UTEXApplication;
import com.utex.data.ApiServiceModule;
import com.utex.utils.OrderWebSocketListener;
import com.quintar.IMarketAidlInterface;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

/**
 * Created by Demon on 2018/5/29.
 */
public class OrderSocketService extends Service {

    private OrderSocketService tickerSocketService;

    private WebSocket webSocket;

    private OrderWebSocketListener listener;

    public OrderSocketService() {
        initSocket();
        Log.e("TAG2", "初创，");
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json = "{\"id\":99,\"method\":\"server.ping\",\"params\":{}}";
            send(json, 1);
            Log.e("TAG2", "begin initSocket");
            handler.sendEmptyMessageDelayed(0, 10000);
        }
    };

    /**
     * 初始化 SocketIo
     */
    public void initSocket() {
        if (listener == null && UTEXApplication.getToken() != null) {
            listener = new OrderWebSocketListener(this);
            String s = ApiServiceModule.ORDER_WEBSOCKET + "token=" + UTEXApplication.getToken() + "&platform=app";
            Log.e("order_socke", s);
            Request request = new Request.Builder()
                    .url(s)
                    .build();
            OkHttpClient client = new OkHttpClient();
            webSocket = client.newWebSocket(request, listener);
            client.dispatcher().executorService().shutdown();
            handler.removeCallbacksAndMessages(null);
            handler.sendEmptyMessage(0);
        }

    }

    public class MarketImpl extends IMarketAidlInterface.Stub {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void pause() throws RemoteException {
            tickerSocketService.pause();
        }

        @Override
        public void destory() throws RemoteException {
            tickerSocketService.destory();
        }

        @Override
        public void send(String json, int type) throws RemoteException {
            tickerSocketService.send(json, type);
        }

    }

    private void send(String json, int type) {
        if (listener == null) {
            initSocket();
        } else if (webSocket == null) {
            listener.getWebSocket();
        } else if (webSocket != null) {
            Log.e("TAG", json);
            webSocket.send(json);
        }
    }

    private void destory() {

    }

    private void pause() {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        tickerSocketService = this;

        return new MarketImpl();
    }

    public OrderWebSocketListener getListener() {
        return listener;
    }

    public void setListener(OrderWebSocketListener listener) {
        this.listener = listener;
    }
}
