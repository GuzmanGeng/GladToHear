package com.mb.mmdepartment.biz.userspace.listrecord.exchangeprizerecord;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.constans.LoginConsts;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.tools.log.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jack on 2015/10/19.
 */
public class ExchangePrizeRecordBiz implements IExchangePrizeRecordBiz {
    public Map<String,String> params = new HashMap<>();
    public void getExchangePrizeRecord(String userid, final RequestListener listener) {
        params.put(BaseConsts.APP, LoginConsts.GetExchangePrizeRecord.APP_ADDRESS);
        params.put(BaseConsts.CLASS, LoginConsts.GetExchangePrizeRecord.params_class);
        params.put(BaseConsts.SIGN, "ef8187b36dce1d53deb7bd8bcef9d250");
        params.put("userid", TApplication.user_id);
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
