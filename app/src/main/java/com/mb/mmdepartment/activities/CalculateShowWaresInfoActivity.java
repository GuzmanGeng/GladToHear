package com.mb.mmdepartment.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mb.mmdepartment.R;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.mb.mmdepartment.fragment.main.buyplan.SortbyDiscountFragment;
import com.mb.mmdepartment.fragment.main.buyplan.SortbyPercentFragment;
import com.mb.mmdepartment.fragment.main.buyplan.SortbyPriceFragment;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.tools.log.Log;
import com.tencent.stat.StatService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalculateShowWaresInfoActivity extends BaseActivity implements View.OnClickListener{

    public static final String TAG = CalculateShowWaresInfoActivity.class.getSimpleName();
    private SortbyPriceFragment sortbyPriceFragment = new SortbyPriceFragment();
    private SortbyDiscountFragment sortbyDiscountFragment = new SortbyDiscountFragment();
    private SortbyPercentFragment sortbyPercentFragment = new SortbyPercentFragment();
    private TextView buy_plan_price,buy_plan_percent,buy_plan_discount;
    private int whickSel = 0,order_type_price=0,order_type_percent = 0,order_type_discount = 0;
    private  FragmentTransaction transaction ;
    public final static int NOTI = 0000;
    private  FragmentManager manager;
    private static TextView circle;
    private  View v;
    private  Bundle bundle ;
    private LuPinModel luPinModel;
    //    public static CircleBadgeView badgeView;
    private LinearLayout shopping_cart_ll;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CalculateShowWaresInfoActivity.NOTI:
                    if(sortbyPriceFragment.isVisible()){
                        sortbyPriceFragment.noti();
                    }
                    if(sortbyDiscountFragment.isVisible()){
                        sortbyDiscountFragment.noti();
                    }
                    if(sortbyPercentFragment.isVisible()){
                        sortbyPercentFragment.noti();
                    }
                    break;
            }
        }
    };
    @Override
    public int getLayout() {
        return R.layout.activity_buy_plan;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initView();
        setListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TApplication.activities.remove(this);
    }

    public static void setbadge(){
        int size = 0;
        for(int i = 0;i<TApplication.shop_list_to_pick.size();i++){
            size = size +TApplication.shop_list_to_pick.get(i).getList().size();
        }
        circle.setText(size+"");
//        badgeView.setText(size + "");
    }

    public void  setListener(){
        buy_plan_percent.setOnClickListener(this);
        buy_plan_price.setOnClickListener(this);
        buy_plan_discount.setOnClickListener(this);
        shopping_cart_ll.setOnClickListener(this);
    }

    public void initView(){
        manager = getFragmentManager();
        Intent intent = getIntent();
        String category = intent.getStringExtra("records");
        String shop_id = intent.getStringExtra("shop_id");
        v = findViewById(R.id.help_you_calculate_goods_detail_shopping_cart);
        circle = (TextView)findViewById(R.id.circle);
        int size = 0;
        for(int i = 0;i<TApplication.shop_list_to_pick.size();i++){
            size = size +TApplication.shop_list_to_pick.get(i).getList().size();
        }
        circle.setText(size+"");
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                badgeView = new CircleBadgeView(.this,v);
//                int size = 0;
//                for(int i = 0;i<TApplication.shop_list_to_pick.size();i++){
//                    size = size +TApplication.shop_list_to_pick.get(i).getList().size();
//                }
//                badgeView.setText(size + "");
//                badgeView.setBackgroundColor(Color.RED);//设置背景颜色
//                badgeView.setGravity(Gravity.CENTER);
//                badgeView.setBadgePosition(CircleBadgeView.POSITION_TOP_LEFT);
//                badgeView.show();
//            }
//        }, 500);
        shopping_cart_ll = (LinearLayout)findViewById(R.id.help_you_calculate_goods_detail_ll);
        bundle = new Bundle();
        if(intent.getStringExtra("tag").equals("by_market")){
            bundle.putInt("group",2);
        }else{
            bundle.putInt("group",1);
        }
        bundle.putString("records",category);
        bundle.putString("shop_id", shop_id);
        bundle.putString("order_type", "asc");
        bundle.putInt("order", 1);
        sortbyPriceFragment.setArguments(bundle);
//        sortbyPriceFragment.setBundle(bundle);
        buy_plan_discount = (TextView)findViewById(R.id.buy_plan_discount);
        buy_plan_percent = (TextView)findViewById(R.id.buy_plan_percent);
        buy_plan_price = (TextView)findViewById(R.id.buy_plan_price);
        buy_plan_price.setTextColor(getResources().getColor(R.color.color_white));
        buy_plan_price.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
        setFragmentChose(sortbyPriceFragment);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        OkHttp.mOkHttpClient.cancel(TAG);
    }

    /**
     * 设置Fragment
     * @param fragment
     */
    private void setFragmentChose(Fragment fragment) {
        transaction = manager.beginTransaction();
        transaction.replace(R.id.buy_plan_content, fragment);
        transaction.commit();
    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("建议购买方案");
        action.setHomeButtonEnabled(isTrue);

    }

    @Override
    public void onClick(View view) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        switch (view.getId()) {
            case R.id.buy_plan_price:
                Log.i("tag","setpricefragment");
//                if (whickSel != 0) {
//                    setFragmentChose(sortbyPriceFragment);
                buy_plan_price.setTextColor(getResources().getColor(R.color.color_white));
                buy_plan_price.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));

                buy_plan_discount.setTextColor(getResources().getColor(R.color.text_half_red));
                buy_plan_discount.setBackgroundColor(getResources().getColor(R.color.color_white));

                buy_plan_percent.setTextColor(getResources().getColor(R.color.text_half_red));
                buy_plan_percent.setBackgroundColor(getResources().getColor(R.color.color_white));
//                    whickSel = 0;
                LuPinModel luPinModel_price = new LuPinModel();
                luPinModel_price.setName(buy_plan_price.getText().toString());
                luPinModel_price.setType("sort");
                luPinModel_price.setOperationtime(sdf.format(new Date()));
                if(order_type_price == 0) {
                    luPinModel_price.setState("desc");
                    buy_plan_price.setText("按价格排序↓");
                    order_type_price = 1;
                    bundle.putString("order_type","desc");
                }else{
                    luPinModel_price.setState("asc");
                    buy_plan_price.setText("按价格排序↑");
                    order_type_price = 0;
                    bundle.putString("order_type", "asc");
                }
                TApplication.luPinModels.add(luPinModel_price);
                bundle.putInt("order", 1);
                if (whickSel != 0) {
                    sortbyPriceFragment.setArguments(bundle);
                    setFragmentChose(sortbyPriceFragment);
                    whickSel = 0;
                } else{
                    sortbyPriceFragment.setBundle(bundle);
                }
                break;
            case R.id.buy_plan_discount:
                LuPinModel luPinModel_discount = new LuPinModel();
                luPinModel_discount.setName(buy_plan_price.getText().toString());
                luPinModel_discount.setType("sort");
                luPinModel_discount.setOperationtime(sdf.format(new Date()));
                buy_plan_discount.setTextColor(getResources().getColor(R.color.color_white));
                buy_plan_discount.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));

                buy_plan_percent.setTextColor(getResources().getColor(R.color.text_half_red));
                buy_plan_percent.setBackgroundColor(getResources().getColor(R.color.color_white));

                buy_plan_price.setTextColor(getResources().getColor(R.color.text_half_red));
                buy_plan_price.setBackgroundColor(getResources().getColor(R.color.color_white));
//                    whickSel = 1;
                if(order_type_discount == 0) {
                    luPinModel_discount.setState("desc");
                    buy_plan_discount.setText("按折扣排序↓");
                    order_type_discount = 1;
                    bundle.putString("order_type","desc");
                }else{
                    luPinModel_discount.setState("asc");
                    buy_plan_discount.setText("按折扣排序↑");
                    order_type_discount = 0;
                    bundle.putString("order_type","asc");
                }
                TApplication.luPinModels.add(luPinModel_discount);
                bundle.putInt("order", 2);
                if (whickSel != 1) {
                    sortbyDiscountFragment.setArguments(bundle);
                    setFragmentChose(sortbyDiscountFragment);
                    whickSel = 1;
                }else{
                    sortbyDiscountFragment.setBundle(bundle);
                }
                break;
            case R.id.buy_plan_percent:
                LuPinModel luPinModel_percent = new LuPinModel();
                luPinModel_percent.setName(buy_plan_price.getText().toString());
                luPinModel_percent.setType("sort");
                luPinModel_percent.setOperationtime(sdf.format(new Date()));
                buy_plan_percent.setTextColor(getResources().getColor(R.color.color_white));
                buy_plan_percent.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));

                buy_plan_price.setTextColor(getResources().getColor(R.color.text_half_red));
                buy_plan_price.setBackgroundColor(getResources().getColor(R.color.color_white));

                buy_plan_discount.setTextColor(getResources().getColor(R.color.text_half_red));
                buy_plan_discount.setBackgroundColor(getResources().getColor(R.color.color_white));
//                    whickSel = 2;
                if(order_type_percent == 0) {
                    luPinModel_percent.setState("desc");
                    buy_plan_percent.setText("按节省比排序↓");
                    order_type_percent = 1;
                    bundle.putString("order_type","desc");
                }else{
                    luPinModel_percent.setState("asc");
                    buy_plan_percent.setText("按节省比排序↑");
                    order_type_percent = 0;
                    bundle.putString("order_type","asc");
                }
                TApplication.luPinModels.add(luPinModel_percent);
                bundle.putInt("order", 3);
                if (whickSel != 2) {
                    sortbyPercentFragment.setArguments(bundle);
                    setFragmentChose(sortbyPercentFragment);
                    whickSel = 2;
                }else{
                    sortbyPercentFragment.setBundle(bundle);
                }
                break;
            case R.id.help_you_calculate_goods_detail_ll:{
                LuPinModel shopping_cart = new LuPinModel();
                Intent intent = new Intent(CalculateShowWaresInfoActivity.this, ShoppingCartPageActivity.class);
                startActivityForResult(intent, 0);
            }
            break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        luPinModel = new LuPinModel();
        luPinModel.setName("helpYouCountCommodityList");
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(sortbyPriceFragment.isVisible()){
            setFragmentChose(sortbyPriceFragment);
        }
        if(sortbyPercentFragment.isVisible()){
            setFragmentChose(sortbyPercentFragment);
        }
        if(sortbyDiscountFragment.isVisible()){
            setFragmentChose(sortbyDiscountFragment);
        }
        setbadge();
    }
}
