package com.mb.mmdepartment.activities;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import com.jauker.widget.BadgeView;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.adapter.ShopingCarAdapter;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.tencent.stat.StatService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class    ProductsListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ShopingCarAdapter adapter;
    private LinearLayoutManager manager;
    private TextView shopping_car_tv;
    private BadgeView badgeView;
    private Toolbar toolbar;
    private LuPinModel luPinModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);
        initView();
        initToolbar();
        setView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        luPinModel = new LuPinModel();
        luPinModel.setName("ProductsListActivity");
        luPinModel.setState("end");
        luPinModel.setType("page");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        luPinModel.setOperationtime(sdf.format(new Date()));
        StatService.onResume(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        luPinModel.setEndtime(sdf.format(new Date()));
        TApplication.luPinModels.add(luPinModel);
        StatService.onPause(this);
    }

    private void setView() {
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        badgeView.setTargetView(shopping_car_tv);
        badgeView.setBadgeCount(3);
    }

    private void initToolbar() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("品牌选择");
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void initView() {
        recyclerView=(RecyclerView)findViewById(R.id.recycle_market);
        adapter=new ShopingCarAdapter(this);
        manager=new LinearLayoutManager(this);
        shopping_car_tv=(TextView)findViewById(R.id.shopping_car_tv);
        badgeView=new BadgeView(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TApplication.activities.remove(this);
    }
}
