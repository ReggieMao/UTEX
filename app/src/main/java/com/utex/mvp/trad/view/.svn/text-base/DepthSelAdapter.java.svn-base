package com.utex.mvp.trad.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.utex.R;
import com.utex.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Demon on 2018/7/16.
 */
public class DepthSelAdapter extends RecyclerView.Adapter<DepthSelAdapter.DepthSelAdapterViewHolder> {

    private List<String> list;

    public DepthSelAdapter(List<String> strings) {
        this.list = strings;
    }

    @NonNull
    @Override
    public DepthSelAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_depth_sel, parent, false);
        return new DepthSelAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull DepthSelAdapterViewHolder holder, final int position) {
        holder.tvDepthSelValue.setText(list.get(position));
        holder.tvDepthSelValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                depthSelAdapterViewListener.onClick(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return Utils.checkList(list);
    }

    public void setData(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    class DepthSelAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_depth_sel_value)
        TextView tvDepthSelValue;

        public DepthSelAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private DepthSelAdapterViewListener depthSelAdapterViewListener;

    public void setDepthSelAdapterViewListener(DepthSelAdapterViewListener depthSelAdapterViewListener) {
        this.depthSelAdapterViewListener = depthSelAdapterViewListener;
    }

    public interface DepthSelAdapterViewListener {
        void onClick(String s);
    }

}
