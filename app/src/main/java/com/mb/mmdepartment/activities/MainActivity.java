package com.mb.mmdepartment.activities;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.fragment.main.MainFragment;
public class MainActivity extends FragmentActivity implements View.OnClickListener{
    private MainFragment mainFragment;
    private ImageView user_center;
    private TextView tv_search,main_local_tv;
    private String provence;
    private SlidingMenu slidingMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();
        provence=intent.getStringExtra("provience");
        mainFragment=new MainFragment();
        changeFragment(mainFragment);
        initSlideMenu();
        initView();
        setListeners();
    }

    private void initSlideMenu() {
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.RIGHT);
        // 设置触摸屏幕的模式
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
//        slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
//        slidingMenu.setShadowDrawable(R.drawable.shadow);
        // 设置滑动菜单视图的宽度
        slidingMenu.setBehindOffsetRes(R.dimen.hundred_eighty_dip);
        // 设置渐入渐出效果的值
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //为侧滑菜单设置布局
        View view = LayoutInflater.from(this).inflate(R.layout.activity_user_center,null);
        slidingMenu.setMenu(view);
        initSlideMenuView(view);
    }

    private void initSlideMenuView(View v) {
        v.findViewById(R.id.iv_top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        v.findViewById(R.id.user_center_login_username).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        v.findViewById(R.id.user_center_regist_score).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        v.findViewById(R.id.jifen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        v.findViewById(R.id.pinglun).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        v.findViewById(R.id.my_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        v.findViewById(R.id.setting_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setListeners() {
        user_center.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        main_local_tv.setOnClickListener(this);
    }

    private void initView() {
        user_center = (ImageView) findViewById(R.id.user_center);
        tv_search = (TextView) findViewById(R.id.tv_search);
        main_local_tv = (TextView) findViewById(R.id.main_local_tv);
        if (provence != null) {
            main_local_tv.setText(provence);
        } else {
            main_local_tv.setText("上海");
            TApplication.city_id="50";
        }
    }

    /**
     * 设置fragment
     * @param targetFragment
     */
    private void changeFragment(Fragment targetFragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_content, targetFragment)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
    /**
     * 检查网络链接状态
     * @param context
     * @return
     */
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_center:
                slidingMenu.toggle();
                break;
            case R.id.tv_search:
                Intent intent_search = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent_search);
                break;
            case R.id.main_local_tv:
                //此处要进行购物车选中数据判断
                Intent intent = new Intent(MainActivity.this, WelcomActivity.class);
                intent.putExtra("setLocation", true);
                startActivityForResult(intent, 200);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {
            if (requestCode == 200) {
                String provience_result = data.getStringExtra("provience");
                main_local_tv.setText(provience_result);
            }else if (requestCode == 300||requestCode==400||requestCode==500) {
                if ("".equals(TApplication.user_id)) {
//                    user_center_login_username.setText("登陆");
//                    user_center_regist_score.setText("注册");
                }else {
//                    if (TApplication.user != null) {
//                        user_center_login_username.setText(TApplication.user.getNickname());
//                    } else {
//                        user_center_login_username.setText(TApplication.user_name);
//                    }
                }
            }
        }
    }
}
