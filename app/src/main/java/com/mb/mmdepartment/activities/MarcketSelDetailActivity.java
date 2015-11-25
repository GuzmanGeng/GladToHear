package com.mb.mmdepartment.activities;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.mb.mmdepartment.tools.log.Log;
import com.mb.mmdepartment.view.SwipeRefreshView;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.adapter.marcket.MarcketSelDetailAdapter;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.bean.marcketseldetail.Lists;
import com.mb.mmdepartment.bean.marcketseldetail.Root;
import com.mb.mmdepartment.biz.helpcheck.marcket_sel.MarcketDetailBiz;
import com.mb.mmdepartment.listener.MarcketSelDetailCallback;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.view.LoadingDialog;
import com.tencent.stat.StatService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

public class MarcketSelDetailActivity extends BaseActivity implements RequestListener,MarcketSelDetailCallback{
    private final String TAG=MarcketSelDetailActivity.class.getSimpleName();
    private RecyclerView marcket_sel_detail_recycle;
    private String shop_name;
    private TextView marcket_sel_detail_food_drink_tv,marcket_sel_detail_mom_boby_tv,marcket_sel_detail_family_home_tv,marcket_sel_detail_costum_skin_tv;
    private TextView[] tv_sel;
    private String keyword;
    private SwipeRefreshView swipeRefreshView;
    private LoadingDialog dialog;
    private MarcketDetailBiz biz;
    private LinearLayoutManager manager;
    private List<Lists> lists = new ArrayList<>();
    private int whickSel,page = 1,lastposition;
    private LuPinModel luPinModel;
    private RelativeLayout no_data_relative;
    private MarcketSelDetailAdapter adapter;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    if (no_data_relative.getVisibility() == View.VISIBLE) {
                        no_data_relative.setVisibility(View.GONE);
                        marcket_sel_detail_recycle.setVisibility(View.VISIBLE);
                    }
                    manager = new LinearLayoutManager(MarcketSelDetailActivity.this);
                    marcket_sel_detail_recycle.setLayoutManager(manager);
                    marcket_sel_detail_recycle.setAdapter(adapter);
                    break;
                case 1:
                    adapter.notifyDataSetChanged();
                    break;
                case 3:
                    showToast("已经加载完毕了");
                    break;
                case 10:
                    if (dialog!=null){
                        dialog.dismiss();
                    }
                    if (no_data_relative.getVisibility() == View.GONE) {
                        no_data_relative.setVisibility(View.VISIBLE);
                        marcket_sel_detail_recycle.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    };

    @Override
    public int getLayout() {
        return R.layout.activity_marcket_sel_detail;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LuPingDestory("help_Search_Result_List", "page", "end", new Date());
        TApplication.activities.remove(this);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        shop_name = getIntent().getStringExtra("shop_name");
        keyword = getIntent().getStringExtra("keyword");
        initView();
        setListeners();
        if (isNetworkConnected(this)){
            if (dialog==null) {
                dialog = new LoadingDialog(this, R.style.dialog);
                dialog.show();
                biz = new MarcketDetailBiz();
                biz.getMacketList(keyword,"3","1","50","1",TAG,this);
            }
        }
    }

    private void setListeners() {
        marcket_sel_detail_food_drink_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (whickSel != 0) {
                    setBackGround(0,1,2,3);
//                    keyword = marcket_sel_detail_food_drink_tv.getText().toString();
                    biz = new MarcketDetailBiz();
                    biz.getMacketList(keyword,"3","1","50","1",TAG,MarcketSelDetailActivity.this);
                    whickSel=0;
                }
            }
        });
        marcket_sel_detail_mom_boby_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (whickSel!=1){
                    setBackGround(1,0,2,3);
//                    keyword = marcket_sel_detail_mom_boby_tv.getText().toString();
                    biz=new MarcketDetailBiz();
                    biz.getMacketList(keyword,"3","1","50","319",TAG,MarcketSelDetailActivity.this);
                    whickSel=1;
                }
            }
        });
        marcket_sel_detail_family_home_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (whickSel != 2) {
                    setBackGround(2, 0, 1, 3);
//                    keyword = marcket_sel_detail_family_home_tv.getText().toString();
                    biz = new MarcketDetailBiz();
                    biz.getMacketList(keyword, "3", "1", "50", "387", TAG, MarcketSelDetailActivity.this);
                    whickSel = 2;
                }
            }
        });
        marcket_sel_detail_costum_skin_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (whickSel!=3){
                    setBackGround(3,0,1,2);
//                    keyword = marcket_sel_detail_costum_skin_tv.getText().toString();
                    biz=new MarcketDetailBiz();
                    biz.getMacketList(keyword,"3","1","50","491",TAG,MarcketSelDetailActivity.this);
                    whickSel=3;
                }
            }
        });
//        swipeRefreshView.setOnDragListener(new View.OnDragListener() {
//            @Override
//            public boolean onDrag(View v, DragEvent event) {
//                ++page;
//                biz.getMacketList(keyword, "3", String.valueOf(page), "50", "1", TAG, MarcketSelDetailActivity.this);
//                return false;
//            }
//        });
        swipeRefreshView.setRefreshing(false);
        marcket_sel_detail_recycle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                lastposition = manager.findLastVisibleItemPosition();
                swipeRefreshView.setRefreshing(false);
                if (newState == SCROLL_STATE_IDLE && lastposition+1 == adapter.getItemCount()) {
                    ++page;
                    biz.getMacketList(keyword, "3", String.valueOf(page), "50", "1", TAG, new RequestListener() {
                        @Override
                        public void onResponse(Response response) {
                            if (response.isSuccessful()) {
                                Gson gson = new Gson();
                                try {
                                    String json = response.body().string();
                                    json = json.replace(" ", "");
                                    if (!json.contains("[")) {
                                        handler.sendEmptyMessage(3);
                                        return;
                                    }
                                    Log.i("json",json);
                                    Root root = gson.fromJson(json, Root.class);
                                    if (root.getStatus() == OkHttp.NET_STATE) {
                                        if (root.getData().getList().size() != 0) {
                                            for (Lists list : root.getData().getList()) {
                                                lists.add(list);
                                            }
                                            Log.i("size", lists.size() + "");
                                            adapter = new MarcketSelDetailAdapter(lists, MarcketSelDetailActivity.this, 0);
                                            handler.sendEmptyMessage(1);
                                        } else {
                                            handler.sendEmptyMessage(3);
                                        }
                                    }
                                } catch (Exception e) {
                                    handler.sendEmptyMessage(10);
                                }
                            }
                        }

                        @Override
                        public void onFailue(Request request, IOException e) {
                            e.printStackTrace();
                            handler.sendEmptyMessage(3);
                        }
                    });
                }
            }
        });
        }

    /**
     * 设置选中状态
     * @param which
     * @param two
     * @param three
     * @param four
     */
    public void setBackGround(int which,int two,int three,int four){
        tv_sel[which].setBackgroundColor(getResources().getColor(R.color.text_half_red));
        tv_sel[two].setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
        tv_sel[three].setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
        tv_sel[four].setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
    }
    private void initView() {
        swipeRefreshView = (SwipeRefreshView)findViewById(R.id.swipeRefreshView);
        no_data_relative = (RelativeLayout) findViewById(R.id.no_data_relative);
        tv_sel = new TextView[4];
        marcket_sel_detail_recycle = (RecyclerView) findViewById(R.id.marcket_sel_detail_recycle);
        marcket_sel_detail_food_drink_tv = (TextView) findViewById(R.id.marcket_sel_detail_food_drink_tv);
        marcket_sel_detail_mom_boby_tv = (TextView) findViewById(R.id.marcket_sel_detail_mom_boby_tv);
        marcket_sel_detail_family_home_tv = (TextView) findViewById(R.id.marcket_sel_detail_family_home_tv);
        marcket_sel_detail_costum_skin_tv = (TextView) findViewById(R.id.marcket_sel_detail_costum_skin_tv);
        tv_sel[0] = marcket_sel_detail_food_drink_tv;
        tv_sel[1] = marcket_sel_detail_mom_boby_tv;
        tv_sel[2] = marcket_sel_detail_family_home_tv;
        tv_sel[3] = marcket_sel_detail_costum_skin_tv;
    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle(shop_name);
        action.setHomeButtonEnabled(isTrue);
    }

    @Override
    public void onResponse(Response response) {
        if (response.isSuccessful()){
            Gson gson=new Gson();
            try {
                String json=response.body().string();
                json=json.replace(" ","");
                if (!json.contains("[")) {
                    handler.sendEmptyMessage(10);
                    return;
                }
                Log.i("json",json);
                Root root=gson.fromJson(json, Root.class);
                if (root.getStatus()== OkHttp.NET_STATE){
                    if(root.getData().getList().size() != 0)
                        lists.clear();
                    for(Lists list:root.getData().getList()){
                        lists.add(list);
                    }
                    adapter = new MarcketSelDetailAdapter(lists, MarcketSelDetailActivity.this,0);
                    handler.sendEmptyMessage(0);
                }
            } catch (Exception e) {
                handler.sendEmptyMessage(10);
            }
        }
    }

    @Override
    public void onFailue(Request request, IOException e) {

    }

    @Override
    public void callBack(Lists lists) {
        LuPingWithSelectId(lists.getCategory_id(),"article","next","help_Search",lists.getSelect_shop_id(),new Date());
        Bundle bundle = new Bundle();
        bundle.putSerializable("lists",lists);
        startActivity(MarcketSelDetailActivity.this,bundle,"bundle", WaresDetailPageActivity.class);
    }
}
