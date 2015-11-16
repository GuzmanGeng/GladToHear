package com.mb.mmdepartment.account.activity;
import android.accounts.AccountManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.account.activity.util.AccountGeneral;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.bean.regist.code.Root;
import com.mb.mmdepartment.bean.regist.code.regist.Roots;
import com.mb.mmdepartment.biz.regist.GetCodeBiz;
import com.mb.mmdepartment.listener.RegistResponse;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static com.mb.mmdepartment.account.activity.util.AccountGeneral.sServerAuthenticate;
import static com.mb.mmdepartment.account.activity.AuthenticatorActivity.ARG_ACCOUNT_TYPE;
import static com.mb.mmdepartment.account.activity.AuthenticatorActivity.KEY_ERROR_MESSAGE;
import static com.mb.mmdepartment.account.activity.AuthenticatorActivity.PARAM_USER_PASS;
/**
 * In charge of the Sign up process. Since it's not an AuthenticatorActivity decendent,
 * it returns the result back to the calling activity, which is an AuthenticatorActivity,
 * and it return the result back to the Authenticator
 * User: GC
 */
public class SignUpActivity extends BaseActivity implements RequestListener,RegistResponse{
    private String TAG = getClass().getSimpleName();
    private String mAccountType;
    private EditText ed_username,ed_password,ed_password_again,ed_phone_number,ed_code;
    private TextView tv_regist,tv_getcode;
    private String authtoken;
    private String username;
    private String password;
    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//
//        setContentView(R.layout);
//
//        findViewById(R.id.alreadyMember).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setResult(RESULT_CANCELED);
//                finish();
//            }
//        });
//        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//    }
    @Override
    public int getLayout() {
        return R.layout.activity_regist;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        mAccountType = getIntent().getStringExtra(ARG_ACCOUNT_TYPE);
        initView();
        setListener();
    }

    private void setListener() {
        tv_getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone_number = ed_phone_number.getText().toString().trim();
                if (!checkPhone(phone_number)) return;
                GetCodeBiz biz=new GetCodeBiz();
                biz.getCode(phone_number,SignUpActivity.this);
            }
        });
        tv_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username=ed_username.getText().toString().trim();
                password=ed_password.getText().toString().trim();
                final String again_pass=ed_password_again.getText().toString().trim();
                final String tel_num=ed_phone_number.getText().toString().trim();
                final String code=ed_code.getText().toString().trim();
                if (checkUserState(username,password,again_pass,tel_num,code)){
                    createAccount(username,password,again_pass,tel_num,code);
                }
            }
        });
    }

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
    private boolean checkUserState(String username,String password,String again_pass,String tel_num,String code) {

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

    private void initView() {
        ed_username = (EditText) findViewById(R.id.regist_username_ed);
        ed_password = (EditText) findViewById(R.id.regist_password_ed);
        ed_password_again = (EditText) findViewById(R.id.regist_again_password_ed);
        ed_phone_number = (EditText) findViewById(R.id.regist_photo_number_ed);
        ed_code = (EditText) findViewById(R.id.regist_code_ed);
        tv_getcode=(TextView)findViewById(R.id.regist_getcode_tv);
        tv_regist=(TextView)findViewById(R.id.regist_regist_tv);
    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("注册");
        action.setHomeButtonEnabled(isTrue);
    }

    /**
     * 创建账户
     */
    private void createAccount(final String user, final String pass, final String again_pass, final String tel,final String code) {
        try {
            sServerAuthenticate.userSignUp(user,pass,again_pass, tel,code, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS,this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onResponseSuccess(Response response) {
        if (response.isSuccessful()){
            Gson gson=new Gson();
            if (response.isSuccessful()) {
                String json= null;
                try {
                    json = response.body().string();
                    Roots root=gson.fromJson(json,Roots.class);
                    authtoken = root.getData().getPassword();

                    Intent intent=new Intent();
                    intent.putExtra("authtoken",authtoken);
                    Bundle data = new Bundle();
                    try {
                        data.putString(AccountManager.KEY_ACCOUNT_NAME, username);
                        data.putString(AccountManager.KEY_ACCOUNT_TYPE, mAccountType);
                        data.putString(AccountManager.KEY_AUTHTOKEN, authtoken);
                        data.putString(PARAM_USER_PASS, password);
                    } catch (Exception e) {
                        data.putString(KEY_ERROR_MESSAGE, e.getMessage());
                    }
                    final Intent res = new Intent();
                    res.putExtras(data);
                    if (intent.hasExtra(KEY_ERROR_MESSAGE)) {
                        Toast.makeText(getBaseContext(), intent.getStringExtra(KEY_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                    } else {
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onFailueRequess(Request request, IOException e) {
        showToast("注册失败");
    }
    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(Response response) {
        if (response.isSuccessful()){
            Gson gson=new Gson();
            try {
                String json=response.body().string();
                Root root = gson.fromJson(json, Root.class);
                if (root.getStatus()!= OkHttp.NET_STATE){
                    showToast("验证码获取失败");
                }else {
                    showToast("验证码已发送,请注意查收");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailue(Request request, IOException e) {
        showToast("网络链接失败");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
