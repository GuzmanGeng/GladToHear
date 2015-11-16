package com.mb.mmdepartment.biz.userspace.listrecord;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.constans.CatlogConsts;
import com.mb.mmdepartment.constans.LoginConsts;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.tools.MD5Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/24 0024.
 */
public class ListRecordBiz implements IListRecordBiz {
    private Map<String,String> paramas=new HashMap<>();
    @Override
    public void getListRecord(String page, String user_id, String device_no, String tag, final RequestListener listener) {
        paramas.put(BaseConsts.APP, CatlogConsts.UserSpaceListRecord.params_app);
        paramas.put(BaseConsts.CLASS, CatlogConsts.UserSpaceListRecord.params_class);
        paramas.put(BaseConsts.SIGN, CatlogConsts.UserSpaceListRecord.params_sign);
        paramas.put("page", page);
        paramas.put("userid", user_id);
        paramas.put("device_no", device_no);
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas,tag, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onFailue(request, e);
            }
            @Override
            public void onResponse(final Response response) throws IOException {
                listener.onResponse(response);
            }
        });
    }
}
