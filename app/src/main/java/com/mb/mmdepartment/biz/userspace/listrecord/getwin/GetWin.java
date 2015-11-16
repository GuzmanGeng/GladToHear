package com.mb.mmdepartment.biz.userspace.listrecord.getwin;

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

/**
 * Created by jack on 2015/10/19.
 */
public class GetWin implements IGetWin {
    private HashMap<String,String> pararms = new HashMap<>();
    public void getWin(String wincode,String useid, final RequestListener listener){
        pararms.put(BaseConsts.APP, LoginConsts.GetWinCode.APP_ADDRESS);
        pararms.put(BaseConsts.CLASS,LoginConsts.GetWinCode.params_class);
        String sign = MD5Utils.MD5(LoginConsts.GetWinCode.APP_ADDRESS+
        LoginConsts.GetWinCode.params_class+BaseConsts.APP_KEY);
        pararms.put(BaseConsts.SIGN,sign);
        pararms.put("wincode",wincode);
        pararms.put("userid",useid);
        OkHttp.asyncPost(BaseConsts.BASE_URL, pararms, new Callback() {
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
