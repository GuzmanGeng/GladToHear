package com.mb.mmdepartment.fragment.main.informationcollection;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.mb.mmdepartment.activities.InformationDetailActivity;
import com.mb.mmdepartment.listener.AccumulateShopItemClickListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.activities.InfomationSummaryActivity;
import com.mb.mmdepartment.adapter.informationcollection.InformationCollctionAdapter;
import com.mb.mmdepartment.bean.informationcollaction.Description;
import com.mb.mmdepartment.bean.informationcollaction.Root;
import com.mb.mmdepartment.biz.informationcollection.InformationCollectionBiz;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.view.LoadingDialog;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2015/9/18 0018.
 */
public class FamilyHomeFragment extends Fragment implements RequestListener,AccumulateShopItemClickListener {
    private final String TAG=FamilyHomeFragment.class.getSimpleName();
    private InformationCollectionBiz biz;
    private RecyclerView information_collection_recycle;
    private InformationCollctionAdapter adapter;
    private List<Description> list;
    private LoadingDialog dialog;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    if (dialog!=null){
                        dialog.dismiss();
                    }
                    LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                    information_collection_recycle.setLayoutManager(manager);
                    information_collection_recycle.setAdapter(adapter);
                    break;
                case 1:
                    ((InfomationSummaryActivity)getActivity()).showToast("数据请求失败");
                    break;
                case 2:
                    Log.e("error", "网络请求失败");
                    break;
            }

        }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (dialog==null){
            dialog=new LoadingDialog(getActivity(),R.style.dialog);
            biz=new InformationCollectionBiz();
            biz.getInformation(0, 3, 1, 10, TAG, this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.information_collection_fragment,container,false);
        information_collection_recycle= (RecyclerView)view.findViewById(R.id.information_collection_recycle);
        return view;
    }

    @Override
    public void onResponse(Response response) {
        if (response.isSuccessful()){
            Gson gson = new Gson();
            try {
                String json = response.body().string();
                Log.e("first", json);
                final Root root = gson.fromJson(json, Root.class);
                if ("0".equals(String.valueOf(root.getStatus()))) {
                    list = root.getData().getList();
                    Log.e("size",list.size()+"");
                    adapter=new InformationCollctionAdapter(list,this);
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
    public void onItemClick(View view, String content_id) {
        Intent intent=new Intent(getActivity(), InformationDetailActivity.class);
        intent.putExtra("content_id", content_id);
        startActivity(intent);
    }

    @Override
    public void onFailue(Request request, IOException e) {
        handler.sendEmptyMessage(2);
    }
}
