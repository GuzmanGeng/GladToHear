package com.mb.mmdepartment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import com.mb.mmdepartment.R;
import com.mb.mmdepartment.adapter.buyplan.ShoppingCartAdapter;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.buyplan.byprice.DataList;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.mb.mmdepartment.bean.marcketseldetail.Lists;
import com.tencent.stat.StatService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ShoppingCartPageActivity extends BaseActivity {

    private ExpandableListView shopping_cart_ell;
    private ArrayList<Lists> lists = new ArrayList<>();
    private Map<String,Lists> shopping_list = TApplication.shop_lists;
    private ShoppingCartAdapter adapter;
    private LuPinModel luPinModel;
    private List<DataList> groups = TApplication.shop_list_to_pick;

    @Override
    public int getLayout() {
        return R.layout.activity_shopping_cart;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initView();
        initData();
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

    private void initView(){
        Intent intent = getIntent();
        shopping_cart_ell = (ExpandableListView)findViewById(R.id.shopping_cart_ell);
        shopping_cart_ell.setGroupIndicator(null);
//        SwipeMenuCreator creator = new SwipeMenuCreator() {
//            @Override
//            public void create(SwipeMenu menu) {
//                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
//                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
//                        0x3F, 0x25)));
//                deleteItem.setWidth(shopping_cart_ell.dp2px(90));
//                deleteItem.setTitle("删除");
//                deleteItem.setTitleSize(18);
//                deleteItem.setTitleColor(Color.WHITE);
//                menu.addMenuItem(deleteItem);
//            }
//        };
//        shopping_cart_ell.setMenuCreator(creator);
        }

    private void initData(){
        adapter = new ShoppingCartAdapter(ShoppingCartPageActivity.this,TApplication.shop_list_to_pick);
//        SwipeMenuAdapter swipeMenuAdapter = new SwipeMenuAdapter(ShoppingCartPageActivity.this,adapter);
//        shopping_cart_ell.setAdapter(swipeMenuAdapter);
        shopping_cart_ell.setAdapter(adapter);
        for(int i = 0;i<groups.size();i++){
            if(!shopping_cart_ell.expandGroup(i))
            shopping_cart_ell.expandGroup(i);
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
                intent.putExtra("lists",(ArrayList)groups);
                startActivityForResult(intent,0);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

//    private ArrayList<DataList> getList(){
//        ArrayList<DataList> dataLists = new ArrayList<>();
//        DataList dataList;
//        List<Lists> listses;
//        for (int i = 0; i < groups.size(); i++) {
//            dataList = new DataList();
//            listses = new ArrayList<>();
//            for (int j = 0; j < groups.get(i).getList().size(); j++) {
//                if (shopping_list.containsKey(groups.get(i).getList().get(j).getId())) {
////                    lists.add(groups.get(i).getList().get(j));
//                    listses.add(groups.get(i).getList().get(j));
//                }
//
//
//            }
//            dataList.setList(listses);
//            dataList.setName(groups.get(i).getName());
//            dataLists.add(dataList);
//        }
//        return dataLists;
//    }

    private void setListener(){
//        shopping_cart_ell.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
//            @Override
//            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
//                Lists item = lists.get(position);
//                lists.remove(item);
//                shopping_list.remove(item);
//                adapter.notifyDataSetChanged();
//            }
//        });
        shopping_cart_ell.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(ShoppingCartPageActivity.this, WaresDetailPageActivity.class);
                Bundle bundle3 = new Bundle();
                bundle3.putSerializable("lists", groups.get(groupPosition).getList().get(childPosition));
                intent.putExtra("bundle", bundle3);
                startActivityForResult(intent,0);
                return false;
            }
        });
        shopping_cart_ell.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (shopping_cart_ell.isGroupExpanded(groupPosition)) {
//                    shopping_cart_ell.setong
                }
                Intent intent = new Intent(ShoppingCartPageActivity.this, MarcketSelDetailActivity.class);
                intent.putExtra("keyword",groups.get(groupPosition).getList().get(0).getShop_id());
                intent.putExtra("shop_name",groups.get(groupPosition).getName());
                startActivityForResult(intent,0);
                return false;
            }
        });
        shopping_cart_ell.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                shopping_cart_ell.expandGroup(groupPosition);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adapter.notifyDataSetChanged();

    }
}
