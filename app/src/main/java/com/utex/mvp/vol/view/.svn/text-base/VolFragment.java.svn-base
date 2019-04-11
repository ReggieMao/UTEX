package com.utex.mvp.vol.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.utex.R;
import com.utex.base.BaseFragment;
import com.utex.core.AppComponent;
import com.utex.mvp.vol.bean.VolVo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Demon on 2018/5/21.
 */
public class VolFragment extends BaseFragment {

    @BindView(R.id.rv_vol_list)
    RecyclerView rvVolList;
    private VolAdapter volAdapter;
    private List<VolVo> volVos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_vol, container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        volAdapter = new VolAdapter();
        rvVolList.setAdapter(volAdapter);
        rvVolList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        volVos = new ArrayList<>();
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    public void setData(JSONArray data) {
        JSONArray jsonArray = data.getJSONArray(1);

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Log.e("Test", jsonObject.toJSONString());
            VolVo vol = new VolVo();
            vol.setId(jsonObject.getString("id"));
            vol.setAmount(jsonObject.getString("amount"));
            vol.setPrice(jsonObject.getString("price"));
            vol.setTime(jsonObject.getString("time"));
            vol.setType(jsonObject.getString("type"));
            if (volAdapter.getItemCount() == 0) {
                volVos.add(vol);
            } else {
                volVos.add(0, vol);
            }
        }


        if (volVos.size() > 20) {
            volVos = volVos.subList(0, 20);
        }

        volAdapter.setData(volVos);
    }

}
