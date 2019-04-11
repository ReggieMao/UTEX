package com.utex.utils;

import android.content.Intent;

import com.utex.service.OrderSocketService;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * 价钱 深度 获取
 */
public final class OrderWebSocketListener extends WebSocketListener {

    private WebSocket webSocket;

    private OrderSocketService service;


    public OrderWebSocketListener(OrderSocketService orderSocketService) {
        service = orderSocketService;
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
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {

    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        String json = new String(GzipUtils.uncompress(bytes.toByteArray()));
        Intent intent = new Intent();
        intent.setAction("com.ticker_trad");
        intent.putExtra("json", json);
        service.sendBroadcast(intent);
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(1000, null);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {

    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        service.setListener(null);
    }
}