package com.utex.utils;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.utex.core.UTEXApplication;

import java.util.Map;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ProSun on 16/10/7.
 */
public class SharedPreferencesUtil {
    public static final String SHARE_PREFS = "exnow_share_prefs";
    public static final String PREF_COOKIES = "PREF_COOKIES";

    public static void putBoolean(String key, boolean value) {
        UTEXApplication.getInstance().getSharedPreferences(SHARE_PREFS, MODE_PRIVATE)
                .edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return UTEXApplication.getInstance().getSharedPreferences(SHARE_PREFS, MODE_PRIVATE)
                .getBoolean(key, defaultValue);

    }

    public static void putInteger(String key, int value) {
        UTEXApplication.getInstance().getSharedPreferences(SHARE_PREFS, MODE_PRIVATE)
                .edit().putInt(key, value).commit();
    }

    public static int getInteger(String key) {
        return getInteger(key, 0);
    }

    public static int getInteger(String key, int defaultValue) {
        return UTEXApplication.getInstance().getSharedPreferences(SHARE_PREFS, MODE_PRIVATE)
                .getInt(key, defaultValue);
    }

    public static void putFloat(String key, float value) {
        UTEXApplication.getInstance().getSharedPreferences(SHARE_PREFS, MODE_PRIVATE)
                .edit().putFloat(key, value);
    }

    public static float getFloat(String key) {
        return getFloat(key, 0f);
    }

    public static float getFloat(String key, float defaultValue) {
        return UTEXApplication.getInstance().getSharedPreferences(SHARE_PREFS, MODE_PRIVATE)
                .getFloat(key, defaultValue);
    }

    public static void putString(String key, String value) {
        UTEXApplication.getInstance().getSharedPreferences(SHARE_PREFS, MODE_PRIVATE)
                .edit().putString(key, value).commit();
    }

    public static String getString(String key, String defaultValue) {
        return UTEXApplication.getInstance().getSharedPreferences(SHARE_PREFS, MODE_PRIVATE)
                .getString(key, defaultValue);
    }

    public static void putStringSet(String key, Set<String> value) {
        UTEXApplication.getInstance().getSharedPreferences(SHARE_PREFS, MODE_PRIVATE)
                .edit().putStringSet(key, value).commit();
    }

    public static Set<String> getStringSet(String key, Set<String> defaultValue) {
        return UTEXApplication.getInstance().getSharedPreferences(SHARE_PREFS, MODE_PRIVATE)
                .getStringSet(key, defaultValue);
    }


    public static void putObject(String key, Object obj) {
        UTEXApplication.getInstance().getSharedPreferences(SHARE_PREFS, MODE_PRIVATE)
                .edit().putString(key, JSON.toJSONString(obj))
                .commit();
    }

    public static <T> T getObject(String key, Class<T> tClass) {
        String json = UTEXApplication.getInstance().getSharedPreferences(SHARE_PREFS, MODE_PRIVATE)
                .getString(key, null);
        return TextUtils.isEmpty(json) ? null : JSON.parseObject(json, tClass);
    }

    public static <T extends Map> void putMap(String key, T map) {
        putString(key, JSON.toJSONString(map));
    }

    public static <T extends Map> T getMap(String key, Class<T> tClass) {
        return getObject(key, tClass);
    }
}
