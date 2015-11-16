package com.mb.mmdepartment.fragment.main.helpcheck;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.activities.HelpYouQuerySearchActivity;
import com.mb.mmdepartment.activities.MarcketSelDetailActivity;
import com.mb.mmdepartment.adapter.HelpCheckAdapter;
import com.mb.mmdepartment.bean.market_address.Description;
import com.mb.mmdepartment.bean.market_address.Root;
import com.mb.mmdepartment.biz.helpcheck.marcket_sel.MacketSelBiz;
import com.mb.mmdepartment.listener.OnMarcketSelCallback;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.view.LoadingDialog;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
/**
 * 超市选择
 */
public class MacketSelFragment extends Fragment implements RequestListener,OnMarcketSelCallback{
        private final String TAG=MacketSelFragment.class.getSimpleName();
        private RecyclerView marcket_sel_fragment_recycle;
        private HelpCheckAdapter adapter;
        private MacketSelBiz biz;
        private List<Description> list;
        private LoadingDialog dialog;
        private Handler handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        switch (msg.what){
                                case 0:
                                        dialog.dismiss();
                                        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                                        marcket_sel_fragment_recycle.setLayoutManager(manager);
                                        marcket_sel_fragment_recycle.setAdapter(adapter);
                                        break;
                                case 1:
                                        Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
                                        break;
                        }
                }
        };
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                if (dialog==null) {
                        dialog = new LoadingDialog(getActivity(), R.style.dialog);
                        biz = new MacketSelBiz();
                        biz.getMacketList(0,TAG,this);
                        dialog.show();
                }
        }
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                View view=inflater.inflate(R.layout.marcket_sel_fragment, container, false);
                marcket_sel_fragment_recycle=(RecyclerView)view.findViewById(R.id.marcket_sel_fragment_recycle);
                return view;
        }
        @Override
        public void onResponse(Response response) {
                if (response.isSuccessful()) {
                        Gson gson = new Gson();
                        try {
                                String json = response.body().string();
                                Log.e("jsons=", json);
                                final Root root = gson.fromJson(json, Root.class);
                                if ("0".equals(String.valueOf(root.getStatus()))) {
                                        list = root.getData().getList();
                                        adapter = new HelpCheckAdapter(list,this);
                                        for(int i = 0;i<list.size();i++) {
                                                TApplication.market.put(list.get(i).getShop_id(), list.get(i).getShop_name());
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
        @Override
        public void marcketSelCallBack(String keyword,String name) {
                LuPinModel luPinModel = new LuPinModel();
                luPinModel.setName(name);
                luPinModel.setState("selected");
                luPinModel.setType("shop");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                luPinModel.setOperationtime(sdf.format(new Date()));
                TApplication.luPinModels.add(luPinModel);
                ((HelpYouQuerySearchActivity) getActivity()).startActivity(getActivity(), MarcketSelDetailActivity.class, new String[]{"keyword", "shop_name"}, new String[]{keyword, name});
        }
}
