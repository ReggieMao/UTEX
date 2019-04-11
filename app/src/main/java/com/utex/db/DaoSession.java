package com.utex.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.utex.bean.TickerDo;
import com.utex.bean.UserDO;

import com.utex.db.TickerDoDao;
import com.utex.db.UserDODao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig tickerDoDaoConfig;
    private final DaoConfig userDODaoConfig;

    private final TickerDoDao tickerDoDao;
    private final UserDODao userDODao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        tickerDoDaoConfig = daoConfigMap.get(TickerDoDao.class).clone();
        tickerDoDaoConfig.initIdentityScope(type);

        userDODaoConfig = daoConfigMap.get(UserDODao.class).clone();
        userDODaoConfig.initIdentityScope(type);

        tickerDoDao = new TickerDoDao(tickerDoDaoConfig, this);
        userDODao = new UserDODao(userDODaoConfig, this);

        registerDao(TickerDo.class, tickerDoDao);
        registerDao(UserDO.class, userDODao);
    }
    
    public void clear() {
        tickerDoDaoConfig.clearIdentityScope();
        userDODaoConfig.clearIdentityScope();
    }

    public TickerDoDao getTickerDoDao() {
        return tickerDoDao;
    }

    public UserDODao getUserDODao() {
        return userDODao;
    }

}