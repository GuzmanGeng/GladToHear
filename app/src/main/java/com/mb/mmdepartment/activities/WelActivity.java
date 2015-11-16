package com.mb.mmdepartment.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.adapter.city.HotCityAdapter;
import com.mb.mmdepartment.adapter.city.LetterCityAdapter;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.getcities.serachcity.Root;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.mb.mmdepartment.bean.getcities.Description;
import com.mb.mmdepartment.biz.getcities.GetcitiesBiz;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.tools.AmapLocationUtils;
import com.mb.mmdepartment.tools.OnLocalListener;
import com.mb.mmdepartment.tools.sp.SPCache;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tencent.stat.StatService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WelActivity extends BaseActivity implements OnLocalListener{
    private TextView tv_location;
    private EditText serachcity_ev;
    private LocationManagerProxy mLocationManagerProxy;
    private AmapLocationUtils amaplocation;
    private String provience;
    private GetcitiesBiz getcitiesBiz;
    private ListView hot_city_lv, lv_serach;
    private ExpandableListView lv_cities;
    private List<Description> datas, serachcity, cities;
    private List<List<Description>> groups;
    private LuPinModel luPinModel;
    private com.mb.mmdepartment.bean.getcities.getlettercities.Data data;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (provience == null || "".equals(provience)) {
                        provience = "上海";
                        TApplication.city_id="50";
                        TApplication.city_name = "";
                    }
                    SPCache.putString("provience", provience);
                    startActivity(WelActivity.this, MainActivity.class, "provience", provience);
                    finish();
                    break;
                case 1:
//                    showToast("定位失败");
                    provience = SPCache.getString("provience", "");
                    if (provience == null || "".equals(provience)) {
                        provience = "上海";
                    }
                    SPCache.putString("provience", provience);
                    TApplication.city_name = "";
                    TApplication.city_id="50";
                    startActivity(WelActivity.this, MainActivity.class, "provience", provience);
                    finish();
                    break;
            }

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        luPinModel = new LuPinModel();
        luPinModel.setName(WelActivity.class.getSimpleName());
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
        TApplication.luPinModels.add(luPinModel );
        StatService.onPause(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_wel;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initView();
        initData();
        boolean setLocation = getIntent().getBooleanExtra("setLocation", false);
    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("切换城市");
        action.setHomeButtonEnabled(isTrue);
    }

    /**
     * 初始化view
     */
    private void initView() {
        tv_location = (TextView) findViewById(R.id.location);
        serachcity_ev = (EditText) findViewById(R.id.search_ciyt_ev);
        amaplocation = new AmapLocationUtils(this);
        mLocationManagerProxy = LocationManagerProxy.getInstance(this);
        mLocationManagerProxy.requestLocationData(
                LocationProviderProxy.AMapNetwork, -1, 10, amaplocation);
        mLocationManagerProxy.setGpsEnable(true);
        hot_city_lv=(ListView)findViewById(R.id.hot_city_lv);
        hot_city_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                provience = datas.get(position).getCity_name();
                SPCache.putString("provience", provience);
                startActivity(WelActivity.this, MainPageActivity.class, "provience", provience);
                finish();
            }
        });
        lv_cities = (ExpandableListView) findViewById(R.id.exlv_cities);
        lv_cities.setGroupIndicator(null);
        lv_cities.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                provience = groups.get(groupPosition).get(childPosition).getCity_name();
                String city_id=groups.get(groupPosition).get(childPosition).getCity_id();
                SPCache.putString("provience", provience);
                SPCache.putString("city_id", city_id);
                setResult(RESULT_OK);
                startActivity(WelActivity.this, MainActivity.class, new String[]{"provience","city_id"}, new String[]{provience,city_id});
                finish();
                return false;
            }
        });
        lv_serach = (ListView) findViewById(R.id.lv_search);
        lv_serach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                provience = serachcity.get(position).getCity_name();
                String city_id = serachcity.get(position).getCity_id();
                SPCache.putString("provience", provience);
                SPCache.putString("city_id", city_id);
                setResult(RESULT_OK);
                startActivity(WelActivity.this, MainActivity.class, new String[]{"provience","city_id"}, new String[]{provience,city_id});
                finish();
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        groups = new ArrayList<>();
        getcitiesBiz = new GetcitiesBiz();
        getcitiesBiz.gethotcities(new RequestListener() {
            @Override
            public void onResponse(Response response) {
                if (response.isSuccessful()) {
                    Log.i("tag", "请求城市");
                    Gson gson = new Gson();
                    try {
                        String json = response.body().string();
                        Root root = gson.fromJson(json, Root.class);
                        if (root.getStatus() == OkHttp.NET_STATE) {
                            datas = root.getData();
                            groups.add(0, datas);
                            cities = new ArrayList<Description>();
                            for(int i =0;i<groups.size();i++){
                                for(int j = 0;j<groups.get(i).size();j++){
                                    cities.add(groups.get(i).get(j));
                                }
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //加载热点城市
                                    lv_cities.setAdapter(new LetterCityAdapter(WelActivity.this, groups));
                                    hot_city_lv.setAdapter(new HotCityAdapter(WelActivity.this, datas));
                                }
                            });
                        } else {

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailue(Request request, IOException e) {
//                showToast("请求城市失败");
            }
        });
        getcitiesBiz.getcities(new RequestListener() {
            @Override
            public void onResponse(Response response) {
                if (response.isSuccessful()) {
                    Log.i("tag", "请求城市");
                    Gson gson = new Gson();
                    try {
                        String json = response.body().string();
                        com.mb.mmdepartment.bean.getcities.getlettercities.Root root = gson.fromJson(json, com.mb.mmdepartment.bean.getcities.getlettercities.Root.class);
                        if (root.getStatus() == OkHttp.NET_STATE) {
                            data = root.getData();
                            groups.add(data.getA());
                            groups.add(data.getB());
                            groups.add(data.getC());
                            groups.add(data.getD());
                            groups.add(data.getF());
                            groups.add(data.getG());
                            groups.add(data.getH());
                            groups.add(data.getJ());
                            groups.add(data.getK());
                            groups.add(data.getL());
                            groups.add(data.getM());
                            groups.add(data.getN());
                            groups.add(data.getP());
                            groups.add(data.getQ());
                            groups.add(data.getR());
                            groups.add(data.getS());
                            groups.add(data.getT());
                            groups.add(data.getW());
                            groups.add(data.getX());
                            groups.add(data.getY());
                            groups.add(data.getZ());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //加载字母城市
                                    lv_cities.requestLayout();
                                    lv_cities.setAdapter(new LetterCityAdapter(WelActivity.this, groups));
                                    for (int i = 0; i < groups.size(); i++) {
                                        lv_cities.requestLayout();
                                        lv_cities.expandGroup(i);
                                    }
                                }
                            });
                        } else {

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailue(Request request, IOException e) {

            }
        });
        serachcity_ev.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(final Editable s) {
                Pattern pattern = Pattern.compile("^[\\u4e00-\\u9fa5]*$");
                Matcher matcher = pattern.matcher(s);
                if (matcher.find()) {
                    getcitiesBiz.serachcities(s + "", new RequestListener() {
                        @Override
                        public void onResponse(Response response) {
                            if (response.isSuccessful()) {
                                Log.i("tag", "请求城市");
                                Gson gson = new GsonBuilder().serializeNulls().create();
                                try {
                                    String json = response.body().string();
                                    com.mb.mmdepartment.bean.getcities.serachcity.Root root = gson.fromJson(json, com.mb.mmdepartment.bean.getcities.serachcity.Root.class);
                                    if (root.getStatus() == OkHttp.NET_STATE) {
                                        serachcity = root.getData();
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                //加载热点城市
                                                lv_serach.setAdapter(new HotCityAdapter(WelActivity.this, serachcity));
                                                lv_serach.setVisibility(View.VISIBLE);
//                                                hot_city_lv.setVisibility(View.GONE);
                                                lv_cities.setVisibility(View.GONE);
                                            }
                                        });
                                    } else {

                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailue(Request request, IOException e) {

                        }
                    });
                }
            }
        });
    }

    /**
     * 结束定位
     */
    private void stopLocation() {
        if (mLocationManagerProxy != null) {
            mLocationManagerProxy.removeUpdates(amaplocation);
            mLocationManagerProxy.destroy();
        }
        mLocationManagerProxy = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLocation();
    }

    /**
     * 定位失败后索要做的事情
     *
     * @param error
     */
    @Override
    public void setError(String error) {
        showMsg(this, error);
        //接下来处理定位失败后要做的事情
        provience = "定位失败";
        SPCache.putString("provience", "上海");
        if (provience == null || "".equals(provience)) {
//            provience = "上海";
        }
//        SPCache.putString("provience", provience);
//        TApplication.city_name = "";
        finish();
    }

    /**
     * 定位成功后索要做的事情
     *
     * @param aMapLocation
     */
    @Override
    public void onSuccess(AMapLocation aMapLocation) {
        provience = aMapLocation.getProvince();
        Log.i("provience", provience + "sss");
        if (provience == null || "".equals(provience)) {
            stopLocation();
            provience = "上海";
//            showToast("请检查网络,或者查看GPS");
//            return;
        }
        showMsg(this, "当前定位城市为：" + provience);
        tv_location.setText(provience);
        getcitiesBiz.serachcities(provience, new RequestListener() {
            @Override
            public void onResponse(Response response) {
                if(response.isSuccessful()){
                    Gson gson = new Gson();
                    try{
                        String json = response.body().string();
                        Root root = gson.fromJson(json,Root.class);
                        TApplication.city_id = root.getData().get(0).getCity_id();
                    }catch (Exception e){}
                }
            }

            @Override
            public void onFailue(Request request, IOException e) {

            }
        });
        tv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TApplication.city_name = provience;
                startActivity(WelActivity.this, MainActivity.class, "provience", provience);
                finish();
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }, 1000);
    }

    /**
     * toast事件
     *
     * @param context
     * @param text
     */
    public void showMsg(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 原始跳转
     *
     * @param context 上下文对象
     * @param cla     类对象
     */
    public void startActivity(Context context, Class cla) {
        Intent intent = new Intent(context, cla);
        startActivity(intent);
        finish();
    }

    /**
     * 带有action的跳转
     *
     * @param context
     * @param cla
     * @param action
     */
    public void startActivity(Context context, Class cla, String action) {
        Intent intent = new Intent(context, cla);
        intent.setAction(action);
        startActivity(intent);
        finish();
    }

    /**
     * 带有bundle的intent
     *
     * @param context
     * @param cla
     * @param bundle
     * @param key     bundle的键名
     */
    public void startActivity(Context context, Class cla, Bundle bundle, String key) {
        Intent intent = new Intent(context, cla);
        intent.putExtra(key, bundle);
        startActivity(intent);
        finish();
    }

    /**
     * 带有string参数的是intent
     *
     * @param context
     * @param cla
     * @param text
     * @param key
     */
    public void startActivity(Context context, Class cla, String text, String key) {
        Intent intent = new Intent(context, cla);
        intent.putExtra(key, text);
        startActivity(intent);
        finish();
    }
}
