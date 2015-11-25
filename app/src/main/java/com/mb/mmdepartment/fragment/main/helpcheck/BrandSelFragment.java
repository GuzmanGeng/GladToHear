package com.mb.mmdepartment.fragment.main.helpcheck;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.activities.HelpYouQuerySearchActivity;
import com.mb.mmdepartment.activities.WaresDetailPageActivity;
import com.mb.mmdepartment.activities.ShowWaresInfoActivity;
import com.mb.mmdepartment.adapter.catlogs.GoodsListAdapter;
import com.mb.mmdepartment.adapter.marcket.MarcketSelDetailAdapter;
import com.mb.mmdepartment.bean.helpcheck.brandsel.Root;
import com.mb.mmdepartment.bean.helpcheck.brandsel.Type;
import com.mb.mmdepartment.bean.marcketseldetail.Lists;
import com.mb.mmdepartment.biz.helpcheck.marcket_sel.brand_sel.BrandSelBiz;
import com.mb.mmdepartment.listener.MarcketSelDetailCallback;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.overridge.MyGridLayoutManager;
import com.mb.mmdepartment.view.LoadingDialog;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *品牌选择
 */
public class BrandSelFragment extends Fragment implements RequestListener,GoodsListAdapter.OnItemClickListener,MarcketSelDetailCallback{
    private final String TAG = BrandSelFragment.class.getSimpleName();
    private List<Type> types;
    private List<Lists> lists;
    private List<String> titles;
    private RecyclerView brand_hot_search_recycle;
    private LoadingDialog dialog;
    private BrandSelBiz biz;
    private GoodsListAdapter adapter;
    private GridLayoutManager manager;
    private MarcketSelDetailAdapter marcketSelDetailAdapter;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    dialog.dismiss();
                    manager = new MyGridLayoutManager(getActivity(),3);
                    brand_hot_search_recycle.setLayoutManager(manager);
                    brand_hot_search_recycle.setAdapter(adapter);
                    MyGridLayoutManager linearManager = new MyGridLayoutManager(getActivity(),1);
                    brand_sel_recycle.setLayoutManager(linearManager);
                    brand_sel_recycle.setAdapter(marcketSelDetailAdapter);
                    break;
                case 1:
                    Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    break;
            }
        }
    };
    private RecyclerView brand_sel_recycle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (dialog==null){
            dialog=new LoadingDialog(getActivity(),R.style.dialog);
            biz = new BrandSelBiz();
            biz.getHotSearch(1,TAG,this);
            dialog.show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.brand_sel_fragment, container, false);
        lists = new ArrayList<>();
        types = new ArrayList<>();
        brand_hot_search_recycle = (RecyclerView) view.findViewById(R.id.brand_hot_search_recycle);
        brand_sel_recycle = (RecyclerView) view.findViewById(R.id.brand_sel_recycle);
        setlistener();
        return view;
    }

    private void setlistener(){

    }

    @Override
    public void onResponse(Response response) {
        if (response.isSuccessful()) {
            Gson gson=new Gson();
            try {
                String json=response.body().string();
                Root root = gson.fromJson(json, Root.class);
                int state=root.getStatus();
                if (OkHttp.NET_STATE==state) {
                    if((root.getData().getTypes().size()==0)||(root.getData().getLists().size()==0)) {
                        handler.sendEmptyMessage(3);
                    } else {
                        for (Lists data : root.getData().getLists()) {
                            lists.add(data);
                        }
                        for (Type type : root.getData().getTypes()) {
                            types.add(type);
                        }
                        titles = new ArrayList<>();
                        for (int i = 0; i < types.size(); i++) {
                            titles.add(types.get(i).getSmall_category());
                        }
                        marcketSelDetailAdapter = new MarcketSelDetailAdapter(lists, this, 2);
                        adapter = new GoodsListAdapter(titles, this);
                        handler.sendEmptyMessage(0);
                    }
                }else {
                    handler.sendEmptyMessage(1);
                }
            } catch (IOException e) {
                handler.sendEmptyMessage(1);
            }
        }
    }

    @Override
    public void onFailue(Request request, IOException e) {

    }

    @Override
    public void onClick(View view) {
        ((BaseActivity)getActivity()).LuPingWithSource(((TextView) view).getText().toString(), "brand", "next","help_Search", new Date());
        Intent intent = new Intent(getActivity(), ShowWaresInfoActivity.class);
        intent.putExtra("keyword",((TextView) view).getText().toString());
        intent.putExtra("catlog", false);
        startActivity(intent);
    }

    @Override
    public void callBack(Lists lists) {
        ((BaseActivity)getActivity()).LuPingWithSelectId(lists.getId(),"article","next","help_Search",lists.getSelect_shop_id(),new Date());
        Bundle bundle = new Bundle();
        bundle.putSerializable("lists", lists);
        ((HelpYouQuerySearchActivity) getActivity()).startActivity(getActivity(), bundle, "bundle", WaresDetailPageActivity.class);
    }

}
