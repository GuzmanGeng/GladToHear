package com.mb.mmdepartment.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.mb.mmdepartment.R;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.base.TApplication;

public class AboutAppActivity extends BaseActivity {
    @Override
    public int getLayout() {
        return R.layout.activity_about_app;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("关于软件");
        action.setHomeButtonEnabled(isTrue);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TApplication.activities.remove(this);
    }
}
