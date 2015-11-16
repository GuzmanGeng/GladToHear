package com.mb.mmdepartment.activities;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.mb.mmdepartment.fragment.main.helpcheck.BrandSelFragment;
import com.mb.mmdepartment.fragment.main.helpcheck.CatlogSelFragment;
import com.mb.mmdepartment.fragment.main.helpcheck.MacketSelFragment;
import com.mb.mmdepartment.listener.OnRecycItemClickListener;
import com.tencent.stat.StatService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HelpYouQuerySearchActivity extends BaseActivity implements OnRecycItemClickListener,View.OnClickListener{
    private MacketSelFragment macketSelFragment;
    private BrandSelFragment brandSelFragment;
    private CatlogSelFragment catlogSelFragment;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private TextView help_check_macket_sel_tv,help_check_brand_sel_tv,help_check_catlog_sel_tv;
    private int tagSel;
    private LuPinModel luPinModel;
    private EditText help_check_search_ed;

    @Override
    public int getLayout() {
        return R.layout.activity_help_check;
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
        luPinModel.setName("helpYouQuerySearch");
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

    private void setListeners() {
        help_check_macket_sel_tv.setOnClickListener(this);
        help_check_brand_sel_tv.setOnClickListener(this);
        help_check_catlog_sel_tv.setOnClickListener(this);
    }

    private void initView() {
        macketSelFragment=new MacketSelFragment();
        manager=getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.help_check_content, macketSelFragment);
        transaction.commit();
        help_check_search_ed = (EditText) findViewById(R.id.help_check_search_ed);
        help_check_macket_sel_tv=(TextView)findViewById(R.id.help_check_macket_sel_tv);
        help_check_brand_sel_tv=(TextView)findViewById(R.id.help_check_brand_sel_tv);
        help_check_catlog_sel_tv=(TextView)findViewById(R.id.help_check_catlog_sel_tv);
        help_check_macket_sel_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
        help_check_macket_sel_tv.setTextColor(getResources().getColor(R.color.color_white));
    }
    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("帮你查");
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
    public void onItemClick(View view, int position) {
        startActivity(HelpYouQuerySearchActivity.this, HelpYouQueryMarketActivity.class, "position", String.valueOf(position));
    }

    /**
     * 设置Fragment
     * @param fragment
     */
    private void setFragmentChose(Fragment fragment) {
        transaction = manager.beginTransaction();
        transaction.replace(R.id.help_check_content,fragment);
        transaction.commit();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.help_check_macket_sel_tv:
                if (tagSel!=0){
                    help_check_search_ed.setHint("请输入超市名");
                    macketSelFragment=new MacketSelFragment();
                    setFragmentChose(macketSelFragment);
                    help_check_macket_sel_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    help_check_macket_sel_tv.setTextColor(getResources().getColor(R.color.color_white));

                    help_check_brand_sel_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    help_check_brand_sel_tv.setTextColor(getResources().getColor(R.color.theme_color));

                    help_check_catlog_sel_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    help_check_catlog_sel_tv.setTextColor(getResources().getColor(R.color.theme_color));
                    tagSel=0;
                }
                break;
            case R.id.help_check_brand_sel_tv:
                if (tagSel!=1){
                    help_check_search_ed.setHint("请输入品牌");
                    brandSelFragment=new BrandSelFragment();
                    setFragmentChose(brandSelFragment);

                    help_check_brand_sel_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    help_check_brand_sel_tv.setTextColor(getResources().getColor(R.color.color_white));

                    help_check_macket_sel_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    help_check_macket_sel_tv.setTextColor(getResources().getColor(R.color.theme_color));

                    help_check_catlog_sel_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    help_check_catlog_sel_tv.setTextColor(getResources().getColor(R.color.theme_color));
                    tagSel=1;
                }
                break;
            case  R.id.help_check_catlog_sel_tv:
                if (tagSel!=2){
                    help_check_search_ed.setHint("请输入分类");
                    catlogSelFragment=new CatlogSelFragment();
                    setFragmentChose(catlogSelFragment);

                    help_check_catlog_sel_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    help_check_catlog_sel_tv.setTextColor(getResources().getColor(R.color.color_white));

                    help_check_macket_sel_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    help_check_macket_sel_tv.setTextColor(getResources().getColor(R.color.theme_color));

                    help_check_brand_sel_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    help_check_brand_sel_tv.setTextColor(getResources().getColor(R.color.theme_color));
                    tagSel=2;
                }
                break;
        }
    }
}
