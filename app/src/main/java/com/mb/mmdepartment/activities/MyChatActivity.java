package com.mb.mmdepartment.activities;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mb.mmdepartment.R;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.biz.mychat.MainMyChatBiz;

public class MyChatActivity extends BaseActivity {
    private RecyclerView my_chat_recycle;
    private MainMyChatBiz biz;
    private TextView tv_replay_to_me,tv_my_replay;
    @Override
    public int getLayout() {
        return R.layout.activity_my_chat;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initView();
        getData();
        setListener();
    }

    private void setListener() {
        tv_replay_to_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biz.getMyChat(TApplication.user_id);
                tv_replay_to_me.setTextColor(Color.WHITE);
                tv_replay_to_me.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                tv_my_replay.setTextColor(getResources().getColor(R.color.theme_color));
                tv_my_replay.setBackgroundColor(Color.WHITE);
            }
        });
        tv_my_replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biz.getMyReplay(TApplication.user_id);
                tv_replay_to_me.setTextColor(getResources().getColor(R.color.theme_color));
                tv_replay_to_me.setBackgroundColor(Color.WHITE);
                tv_my_replay.setTextColor(Color.WHITE);
                tv_my_replay.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
            }
        });
    }

    private void getData() {
        biz = new MainMyChatBiz(my_chat_recycle, this);
        biz.getMyChat(TApplication.user_id);
    }

    private void initView() {
        my_chat_recycle = (RecyclerView) findViewById(R.id.my_chat_recycle);
        tv_replay_to_me = (TextView) findViewById(R.id.tv_replay_to_me);
        tv_my_replay=(TextView)findViewById(R.id.tv_my_replay);
    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("我的评论");
        action.setHomeButtonEnabled(isTrue);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TApplication.activities.remove(this);
    }
}
