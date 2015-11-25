package com.mb.mmdepartment.biz.main_search;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.mb.mmdepartment.activities.InformationDetailActivity;
import com.mb.mmdepartment.adapter.main.MainListViewAdapter;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.bean.main_brand.News;
import com.mb.mmdepartment.bean.main_brand.Root;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.constans.CatlogConsts;
import com.mb.mmdepartment.listener.NoMoreDataListener;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.view.RefreshListView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by joyone2one on 2015/11/13.
 */
public class MainSearchDetailList {
    private Map<String, String> paramas = new HashMap<>();
    private MainListViewAdapter adapter;
    private List<News> datas;
    private int PULL_STATE;
    private RefreshListView refreshListView;
    private Context context;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                    if (PULL_STATE == 0) {
                        refreshListView.hideFooterView();
                    }else if (PULL_STATE==1){
                        refreshListView.hideHeaderView();
                    }
                    break;
                case 1:
                    Toast.makeText(context, "网络数据异常", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    refreshListView.setVisibility(View.GONE);
                    Toast.makeText(context, "您搜索的内容暂时没有数据", Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    listener.haveNoMoreData();
                    break;
                case 10:
                    Toast.makeText(context, "服务器正在维护,请稍后访问", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private int count;
    private NoMoreDataListener listener;
    public MainSearchDetailList(final Context context,RefreshListView listView,NoMoreDataListener listener) {
        this.context=context;
        this.refreshListView=listView;
        this.listener=listener;
        datas = new ArrayList<>();
        adapter = new MainListViewAdapter(datas,context);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((BaseActivity)context).LuPingWithSource(datas.get(position).getContent_id(),"other","next","main_search",new Date());
                Intent intent = new Intent(context, InformationDetailActivity.class);
                intent.putExtra("content_id", datas.get(position).getContent_id());
                context.startActivity(intent);
            }
        });
    }
    /**
     * 获取搜索列表
     * @param user_id
     * @param page
     * @param city_id
     * @param keyword
     */
    public void getSearchList(String user_id,int page,String city_id,String keyword,final int pull_state) {
        PULL_STATE=pull_state;
        paramas.put(BaseConsts.APP, CatlogConsts.Brand.params_app);
        paramas.put(BaseConsts.CLASS, CatlogConsts.Brand.params_class);
        paramas.put(BaseConsts.SIGN, CatlogConsts.Brand.params_sign);
        paramas.put("page", String.valueOf(page));
        paramas.put("userid", user_id);
        paramas.put("city_id", city_id);
        paramas.put("keyword", keyword);
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }
            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    Gson gson=new Gson();
                    try {
                        String json=response.body().string();
                        try {
                            JSONObject object = new JSONObject(json);
                            if (object.getInt("status") == 0) {
                                Root root=null;
                                if (json.contains("[")) {
                                    root = gson.fromJson(json, Root.class);
                                } else {
                                    handler.sendEmptyMessage(2);
                                    return;
                                }
                                //代表是上拉加载更多的操作
                                if (PULL_STATE == 0) {
                                    for (News news : root.getData().getNews()) {
                                        datas.add(news);
                                    }
                                    if (count == datas.size()) {
                                        handler.sendEmptyMessage(5);
                                    }
                                } else {
                                    datas.clear();
                                    count = Integer.valueOf(root.getData().getCount());
                                    for (News news : root.getData().getNews()) {
                                        datas.add(news);
                                    }
                                }
                                handler.sendEmptyMessage(0);
                            } else {
                                handler.sendEmptyMessage(1);
                            }
                        } catch (JSONException e) {
                            handler.sendEmptyMessage(1);
                        }
                    } catch (IOException e) {
                        handler.sendEmptyMessage(10);
                    }

                }
            }
        });
    }
}
