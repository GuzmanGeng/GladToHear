package com.mb.mmdepartment.biz.maininformation.commentlist;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.constans.CatlogConsts;
import com.mb.mmdepartment.listener.MakeCommentListener;
import com.mb.mmdepartment.network.OkHttp;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/25 0025.
 */
public class CommentListBiz implements ICommentListBiz {
    private Map<String, String> paramas = new HashMap<>();

    /**
     * 获取评论列表
     * @param content_id
     * @param userid
     * @param page
     * @param tag
     * @param listener
     */
    @Override
    public void getCommentList(String content_id, String userid, int page, String tag, final MakeCommentListener listener) {
        paramas.put(BaseConsts.APP, CatlogConsts.CommentList.params_app);
        paramas.put(BaseConsts.CLASS, CatlogConsts.CommentList.params_class);
        paramas.put(BaseConsts.SIGN, CatlogConsts.CommentList.params_sign);
        paramas.put("content_id", content_id);
        paramas.put("userid", userid);
        paramas.put("page", String.valueOf(page));
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas, tag, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onMakeCommentFailue(request, e);
            }
            @Override
            public void onResponse(Response response) {
                listener.onMakeCommentResponse(response);
            }
        });
    }
}
