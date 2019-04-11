package com.utex.base;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Demon on 2017/5/11.
 */
@Module
public abstract class BaseModule<Activity extends View, View> {
    protected Activity activity;

    public BaseModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    protected View provideView() {
        return activity;
    }


}
