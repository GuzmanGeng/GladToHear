package com.mb.mmdepartment.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.mb.mmdepartment.R;
import com.mb.mmdepartment.base.BaseActivity;
import com.sina.weibo.sdk.api.share.Base;

public class ContactUsActivity extends BaseActivity {
    @Override
    public int getLayout() {
        return R.layout.ac_setting_for_us;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("联系我们");
        action.setHomeButtonEnabled(isTrue);
    }
}
