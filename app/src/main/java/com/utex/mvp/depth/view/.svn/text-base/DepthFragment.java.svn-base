package com.utex.mvp.depth.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.utex.R;
import com.utex.base.BaseFragment;
import com.utex.bean.TickerDo;
import com.utex.common.FiledConstants;
import com.utex.core.AppComponent;
import com.utex.db.ExTickerDao;
import com.utex.mvp.trad.bean.Depth;
import com.utex.widget.klinechart.DepthMap;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Demon on 2018/5/21.
 */
public class DepthFragment extends BaseFragment {

    @BindView(R.id.rv_depth_bid_list)
    RecyclerView rvDepthBidList;

    @BindView(R.id.rv_depth_asks_list)
    RecyclerView rvDepthAsksList;

    @BindView(R.id.dm_depth_chat)
    DepthMap dmDepthChat;

    private JSONArray data;

    private DepthHandicapAdpater marketHandicapBidAdapter;

    private DepthHandicapAdpater marketHandicapAskAdapter;

    private Depth depth;
    private TickerDo tickerDo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_depth, container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String string = getArguments().getString(FiledConstants.COIN_CODE);
        List<TickerDo> tickerByCoinCode = ExTickerDao.getTickerByCoinCode(string);
        if (tickerByCoinCode != null && tickerByCoinCode.size() > 0) {
            tickerDo = tickerByCoinCode.get(0);
        }

        dmDepthChat.setShowScale(Integer.parseInt(tickerDo.getMoney_decimal()) - Integer.parseInt(tickerDo.getAmount_decimal()));

        rvDepthAsksList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        marketHandicapAskAdapter = new DepthHandicapAdpater(1, tickerDo);
        rvDepthAsksList.setAdapter(marketHandicapAskAdapter);

        rvDepthBidList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        marketHandicapBidAdapter = new DepthHandicapAdpater(2, tickerDo);
        rvDepthBidList.setAdapter(marketHandicapBidAdapter);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    public void setData(JSONArray params) {
        Boolean aBoolean = params.getBoolean(0);
        Depth depth = JSON.parseObject(JSON.toJSONString(params.getJSONObject(1)), Depth.class);

        if (aBoolean) {
            //全量推送
            this.depth = depth;
            if (depth != null) {
                if (depth.getAsks() != null) {
//                    Collections.reverse(depth.getAsks());
                    marketHandicapAskAdapter.setData(depth.getAsks());
                }

                if (depth.getBids() != null) {
                    marketHandicapBidAdapter.setData(depth.getBids());
                }
            }
        } else {
//              增量推送时，每一项的数量如果是正数，需要用新值覆盖旧值，如果是0，表示删除这一项
            if (depth != null && this.depth != null) {
                List<List<String>> asks = depth.getAsks();
                List<List<String>> bids = depth.getBids();
                this.depth.setAsks(calculationDepth(2, asks));
                this.depth.setBids(calculationDepth(1, bids));
            }
        }

        if (this.depth != null) {
            com.utex.mvp.depth.bean.Depth aaaa = new com.utex.mvp.depth.bean.Depth();
            aaaa.setAsks(this.depth.getAsks());
            aaaa.setBids(this.depth.getBids());
            dmDepthChat.setPoint(aaaa);

            marketHandicapAskAdapter.setData(this.depth.getAsks());
            marketHandicapBidAdapter.setData(this.depth.getBids());
            marketHandicapAskAdapter.notifyDataSetChanged();
            marketHandicapBidAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 1 买
     * 2 卖
     *
     * @param type
     * @param list
     */
    private List<List<String>> calculationDepth(final int type, List<List<String>> list) {
        List<List<String>> oldList = null;

        if (this.depth == null) {
            return null;
        }

        switch (type) {
            case 1:
                oldList = this.depth.getBids();

                break;
            case 2:
                oldList = this.depth.getAsks();
                break;
        }
        if (list != null && list.size() > 0) {
            //增量推有数据
            for (List<String> strings : list) {
                if (oldList != null) {
                    for (List<String> a : oldList) {
                        String s = strings.get(0);
                        String s1 = a.get(0);
                        if (s.equals(s1)) {
                            //相等,赋值
                            oldList.remove(a);
                            break;
                        }
                    }
                }
                try {
                    if (Double.parseDouble(strings.get(1)) > 0) {
                        oldList.add(strings);
                    }
                } catch (Exception e) {
                    Log.e("TAG", e.getMessage());
                }
            }

            //进行排序,哼，真麻烦
            Collections.sort(oldList, (t1, t2) -> {
                switch (type) {
                    case 1:
                        if (t1 == null) {
                            return 1;
                        }

                        if (t2 == null) {
                            return -1;
                        }

                        if (Double.parseDouble(t1.get(0)) > Double.parseDouble(t2.get(0))) {
                            return -1;
                        } else {
                            return 1;
                        }
                    case 2:
                        if (t1 == null) {
                            return -1;
                        }

                        if (t2 == null) {
                            return 1;
                        }

                        if (Double.parseDouble(t1.get(0)) > Double.parseDouble(t2.get(0))) {
                            return 1;
                        } else {
                            return -1;
                        }
                }
                return 0;
            });
            if (oldList.size() > 20) {
                return oldList.subList(0, 20);
            }
        }
        return oldList;
    }
}
