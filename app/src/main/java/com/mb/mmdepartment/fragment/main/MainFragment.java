package com.mb.mmdepartment.fragment.main;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.activities.AccumulatedShopActivity;
import com.mb.mmdepartment.activities.CalculateSelectShopActivity;
import com.mb.mmdepartment.activities.HelpYouQuerySearchActivity;
import com.mb.mmdepartment.activities.InfomationSummaryActivity;
import com.mb.mmdepartment.activities.InformationDetailActivity;
import com.mb.mmdepartment.activities.MainActivity;
import com.mb.mmdepartment.activities.UserSpaceActivity;
import com.mb.mmdepartment.adapter.main.MainListViewAdapter;
import com.mb.mmdepartment.bean.main_brand.News;
import com.mb.mmdepartment.bean.main_brand.Root;
import com.mb.mmdepartment.biz.helpcheck.marcket_sel.main_new.NewBrandBiz;
import com.mb.mmdepartment.listener.OnRefreshListener;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.tools.sp.SPCache;
import com.mb.mmdepartment.view.RefreshListView;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joyone2one on 2015/11/11.
 */
public class MainFragment extends Fragment implements View.OnClickListener,RequestListener,OnRefreshListener{
    private RefreshListView main_page_listview;
    private TextView help_calculate_tv,help_check_tv,informationsummary_tv,integralmall_tv,persional_space_tv,woman_chat_tv,main_new_brand_tv,main_release_brand_tv,main_story_brand_tv;
    private int whichSel=10;
    private ProgressDialog dialog;
    private List<News> datas;
    private NewBrandBiz biz;
    private int page=1;
    private int PULL_STATE=1;//记录当前状态上拉刷新
    private ImageView go_top;
    private MainListViewAdapter adapter;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                    if (PULL_STATE == 0) {
                        main_page_listview.hideFooterView();
                    }else if (PULL_STATE==1){
                        main_page_listview.hideHeaderView();
                    }
                    break;
                case 1:
                    Toast.makeText(getActivity(), "网络数据异常", Toast.LENGTH_LONG).show();
                    break;
                case 5:
                    Toast.makeText(getActivity(), "没有更多数据了", Toast.LENGTH_LONG).show();
                    break;
                case 10:
                    Toast.makeText(getActivity(), "服务器正在维护,请稍后访问", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
    private int count;
    private View dialog_view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        datas = new ArrayList<>();
        biz = new NewBrandBiz();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.home_fragment,container,false);
        dialog=new ProgressDialog(getActivity());
        dialog_view = inflater.inflate(R.layout.loading_dialog,null);
        initView(view, inflater);
        return view;
    }
    private void initView(View view,LayoutInflater inflater) {
        go_top = (ImageView) view.findViewById(R.id.go_top);
        main_page_listview = (RefreshListView) view.findViewById(R.id.main_page_listview);
        adapter = new MainListViewAdapter(datas, getActivity());
        main_page_listview.setAdapter(adapter);
        main_page_listview.setOnRefreshListener(this);
        View header = inflater.inflate(R.layout.main_fragment_header, null);
        main_page_listview.addHeaderView(header);
        initHeaderView(header);
        setHeaderListener();
    }
    /**
     * 设置监听器
     */
    private void setHeaderListener() {
        help_calculate_tv.setOnClickListener(this);
        help_check_tv.setOnClickListener(this);
        informationsummary_tv.setOnClickListener(this);
        integralmall_tv.setOnClickListener(this);
        persional_space_tv.setOnClickListener(this);
        woman_chat_tv.setOnClickListener(this);
        main_new_brand_tv.setOnClickListener(this);
        main_release_brand_tv.setOnClickListener(this);
        main_story_brand_tv.setOnClickListener(this);
        go_top.setOnClickListener(this);
        main_page_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 1) {
                    Intent intent = new Intent(getActivity(), InformationDetailActivity.class);
                    intent.putExtra("content_id", datas.get(position-2).getContent_id());
                    startActivity(intent);
                }
            }
        });
    }
    /**
     * 初始化headerview
     * @param header
     */
    private void initHeaderView(View header) {
        help_calculate_tv = (TextView) header.findViewById(R.id.help_calculate_tv);
        help_check_tv=(TextView)header.findViewById(R.id.help_check_tv);
        informationsummary_tv = (TextView) header.findViewById(R.id.informationsummary_tv);
        integralmall_tv = (TextView) header.findViewById(R.id.integralmall_tv);
        persional_space_tv = (TextView) header.findViewById(R.id.persional_space_tv);
        woman_chat_tv=(TextView)header.findViewById(R.id.woman_chat_tv);
        main_new_brand_tv=(TextView)header.findViewById(R.id.main_new_brand_tv);
        main_release_brand_tv = (TextView) header.findViewById(R.id.main_release_brand_tv);
        main_story_brand_tv = (TextView) header.findViewById(R.id.main_story_brand_tv);
        getNewBrandData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.help_calculate_tv:
                alertDialog();
                break;
            case R.id.help_check_tv:
                Intent help_check_intent=new Intent(getActivity(),HelpYouQuerySearchActivity.class);
                startActivity(help_check_intent);
                break;
            case R.id.informationsummary_tv:
                Intent information_intent=new Intent(getActivity(),InfomationSummaryActivity.class);
                startActivity(information_intent);
                break;
            case R.id.integralmall_tv:
                Intent accumulate_intent=new Intent(getActivity(),AccumulatedShopActivity.class);
                startActivity(accumulate_intent);
                break;
            case R.id.persional_space_tv:
                Intent persion_center_intent=new Intent(getActivity(),UserSpaceActivity.class);
                startActivity(persion_center_intent);
                break;
            case R.id.woman_chat_tv:
                Toast.makeText(getActivity(), "此功能暂时未开放,敬请期待.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_new_brand_tv:
                getNewBrandData();
                break;
            case R.id.main_release_brand_tv:
                getReleaseBrandData();
                break;
            case R.id.main_story_brand_tv:
                getStoryBrandData();
                break;
            case R.id.go_top:
                main_page_listview.smoothScrollToPositionFromTop(0,10);
                go_top.setVisibility(View.GONE);
                break;
        }
    }

    private void getStoryBrandData() {
        if (whichSel != 2) {
            datas.clear();
            page=1;
            if (((MainActivity) getActivity()).isNetworkConnected(getActivity())) {
                if (dialog != null) {
                    dialog.show();
                    dialog.setMessage("加载中....");
                    PULL_STATE=10;
                    biz.getMacketList("品牌买赠", "StoryBrand", this);
                }
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

    private void getReleaseBrandData() {
        if (whichSel != 1) {
            datas.clear();
            page=1;
            if (((MainActivity) getActivity()).isNetworkConnected(getActivity())) {
                if (dialog != null) {
                    dialog.show();
                    dialog.setMessage("加载中....");
                    PULL_STATE=10;
                    biz.getMacketList("品牌大促", "ReleaseBrand", this);
                }
            }else {
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
    private void getNewBrandData() {
        if (whichSel!=0) {
            datas.clear();
            page=1;
            if (((MainActivity)getActivity()).isNetworkConnected(getActivity())) {
                if (dialog != null) {
                    dialog.show();
                    dialog.setMessage("加载中....");
                    PULL_STATE=10;
                    biz.getMacketList(page, 0, 50, null, "品牌新品", this);
                }
            }else {
                Toast.makeText(getActivity(), "网络无连接", Toast.LENGTH_LONG).show();
            }
            main_new_brand_tv.setTextColor(getResources().getColor(R.color.color_white));
            main_new_brand_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
            main_release_brand_tv.setTextColor(getResources().getColor(R.color.color_black));
            main_release_brand_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
            main_story_brand_tv.setTextColor(getResources().getColor(R.color.color_black));
            main_story_brand_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
            whichSel = 0;
        }
    }

    /**
     * 加载更多内容
     * @param page
     * @param brand
     */
    public void getMoreData(int page,String brand){
        if (((MainActivity)getActivity()).isNetworkConnected(getActivity())) {
            biz=new NewBrandBiz();
            biz.getMacketList(page,0,50,null,brand, this);
        }else {
            Toast.makeText(getActivity(), "网络无连接", Toast.LENGTH_LONG).show();
        }
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

    @Override
    public void onResponse(Response response) {
        if (response.isSuccessful()){
            Gson gson=new Gson();
            try {
                String json=response.body().string();
                json = json.replace(" ","");
                final Root root = gson.fromJson(json, Root.class);
                if ("0".equals(String.valueOf(root.getStatus()))) {
                    if (json.contains("[")) {
                        //代表是上拉加载更多的操作
                        if (PULL_STATE == 0) {
                            for (News news : root.getData().getNews()) {
                                datas.add(news);
                            }
                        } else {
                            //代表下拉刷新或者切换按钮
                            count = Integer.valueOf(root.getData().getCount());
                            for (News news : root.getData().getNews()) {
                                datas.add(news);
                            }
                        }
                        handler.sendEmptyMessage(0);
                    }
                } else {
                    handler.sendEmptyMessage(1);
                }
            } catch (IOException e) {
                handler.sendEmptyMessage(10);
            }
        }
    }

    @Override
    public void onFailue(Request request, IOException e) {

    }
    @Override
    public void onDownPullRefresh() {
        PULL_STATE=1;
        page=1;
        if (whichSel == 0) {
            getMoreData(page,"品牌新品");
        }else if (whichSel==1){
            getMoreData(page,"品牌大促");
        }else if (whichSel == 2) {
            getMoreData(page,"品牌买赠");
        }
    }

    @Override
    public void onLoadingMore() {
        PULL_STATE=0;
        go_top.setVisibility(View.VISIBLE);
        if (count == adapter.getCount()) {
            handler.sendEmptyMessage(5);
            main_page_listview.hideFooterView();
        } else {
            if (whichSel == 0) {
                ++page;
                getMoreData(page, "品牌新品");
            }else if (whichSel==1){
                ++page;
                getMoreData(page,"品牌大促");
            }else if (whichSel == 2) {
                ++page;
                getMoreData(page,"品牌买赠");
            }
        }
    }

    @Override
    public void onScroll(int firstVisibleItem, int visibleItemCount) {
        if (firstVisibleItem <= 5) {
            go_top.setVisibility(View.GONE);
        }else {
            go_top.setVisibility(View.VISIBLE);
        }
    }
}
