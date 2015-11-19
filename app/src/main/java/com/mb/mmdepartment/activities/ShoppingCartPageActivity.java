package com.mb.mmdepartment.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.mb.mmdepartment.R;
import com.mb.mmdepartment.adapter.buyplan.ShoppingCartAdapter;
import com.mb.mmdepartment.adapter.proposedproject.ProposedProjectInnerAdapter;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.mb.mmdepartment.bean.marcketseldetail.Lists;
import com.tencent.stat.StatService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class ShoppingCartPageActivity extends BaseActivity{
//    private ExpandableListView shopping_cart_ell;
//    private ArrayList<Lists> lists = new ArrayList<>();
//    private Map<String,Lists> shopping_list = TApplication.shop_lists;
//    private ShoppingCartAdapter adapter;
//    private List<DataList> groups = TApplication.shop_list_to_pick;
    private LuPinModel luPinModel;
    private List<Lists> shopping_car;//过度购物车
    private List<Lists> shopping_car_order;//排序的购物车
    private RecyclerView shop_car_recycle;
    private ProposedProjectInnerAdapter inner_adapter;
    private TextView back_to_main;

    @Override
    public int getLayout() {
        return R.layout.activity_shopping_cart;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initData();
        initView();
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        luPinModel = new LuPinModel();
        luPinModel.setName("car");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        luPinModel.setOperationtime(sdf.format(new Date()));
        luPinModel.setState("end");
        luPinModel.setType("page");
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

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("购物车");
        action.setHomeButtonEnabled(isTrue);
    }

    private void initView() {
        shop_car_recycle = (RecyclerView) findViewById(R.id.shop_car_recycle);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        shop_car_recycle.setLayoutManager(manager);
        inner_adapter = new ProposedProjectInnerAdapter(shopping_car_order, 0, null, null, null,this);
        shop_car_recycle.setAdapter(inner_adapter);
        back_to_main = (TextView) findViewById(R.id.back_to_main);

        if (shopping_car_order.size() == 0) {
            shop_car_recycle.setVisibility(View.GONE);
        } else {
            shop_car_recycle.setVisibility(View.VISIBLE);
        }
    }

    private void initData() {
        shopping_car = new ArrayList<>();
        shopping_car_order = new ArrayList<>();
        for (String key:TApplication.shop_lists.keySet()) {
            shopping_car.add(TApplication.shop_lists.get(key));
        }
        for (int i=0;i<TApplication.ids.size();i++) {
            if (shopping_car.size() > 0) {
                String name = shopping_car.get(0).getSelect_shop_name();
                if (TextUtils.isEmpty(name)) {
                    name=shopping_car.get(0).getShop_name();
                }
                Lists remove_first = shopping_car.remove(0);
                shopping_car_order.add(remove_first);
                for (int j = 0; j < shopping_car.size(); j++) {
                    String shop_name=shopping_car.get(j).getSelect_shop_name();
                    if (TextUtils.isEmpty(shop_name)) {
                        shop_name = shopping_car.get(j).getShop_name();
                    }
                    if (name.equals(shop_name)) {
                        Lists remove = shopping_car.remove(j);
                        shopping_car_order.add(remove);
                    }
                }
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
        inner_adapter = new ProposedProjectInnerAdapter(shopping_car_order, 0, null, null, null,this);
        shop_car_recycle.setAdapter(inner_adapter);
        if (shopping_car_order.size() == 0) {
            shop_car_recycle.setVisibility(View.GONE);
        } else {
            shop_car_recycle.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shopping_cart, menu);
        MenuItem item = menu.findItem(R.id.goods_shopping_cart);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                LuPinModel luPinModel_list = new LuPinModel();
                luPinModel_list.setType("other");
                luPinModel_list.setState("next");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                luPinModel_list.setOperationtime(sdf.format(new Date()));
                luPinModel_list.setName("productionOrderButton");
                TApplication.luPinModels.add(luPinModel_list);
                Intent intent = new Intent(ShoppingCartPageActivity.this,OrderInfoPageActivity.class);
                intent.putExtra("tag",ShoppingCartPageActivity.class.getSimpleName());
//                intent.putExtra("lists",(ArrayList)groups);
                startActivity(intent);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    private void setListener(){
        back_to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ShoppingCartPageActivity.this, MainActivity.class);
            }
        });
    }
}
