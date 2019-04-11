package com.utex.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.utex.utils.TickerWebSocketListener;
import com.quintar.IMarketAidlInterface;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

import static com.utex.data.ApiServiceModule.TICKER_WEBSOCKET;

/**
 * Created by Demon on 2018/5/29.
 */
public class TickerSocketService extends Service {

    private TickerSocketService tickerSocketService;

    private WebSocket webSocket;

    private TickerWebSocketListener listener;

    String messageBody = "{\"id\":99,\"method\":\"server.ping\",\"params\":{}}";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:

                    try {
                        send(messageBody, listener.getType());
                    } catch (Exception e) {
                        send(messageBody, 0);
                    }

                    handler.sendEmptyMessageDelayed(0, 10000);
                    break;
                case 1:
                    send(TickerSocketService.this.json, type);
                    break;
            }
        }
    };
    private String json;
    private int type;

    public TickerSocketService() {
        initSocket();
        Log.e("TAG", "初创，");
    }

    /**
     * 初始化 SocketIo
     */
    public void initSocket() {
        listener = new TickerWebSocketListener(this);
        Request request = new Request.Builder()
                .url(TICKER_WEBSOCKET)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();
        handler.removeCallbacksAndMessages(null);
        handler.sendEmptyMessage(0);
    }

    /**
     * 若打开socket  发送数据
     */
    public void sendoldData() {
        Log.e("WebSocket", "发送旧数据:" + json + ":" + type);
        handler.sendEmptyMessageDelayed(1, 200);
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
        try {
            if (!messageBody.equals(json)) {
                this.json = json;
                this.type = type;
            }
            if (webSocket == null) {
                if (listener == null) {
                    initSocket();
                }
                webSocket = listener.getWebSocket();
            }

            if (webSocket != null) {
                Log.e("WebSocket", json);
                listener.setType(type);
                if (!TextUtils.isEmpty(json)) {
                    if (webSocket != null) {
                        webSocket.send(json);
                    }
                }
            }
        } catch (Exception e) {
            Log.e("WebSocket ", "可能socket 参数为空");
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


    public WebSocket getWebSocket() {
        return webSocket;
    }

    public void setWebSocket(WebSocket webSocket) {
        this.webSocket = webSocket;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public TickerWebSocketListener getListener() {
        return listener;
    }

    public void setListener(TickerWebSocketListener listener) {
        this.listener = listener;
    }
}

