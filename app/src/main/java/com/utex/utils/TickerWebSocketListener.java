package com.utex.utils;

import android.content.Intent;

import com.utex.service.TickerSocketService;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * 价钱 深度 获取
 */
public final class TickerWebSocketListener extends WebSocketListener {

    private WebSocket webSocket;

    private TickerSocketService service;

    private Integer type;

    public TickerWebSocketListener(TickerSocketService tickerSocketService) {
        service = tickerSocketService;
    }

    public WebSocket getWebSocket() {
        return webSocket;
    }

    public void setWebSocket(WebSocket webSocket) {
        this.webSocket = webSocket;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        this.webSocket = webSocket;
        service.sendoldData();
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {

    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        String json = new String(ZLibUtils.decompress(bytes.toByteArray()));
        Intent intent = new Intent();
        switch (type) {
            case 0:
                intent.setAction("com.ticker");
                break;
            case 1:
            case 2:
                intent.setAction("com.ticker_trad");
                break;
            case 3:
                intent.setAction("com.ticker_detail");
                break;
            case 5:
                intent.setAction("com.asset");
                break;
        }

        if (type != 4) {
            intent.putExtra("json", json);
            intent.putExtra("type", type);
            service.sendBroadcast(intent);
        }
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(1000, null);
        service.setWebSocket(null);
        service.setListener(null);
        service.getHandler().removeCallbacksAndMessages(null);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        service.setWebSocket(null);
        service.setListener(null);
        service.getHandler().removeCallbacksAndMessages(null);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        service.setWebSocket(null);
        service.setListener(null);
        service.getHandler().removeCallbacksAndMessages(null);
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}