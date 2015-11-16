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
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.activities.ExchangePrizeDetailActivity;
import com.mb.mmdepartment.activities.LoginActivity;
import com.mb.mmdepartment.activities.UserSpaceActivity;
import com.mb.mmdepartment.adapter.userspace.ExchangePrizeRecordAdapter;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.userspace.listrecord.getexchangeprizerecord.Exchange;
import com.mb.mmdepartment.bean.userspace.listrecord.getexchangeprizerecord.Root;
import com.mb.mmdepartment.biz.userspace.listrecord.exchangeprizerecord.ExchangePrizeRecordBiz;
import com.mb.mmdepartment.listener.ExchangePrizeRecordItemClickListener;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.tools.log.Log;
import com.mb.mmdepartment.view.LoadingDialog;

import java.io.IOException;
import java.util.List;
public class ExchangePrizeRecordFragment extends Fragment implements RequestListener,ExchangePrizeRecordItemClickListener {
    private final String TAG = ExchangePrizeRecordFragment.class.getSimpleName();
    private ExchangePrizeRecordBiz biz;
    private RecyclerView userspace_list_record_recycle;
    private LoadingDialog dialog;
    private List<Exchange> list;
    private ExchangePrizeRecordAdapter adapter;
    private LinearLayoutManager manager;
    private RelativeLayout not_login;
    private TextView list_record_login,list_record_price;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    if (dialog!=null){
                        dialog.dismiss();
                    }
                    Log.i("length",list.size()+"");
                    adapter = new ExchangePrizeRecordAdapter(list, ExchangePrizeRecordFragment.this);
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
                biz = new ExchangePrizeRecordBiz();
                biz.getExchangePrizeRecord(TApplication.user_id, this);
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
                    biz = new ExchangePrizeRecordBiz();
                    biz.getExchangePrizeRecord(TApplication.user_id, this);
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
        list_record_login = (TextView) view.findViewById(R.id.list_record_login);
        list_record_price = (TextView) view.findViewById(R.id.list_record_price);
        list_record_price.setText("商品");
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
                    list = root.getData().getExchanges();
                    Log.i("list",list.toString());
                    handler.sendEmptyMessage(0);
                } else {
                    handler.sendEmptyMessage(1);
                }
            }catch (Exception e) {

            }
        }
    }

    @Override
    public void onFailue(Request request, IOException e) {

    }

    @Override
    public void onItemClick(View view, Exchange exchange) {
        Intent intent = new Intent(getActivity(), ExchangePrizeDetailActivity.class);
        intent.putExtra("exchange",exchange);
        ((UserSpaceActivity) getActivity()).startActivity(intent);
    }
}
