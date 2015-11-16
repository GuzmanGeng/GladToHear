package com.mb.mmdepartment.biz.helpcheck.marcket_sel.main_new;
import android.util.Log;

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
 * maina 品牌新品
 */
public class NewBrandBiz implements INewBrandBiz {
    private Map<String, String> paramas = new HashMap<>();
    @Override
    public void getMacketList(int page,int user_id, int city_id, String keyword, String zixun_category, final RequestListener listener) {
        paramas.put(BaseConsts.APP, CatlogConsts.Brand.params_app);
        paramas.put(BaseConsts.CLASS, CatlogConsts.Brand.params_class);
        paramas.put(BaseConsts.SIGN, CatlogConsts.Brand.params_sign);
        paramas.put("page",String.valueOf(page));
        paramas.put("city_id",String.valueOf(city_id));
//        paramas.put("userid",String.valueOf(user_id));
        paramas.put("zixun_category",zixun_category);
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas, new Callback() {
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
    public void getMacketList(int page,String zixun_category,String tag,final RequestListener listener) {
        paramas.put(BaseConsts.APP, CatlogConsts.Brand.params_app);
        paramas.put(BaseConsts.CLASS, CatlogConsts.Brand.params_class);
        paramas.put(BaseConsts.SIGN, CatlogConsts.Brand.params_sign);
        paramas.put("page",String.valueOf(page));
        Log.i("page", "page:" + page);
        paramas.put("zixun_category",zixun_category);
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas,tag, new Callback() {
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

    public void getMacketList(String zixun_category,String tag,final RequestListener listener) {
        paramas.put(BaseConsts.APP, CatlogConsts.Brand.params_app);
        paramas.put(BaseConsts.CLASS, CatlogConsts.Brand.params_class);
        paramas.put(BaseConsts.SIGN, CatlogConsts.Brand.params_sign);
        paramas.put("zixun_category",zixun_category);
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas,tag, new Callback() {
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
