package com.mb.mmdepartment.fragment.main.userspace;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mb.mmdepartment.base.BaseActivity;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.activities.OrderDetailActivity;
import com.mb.mmdepartment.activities.LoginActivity;
import com.mb.mmdepartment.activities.UserSpaceActivity;
import com.mb.mmdepartment.adapter.userspace.ListRecordAdapter;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.userspace.listrecord.Orders;
import com.mb.mmdepartment.bean.userspace.listrecord.Root;
import com.mb.mmdepartment.biz.userspace.listrecord.ListRecordBiz;
import com.mb.mmdepartment.listener.AccumulateShopItemClickListener;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.view.LoadingDialog;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import cn.jpush.android.api.JPushInterface;
public class ListRecordFragment extends Fragment implements RequestListener,AccumulateShopItemClickListener{
    private final String TAG = ListRecordFragment.class.getSimpleName();
    private ListRecordBiz biz;
    private RecyclerView userspace_list_record_recycle;
    private LoadingDialog dialog;
    private List<Orders> list;
    private ListRecordAdapter adapter;
    private LinearLayoutManager manager;
    private RelativeLayout not_login,not_data;
    private TextView list_record_login;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    if (dialog!=null){
                        dialog.dismiss();
                    }
                    manager = new LinearLayoutManager(getActivity());
                    userspace_list_record_recycle.setLayoutManager(manager);
                    userspace_list_record_recycle.setAdapter(adapter);
                    break;
                case 1:
                    ((UserSpaceActivity)getActivity()).showToast("请求出错，请检查网络异常");
                    break;
            }
        }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!TextUtils.isEmpty(TApplication.user_id)) {
            if (dialog==null) {
                dialog = new LoadingDialog(getActivity(), R.style.dialog);
                dialog.show();
                biz = new ListRecordBiz();
                biz.getListRecord(String.valueOf(1),TApplication.user_id, JPushInterface.getRegistrationID(getActivity()),TAG,this);
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == getActivity().RESULT_OK) {
            if (!TextUtils.isEmpty(TApplication.user_id)) {
                not_login.setVisibility(View.INVISIBLE);
                if (dialog == null) {
                    dialog = new LoadingDialog(getActivity(), R.style.dialog);
                    dialog.show();
                    biz = new ListRecordBiz();
                    biz.getListRecord(String.valueOf(1), TApplication.user_id, JPushInterface.getRegistrationID(getActivity()), TAG, ListRecordFragment.this);
                }
            }
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.userspace_list_record_fragment,container,false);
        userspace_list_record_recycle = (RecyclerView) view.findViewById(R.id.userspace_list_record_recycle);
        not_login = (RelativeLayout) view.findViewById(R.id.not_login);
        not_data = (RelativeLayout) view.findViewById(R.id.no_data);
        list_record_login = (TextView) view.findViewById(R.id.list_record_login);
        if (!TextUtils.isEmpty(TApplication.user_id)) {
            not_login.setVisibility(View.INVISIBLE);
        }else {
            list_record_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.putExtra("login", true);
                    startActivityForResult(intent, 200);
                }
            });
        }
        return view;
    }

    @Override
    public void onResponse(Response response) {
        if (response.isSuccessful()) {
            Gson gson = new Gson();
            try {
               String json = response.body().string();
                Root root = gson.fromJson(json, Root.class);
                if (root.getStatus() == OkHttp.NET_STATE) {
                    list=root.getData().getOrders();
                    adapter=new ListRecordAdapter(list,this);
                    handler.sendEmptyMessage(0);
                }else {
                    handler.sendEmptyMessage(1);
                }
            }catch (Exception e){
                dialog.dismiss();
                not_data.setVisibility(View.VISIBLE);

            }
        }
    }

    @Override
    public void onFailue(Request request, IOException e) {

    }

    @Override
    public void onItemClick(View view, String oNumber) {
        ((BaseActivity)getActivity()).LuPing(oNumber,"other","next",new Date());
        Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
        intent.putExtra("onumber",oNumber);
        ((UserSpaceActivity) getActivity()).startActivity(intent);
    }
}
