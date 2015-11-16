package com.mb.mmdepartment.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
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
        initview();
        initdata();
        setlistener();
    }

    public void setlistener(){
        save.setOnClickListener(this);
        list_record_login.setOnClickListener(this);
    }

    public void initview(){
        save = (TextView)findViewById(R.id.save_list);
        buy_list_save_tv = (TextView)findViewById(R.id.buy_list_save_tv);
        buy_list_cost_tv = (TextView)findViewById(R.id.buy_list_cost_tv);
        not_login = (RelativeLayout) findViewById(R.id.not_login);
        list_record_login = (TextView) findViewById(R.id.list_record_login);
        listview = (ExpandableListView)findViewById(R.id.buy_list_lv);
        listview.setGroupIndicator(null);
    }

    private void initdata(){
        Intent intent = getIntent();
        lists = (List<DataList>)intent.getSerializableExtra("lists");
        for(int i = 0;i<lists.size();i++){
            for (int j = 0;j<lists.get(i).getList().size();j++){
                cost_price = cost_price+(Integer.parseInt(lists.get(i).getList().get(j).getPid())*Double.parseDouble(lists.get(i).getList().get(j).getF_price()));
                save_price = save_price+(Integer.parseInt(lists.get(i).getList().get(j).getPid())*Double.parseDouble(lists.get(i).getList().get(j).getSave()));
            }
        }
        buy_list_cost_tv.setText("¥"+cost_price);
        buy_list_save_tv.setText("¥" + save_price);
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
                Log.i("save", "保存");
                LuPinModel save = new LuPinModel();
                save.setName("orderSaveButton");
                save.setType("other");
                save.setState("saveOrder");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                save.setOperationtime(sdf.format(new Date()));
                TApplication.luPinModels.add(save);
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
                    TApplication.shop_list_to_pick.clear();
                    TApplication.shop_lists.clear();
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
                Log.i("save","保存2");
                Gson gson = new Gson();
                String json = response.body().string();
                Root root = gson.fromJson(json,Root.class);
                Log.i("save","保存:"+root.getStatus());
                if(root.getStatus() == 0) {
                    Log.i("save","保存3");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast(" 保存成功");
                        }
                    });
                    startActivity(OrderInfoPageActivity.this, MainPageActivity.class);
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast(" 保存失败");
                        }
                    });
                }
            }catch (Exception e){

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        luPinModel = new LuPinModel();
        luPinModel.setName("productionOrder");
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

    @Override
    public void onFailue(Request request, IOException e) {
        showToast("保存失败");
    }
}
