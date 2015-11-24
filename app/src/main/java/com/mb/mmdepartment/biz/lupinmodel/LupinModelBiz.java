package com.mb.mmdepartment.biz.lupinmodel;

import com.google.gson.Gson;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.tools.log.Log;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by krisi on 2015/11/2.
 */
public class LupinModelBiz implements ILupinModelBiz{
    HashMap<String,String> params = new HashMap<>();
    @Override
    public void sendLuPinModel(final String list, String tag, final RequestListener listener) {
        params.put(BaseConsts.APP,"shop");
        params.put(BaseConsts.CLASS,"submitshopaction");
        params.put(BaseConsts.SIGN,"b62b3805eddb0698fcffafae7ae51932");
        params.put("list",list);
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

    public String getlist(String device_no){
        Gson gson = new Gson();
        LuPinModel luPinModelmodel;
        for(int i = 0;i<TApplication.luPinModels.size();i++){
            luPinModelmodel = TApplication.luPinModels.get(i);
            TApplication.luPinModelList.add(luPinModelmodel);
        }
        TApplication.luPinModels.clear();
        String json = gson.toJson(TApplication.luPinModelList);
        String userid = "";
        if(TApplication.user_id != null && !TApplication.user_id.equals("")){
            userid = TApplication.user_id;
        }
        json = "{\"userid\":\""+userid+"\",\"device_no\":"+ "\""+device_no+"\",\"data\":"
                +json+"}";
        return json;
    }
}
