package com.mb.mmdepartment.biz.calculate;

import com.mb.mmdepartment.base.TApplication;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.constans.CatlogConsts;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/26 0026.
 */
public class MarcketSelBiz implements IMarcketSelBiz {
    private Map<String, String> paramas = new HashMap<>();

    @Override
    public void getMacketList(int parentId, int city_id, String tag, final RequestListener listener) {
        paramas.put(BaseConsts.APP, "shop");
        paramas.put(BaseConsts.CLASS, "getaddress");
        paramas.put(BaseConsts.SIGN, "e4bd9c59021dc095736ef5d44d202e70");
        if(parentId == 0){
            paramas.put("shop_id","0");
        }
        paramas.put("parent_id",String.valueOf(parentId));
        if (TApplication.city_id != null) {
            paramas.put("city", TApplication.city_id);
        } else {
            paramas.put("city", String.valueOf(city_id));
        }
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas, tag, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onFailue(request, e);
            }

            @Override
            public void onResponse(Response response) {
                listener.onResponse(response);
            }
        });
    }
}
