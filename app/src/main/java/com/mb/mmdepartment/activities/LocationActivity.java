package com.mb.mmdepartment.activities;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.google.gson.Gson;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.adapter.city.HotCityAdapter;
import com.mb.mmdepartment.adapter.city.LetterCityAdapter;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.getcities.Description;
import com.mb.mmdepartment.bean.getcities.Root;
import com.mb.mmdepartment.bean.getcities.getlettercities.Data;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.mb.mmdepartment.biz.getcities.GetcitiesBiz;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.tools.AmapLocationUtils;
import com.mb.mmdepartment.tools.OnLocalListener;
import com.mb.mmdepartment.tools.log.Log;
import com.mb.mmdepartment.tools.sp.SPCache;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tencent.stat.StatService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LocationActivity extends BaseActivity implements OnLocalListener ,TextWatcher{
    private ListView hot_city_lv,lv_search;
    private ExpandableListView exlv_cities;
    private EditText search_ciyt_ev;
    private TextView location;
    private LuPinModel luPinModel;
    private AmapLocationUtils amaplocation;
    private LocationManagerProxy mLocationManagerProxy;
    private String provience;
    private GetcitiesBiz getcitiesBiz;
    private List<Description> datas, cities;
    private List<List<Description>> groups;
    private Data data;

    @Override
    public int getLayout() {
        return R.layout.activity_wel;
    }
    @Override
    public void init(Bundle savedInstanceState) {
        getcitiesBiz=new GetcitiesBiz();
        groups = new ArrayList<>();
        initView();
        setListener();
    }
    private void setListener() {
        search_ciyt_ev.addTextChangedListener(this);
        exlv_cities.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                provience = groups.get(groupPosition).get(childPosition).getCity_name();
                String city_id = provience = groups.get(groupPosition).get(childPosition).getCity_id();
                SPCache.putString("provience", provience);
                SPCache.putString("city_id", city_id);
                setResult(RESULT_OK);
                startActivity(LocationActivity.this, MainActivity.class, new String[]{"provience", "city_id"}, new String[]{provience, city_id});
                finish();
                return false;
            }
        });
        hot_city_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                provience = datas.get(position).getCity_name();
                String city_id = datas.get(position).getCity_id();
                SPCache.putString("provience", provience);
                SPCache.putString("city_id", city_id);
                setResult(RESULT_OK);
                startActivity(LocationActivity.this, MainActivity.class, new String[]{"provience", "city_id"}, new String[]{provience, city_id});

                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TApplication.activities.remove(this);
        stopLocation();
    }

    private void initView() {
        exlv_cities = (ExpandableListView) findViewById(R.id.exlv_cities);
        exlv_cities.setGroupIndicator(null);
        hot_city_lv = (ListView) findViewById(R.id.hot_city_lv);
        lv_search = (ListView) findViewById(R.id.lv_search);
        search_ciyt_ev = (EditText) findViewById(R.id.search_ciyt_ev);
        location = (TextView) findViewById(R.id.location);
        initHotCity();
        initLocation();
    }

    private void initHotCity() {
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
                            cities = new ArrayList<>();
                            for (int i = 0; i < groups.size(); i++) {
                                for (int j = 0; j < groups.get(i).size(); j++) {
                                    cities.add(groups.get(i).get(j));
                                }
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //加载热点城市
                                    exlv_cities.setAdapter(new LetterCityAdapter(LocationActivity.this, groups));
                                    hot_city_lv.setAdapter(new HotCityAdapter(LocationActivity.this, datas));
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
                                    exlv_cities.requestLayout();
                                    exlv_cities.setAdapter(new LetterCityAdapter(LocationActivity.this, groups));
                                    for (int i = 0; i < groups.size(); i++) {
                                        exlv_cities.requestLayout();
                                        exlv_cities.expandGroup(i);
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
    }

    private void initLocation() {
        amaplocation = new AmapLocationUtils(this);
        mLocationManagerProxy = LocationManagerProxy.getInstance(this);
        mLocationManagerProxy.requestLocationData(
                LocationProviderProxy.AMapNetwork, -1, 10, amaplocation);
        mLocationManagerProxy.setGpsEnable(true);
    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("切换城市");
        action.setHomeButtonEnabled(isTrue);
    }

    @Override
    public void setError(String error) {
        showToast(error);
        provience = "定位失败";
        location.setText(provience);
    }

    @Override
    public void onSuccess(AMapLocation aMapLocation) {
        provience = aMapLocation.getCity();
        if (provience == null || "".equals(provience)) {
            location.setText("网络异常,请重试...");
            stopLocation();
            showToast("请检查网络,或者查看GPS");
            return;
        }
        location.setText(provience);
        getcitiesBiz.getLocationCityId(provience);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                startActivity(LocationActivity.this, MainActivity.class, "provience", provience);
                finish();
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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(s.toString())) {
            getcitiesBiz.serachcities(s.toString());
            lv_search.setVisibility(View.VISIBLE);
//            if (TApplication.city_name != null) {
//                city_lv_puzzy.setText(TApplication.city_name);
//                city_lv_puzzy.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        provience = TApplication.city_name;
//                        String city_id=TApplication.city_id;
//                        SPCache.putString("provience", provience);
//                        SPCache.putString("city_id",city_id);
//                        TApplication.city_name=null;
//                        TApplication.city_id=null;
//                        setResult(RESULT_OK);
//                        startActivity(LocationActivity.this, MainActivity.class, new String[]{"provience","city_id"}, new String[]{provience,city_id});
//                        finish();
//                    }
//                });
//            } else {
//            }
        }else {
            lv_search.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
