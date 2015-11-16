package com.mb.mmdepartment.biz.login.getuserpic;

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
import java.util.Map;

/**
 * Created by krisi on 2015/10/28.
 */
public class GetUserPicBiz implements IGetUserPicBiz {
    Map<String,String> param = new HashMap<>();
    @Override
    public void getuserpic(String tag, final RequestListener listener) {
        param.put(BaseConsts.APP, LoginConsts.Account.APP_ADDRESS);
        param.put(BaseConsts.CLASS, LoginConsts.Account.Getuserheadpic.params_class);
        param.put(BaseConsts.SIGN,LoginConsts.Account.Getuserheadpic.params_sign);
        param.put("userid", TApplication.user_id);
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
