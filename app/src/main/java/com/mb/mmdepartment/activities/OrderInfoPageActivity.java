package com.mb.mmdepartment.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.mb.mmdepartment.tools.CustomToast;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.adapter.buyplan.BuyListAdapter;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.buyplan.byprice.DataList;
import com.mb.mmdepartment.bean.marcketseldetail.Lists;
import com.mb.mmdepartment.bean.submitorders.Root;
import com.mb.mmdepartment.biz.submitorders.SubmitordersBiz;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.tools.log.Log;
import com.tencent.stat.StatService;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class OrderInfoPageActivity extends BaseActivity implements View.OnClickListener,RequestListener{
    private static final String TAG = OrderInfoPageActivity.class.getSimpleName();
    private TextView buy_list_save_tv;
    private TextView buy_list_cost_tv;
    private ExpandableListView listview;
    private TextView save;
    private List<DataList> lists;
    private double cost_price = 0,save_price = 0;
    private BuyListAdapter adapter;
    private RelativeLayout not_login;
    private TextView list_record_login;
    private SubmitordersBiz biz;
    private String list_sub;
    private LuPinModel luPinModel;
    @Override
    public int getLayout() {
        return R.layout.activity_buy_list;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initView();
        initData();
        initListener();
    }

    public void initListener(){
        save.setOnClickListener(this);
        list_record_login.setOnClickListener(this);
    }

    public void initView(){
        save = (TextView)findViewById(R.id.save_list);
        buy_list_save_tv = (TextView)findViewById(R.id.buy_list_save_tv);
        buy_list_cost_tv = (TextView)findViewById(R.id.buy_list_cost_tv);
        not_login = (RelativeLayout) findViewById(R.id.not_login);
        list_record_login = (TextView) findViewById(R.id.list_record_login);
        listview = (ExpandableListView)findViewById(R.id.buy_list_lv);
        listview.setGroupIndicator(null);
        listview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return true;
            }
        });
    }

    private void initData(){
        lists=TApplication.shop_list_to_pick;
        for(int i = 0;i<lists.size();i++){
            for (int j = 0;j<lists.get(i).getList().size();j++){
                cost_price+=Double.parseDouble(lists.get(i).getList().get(j).getF_price());
                save_price+=Double.parseDouble(lists.get(i).getList().get(j).getSave());
            }
        }
        buy_list_cost_tv.setText("¥"+Math.floor(cost_price*10d)/10);
        buy_list_save_tv.setText("¥" + Math.floor(save_price*10d)/10);

        adapter = new BuyListAdapter(OrderInfoPageActivity.this,lists);
        listview.setAdapter(adapter);
        for(int i = 0;i<lists.size();i++){
            listview.expandGroup(i);
        }
        biz = new SubmitordersBiz();
    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("购物清单");
        action.setHomeButtonEnabled(isTrue);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK) {
            if (!TextUtils.isEmpty(TApplication.user_id)) {
                not_login.setVisibility(View.INVISIBLE);
                listview.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_list:
                LuPing("orderSaveButton","other","saveOrder",new Date());
                if (TextUtils.isEmpty(TApplication.user_id)||null==TApplication.user_id) {
                    not_login.setVisibility(View.VISIBLE);
                    listview.setVisibility(View.GONE);
                }else {
                    JSONObject jsonObject;
                    try {
                        Lists item;
                        double allpay = 0;
                        jsonObject = new JSONObject();
                        JSONArray jsonArray = new JSONArray();
                        JSONObject shopobj;
                        JSONObject obj ;
                        JSONArray dataary ;
                        jsonObject.put("user_id",TApplication.user_id);
                        jsonObject.put("t_price", cost_price);
                        jsonObject.put("s_price",save_price);
                        jsonObject.put("device_no",JPushInterface.getRegistrationID(this));
                        for(int i = 0;i<lists.size();i++){
                            shopobj =  new JSONObject();
                            dataary = new JSONArray();
                            shopobj.put("shop_name",lists.get(i).getName());
                            shopobj.put("shop_id", lists.get(i).getList().get(0).getShop_id());
                            for(int j = 0;j<lists.get(i).getList().size();j++){
                                obj = new JSONObject();
                                item = lists.get(i).getList().get(j);
                                double cost = Integer.parseInt(item.getPid())*Double.parseDouble(item.getF_price());
                                allpay = allpay + cost;
                                obj.put("c_number",item.getId());
                                obj.put("t_price",cost);
                                obj.put("quantity",item.getPid());
                                dataary.put(obj);
                            }
                            shopobj.put("data",dataary);
                            shopobj.put("t_price",allpay);
                            jsonArray.put(shopobj);
                        }
                        jsonObject.put("shop",jsonArray);

                        list_sub = jsonObject.toString();
                        Log.i("json",list_sub);
                    }catch (Exception e){

                    }
                    TApplication.ids.clear();
                    TApplication.shop_lists.clear();
                    TApplication.shop_list_to_pick.clear();
                    biz.submitorders(list_sub,TAG,this);
                }
                break;
            case R.id.list_record_login:
                Intent intent = new Intent(OrderInfoPageActivity.this, LoginActivity.class);
                intent.putExtra("login", true);
                startActivityForResult(intent, 200);
                break;
        }
    }

    @Override
    public void onResponse(Response response) {
        if (response.isSuccessful()) {
            try {
                Gson gson = new Gson();
                String json = response.body().string();
                Root root = gson.fromJson(json,Root.class);
                if(root.getStatus() == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            CustomToast.show(OrderInfoPageActivity.this,"提示","保存成功");
                        }
                    });
                    startActivity(OrderInfoPageActivity.this, MainActivity.class);
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            CustomToast.show(OrderInfoPageActivity.this, "提示", "保存失败");
                        }
                    });
                }
            }catch (Exception e){

            }
        }
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
    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        JPushInterface.onPause(this);
        StatService.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LuPingDestory("order_Detail","page","end",new Date());
    }

    @Override
    public void onFailue(Request request, IOException e) {

    }
}
