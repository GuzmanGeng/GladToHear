package com.mb.mmdepartment.activities;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.mb.mmdepartment.adapter.catlogs.LeftCatlogAdapter;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.mb.mmdepartment.tools.CustomToast;
import com.mb.mmdepartment.tools.sp.SPCache;
import com.readystatesoftware.viewbadger.BadgeView;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.adapter.catlogs.CatlogListAdapter;
import com.mb.mmdepartment.adapter.commodity.CommodityListAdapter;
import com.mb.mmdepartment.adapter.commodity.CommoditySelectedAdapter;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.bean.commodity.CategoryList;
import com.mb.mmdepartment.bean.commodity.Root;
import com.mb.mmdepartment.bean.commodity.SelRecord;
import com.mb.mmdepartment.bean.commodity.Type;
import com.mb.mmdepartment.biz.calculate.CommodityBiz;
import com.mb.mmdepartment.listener.CommodityRightItemClickListener;
import com.mb.mmdepartment.listener.CommoditySelectedListener;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.view.LoadingDialog;
import com.tencent.stat.StatService;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class CalculateSelectCategoryActivity extends BaseActivity implements CommodityRightItemClickListener,RequestListener,CatlogListAdapter.OnItemClickListener,CommoditySelectedListener{
    private final String TAG = CalculateSelectCategoryActivity.class.getSimpleName();
    private TextView floatButton,buy_plan_by_supermarket_tv,buy_plan_brand_tv;
    private RecyclerView goods_sel_recycle,goods_selected_recycle;
    private TextView catlog_sel_food_drink_tv,catlog_sel_mom_baby_tv,catlog_sel_family_home_tv,catlog_sel_cosmetic_skin_tv;
    private LinearLayout goods_sel_liner,goods_sel_liner_parent;
    private static int selWhich;
    private BadgeView badge;
    private ListView catlog_sel_recycle;
    /**
     * 网络请求接口
     */
    private CommodityBiz biz;
    private LoadingDialog dialog;
    private String shop_id;
    /**
     * 服务端返回总数据
     */
    private List<CategoryList> totalList;
    private CategoryList food_drink_list;
    private CategoryList mom_baby_list;
    private CategoryList family_home_list;
    private CategoryList cosmetic_skin_list;
    private int fistVisiable;
    private List<Type> food_drink_types,mom_baby_types,family_home_types,cosmetic_skin_types;
    private RelativeLayout relativelayout;
    /**
     * adapter设定
     */
//    private CatlogListAdapter mom_baby_catlogListAdapter,family_home_catlogListAdapter,cosmetic_skin_catlogListAdapter;
    private CommodityListAdapter food_drink_commodityListAdapter,mom_baby_commodityListAdapter,family_home_commodityListAdapter,cosmetic_skin_commodityListAdapter;
    private CommoditySelectedAdapter adapter;
    private LeftCatlogAdapter food_drink_catlogListAdapter;
    /**
     * 保存记录
     */
    public List<SelRecord> records = new ArrayList<>();
    private SelRecord record;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    initData();
                    break;
                case 1:
                    showToast("网络链接异常");
                    break;
                case 10:
                    no_data_tv.setVisibility(View.VISIBLE);
                    catlog_sel_recycle.setVisibility(View.GONE);
                    goods_sel_recycle.setVisibility(View.GONE);
                    break;
            }
        }
    };
    /**
     * 遮盖层
     */
    private TextView no_data_tv;
    private LinearLayout contenner_progress;

    @Override
    public int getLayout() {
        return R.layout.content_commodity;
    }

    /**
     * w网络请求完毕后的数据设定
     */
    private void initData() {
        endProgress();
        checkData(0);
        try{
            goods_sel_recycle.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    fistVisiable=((LinearLayoutManager)goods_sel_recycle.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                    food_drink_catlogListAdapter.setDefSelction(fistVisiable);
                    catlog_sel_recycle.setSelection(fistVisiable);
                }
            });
        }catch (Exception e) {

        }
    }
    /**
     * 设置recycleview显示方式
     * @param
     * @param
     */
    private void setLayoutManager(RecyclerView recyclerView) {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
    }
    @Override
    public void init(Bundle savedInstanceState) {
        shop_id=getIntent().getStringExtra("shop_id");
        initView();
        setListener();
    }

    /**
     * 设置背景色
     */
    private TextView[] tv_sel=new TextView[4];
    public void setBackGround(int which,int two,int three,int fore){
        tv_sel[which].setTextColor(getResources().getColor(R.color.color_white));
        tv_sel[which].setBackgroundColor(getResources().getColor(R.color.text_little_half_red));

        tv_sel[two].setTextColor(getResources().getColor(R.color.theme_color));
        tv_sel[two].setBackgroundColor(getResources().getColor(R.color.color_white));

        tv_sel[three].setTextColor(getResources().getColor(R.color.theme_color));
        tv_sel[three].setBackgroundColor(getResources().getColor(R.color.color_white));

        tv_sel[fore].setTextColor(getResources().getColor(R.color.theme_color));
        tv_sel[fore].setBackgroundColor(getResources().getColor(R.color.color_white));
    }

    private String[] titles = new String[]{"食品饮料","母婴亲子","日用家化","护肤彩妆"};
    public void checkData(int which){
        if (View.VISIBLE==no_data_tv.getVisibility()){
            no_data_tv.setVisibility(View.GONE);
        }
        if (totalList.size()==0) {
            handler.sendEmptyMessage(10);
        }else {
            for (int i = 0; i < totalList.size(); i++) {
                if (titles[which].equals(totalList.get(i).getTitle())) {
                    if (which == 0) {
                        food_drink_list = totalList.get(i);
                        food_drink_types=food_drink_list.getSon();
                        food_drink_commodityListAdapter=new CommodityListAdapter(CalculateSelectCategoryActivity.this,food_drink_types,which,CalculateSelectCategoryActivity.this);
                        food_drink_catlogListAdapter= new LeftCatlogAdapter(this,food_drink_types);
                        catlog_sel_recycle.setAdapter(food_drink_catlogListAdapter);
                        goods_sel_recycle.setAdapter(food_drink_commodityListAdapter);
                    }else if (which == 1) {
                        mom_baby_list = totalList.get(i);
                        mom_baby_types=mom_baby_list.getSon();
                        mom_baby_commodityListAdapter=new CommodityListAdapter(CalculateSelectCategoryActivity.this,mom_baby_types,which,CalculateSelectCategoryActivity.this);
                        food_drink_catlogListAdapter= new LeftCatlogAdapter(this,mom_baby_types);
                        catlog_sel_recycle.setAdapter(food_drink_catlogListAdapter);
                        goods_sel_recycle.setAdapter(mom_baby_commodityListAdapter);
                    }else if (which == 2) {
                        family_home_list = totalList.get(i);
                        family_home_types=family_home_list.getSon();
                        family_home_commodityListAdapter=new CommodityListAdapter(CalculateSelectCategoryActivity.this,family_home_types,which,CalculateSelectCategoryActivity.this);
                        food_drink_catlogListAdapter= new LeftCatlogAdapter(this,family_home_types);
                        catlog_sel_recycle.setAdapter(food_drink_catlogListAdapter);
                        goods_sel_recycle.setAdapter(family_home_commodityListAdapter);
                    }else if (which == 3) {
                        cosmetic_skin_list = totalList.get(i);
                        cosmetic_skin_types=cosmetic_skin_list.getSon();
                        cosmetic_skin_commodityListAdapter=new CommodityListAdapter(CalculateSelectCategoryActivity.this,cosmetic_skin_types,which,CalculateSelectCategoryActivity.this);
                        food_drink_catlogListAdapter= new LeftCatlogAdapter(this,cosmetic_skin_types);
                        catlog_sel_recycle.setAdapter(food_drink_catlogListAdapter);
                        goods_sel_recycle.setAdapter(cosmetic_skin_commodityListAdapter);
                    }
                    if (View.GONE == catlog_sel_recycle.GONE && View.GONE == goods_sel_recycle.GONE) {
                        catlog_sel_recycle.setVisibility(View.VISIBLE);
                        goods_sel_recycle.setVisibility(View.VISIBLE);
                    }
                    return;
                }
            }
            handler.sendEmptyMessage(10);
        }
    }
    private void setListener() {
        catlog_sel_food_drink_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selWhich != 0) {
                    selWhich = 0;
                    setBackGround(0, 1, 2, 3);
                    checkData(selWhich);
                }
            }
        });
        catlog_sel_mom_baby_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selWhich != 1) {
                    selWhich = 1;
                    setBackGround(1, 0, 2, 3);
                    checkData(selWhich);
                }
            }
        });

        catlog_sel_family_home_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selWhich != 2) {
                    selWhich = 2;
                    setBackGround(2, 0, 1, 3);
                    checkData(selWhich);
                }
            }
        });
        catlog_sel_cosmetic_skin_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selWhich = 3;
                setBackGround(3, 0, 1, 2);
                checkData(selWhich);
            }
        });
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (floatButton.getVisibility() == View.VISIBLE) {
                    goods_sel_liner_parent.setVisibility(View.VISIBLE);
                    goods_sel_liner.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * 初始化view
     */
    private void initView() {
        contenner_progress = (LinearLayout) findViewById(R.id.contenner_progress);
        no_data_tv = (TextView) findViewById(R.id.no_data_tv);
        floatButton = (TextView) findViewById(R.id.floatButton);
        goods_sel_recycle = (RecyclerView) findViewById(R.id.goods_sel_recycle);
        setLayoutManager(goods_sel_recycle);
        catlog_sel_recycle = (ListView) findViewById(R.id.catlog_sel_recycle);
        catlog_sel_recycle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                food_drink_catlogListAdapter.setDefSelction(i);
                catlog_sel_recycle.setSelection(i);
                goods_sel_recycle.scrollToPosition(i);
            }
        });
        goods_selected_recycle = (RecyclerView) findViewById(R.id.goods_selected_recycle);

        catlog_sel_food_drink_tv = (TextView) findViewById(R.id.catlog_sel_food_drink_tv);

        catlog_sel_food_drink_tv.setTextColor(getResources().getColor(R.color.color_white));
        catlog_sel_food_drink_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));

        catlog_sel_mom_baby_tv = (TextView) findViewById(R.id.catlog_sel_mom_baby_tv);

        catlog_sel_family_home_tv = (TextView) findViewById(R.id.catlog_sel_family_home_tv);

        catlog_sel_cosmetic_skin_tv = (TextView) findViewById(R.id.catlog_sel_cosmetic_skin_tv);

        tv_sel[0] = catlog_sel_food_drink_tv;
        tv_sel[1] = catlog_sel_mom_baby_tv;
        tv_sel[2] = catlog_sel_family_home_tv;
        tv_sel[3] = catlog_sel_cosmetic_skin_tv;


        goods_sel_liner = (LinearLayout) findViewById(R.id.goods_sel_liner);
        goods_sel_liner_parent = (LinearLayout) findViewById(R.id.goods_sel_liner_parent);
        relativelayout = (RelativeLayout) findViewById(R.id.relativelayout);

        goods_sel_liner_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!goods_sel_liner.isFocused()) {
                    goods_sel_liner_parent.setVisibility(View.GONE);
                }
            }
        });

        if (isNetworkConnected(this)) {
            if (dialog == null) {
                setProgress(contenner_progress);
                biz = new CommodityBiz();
                biz.getCommodityList(0, SPCache.getString("city_id", "50"), shop_id, TAG, this);
            }
        } else {
            showToast("网络无连接");
        }

        badge = new BadgeView(this, floatButton);
        badge.setText(records.size() + "");
        badge.setBadgePosition(BadgeView.POSITION_TOP_LEFT);
        badge.show();

        buy_plan_by_supermarket_tv = (TextView) findViewById(R.id.buy_plan_by_supermarket_tv);
        buy_plan_brand_tv = (TextView) findViewById(R.id.buy_plan_brand_tv);
        buy_plan_brand_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (records.size() == 0) {
                    CustomToast.show(CalculateSelectCategoryActivity.this,"提示","请先选择要查询的商品");
                    return;
                }
                LuPing("btn_Accu_Category_Priority", "category", "next", new Date());
                Intent intent = new Intent(CalculateSelectCategoryActivity.this, ProposedProjectActivity.class);
                intent.putExtra("which", 1);//按品类优先
                intent.putExtra("shop_id", shop_id);
                String category_ids = "";
                for (int i = 0; i < records.size(); i++) {
                    category_ids = category_ids + records.get(i).getCategory_id() + ",";
                }
                if (category_ids.endsWith(",")) {
                    category_ids = category_ids.substring(0, category_ids.length() - 1);
                }
                intent.putExtra("records", category_ids);
                startActivity(intent);
            }
        });
        buy_plan_by_supermarket_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (records.size() == 0) {
                    CustomToast.show(CalculateSelectCategoryActivity.this,"提示","请先选择要查询的商品");
                    return;
                }
                LuPing("btn_Accu_Shop_Priority", "category", "next", new Date());
                Intent intent = new Intent(CalculateSelectCategoryActivity.this, ProposedProjectActivity.class);
                intent.putExtra("shop_id", shop_id);
                intent.putExtra("which", 2);
                String category_ids = "";
                for (int i = 0; i < records.size(); i++) {
                    category_ids = category_ids + records.get(i).getCategory_id() + ",";
                }
                if (category_ids.endsWith(",")) {
                    category_ids = category_ids.substring(0, category_ids.length() - 1);

                    Log.e("category_ids", category_ids);

                }
                intent.putExtra("records", category_ids);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                OkHttp.mOkHttpClient.cancel(TAG);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue){
        action.setTitle("商品选择");
        action.setHomeButtonEnabled(isTrue);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        OkHttp.mOkHttpClient.cancel(TAG);
        finish();
    }

    @Override
    public void onResponse(Response response) {
        if (response.isSuccessful()) {
            Gson gson = new Gson();
            try {
                String json = response.body().string();
                Log.e("commodity",json);
                Root root = gson.fromJson(json, Root.class);
                if (root.getStatus() == OkHttp.NET_STATE) {
                    totalList = root.getData().getList();
                    if (totalList.size() == 0) {
                        handler.sendEmptyMessage(10);
                    }
                    handler.sendEmptyMessage(0);
                }else {
                    handler.sendEmptyMessage(1);
                }
            } catch (IOException e) {
                handler.sendEmptyMessage(10);
            }
        }
    }

    @Override
    public void onFailue(Request request, IOException e) {

    }
    @Override
    public void onClick(int position) {
        goods_sel_recycle.smoothScrollToPosition(position);
    }

    /**
     * 设置选定数据并且保存记录
     * @param title
     * @param position
     * @param index
     * @param adapter
     * @param categoryList
     */
    public void setDataChange(String title,int category_id,String hid,int position,int index,int which,RecyclerView.Adapter adapter,CategoryList categoryList) {
        String state = categoryList.getSon().get(index).getSon().get(position).getSelect();
        if ("0".equals(state)) {
            LuPing(String.valueOf(category_id),"category","selected",new Date());
            categoryList.getSon().get(index).getSon().get(position).setSelect("1");
            record = new SelRecord();
            record.setTitle(title);
            record.setPosition(position);
            record.setIndex(index);
            record.setHid(hid);
            record.setSelWhich(which);
            record.setCategory_id(category_id);
            records.add(record);
            badge.setText(records.size()+"");
        } else if ("1".equals(state)) {
            LuPing(String.valueOf(category_id),"category","unSelected",new Date());
            categoryList.getSon().get(index).getSon().get(position).setSelect("0");
            for (int i = 0; i < records.size(); i++) {
                SelRecord rel = records.get(i);
                if (title.equals(rel.getTitle())) {
                    records.remove(rel);
                    badge.setText(records.size()+"");
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
    /**
     * 数据回调接口
     * @param title
     * @param hid
     * @param position
     * @param index
     */
    public void onCommodityRightItemClickListener(String title,int category_id, String hid, int position, int index,int which) {
        if (selWhich==0){
            setDataChange(title, category_id, hid, position, index, which, food_drink_commodityListAdapter, food_drink_list);
        }else if (selWhich==1){
            setDataChange(title, category_id, hid, position, index, which, mom_baby_commodityListAdapter, mom_baby_list);
        }else if (selWhich==2){
            setDataChange(title, category_id, hid, position, index, which, family_home_commodityListAdapter, family_home_list);
        }else if (selWhich==3){
            setDataChange(title, category_id, hid, position, index, which, cosmetic_skin_commodityListAdapter, cosmetic_skin_list);
        }
        adapter = new CommoditySelectedAdapter(records,CalculateSelectCategoryActivity.this);
        GridLayoutManager manager = new GridLayoutManager(CalculateSelectCategoryActivity.this, 4);
        goods_selected_recycle.setLayoutManager(manager);
        goods_selected_recycle.setAdapter(adapter);
    }
    @Override
    public void onItemLongClick(SelRecord record) {
        int which = record.getSelWhich();
        if (which == 0) {
            food_drink_types.get(record.getIndex()).getSon().get(record.getPosition()).setSelect("0");
            food_drink_commodityListAdapter.notifyDataSetChanged();
        } else if (which == 1) {
            mom_baby_types.get(record.getIndex()).getSon().get(record.getPosition()).setSelect("0");
            mom_baby_commodityListAdapter.notifyDataSetChanged();
        } else if (which == 2) {
            family_home_types.get(record.getIndex()).getSon().get(record.getPosition()).setSelect("0");
            family_home_commodityListAdapter.notifyDataSetChanged();
        } else if (which == 3) {
            cosmetic_skin_types.get(record.getIndex()).getSon().get(record.getPosition()).setSelect("0");
            cosmetic_skin_commodityListAdapter.notifyDataSetChanged();
        }
        for (int i = 0; i < records.size(); i++) {
            SelRecord rel = records.get(i);
            if (record.getTitle().equals(rel.getTitle())) {
                records.remove(rel);
            }
        }
        LuPing(String.valueOf(record.getCategory_id()),"category","unSelected",new Date());
        badge.setText(records.size() + "");
        adapter.notifyDataSetChanged();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
        MobclickAgent.onPause(this);
        StatService.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LuPingDestory("help_Accu_Shop","page","end",new Date());
    }
}
