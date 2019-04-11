package com.utex.mvp.market.presenter;

import com.utex.bean.TickerDo;
import com.utex.data.ApiService;
import com.utex.mvp.market.view.MarketFragment;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Demon on 2018/5/30.
 */
public class MarketPresenter implements IMarketPresenter {

    private ApiService apiService;

    public MarketPresenter(ApiService apiService, MarketFragment activity) {
        this.apiService = apiService;
    }

    @Override
    public void sorttickerList(List<TickerDo> tickerDos, int currStatus) {
        //排序
        switch (currStatus) {
            case 1:
                //vol up
                Collections.sort(tickerDos, new Comparator<TickerDo>() {

                    @Override
                    public int compare(TickerDo t1, TickerDo t2) {
                        if (t1.getResultBean() == null) {
                            return -1;
                        }

                        if (t2.getResultBean() == null) {
                            return 1;
                        }
                        if (Double.parseDouble(t1.getResultBean().getVolume()) > Double.parseDouble(t2.getResultBean().getVolume())) {
                            return 1;
                        } else {
                            return -1;
                        }
//                        return t1.getResultBean().getVolume().compareTo(t2.getResultBean().getVolume());
                    }
                });
                break;
            case 2:
                //vol down
                Collections.sort(tickerDos, new Comparator<TickerDo>() {

                    @Override
                    public int compare(TickerDo t1, TickerDo t2) {
                        if (t1.getResultBean() == null) {
                            return 1;
                        }

                        if (t2.getResultBean() == null) {
                            return -1;
                        }
                        if (Double.parseDouble(t1.getResultBean().getVolume()) > Double.parseDouble(t2.getResultBean().getVolume())) {
                            return -1;
                        } else {
                            return 1;
                        }
//                        return t2.getResultBean().getVolume().compareTo(t1.getResultBean().getVolume());
                    }
                });
                break;
            case 3:
                //price up
                Collections.sort(tickerDos, new Comparator<TickerDo>() {

                    @Override
                    public int compare(TickerDo t1, TickerDo t2) {
                        if (t1.getResultBean() == null) {
                            return -1;
                        }

                        if (t2.getResultBean() == null) {
                            return 1;
                        }

                        double v1 = Double.parseDouble(t1.getResultBean().getLast());
                        double v2 = Double.parseDouble(t2.getResultBean().getLast());

                        if (v1 > v2) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                });
                break;
            case 4:
                //price down
                Collections.sort(tickerDos, (t1, t2) -> {
                    if (t1.getResultBean() == null) {
                        return 1;
                    }

                    if (t2.getResultBean() == null) {
                        return -1;
                    }
                    double v1 = Double.parseDouble(t1.getResultBean().getLast());
                    double v2 = Double.parseDouble(t2.getResultBean().getLast());

                    if (v1 > v2) {
                        return -1;
                    } else {
                        return 1;
                    }

                });
                break;
            case 5:
                //rate up
                Collections.sort(tickerDos, (t1, t2) -> {
                    if (t1.getResultBean() == null) {
                        return -1;
                    }

                    if (t2.getResultBean() == null) {
                        return 1;
                    }
                    Double v1 = (Double.parseDouble(t1.getResultBean().getLast()) - Double.parseDouble(t1.getResultBean().getOpen())) / Double.parseDouble(t1.getResultBean().getOpen());
                    Double v2 = (Double.parseDouble(t2.getResultBean().getLast()) - Double.parseDouble(t2.getResultBean().getOpen())) / Double.parseDouble(t2.getResultBean().getOpen());
                    if (v1 > v2) {
                        return 1;
                    } else {
                        return -1;
                    }


//                        return v1.compareTo(v2);
                });
                break;
            case 6:
                //rate down
                Collections.sort(tickerDos, new Comparator<TickerDo>() {

                    @Override
                    public int compare(TickerDo t1, TickerDo t2) {
                        if (t1.getResultBean() == null) {
                            return 1;
                        }

                        if (t2.getResultBean() == null) {
                            return -1;
                        }

                        Double v1 = (Double.parseDouble(t1.getResultBean().getLast()) - Double.parseDouble(t1.getResultBean().getOpen())) / Double.parseDouble(t1.getResultBean().getOpen());
                        Double v2 = (Double.parseDouble(t2.getResultBean().getLast()) - Double.parseDouble(t2.getResultBean().getOpen())) / Double.parseDouble(t2.getResultBean().getOpen());

                        if (v1 > v2) {
                            return -1;
                        } else {
                            return 1;
                        }

//                        return v2.compareTo(v1);
                    }
                });
                break;
        }
    }
}
