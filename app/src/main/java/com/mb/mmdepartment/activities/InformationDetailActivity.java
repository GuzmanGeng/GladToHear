package com.mb.mmdepartment.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mb.mmdepartment.bean.comment.CommentBackRoot;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.mb.mmdepartment.biz.comment.CommentBiz;
import com.mb.mmdepartment.biz.maininformation.IInfomationRecordBiz.InformationRecordBiz;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.constans.CatlogConsts;
import com.mb.mmdepartment.tools.CustomToast;
import com.mb.mmdepartment.tools.Tools;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.adapter.informationcollection.InformationCommentAdapter;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.informationcollaction.commentlist.Comment;
import com.mb.mmdepartment.bean.informationcollaction.commentlist.CommentRoot;
import com.mb.mmdepartment.bean.informationcollaction.commentlist.User;
import com.mb.mmdepartment.bean.informationcollaction.detail.Content;
import com.mb.mmdepartment.bean.informationcollaction.detail.Root;
import com.mb.mmdepartment.biz.maininformation.InformationDetailBiz;
import com.mb.mmdepartment.biz.maininformation.commentlist.CommentListBiz;
import com.mb.mmdepartment.listener.MakeCommentListener;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.overridge.MyGridLayoutManager;
import com.mb.mmdepartment.view.LoadingDialog;
import com.tencent.stat.StatService;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class InformationDetailActivity extends BaseActivity implements RequestListener,MakeCommentListener{
    private final String TAG = InformationDetailActivity.class.getSimpleName();
    private TextView information_detail_title_tv,information_detail_author_tv,information_detail_time_tv;
    private RecyclerView information_detai_recycle;
    private WebView information_detail_web;
    private TextView information_detail_more_comment_iv;
    private EditText comment_et;
    private String content_id,start_time;
    private long startsecrond;
    private Dialog dialog;
    private Content content;
    private InformationDetailBiz informationDetailBiz;
    private CommentListBiz commentListBiz;
    private Comment comment,comment1;
    private InformationCommentAdapter adapter;
    private List<Comment> list=new ArrayList<Comment>();
    private MyGridLayoutManager manager;
    private CommentRoot root;
    private ImageView send;
    private LuPinModel luPinModel;
    private CommentBiz commentBiz;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0://处理资讯详情请求
                    information_detail_title_tv.setText(content.getTitle());
                    information_detail_author_tv.setText(content.getAuthor());
                    information_detail_time_tv.setText(content.getCtime());


                    String body=content.getContentbody().replace(" ","");

                    information_detail_web.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8
                    information_detail_web.loadUrl(content.getShare_url());
//                    information_detail_web.loadData(body, "text/html;charset=UTF-8", null);
                    information_detail_web.getSettings().setLoadsImagesAutomatically(true);
//                    information_detail_web.getSettings().setBlockNetworkImage(false);
//                    information_detail_web.loadData(content.getContentbody(), "text/html;charset=UTF-8", null);
                    Log.i("webview",content.getContentbody());
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    break;
                case 1:
                    break;
                case 2:
                    manager = new MyGridLayoutManager(InformationDetailActivity.this,1);
                    information_detai_recycle.setLayoutManager(manager);
                    information_detai_recycle.setAdapter(adapter);
                    break;
                case 10:
                    information_detail_more_comment_iv.setText("没有更多评论");
                    information_detail_more_comment_iv.setClickable(false);
                    break;
            }
        }
    };
    private List<Comment> commentList;

    @Override
    public int getLayout() {
        return R.layout.activity_information_detail;
    }
    @Override
    public void init(Bundle savedInstanceState) {
        content_id=getIntent().getStringExtra("content_id");
        Log.e("content_id", content_id);
        initView();
        setListener();
        if (isNetworkConnected(this)){
            if (!TextUtils.isEmpty(content_id)) {
                if (dialog == null) {
                    dialog = new Dialog(this);
                    dialog.show();
                    dialog.setContentView(R.layout.loading_dialog);

                    informationDetailBiz = new InformationDetailBiz();
                    commentListBiz=new CommentListBiz();

                    if (TApplication.user_id == null||TextUtils.isEmpty(TApplication.user_id)) {
                        informationDetailBiz.getDetailInformation(content_id, "0", JPushInterface.getRegistrationID(this), TAG, this);
                        commentListBiz.getCommentList(content_id, "0", 0, TAG, this);
                    }else {
                        informationDetailBiz.getDetailInformation(content_id,TApplication.user_id, JPushInterface.getRegistrationID(this),TAG,this);
                        commentListBiz.getCommentList(content_id, TApplication.user_id, 0, TAG, this);
                    }

                }
            }
        }else {
            showToast("网络无连接");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        luPinModel = new LuPinModel();
        luPinModel.setName(InformationDetailActivity.class.getSimpleName());
        SimpleDateFormat sdf = new SimpleDateFormat();
        start_time = sdf.format(new Date());
        startsecrond = System.currentTimeMillis();
        luPinModel.setOperationtime(sdf.format(new Date()));
        luPinModel.setType("page");
        StatService.onResume(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        luPinModel.setEndtime(sdf.format(new Date()));
        TApplication.luPinModels.add(luPinModel);
        String end_time = sdf.format(new Date());
        long endsecrond = System.currentTimeMillis();
        final String read_time = (endsecrond-startsecrond)/1000+"";
        InformationRecordBiz informationRecordBiz = new InformationRecordBiz();
        informationRecordBiz.getinformationrecord(content_id, start_time, end_time, read_time, TApplication.city_name, JPushInterface.getRegistrationID(this), new RequestListener() {
            @Override
            public void onResponse(Response response) {
                if(response.isSuccessful()){
                    Gson gson = new Gson();
                    try{
                        String json = response.body().string();
                        Log.i("json",json);
                        com.mb.mmdepartment.bean.informationcollaction.informationrecord.Root root =
                                gson.fromJson(json, com.mb.mmdepartment.bean.informationcollaction.informationrecord.Root.class);
                        if(root.getStatus() == 0){
                            Log.i("tag","record");
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailue(Request request, IOException e) {
                e.printStackTrace();
            }
        });
        StatService.onPause(this);
    }

    private void setListener() {
        information_detail_more_comment_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(InformationDetailActivity.this,CommentListActivity.class,"content_id",content_id);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TApplication.user != null || TApplication.user_id != null) {
                    String comment_et_text = comment_et.getText().toString();
                    if (TextUtils.isEmpty(comment_et_text)) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(InformationDetailActivity.this);
                        dialog.setTitle("提示");
                        dialog.setMessage("请输入评论内容");
                        dialog.show();
                        return;
                    } else {
                        commentBiz = new CommentBiz();
                        commentBiz.sendcomment(content_id, comment_et_text, TAG, new RequestListener() {
                            @Override
                            public void onResponse(Response response) {
                                if (response.isSuccessful()) {
                                    Gson gson = new Gson();
                                    try {
                                        String json = response.body().string();
                                        CommentBackRoot root = gson.fromJson(json, CommentBackRoot.class);
                                        if (root.getStatus()==0) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    CustomToast.show(InformationDetailActivity.this, "提示", "评论成功");
                                                }
                                            });
                                        }
                                    } catch (Exception e) {

                                    }

                                }
                            }

                            @Override
                            public void onFailue(Request request, IOException e) {
                                showToast("评论失败");
                            }
                        });
                    }
                }else {
                    startActivity(InformationDetailActivity.this, LoginActivity.class);
                }
            }
        });
        comment_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if( TApplication.user_id == null){
                    startActivity(InformationDetailActivity.this,LoginActivity.class);
                }
            }
        });
    }

    private void initView() {
        information_detai_recycle = (RecyclerView) findViewById(R.id.information_detai_recycle);
        information_detail_web = (WebView) findViewById(R.id.information_detail_web);
        information_detail_more_comment_iv = (TextView) findViewById(R.id.information_detail_more_comment_iv);
        information_detail_title_tv = (TextView) findViewById(R.id.information_detail_title_tv);
        information_detail_author_tv = (TextView) findViewById(R.id.information_detail_time_tv);
        information_detail_time_tv = (TextView) findViewById(R.id.information_detail_time_tv);
        send = (ImageView)findViewById(R.id.send_comment);
        comment_et = (EditText)findViewById(R.id.comment_et);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_information_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.share:

                break;
            case R.id.collect:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setHomeButtonEnabled(isTrue);
        action.setTitle("资讯详情");
    }

    @Override
    public void onResponse(Response response) {
        if (response.isSuccessful()) {
            Gson gson = new Gson();
            try {
                String json = response.body().string();
                json=json.replace(" ","");
                Log.e("information_detail", json);
                Root root = gson.fromJson(json, Root.class);
                if (root.getStatus() == OkHttp.NET_STATE) {
                    content = root.getData().getContent();
                    handler.sendEmptyMessage(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onFailue(Request request, IOException e) {

    }

    @Override
    public void onMakeCommentResponse(Response response) {
        if (response.isSuccessful()) {
            Gson gson = new Gson();
            try {

                String json = response.body().string();
                json=json.replace(" ","");
                Log.e("commentlist",json);
                root=gson.fromJson(json, CommentRoot.class);
                if (root.getStatus()==OkHttp.NET_STATE) {
                    commentList = root.getData().getComment();
                    if (commentList == null) {
                        handler.sendEmptyMessage(10);
                    } else {
                        if (commentList.size() == 1) {
                            list.add(commentList.get(0));
                        } else {
                            list.add(commentList.get(0));
                            list.add(commentList.get(1));
                        }
                        adapter = new InformationCommentAdapter(list, false);
                        handler.sendEmptyMessage(2);
                    }
                }else {

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onMakeCommentFailue(Request request, IOException e) {

    }


    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(view, url);
            // html加载完成之后，添加监听图片的点击js函数
//            addImageClickListner();

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }


}
