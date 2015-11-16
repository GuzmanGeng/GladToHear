package com.mb.mmdepartment.activities;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.adapter.marcket.MarcketSelDetailAdapter;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.bean.marcketseldetail.Lists;
import com.mb.mmdepartment.bean.marcketseldetail.Root;
import com.mb.mmdepartment.biz.helpcheck.marcket_sel.searchlist.BrandSearchListBiz;
import com.mb.mmdepartment.listener.MarcketSelDetailCallback;
import com.mb.mmdepartment.listener.RequestListener;
import com.tencent.stat.StatService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

public class ShowWaresInfoActivity extends BaseActivity implements RequestListener,MarcketSelDetailCallback,SwipeRefreshLayout.OnRefreshListener{

    private SwipeRefreshLayout swipeRefreshView;
    private RecyclerView search_list_recycle;
    private String keyword;
    private boolean catlog;
    private LuPinModel luPinModel;
    private BrandSearchListBiz biz;
    private MarcketSelDetailAdapter adapter;
    private LinearLayoutManager manager;
    private int count=1;

    private List<Lists> lists;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adapter.notifyDataSetChanged();
                    break;
                case 1:
                    showToast("网络无连接");
                    break;
                case 2://上拉加载
                    ++count;
                    if (catlog) {
                        biz.getSearchList(keyword,"2",String.valueOf(count),"50",ShowWaresInfoActivity.this);
                    }else {
                        biz.getSearchList(keyword,"1",String.valueOf(count),"50",ShowWaresInfoActivity.this);
                    }
                    swipeRefreshView.setRefreshing(false);
                    break;
                case 3:
                    showToast("已经加载完毕了");
                    swipeRefreshView.setRefreshing(false);
                    break;
                case 10:
                    break;
            }
        }
    };
    private int lastPosition;

    @Override
    public int getLayout() {
        return R.layout.activity_search_list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        luPinModel = new LuPinModel();
        luPinModel.setName("shopdetailactivity");
        luPinModel.setType("page");
        luPinModel.setState("end");
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        luPinModel.setOperationtime(sdf.format(new Date()));
        StatService.onResume(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        luPinModel.setEndtime(sdf.format(new Date()));
        TApplication.luPinModels.add(luPinModel);
        StatService.onPause(this);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        keyword=getIntent().getStringExtra("keyword");
        catlog = getIntent().getBooleanExtra("catlog", false);
        manager = new LinearLayoutManager(ShowWaresInfoActivity.this);
        lists = new ArrayList<>();
        adapter=new MarcketSelDetailAdapter(lists, this, 1);

        initView();
        initListener();
    }

    private void initListener() {
        search_list_recycle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == SCROLL_STATE_IDLE
                        && lastPosition + 1 == adapter.getItemCount()) {
                    swipeRefreshView.setRefreshing(true);
                    // 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
                    handler.sendEmptyMessageDelayed(2, 2000);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.e("pisition", lastPosition+"");
                lastPosition=manager.findLastVisibleItemPosition();
            }
        });
    }

    private void initView() {
        search_list_recycle = (RecyclerView) findViewById(R.id.search_list_recycle);
        search_list_recycle.setLayoutManager(manager);
        search_list_recycle.setAdapter(adapter);
        swipeRefreshView=(SwipeRefreshLayout)findViewById(R.id.swipeRefreshView);
        swipeRefreshView.setOnRefreshListener(this);
        if (isNetworkConnected(this)) {
            biz=new BrandSearchListBiz();
            if (catlog) {
                biz.getSearchList(keyword,"2","1","50",this);
            }else {
                biz.getSearchList(keyword,"1","1","50",this);
            }
        }else {
            handler.sendEmptyMessage(2);
        }
    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        if (catlog) {
            String searchName = getIntent().getStringExtra("searchName");
            action.setTitle(searchName);
        }else {
            action.setTitle(keyword);
        }
        action.setHomeButtonEnabled(isTrue);
    }

    @Override
    public void onResponse(Response response) {
        if (response.isSuccessful()) {
            try {
                String json = response.body().string();
                Log.e("searchlist", json);
                if (!json.contains("[")) {
                    handler.sendEmptyMessage(10);
                    return;
                }
                Gson gson = new Gson();
                Root root = gson.fromJson(json, Root.class);
                if (root.getData().getList().size()==0){
                    Log.e("data","000000000000");
                    handler.sendEmptyMessage(3);
                }else {
                    for (Lists data : root.getData().getList()) {
                        lists.add(data);
                    }
                    handler.sendEmptyMessage(0);
                }
            } catch (IOException e) {

            }
        }
    }

    @Override
    public void onFailue(Request request, IOException e) {

    }

    @Override
    public void callBack(Lists lists) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("lists", lists);
        LuPinModel luPinModel_goods_detail = new LuPinModel();
        luPinModel_goods_detail.setType("article");
        luPinModel_goods_detail.setName(lists.getId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
        luPinModel_goods_detail.setOperationtime(sdf.format(new Date()));
        luPinModel_goods_detail.setState("next");
        TApplication.luPinModels.add(luPinModel_goods_detail);
        startActivity(this, bundle, "bundle", WaresDetailPageActivity.class);
    }

    @Override
    public void onRefresh() {
        if (lastPosition==0) {
            swipeRefreshView.setRefreshing(false);
        }
    }
}
