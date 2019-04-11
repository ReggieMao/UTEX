package com.utex.base;

/**
 * Created by Demon on 2017/5/11.
 */
public abstract interface BaseComponent<Activity, Presenter> {
    Activity inject(Activity activity);

    Presenter presenter();
}
