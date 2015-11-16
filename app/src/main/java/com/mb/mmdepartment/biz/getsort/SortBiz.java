package com.mb.mmdepartment.biz.getsort;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.mb.mmdepartment.bean.buyplan.byprice.DataList;
import com.mb.mmdepartment.tools.sp.SPCache;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.constans.CatlogConsts;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.tools.log.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by krisi on 2015/10/22.
 */
public class SortBiz implements ISortBiz {
    private Map<String, String> params = new HashMap<>();
    private RecyclerView recyclerView;
    private Context context;
    private List<DataList> list;
    private LinearLayoutManager manager;
    public SortBiz(Context context,RecyclerView recyclerView){
        this.context=context;
        this.recyclerView=recyclerView;
        list = new ArrayList<>();
        manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);

    }
    @Override
    public void sort(String tag, String device_no, String order_type, String category_id, String shop_id, int group, int city, int order, final RequestListener listener) {
        params.put(BaseConsts.APP, CatlogConsts.SortPlan.params_app);
        params.put(BaseConsts.CLASS, CatlogConsts.SortPlan.params_class);
        params.put(BaseConsts.SIGN, CatlogConsts.SortPlan.params_sign);
        if(null == TApplication.user_id|| TextUtils.isEmpty(TApplication.user_id)){
            params.put("userid", "0");
        }else{
            params.put("userid", TApplication.user_id);
        }
        if((null == device_no )|| ("".equals(device_no))){
            params.put("device_no", "0");
        }else {
            params.put("device_no", device_no);
        }
        params.put("address_id", shop_id);
        params.put("category_id", category_id );
        params.put("city", String.valueOf(city));
        params.put("order", order + "");
        params.put("order_type", order_type);
        params.put("group", group + "");
        Log.i("params", TApplication.user_id + device_no + shop_id + category_id + city + order + order_type + group);
        OkHttp.asyncPost(BaseConsts.BASE_URL, params, tag, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onFailue(request, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                listener.onResponse(response);
            }
        });

    }
}
