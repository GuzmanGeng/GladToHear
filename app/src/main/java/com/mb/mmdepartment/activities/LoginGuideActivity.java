package com.mb.mmdepartment.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mb.mmdepartment.R;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.base.TApplication;
import com.tencent.stat.StatService;

public class LoginGuideActivity extends BaseActivity implements View.OnClickListener{

    private TextView tologin;
    private TextView toregist;
    private TextView toquickregister;
    @Override
    public int getLayout() {
        return R.layout.activity_login_guide;
    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("登录指导");
        action.setHomeButtonEnabled(isTrue);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        intiview();
        setListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TApplication.activities.remove(this);
    }

    private void intiview(){
        tologin = (TextView)findViewById(R.id.tologin);
        toregist = (TextView)findViewById(R.id.toregister);
        toquickregister = (TextView)findViewById(R.id.to_quick_register);
    }

    private void setListener(){
        tologin.setOnClickListener(this);
        toregist.setOnClickListener(this);
        toquickregister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tologin:
                startActivity(LoginGuideActivity.this, LoginActivity.class);
                break;
            case R.id.toregister:
                startActivity(LoginGuideActivity.this, RegistActivity.class);
                break;
            case R.id.to_quick_register:
                startActivity(LoginGuideActivity.this, QuickRegisterActivity.class);
                break;
        }
    }
}
