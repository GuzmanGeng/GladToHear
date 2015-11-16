package com.mb.mmdepartment.activities;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.tencent.stat.StatService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatListActivity extends BaseActivity {
    private RecyclerView chat_recycle;
    private LuPinModel luPinModel;

    @Override
    public int getLayout() {
        return R.layout.activity_chat_list;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        luPinModel = new LuPinModel();
        luPinModel.setName("chatlist");
        luPinModel.setState("end");
        luPinModel.setType("page");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        luPinModel.setOperationtime(sdf.format(new Date()));
        StatService.onResume(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
        luPinModel.setEndtime(sdf.format(new Date()));
        TApplication.luPinModels.add(luPinModel);
        StatService.onPause(this);
    }
}
