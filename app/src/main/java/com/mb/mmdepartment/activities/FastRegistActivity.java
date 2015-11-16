package com.mb.mmdepartment.activities;

import android.app.AlertDialog;
import android.content.Intent;
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
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.mb.mmdepartment.bean.regist.code.regist.Roots;
import com.mb.mmdepartment.biz.regist.regist.RegistBiz;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.listener.RegistResponse;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.tools.sp.SPCache;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FastRegistActivity extends BaseActivity implements View.OnClickListener,RegistResponse {
    private final String TAG = FastRegistActivity.class.getSimpleName();
    private EditText username;
    private EditText password;
    private EditText password1;
    private TextView regist;
    private String phone;
    private String code;
    private RegistBiz biz;
    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("完善个人信息");
        action.setHomeButtonEnabled(isTrue);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_fast_regist;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initview();
        initdata();
        setlistener();
    }

    private void initview(){
        username = (EditText)findViewById(R.id.fast_regist_username_ed);
        password = (EditText)findViewById(R.id.fast_regist_password_ed);
        password1 = (EditText)findViewById(R.id.fast_regist_again_password_ed);
        regist = (TextView)findViewById(R.id.fastregist);
    }

    private void initdata(){
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        code = intent.getStringExtra("code");
    }

    private void setlistener(){
        regist.setOnClickListener(this);
        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    biz = new RegistBiz();
                    if (!"".equals(username.getText().toString()) && null != username.getText().toString()) {
                        biz.checkname(username.getText().toString(), TAG, new RequestListener() {
                            @Override
                            public void onResponse(Response response) {
                                if (response.isSuccessful()) {
                                    Gson gson = new Gson();

                                    try {
                                        String json = response.body().string();
                                        final com.mb.mmdepartment.bean.regist.code.checkname.Root root =
                                                gson.fromJson(json, com.mb.mmdepartment.bean.regist.code.checkname.Root.class);
                                        if (root.getStatus() == 1) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    alertDialog(root.getError());
                                                }
                                            });
                                        }
                                    } catch (Exception e) {
                                    }
                                }
                            }

                            @Override
                            public void onFailue(Request request, IOException e) {

                            }
                        });
                    } else {
                        runOnUiThread(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        showToast("用户名不能为空");
                                    }
                                }
                        );

                    }
                }
            }
        });
    }

    /**
     * 注册的返回
     * @param response
     */
    @Override
    public void onResponseSuccess(Response response) {
        if (response.isSuccessful()){
            Gson  gson=new Gson();
            try {
                String json = response.body().string();
                Roots roots = gson.fromJson(json, Roots.class);
                if (roots.getStatus()== OkHttp.NET_STATE){
                    TApplication.token = roots.getData().getPassword();
                    // 保存SP
                    SPCache.putString(BaseConsts.SharePreference.USER_TOKEN, roots.getData().getPassword());
                    SPCache.putString(BaseConsts.SharePreference.USER_ID, roots.getData().getUserid());
                    SPCache.putString(BaseConsts.SharePreference.USER_NAME, roots.getData().getUsername());
                    TApplication.user_id =roots.getData().getUserid();
                    TApplication.user_name=roots.getData().getUsername();
                    startActivity(FastRegistActivity.this, LoginActivity.class);
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("注册失败，服务器在维护，请稍后尝试。");
                        }
                    });

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailueRequess(Request request, IOException e) {
        showToast("网络链接异常");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fastregist:
                LuPinModel luPinModel_register = new LuPinModel();
                luPinModel_register.setName("registerbutton");
                luPinModel_register.setState("selected");
                luPinModel_register.setType("next");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                luPinModel_register.setOperationtime(sdf.format(new Date()));
                TApplication.luPinModels.add(luPinModel_register);
                biz = new RegistBiz();
                biz.regist(username.getText().toString(), password.getText().toString(),
                        password1.getText().toString(), phone, code, this);
                break;
        }
    }

    private void alertDialog(String msg) {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle(msg);
        dialog.show();

    }

    /**
     * 确定所有的输入是否完整
     * @param username
     * @param password
     * @param again_pass
     * @param tel_num
     * @param code
     * @return
     */
    private boolean checkUserState(String username, String password, String again_pass, String tel_num, String code) {
        if (TextUtils.isEmpty(username)){
            showToast("用户名不能为空");
            return false;
        }
        if (TextUtils.isEmpty(password)){
            showToast("密码不能为空");
            return false;
        }
        if (TextUtils.isEmpty(again_pass)){
            showToast("密码不能为空");
            return false;
        }
        if (!again_pass.equals(password)){
            showToast("密码不一致请检查");
            return false;
        }

        return true;
    }



}
