package com.mb.mmdepartment.fragment.main.buyplan;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.mb.mmdepartment.activities.WaresDetailPageActivity;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.activities.CalculateShowWaresInfoActivity;
import com.mb.mmdepartment.adapter.ExpandableAdapter;
import com.mb.mmdepartment.adapter.buyplan.BuyPlanAdapter;
import com.mb.mmdepartment.bean.buyplan.Root;
import com.mb.mmdepartment.bean.buyplan.byprice.DataList;
import com.mb.mmdepartment.biz.getsort.SortBiz;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.view.LoadingDialog;

import java.io.IOException;
import java.util.List;

import cn.jpush.android.api.JPushInterface;


public class SortbyDiscountFragment extends Fragment implements RequestListener {
    private static final String TAG = SortbyDiscountFragment.class.getSimpleName();
    private SortBiz biz;
    private List<DataList> list;
    private BuyPlanAdapter adapter;
    private Bundle bundle2;
    private int group;
    private LoadingDialog dialog;
    private String order_type;
    private ExpandableListView buy_plan_recycle;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    adapter = new BuyPlanAdapter(getActivity(), list, new ExpandableAdapter.CallBack() {
                        @Override
                        public void getView(View view, int groupPosition, int childPosition) {
                            CalculateShowWaresInfoActivity.setbadge();
                        }
                    });
                    adapter.setSort_group(group);
                    buy_plan_recycle.setAdapter(adapter);
                    for(int i = 0;i<list.size();i++){
                        buy_plan_recycle.expandGroup(i);
                    }
                    break;
                case 1:
                    ((CalculateShowWaresInfoActivity) getActivity()).showToast("数据请求失败");
                    break;
                case 2:
                    Log.e("error", "网络请求失败");
                    break;
            }
        }
    };

    public static SortbyDiscountFragment newInstance(String param1, String param2) {
        SortbyDiscountFragment fragment = new SortbyDiscountFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    public SortbyDiscountFragment() {
        // Required empty public constructor
    }

    public void noti(){
        adapter.notifyDataSetChanged();
    }

    public void   setBundle(Bundle bundle){
        this.bundle2 = bundle;
        String shop_id = bundle.getString("shop_id");
        String category = bundle.getString("records");
        order_type = bundle2.getString("order_type");
        group = bundle.getInt("group");
        int order = bundle.getInt("order");
//        biz = new SortBiz();
        biz.sort(TAG, JPushInterface.getRegistrationID(getActivity()), order_type,category, shop_id,  group, 50, order, this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            if (dialog == null) {
                dialog = new LoadingDialog(getActivity(), R.style.dialog);
                order_type = "desc";
                Bundle bundle = getArguments();
                String shop_id = bundle.getString("shop_id");
                String category = bundle.getString("records");
                group = bundle.getInt("group");
                int order = bundle.getInt("order");
//                biz = new SortBiz();
                biz.sort(TAG, JPushInterface.getRegistrationID(getActivity()), order_type,category, shop_id,  group, 50, order, this);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == getActivity().RESULT_OK) {
            if (dialog == null) {
                dialog = new LoadingDialog(getActivity(), R.style.dialog);
                Bundle bundle = getArguments();
                order_type = bundle.getString("order_type");
                String shop_id = bundle.getString("shop_id");
                String category = bundle.getString("records");
                group = bundle.getInt("group");
                int order = bundle.getInt("order");
//                biz = new SortBiz();
                biz.sort(TAG, JPushInterface.getRegistrationID(getActivity()), order_type,category, shop_id,  group, 50, order, this);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sortby_price, container, false);
        buy_plan_recycle = (ExpandableListView) view.findViewById(R.id.buy_plan_ell);
        buy_plan_recycle.setGroupIndicator(null);
        buy_plan_recycle.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Log.i("tag", "click child");
                Intent intent = new Intent(getActivity(), WaresDetailPageActivity.class);
                Bundle bundle3 = new Bundle();
                bundle3.putSerializable("lists", list.get(groupPosition).getList().get(childPosition));
                intent.putExtra("bundle", bundle3);
                startActivity(intent);
                return false;
            }
        });
        return view;
    }

    @Override
    public void onResponse(Response response) {
        if (response.isSuccessful()) {
            Gson gson = new Gson();
            try {
                String json = response.body().string();
                Root root = gson.fromJson(json, Root.class);
                Log.i("json",  "sort："+json );
                Log.i("sort", root.getStatus() + "sort");
                if (root.getStatus() == OkHttp.NET_STATE) {
                    list = root.getData().getList();
                    Log.i("sort", list.toString());
                }
                handler.sendEmptyMessage(0);
            } catch (IOException e) {

            }
        }

    }

    @Override
    public void onFailue(Request request, IOException e) {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Bundle bundle = getArguments();
        order_type = bundle.getString("order_type");
        String shop_id = bundle.getString("shop_id");
        String category = bundle.getString("records");
        group = bundle.getInt("group");
        int order = bundle.getInt("order");
//        biz = new SortBiz();
        biz.sort(TAG, JPushInterface.getRegistrationID(getActivity()), order_type,category, shop_id,  group, 50, order, this);
    }


}
