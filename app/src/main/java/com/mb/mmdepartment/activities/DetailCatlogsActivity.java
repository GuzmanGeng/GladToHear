package com.mb.mmdepartment.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mb.mmdepartment.R;
import com.tencent.stat.StatService;

public class DetailCatlogsActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView detail_tv_next;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_catlogs);
        initView();
        initToolbar();
        setListener();
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

    private void setListener() {
        detail_tv_next.setOnClickListener(this);
    }

    private void initView() {
        detail_tv_next=(TextView)findViewById(R.id.detail_tv_next);
    }
    private void initToolbar() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("品牌选择");
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    public void onClick(View view) {
        Intent intent=new Intent(DetailCatlogsActivity.this,ProductsListActivity.class);
        startActivity(intent);
    }
}
