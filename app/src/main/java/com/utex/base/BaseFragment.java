package com.utex.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.utex.core.AppComponent;
import com.utex.core.UTEXApplication;

/**
 * Created by admin on 2017/4/18.
 */

public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setupActivityComponent(UTEXApplication.getInstance().getAppComponent());
        } catch (Exception e) {
            getActivity().finish();
        }

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    protected abstract void setupActivityComponent(AppComponent appComponent);


}
