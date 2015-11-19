package com.mb.mmdepartment.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.mb.mmdepartment.R;
import com.mb.mmdepartment.base.BaseActivity;

public class HelpCenterActivity extends BaseActivity {

    @Override
    public int getLayout() {
        return R.layout.activity_help_center;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("帮助中心");
        action.setHomeButtonEnabled(isTrue);
    }
}
