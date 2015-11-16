package com.mb.mmdepartment.biz.main_search;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import com.google.gson.Gson;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.adapter.main_search.HotDataAdapter;
import com.mb.mmdepartment.bean.main_search.HotRoot;
import com.mb.mmdepartment.bean.main_search.Market;
import com.mb.mmdepartment.bean.main_search.Tags;
import com.mb.mmdepartment.bean.main_search.UserKeyword;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.constans.CatlogConsts;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.overridge.MyGridLayoutManager;
import com.mb.mmdepartment.tools.log.Log;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by joyone2one on 2015/11/13.
 */
public class MainSearchHotBiz {
    private RecyclerView.Adapter adapter_tag,adapter_markets,adapter_userKey;
    private Map<String, String> paramas = new HashMap<>();
    private List<String> tags;
    private List<String> markets;
    private List<String> userkeywords;
    private MyGridLayoutManager manager_hot_brand;
    private MyGridLayoutManager manager_hot_channel;
    private MyGridLayoutManager manager;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adapter_tag.notifyDataSetChanged();
                    break;
                case 1:
                    adapter_markets.notifyDataSetChanged();
                    break;
                case 2:
                    adapter_userKey.notifyDataSetChanged();
                    break;
            }
        }
    };
    public MainSearchHotBiz(Context context,RecyclerView[] recycs) {
        tags = new ArrayList<>();
        markets = new ArrayList<>();
        userkeywords = new ArrayList<>();
        manager = new MyGridLayoutManager(context, 3);
        manager_hot_brand = new MyGridLayoutManager(context, 3);
        manager_hot_channel = new MyGridLayoutManager(context, 3);
        adapter_tag = new HotDataAdapter(tags);
        adapter_markets = new HotDataAdapter(markets);
        adapter_userKey = new HotDataAdapter(userkeywords);
        recycs[0].setLayoutManager(manager);
        recycs[1].setLayoutManager(manager_hot_brand);
        recycs[2].setLayoutManager(manager_hot_channel);
        recycs[0].setAdapter(adapter_tag);
        recycs[1].setAdapter(adapter_markets);
        recycs[2].setAdapter(adapter_userKey);

    }
    public void getHotBrand(String user_id) {
        paramas.put(BaseConsts.APP, CatlogConsts.TagList.params_app);
        paramas.put(BaseConsts.CLASS, CatlogConsts.TagList.params_class);
        paramas.put(BaseConsts.SIGN, CatlogConsts.TagList.params_sign);
        paramas.put("userid", user_id);
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }
            @Override
            public void onResponse(Response response) throws IOException {
                Gson gson=new Gson();
                if (response.isSuccessful()) {
                    String json=response.body().string();
                    HotRoot root = gson.fromJson(json, HotRoot.class);
                    if (root.getData().getTags().size()!=0) {
                        for (Tags tag:root.getData().getTags()) {
                            tags.add(tag.getTag_name());
                        }
                        handler.sendEmptyMessage(0);
                    }
                    if (root.getData().getMarket().size() != 0) {
                        for (Market market:root.getData().getMarket()) {
                            markets.add(market.getMarket_name());
                        }
                        handler.sendEmptyMessage(1);
                    }
                    if (root.getData().getUserkeyword().size() != 0) {
                        for (UserKeyword keyword:root.getData().getUserkeyword()) {
                            userkeywords.add(keyword.getKeyword());
                        }
                        handler.sendEmptyMessage(2);
                    }
                }
            }
        });
    }
}
