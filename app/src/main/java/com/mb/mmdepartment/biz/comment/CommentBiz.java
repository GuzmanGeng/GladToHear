package com.mb.mmdepartment.biz.comment;

import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.constans.CatlogConsts;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by krisi on 2015/10/28.
 */
public class CommentBiz implements ICommentBiz {
    HashMap<String, String> param = new HashMap<>();
    @Override
    public void sendcomment(String content_id, String body, String tag, final RequestListener listener) {
        param.put(BaseConsts.APP, CatlogConsts.SendComment.params_app);
        param.put(BaseConsts.CLASS,CatlogConsts.SendComment.params_class);
        param.put(BaseConsts.SIGN,CatlogConsts.SendComment.params_sign);
        param.put(BaseConsts.SharePreference.USER_ID, TApplication.user_id);
        param.put("content_id",content_id);
        param.put("body",body);
        OkHttp.asyncPost(BaseConsts.BASE_URL, param, tag, new Callback() {
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