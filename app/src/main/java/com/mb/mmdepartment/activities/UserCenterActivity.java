package com.mb.mmdepartment.activities;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

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

public class UserCenterActivity extends BaseActivity implements View.OnClickListener,RequestListener{
    private RecyclerView function;
    private ImageView headImageView;
    private LuPinModel luPinModel;
    @Override
    public int getLayout() {
        return R.layout.activity_user_center;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {

    }

    private void SearchScore() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        luPinModel = new LuPinModel();
        luPinModel.setName("UserCenter");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        luPinModel.setOperationtime(sdf.format(new Date()));
        luPinModel.setState("end");
        StatService.onResume(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("个人中心");
        action.setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onResponse(Response response) {

    }

    @Override
    public void onFailue(Request request, IOException e) {

    }
}
