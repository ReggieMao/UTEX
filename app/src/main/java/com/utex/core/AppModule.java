package com.utex.core;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by admin on 2017/4/18.
 */
@Module
public class AppModule {
    private final UTEXApplication application;

    public AppModule(UTEXApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application providesApplicationContext() {
        return application;
    }
}
