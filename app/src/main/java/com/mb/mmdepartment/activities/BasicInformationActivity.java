package com.mb.mmdepartment.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.listener.RequestListener;
import com.tencent.stat.StatService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class BasicInformationActivity extends BaseActivity implements RequestListener,View.OnClickListener{
    private EditText basic_information_input_number_ed,basic_information_input_code_ed,basic_information_input_want_number_ed;
    private TextView basic_information_get_number_tv,basic_information_ensure_code_tv,basic_information_ensure_number_tv,basic_information_commit_tv;
    private TextView basic_information_bill_money_tv;
    private String input_number,input_code,input_want_number;
    private LuPinModel luPinModel;
    @Override
    public int getLayout() {
        return R.layout.activity_basic_information;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        basic_information_input_number_ed=(EditText)findViewById(R.id.basic_information_input_number_ed);
        basic_information_input_code_ed=(EditText)findViewById(R.id.basic_information_input_code_ed);
        basic_information_input_want_number_ed=(EditText)findViewById(R.id.basic_information_input_want_number_ed);
        basic_information_get_number_tv=(TextView)findViewById(R.id.basic_information_get_number_tv);
        basic_information_ensure_code_tv=(TextView)findViewById(R.id.basic_information_ensure_code_tv);
        basic_information_ensure_number_tv=(TextView)findViewById(R.id.basic_information_ensure_number_tv);
        basic_information_commit_tv=(TextView)findViewById(R.id.basic_information_commit_tv);
        basic_information_bill_money_tv=(TextView)findViewById(R.id.basic_information_bill_money_tv);
    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("信息填写");
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

    }

    @Override
    public void onFailue(Request request, IOException e) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TApplication.activities.remove(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.basic_information_get_number_tv:
                input_number=basic_information_input_number_ed.getText().toString();
                if (TextUtils.isEmpty(input_number)){
                    showToast("手机号不能为空");
                    return;
                }
                if (checkMobile(input_number)){
                    showToast("手机号格式不正确");
                    return;
                }

                break;
            case R.id.basic_information_ensure_code_tv:
                break;
            case R.id.basic_information_ensure_number_tv:
                break;
            case R.id.basic_information_commit_tv:
                break;
        }
    }
    /**
     * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
     * @param mobile 移动、联通、电信运营商的号码段
     *<p>移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
     *、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）</p>
     *<p>联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）</p>
     *<p>电信的号段：133、153、180（未启用）、189</p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkMobile(String mobile) {
        String regex = "(\\+\\d+)?1[3458]\\d{9}$";
        return Pattern.matches(regex, mobile);
    }
}
