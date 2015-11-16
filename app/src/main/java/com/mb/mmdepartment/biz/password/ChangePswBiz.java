package com.mb.mmdepartment.biz.password;

import com.mb.mmdepartment.base.TApplication;
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
 * Created by krisi on 2015/11/9.
 */
public class ChangePswBiz implements IChangePswBiz{
    HashMap<String,String> params = new HashMap<>();
    @Override
    public void changepsw(String old_psw, String change_psw, String psw_again,String TAG, final RequestListener listener) {
        params.put(BaseConsts.APP, LoginConsts.Account.APP_ADDRESS);
        params.put(BaseConsts.CLASS,"pwd");
        params.put(BaseConsts.SIGN,"1c1a4a0a148932677b5cdc2e84300c85");
        params.put("userid", TApplication.user_id);
        params.put("pwd",old_psw);
        params.put("pwd1",change_psw);
        params.put("pwd2",psw_again);
        OkHttp.asyncPost(BaseConsts.BASE_URL, params, TAG, new Callback() {
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
