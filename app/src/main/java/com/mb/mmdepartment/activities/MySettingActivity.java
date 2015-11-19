package com.mb.mmdepartment.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mb.mmdepartment.R;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.base.TApplication;

public class MySettingActivity extends BaseActivity {
    private TextView feed_back_tv,about_app_tv,chat_with_us_tv,help_center_tv;
    @Override
    public int getLayout() {
        return R.layout.activity_my_setting;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initView();
        setListener();
    }

    private void setListener() {
        feed_back_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(TApplication.user_id)) {
                    startActivity(MySettingActivity.this, LoginActivity.class);
                } else {
                    startActivity(MySettingActivity.this, FeedBackActivity.class);
                }
            }
        });
        about_app_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MySettingActivity.this,AboutAppActivity.class);
            }
        });
        chat_with_us_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MySettingActivity.this,ContactUsActivity.class);
            }
        });
        help_center_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MySettingActivity.this, HelpCenterActivity.class);
            }
        });
    }

    private void initView() {
        feed_back_tv = (TextView) findViewById(R.id.feed_back_tv);
        about_app_tv = (TextView) findViewById(R.id.about_app_tv);
        chat_with_us_tv = (TextView) findViewById(R.id.chat_with_us_tv);
        help_center_tv = (TextView) findViewById(R.id.help_center_tv);
    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("意见反馈");
        action.setHomeButtonEnabled(isTrue);
    }
}
