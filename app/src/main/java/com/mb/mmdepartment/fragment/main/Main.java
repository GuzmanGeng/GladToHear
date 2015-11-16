package com.mb.mmdepartment.fragment.main;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.activities.AccumulatedShopActivity;
import com.mb.mmdepartment.activities.HelpYouQuerySearchActivity;
import com.mb.mmdepartment.activities.InfomationSummaryActivity;
import com.mb.mmdepartment.activities.MainPageActivity;
import com.mb.mmdepartment.activities.CalculateSelectShopActivity;
import com.mb.mmdepartment.activities.UserSpaceActivity;
import com.mb.mmdepartment.adapter.FunctionAdapter;
import com.mb.mmdepartment.adapter.main.MainListViewAdapter;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.FunctionItem;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.mb.mmdepartment.bean.main_brand.News;
import com.mb.mmdepartment.bean.main_brand.Root;
import com.mb.mmdepartment.biz.helpcheck.marcket_sel.main_new.NewBrandBiz;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.overridge.MyGridLayoutManager;
import com.mb.mmdepartment.tools.sp.SPCache;
import com.mb.mmdepartment.view.MyListView;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/10/5 0005.
 */
public class Main extends Fragment implements FunctionAdapter.OnItemClickListener{
    private List<FunctionItem> functionItems;
    private FunctionItem item;
    private TextView main_new_brand_tv;
    private TextView main_release_brand_tv;
    private TextView main_story_brand_tv;
    private RecyclerView main_function_recyc;
    private FunctionAdapter adapter;
    private List<News> datas;
    private MyGridLayoutManager gridLayoutManager;
    //    private FragmentManager manager;
//    private FirstFragment firstFragment;
//    private FragmentTransaction transaction;
    private MyListView listView;
    private final String TAG=Main.class.getSimpleName();
    private int lastPosition;
    private int whichSel;
    private LuPinModel luPinModel;
    private NewBrandBiz biz;
    private MainListViewAdapter listViewAdapter;
    //    private SecondFragment secondFragment;
//    private ThirdFragment thirdFragment;
    private int page=0,count;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    setListViewHeightBasedOnChildren(listView);
                    listViewAdapter = new MainListViewAdapter(datas,getActivity());
                    listView.setAdapter(listViewAdapter);
                    break;
                case 1:
                    Toast.makeText(getActivity(),"请求失败",Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    ++count;
                    biz.getMacketList(count, 0, 50, null, "品牌新品", new RequestListener() {
                        @Override
                        public void onResponse(Response response) {
                            if (response.isSuccessful()) {
                                Gson gson = new Gson();
                                try {
                                    String json = response.body().string();
                                    json = json.replace(" ", "");
                                    Log.e("datadata", json);
                                    final Root root = gson.fromJson(json, Root.class);
                                    if ("0".equals(String.valueOf(root.getStatus()))) {
                                        if (root.getData().getNews().size() != 0) {
                                            count = Integer.valueOf(root.getData().getCount());
                                            for (News news : root.getData().getNews()) {
                                                datas.add(news);
                                            }
                                            listViewAdapter.notifyDataSetChanged();
                                        } else {
                                            handler.sendEmptyMessage(3);
                                        }
                                    }else {
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
                    });
                    break;
                case 3:
                    Toast.makeText(getActivity(),"全部加载完",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFunction();
    }
    private void getDataFunction() {
        functionItems=new ArrayList<>();
        item=new FunctionItem();
        item.setUrl(String.valueOf(R.mipmap.help_calculate));
        item.setTitle("帮你算");
        functionItems.add(item);

        item=new FunctionItem();
        item.setUrl(String.valueOf(R.mipmap.help_check));
        item.setTitle("帮你查");
        functionItems.add(item);

        item=new FunctionItem();
        item.setUrl(String.valueOf(R.mipmap.informationsummary));
        item.setTitle("资讯汇总");
        functionItems.add(item);

        item=new FunctionItem();
        item.setUrl(String.valueOf(R.mipmap.integralmall));
        item.setTitle("积分商城");
        functionItems.add(item);

        item=new FunctionItem();
        item.setUrl(String.valueOf(R.mipmap.persional_space));
        item.setTitle("个人空间");
        functionItems.add(item);

        item=new FunctionItem();
        item.setUrl(String.valueOf(R.mipmap.woman_chat));
        item.setTitle("主妇论坛");
        functionItems.add(item);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
//        listView = (MyListView)view.findViewById(R.id.main_listview);
        initView(view);
        setListeners();
        return view;
    }

    /**
     * 动态设置ListView的高度
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if(listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setListeners() {
        main_new_brand_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (whichSel!=0) {
                    if (((MainPageActivity)getActivity()).isNetworkConnected(getActivity())) {
                        OkHttp.cancleMainNetWork(new String[]{"FirstFragment"});
//                        setFragmentChose(firstFragment);
                    }else {
                        Toast.makeText(getActivity(),"网络无连接",Toast.LENGTH_LONG).show();
                    }
                    main_new_brand_tv.setTextColor(getResources().getColor(R.color.color_white));
                    main_new_brand_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
//                        firstFragment = new FirstFragment();

                    main_release_brand_tv.setTextColor(getResources().getColor(R.color.color_black));
                    main_release_brand_tv.setBackgroundColor(getResources().getColor(R.color.color_white));

                    main_story_brand_tv.setTextColor(getResources().getColor(R.color.color_black));
                    main_story_brand_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    whichSel = 0;
                }
            }
        });
        main_release_brand_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (whichSel != 1) {
                    if (((MainPageActivity) getActivity()).isNetworkConnected(getActivity())) {
                        OkHttp.cancleMainNetWork(new String[]{"SecondFragment"});
//                        secondFragment = new SecondFragment();
//                        setFragmentChose(secondFragment);
                    } else {
                        Toast.makeText(getActivity(), "网络无连接", Toast.LENGTH_LONG).show();
                    }
                    main_release_brand_tv.setTextColor(getResources().getColor(R.color.color_white));
                    main_release_brand_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));

                    main_new_brand_tv.setTextColor(getResources().getColor(R.color.color_black));
                    main_new_brand_tv.setBackgroundColor(getResources().getColor(R.color.color_white));

                    main_story_brand_tv.setTextColor(getResources().getColor(R.color.color_black));
                    main_story_brand_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    whichSel = 1;
                }
            }
        });
        main_story_brand_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (whichSel != 2) {
                    if (((MainPageActivity) getActivity()).isNetworkConnected(getActivity())) {
                        OkHttp.cancleMainNetWork(new String[]{"ThirdFragment"});
//                        thirdFragment = new ThirdFragment();
//                        setFragmentChose(thirdFragment);
                    } else {
                        Toast.makeText(getActivity(), "网络无连接", Toast.LENGTH_LONG).show();
                    }
                    main_story_brand_tv.setTextColor(getResources().getColor(R.color.color_white));
                    main_story_brand_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));

                    main_new_brand_tv.setTextColor(getResources().getColor(R.color.color_black));
                    main_new_brand_tv.setBackgroundColor(getResources().getColor(R.color.color_white));

                    main_release_brand_tv.setTextColor(getResources().getColor(R.color.color_black));
                    main_release_brand_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    whichSel = 2;
                }
            }
        });
    }

    /**
     * 初始化界面
     */
    private void initView(View view) {

        main_new_brand_tv = (TextView) view.findViewById(R.id.main_new_brand_tv);
        main_release_brand_tv = (TextView) view.findViewById(R.id.main_release_brand_tv);
        main_story_brand_tv = (TextView) view.findViewById(R.id.main_story_brand_tv);
        main_function_recyc = (RecyclerView) view.findViewById(R.id.main_function_recyc);
        adapter = new FunctionAdapter(functionItems, this);

        gridLayoutManager = new MyGridLayoutManager(getActivity(),3);
        gridLayoutManager.setOrientation(MyGridLayoutManager.VERTICAL);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        main_function_recyc.setLayoutManager(gridLayoutManager);

        main_function_recyc.setAdapter(adapter);
        datas = new ArrayList<>();
        biz=new NewBrandBiz();
        biz.getMacketList(page, 0, 50, null, "品牌新品", new RequestListener() {
            @Override
            public void onResponse(Response response) {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    try {
                        String json = response.body().string();
                        json = json.replace(" ", "");
                        Log.e("datadata", json);
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
            public void onFailue(Request request, IOException e) {

            }
        });
//            manager = getChildFragmentManager();
//            if (((MainPageActivity)getActivity()).isNetworkConnected(view.getContext())) {
//                firstFragment = new FirstFragment();
//                setFragmentChose(firstFragment);
//            }
        main_new_brand_tv.setTextColor(getResources().getColor(R.color.color_white));
        main_new_brand_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));

//        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                if (scrollState == SCROLL_STATE_IDLE
//                        && lastPosition + 1 == adapter.getItemCount()) {
//                    handler.sendEmptyMessageDelayed(2, 2000);
//                }
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//            }
//        });
        listView.setOnRefreshListener(new MyListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("tag","onrefesh");
                handler.sendEmptyMessageDelayed(2, 2000);
            }

            @Override
            public void onLoadMore() {
                Log.i("tag","onloadmore");
                handler.sendEmptyMessageDelayed(4, 2000);
            }
        });
    }

    /**
     * 设置Fragment
     * @param fragment
     */
    private void setFragmentChose(Fragment fragment) {
//        transaction = manager.beginTransaction();
//        transaction.replace(R.id.main_content,fragment);
//        transaction.commit();
    }

    public void loadingmore(){
        Bundle bundle = new Bundle();
//        manager = getChildFragmentManager();
        String loading_more = "loading_more";
        bundle.putString(loading_more,loading_more);
//        if(whichSel == 0){
//            firstFragment = new FirstFragment();
//            firstFragment.setArguments(bundle);
//            setFragmentChose(firstFragment);
//        }else if(whichSel == 1){
//            secondFragment = new SecondFragment();
//            secondFragment.setArguments(bundle);
//            setFragmentChose(secondFragment);
//        }else{
//            thirdFragment = new ThirdFragment();
//            thirdFragment.setArguments(bundle);
//            setFragmentChose(thirdFragment);
//        }
    }


    @Override
    public void onClick(View view, int position) {
        luPinModel = new LuPinModel();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        luPinModel.setOperationtime(sdf.format(new Date()));
        luPinModel.setType("other");
        luPinModel.setState("next");
        switch (position){
            case 0:
                OkHttp.cancleMainNetWork(new String[]{TAG});
                alertDialog();
                luPinModel.setName("helpYouCount");
                break;
            case 1:
                OkHttp.cancleMainNetWork(new String[]{TAG});
                Intent intent1=new Intent(getActivity(),HelpYouQuerySearchActivity.class);
                startActivity(intent1);
                luPinModel.setName("helpYouSerach");
                break;
            case 2:
                OkHttp.cancleMainNetWork(new String[]{TAG});
                Intent intent5=new Intent(getActivity(),InfomationSummaryActivity.class);
                startActivity(intent5);
                luPinModel.setName("Informationcollect");
                break;
            case 3:
                OkHttp.cancleMainNetWork(new String[]{TAG});
                Intent intent3=new Intent(getActivity(),AccumulatedShopActivity.class);
                startActivity(intent3);
                luPinModel.setName("积分商城");
                break;
            case 4:
                OkHttp.cancleMainNetWork(new String[]{TAG});
                Intent intent4=new Intent(getActivity(),UserSpaceActivity.class);
                startActivity(intent4);
                luPinModel.setName("个人空间");
                break;
            case 5:
                OkHttp.cancleMainNetWork(new String[]{TAG});
                Toast.makeText(getActivity(), "此功能暂时未开放,敬请期待.", Toast.LENGTH_SHORT).show();
                luPinModel.setName("主妇论坛");
                break;
        }
        TApplication.luPinModels.add(luPinModel);
    }

    /**
     * 帮你算dialog
     */
    private void alertDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
        dialog.setCanceledOnTouchOutside(true);
        if (SPCache.isChecked()) {
            Intent intent = new Intent(getActivity(), CalculateSelectShopActivity.class);
            startActivity(intent);
            dialog.dismiss();
        }else {
            dialog.show();
        }
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setContentView(R.layout.activity_prompt);
        window.findViewById(R.id.title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        final TextView dialog_next=(TextView)window.findViewById(R.id.dialog_next);
        final CheckBox checkbox= (CheckBox) window.findViewById(R.id.checkbox);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                SPCache.setChecked(isChecked);
                if (SPCache.isChecked()) {
                    dialog_next.setClickable(true);
                    dialog_next.setBackgroundColor(getResources().getColor(R.color.dialog_color_sle));
                    dialog_next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), CalculateSelectShopActivity.class);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
                } else {
                    dialog_next.setClickable(false);
                    dialog_next.setBackgroundColor(getResources().getColor(R.color.dialog_color_unsle));
                }
            }
        });
    }
}
