package com.mb.mmdepartment.activities;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.biz.main_search.MainSearchDetailList;
import com.mb.mmdepartment.listener.NoMoreDataListener;
import com.mb.mmdepartment.listener.OnRefreshListener;
import com.mb.mmdepartment.tools.sp.SPCache;
import com.mb.mmdepartment.view.RefreshListView;

import java.util.Date;

public class SearchListActivity extends BaseActivity implements OnRefreshListener,NoMoreDataListener{
    private RefreshListView main_search_list;
    private MainSearchDetailList biz;
    private ImageView go_to_top;
    private String keyWord;
    private int state=1;
    private int page=1;
    private boolean haveNoData;

    @Override
    public int getLayout() {
        return R.layout.activity_search_list_show;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        keyWord = getIntent().getStringExtra("keyword");
        initView();
        biz = new MainSearchDetailList(this,main_search_list,this);
        initListener();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LuPingDestory("infomation_Summary", "page", "end", new Date());
        TApplication.activities.remove(this);
    }

    /**
     * 初次加载数据 或者是下拉刷新数据
     */
    private void initData() {
        biz.getSearchList("1",page,SPCache.getString("city_id","50"),keyWord,state);
    }

    private void initListener() {
        main_search_list.setOnRefreshListener(this);
        go_to_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_search_list.smoothScrollToPositionFromTop(0, 10);
            }
        });
    }

    private void initView() {
        main_search_list = (RefreshListView) findViewById(R.id.main_search_list);
        go_to_top = (ImageView) findViewById(R.id.go_to_top);
    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("搜索结果");
        action.setHomeButtonEnabled(isTrue);
    }

    @Override
    public void onDownPullRefresh() {
        page=1;
        state=1;
        initData();
        haveNoData=false;
    }

    @Override
    public void onLoadingMore() {
        if (haveNoData) {
            main_search_list.hideFooterView();
            showToast("没有更多数据了");
        } else {
            ++page;
            state = 0;
            initData();
        }
    }

    @Override
    public void onScroll(int firstVisibleItem, int visibleItemCount) {
        if (firstVisibleItem >= 8) {
            go_to_top.setVisibility(View.VISIBLE);
        } else {
            go_to_top.setVisibility(View.GONE);
        }
    }

    @Override
    public void haveNoMoreData() {
        haveNoData=true;
        main_search_list.hideFooterView();
    }
}
