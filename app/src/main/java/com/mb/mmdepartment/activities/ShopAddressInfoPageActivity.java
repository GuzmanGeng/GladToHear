package com.mb.mmdepartment.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.mb.mmdepartment.R;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.helpcheck.detail.Detail;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.mb.mmdepartment.tools.log.Log;
import com.tencent.stat.StatService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShopAddressInfoPageActivity extends BaseActivity {

    private TextView detail_shop_name;
    private TextView detail_shop_address;
    private TextView detail_shop_translate;
    private TextView shop_detail_business_time;
    private TextView detail_shop_bus;
    private Detail detail;
    private LuPinModel luPinModel;
    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("门店详情");
        action.setHomeButtonEnabled(isTrue);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initview();
        initdata();
    }

    private void initview(){
        detail_shop_name = (TextView)findViewById(R.id.detail_shop_name);
        detail_shop_address = (TextView)findViewById(R.id.detail_shop_address);
        detail_shop_translate = (TextView)findViewById(R.id.detail_shop_translate);
        shop_detail_business_time = (TextView)findViewById(R.id.shop_detail_business_time);
        detail_shop_bus = (TextView)findViewById(R.id.detail_shop_bus);
    }

    private void initdata(){
        Intent intent = getIntent();
        detail = (Detail)intent.getSerializableExtra("detail");
        detail_shop_name.setText(detail.getShop_name2());
        Log.i("shop_name", detail.getShop_name2());
        detail_shop_address.setText("超市地址："+detail.getAddres());
        detail_shop_translate.setText("附近公交："+detail.getRoute());
        if(detail.getSpecial().equals("")) {
            detail_shop_bus.setText("超市班车：" + "暂无");
        }else {
            detail_shop_bus.setText("超市班车：" + detail.getSpecial());
        }
        shop_detail_business_time.setText("营业时间："+detail.getBusiness_hours());
    }

    @Override
    protected void onResume() {
        super.onResume();
        luPinModel = new LuPinModel();
        luPinModel.setName("shopStoreInfo");
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
    public int getLayout() {
        return R.layout.activity_shop_address_info_page;
    }
}
