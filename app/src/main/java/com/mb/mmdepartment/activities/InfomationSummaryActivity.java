package com.mb.mmdepartment.activities;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mb.mmdepartment.R;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.mb.mmdepartment.fragment.main.informationcollection.CosmeticSkinFragment;
import com.mb.mmdepartment.fragment.main.informationcollection.FamilyHomeFragment;
import com.mb.mmdepartment.fragment.main.informationcollection.FoodDrinkFragment;
import com.mb.mmdepartment.fragment.main.informationcollection.MomBabyFragment;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.view.SwipeRefreshView;
import com.tencent.stat.StatService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InfomationSummaryActivity extends BaseActivity implements View.OnClickListener{
    private SwipeRefreshView swipeRefreshView;
    private TextView food_drink;
    private TextView mom_baby;
    private TextView day_user_family;
    private TextView cosmetic_skin;
    private int index=0;
    private Fragment foodDrink,momBaby,familyHome,cosmeticSkin;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private LuPinModel luPinModel;
    @Override
    public int getLayout() {
        return R.layout.activity_information_collection;
    }
    @Override
    public void init(Bundle savedInstanceState) {
        initView();
        setListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        luPinModel = new LuPinModel();
        luPinModel.setName("infomationSummary");
        luPinModel.setState("end");
        luPinModel.setType("page");
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-ss HH:mm:ss");
        luPinModel.setOperationtime(sdf.format(new Date()));
        StatService.onResume(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ss");
        luPinModel.setEndtime(sdf.format(new Date()));
        TApplication.luPinModels.add(luPinModel);
        StatService.onPause(this);
    }

    private void setListeners() {
        food_drink.setOnClickListener(this);
        mom_baby.setOnClickListener(this);
        day_user_family.setOnClickListener(this);
        cosmetic_skin.setOnClickListener(this);
    }

    private void initView() {
        foodDrink=new FoodDrinkFragment();
        manager=getSupportFragmentManager();
        setFragmentChose(foodDrink);

        food_drink=(TextView)findViewById(R.id.food_drink);
        mom_baby=(TextView)findViewById(R.id.mom_baby);
        day_user_family=(TextView)findViewById(R.id.day_user_family);
        cosmetic_skin=(TextView)findViewById(R.id.cosmetic_skin);
        swipeRefreshView=(SwipeRefreshView)findViewById(R.id.swipeRefreshView);
        //设置卷内的颜色
        swipeRefreshView.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        // 开始加载数据
        swipeRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                //swipeRefreshView.setRefreshing(true);
            }
        }, 100);

    }
    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("资讯汇总");
        action.setHomeButtonEnabled(isTrue);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * 设置Fragment
     * @param fragment
     */
    private void setFragmentChose(Fragment fragment) {
        transaction = manager.beginTransaction();
        transaction.replace(R.id.information_collection_content,fragment);
        transaction.commit();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.food_drink:
                if (index!=0) {
                    OkHttp.cancleInforMationNetWork(new String[]{"FoodDrinkFragment"});
                    food_drink.setBackgroundColor(getResources().getColor(R.color.text_half_red));
                    mom_baby.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    day_user_family.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    cosmetic_skin.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    index = 0;
                    foodDrink=new FoodDrinkFragment();
                    setFragmentChose(foodDrink);
                }
                break;
            case R.id.mom_baby:
                if (index!=1) {
                    OkHttp.cancleInforMationNetWork(new String[]{"MomBabyFragment"});
                    food_drink.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    mom_baby.setBackgroundColor(getResources().getColor(R.color.text_half_red));
                    day_user_family.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    cosmetic_skin.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    index = 1;
                    momBaby=new MomBabyFragment();
                    setFragmentChose(momBaby);
                }
                break;
            case R.id.day_user_family:
                if (index!=2) {
                    OkHttp.cancleInforMationNetWork(new String[]{"FamilyHomeFragment"});
                    food_drink.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    mom_baby.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    day_user_family.setBackgroundColor(getResources().getColor(R.color.text_half_red));
                    cosmetic_skin.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    index = 2;
                    familyHome=new FamilyHomeFragment();
                    setFragmentChose(familyHome);
                }
                break;
            case R.id.cosmetic_skin:
                if (index!=3) {
                    OkHttp.cancleInforMationNetWork(new String[]{"CosmeticSkinFragment"});
                    food_drink.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    mom_baby.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    day_user_family.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    cosmetic_skin.setBackgroundColor(getResources().getColor(R.color.text_half_red));
                    index = 3;
                    cosmeticSkin=new CosmeticSkinFragment();
                    setFragmentChose(cosmeticSkin);
                }
                break;
        }
    }
}
