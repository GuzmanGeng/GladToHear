package com.mb.mmdepartment.biz.helpcheck.marcket_sel.detail;

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
 * Created by Administrator on 2015/10/7 0007.
 */
public class DetailAddressBiz implements IDetailAddressBiz {
    private Map<String, String> paramas = new HashMap<>();
    @Override
    public void getDetailAddress(String shop_id, final RequestListener listener) {
        paramas.put(BaseConsts.APP, CatlogConsts.GetMarket.params_app);
        paramas.put(BaseConsts.CLASS, CatlogConsts.GetMarket.params_class);
        paramas.put(BaseConsts.SIGN, CatlogConsts.GetMarket.params_sign);
        paramas.put("parent_id", shop_id);
        paramas.put("shop_id", "0");
        if (TApplication.city_id != null) {
            paramas.put("city", TApplication.city_id);
        } else {
            paramas.put("city", String.valueOf(0));
        }
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas, new Callback() {
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
