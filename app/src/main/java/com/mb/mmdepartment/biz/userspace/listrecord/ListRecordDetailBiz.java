package com.mb.mmdepartment.biz.userspace.listrecord;

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
 * Created by krisi on 2015/10/20.
 */
public class ListRecordDetailBiz implements IListRecordDetailBiz {
    private Map<String,String> params = new HashMap<>();
    public void getListRecordDetail(String onumber,String device_no,final RequestListener listener) {
        Log.i("userid", TApplication.user_id);
        Log.i("device_no",device_no);
        if(null==TApplication.user_id||"".equals(TApplication.user_id)){
            params.put("userid", "543");
        }else {
            params.put("userid", TApplication.user_id);
        }
//        if(null==TApplication.device_no||"".equals(TApplication.device_no)){
//            params.put("device_no","");
//        }else {
            params.put("device_no", device_no);
//        }
        params.put("onumber",onumber);
        params.put(BaseConsts.APP, LoginConsts.GetListRecordDetail.APP_ADDRESS);
        params.put(BaseConsts.CLASS,LoginConsts.GetListRecordDetail.params_class);
        params.put("sign","a3a5db0ae00f222be9dfc4c733d5d9f4");
        Log.i("getListRecordDetail", "getListRecordDetail");
        OkHttp.asyncPost(BaseConsts.BASE_URL, params, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.i("getListRecordDetail", "getListRecordDetail failed");
                listener.onFailue(request, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.i("getListRecordDetail", "getListRecordDetail success");
                listener.onResponse(response);
            }
        });
    }
}
