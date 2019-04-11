package com.utex.utils;

import android.util.Log;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * 下单 等等
 */
public final class AssetWebSocketListener extends WebSocketListener {

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        webSocket.send("hello world");
        webSocket.send("welcome");
        webSocket.send(ByteString.decodeHex("adef"));
        webSocket.close(1000, "再见");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        Log.e("TAG", ("onMessage: " + text));
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        Log.e("TAG", "onMessage byteString: " + bytes);
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(1000, null);
        Log.e("TAG", "onClosing: " + code + "/" + reason);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        Log.e("TAG", "onClosed: " + code + "/" + reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        Log.e("TAG", "onFailure: " + t.getMessage());
    }
}