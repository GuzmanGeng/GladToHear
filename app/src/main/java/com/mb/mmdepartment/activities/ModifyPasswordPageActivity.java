package com.mb.mmdepartment.activities;

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
import com.mb.mmdepartment.bean.setting.UserPassChangeRoot;
import com.mb.mmdepartment.biz.password.ChangePswBiz;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.tools.log.Log;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModifyPasswordPageActivity extends BaseActivity {

    private final String TAG = ModifyPasswordPageActivity.class.getSimpleName();

    private EditText old_psw;
    private EditText psw_changing;
    private EditText psw_again;
    private TextView chang_psw;
    @Override
    public int getLayout() {
        return R.layout.activity_modify_password_page;
    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("修改密码");
        action.setHomeButtonEnabled(isTrue);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TApplication.activities.remove(this);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initview();
        setlistener();
    }

    private void initview(){
        old_psw = (EditText)findViewById(R.id.old_psw);
        psw_again = (EditText)findViewById(R.id.psw_again);
        psw_changing = (EditText)findViewById(R.id.psw_changing);
        chang_psw = (TextView)findViewById(R.id.modify_psw);
    }

    private void setlistener(){
        psw_changing.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if (!isPsw(chang_psw.getText().toString())){
                        showToast("密码至少8位，必须有大小写数字组成");
                    }
                }
            }
        });
        psw_again.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if (!isPsw(chang_psw.getText().toString())){
                        showToast("密码至少8位，必须有大小写数字组成");
                    }
                }
            }
        });
        chang_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkUserState(old_psw.getText().toString(),psw_changing.getText().toString(),
                        psw_again.getText().toString())) return;
                ChangePswBiz biz = new ChangePswBiz();
                biz.changepsw(old_psw.getText().toString(), psw_changing.getText().toString(),
                        psw_again.getText().toString(), TAG, new RequestListener() {
                            @Override
                            public void onResponse(Response response) {
                                if(response.isSuccessful()){
                                    try {
                                        Gson gson = new Gson();
                                        String json = response.body().string();
                                        UserPassChangeRoot root = gson.fromJson(json, UserPassChangeRoot.class);
                                        if (root.getStatus()==0) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    showToast("修改成功");
                                                    ModifyPasswordPageActivity.this.finish();
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
        });
    }

    /**
     * 确定所有的输入是否完整
     * @param old_psw
     * @param password
     * @param again_pass
     * @return
     */
    private boolean checkUserState(String old_psw, String password, String again_pass) {
        if (TextUtils.isEmpty(old_psw)){
            showToast("密码不能为空");
            return false;
        }
        if (TextUtils.isEmpty(password)){
            showToast("密码不能为空");
            return false;
        }
        if (TextUtils.isEmpty(again_pass)) {
            showToast("密码不能为空");
            return false;
        }
        if (!again_pass.equals(password)){
            showToast("密码不一致请检查");
            return false;
        }
//        if (!isPsw(again_pass)){
//            showToast("密码至少8位，必须有大小写数字组成");
//            return false;
//        }
        return true;
    }


    /**
     * 检查密码格式是否正确
     * @param psw
     * @return
     */
    public static boolean isPsw(String psw) {
//        String match = "^(([a-z]|[A-Z]|0~9)\\d{9})";
//        String match = "[a-z]{1,}+[A-Z]{1,}+\\w{6,}";
        String match = "^(?![0-9]+$)(?![a-zA-Z]+$)[a-zA-Z0-9]{6,}";
        Pattern pattern = Pattern.compile(match);
        Matcher matcher = pattern.matcher(psw);
        return matcher.matches();
    }
}
