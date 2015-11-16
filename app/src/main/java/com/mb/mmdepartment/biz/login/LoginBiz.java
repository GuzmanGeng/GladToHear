package com.mb.mmdepartment.biz.login;

import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.constans.LoginConsts;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.tools.MD5Utils;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by Administrator on 2015/9/23 0023.
 */
public class LoginBiz implements ILoginBiz {
    private Map<String,String> paramas=new HashMap<>();
    @Override
    public void login(String username, String password, String device_no, final RequestListener listener) {
        paramas.put(BaseConsts.APP, LoginConsts.Account.APP_ADDRESS);
        paramas.put(BaseConsts.CLASS, LoginConsts.Account.Login.params_class);
        paramas.put(BaseConsts.username, username);
        paramas.put(BaseConsts.password, password);
        paramas.put("device_no", device_no);
        String sign = "";
        if (paramas.size() != 0) {
            sign = MD5Utils.MD5(paramas.get(BaseConsts.APP) + paramas.get(BaseConsts.CLASS) + BaseConsts.APP_KEY);
        }
        paramas.put(BaseConsts.SIGN, sign);
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
               listener.onFailue(request,e);
            }
            @Override
            public void onResponse(final Response response) throws IOException {
                listener.onResponse(response);
            }
        });
    }
}
