package com.mb.mmdepartment.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.mb.mmdepartment.constans.CatlogConsts;
import com.mb.mmdepartment.tools.log.Log;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.regist.code.Root;
import com.mb.mmdepartment.bean.regist.code.regist.Roots;
import com.mb.mmdepartment.biz.regist.GetCodeBiz;
import com.mb.mmdepartment.biz.regist.regist.RegistBiz;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.listener.RegistResponse;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.tools.sp.SPCache;
import com.mb.mmdepartment.view.LoadingDialog;
import com.tencent.stat.StatService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.jpush.android.d;

public class RegistActivity extends BaseActivity implements RequestListener,RegistResponse,View.OnClickListener{
    private final String TAG=RegistActivity.class.getSimpleName();
    private EditText ed_username;
    private EditText ed_password;
    private EditText ed_password_again;
    private EditText ed_phone_number;
    private EditText ed_code;
    private TextView tv_getcode;
    private TextView tv_regist;
    private String username;
    private String password;
    private LoadingDialog dialog;
    private LuPinModel luPinModel;
    private MyCount mc;
    @Override
    public int getLayout() {
        return R.layout.activity_regist;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initView();
        setListener();
    }

    private void setListener() {
        tv_getcode.setOnClickListener(this);
        tv_regist.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        luPinModel = new LuPinModel();
        luPinModel.setName("register");
        luPinModel.setType("page");
        luPinModel.setState("end");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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

    /**
     * 检查手机号码输入状态
     * @param phone_number
     * @return
     */
    private boolean checkPhone(String phone_number) {
        if (TextUtils.isEmpty(phone_number)){
            showToast("手机号码不能为空");
            return false;
        }
        if (!isMobileNo(phone_number)){
            showToast("请检查手机格式是否正确");
            return false;
        }
        return true;
    }
    /**
     * 检查手机号是否正确
     * @param phone
     * @return
     */
    public static boolean isMobileNo(String phone) {
        String match = "^((13|15|18|17|14)\\d{9})|147\\d{8}$";
        Pattern pattern = Pattern.compile(match);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    /**
     * 初始化view
     */
    private void initView() {
        ed_username = (EditText) findViewById(R.id.regist_username_ed);
        ed_password = (EditText) findViewById(R.id.regist_password_ed);
        ed_password_again = (EditText) findViewById(R.id.regist_again_password_ed);
        ed_phone_number = (EditText) findViewById(R.id.regist_photo_number_ed);
        ed_code = (EditText) findViewById(R.id.regist_code_ed);
        tv_getcode=(TextView)findViewById(R.id.regist_getcode_tv);
        tv_regist=(TextView)findViewById(R.id.regist_regist_tv);
        if (dialog==null) {
            dialog = new LoadingDialog(this, R.style.dialog);
        }
        ed_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    Log.i("test",ed_username.getText().toString());
                    RegistBiz biz = new RegistBiz();
                    biz.checkname(ed_username.getText().toString(), RegistActivity.class.getSimpleName(), new RequestListener() {
                        @Override
                        public void onResponse(Response response) {
                            if(response.isSuccessful()){
                                Gson gson = new Gson();

                                try{
                                    String json = response.body().string();
                              final  com.mb.mmdepartment.bean.regist.code.checkname.Root root =
                                        gson.fromJson(json, com.mb.mmdepartment.bean.regist.code.checkname.Root.class);
                                    if(root.getStatus()==1){
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                alertDialog(root.getError());
                                            }
                                        });
                                    }
                                }catch (Exception e){}
                            }
                        }

                        @Override
                        public void onFailue(Request request, IOException e) {

                        }
                    });
                }
            }
        });
    }

    private void alertDialog(String msg) {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle(msg);
        dialog.show();

    }


    /**
     * 设置toolbar
     * @param action
     * @param isTrue
     */
    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("注册");
        action.setHomeButtonEnabled(isTrue);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_regist, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.regist_login:
                LuPinModel luPinModel_register = new LuPinModel();
                luPinModel_register.setName("registerbutton");
                luPinModel_register.setState("selected");
                luPinModel_register.setType("next");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                luPinModel_register.setOperationtime(sdf.format(new Date()));
                TApplication.luPinModels.add(luPinModel_register);
                startActivity(RegistActivity.this, LoginActivity.class);
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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
                if (roots.getStatus()==OkHttp.NET_STATE){
                    TApplication.token = roots.getData().getPassword();
                    // 保存SP
                    SPCache.putString(BaseConsts.SharePreference.USER_TOKEN,roots.getData().getPassword());
                    SPCache.putString(BaseConsts.SharePreference.USER_ID, roots.getData().getUserid());
                    SPCache.putString(BaseConsts.SharePreference.USER_NAME, roots.getData().getUsername());
                    SPCache.putString(BaseConsts.SharePreference.USER_PASS, password);
                    TApplication.user_id =roots.getData().getUserid();
                    TApplication.user_name=roots.getData().getUsername();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("注册成功");
                        }
                    });
                    finish();
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("用户名或手机号码已被注册.");
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToast("网络链接异常");
            }
        });
    }

    /**
     * 验证码的返回
     * @param response
     */
    @Override
    public void onResponse(Response response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        if (response.isSuccessful()){
            Gson gson=new Gson();
            try {
                String json=response.body().string();
                Root root = gson.fromJson(json, Root.class);
                if (root.getStatus()!= OkHttp.NET_STATE){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("验证码获取失败");
                        }
                    });

                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("验证码已发送,请注意查收");
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailue(Request request, IOException e) {
        showToast("网络链接异常");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.regist_getcode_tv:
                String phone_number = ed_phone_number.getText().toString().trim();
                if (!checkPhone(phone_number)) return;
                GetCodeBiz biz=new GetCodeBiz();
                biz.getCode(phone_number,RegistActivity.this);
                mc = new MyCount(60000,1000);
                mc.start();
                break;
            case R.id.regist_regist_tv:
                username=ed_username.getText().toString().trim();
                password=ed_password.getText().toString().trim();
                final String again_pass=ed_password_again.getText().toString().trim();
                final String tel_num=ed_phone_number.getText().toString().trim();
                final String code=ed_code.getText().toString().trim();
                if (checkUserState(username,password,again_pass,tel_num,code)) {
                    if (dialog != null) {
                        dialog.show();
                    }
                    RegistBiz registBiz = new RegistBiz();
                    registBiz.regist(username, password, again_pass, tel_num, code, this);
                }
                break;
        }
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
        if (TextUtils.isEmpty(username)){
            showToast("用户名不能为空");
            return false;
        }
        if (!checkPhone(tel_num)){
            return false;
        }
        if (TextUtils.isEmpty(code)){
            showToast("验证码不能为空");
            return false;
        }
        return true;
    }

    class MyCount extends CountDownTimer {

        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            Date date = new Date(millisUntilFinished);
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            String str = sdf.format(date);
            System.out.println(str);
            tv_getcode.setText("重新发送"+millisUntilFinished / 1000 +"秒");
        }
        @Override
        public void onFinish() {
            tv_getcode.setClickable(true);
            tv_getcode.setBackgroundColor(Color.rgb(255, 121, 76));
            tv_getcode.setText("获取验证码");
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mc != null) {
            mc.cancel();
        }
    }
}
