package com.mb.mmdepartment.biz.referesh;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;

import com.google.gson.Gson;
import com.mb.mmdepartment.bean.referesh.RefereshRoot;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.constans.CatlogConsts;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by joyone2one on 2015/11/20.
 */
public class RefereshBiz {
    private Map<String, String> paramas = new HashMap<>();
    public void getVersionNam(String type, final RequestListener listener) {
        paramas.put(BaseConsts.APP, CatlogConsts.RefereshUpdate.params_app);
        paramas.put(BaseConsts.CLASS, CatlogConsts.RefereshUpdate.params_class);
        paramas.put(BaseConsts.SIGN, CatlogConsts.RefereshUpdate.params_sign);
        paramas.put("device_type", type);
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas, new Callback() {
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
