package com.mb.mmdepartment.activities;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.mb.mmdepartment.fragment.main.userspace.ExchangePrizeRecordFragment;
import com.mb.mmdepartment.fragment.main.userspace.ListRecordFragment;
import com.mb.mmdepartment.fragment.main.userspace.PrizeExchangeFragment;
import com.tencent.stat.StatService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserSpaceActivity extends BaseActivity implements View.OnClickListener{
    private TextView user_space_list_record_tv,user_space_exchange_prizes_record_tv,user_space_prizes_exchange_tv;
    private Fragment listRecord,exchangePrize,prizeExchange;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private int whickSel;
    private LuPinModel luPinModel;
    @Override
    public int getLayout() {
        return R.layout.activity_user_space;
    }
    @Override
    public void init(Bundle savedInstanceState) {
        initView();
        setListeners();
    }
    private void setListeners() {
        user_space_list_record_tv.setOnClickListener(this);
        user_space_exchange_prizes_record_tv.setOnClickListener(this);
        user_space_prizes_exchange_tv.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        luPinModel = new LuPinModel();
        luPinModel.setName("PersonalCenterMain");
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
    private void initView() {
        user_space_list_record_tv=(TextView)findViewById(R.id.user_space_list_record_tv);
        user_space_exchange_prizes_record_tv=(TextView)findViewById(R.id.user_space_exchange_prizes_record_tv);
        user_space_prizes_exchange_tv=(TextView)findViewById(R.id.user_space_prizes_exchange_tv);
        listRecord=new ListRecordFragment();
        manager=getSupportFragmentManager();
        setFragmentChose(listRecord);
        user_space_list_record_tv.setTextColor(getResources().getColor(R.color.color_white));
        user_space_list_record_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
    }
    /**
     * 设置Fragment
     * @param fragment
     */
    private void setFragmentChose(Fragment fragment) {
        transaction = manager.beginTransaction();
        transaction.replace(R.id.user_space_content,fragment);
        transaction.commit();
    }
    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("个人空间");
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

    @Override
    public void onClick(View view) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        switch (view.getId()){
            case R.id.user_space_list_record_tv:
                if (whickSel!=0){
                    listRecord=new ListRecordFragment();
                    LuPinModel luPinModel_list_record = new LuPinModel();
                    luPinModel_list_record.setState("selected");
                    luPinModel_list_record.setName("list_record");
                    luPinModel_list_record.setOperationtime(sdf.format(new Date()));
                    luPinModel_list_record.setType("next");
                    TApplication.luPinModels.add(luPinModel_list_record);
                    setFragmentChose(listRecord);
                    user_space_list_record_tv.setTextColor(getResources().getColor(R.color.color_white));
                    user_space_list_record_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));

                    user_space_exchange_prizes_record_tv.setTextColor(getResources().getColor(R.color.text_half_red));
                    user_space_exchange_prizes_record_tv.setBackgroundColor(getResources().getColor(R.color.color_white));

                    user_space_prizes_exchange_tv.setTextColor(getResources().getColor(R.color.text_half_red));
                    user_space_prizes_exchange_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    whickSel=0;
                }
                break;
            case R.id.user_space_exchange_prizes_record_tv:
                if (whickSel!=1){
                    LuPinModel luPinModel_exchange_prizes_record = new LuPinModel();
                    luPinModel_exchange_prizes_record.setState("selected");
                    luPinModel_exchange_prizes_record.setName("exchange_prizes_record");
                    luPinModel_exchange_prizes_record.setOperationtime(sdf.format(new Date()));
                    luPinModel_exchange_prizes_record.setType("next");
                    TApplication.luPinModels.add(luPinModel_exchange_prizes_record);
                    exchangePrize=new ExchangePrizeRecordFragment();
                    setFragmentChose(exchangePrize);
                    user_space_exchange_prizes_record_tv.setTextColor(getResources().getColor(R.color.color_white));
                    user_space_exchange_prizes_record_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));

                    user_space_list_record_tv.setTextColor(getResources().getColor(R.color.text_half_red));
                    user_space_list_record_tv.setBackgroundColor(getResources().getColor(R.color.color_white));

                    user_space_prizes_exchange_tv.setTextColor(getResources().getColor(R.color.text_half_red));
                    user_space_prizes_exchange_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    whickSel=1;
                }
                break;
            case R.id.user_space_prizes_exchange_tv:
                if (whickSel!=2){
                    LuPinModel luPinModel_exchange_prizes_record = new LuPinModel();
                    luPinModel_exchange_prizes_record.setState("selected");
                    luPinModel_exchange_prizes_record.setName("prizes_exchange");
                    luPinModel_exchange_prizes_record.setOperationtime(sdf.format(new Date()));
                    luPinModel_exchange_prizes_record.setType("next");
                    TApplication.luPinModels.add(luPinModel_exchange_prizes_record);
                    prizeExchange=new PrizeExchangeFragment();
                    setFragmentChose(prizeExchange);

                    user_space_prizes_exchange_tv.setTextColor(getResources().getColor(R.color.color_white));
                    user_space_prizes_exchange_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));

                    user_space_exchange_prizes_record_tv.setTextColor(getResources().getColor(R.color.text_half_red));
                    user_space_exchange_prizes_record_tv.setBackgroundColor(getResources().getColor(R.color.color_white));

                    user_space_list_record_tv.setTextColor(getResources().getColor(R.color.text_half_red));
                    user_space_list_record_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    whickSel=2;
                }
                break;
        }
    }
}
