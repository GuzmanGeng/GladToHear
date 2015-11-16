package com.mb.mmdepartment.biz.login.getpic;

import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by krisi on 2015/10/29.
 */
public class GetPic implements IGetPic {
    Map<String,String> params = new HashMap<>();
    @Override
    public void getpic(String advert_group_id, String device_type, final RequestListener listener) {
        params.put(BaseConsts.APP,"advert");
        params.put(BaseConsts.CLASS,"getadvertbygroupid");
        params.put(BaseConsts.SIGN,"91ba38930c49cda5a5fba720105a15fc");
        params.put("advert_group_id",advert_group_id);
        params.put("device_type","2");
        OkHttp.asyncPost(BaseConsts.BASE_URL, params, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onFailue(request,e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                listener.onResponse(response);
            }
        });
    }
}
