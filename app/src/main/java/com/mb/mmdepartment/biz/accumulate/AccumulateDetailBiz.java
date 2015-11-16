package com.mb.mmdepartment.biz.accumulate;
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
 * Created by Administrator on 2015/9/24 0024.
 */
public class AccumulateDetailBiz implements IAccumulateDetailBiz {
    private Map<String, String> paramas = new HashMap<>();
    @Override
    public void getDetail(String content_id, String tag, final RequestListener listener) {
        paramas.put(BaseConsts.APP, CatlogConsts.AccumulationDetail.params_app);
        paramas.put(BaseConsts.CLASS, CatlogConsts.AccumulationDetail.params_class);
        paramas.put(BaseConsts.SIGN, CatlogConsts.AccumulationDetail.params_sign);
        paramas.put("content_id",content_id);
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas, tag, new Callback() {
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
