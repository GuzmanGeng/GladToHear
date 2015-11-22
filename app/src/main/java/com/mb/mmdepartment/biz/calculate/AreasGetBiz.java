package com.mb.mmdepartment.biz.calculate;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.mb.mmdepartment.adapter.helpcalculate.AreasAdapter;
import com.mb.mmdepartment.adapter.helpcalculate.AreasMarketListAdapter;
import com.mb.mmdepartment.bean.market_sel.AreaMarketSelData;
import com.mb.mmdepartment.bean.market_sel.AreaMarketSelRoot;
import com.mb.mmdepartment.bean.market_sel.AreaSelData;
import com.mb.mmdepartment.bean.market_sel.AreaSelRoot;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.constans.CatlogConsts;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.tools.sp.SPCache;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/22.
 */
public class AreasGetBiz {
    private Spinner spinner;
    private Context context;
    private List<AreaSelData> datas;
    private AreasAdapter areasAdapter;
    private RecyclerView recyclerView;
    private List<AreaMarketSelData> lists;
    private AreasMarketListAdapter adapter;
    private String shop_id;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    areasAdapter = new AreasAdapter(datas);
                    spinner.setAdapter(areasAdapter);

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            lists.clear();
                            getListAreas(shop_id, SPCache.getString("city_id", "50"), SPCache.getString(BaseConsts.SharePreference.MAP_LOCATION, "0,0"), datas.get(position).getId());
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    break;
                case 1:
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    public AreasGetBiz(Spinner spinner,Context context,RecyclerView recyclerView,String shop_id) {
        this.spinner = spinner;
        this.context=context;
        this.recyclerView=recyclerView;
        this.shop_id=shop_id;
        lists = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);
        adapter = new AreasMarketListAdapter(lists);
        recyclerView.setAdapter(adapter);
    }
    public void getAreas(String city_name) {
        Map<String, String> paramas = new HashMap<>();
        paramas.put(BaseConsts.APP, CatlogConsts.Areas.params_app);
        paramas.put(BaseConsts.CLASS, CatlogConsts.Areas.params_class);
        paramas.put(BaseConsts.SIGN, CatlogConsts.Areas.params_sign);
        paramas.put("city_name", city_name);
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }
            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Gson gson = new Gson();
                    AreaSelRoot root = gson.fromJson(json, AreaSelRoot.class);
                    if (root.getStatus() == 0) {
                        datas = root.getData();
                        handler.sendEmptyMessage(0);
                    }
                }
            }
        });
    }

    /**
     * 请求对应地区对应的超市
     * @param shop_id 超市id
     * @param city_id 城市id
     * @param coordinate 经纬度
     * @param area 区域id
     */
    public void getListAreas(String shop_id,String city_id,String coordinate,String area){
        Map<String, String> paramas = new HashMap<>();
        paramas.put(BaseConsts.APP, CatlogConsts.AreasList.params_app);
        paramas.put(BaseConsts.CLASS, CatlogConsts.AreasList.params_class);
        paramas.put(BaseConsts.SIGN, CatlogConsts.AreasList.params_sign);
        paramas.put("shop_id", shop_id);
        paramas.put("city_id", city_id);
        paramas.put("coordinate", coordinate);
        paramas.put("area", area);
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }
            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Gson gson = new Gson();
                    AreaMarketSelRoot root = gson.fromJson(json, AreaMarketSelRoot.class);
                    if (root.getStatus() == 0) {
                        for (AreaMarketSelData data:root.getData()) {
                            lists.add(data);
                        }
                        handler.sendEmptyMessage(1);
                    }
                }
            }
        });
    }
}
