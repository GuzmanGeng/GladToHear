package com.mb.mmdepartment.biz.submitorders;

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
 * Created by krisi on 2015/10/27.
 */
public class SubmitordersBiz implements IsubmitordersBiz {
    private Map<String, String> param = new HashMap<>();

    @Override
    public void submitorders(String list, String tag, final RequestListener listener) {
        param.put(BaseConsts.APP, CatlogConsts.Submitorders.params_app);
        param.put(BaseConsts.CLASS, CatlogConsts.Submitorders.params_class);
        param.put(BaseConsts.SIGN, CatlogConsts.Submitorders.params_sign);
        param.put("list", list);
        OkHttp.asyncPost(BaseConsts.BASE_URL, param, tag, new Callback() {
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
