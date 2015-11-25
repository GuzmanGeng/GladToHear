package com.mb.mmdepartment.activities;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.adapter.accumulate.AccumulateShopAdapter;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.accumulate.Product;
import com.mb.mmdepartment.bean.accumulate.Root;
import com.mb.mmdepartment.bean.setting.MyScoreRoot;
import com.mb.mmdepartment.biz.accumulate.AccumulateShopBiz;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.constans.CatlogConsts;
import com.mb.mmdepartment.listener.AccumulateShopItemClickListener;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.view.LoadingDialog;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyScoreActivity extends BaseActivity implements RequestListener,AccumulateShopItemClickListener {
    private RecyclerView recyclerView;
    private LoadingDialog dialog;
    private TextView tv_name,tv_month_score,tv_total_score;
    private AccumulateShopBiz biz;
    private GridLayoutManager gridLayoutManager;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (!TextUtils.isEmpty(month_score)) {
                        tv_month_score.setText(month_score);
                    } else {
                        tv_month_score.setText("0积分");
                    }
                    tv_name.setText(TApplication.user_name);
                    if (!TextUtils.isEmpty(total_score)) {
                        tv_total_score.setText(total_score);
                    } else {
                        tv_total_score.setText("0积分");
                    }
                    break;
                case 1:
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    showToast("服务器在维护,请等待！");
                    break;
                case 2:
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    showToast("网络异常,请检查网络后重试!");
                    break;
                case 3:
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    gridLayoutManager = new GridLayoutManager(MyScoreActivity.this,2);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setAdapter(adapter);
                    break;
            }
        }
    };
    private String month_score;
    private String total_score;
    private AccumulateShopAdapter adapter;

    @Override
    public int getLayout() {
        return R.layout.activity_my_score;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initView();
        initData();
    }

    private void initView() {
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_month_score = (TextView) findViewById(R.id.tv_month_score);
        tv_total_score = (TextView) findViewById(R.id.tv_total_score);
        recyclerView=(RecyclerView)findViewById(R.id.my_score_recycle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TApplication.activities.remove(this);
    }

    private void initData() {
        if (dialog==null) {
            dialog = new LoadingDialog(this, R.style.dialog);
            dialog.show();
            biz=new AccumulateShopBiz();
            biz.getProductsList(38, 1, "desc", TAG, this);
        }
        Map<String, String> paramas = new HashMap<>();
        paramas.put(BaseConsts.APP, CatlogConsts.ScoreSearch.params_app);
        paramas.put(BaseConsts.CLASS, CatlogConsts.ScoreSearch.params_class);
        paramas.put(BaseConsts.SIGN, CatlogConsts.ScoreSearch.params_sign);
        paramas.put("userid", TApplication.user_id);
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(2);
            }
            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String json = response.body().string();
                        Gson gson = new Gson();
                        MyScoreRoot root = gson.fromJson(json, MyScoreRoot.class);
                        if (root.getStatus() == 0) {
                            month_score = root.getData().getSum1();
                            total_score = root.getData().getSum5();
                            handler.sendEmptyMessage(0);
                        } else {
                            handler.sendEmptyMessage(1);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("积分查询");
        action.setHomeButtonEnabled(isTrue);
    }

    @Override
    public void onResponse(Response response) {
        if (response.isSuccessful()) {
            Gson gson = new Gson();
            try {
                String json = response.body().string();
                json=json.replaceAll(" ", "");
                Root root = gson.fromJson(json, Root.class);
                if (root.getStatus() == 0) {
                    List<Product> list = root.getData().getProducts();
                    adapter = new AccumulateShopAdapter(list,this);
                    handler.sendEmptyMessage(3);
                } else {
                    handler.sendEmptyMessage(1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onFailue(Request request, IOException e) {
        handler.sendEmptyMessage(2);
    }

    @Override
    public void onItemClick(View view, String content_id) {
       startActivity(this, AccumulateShopDetailActivity.class, "content_id", content_id);
    }
}
