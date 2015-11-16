package com.mb.mmdepartment.activities;

import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.login.getuserheadpic.Root;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.mb.mmdepartment.biz.login.getuserpic.GetUserPicBiz;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.tools.sp.SPCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tencent.stat.StatService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyAccuntActivity extends BaseActivity implements View.OnClickListener,RequestListener{
    private TextView tv_check_out_login;
    private TextView change_psw;
    private TextView user_info;
    private GetUserPicBiz biz;
    private String path;
    private ImageView userpic;
    private LuPinModel luPinModel;

    @Override
    public int getLayout() {
        return R.layout.activity_my_accunt;
    }

    @Override
    protected void onResume() {
        super.onResume();
        luPinModel = new LuPinModel();
        luPinModel.setType("page");
        luPinModel.setName("my_account");
        luPinModel.setState("end");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        luPinModel.setOperationtime(sdf.format(new Date()));
        StatService.onResume(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        luPinModel.setEndtime(sdf.format(new Date()));
        TApplication.luPinModels.add(luPinModel);
        StatService.onPause(this);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        user_info = (TextView)findViewById(R.id.user_info);
        change_psw = (TextView)findViewById(R.id.change_psw);
        tv_check_out_login = (TextView) findViewById(R.id.tv_check_out_login);
        tv_check_out_login.setOnClickListener(this);
        change_psw.setOnClickListener(this);
        user_info.setOnClickListener(this);
        userpic = (ImageView)findViewById(R.id.userpic);
        biz = new GetUserPicBiz();
        biz.getuserpic(MyAccuntActivity.class.getSimpleName(),this);

    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("我的账号");
        action.setHomeButtonEnabled(isTrue);
    }

    @Override
    public void onResponse(Response response) {
        if(response.isSuccessful()){
            Gson gson = new Gson();
            try {
                String json = response.body().string();
                Root root = gson.fromJson(json,Root.class);
                if(root.getStatus()==0){
                    path = root.getData();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ImageLoader.getInstance().displayImage(path,userpic);
                        }
                    });
                }
            }catch (Exception e){

            }
        }
    }

    @Override
    public void onFailue(Request request, IOException e) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_check_out_login:
                LuPinModel checkout = new LuPinModel();
                 checkout.setName("checkoutbutton");
                checkout.setType("other");
                checkout.setState("next");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                checkout.setOperationtime(sdf.format(new Date()));
                TApplication.luPinModels.add(checkout);
                TApplication.user_id="";
                TApplication.user_name=null;
                SPCache.putString(BaseConsts.SharePreference.USER_ID, "");
                SPCache.putString(BaseConsts.SharePreference.USER_NAME, "");
                SPCache.putString(BaseConsts.SharePreference.USER_SCORE, "");
                SPCache.putString(BaseConsts.SharePreference.USER_LITTLE_IMAGE, "");
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.user_info:
                break;
            case R.id.change_psw:
                startActivity(MyAccuntActivity.this,ModifyPasswordPageActivity.class);
                break;
        }
    }
}
