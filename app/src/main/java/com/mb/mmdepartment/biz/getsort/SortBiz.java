package com.mb.mmdepartment.biz.getsort;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mb.mmdepartment.activities.ProposedProjectActivity;
import com.mb.mmdepartment.adapter.proposedproject.ProposedProjectAdapter;
import com.mb.mmdepartment.bean.buyplan.Root;
import com.mb.mmdepartment.bean.buyplan.byprice.DataList;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.constans.CatlogConsts;
import com.mb.mmdepartment.network.OkHttp;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by krisi on 2015/10/22.
 */
public class SortBiz{
    private Map<String, String> params = new HashMap<>();
    private RecyclerView recyclerView;
    private Context context;
    private List<DataList> list;
    private LinearLayoutManager manager;
    private int which;
    private ProposedProjectAdapter adapter;
    private TextView textView;
    private Activity activity;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Log.e("adapter", "有数据传递");
                    adapter = new ProposedProjectAdapter(list, which, context,textView,activity);
                    manager = new LinearLayoutManager(context);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(adapter);
                    break;
                case 1:
                    ((ProposedProjectActivity)context).showToast("服务端正在维护,请稍后再试");
                    break;
                case 10:
                    ((ProposedProjectActivity)context).showToast("网络异常,请检查网络后再重试");
                    break;
            }
        }
    };
    public SortBiz(Context context,RecyclerView recyclerView,int which,TextView textView,Activity activity){
        this.context=context;
        this.recyclerView=recyclerView;
        this.which=which;
        this.activity=activity;
        this.textView=textView;
    }
    public void sort(String tag, String device_no, String order_type, String category_id, String shop_id, int group, int city, int order) {
        params.put(BaseConsts.APP, CatlogConsts.SortPlan.params_app);
        params.put(BaseConsts.CLASS, CatlogConsts.SortPlan.params_class);
        params.put(BaseConsts.SIGN, CatlogConsts.SortPlan.params_sign);
        if (!TextUtils.isEmpty(TApplication.user_id)) {
            params.put("userid", TApplication.user_id);
        } else {
            params.put("userid","0");
        }
        if (device_no != null) {
            params.put("device_no", device_no);
        } else {
            params.put("device_no", "0");
        }
        params.put("address_id", shop_id);
        params.put("category_id", category_id );
        params.put("city", String.valueOf(city));
        params.put("order", order + "");
        params.put("order_type", order_type);
        params.put("group", group + "");
        Log.e("网络请求了", "到这了");
        OkHttp.asyncPost(BaseConsts.BASE_URL, params, tag, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(10);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Gson gson = new Gson();
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Log.e("json=====", json);
                    Root root = gson.fromJson(json, Root.class);
                    if (root.getStatus() == 0) {
                        list = root.getData().getList();
                        Log.e("root有数据", "有的");
                        handler.sendEmptyMessage(0);
                    } else {
                        handler.sendEmptyMessage(1);
                    }
                }
            }
        });
    }
}
