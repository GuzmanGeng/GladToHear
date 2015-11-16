package com.mb.mmdepartment.biz.getversion;

import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by krisi on 2015/11/2.
 */
public class GetVersionBiz implements IGetVersionBiz {
    HashMap<String, String> params = new HashMap<>();

    @Override
    public void getVersion(final RequestListener listener) {
        params.put(BaseConsts.APP, "system");
        params.put(BaseConsts.CLASS, "getversion");
        params.put(BaseConsts.SIGN, "090ed9f4dafa9cac13eace31574c3d90");
        params.put("device_type", "2");
        OkHttp.asyncPost(BaseConsts.BASE_URL, params, new Callback() {
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
