package com.mb.mmdepartment.activities;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.adapter.help_check_puzzy.HelpCheckPuzzyAdapter;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.helpcheck.puzzy.BrandPuzzyData;
import com.mb.mmdepartment.bean.helpcheck.puzzy.BrandPuzzyList;
import com.mb.mmdepartment.bean.helpcheck.puzzy.BrandPuzzyRoot;
import com.mb.mmdepartment.bean.helpcheck.puzzy.CatlogPuzzyData;
import com.mb.mmdepartment.bean.helpcheck.puzzy.CatlogPuzzyList;
import com.mb.mmdepartment.bean.helpcheck.puzzy.CatlogPuzzyRoot;
import com.mb.mmdepartment.bean.helpcheck.puzzy.MarketPuzzyData;
import com.mb.mmdepartment.bean.helpcheck.puzzy.MarketPuzzyList;
import com.mb.mmdepartment.bean.helpcheck.puzzy.MarketPuzzyRoot;
import com.mb.mmdepartment.bean.helpcheck.puzzy.PuzzyModel;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.constans.CatlogConsts;
import com.mb.mmdepartment.fragment.main.helpcheck.BrandSelFragment;
import com.mb.mmdepartment.fragment.main.helpcheck.CatlogSelFragment;
import com.mb.mmdepartment.fragment.main.helpcheck.MacketSelFragment;
import com.mb.mmdepartment.listener.OnRecycItemClickListener;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.tools.sp.SPCache;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tencent.stat.StatService;
import com.umeng.analytics.MobclickAgent;

import org.w3c.dom.Text;

import java.io.IOException;
import java.nio.DoubleBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

public class HelpYouQuerySearchActivity extends BaseActivity implements OnRecycItemClickListener,View.OnClickListener,TextWatcher{
    private MacketSelFragment macketSelFragment;
    private BrandSelFragment brandSelFragment;
    private CatlogSelFragment catlogSelFragment;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private TextView help_check_macket_sel_tv,help_check_brand_sel_tv,help_check_catlog_sel_tv;
    private int tagSel=3;
    private LuPinModel luPinModel;
    private EditText help_check_search_ed;
    private Map<String,String> paramas;
    private ListView lv_puzzy;
    private List<PuzzyModel> list;
    private HelpCheckPuzzyAdapter adapter;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adapter.setWhich(tagSel);
                    break;
            }
        }
    };
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
    protected void onDestroy() {
        super.onDestroy();
        LuPingDestory("help_Search", "page", "end", new Date());
        TApplication.activities.remove(this);
    }

    private void setListeners() {
        help_check_macket_sel_tv.setOnClickListener(this);
        help_check_brand_sel_tv.setOnClickListener(this);
        help_check_catlog_sel_tv.setOnClickListener(this);
        help_check_search_ed.addTextChangedListener(this);
        lv_puzzy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String keyword = list.get(i).getKeyword();
                if (tagSel == 3) {
                    LuPing(keyword,"shop","search",new Date());
                    String shopName = list.get(i).getShop_name();
                    startActivity(HelpYouQuerySearchActivity.this, MarcketSelDetailActivity.class, new String[]{"keyword", "shop_name"}, new String[]{keyword, shopName});
                }else if (tagSel == 2) {
                    String title = list.get(i).getSearch_name();
                    LuPing(title,"category","search",new Date());
                    Intent intent = new Intent(HelpYouQuerySearchActivity.this, ShowWaresInfoActivity.class);
                    intent.putExtra("keyword", keyword);
                    intent.putExtra("catlog", true);
                    intent.putExtra("searchName", title);
                    startActivity(intent);
                } else {
                    LuPing(keyword,"brand","search",new Date());
                    Intent intent = new Intent(HelpYouQuerySearchActivity.this, ShowWaresInfoActivity.class);
                    intent.putExtra("keyword",keyword);
                    intent.putExtra("catlog", false);
                    startActivity(intent);
                }
            }
        });
    }

    private void initView() {
        macketSelFragment=new MacketSelFragment();
        manager=getSupportFragmentManager();
        transaction = manager.beginTransaction();
        lv_puzzy = (ListView)findViewById(R.id.lv_puzzy);
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
                if (tagSel!=3){
                    if (list != null) {
                        list.clear();
                        adapter.notifyDataSetChanged();
                        lv_puzzy.setVisibility(View.GONE);
                    }
                    help_check_search_ed.setText("");
                    help_check_search_ed.setHint("请输入超市名");
                    macketSelFragment=new MacketSelFragment();
                    setFragmentChose(macketSelFragment);
                    help_check_macket_sel_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    help_check_macket_sel_tv.setTextColor(getResources().getColor(R.color.color_white));

                    help_check_brand_sel_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    help_check_brand_sel_tv.setTextColor(getResources().getColor(R.color.theme_color));

                    help_check_catlog_sel_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    help_check_catlog_sel_tv.setTextColor(getResources().getColor(R.color.theme_color));
                    tagSel=3;
                }
                break;
            case R.id.help_check_brand_sel_tv:
                if (tagSel!=1){
                    if (list != null) {
                        list.clear();
                        adapter.notifyDataSetChanged();
                        lv_puzzy.setVisibility(View.GONE);
                    }
                    help_check_search_ed.setText("");
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
                if (tagSel!=2) {
                    if (list != null) {
                        list.clear();
                        adapter.notifyDataSetChanged();
                        lv_puzzy.setVisibility(View.GONE);
                    }
                    help_check_search_ed.setText("");
                    help_check_search_ed.setHint("请输入分类");
                    catlogSelFragment = new CatlogSelFragment();
                    setFragmentChose(catlogSelFragment);

                    help_check_catlog_sel_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    help_check_catlog_sel_tv.setTextColor(getResources().getColor(R.color.color_white));

                    help_check_macket_sel_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    help_check_macket_sel_tv.setTextColor(getResources().getColor(R.color.theme_color));

                    help_check_brand_sel_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    help_check_brand_sel_tv.setTextColor(getResources().getColor(R.color.theme_color));
                    tagSel = 2;
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (list == null) {
            list = new ArrayList<>();
            adapter = new HelpCheckPuzzyAdapter(list, tagSel);
            lv_puzzy.setAdapter(adapter);
        }
        initData();
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (!TextUtils.isEmpty(charSequence)) {
            lv_puzzy.setVisibility(View.VISIBLE);
            if (tagSel == 1) {
                LuPing(charSequence.toString(),"searchBrand","input",new Date());
            }else if (tagSel == 2) {
                LuPing(charSequence.toString(),"searchCategory","input",new Date());
            } else {
                LuPing(charSequence.toString(),"searchShop","input",new Date());
            }
            String keyword = charSequence.toString();
            getPuzzyData(keyword,String.valueOf(tagSel));
        } else {
            lv_puzzy.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
    private void initData() {
        if (paramas == null) {
            paramas = new HashMap<>();
            paramas.put(BaseConsts.APP, CatlogConsts.MarketPuzzy.params_app);
            paramas.put(BaseConsts.CLASS, CatlogConsts.MarketPuzzy.params_class);
            paramas.put(BaseConsts.SIGN, CatlogConsts.MarketPuzzy.params_sign);
            paramas.put("local", SPCache.getString("city_id", "50"));
        }
    }
    /**
     * 获取最新数据
     * @param keyword
     * @param search_type
     */
    private void getPuzzyData(String keyword, final String search_type) {
        paramas.put("keyword",keyword);
        paramas.put("search_type", search_type);
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }
            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    list.clear();
                    Gson gson = new Gson();
                    String json = response.body().string();
                    Log.e("ziyuan", json);
                    if (json.contains("[")) {
                        PuzzyModel model=null;
                        if (search_type.equals("1")) {
                            Log.e("tag",search_type);
                            BrandPuzzyRoot root = gson.fromJson(json, BrandPuzzyRoot.class);
                            BrandPuzzyData data = root.getData();

                            for (BrandPuzzyList puzzy_list : data.getList()) {
                                model = new PuzzyModel();
                                model.setCatlog(false);
                                model.setKeyword(puzzy_list.getSmall_category());
                                list.add(model);
                                handler.sendEmptyMessage(0);
                            }
                        } else if ("2".equals(search_type)) {
                            Log.e("tag",search_type);
                            CatlogPuzzyRoot root = gson.fromJson(json, CatlogPuzzyRoot.class);
                            CatlogPuzzyData data = root.getData();
                            for (CatlogPuzzyList puzzy_list : data.getList()) {
                                model = new PuzzyModel();
                                model.setCatlog(true);
                                model.setKeyword(puzzy_list.getCategory_id());
                                model.setSearch_name(puzzy_list.getTitle());
                                list.add(model);
                                handler.sendEmptyMessage(0);
                            }
                        } else if ("3".equals(search_type)) {
                            Log.e("tag",search_type);
                            MarketPuzzyRoot root = gson.fromJson(json, MarketPuzzyRoot.class);
                            MarketPuzzyData data = root.getData();
                            for (MarketPuzzyList puzzy_list : data.getList()) {
                                model = new PuzzyModel();
                                model.setShop_name(puzzy_list.getShop_name());
                                model.setKeyword(puzzy_list.getShop_id());
                                list.add(model);
                                handler.sendEmptyMessage(0);
                            }
                        }
                    }
                }
            }
        });
    }
}
