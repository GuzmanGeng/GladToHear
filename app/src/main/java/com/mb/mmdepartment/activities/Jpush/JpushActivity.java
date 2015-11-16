package com.mb.mmdepartment.activities.Jpush;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.mb.mmdepartment.R;
import com.mb.mmdepartment.base.BaseActivity;
import com.tencent.stat.StatService;

public class JpushActivity extends BaseActivity {
    @Override
    public int getLayout() {
        return R.layout.activity_jpush;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("我是推送消息");
        action.setHomeButtonEnabled(isTrue);
    }
}
