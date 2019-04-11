package com.utex.db;

import android.text.TextUtils;

import com.utex.R;
import com.utex.bean.TickerDo;
import com.utex.core.UTEXApplication;
import com.utex.utils.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Demon on 2018/5/29.
 */
public class ExTickerDao {

    /**
     * 插入 所有币种
     */
    public static void insertTicker(List<TickerDo> tickerDos) {
        TickerDoDao tickerDoDao = UTEXApplication.getmDaoSession().getTickerDoDao();
        tickerDoDao.insertOrReplaceInTx(tickerDos);
    }

    public static ArrayList<String> getMarket(boolean isHasTimeLimit) {
        TickerDoDao tickerDoDao = UTEXApplication.getmDaoSession().getTickerDoDao();
        tickerDoDao.detachAll();
        List<TickerDo> all = tickerDoDao.loadAll();
        Set<String> set = new HashSet();
        ArrayList<String> titles = new ArrayList<>();

        for (TickerDo tickerDo : all) {
            String[] split = tickerDo.getCoin_market_code().split("_");
            if (tickerDo.getIs_show() == 0) {
                continue;
            }

            if (tickerDo.getType() == 1 && isHasTimeLimit) {
                //限时交易区
                set.add(Utils.getResourceString(R.string.xian_shi));
            } else {
                set.add(split[1]);
            }
        }

        if (isHasTimeLimit) {
            titles.add(Utils.getResourceString(R.string.zi_xuan));
        }
        titles.addAll(set);
        for (String str : titles) {
            if (str.equals(Utils.getResourceString(R.string.xian_shi))) {
                //含有限时
                titles.remove(str);
                titles.add(Utils.getResourceString(R.string.xian_shi));
                break;
            }
        }

        return titles;
    }

    /**
     * 根据交易区查询交易区的币种
     */
    public static List<TickerDo> getTickerByMarket(String market) {
        TickerDoDao tickerDoDao = UTEXApplication.getmDaoSession().getTickerDoDao();

        List<TickerDo> list = null;
        if (Utils.getResourceString(R.string.xian_shi).equals(market)) {
            list = tickerDoDao.queryBuilder()
                    .where(TickerDoDao.Properties.Type.eq("1"), TickerDoDao.Properties.Is_show.eq("1"))
                    .build().list();
        } else if (Utils.getResourceString(R.string.zi_xuan).equals(market)) {
            list = tickerDoDao.queryBuilder()
                    .where(TickerDoDao.Properties.Optional.eq("1"), TickerDoDao.Properties.Is_show.eq("1"))
                    .build().list();
        } else {
            list = tickerDoDao.queryBuilder()
                    .where(TickerDoDao.Properties.Type.eq("0"), TickerDoDao.Properties.Is_show.eq("1"),
                            TickerDoDao.Properties.Coin_market_code.like("%_" + market))
                    .build().list();
        }
        return list;
    }

    /**
     * 获取所有币种
     *
     * @return
     */
    public static List<TickerDo> getTickers() {
        TickerDoDao tickerDoDao = UTEXApplication.getmDaoSession().getTickerDoDao();
        List<TickerDo> tickerDos = tickerDoDao.loadAll();
        return tickerDos;
    }

    /**
     * 获取 显示的币种
     *
     * @return
     */
    public static List<TickerDo> getShowTickers() {
        TickerDoDao tickerDoDao = UTEXApplication.getmDaoSession().getTickerDoDao();
        List<TickerDo> tickerDos = tickerDoDao.queryBuilder().where(TickerDoDao.Properties.Is_show.eq("1")).list();
        return tickerDos;
    }

    /**
     * 根据code获取币种
     *
     * @param codeMarketCode
     */
    public static TickerDo getTickerByCode(String codeMarketCode) {
        TickerDoDao tickerDoDao = UTEXApplication.getmDaoSession().getTickerDoDao();
        try {
            if (TextUtils.isEmpty(codeMarketCode)) {
                return tickerDoDao.queryBuilder().list().get(0);
            } else {
                return tickerDoDao.queryBuilder()
                        .where(TickerDoDao.Properties.Coin_market_code.eq(codeMarketCode)).list().get(0);
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据币种code获取币种list
     */
    public static List<TickerDo> getTickerByCoinCode(String coinCode) {
        TickerDoDao tickerDoDao = UTEXApplication.getmDaoSession().getTickerDoDao();
        return tickerDoDao.queryBuilder()
                .where(TickerDoDao.Properties.Coin_market_code.like("%" + coinCode + "%")).list();
    }

    /**
     * 搜索币种
     *
     * @param str
     * @return
     */
    public static List<TickerDo> searchTickers(String str) {
        TickerDoDao tickerDoDao = UTEXApplication.getmDaoSession().getTickerDoDao();
        List<TickerDo> tickerDos = tickerDoDao.queryBuilder().where(TickerDoDao.Properties.Coin_market_code.like("%" + str + "%"),
                TickerDoDao.Properties.Is_show.eq("1")).list();
        return tickerDos;
    }

    /**
     * 修改自选
     *
     * @param coinCode
     */
    public static void changeOption(String coinCode) {
        TickerDoDao tickerDoDao = UTEXApplication.getmDaoSession().getTickerDoDao();
        List<TickerDo> list = tickerDoDao.queryBuilder().where(TickerDoDao.Properties.Coin_market_code.eq(coinCode)).list();
        if (list != null && list.size() > 0) {
            TickerDo tickerDo = list.get(0);
            if (tickerDo.getOptional() == 1) {
                tickerDo.setOptional(0);
            } else {
                tickerDo.setOptional(1);
            }
            tickerDoDao.update(tickerDo);
        }

    }

    /**
     * 清空自选
     */
    public static void clearOption() {
        TickerDoDao tickerDoDao = UTEXApplication.getmDaoSession().getTickerDoDao();
        List<TickerDo> tickerDos = tickerDoDao.loadAll();
        for (TickerDo tickerDo : tickerDos) {
            tickerDo.setOptional(0);
            tickerDoDao.update(tickerDo);
        }
    }

    /**
     * 资产 获取交易对
     */
    public static List<TickerDo> getAssetTickerByCoinCode(String coinCode) {
        TickerDoDao tickerDoDao = UTEXApplication.getmDaoSession().getTickerDoDao();
        return tickerDoDao.queryBuilder()
                .where(TickerDoDao.Properties.Coin_market_code.like(coinCode + "%")).list();
    }

}
