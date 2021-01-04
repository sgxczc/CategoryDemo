package com.zzc.category.recycler;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zzc.category.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView 滚动
 */
public class MainActivity extends AppCompatActivity {

    private List<String> menuList = new ArrayList<>();
    private List<CategoryBean.DataBean> homeList = new ArrayList<>();
    private List<Integer> showTitle;

    private RecyclerView lv_menu;
    private RecyclerView lv_home;

    private MenuAdapter2 menuAdapter;
    private HomeAdapter2 homeAdapter;
    private int currentItem;

    private TextView tv_title;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        loadData();
    }


    private void loadData() {

        String json = getJson(this, "category.json");
        CategoryBean categoryBean = JSONObject.parseObject(json, CategoryBean.class);
        showTitle = new ArrayList<>();
        for (int i = 0; i < categoryBean.getData().size(); i++) {
            CategoryBean.DataBean dataBean = categoryBean.getData().get(i);
            menuList.add(dataBean.getModuleTitle());
            showTitle.add(i);
            homeList.add(dataBean);
        }
        tv_title.setText(categoryBean.getData().get(0).getModuleTitle());

        menuAdapter.notifyDataSetChanged();
        homeAdapter.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initView() {
        lv_menu = (RecyclerView) findViewById(R.id.lv_menu);
        tv_title = (TextView) findViewById(R.id.tv_titile);
        lv_home = (RecyclerView) findViewById(R.id.lv_home);
        /*左边的适配器*/
        LinearLayoutManager managerMenu = new LinearLayoutManager(this);
        managerMenu.setOrientation(LinearLayoutManager.VERTICAL);
        lv_menu.setLayoutManager(managerMenu);
        menuAdapter = new MenuAdapter2(R.layout.item_menu,menuList,this);
        lv_menu.setAdapter(menuAdapter);

        /*右边的适配器*/
        LinearLayoutManager managerHome = new LinearLayoutManager(this);
        managerHome.setOrientation(LinearLayoutManager.VERTICAL);
        lv_home.setLayoutManager(managerHome);
        homeAdapter = new HomeAdapter2(R.layout.item_home2,homeList,this);
        lv_home.setAdapter(homeAdapter);

        menuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                menuAdapter.setSelectItem(position);
                menuAdapter.notifyDataSetChanged();
                tv_title.setText(menuList.get(position));
                lv_home.getLayoutManager().scrollToPosition(showTitle.get(position));
            }
        });


        lv_home.setOnScrollListener(new RecyclerView.OnScrollListener() {
            private int scrollState;
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int scrollState) {
                super.onScrollStateChanged(recyclerView, scrollState);
                this.scrollState = scrollState;
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    return;
                }
                LinearLayoutManager layoutManager= (LinearLayoutManager) recyclerView.getLayoutManager();
                /*获取第一个可见view的位置*/
                int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                /*获取最后一个可见view的位置*/
                int lastVisible = layoutManager.findLastVisibleItemPosition();
                int current = showTitle.indexOf(firstVisibleItem);
                if (currentItem != current && current >= 0) {
                    currentItem = current;
                    tv_title.setText(menuList.get(currentItem));
                    menuAdapter.setSelectItem(currentItem);
                    menuAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    /**
     * 得到json文件中的内容
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        //获得assets资源管理器
        AssetManager assetManager = context.getAssets();
        //使用IO流读取json文件内容
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName), "utf-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


}
