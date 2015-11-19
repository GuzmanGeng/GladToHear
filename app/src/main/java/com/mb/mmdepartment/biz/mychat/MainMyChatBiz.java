package com.mb.mmdepartment.biz.mychat;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.adapter.mychat.MyChatAdapter;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.bean.mychat.Comment;
import com.mb.mmdepartment.bean.mychat.CommentRoot;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.constans.CatlogConsts;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.view.LoadingDialog;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by joyone2one on 2015/11/19.
 */
public class MainMyChatBiz {
    private Map<String, String> paramas = new HashMap<>();
    private RecyclerView recyclerView;
    private Activity context;
    private List<Comment> list;
    private ProgressDialog dialog;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (dialog != null) {
                        dialog.dismiss();
                        dialog = null;
                    }
                    recyclerView.setVisibility(View.VISIBLE);
                    MyChatAdapter adapter = new MyChatAdapter(list);
                    recyclerView.setAdapter(adapter);
                    break;
                case 1:
                    if (dialog != null) {
                        dialog.dismiss();
                        dialog = null;
                    }
                    recyclerView.setVisibility(View.GONE);
                case 5:
                    if (dialog != null) {
                        dialog.dismiss();
                        dialog = null;
                    }
                    ((BaseActivity)context).showToast("服务器正在维护,请稍后再试.");
                    break;
                case 10:
                    if (dialog != null) {
                        dialog.dismiss();
                        dialog = null;
                    }
                    ((BaseActivity)context).showToast("网络异常,请检查网络后重试!");
                    break;
            }
        }
    };
    public MainMyChatBiz(RecyclerView recyclerView,Activity context) {
        this.recyclerView = recyclerView;
        this.context = context;
        LinearLayoutManager manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);
    }

    /**
     * 我的评论
     * @param user_id
     */
    public void getMyChat(String user_id) {
        if (dialog == null) {
            dialog = new ProgressDialog(context,R.style.Translucent_NoTitle);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("正在获取数据....");
            dialog.show();
        } else {
            dialog.show();
        }
        paramas.put(BaseConsts.APP, CatlogConsts.MainMyReplay.params_app);
        paramas.put(BaseConsts.CLASS, CatlogConsts.MainMyReplay.params_class);
        paramas.put(BaseConsts.SIGN, CatlogConsts.MainMyReplay.params_sign);
        paramas.put("userid", user_id);
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(10);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String json = response.body().string();
                    if (json.contains("[")) {
                        CommentRoot root = gson.fromJson(json, CommentRoot.class);
                        if (root.getStatus() == 0) {
                            list = root.getData().getComment();
                            handler.sendEmptyMessage(0);
                        } else {
                            handler.sendEmptyMessage(1);
                        }
                    } else {
                        handler.sendEmptyMessage(1);
                    }
                }
            }
        });
    }
    /**
     * 回复我的
     * @param user_id
     */
    public void getMyReplay(String user_id) {
        if (dialog == null) {
            dialog = new ProgressDialog(context,R.style.Translucent_NoTitle);
            dialog.setMessage("正在获取数据....");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } else {
            dialog.show();
        }
        paramas.put(BaseConsts.APP, CatlogConsts.MainMyChat.params_app);
        paramas.put(BaseConsts.CLASS, CatlogConsts.MainMyChat.params_class);
        paramas.put(BaseConsts.SIGN, CatlogConsts.MainMyChat.params_sign);
        paramas.put("userid", user_id);
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(10);
            }
            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String json = response.body().string();
                    if (json.contains("[")) {
                        CommentRoot root = gson.fromJson(json, CommentRoot.class);
                        if (root.getStatus() == 0) {
                            list = root.getData().getComment();
                            handler.sendEmptyMessage(0);
                        } else {
                            handler.sendEmptyMessage(1);
                        }
                    } else {
                        handler.sendEmptyMessage(1);
                    }
                }
            }
        });
    }

}
