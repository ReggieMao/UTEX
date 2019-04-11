package com.utex.core;

import android.app.Application;

import com.utex.data.ApiService;
import com.utex.data.ApiServiceModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by admin on 2017/4/6.
 */
@Singleton
@Component(modules = {AppModule.class, ApiServiceModule.class})
public interface AppComponent {

    Application getApplication();

    ApiService getApiService();

}
