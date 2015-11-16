package com.mb.mmdepartment.biz.helpcheck.marcket_sel;
import com.mb.mmdepartment.base.TApplication;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by Administrator on 2015/9/16 0016.
 */
public class MacketSelBiz implements IMacketSelBiz {
    private Map<String, String> paramas = new HashMap<>();
    @Override
    public void getMacketList(int parentId, String tag,final RequestListener listener) {
        paramas.put(BaseConsts.APP, "shop");
        paramas.put(BaseConsts.CLASS,"getaddress");
        paramas.put(BaseConsts.SIGN, "e4bd9c59021dc095736ef5d44d202e70");
        paramas.put("parent_id", String.valueOf(parentId));
        paramas.put("shop_id", String.valueOf(0));
        if(TApplication.city_id != null) {
            paramas.put("city", TApplication.city_id);
        }else {
            paramas.put("city", "50");
        }
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas,tag, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onFailue(request,e);
            }
            @Override
            public void onResponse(Response response){
                listener.onResponse(response);
            }
        });
    }
}
