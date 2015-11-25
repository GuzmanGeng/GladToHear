package com.mb.mmdepartment.activities;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import com.mb.mmdepartment.tools.CustomToast;
import com.mb.mmdepartment.tools.shop_car.ShopCarAtoR;
import com.tencent.stat.StatService;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class ShoppingCartPageActivity extends BaseActivity{
    private LuPinModel luPinModel;
    private List<Lists> shopping_car;//过度购物车
    private List<Lists> shopping_car_order;//排序的购物车
    private RecyclerView shop_car_recycle;
    private ProposedProjectInnerAdapter inner_adapter;
    private TextView back_to_main;
    private ShopCarAtoR shopCarAtoR;
    private int index;

    @Override
    public int getLayout() {
        return R.layout.activity_shopping_cart;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        shopCarAtoR = new ShopCarAtoR(ShoppingCartPageActivity.this);
        initData();
        initView();
        setListener();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LuPingDestory("shopping_Car", "page", "end", new Date());
        TApplication.activities.remove(this);
    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("购物车");
        action.setHomeButtonEnabled(isTrue);
    }

    private void initView() {
        shop_car_recycle = (RecyclerView) findViewById(R.id.shop_car_recycle);
        shop_car_recycle.setHasFixedSize(true);
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
                if (TApplication.ids.size() == 0) {
                    CustomToast.show(ShoppingCartPageActivity.this, "提示", "购物车空了,先去添加吧!");
                } else {
                    LuPing("btn_Order_List","other","next",new Date());
                    Intent intent = new Intent(ShoppingCartPageActivity.this,OrderInfoPageActivity.class);
                    intent.putExtra("tag",ShoppingCartPageActivity.class.getSimpleName());
                    startActivity(intent);
                }
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
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,ItemTouchHelper.LEFT){
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                LuPingWithSelectId(shopping_car_order.get(position).getId(),"car","unSelected","shopping_car",shopping_car_order.get(position).getSelect_shop_id(),new Date());
                shopping_car_order.remove(position);
                inner_adapter.notifyItemRemoved(position);
                index=position;
                inner_adapter = null;
                inner_adapter = new ProposedProjectInnerAdapter(shopping_car_order, 0, null, null, null,ShoppingCartPageActivity.this);
                shop_car_recycle.setAdapter(inner_adapter);
                shop_car_recycle.scrollToPosition(index);
                shopCarAtoR.remove_cars_index(TApplication.ids.get(position));
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    //左右滑动时改变Item的透明度
                    final float alpha = 1 - Math.abs(dX) / (float)viewHolder.itemView.getWidth();
                    viewHolder.itemView.setAlpha(alpha);
                    viewHolder.itemView.setTranslationX(dX);
                }
            }
        });
        itemTouchHelper.attachToRecyclerView(shop_car_recycle);
    }
}
