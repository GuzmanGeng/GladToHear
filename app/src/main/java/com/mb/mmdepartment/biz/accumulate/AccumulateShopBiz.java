package com.mb.mmdepartment.biz.accumulate;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.mb.mmdepartment.bean.accumulate.Root;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.constans.CatlogConsts;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by Administrator on 2015/9/11 0011.
 */
public class AccumulateShopBiz implements IAccumulateShopBiz {
    Map<String, String> paramas = new HashMap<>();
    @Override
    public void getProductsList(int category, int contion, String sort,String tag, final RequestListener listener) {
        paramas.put(BaseConsts.APP, CatlogConsts.Accumulation.params_app);
        paramas.put(BaseConsts.CLASS, CatlogConsts.Accumulation.params_class);
        paramas.put(BaseConsts.SIGN, CatlogConsts.Accumulation.params_sign);
        paramas.put("category",String.valueOf(category));
        paramas.put("contion",String.valueOf(contion));
        paramas.put("sort",sort);
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas,tag, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onFailue(request,e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                listener.onResponse(response);
            }
        });
    }
}
