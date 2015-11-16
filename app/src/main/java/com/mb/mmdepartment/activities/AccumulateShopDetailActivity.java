package com.mb.mmdepartment.activities;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.bean.accumulate.detail.Root;
import com.mb.mmdepartment.bean.accumulate.detail.Info;
import com.mb.mmdepartment.biz.accumulate.AccumulateDetailBiz;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.view.LoadingDialog;
import com.tencent.stat.StatService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AccumulateShopDetailActivity extends BaseActivity implements RequestListener{
    private final String TAG = AccumulateShopDetailActivity.class.getSimpleName();
    private TextView accumulate_detail_title_tv,accumulate_detail_time_tv,accumulate_detail_accumulate_tv,accumulate_detail_exchange_tv,accumulate_detail_description_content_tv,accumulate_detail_activity_rool_content_tv,accumulate_detail_change_content_tv;
    private ImageView accumulate_detail_iv;
    private AccumulateDetailBiz biz;
    private LoadingDialog dialog;
    private Info info;
    private  Spanned sp;
    private LuPinModel luPinModel;
    private WebView accumulate_detail_content_webview;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    if (dialog!=null){
                        dialog.dismiss();
                    }
                    ImageLoader.getInstance().displayImage(info.getImage_default(), accumulate_detail_iv);
                    accumulate_detail_title_tv.setText(info.getTitle());
                    accumulate_detail_time_tv.append(info.getExchange_time());
                    accumulate_detail_accumulate_tv.setText(info.getExchange_integral());
                    accumulate_detail_description_content_tv.setText(info.getSummary());
                    accumulate_detail_activity_rool_content_tv.setText(info.getRule());
                    accumulate_detail_change_content_tv.setText(info.getExchange_note());

                    accumulate_detail_content_webview.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8
                    accumulate_detail_content_webview.loadData(info.getContentbody(), "text/html;charset=UTF-8", null);//这种写法可以正确解码
                    break;
                case 1:
                    showToast("网络异常");
                    break;
            }
        }
    };

    @Override
    public int getLayout() {
        return R.layout.activity_accumulate_shop_detail;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        String content_id=getIntent().getStringExtra("content_id");
        if (content_id==null||"".equals(content_id)){
            showToast("请求出错");
        }
        initView();
        setListener();
        if (isNetworkConnected(this)){
            if (dialog==null) {
                dialog = new LoadingDialog(this,R.style.dialog);
                biz = new AccumulateDetailBiz();
                biz.getDetail(content_id, TAG, this);
                dialog.show();
            }
        }else {
            showToast("网络链接异常");
        }
    }

    private void setListener() {
        accumulate_detail_exchange_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        luPinModel = new LuPinModel();
        luPinModel.setName(AccumulateShopDetailActivity.class.getSimpleName());
        luPinModel.setType("page");
        luPinModel.setState("end");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm;ss");
        luPinModel.setOperationtime(sdf.format(new Date()));
        StatService.onResume(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        luPinModel.setEndtime(sdf.format(new Date()));
        TApplication.luPinModels.add(luPinModel);
        StatService.onPause(this);
    }

    private void initView() {
        accumulate_detail_title_tv=(TextView)findViewById(R.id.accumulate_detail_title_tv);
        accumulate_detail_time_tv=(TextView)findViewById(R.id.accumulate_detail_time_tv);
        accumulate_detail_accumulate_tv=(TextView)findViewById(R.id.accumulate_detail_accumulate_tv);
        accumulate_detail_exchange_tv=(TextView)findViewById(R.id.accumulate_detail_exchange_tv);
        accumulate_detail_description_content_tv=(TextView)findViewById(R.id.accumulate_detail_description_content_tv);
        accumulate_detail_activity_rool_content_tv=(TextView)findViewById(R.id.accumulate_detail_activity_rool_content_tv);
        accumulate_detail_change_content_tv=(TextView)findViewById(R.id.accumulate_detail_change_content_tv);
        accumulate_detail_iv = (ImageView) findViewById(R.id.accumulate_detail_iv);
        accumulate_detail_content_webview = (WebView) findViewById(R.id.accumulate_detail_content_webview);
    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("商品详情");
        action.setHomeButtonEnabled(isTrue);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(Response response) {
        if (response.isSuccessful()) {
            Gson gson=new Gson();
            try {
                String json = response.body().string();
                Root root = gson.fromJson(json, Root.class);
                if (root.getStatus() == OkHttp.NET_STATE) {
                    info = root.getData().getInfo();
                    handler.sendEmptyMessage(0);
                }else {
                    handler.sendEmptyMessage(1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailue(Request request, IOException e) {

    }
}
