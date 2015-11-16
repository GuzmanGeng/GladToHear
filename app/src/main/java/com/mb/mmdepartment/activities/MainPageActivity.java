package com.mb.mmdepartment.activities;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;

import com.mb.mmdepartment.adapter.FunctionAdapter;
import com.mb.mmdepartment.adapter.main.MainListViewAdapter;
import com.mb.mmdepartment.bean.FunctionItem;
import com.mb.mmdepartment.bean.main_brand.News;
import com.mb.mmdepartment.biz.helpcheck.marcket_sel.main_new.NewBrandBiz;
import com.mb.mmdepartment.biz.lupinmodel.LupinModelBiz;
import com.mb.mmdepartment.overridge.MyGridLayoutManager;
import com.mb.mmdepartment.tools.log.Log;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.getversion.GetVersion;
import com.mb.mmdepartment.bean.login.getuserheadpic.Root;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.mb.mmdepartment.biz.getversion.GetVersionBiz;
import com.mb.mmdepartment.biz.login.getuserpic.GetUserPicBiz;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.residemenu.ResideMenu;
import com.mb.mmdepartment.residemenu.ResideMenuItem;
import com.mb.mmdepartment.tools.sp.SPCache;
import com.mb.mmdepartment.view.SwipeRefreshView;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tencent.stat.StatService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class MainPageActivity extends FragmentActivity implements FunctionAdapter.OnItemClickListener{
    //帮你算按钮
    private TextView main_local_tv;
    private String provience;
    private int page=1,count,lastposition,whichSel = 1;
    private NewBrandBiz biz;
//    private FloatingActionButton loading_more;
    private final String TAG=MainPageActivity.class.getSimpleName();
    private ImageView user_center,user_pic_in_main;
    private LuPinModel luPinModel;
    private ResideMenu resideMenu;
    private ResideMenuItem item_score;
    private ResideMenuItem item_pinglun;
    private ResideMenuItem item_zhanghao;
    private ResideMenuItem item_setting;
    private TextView user_center_login_username;
    private TextView user_center_regist_score;
    private String keyword = "品牌新品";
    private String version_name = "";
    private RecyclerView main_function_recyc;
    private MyGridLayoutManager gridLayoutManager;
    private FunctionAdapter adapter;
    private FunctionItem item;
    private TextView main_new_brand_tv;
    private TextView main_release_brand_tv;
    private TextView main_story_brand_tv;
    private List<FunctionItem> functionItems;
    private SwipeRefreshView swipeRefreshView;
    private ListView listView;
    private int ver,ver_name;
    private List<News> datas = new ArrayList<>();
    private MainListViewAdapter listViewAdapter;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    listViewAdapter = new MainListViewAdapter(datas,MainPageActivity.this);
                    listView.setAdapter(listViewAdapter);
                    break;
                case 1:
                    Toast.makeText(MainPageActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    break;
                case 2:adapter.notifyDataSetChanged();
                    break;
                case 3:
                    Toast.makeText(MainPageActivity.this, "已经加载完毕了", Toast.LENGTH_SHORT).show();
                    swipeRefreshView.setRefreshing(false);
                    break;
            }}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uploadlupinModel();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        Log.e("intent", intent.toString());
        provience=intent.getStringExtra("provience");
        TApplication.city_name = provience;
        initView();
        setListeners();
//        if( savedInstanceState == null )
//            changeFragment(new Main());
        if (!isNetworkConnected(this)) {
            OkHttp.cancleMainNetWork(new String[]{TAG});
            Toast.makeText(MainPageActivity.this, "网络无链接", Toast.LENGTH_SHORT).show();
        }
        final String version = getVersion();
        Log.i("tag", "version" + version);
        GetVersionBiz getVersionBiz = new GetVersionBiz();
        getVersionBiz.getVersion(new RequestListener() {
            @Override
            public void onResponse(Response response) {
                if(response.isSuccessful()){
                    try{
                        Gson gson = new Gson();
                        String json = response.body().string();
                        Log.i("tag",json);
                        GetVersion getVersion = gson.fromJson(json, GetVersion.class);
//                        if(getVersion.getStatus()==0){
                        version_name = getVersion.getData().get(0).getV_name();
                            Log.i("tag","version:"+version_name);
                        version_name = version_name.substring(1);
                        version_name = version_name.replace(".", "");
                        ver_name = Integer.parseInt(version_name);
//                        }else{
//                            Log.i("tag", getVersion.getError());
//                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailue(Request request, IOException e) {
            e.printStackTrace();
            }
        });
        String getVersion = getVersion().substring(1);
        getVersion = getVersion.replace(".", "");
        ver = Integer.parseInt(getVersion);
        if(ver<ver_name){
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("提示").setMessage("请去下载最新版本").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create();
            dialog.show();
        }
    }

//    /**
//     * 设置fragment
//     * @param targetFragment
//     */
//    private void changeFragment(Fragment targetFragment){
//        resideMenu.clearIgnoredViewList();
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.main_content, targetFragment)
//                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                .commit();
//    }
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

    private void getDataFunction() {
        functionItems=new ArrayList<>();
        item=new FunctionItem();
        item.setUrl(String.valueOf(R.mipmap.help_calculate));
        item.setTitle("帮你算");
        functionItems.add(item);

        item=new FunctionItem();
        item.setUrl(String.valueOf(R.mipmap.help_check));
        item.setTitle("帮你查");
        functionItems.add(item);

        item=new FunctionItem();
        item.setUrl(String.valueOf(R.mipmap.informationsummary));
        item.setTitle("资讯汇总");
        functionItems.add(item);

        item=new FunctionItem();
        item.setUrl(String.valueOf(R.mipmap.integralmall));
        item.setTitle("积分商城");
        functionItems.add(item);

        item=new FunctionItem();
        item.setUrl(String.valueOf(R.mipmap.persional_space));
        item.setTitle("个人空间");
        functionItems.add(item);

        item=new FunctionItem();
        item.setUrl(String.valueOf(R.mipmap.woman_chat));
        item.setTitle("主妇论坛");
        functionItems.add(item);
    }

    /**
     * 设置监听器
     */
    private void setListeners() {
        main_local_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this, WelActivity.class);
                intent.putExtra("setLocation", true);
                startActivityForResult(intent, 200);
            }
        });
        user_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
            }
        });
        item_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("".equals(TApplication.user_id)) {
                    Intent intent = new Intent(MainPageActivity.this, LoginGuideActivity.class);
                    startActivityForResult(intent,500);
                }
            }
        });
        item_pinglun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("".equals(TApplication.user_id)) {
                    Intent intent = new Intent(MainPageActivity.this, LoginGuideActivity.class);
                    startActivityForResult(intent,500);
                }
            }
        });
        item_zhanghao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("".equals(TApplication.user_id)) {
                    Intent intent = new Intent(MainPageActivity.this, LoginGuideActivity.class);
                    startActivityForResult(intent,500);
                }else {
                    Intent intent = new Intent(MainPageActivity.this, MyAccuntActivity.class);
                    startActivityForResult(intent,500);
                }
            }
        });
        item_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("".equals(TApplication.user_id)) {
                    Intent intent = new Intent(MainPageActivity.this, LoginGuideActivity.class);
                    startActivityForResult(intent,500);
                }
            }
        });
        user_center_login_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this, LoginActivity.class);
                startActivityForResult(intent, 300);
            }
        });
        user_center_regist_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this, RegistActivity.class);
                startActivityForResult(intent, 400);
            }
        });
        swipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initnews();
                swipeRefreshView.setRefreshing(false);
            }
        });
        swipeRefreshView.setDragScrollListener(new SwipeRefreshView.OnDragScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                lastposition = listView.getLastVisiblePosition();
                if (lastposition == datas.size()) {
                    ++page;
                    biz.getMacketList(page, 0, 50, null, keyword, new RequestListener() {
                        @Override
                        public void onResponse(Response response) {
                            if (response.isSuccessful()) {
                                Gson gson = new Gson();
                                try {
                                    String json = response.body().string();
                                    json = json.replace(" ", "");
                                    android.util.Log.e("datadata", json);
                                    final com.mb.mmdepartment.bean.main_brand.Root root = gson.fromJson(json, com.mb.mmdepartment.bean.main_brand.Root.class);
                                    if ("0".equals(String.valueOf(root.getStatus()))) {
                                        if (root.getData().getNews().size() != 0) {
                                            count = Integer.valueOf(root.getData().getCount());
                                            for (News news : root.getData().getNews()) {
                                                datas.add(news);
                                            }
                                            Log.i("list", datas.toString());
                                            handler.sendEmptyMessage(0);
                                        } else {
                                            handler.sendEmptyMessage(3);
                                        }
                                    } else {
                                        handler.sendEmptyMessage(1);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailue(Request request, IOException e) {

                        }
                    });
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainPageActivity.this, InformationDetailActivity.class);
                intent.putExtra("content_id", datas.get(position).getContent_id());
                startActivity(intent);
            }
        });
        main_new_brand_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (whichSel != 0) {
                    if (( MainPageActivity.this).isNetworkConnected(MainPageActivity.this)) {
                        OkHttp.cancleMainNetWork(new String[]{"FirstFragment"});
//                        setFragmentChose(firstFragment);
                    } else {
                        Toast.makeText(MainPageActivity.this, "网络无连接", Toast.LENGTH_LONG).show();
                    }
                    main_new_brand_tv.setTextColor(getResources().getColor(R.color.color_white));
                    main_new_brand_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    keyword = "品牌新品";
                    page = 1;
                    datas.clear();
                    initnews();

                    main_release_brand_tv.setTextColor(getResources().getColor(R.color.color_black));
                    main_release_brand_tv.setBackgroundColor(getResources().getColor(R.color.color_white));

                    main_story_brand_tv.setTextColor(getResources().getColor(R.color.color_black));
                    main_story_brand_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    whichSel = 0;
                }
            }
        });
        main_release_brand_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (whichSel != 1) {
                    if (( MainPageActivity.this).isNetworkConnected(MainPageActivity.this)) {
                        OkHttp.cancleMainNetWork(new String[]{"SecondFragment"});
//                        secondFragment = new SecondFragment();
//                        setFragmentChose(secondFragment);
                    } else {
                        Toast.makeText(MainPageActivity.this, "网络无连接", Toast.LENGTH_LONG).show();
                    }
                    main_release_brand_tv.setTextColor(getResources().getColor(R.color.color_white));
                    main_release_brand_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    keyword = "品牌大促";
                    datas.clear();
                    page = 1;
                    initnews();

                    main_new_brand_tv.setTextColor(getResources().getColor(R.color.color_black));
                    main_new_brand_tv.setBackgroundColor(getResources().getColor(R.color.color_white));

                    main_story_brand_tv.setTextColor(getResources().getColor(R.color.color_black));
                    main_story_brand_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    whichSel = 1;
                }
            }
        });
        main_story_brand_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (whichSel != 2) {
                    if (( MainPageActivity.this).isNetworkConnected(MainPageActivity.this)) {
                        OkHttp.cancleMainNetWork(new String[]{"ThirdFragment"});
//                        thirdFragment = new ThirdFragment();
//                        setFragmentChose(thirdFragment);
                    } else {
                        Toast.makeText(MainPageActivity.this, "网络无连接", Toast.LENGTH_LONG).show();
                    }
                    main_story_brand_tv.setTextColor(getResources().getColor(R.color.color_white));
                    main_story_brand_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    keyword = "品牌买赠";
                    datas.clear();
                    page = 1;
                    initnews();

                    main_new_brand_tv.setTextColor(getResources().getColor(R.color.color_black));
                    main_new_brand_tv.setBackgroundColor(getResources().getColor(R.color.color_white));

                    main_release_brand_tv.setTextColor(getResources().getColor(R.color.color_black));
                    main_release_brand_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    whichSel = 2;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {
            if (requestCode == 200) {
                if (data == null) {
                    Toast.makeText(MainPageActivity.this, "定位信息获取失败", Toast.LENGTH_SHORT).show();
                    main_local_tv.setText("上海");
                }else {
                    String provience_result = data.getStringExtra("provience");
                    main_local_tv.setText(provience_result);
                }
            }else if (requestCode == 300||requestCode==400||requestCode==500) {
                if ("".equals(TApplication.user_id)) {
                    user_center_login_username.setText("登陆");
                    user_center_regist_score.setText("注册");
                }else {
                    if (TApplication.user != null) {
                        user_center_login_username.setText(TApplication.user.getNickname());
                    } else {
                        user_center_login_username.setText(TApplication.user_name);
                    }
                }
            }
        }
    }

    /**
     * 初始化界面
     */
    private void initView() {
            swipeRefreshView = (SwipeRefreshView)findViewById(R.id.swipeRefreshView);
            resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.mipmap.ditu_right);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);
        resideMenu.attachToActivity(this);
        resideMenu.setScaleValue(0.6f);
        item_score = new ResideMenuItem(this, R.mipmap.icon_jifen, "积分");
        item_pinglun = new ResideMenuItem(this, R.mipmap.icon_jifen, "评论");
        item_pinglun.setVisibility(View.GONE);
        item_zhanghao = new ResideMenuItem(this, R.mipmap.icon_jifen, "账号");
        item_setting = new ResideMenuItem(this, R.mipmap.icon_jifen, "设置");
        item_setting.setVisibility(View.GONE);
        user_pic_in_main = (ImageView)findViewById(R.id.user_pic_in_main);
        resideMenu.addMenuItem(item_score, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(item_pinglun, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(item_zhanghao, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(item_setting, ResideMenu.DIRECTION_RIGHT);

        user_center_login_username = (TextView) findViewById(R.id.user_center_login_username);
        user_center_regist_score = (TextView) findViewById(R.id.user_center_regist_score);
        user_center = (ImageView) findViewById(R.id.user_center);
        if ("".equals(TApplication.user_id)||TApplication.user_id==null) {
            Log.e("user_id","user_id1="+TApplication.user_id);
            user_center_login_username.setText("登陆");
            user_center_regist_score.setText("注册");
        }else {
            Log.e("user_id", "user_id2=" + TApplication.user_id);
            if (TApplication.user != null) {
                user_center_login_username.setText(TApplication.user.getNickname());
//                getuserpic();
//                user_center.setImageResource(TApplication.user.);
            } else {
                user_center_login_username.setText(TApplication.user_name);
//                getuserpic();
            }
        }

        listView = (ListView)findViewById(R.id.main_page_listview);
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.main_fragment,null);
        main_new_brand_tv = (TextView) v.findViewById(R.id.main_new_brand_tv);
        main_release_brand_tv = (TextView) v.findViewById(R.id.main_release_brand_tv);
        main_story_brand_tv = (TextView) v.findViewById(R.id.main_story_brand_tv);
        listView.addHeaderView(v);


        getDataFunction();
        main_function_recyc = (RecyclerView)v.findViewById(R.id.main_function_recyc);

        gridLayoutManager = new MyGridLayoutManager(MainPageActivity.this,3);
        gridLayoutManager.setOrientation(MyGridLayoutManager.VERTICAL);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        main_function_recyc.setLayoutManager(gridLayoutManager);
        adapter = new FunctionAdapter(functionItems, this);

        main_function_recyc.setAdapter(adapter);

        main_local_tv = (TextView) findViewById(R.id.main_local_tv);
        main_local_tv.setText(provience);
//        loading_more = (FloatingActionButton)findViewById(R.id.loading_more);
//        loading_more.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                getSupportFragmentManager()
//                new Main().loadingmore();
//            }
//
//        });
        biz=new NewBrandBiz();
        initnews();
}

    private void initnews(){
        biz.getMacketList(page, 0, 50, TApplication.city_id, keyword, new RequestListener() {
            @Override
            public void onResponse(Response response) {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    try {
                        String json = response.body().string();
                        json = json.replace(" ", "");
                        android.util.Log.e("datadata", json);
                        final com.mb.mmdepartment.bean.main_brand.Root root = gson.fromJson(json, com.mb.mmdepartment.bean.main_brand.Root.class);
                        if ("0".equals(String.valueOf(root.getStatus()))) {
                            count = Integer.valueOf(root.getData().getCount());
                            for (News news : root.getData().getNews()) {
                                datas.add(news);
                            }
                            handler.sendEmptyMessage(0);
                        } else {
                            handler.sendEmptyMessage(1);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailue(Request request, IOException e) {

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        luPinModel = new LuPinModel();
        luPinModel.setName("main");
        luPinModel.setType("page");
        luPinModel.setState("end");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        luPinModel.setOperationtime(sdf.format(new Date()));

        StatService.onResume(this);
    }

    private String getVersion(){
        try{
            PackageManager manager = this.getPackageManager();
            PackageInfo packageInfo = manager.getPackageInfo(this.getPackageName(), 0);
            String Version = packageInfo.versionName;
            return Version;
        }catch (Exception e){
            e.printStackTrace();
            return "不能得到当前版本号";
        }
    }

    private void uploadlupinModel(){
        LupinModelBiz lupinModelBiz = new LupinModelBiz();
        String json = lupinModelBiz.getlist(JPushInterface.getRegistrationID(this));
        lupinModelBiz.sendLuPinModel(json, TAG, new RequestListener() {
            @Override
            public void onResponse(Response response) {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    try {
                        String json = response.body().string();
                        Log.i("tag",json);
                        com.mb.mmdepartment.bean.lupinmodel.Root root =
                                gson.fromJson(json, com.mb.mmdepartment.bean.lupinmodel.Root.class);
                        if (root.getStatus() == 0) {
                            Log.i("Tag", "success");
                        } else {
                            Log.i("Tag", root.getError());
                        }
                    } catch (Exception e) {
                    }

                    android.util.Log.i("tag", "succeed");
                }
            }

            @Override
            public void onFailue(Request request, IOException e) {
                LuPinModel luPinModelmodel;
                for(int i = 0;i<TApplication.luPinModelList.size();i++){
                    luPinModelmodel = TApplication.luPinModelList.get(i);
                    TApplication.luPinModels.add(luPinModelmodel);
                }
                TApplication.luPinModelList.clear();
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        luPinModel.setEndtime(sdf.format(new Date()));
        TApplication.luPinModels.add(luPinModel);
        StatService.onPause(this);
    }

    private void getuserpic(){
        GetUserPicBiz biz = new GetUserPicBiz();
        biz.getuserpic(MyAccuntActivity.class.getSimpleName(), new RequestListener() {
            @Override
            public void onResponse(Response response) {
                final String path;
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    try {
                        String json = response.body().string();
                        Root root = gson.fromJson(json, Root.class);
                        if (root.getStatus() == 0) {
                            path = root.getData();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    ImageLoader.getInstance().displayImage(path,user_pic_in_main);
                                }
                            });
                        }
                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void onFailue(Request request, IOException e) {

            }
        });
    }



    @Override
    public void onClick(View view, int position) {
        luPinModel = new LuPinModel();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        luPinModel.setOperationtime(sdf.format(new Date()));
        luPinModel.setType("other");
        luPinModel.setState("next");
        switch (position){
            case 0:
                OkHttp.cancleMainNetWork(new String[]{TAG});
                alertDialog();
                luPinModel.setName("helpYouCount");
                break;
            case 1:
                OkHttp.cancleMainNetWork(new String[]{TAG});
                Intent intent1=new Intent(MainPageActivity.this,HelpYouQuerySearchActivity.class);
                startActivity(intent1);
                luPinModel.setName("helpYouSerach");
                break;
            case 2:
                OkHttp.cancleMainNetWork(new String[]{TAG});
                Intent intent5=new Intent(MainPageActivity.this,InfomationSummaryActivity.class);
                startActivity(intent5);
                luPinModel.setName("Informationcollect");
                break;
            case 3:
                OkHttp.cancleMainNetWork(new String[]{TAG});
                Intent intent3=new Intent(MainPageActivity.this,AccumulatedShopActivity.class);
                startActivity(intent3);
                luPinModel.setName("积分商城");
                break;
            case 4:
                OkHttp.cancleMainNetWork(new String[]{TAG});
                Intent intent4=new Intent(MainPageActivity.this,UserSpaceActivity.class);
                startActivity(intent4);
                luPinModel.setName("个人空间");
                break;
            case 5:
                OkHttp.cancleMainNetWork(new String[]{TAG});
                Toast.makeText(MainPageActivity.this, "此功能暂时未开放,敬请期待.", Toast.LENGTH_SHORT).show();
                luPinModel.setName("主妇论坛");
                break;
        }
        TApplication.luPinModels.add(luPinModel);
    }

    /**
     * 帮你算dialog
     */
    private void alertDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(MainPageActivity.this).create();
        dialog.setCanceledOnTouchOutside(true);
        if (SPCache.isChecked()) {
            Intent intent = new Intent(MainPageActivity.this, CalculateSelectShopActivity.class);
            startActivity(intent);
            dialog.dismiss();
        }else {
            dialog.show();
        }
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setContentView(R.layout.activity_prompt);
        window.findViewById(R.id.title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        final TextView dialog_next=(TextView)window.findViewById(R.id.dialog_next);
        final CheckBox checkbox= (CheckBox) window.findViewById(R.id.checkbox);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                SPCache.setChecked(isChecked);
                if (SPCache.isChecked()) {
                    dialog_next.setClickable(true);
                    dialog_next.setBackgroundColor(getResources().getColor(R.color.dialog_color_sle));
                    dialog_next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(MainPageActivity.this, CalculateSelectShopActivity.class);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
                } else {
                    dialog_next.setClickable(false);
                    dialog_next.setBackgroundColor(getResources().getColor(R.color.dialog_color_unsle));
                }
            }
        });
    }
}
