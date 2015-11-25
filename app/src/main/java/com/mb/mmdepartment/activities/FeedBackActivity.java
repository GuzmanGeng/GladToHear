package com.mb.mmdepartment.activities;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.setting.FeedBackRoot;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.constans.CatlogConsts;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.tools.sp.SPCache;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FeedBackActivity extends BaseActivity {
    private EditText ed_feed_back;
    private TextView tv_feed_back;
    private ProgressDialog dialog;
    @Override
    public int getLayout() {
        return R.layout.activity_feed_back;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initView();
        setListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TApplication.activities.remove(this);
    }

    private void setListener() {
        tv_feed_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = ed_feed_back.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    showToast("请输入意见在提交");
                    return;
                }
                dialog = new ProgressDialog(FeedBackActivity.this,R.style.Translucent_NoTitle);
                dialog.setMessage("正在上传");
                dialog.show();
                Map<String, String> paramas = new HashMap<>();
                paramas.put(BaseConsts.APP, CatlogConsts.FeedBack.params_app);
                paramas.put(BaseConsts.CLASS, CatlogConsts.FeedBack.params_class);
                paramas.put(BaseConsts.SIGN, CatlogConsts.FeedBack.params_app);
                paramas.put("userid", TApplication.user_id);
                paramas.put("title", "意见反馈");
                paramas.put("label", "android");
                paramas.put("body", content);
                paramas.put("attachment", "0");
                paramas.put("location", SPCache.getString("city_id","50"));
                paramas.put("tag_name", "意见反馈");
                OkHttp.asyncPost(BaseConsts.BASE_URL, paramas, new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }
                    @Override
                    public void onResponse(Response response) throws IOException {
                        if (response.isSuccessful()) {
                            Gson gson = new Gson();
                            String json = response.body().string();
                            FeedBackRoot root = gson.fromJson(json, FeedBackRoot.class);
                            if (root.getStatus() == 0) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (dialog != null) {
                                            dialog.dismiss();
                                        }
                                        showToast("上传成功");
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (dialog != null) {
                                            dialog.dismiss();
                                        }
                                        showToast("上传失败,请检查网络");
                                    }
                                });
                            }
                        }
                    }
                });
            }
        });
    }

    private void initView() {
        ed_feed_back = (EditText) findViewById(R.id.ed_feed_back);
        tv_feed_back = (TextView) findViewById(R.id.tv_feed_back);
    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setHomeButtonEnabled(isTrue);
        action.setTitle("意见反馈");
    }
}
