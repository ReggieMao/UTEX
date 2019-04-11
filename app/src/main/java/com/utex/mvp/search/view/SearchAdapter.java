package com.utex.mvp.search.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.utex.R;
import com.utex.bean.TickerDo;
import com.utex.common.Constants;
import com.utex.common.UMConstants;
import com.utex.mvp.marketdetail.view.MarketDetailActivity;
import com.utex.utils.Utils;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Demon on 2018/7/6.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchAdapterViewHoler> {

    private List<TickerDo> tickers;

    public SearchAdapter(List<TickerDo> tickers) {
        this.tickers = tickers;
    }

    @NonNull
    @Override
    public SearchAdapterViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new SearchAdapterViewHoler(inflate, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchAdapterViewHoler holder, final int position) {
        holder.tvSearchCoinName.setText(tickers.get(position).getCoin_market_code().toUpperCase().replace("_", "/"));
        Drawable nav_up = null;
        if (tickers.get(position).getOptional() == 1) {
            //是自选
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                nav_up = holder.tvSearchCoinName.getContext().getDrawable(R.drawable.transaction_icon_collect_sel);
            } else {
                nav_up = holder.tvSearchCoinName.getContext().getResources().getDrawable(R.drawable.transaction_icon_collect_sel);
            }
            nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
            holder.tvSearchCoinName.setCompoundDrawables(null, null, nav_up, null);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                nav_up = holder.tvSearchCoinName.getContext().getDrawable(R.drawable.transaction_icon_collect);
            } else {
                nav_up = holder.tvSearchCoinName.getContext().getResources().getDrawable(R.drawable.transaction_icon_collect);
            }
            nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
            holder.tvSearchCoinName.setCompoundDrawables(null, null, nav_up, null);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return Utils.checkList(tickers);
    }

    public void setData(List<TickerDo> tickers) {
        this.tickers = tickers;
        notifyDataSetChanged();
    }

    class SearchAdapterViewHoler extends RecyclerView.ViewHolder {

        private int position;

        @BindView(R.id.tv_search_coin_name)
        TextView tvSearchCoinName;

        public SearchAdapterViewHoler(View itemView, int viewType) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.position = viewType;

            tvSearchCoinName.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    // et.getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
                    Drawable drawable = tvSearchCoinName.getCompoundDrawables()[2];
                    //如果右边没有图片，不再处理
                    if (drawable == null)
                        return false;
                    //如果不是按下事件，不再处理
                    if (motionEvent.getAction() != MotionEvent.ACTION_UP)
                        return false;
                    if (motionEvent.getX() > tvSearchCoinName.getWidth()
                            - tvSearchCoinName.getPaddingRight()
                            - drawable.getIntrinsicWidth()) {
                        //添加或移除自选

                        MobclickAgent.onEvent(view.getContext(), UMConstants.HOME_SEARCH_PAGE_COLLECT_BTN);

                        switch (tickers.get(position).getOptional()) {
                            case 0:
                                searchOnclickListener.addOption(tickers.get(position));
                                return true;
                            case 1:
                                searchOnclickListener.removeOption(tickers.get(position));
                                return true;
                        }
                    }
                    return false;
                }
            });
        }

        @OnClick({R.id.tv_search_coin_name})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_search_coin_name:
                    if (tickers.get(position).getStatus() == 2) {
                        return;
                    }
                    Intent intent = new Intent(view.getContext(), MarketDetailActivity.class);
                    intent.putExtra(Constants.COIN_CODE, tickers.get(position).getCoin_market_code());
                    Activity activity = (Activity) view.getContext();
                    activity.startActivityForResult(intent, 199);
                    break;
            }
        }
    }

    private SearchOnclickListener searchOnclickListener;

    public void setSearchOnclickListener(SearchOnclickListener searchOnclickListener) {
        this.searchOnclickListener = searchOnclickListener;
    }

    interface SearchOnclickListener {
        void addOption(TickerDo tickerDo);

        void removeOption(TickerDo tickerDo);
    }

}
