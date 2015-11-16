package com.mb.mmdepartment.fragment.main.main;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.activities.InformationDetailActivity;
import com.mb.mmdepartment.adapter.main.MainFragmentAdapter;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.main_brand.News;
import com.mb.mmdepartment.bean.main_brand.Root;
import com.mb.mmdepartment.biz.helpcheck.marcket_sel.main_new.NewBrandBiz;
import com.mb.mmdepartment.listener.AccumulateShopItemClickListener;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.overridge.MyGridLayoutManager;
import com.mb.mmdepartment.view.LoadingDialog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Administrator on 2015/8/27.
 */
public class FirstFragment extends Fragment implements RequestListener,AccumulateShopItemClickListener,SwipeRefreshLayout.OnRefreshListener{
    private final String TAG=FirstFragment.class.getSimpleName();
    private RecyclerView main_first_fragment_recycle;
    private NewBrandBiz biz;
//    private List<News> list;
    private MainFragmentAdapter adapter;
    private MyGridLayoutManager gridLayoutManager;
    private LoadingDialog dialog;
    private boolean isLoadingMore;
    private int page = 1;
    private int count;
    private List<News> datas;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    dialog.dismiss();
                    adapter.notifyDataSetChanged();
                    break;
                case 1:
                    Toast.makeText(getActivity(),"请求失败",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private FloatingActionButton loading_more;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        datas = new ArrayList<>();
        if (dialog==null){
            dialog=new LoadingDialog(getActivity(),R.style.dialog);
            dialog.show();
            biz=new NewBrandBiz();
            biz.getMacketList(page,0, 50,null, "品牌新品", this);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.main_first_fragment, container, false);
        main_first_fragment_recycle=(RecyclerView)view.findViewById(R.id.main_first_fragment_recycle);
        adapter=new MainFragmentAdapter(datas,this);
        gridLayoutManager = new MyGridLayoutManager(getActivity(), 1);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        main_first_fragment_recycle.setLayoutManager(gridLayoutManager);
        main_first_fragment_recycle.setAdapter(adapter);
        main_first_fragment_recycle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ++page;
                Log.d("tag","scroll"+page);
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.d("tag","scroll1");
                    View view = ((ScrollView) v).getChildAt(0);
                    Log.e("taggetMeasuredHeight>", view.getMeasuredHeight() + "");
                    Log.e("taggetScrollY()------->", v.getScrollY() + "");
                    //v.getHeight()可看見的控件高度
                    //v.getScrollY()在y軸方向的偏移量
                    //整個控件的高度（包括不可見的如ScrollView）
                    if (view.getMeasuredHeight() <= v.getScrollY()+ v.getHeight()) {
//                        ++page;
                        Log.i("i------->", page+"");
                        for (int i = 0; i <10; i++) {

                        }
                    }
                }
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    View view = ((ScrollView) v).getChildAt(0);
                    Log.e("taggetMeasuredHeight>", view.getMeasuredHeight() + "");
                    Log.e("taggetScrollY()------->", v.getScrollY() + "");
                }
                return false;
            }
        });


        loading_more=(FloatingActionButton)view.findViewById(R.id.loading_more);
        loading_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++page;
                if (count == datas.size()) {
                    Toast.makeText(getActivity(),"已经加载完毕喽",Toast.LENGTH_SHORT).show();
                }else {
                    biz.getMacketList(page,0, 50,null, "品牌新品", FirstFragment.this);
                }
            }
        });
        return view;
    }
    @Override
    public void onResponse(Response response) {
        if (response.isSuccessful()){
            Gson gson = new Gson();
            try {
                String json = response.body().string();
                json = json.replace(" ","");
                final Root root = gson.fromJson(json, Root.class);
                if ("0".equals(String.valueOf(root.getStatus()))) {
                    count = Integer.valueOf(root.getData().getCount());
                    for (News news : root.getData().getNews()) {
                        datas.add(news);
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
    public void onRefresh() {

    }

    @Override
    public void onFailue(Request request, IOException e) {

    }

    @Override
    public void onItemClick(View view, String content_id) {
        Intent intent=new Intent(getActivity(), InformationDetailActivity.class);
        intent.putExtra("content_id", content_id);
        startActivity(intent);
    }
}
