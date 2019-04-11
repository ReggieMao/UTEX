package com.utex.mvp.mine.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.utex.R;
import com.utex.common.FiledConstants;
import com.utex.mvp.mine.bean.DistributeRecordVO;
import com.utex.utils.SharedPreferencesUtil;
import com.utex.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Demon on 2018/11/26.
 */
public class DistributeRecordAdapter extends RecyclerView.Adapter<DistributeRecordAdapter.DistributeRecordAdapterViewHolder> {

    private List<DistributeRecordVO.DataBean> data;

    @NonNull
    @Override
    public DistributeRecordAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_distribuute_record, parent, false);
        return new DistributeRecordAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull DistributeRecordAdapterViewHolder holder, int position) {
        DistributeRecordVO.DataBean dataBean = data.get(position);
        holder.tvDistributeRecordTime.setText(Utils.long2String(dataBean.getCreate_time(), 3));
        holder.tvDistributeRecordAmount.setText(Utils.getScientificCountingMethodAfterData(dataBean.getAmount(), 8) + " " + dataBean.getCoin_code().toUpperCase());

        if (TextUtils.isEmpty(dataBean.getEn_title()) && TextUtils.isEmpty(dataBean.getZh_title())) {
            holder.tvDistributeRecordTitle.setText(Utils.getResourceString(R.string.huo_dong_fen_fa));
        }

        if ("zh".equals(SharedPreferencesUtil.getString(FiledConstants.SP_LANGUGE_ABBREVIATION, "tw"))
                || "tw".equals(SharedPreferencesUtil.getString(FiledConstants.SP_LANGUGE_ABBREVIATION, "tw"))) {
            if (TextUtils.isEmpty(dataBean.getZh_title())) {
                if (TextUtils.isEmpty(dataBean.getEn_title())) {
                    holder.tvDistributeRecordTitle.setText(Utils.getResourceString(R.string.huo_dong_fen_fa));
                } else {
                    holder.tvDistributeRecordTitle.setText(dataBean.getEn_title());
                }
            } else {
                if (TextUtils.isEmpty(dataBean.getZh_title())) {
                    holder.tvDistributeRecordTitle.setText(Utils.getResourceString(R.string.huo_dong_fen_fa));
                } else {
                    holder.tvDistributeRecordTitle.setText(dataBean.getZh_title());
                }
            }
        } else {
            if (TextUtils.isEmpty(dataBean.getEn_title())) {
                holder.tvDistributeRecordTitle.setText(Utils.getResourceString(R.string.huo_dong_fen_fa));
            } else {
                holder.tvDistributeRecordTitle.setText(dataBean.getEn_title());
            }
        }

        switch (dataBean.getType()) {
            case 1:
                holder.tvDistributeRecordLabel.setText(Utils.getResourceString(R.string.xi_tong));
                break;
            case 2:
                holder.tvDistributeRecordLabel.setText(Utils.getResourceString(R.string.kong_tou));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return Utils.checkList(data);
    }

    public void setData(List<DistributeRecordVO.DataBean> data, boolean isLoadMore) {
        if (data == null || !isLoadMore) {
            this.data = data;
        } else {
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }

    public List<DistributeRecordVO.DataBean> getData() {
        return data;
    }

    class DistributeRecordAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_distribute_record_title)
        TextView tvDistributeRecordTitle;

        @BindView(R.id.tv_distribute_record_time)
        TextView tvDistributeRecordTime;

        @BindView(R.id.tv_distribute_record_amount)
        TextView tvDistributeRecordAmount;

        @BindView(R.id.tv_distribute_record_label)
        TextView tvDistributeRecordLabel;

        public DistributeRecordAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
