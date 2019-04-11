package com.utex.mvp.hometab.view;

import com.utex.base.BaseFragment;

import java.util.List;

/**
 * Created by Demon on 2018/5/17.
 */

public interface IHomeTabView {
    void setPage(List<BaseFragment> fragments);


    void updateApp(String download);

}
