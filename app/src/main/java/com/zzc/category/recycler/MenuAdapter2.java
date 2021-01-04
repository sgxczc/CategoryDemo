package com.zzc.category.recycler;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zzc.category.R;

import java.util.List;

public class MenuAdapter2 extends BaseQuickAdapter<String, BaseViewHolder> {

    private int selectItem = 0;
    Context context;

    public MenuAdapter2(int layoutResId, @Nullable List<String> data,Context context) {
        super(layoutResId, data);
        this.context = context;

    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        helper.setText(R.id.item_name,item);
        TextView tv_name = helper.getView(R.id.item_name);

        int position = helper.getLayoutPosition();
        if (position == selectItem) {
            tv_name.setBackgroundColor(Color.WHITE);
            tv_name.setTextColor(context.getResources().getColor(R.color.green));
        } else {
            tv_name.setBackgroundColor(context.getResources().getColor(R.color.background));
            tv_name.setTextColor(context.getResources().getColor(R.color.black));
        }
    }

    public int getSelectItem() {
        return selectItem;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }
}
