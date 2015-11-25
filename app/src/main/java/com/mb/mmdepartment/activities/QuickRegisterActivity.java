package com.mb.mmdepartment.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
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
import com.mb.mmdepartment.bean.regist.code.Root;
import com.mb.mmdepartment.bean.user.User;
import com.mb.mmdepartment.biz.regist.GetCodeBiz;
import com.mb.mmdepartment.biz.regist.signquick.SignQuickBIz;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.tools.log.Log;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tencent.stat.StatService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.jpush.android.api.JPushInterface;

public class QuickRegisterActivity extends BaseActivity implements View.OnClickListener{

    private EditText phone_ev;
    private EditText ver_code_ev;
    private TextView get_ver_code;
    private TextView login_register;
    private LuPinModel luPinModel;
    private MyCount mc;
    @Override
    public void init(Bundle savedInstanceState) {
        initView();
        initData();
        setListener();
    }

    private void initView(){
        phone_ev = (EditText)findViewById(R.id.quick_phone_ev);
        ver_code_ev = (EditText)findViewById(R.id.quick_get_ver_ev);
        get_ver_code = (TextView)findViewById(R.id.get_verification_code);
        login_register = (TextView)findViewById(R.id.quick_register);
    }

    private void initData(){
        mc = new MyCount(60000, 1000);
    }

    private void setListener(){
        get_ver_code.setOnClickListener(this);
        login_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.get_verification_code:
                String phone_number = phone_ev.getText().toString().trim();
                if (!checkPhone(phone_number)){
//                    alertDialog("手机号码不正确");
                    return;
                }
                mc.start();
                get_ver_code.setClickable(false);
                get_ver_code.setBackgroundColor(Color.GRAY);
                GetCodeBiz biz = new GetCodeBiz();
                biz.getCode(phone_ev.getText().toString(), new RequestListener() {
                    @Override
                    public void onResponse(Response response) {
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
                });
                break;
            case R.id.quick_register:
                SignQuickBIz quickBIz = new SignQuickBIz();
                quickBIz.signquick(phone_ev.getText().toString(), ver_code_ev.getText().toString(),
                        JPushInterface.getRegistrationID(this), new RequestListener() {
                            @Override
                            public void onResponse(Response response) {
                                if(response.isSuccessful()) {
                                    try {
                                        Gson gson = new Gson();
                                        String json = response.body().string();
                                        Log.i("json",json);
                                        if(json.contains("\"error\": \"\\u624b\\u673a\\u7528\\u6237\\u4e0d\\u5b58\\u5728\",")){
                                            Intent intent = new Intent(QuickRegisterActivity.this,FastRegistActivity.class);
                                            intent.putExtra("phone",phone_ev.getText().toString());
                                            intent.putExtra("code",ver_code_ev.getText().toString());
                                            startActivity(intent);
                                        }else if(json.contains("\"error\": \"\\u767b\\u5f55\\u6210\\u529f\",")){
                                            com.mb.mmdepartment.bean.login.quicklogin.Root root =
                                                    gson.fromJson(json, com.mb.mmdepartment.bean.login.quicklogin.Root.class);
                                            User user = new User();
                                            user.setUsername(root.getData().getUsername());
                                            user.setUserid(root.getData().getUserid());
                                            user.setNickname(root.getData().getNickname());
                                            user.setPhone(root.getData().getPhone());
                                            TApplication.user = user;
                                            TApplication.user_id = user.getUserid();
                                            TApplication.user_name = user.getNickname();
                                            startActivity(QuickRegisterActivity.this, MainPageActivity.class);
                                        }else if(json.contains("\"error\": \"\\u624b\\u673a\\u53f7\\u6216\\u9a8c\\u8bc1\\u7801\\u4e0d\\u6b63\\u786e\",")){
                                            alertDialog("手机号或验证码不正确");
                                        }

                                    }catch (Exception e){}
                                }
                            }

                            @Override
                            public void onFailue(Request request, IOException e) {

                            }
                        });
                break;
        }
    }

    private void alertDialog(String msg) {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle(msg);
        dialog.show();

    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("快速登录/注册");
        action.setHomeButtonEnabled(isTrue);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_quick_register;
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

    class MyCount extends CountDownTimer {

        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            System.out.println("你好");
        }
        @Override
        public void onTick(long millisUntilFinished) {
            Date date = new Date(millisUntilFinished);
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            String str = sdf.format(date);
            System.out.println(str);
            get_ver_code.setText("重新发送"+millisUntilFinished / 1000 +"秒");
        }
        @Override
        public void onFinish() {
            get_ver_code.setClickable(true);
            get_ver_code.setBackgroundColor(Color.rgb(255,121,76));
            get_ver_code.setText("获取验证码");
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mc.cancel();
        TApplication.activities.remove(this);
    }

}
