package com.mb.mmdepartment.biz.maininformation.IInfomationRecordBiz;

import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by krisi on 2015/11/3.
 */
public class InformationRecordBiz implements IIformationRecordBiz {
    HashMap<String,String> params = new HashMap<>();
    @Override
    public void getinformationrecord(String content_id, String start_time, String end_time, String read_time, String location, String device_no, final RequestListener listener) {
        params.put(BaseConsts.APP,"news");
        params.put(BaseConsts.CLASS,"articlestatistics");
        params.put(BaseConsts.SIGN,"34615cd5dcc7f7a3d128bed1e4e21ee3");
        params.put("content_id", content_id);
        if(TApplication.user_id != null) {
            params.put("userid",TApplication.user_id);
        }else {
            params.put("userid","0");
        }
        params.put("start_time",start_time);
        params.put("end_time",end_time);
        params.put("read_time",read_time);
        params.put("location",location);
        params.put("device_no",device_no);
        params.put("device_type","2");
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
