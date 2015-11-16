package com.mb.mmdepartment.activities;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.adapter.ExpandableAdapter;
import com.mb.mmdepartment.adapter.userspace.ListRecordDetailAdapter;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.userspace.listrecord.getlistrecorddetail.Root;
import com.mb.mmdepartment.bean.userspace.listrecord.getlistrecorddetail.Shop;
import com.mb.mmdepartment.biz.userspace.listrecord.ListRecordDetailBiz;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.constans.LoginConsts;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.tools.log.Log;
import com.tencent.stat.StatService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

public class OrderDetailActivity extends BaseActivity implements RequestListener{
    private TextView list_record_num_tv;
    private TextView list_record_time_tv;
    private TextView list_record_save_tv;
    private TextView list_record_cost_tv;
    private ExpandableListView expandableListView;
    private ListRecordDetailAdapter adapter;
    private ListRecordDetailBiz biz;
    private List<Shop> shops;
    private String t_price,s_price,onumber,o_date;
    private LuPinModel luPinModel;

    @Override
    public int getLayout() {
        return R.layout.activity_list_record_detail;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initview();
        initData();
    }
    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("订单详细");
        action.setHomeButtonEnabled(isTrue);
    }

    private void initview() {
        expandableListView = (ExpandableListView)findViewById(R.id.list_record_detail_expandlv);
        list_record_cost_tv = (TextView)findViewById(R.id.list_record_coast_tv);
        list_record_save_tv = (TextView)findViewById(R.id.list_record_save_tv);
        list_record_time_tv = (TextView)findViewById(R.id.list_record_time_tv);
        list_record_num_tv = (TextView)findViewById(R.id.list_record_num_tv);
    }

    @Override
    protected void onResume() {
        super.onResume();
        luPinModel = new LuPinModel();
        luPinModel.setName("ListRecordDetai");
        luPinModel.setType("page");
        luPinModel.setState("end");
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
    public void onResponse(Response response) {
        Gson gson = new Gson();
        if(response.isSuccessful()) {
            try {
                String json = response.body().string();
                Log.i("json", json);
                Root root = gson.fromJson(json, Root.class);
                Log.i("tag", root.getStatus() + "");
                if (root.getStatus() == OkHttp.NET_STATE) {
                    shops = root.getData().getShop();
                    t_price = root.getData().getT_price();
                    s_price = root.getData().getS_price();
                    onumber = root.getData().getOnumber();
                    o_date = root.getData().getO_date();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("订单详细成功");
                            getData();
                        }
                    });
                } else {
                    Log.e("error", root.getError());
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void getData(){
        list_record_num_tv.setText("编号："+onumber);
        list_record_time_tv.setText("生成时间："+o_date);
        list_record_cost_tv.setText(t_price);;
        list_record_save_tv.setText(s_price );
        adapter = new ListRecordDetailAdapter(this, shops, new ExpandableAdapter.CallBack() {
            @Override
            public void getView(View view, int groupPosition, int childPosition) {

            }
        });
        expandableListView.setGroupIndicator(null);
        expandableListView.setAdapter(adapter);
    }

    private void initData() {
        Intent intent = getIntent();
        String onumber = intent.getStringExtra("onumber");
        biz = new ListRecordDetailBiz();
        Log.i("oNumber", onumber);
        biz.getListRecordDetail(onumber, JPushInterface.getRegistrationID(this),this);
        Log.i("222", ""+111);
    }

    @Override
    public void onFailue(Request request, IOException e) {
        showToast("请求订单详细失败");
    }
}
