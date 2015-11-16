package com.mb.mmdepartment.biz.main_search;
import android.util.Log;

import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.constans.CatlogConsts;
import com.mb.mmdepartment.network.OkHttp;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 增加用户搜索记录的接口
 */
public class MainAddSearchKeyword {
    private Map<String, String> paramas = new HashMap<>();
    public void addHistory(String user_id,String keyword) {
        paramas.put(BaseConsts.APP, CatlogConsts.AddHistoryTag.params_app);
        paramas.put(BaseConsts.CLASS, CatlogConsts.AddHistoryTag.params_class);
        paramas.put(BaseConsts.SIGN, CatlogConsts.AddHistoryTag.params_sign);
        paramas.put("userid", user_id);
        paramas.put("keyword", keyword);
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }
            @Override
            public void onResponse(Response response) throws IOException {
                String json = response.body().string();
                Log.e("fanhui",json);
            }
        });
    }
}
