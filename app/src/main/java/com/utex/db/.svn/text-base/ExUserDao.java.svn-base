package com.utex.db;

import android.util.Log;

import com.utex.bean.UserDO;
import com.utex.core.UTEXApplication;

import java.util.List;

/**
 * Created by Demon on 2018/6/4.
 */
public class ExUserDao {

    /**
     * 插入 用户
     */
    public static void insertUser(UserDO userDO) {
        UserDODao userDODao = UTEXApplication.getmDaoSession().getUserDODao();
        clearUser();
        userDODao.insert(userDO);
    }

    /**
     * 获取用户
     *
     * @return
     */
    public static UserDO query() {
        UserDODao userDODao = UTEXApplication.getmDaoSession().getUserDODao();
        userDODao.detachAll();
        List<UserDO> userDOS = userDODao.loadAll();
        if (userDOS != null && userDOS.size() > 0) {
            return userDOS.get(0);
        } else {
            return null;
        }
    }

    public static void clearUser() {
        Log.w("Test", "clearUser");
        UserDODao userDODao = UTEXApplication.getmDaoSession().getUserDODao();
        userDODao.deleteAll();
        userDODao.detachAll();
    }
}
