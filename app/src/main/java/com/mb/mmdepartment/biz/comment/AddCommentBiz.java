package com.mb.mmdepartment.biz.comment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
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
 * Created by Administrator on 2015/11/22.
 */
public class AddCommentBiz {
    private Map<String, String> map = new HashMap<>();
    private RecyclerView recyclerView;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:

                    break;
                case 1:

                    break;
            }
        }
    };
    public AddCommentBiz(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }
    public void addComment(String content_id,String user_id,String content) {
        map.put(BaseConsts.APP, CatlogConsts.AddComment.params_app);
        map.put(BaseConsts.CLASS, CatlogConsts.AddComment.params_class);
        map.put(BaseConsts.SIGN, CatlogConsts.AddComment.params_sign);
        map.put("userid", user_id);
        map.put("content_id", content_id);
        map.put("body", content);
        OkHttp.asyncPost(BaseConsts.BASE_URL, map, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }
            @Override
            public void onResponse(Response response) throws IOException {

            }
        });
    }
}
