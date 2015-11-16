package com.mb.mmdepartment.activities;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.jauker.widget.BadgeView;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.adapter.ExpandableAdapter;
import com.mb.mmdepartment.adapter.catlogs.CatlogListAdapter;
import com.mb.mmdepartment.adapter.catlogs.ChildItem;
import com.mb.mmdepartment.adapter.catlogs.GoodsListAdapter;
import com.mb.mmdepartment.adapter.catlogs.GroupItem;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.mb.mmdepartment.overridge.MyGridLayoutManager;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.List;
public class CatlogsSelActivity extends BaseActivity implements GoodsListAdapter.OnItemClickListener,CatlogListAdapter.OnItemClickListener{
    private List<GroupItem> groupItemList;
    private GroupItem groupItem;
    private ChildItem childItem;
    private List<ChildItem> childItemList;
    private ExpandableAdapter adapter;
    private RecyclerView catlog_sel_recycle;
    private TextView catlog_sel_food_drink_tv,catlog_sel_mom_baby_tv,catlog_sel_family_home_tv,catlog_sel_cosmetic_skin_tv;
    private TextView catlog_sel_buy_count_tv;
    private List<String> list1;
    private List<String> list2;
    private List<String> list3;
    private LinearLayout catlog_sel_sc_liner;
    private boolean isSel;
    private List<String> record;
    private boolean isClick;
    private PopupWindow popupWindow=null;
    @Override
    public int getLayout() {
        return R.layout.activity_catlogs_sel;
    }
    @Override
    public void init(Bundle savedInstanceState) {
        initView();
        initData();
        initRecycleLeft();
//        setData();
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
    }

    private void initRecycleLeft() {
        List<String> list = new ArrayList<>();
        list.add("水果");
        list.add("饮料");
        list.add("蔬菜");
        list.add("饼干");
        list.add("洗衣粉");
        list.add("奶粉");
        list.add("零食");
        list.add("厨房用品");
        list.add("纸巾");
        list.add("床上用品");
        list.add("电子产品");
        list.add("电动车");
        list.add("洗护用品");
        list.add("清洁用品");
//        CatlogListAdapter adapter=new CatlogListAdapter(list,CatlogsSelActivity.this);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        catlog_sel_recycle.setLayoutManager(manager);
//        catlog_sel_recycle.setAdapter(adapter);
    }

    private void initData() {
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();
//        list1.add("我是1标题");
        list1.add("大麻");
        list1.add("香蕉");
        list1.add("咖喱");
        list1.add("猫粮");
        list1.add("狮子");
        list1.add("饼干");
        list1.add("水果");
        list1.add("猫粮");
        list1.add("狮子");
        list1.add("饼干");
        list1.add("水果");
        list1.add("橘子");
        list1.add("栗子");
//        list2.add("我是2标题");
        list2.add("锋利");
        list2.add("铁锹");
        list2.add("出头");
        list2.add("河蚌");
        list2.add("虾米");
        list2.add("啤酒");
        list2.add("铁锹");
        list2.add("出头");
        list2.add("河蚌");
        list2.add("虾米");
        list2.add("啤酒");
        list2.add("易拉罐");
        list2.add("哈格达斯");
//        list3.add("我是标题3");
        list3.add("你这奇葩");
        list3.add("你是喇嘛");
        list3.add("大家都说");
        list3.add("素颜大爱");
        list3.add("可怜兮兮");
        list3.add("真心可爱");
        list3.add("水表");
        list3.add("绅士");
        list3.add("水表");
        list3.add("绅士");
        list3.add("水表");
        list3.add("绅士");
        list3.add("水表");
        list3.add("绅士");


        RecyclerView recyclerView1=new RecyclerView(this);

        RecyclerView recyclerView2=new RecyclerView(this);

        RecyclerView recyclerView3=new RecyclerView(this);

        GridLayoutManager manager1=new MyGridLayoutManager(this,3);
        GridLayoutManager manager2=new MyGridLayoutManager(this,3);
        GridLayoutManager manager3=new MyGridLayoutManager(this,3);

        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        manager2.setOrientation(LinearLayoutManager.VERTICAL);
        manager3.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView1.setLayoutManager(manager1);
        recyclerView2.setLayoutManager(manager2);
        recyclerView3.setLayoutManager(manager3);

        GoodsListAdapter adapter1=new GoodsListAdapter(list1,this);
        GoodsListAdapter adapter2=new GoodsListAdapter(list2,this);
        GoodsListAdapter adapter3=new GoodsListAdapter(list3,this);
        manager1.setSmoothScrollbarEnabled(true);
        manager2.setSmoothScrollbarEnabled(true);
        manager3.setSmoothScrollbarEnabled(true);
        recyclerView1.setAdapter(adapter1);
        recyclerView2.setAdapter(adapter2);
        recyclerView3.setAdapter(adapter3);
        TextView textView1=new TextView(this);
        textView1.setHeight(1);
        textView1.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        textView1.setBackgroundColor(getResources().getColor(R.color.text_half_red));
        textView1.setPadding(0, 5, 0, 5);
        TextView textView2=new TextView(this);
        textView2.setHeight(1);
        textView2.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        textView2.setBackgroundColor(getResources().getColor(R.color.text_half_red));
        textView2.setPadding(0, 5, 0, 5);
        catlog_sel_sc_liner.addView(recyclerView1);
        catlog_sel_sc_liner.addView(textView1);
        catlog_sel_sc_liner.addView(recyclerView2);
        catlog_sel_sc_liner.addView(textView2);
        catlog_sel_sc_liner.addView(recyclerView3);
    }
    private void initView() {
        catlog_sel_buy_count_tv=(TextView)findViewById(R.id.catlog_sel_buy_count_tv);
        catlog_sel_recycle=(RecyclerView)findViewById(R.id.catlog_sel_recycle);
        catlog_sel_sc_liner=(LinearLayout)findViewById(R.id.catlog_sel_sc_liner);

        catlog_sel_food_drink_tv=(TextView)findViewById(R.id.catlog_sel_food_drink_tv);
        catlog_sel_mom_baby_tv=(TextView)findViewById(R.id.catlog_sel_mom_baby_tv);
        catlog_sel_family_home_tv=(TextView)findViewById(R.id.catlog_sel_family_home_tv);
        catlog_sel_cosmetic_skin_tv=(TextView)findViewById(R.id.catlog_sel_cosmetic_skin_tv);
        record = new ArrayList<>();
        record.add("西瓜");
        record.add("土豆");
        record.add("香蕉");
        record.add("四季豆");
        record.add("水果");
    }
    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("商品类别选择");
        action.setHomeButtonEnabled(isTrue);
    }
    private void setData() {
        String[] catlogs=getResources().getStringArray(R.array.Catalogs);
        String[] food_drink_catalog=getResources().getStringArray(R.array.food_drink_catalog);
        String[] daily_necessities_catalog=getResources().getStringArray(R.array.daily_necessities_catalog);
        String[] mami_baby_catalog=getResources().getStringArray(R.array.mami_baby_catalog);
        String[] fresh_food_catalog=getResources().getStringArray(R.array.fresh_food_catalog);
        String[] aesthetic_nursing_catalog=getResources().getStringArray(R.array.aesthetic_nursing_catalog);
        String[] washing_nursing_catalog=getResources().getStringArray(R.array.washing_nursing_catalog);
        String[] baby_toys_catalog=getResources().getStringArray(R.array.baby_toys_catalog);
        groupItemList=new ArrayList<>();
        /**
         * 1
         */
        groupItem=new GroupItem();
        childItemList=new ArrayList<>();
        for (int i=0;i<food_drink_catalog.length;i++){
            childItem=new ChildItem();
            childItem.setCatlogName(food_drink_catalog[i]);
            childItem.setIsSel(false);
            childItemList.add(childItem);
        }
        groupItem.setList(childItemList);
        groupItem.setTitle(catlogs[0]);
        groupItemList.add(groupItem);
/**
 * 2
 */
        groupItem=new GroupItem();
        childItemList=new ArrayList<>();
        for (int i=0;i<daily_necessities_catalog.length;i++){
            childItem=new ChildItem();
            childItem.setCatlogName(daily_necessities_catalog[i]);
            childItem.setIsSel(false);
            childItemList.add(childItem);
        }
        groupItem.setList(childItemList);
        groupItem.setTitle(catlogs[1]);
        groupItemList.add(groupItem);
/**
 * 3
 */
        groupItem=new GroupItem();
        childItemList=new ArrayList<>();
        for (int i=0;i<mami_baby_catalog.length;i++){
            childItem=new ChildItem();
            childItem.setCatlogName(mami_baby_catalog[i]);
            childItem.setIsSel(false);
            childItemList.add(childItem);
        }
        groupItem.setList(childItemList);
        groupItem.setTitle(catlogs[2]);
        groupItemList.add(groupItem);
/**
 * 4
 */
        groupItem=new GroupItem();
        childItemList=new ArrayList<>();
        for (int i=0;i<fresh_food_catalog.length;i++){
            childItem=new ChildItem();
            childItem.setCatlogName(fresh_food_catalog[i]);
            childItem.setIsSel(false);
            childItemList.add(childItem);
        }
        groupItem.setList(childItemList);
        groupItem.setTitle(catlogs[3]);
        groupItemList.add(groupItem);
/**
 * 5
 */
        groupItem=new GroupItem();
        childItemList=new ArrayList<>();
        for (int i=0;i<aesthetic_nursing_catalog.length;i++){
            childItem=new ChildItem();
            childItem.setCatlogName(aesthetic_nursing_catalog[i]);
            childItem.setIsSel(false);
            childItemList.add(childItem);
        }
        groupItem.setList(childItemList);
        groupItem.setTitle(catlogs[4]);
        groupItemList.add(groupItem);
/**
 * 6
 */
        groupItem=new GroupItem();
        childItemList=new ArrayList<>();
        for (int i=0;i<washing_nursing_catalog.length;i++){
            childItem=new ChildItem();
            childItem.setCatlogName(washing_nursing_catalog[i]);
            childItem.setIsSel(false);
            childItemList.add(childItem);
        }
        groupItem.setList(childItemList);
        groupItem.setTitle(catlogs[5]);
        groupItemList.add(groupItem);
/**
 * 7
 */
        groupItem=new GroupItem();
        childItemList=new ArrayList<>();
        for (int i=0;i<baby_toys_catalog.length;i++){
            childItem=new ChildItem();
            childItem.setCatlogName(baby_toys_catalog[i]);
            childItem.setIsSel(false);
            childItemList.add(childItem);
        }
        groupItem.setList(childItemList);
        groupItem.setTitle(catlogs[6]);
        groupItemList.add(groupItem);

//        adapter=new ExpandableAdapter(this,groupItemList,this);
//        catlog_sel_expandableListView.setAdapter(adapter);
    }
    private void setListener() {
        catlog_sel_buy_count_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isClick){
                    if (popupWindow!=null){
                        isClick=false;
                        popupWindow.dismiss();
                    }
                }else {
                    View viewPop=LayoutInflater.from(CatlogsSelActivity.this).inflate(R.layout.catlog_sel_pop,null,false);
                    RecyclerView recyclerView=(RecyclerView)viewPop.findViewById(R.id.catlog_sel_pop_recycle);
                    GoodsListAdapter adapter=new GoodsListAdapter(record,CatlogsSelActivity.this);
                    GridLayoutManager manager=new GridLayoutManager(CatlogsSelActivity.this,4);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(adapter);
                    showPopUp(catlog_sel_buy_count_tv,viewPop);
                    isClick=true;
                }
            }
        });
    }
    private void showPopUp(View v,View item) {
        popupWindow = new PopupWindow(item, LinearLayout.LayoutParams.MATCH_PARENT,800);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0], location[1]-popupWindow.getHeight());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onItemClick(View view, int position) {
//
//        if (isSel){
//            ((TextView)view).setBackgroundResource(R.drawable.cicle_back_login_tv);
//            ((TextView)view).setTextColor(getResources().getColor(R.color.color_white));
//            record.remove(((TextView) view).getText().toString());
//            isSel=false;
//        }else {
//            ((TextView)view).setBackgroundResource(R.drawable.cicle_back_catlog_sel);
//            ((TextView)view).setTextColor(getResources().getColor(R.color.text_half_red));
//            record.add(((TextView) view).getText().toString());
//            isSel=true;
//        }
//        BadgeView badgeView = new BadgeView(this);
//        if (record.size()!=0){
//            badgeView.setVisibility(View.VISIBLE);
//            badgeView.setText(record.size()+"");
//            badgeView.setTargetView(catlog_sel_buy_count_tv);
//        }else {
//            badgeView.setVisibility(View.GONE);
//        }
//    }

    @Override
    public void onClick(View view) {
        if (isSel){
            ((TextView)view).setTextColor(getResources().getColor(R.color.color_white));
//            ((TextView)view).setBackgroundResource(R.drawable.cicle_back_login_tv);
            view.setBackground(getResources().getDrawable(R.drawable.cicle_back_login_tv));
            record.remove(((TextView) view).getText().toString());
//            LuPinModel luPinModelsel = new LuPinModel();
//            luPinModelsel.setName();
            isSel=false;
        }else {
            ((TextView)view).setTextColor(getResources().getColor(R.color.text_half_red));
//            ((TextView)view).setBackgroundResource(R.drawable.cicle_back_catlog_sel);
            view.setBackgroundResource(R.drawable.cicle_back_catlog_sel);
            record.add(((TextView) view).getText().toString());
            isSel=true;
        }
        BadgeView badgeView = new BadgeView(this);
        if (record.size()!=0){
            badgeView.setVisibility(View.VISIBLE);
            badgeView.setText(record.size()+"");
            badgeView.setTargetView(catlog_sel_buy_count_tv);
        }else {
            badgeView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(int position) {

    }
}
