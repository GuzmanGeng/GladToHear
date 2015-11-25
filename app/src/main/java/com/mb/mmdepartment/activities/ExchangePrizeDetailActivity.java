package com.mb.mmdepartment.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.mb.mmdepartment.R;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.mb.mmdepartment.bean.userspace.listrecord.getexchangeprizerecord.Exchange;
import com.mb.mmdepartment.tools.log.Log;
import com.tencent.stat.StatService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExchangePrizeDetailActivity extends BaseActivity {

    private TextView order_number;
    private TextView ctime;
    private TextView content_id;
    private TextView goods_name;
    private TextView address;
    private Exchange exchange;
    private LuPinModel luPinModel;
    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("兑换详情");
        action.setHomeButtonEnabled(isTrue);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TApplication.activities.remove(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_exchange_prize_detail;
    }


    @Override
    public void init(Bundle savedInstanceState) {
        exchange = (Exchange)getIntent().getSerializableExtra("exchange");
        String content_id = exchange.getContent_id();
        if (content_id  ==null||"".equals(content_id  )){
            showToast("请求出错");
        }
        initView();
        initData();
        setListener();
        if (isNetworkConnected(this)){

        }else {
            showToast("网络链接异常");
        }
    }

    private void initView() {
        order_number = (TextView)findViewById(R.id.prize_exchange_detail_order_number);
        ctime = (TextView)findViewById(R.id.prize_exchange_detail_order_time);
        content_id = (TextView)findViewById(R.id.prize_exchange_detail_content_id);
        goods_name = (TextView)findViewById(R.id.prize_exchange_detail_goods_name);
        address = (TextView)findViewById(R.id.prize_exchange_detail_address);
    }

    private void initData(){
        order_number.setText(exchange.getOrder_number());
        ctime.setText(exchange.getCtime());
        content_id.setText(exchange.getContent_id());
        goods_name.setText(exchange.getGoods_name());
        address.setText(exchange.getAddress());
    }

    private void setListener() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exchange_prize_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
