package com.mb.mmdepartment.fragment.main.helpcheck;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.gson.Gson;
import com.mb.mmdepartment.R;
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
 * Created by joyone2one on 2015/11/23.
 */
public class PuzzyFragment extends Fragment{
    private String query_name;
    private Map<String, String> paramas = new HashMap<>();
    private String search_type;
    private List<PuzzyModel> list;
    private MyPuzzyReceiver receiver;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:

                    break;
                case 1:

                    break;
            }
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        receiver = new MyPuzzyReceiver();
        IntentFilter filter = new IntentFilter("com.mb.mmdepartment.puzzy");
        getActivity().registerReceiver(receiver, filter);
        if (getArguments() != null) {
            query_name=getArguments().getString("query_name");
            search_type = getArguments().getString("search_type");
        }
        list = new ArrayList<PuzzyModel>();
        initData();
        getPuzzyData(query_name,search_type);
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
                    if (search_type.equals("1")) {
                        BrandPuzzyRoot root = gson.fromJson(json, BrandPuzzyRoot.class);
                        BrandPuzzyData data = root.getData();
                        for (BrandPuzzyList puzzy_list : data.getList()) {
                            PuzzyModel model = new PuzzyModel();
                            model.setCatlog(false);
                            model.setKeyword(puzzy_list.getSmall_category());
                            list.add(model);
                        }
                    }else if ("2".equals(search_type)) {
                        CatlogPuzzyRoot root = gson.fromJson(json, CatlogPuzzyRoot.class);
                        CatlogPuzzyData data = root.getData();
                        for (CatlogPuzzyList puzzy_list : data.getList()) {
                            PuzzyModel model = new PuzzyModel();
                            model.setCatlog(true);
                            model.setKeyword(puzzy_list.getCategory_id());
                            model.setSearch_name(puzzy_list.getTitle());
                            list.add(model);
                        }
                    }else if ("3".equals(search_type)) {
                        MarketPuzzyRoot root = gson.fromJson(json, MarketPuzzyRoot.class);
                        MarketPuzzyData data = root.getData();
                        for (MarketPuzzyList puzzy_list : data.getList()) {
                            PuzzyModel model = new PuzzyModel();
                            model.setKeyword(puzzy_list.getShop_name());
                            model.setKeyword(puzzy_list.getShop_id());
                            list.add(model);
                        }
                    }
                }
            }
        });
    }
    private void initData() {
        paramas.put(BaseConsts.APP, CatlogConsts.MarketPuzzy.params_app);
        paramas.put(BaseConsts.CLASS, CatlogConsts.MarketPuzzy.params_class);
        paramas.put(BaseConsts.SIGN, CatlogConsts.MarketPuzzy.params_sign);
        paramas.put("local", SPCache.getString("city_id","50"));
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.puzzy_lv, container, false);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
    }

    class MyPuzzyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("com.mb.mmdepartment.puzzy".equals(action)) {
                query_name = intent.getStringExtra("query_name");
                getPuzzyData(query_name,search_type);
            }
        }
    }
}
