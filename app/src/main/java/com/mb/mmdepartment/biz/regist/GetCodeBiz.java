package com.mb.mmdepartment.biz.regist;

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
 * Created by Administrator on 2015/9/22 0022.
 */
public class GetCodeBiz implements IGetCodeBiz{
    private Map<String, String> paramas = new HashMap<>();
    @Override
    public void getCode(String phone,final RequestListener listener) {
        paramas.put(BaseConsts.APP, CatlogConsts.GetMailCode.params_app);
        paramas.put(BaseConsts.CLASS, CatlogConsts.GetMailCode.params_class);
        paramas.put(BaseConsts.SIGN, CatlogConsts.GetMailCode.params_sign);
        paramas.put("phone", phone);
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
