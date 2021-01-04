package com.zzc.category.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zzc.category.R;

import java.util.List;

public class HomeAdapter2 extends BaseQuickAdapter<CategoryBean.DataBean, BaseViewHolder> {

    Context context;

    public HomeAdapter2(int layoutResId, @Nullable List<CategoryBean.DataBean> data,Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CategoryBean.DataBean item) {
        helper.setText(R.id.blank,item.getModuleTitle());
        RecyclerView gridView = helper.getView(R.id.gridView);

        List<CategoryBean.DataBean.DataListBean> dataList = item.getDataList();

        GridLayoutManager manager = new GridLayoutManager(context,3);
        gridView.setLayoutManager(manager);
        HomeItemAdapter2 itemAdapter = new HomeItemAdapter2(R.layout.item_home_category,dataList,context);
        gridView.setAdapter(itemAdapter);
    }
}
