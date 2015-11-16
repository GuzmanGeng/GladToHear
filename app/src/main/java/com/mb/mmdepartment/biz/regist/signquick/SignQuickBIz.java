package com.mb.mmdepartment.biz.regist.signquick;

import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.constans.LoginConsts;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by krisi on 2015/11/6.
 */
public class SignQuickBIz implements ISignQuickBiz {
    HashMap<String,String> params = new HashMap<>();
    @Override
    public void signquick(String phone, String code, String device_no, final RequestListener listener) {
        params.put(BaseConsts.APP, LoginConsts.Account.APP_ADDRESS);
        params.put(BaseConsts.CLASS,"signquick");
        params.put(BaseConsts.SIGN,"ec9814711782792ba5c64f4c8c23fbf0");
        params.put("phone",phone);
        params.put("code",code);
        params.put("device_no",device_no);
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
