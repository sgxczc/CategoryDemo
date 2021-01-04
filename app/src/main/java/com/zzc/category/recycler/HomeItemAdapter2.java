package com.zzc.category.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zzc.category.R;

import java.util.List;

public class HomeItemAdapter2 extends BaseQuickAdapter<CategoryBean.DataBean.DataListBean, BaseViewHolder> {

    Context context;

    public HomeItemAdapter2(int layoutResId, @Nullable List<CategoryBean.DataBean.DataListBean> data,Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CategoryBean.DataBean.DataListBean item) {
        helper.setText(R.id.item_home_name,item.getTitle());
        ImageView imageView = helper.getView(R.id.item_album);
        Glide.with(context).load(item.getImgURL()).into(imageView);
    }
}
