package com.mb.mmdepartment.biz.calculate;
import com.mb.mmdepartment.base.TApplication;
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
public class CommodityBiz implements ICommondityBiz {
    private Map<String,String> paramas = new HashMap<>();
    @Override
    public void getCommodityList(int parent_id,int local, String shop_id, String tag,final RequestListener listener) {
        paramas.put(BaseConsts.APP, CatlogConsts.GetCommodity.params_app);
        paramas.put(BaseConsts.CLASS, CatlogConsts.GetCommodity.params_class);
        paramas.put(BaseConsts.SIGN, CatlogConsts.GetCommodity.params_sign);
        paramas.put("parent_id", String.valueOf(parent_id));
        if(TApplication.city_id!=null){
            paramas.put("local", TApplication.city_id);
        }else {
            paramas.put("local", String.valueOf(local));
        }
        paramas.put("shop_id", shop_id);
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas, tag, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onFailue(request, e);
            }

            @Override
            public void onResponse(Response response) {
                listener.onResponse(response);
            }
        });
    }
}
