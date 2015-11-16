package com.mb.mmdepartment.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.mb.mmdepartment.bean.user.User;
import com.mb.mmdepartment.wxapi.WXEntryActivity;
//import com.sina.weibo.sdk.auth.AuthInfo;
//import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.stat.StatService;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.login.Root;
import com.mb.mmdepartment.biz.login.LoginBiz;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.constans.LoginConsts;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.tools.sp.SPCache;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.jpush.android.api.JPushInterface;

public class LoginActivity extends BaseActivity implements View.OnClickListener,RequestListener{
    //输入框
    private EditText login_ed_phone,login_ed_pass;
    //登录 注册 忘记密码
    private TextView login_tv_login,login_tv_forget_pass,login_tv_regist;
    // 第三方登录 qq,微信,新浪
    private TextView login_tv_qq,login_tv_weixin,login_tv_sina;

    private IWXAPI api;

//    private SsoHandler blogSsoHandler;
//
//    private AuthInfo blogAuthInfo;

    String phone_number,password;
    private Toolbar toolbar;
    private final String TAG=LoginActivity.class.getSimpleName();
    private boolean login;
    private Tencent mTencent ;
    private String type;
    private LuPinModel luPinModel;
    private String token,openId,expires;
    private String username;
    private MyIUListener listener;
    public Handler handlerInstance=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 2: User user = (User)msg.obj;
                    username = user.getUsername();
                    openId = user.getToken();
            }
            handleLogin(openId,username);
        }
    };
    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        login = getIntent().getBooleanExtra("login", false);
        initView();
        setListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        luPinModel = new LuPinModel();
        luPinModel.setName("login");
        luPinModel.setState("end");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
        StatService.onPause(this);
    }

    @Override
    protected void setToolBar(ActionBar action,boolean isTrue) {
        action.setTitle("登录");
        action.setHomeButtonEnabled(isTrue);
    }
    private void setListeners() {
        login_tv_login.setOnClickListener(this);
        login_tv_forget_pass.setOnClickListener(this);
        login_tv_regist.setOnClickListener(this);
        login_tv_qq.setOnClickListener(this);
        login_tv_weixin.setOnClickListener(this);
        login_tv_sina.setOnClickListener(this);
//        login_ed_phone.setOnKeyListener(this);
//        login_ed_pass.setOnKeyListener(this);
    }

    private void initView() {
//        blogAuthInfo = new AuthInfo(this, LoginConsts.Account.SinaLogin.APP_KEY_FOR_BLOG,
//                LoginConsts.Account.SinaLogin.REDIRECT_URL, LoginConsts.Account.SinaLogin.SCOPE);
//        blogSsoHandler = new SsoHandler(LoginActivity.this, blogAuthInfo);
        login_ed_phone=(EditText)findViewById(R.id.login_ed_phone);
        login_ed_pass=(EditText)findViewById(R.id.login_ed_pass);
        login_tv_login=(TextView)findViewById(R.id.login_tv_login);
        login_tv_forget_pass=(TextView)findViewById(R.id.login_tv_forget_pass);
        login_tv_regist=(TextView)findViewById(R.id.login_tv_regist);
        login_tv_qq=(TextView)findViewById(R.id.login_tv_qq);
        login_tv_weixin=(TextView)findViewById(R.id.login_tv_weixin);
        login_tv_sina=(TextView)findViewById(R.id.login_tv_sina);
        login_tv_forget_pass.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        login_tv_regist.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mTencent = Tencent.createInstance(LoginConsts.Account.QQLogin.QQ_APP_KEY, this);
        regToWx();
        listener=new MyIUListener();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onClick(View view) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        switch (view.getId()){
            case R.id.login_tv_login:
                LuPinModel luPinModellogin = new LuPinModel();
                luPinModellogin.setState("selected");
                luPinModellogin.setName("loginbutton");
                luPinModellogin.setType("next");
                luPinModellogin.setOperationtime(sdf.format(new Date()));
                TApplication.luPinModels.add(luPinModellogin);
                if (!isNetworkConnected(this)){
                    showToast("网络无连接");
                    return;
                }
                switch (foxMessage()){
                    case 0:
                        LoginBiz biz=new LoginBiz();
                        biz.login(phone_number,password,JPushInterface.getRegistrationID(this),this);
                        break;
                    case 1:
                        Toast.makeText(LoginActivity.this,"请检查手机号格式。",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(LoginActivity.this,"密码格式错误。",Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(LoginActivity.this,"账号或密码有误。",Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(LoginActivity.this,"账号或密码有误。",Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        Toast.makeText(LoginActivity.this,"手机号不能为空。",Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        Toast.makeText(LoginActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            case R.id.login_tv_forget_pass:
                LuPinModel luPinModelforgetpsw = new LuPinModel();
                luPinModelforgetpsw.setState("selected");
                luPinModelforgetpsw.setName("registerbutton");
                luPinModelforgetpsw.setType("next");
                luPinModelforgetpsw.setOperationtime(sdf.format(new Date()));
                TApplication.luPinModels.add(luPinModelforgetpsw);
                startActivity(LoginActivity.this,ForgetPasswordPageActivity.class);
                break;
            case R.id.login_tv_regist:
                LuPinModel luPinModelregister = new LuPinModel();
                luPinModelregister.setState("selected");
                luPinModelregister.setName("registerbutton");
                luPinModelregister.setType("next");
                luPinModelregister.setOperationtime(sdf.format(new Date()));
                TApplication.luPinModels.add(luPinModelregister);
                startActivity(LoginActivity.this,RegistActivity.class);
                break;
            case R.id.login_tv_qq:
                LuPinModel luPinModelloginqq = new LuPinModel();
                luPinModelloginqq.setState("selected");
                luPinModelloginqq.setName("loginqqbutton");
                luPinModelloginqq.setType("next");
                luPinModelloginqq.setOperationtime(sdf.format(new Date()));
                TApplication.luPinModels.add(luPinModelloginqq);
                mTencent.login(this, "all",listener);
                break;
            case R.id.login_tv_weixin:
                LuPinModel luPinModelloginweixin = new LuPinModel();
                luPinModelloginweixin.setState("selected");
                luPinModelloginweixin.setName("loginweixinbutton");
                luPinModelloginweixin.setType("next");
                luPinModelloginweixin.setOperationtime(sdf.format(new Date()));
                TApplication.luPinModels.add(luPinModelloginweixin);
                // 微信
                // Platform wechat= ShareSDK.getPlatform(context, Wechat.NAME);
                // wechat.setPlatformActionListener(paListener);
                // wechat.authorize();
                Log.i("weixin", "weixin");
//                regToWx();
                if (!api.isWXAppInstalled()) {
                    Toast.makeText(LoginActivity.this, "请先检查是否已安装微信",Toast.LENGTH_SHORT).show();
                    return;
                }

                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                //wx13bfc51c959a8c2e
//                req.transaction = "wx0d047246911bcdc5";
                req.state = "glad_to_hear";
                api.sendReq(req);

                WXEntryActivity.isFromShareDialog = false;
                break;
            case R.id.login_tv_sina:
                LuPinModel luPinModelloginsina = new LuPinModel();
                luPinModelloginsina.setState("selected");
                luPinModelloginsina.setName("loginweixinbutton");
                luPinModelloginsina.setType("next");
                luPinModelloginsina.setOperationtime(sdf.format(new Date()));
                TApplication.luPinModels.add(luPinModelloginsina);
                break;
        }
    }

    private void regToWx(){
        api = WXAPIFactory.createWXAPI(this, LoginConsts.Account.WXLogin.WEIXIN_APP_ID, true);
        api.registerApp( LoginConsts.Account.WXLogin.WEIXIN_APP_ID);
    }

    private void sendReq(){
        WXTextObject textObject = new WXTextObject();
        textObject.text = "gladtohear";
        WXMediaMessage msg = new WXMediaMessage();
        msg.description = "gladtohear";
        msg.mediaObject = textObject;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        api.sendReq(req);
    }

    @Override
    public void onResponse(Response response) {
         if (response.isSuccessful()) {
             Gson gson = new Gson();
             try {
                 String json=response.body().string();
                 final Root root = gson.fromJson(json, Root.class);
                 if (root.getStatus()==OkHttp.NET_STATE) {
                     SPCache.putString(BaseConsts.SharePreference.USER_ID, root.getData().getUserid());
                     SPCache.putString(BaseConsts.SharePreference.USER_NAME, root.getData().getUsername());
                     TApplication.user_id =root.getData().getUserid();
                     TApplication.user_name=root.getData().getUsername();
                     TApplication.device_no=root.getData().getDevice_no();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (login) {
                                setResult(RESULT_OK);
                                finish();
                            }else {
                                startActivity(LoginActivity.this, MainPageActivity.class);
                                finish();
                            }
                        }
                    });
                 }else{
                     runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             showToast(root.getError());
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
    /**
     * 登录监听接口
     */
    class MyIUListener implements IUiListener {
        JSONObject json;
        @Override
        public void onComplete(Object o) {
            if (null == o) {
                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                json=new JSONObject(o.toString());
                openId = ((JSONObject) o).getString("openid");
                token = json.getString(Constants.PARAM_ACCESS_TOKEN);
                Log.e("login_info",token);
                Log.e("login_json",json.toString());
                expires = json.getString(Constants.PARAM_EXPIRES_IN);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
            UserInfo info = new UserInfo(LoginActivity.this, mTencent.getQQToken());
            info.getUserInfo(new IUiListener() {
                @Override
                public void onError(UiError o) {
                    Toast.makeText(LoginActivity.this, "获取用户信息失败：" + o.errorDetail, Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onComplete(Object o) {
                    try {
                        username = new JSONObject(o.toString()).getString("nickname");
                        Log.i("login", new JSONObject(o.toString()).toString());
                        Message msg = new Message();
                        msg.what = 0;
                        msg.obj = type;
                        handlerInstance.sendMessage(msg);
                        Toast.makeText(LoginActivity.this,"username"+username,Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onCancel() {
                    Toast.makeText(LoginActivity.this, "授权取消",  Toast.LENGTH_SHORT).show();
                }
            });

        }
        @Override
        public void onError(UiError o) {
            Toast.makeText(LoginActivity.this, "授权错误：" + o.errorDetail, Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this, "授权取消",  Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data,new MyIUListener());
//        if (blogSsoHandler != null) {
//            blogSsoHandler.authorizeCallBack(requestCode, resultCode, data);
//        }

//		/*
//		 * 在某些低端机上调用登录后，由于内存紧张导致APP被系统回收，Tencent SDK登录成功后无法成功回传数据。
//		 */
//        if (requestCode == com.tencent.connect.common.Constants.REQUEST_API) {
//            if (resultCode == com.tencent.connect.common.Constants.RESULT_LOGIN) {
//                Tencent.handleResultData(data, new MyIUListener());
//            }
//        }
    }

    /**
     * 登录时的判断
     * @return
     */
    private int foxMessage() {
        int format=0;
        phone_number=login_ed_phone.getText().toString().trim();
        password=login_ed_pass.getText().toString().trim();
        if (TextUtils.isEmpty(phone_number)){
            format=LoginConsts.PHONE_EMPTY;
            return format;
        }
        if (TextUtils.isEmpty(password)){
            format=LoginConsts.PASS_EMPTY;
            return format;
        }
//        if (!isMobileNo(phone_number)){
//            format= LoginConsts.PHONE_FORMAT_ERROR;
//            return format;
//        }
        return format;
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
     * 第三方平台登录
     *
     * @param token
     * @param userName
     */
    private void handleLogin(final String token, String userName) {
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("username", userName);
        params.put(BaseConsts.APP,"third");
        params.put(BaseConsts.CLASS,"login");
        params.put(BaseConsts.SIGN,"ae546c6bd3d771f492539fb44ce8a1c6");
        params.put("type","0");
        OkHttp.asyncPost(BaseConsts.BASE_URL, params, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
            @Override
            public void onResponse(final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()){
                            try {
                                Gson gson = new Gson();
                                String json = response.body().string();
                                Root root = gson.fromJson(json,Root.class);
                                if(root.getStatus()==0){
                                    User user = new User();
                                    user.setNickname(root.getData().getUsername());
                                    user.setUserid(root.getData().getUserid());
                                    TApplication.user = user;
                                    TApplication.user_id = user.getUserid();
                                    TApplication.user_name = user.getNickname();
                                    TApplication.device_no = root.getData().getDevice_no();
                                 }
                            }catch (Exception e){

                            }
                            Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(LoginActivity.this,MainPageActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });
    }
}
