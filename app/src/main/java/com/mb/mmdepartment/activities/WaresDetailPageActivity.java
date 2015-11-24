package com.mb.mmdepartment.activities;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.mb.mmdepartment.bean.buyplan.byprice.DataList;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.mb.mmdepartment.listener.OnRecycItemClickListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.adapter.helpcheck.shop_information.ShopInformationAdapter;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.helpcheck.detail.Detail;
import com.mb.mmdepartment.bean.helpcheck.detail.DetailRoot;
import com.mb.mmdepartment.bean.marcketseldetail.Lists;
import com.mb.mmdepartment.biz.helpcheck.marcket_sel.detail.DetailAddressBiz;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.view.CircleBadgeView;
import com.tencent.stat.StatService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
public class WaresDetailPageActivity extends BaseActivity implements RequestListener{
    //传递过来的详细信息
    private RecyclerView help_address_recycleview;
    private List<Detail> list;
    private Map<String,String> map;
    private GridLayoutManager manager;
    private ShopInformationAdapter adapter;
    private TextView floatActionButton;
    private TextView detail_content_tv;
    private TextView prompt;
    private TextView detail_tv_title,detail_apply_people,detail_market_name,detail_start_end_time,detail_now_money,detail_old_money,detail_reference_money;
    private ImageView detail_iv_content,detail_market_pic;
    private TextView reference_tv;
    private ImageView left_shop_add_remove;
    private DetailAddressBiz biz;
    private View v;
    private int add_romove = 0;
    private CircleBadgeView badgeView;
    private Lists lists;
    private Menu goods_detail_shopping_cart;
    private LuPinModel luPinModel;

    //购物车内容
    private ViewGroup anim_mask_layout;//动画层
    private ImageView ball;// 小圆点

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    manager = new GridLayoutManager(WaresDetailPageActivity.this, 2);
                    help_address_recycleview.setLayoutManager(manager);
                    help_address_recycleview.setAdapter(adapter);
                    break;
                case 5:
                    int size = 0;
                    for(int i = 0;i<TApplication.shop_list_to_pick.size();i++){
                        size = size +TApplication.shop_list_to_pick.get(i).getList().size();
                    }
                    badgeView.setText(size + "");
                    break;
                case 10:
                    break;
            }
        }
    };
    private String id_goods;

    @Override
    public int getLayout() {
        return R.layout.activity_help_check_goods_detail;
    }

    @Override
    protected void onResume() {
        super.onResume();
        luPinModel = new LuPinModel();
        luPinModel.setName(lists.getId());
        luPinModel.setType("page");
        luPinModel.setState("end");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        luPinModel.setOperationtime(sdf.format(new Date()));
        StatService.onResume(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        luPinModel.setEndtime(sdf.format(new Date()));
        TApplication.luPinModels.add(luPinModel);
        StatService.onPause(this);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        lists = (Lists) bundle.getSerializable("lists");
        id_goods=lists.getId();
        initView();
        goods_detail_shopping_cart= (Menu) findViewById(R.id.goods_detail_shopping_cart);
        setData();
        setListeners();
    }

    private void setListeners() {
        left_shop_add_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
                view.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
                ball = new ImageView(WaresDetailPageActivity.this);// buyImg是动画的图片，我的是一个小球（R.drawable.sign）
                ball.setImageResource(R.mipmap.sign);// 设置buyImg的图片
//                ball.set;
                if (add_romove == 0) {
                    LuPinModel add_shopping_car = new LuPinModel();
                    add_shopping_car.setName(lists.getId());
                    add_shopping_car.setOperationtime(sdf.format(new Date()));
                    add_shopping_car.setType("car");
                    add_shopping_car.setState("selected");
                    TApplication.luPinModels.add(add_shopping_car);
                    left_shop_add_remove.setImageResource(R.mipmap.minus);
                    setAnim(ball, startLocation, "plus");// 开始执行动画

//                    TApplication.shop_lists.put(lists.getId(), lists);
//                    WaresDetailPageActivity.add_goods(lists);
                    //向购物车中追加数据

                    String shop_name = lists.getSelect_shop_name();
                    if (TextUtils.isEmpty(shop_name)) {
                        shop_name = lists.getShop_name();
                    }
                    add_cars_index(id_goods, shop_name, lists);
                    badgeView.setText(String.valueOf(TApplication.shop_lists.size()));


                    add_romove = 1;
                } else {
                    LuPinModel remove_shopping_car = new LuPinModel();
                    remove_shopping_car.setName(lists.getId());
                    remove_shopping_car.setOperationtime(sdf.format(new Date()));
                    remove_shopping_car.setType("car");
                    remove_shopping_car.setState("unselected");
                    TApplication.luPinModels.add(remove_shopping_car);
                    startLocation[1] = 120;
                    setAnim(ball, startLocation, "minus");// 开始执行动画


//                    TApplication.shop_lists.remove(lists.getId());
//                    WaresDetailPageActivity.remove_goods(lists);
                    remove_cars_index(id_goods);
                    left_shop_add_remove.setImageResource(R.mipmap.plus);
                    badgeView.setText(String.valueOf(TApplication.shop_lists.size()));
                    add_romove = 0;
                }
                handler.sendEmptyMessage(CalculateShowWaresInfoActivity.NOTI);
//                .setbadge();
            }
        });}


    private void setData() {
//        for (int i = 0; i < TApplication.shop_list_to_pick.size(); i++) {
//            Log.i("tag","tag"+i);
//            for(int j = 0;j <TApplication.shop_list_to_pick.get(i).getList().size();j++){
//                if(TApplication.shop_list_to_pick.get(i).getList().get(j).getId().equals(lists.getId())){
//                    left_shop_add_remove.setImageResource(R.mipmap.minus);
//                    add_romove = 1;
//                }
//            }
//        }

        if (TApplication.ids.contains(id_goods)) {
            left_shop_add_remove.setImageResource(R.mipmap.minus);
            add_romove = 1;
        } else {
            left_shop_add_remove.setImageResource(R.mipmap.plus);
            Log.e("remove", "加号");
            add_romove = 0;
        }
        detail_tv_title.setText(lists.getName());
        detail_market_name.setText(lists.getShop_name());
        detail_apply_people.setText(lists.getCrowd());
        String startTime = lists.getStart_time();
        String endTime = lists.getEnd_time();
        startTime = startTime.substring(0, 10);
        endTime = endTime.substring(0, 10);
        detail_start_end_time.setText(startTime + "~" + endTime);
        detail_now_money.setText("￥" + lists.getF_price());
        detail_old_money.setText("￥" + lists.getO_price());
        detail_old_money.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        detail_reference_money.setText(lists.getOne_shop());
        detail_content_tv.setText(lists.getInfo());
        ImageLoader.getInstance().displayImage(BaseConsts.BASE_IMAGE_URL + lists.getTitlepic(), detail_iv_content, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                ((ImageView) view).setImageResource(R.mipmap.loading);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                ((ImageView) view).setImageBitmap(bitmap);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
        String shop_name = lists.getShop_name();
        /***
         * 此处加入超市名称的判断
         */
//        if(TApplication.shop_lists.containsKey(lists.getId())){
//            left_shop_add_remove.setImageResource(R.mipmap.minus);
//        }

    }
    private boolean is_success_remove;
    /**
     * 移除购物车数据
     * @param id
     */
    public void remove_cars_index(String id) {
        if (TApplication.ids.contains(id)) {
            TApplication.ids.remove(id);
        } else {
            showToast("商品不存在");
        }
        if (TApplication.shop_lists.containsKey(id)) {
            TApplication.shop_lists.remove(id);
            is_success_remove = true;
        } else {
            showToast("商品不存在");
            is_success_remove=false;
        }
        for (int i=0;i<TApplication.shop_list_to_pick.size();i++) {
            DataList list = TApplication.shop_list_to_pick.get(i);
            for (int j = 0; j < list.getList().size();j++) {
                Lists lists=list.getList().get(j);
                String id_get=lists.getId().trim();
                try {
                    id_get=URLEncoder.encode(id_get, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (id.equals(id_get)) {
                    list.getList().remove(lists);
                    if (list.getList().size() == 0) {
                        TApplication.shop_list_to_pick.remove(list);
                    }
                    if (is_success_remove) {
                        showToast("购物车移除成功");
                    }
                    return;
                }
            }
        }
    }

    /**
     * 向购物车中追加数据
     * @param id
     * @param shop_name
     * @param list
     */
    public void add_cars_index(String id,String shop_name,Lists list){
        if (TApplication.ids.size()==0||!TApplication.ids.contains(id)) {
            TApplication.ids.add(id);
        } else {
            showToast("商品已存在");
        }
        if (TApplication.shop_lists.size()==0||TApplication.shop_lists.get(id) == null) {
            TApplication.shop_lists.put(id, list);
        } else {
            showToast("商品已存在");
        }
        if (TApplication.shop_list_to_pick.size() == 0) {
            DataList datalist = new DataList();
            List<Lists> new_list = new ArrayList<>();
            datalist.setName(shop_name);
            new_list.add(list);
            datalist.setList(new_list);
            TApplication.shop_list_to_pick.add(datalist);
            return;
        }
        for (int i = 0; i < TApplication.shop_list_to_pick.size(); i++) {
            DataList data = TApplication.shop_list_to_pick.get(i);
            String get_shop_name = data.getName().trim();
            try {
                get_shop_name=URLEncoder.encode(get_shop_name, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (shop_name.equals(get_shop_name)) {
                data.getList().add(list);
                return;
            }
        }
        DataList datalist = new DataList();
        List<Lists> new_list = new ArrayList<>();
        datalist.setName(shop_name);
        new_list.add(list);
        datalist.setList(new_list);
        TApplication.shop_list_to_pick.add(datalist);
    }

    public static void remove_goods(Lists lists){
        String shop_name = TApplication.market.get(lists.getShop_id());
        for(int i = 0;i<TApplication.shop_list_to_pick.size();i++) {
            if (shop_name.equals(TApplication.shop_list_to_pick.get(i).getName())) {
                for(int j = 0;j<TApplication.shop_list_to_pick.get(i).getList().size();j++){
                    if(TApplication.shop_list_to_pick.get(i).getList().get(j).getId().
                            equals(lists.getId())) {
                        TApplication.shop_list_to_pick.get(i).getList().remove(TApplication.
                                shop_list_to_pick.get(i).getList().get(j));
                    }
                }
            }
            if(TApplication.shop_list_to_pick.get(i).getList().size()== 0){
                TApplication.shop_list_to_pick.remove(TApplication.shop_list_to_pick.get(i));
            }
        }
    }

    public static void add_goods(Lists lists){
        int a = 0;
        String shop_name = TApplication.market.get(lists.getShop_id());
        for(int i = 0;i<TApplication.shop_list_to_pick.size();i++) {
            if (shop_name.equals(TApplication.shop_list_to_pick.get(i).getName())) {
                if (!TApplication.shop_list_to_pick.get(i).getList().contains(lists))
                {
                    TApplication.shop_list_to_pick.get(i).getList().add(lists);
                }
                a++;
            }
        }
        if(a == 0){
            DataList dataList = new DataList();
            dataList.setName(shop_name);
            List<Lists> listses = new ArrayList<Lists>();
            listses.add(lists);
            dataList.setList(listses);
            TApplication.shop_list_to_pick.add(dataList);
        }
    }

    private void initView() {
        detail_tv_title = (TextView) findViewById(R.id.detail_tv_title);
        detail_apply_people = (TextView) findViewById(R.id.detail_apply_people);
        detail_market_name = (TextView) findViewById(R.id.detail_market_name);
        detail_start_end_time = (TextView) findViewById(R.id.detail_start_end_time);
        detail_now_money = (TextView) findViewById(R.id.detail_now_money);
        detail_old_money = (TextView) findViewById(R.id.detail_old_money);
        detail_reference_money = (TextView) findViewById(R.id.detail_reference_money);
        detail_iv_content = (ImageView) findViewById(R.id.detail_iv_content);
        detail_market_pic = (ImageView) findViewById(R.id.detail_market_pic);
        left_shop_add_remove = (ImageView) findViewById(R.id.left_shop_add_remove);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                badgeView = new CircleBadgeView(WaresDetailPageActivity.this, findViewById(R.id.goods_detail_shopping_cart));
                v = findViewById(R.id.goods_detail_shopping_cart);
                int size = TApplication.ids.size();
//                for(int i = 0;i<TApplication.shop_list_to_pick.size();i++){
//                    size = size +TApplication.shop_list_to_pick.get(i).getList().size();
//                }

                badgeView.setText(size + "");
                badgeView.setBackgroundColor(Color.RED);//设置背景颜色
                badgeView.setGravity(Gravity.CENTER);
                badgeView.setBadgePosition(CircleBadgeView.POSITION_TOP_LEFT);
                badgeView.show();
            }
        }, 500);
        reference_tv = (TextView) findViewById(R.id.reference_tv);
        prompt = (TextView) findViewById(R.id.prompt);
        detail_content_tv = (TextView) findViewById(R.id.detail_content_tv);
        help_address_recycleview = (RecyclerView) findViewById(R.id.address_recycle);
        if (isNetworkConnected(this)) {
            biz = new DetailAddressBiz();
            biz.getDetailAddress(lists.getShop_id(), this);
        }
        floatActionButton = (TextView) findViewById(R.id.floatActionButton);
        floatActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (floatActionButton.getVisibility() == View.VISIBLE) {
                    if (isNetworkConnected(WaresDetailPageActivity.this)) {
                        floatActionButton.setVisibility(View.INVISIBLE);
                        prompt.setVisibility(View.VISIBLE);
                        help_address_recycleview.setVisibility(View.VISIBLE);
                        prompt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (floatActionButton.getVisibility() != View.VISIBLE) {
                                    floatActionButton.setVisibility(View.VISIBLE);
                                    help_address_recycleview.setVisibility(View.GONE);
                                    prompt.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
                    } else {
                        floatActionButton.setVisibility(View.INVISIBLE);
                        prompt.setVisibility(View.VISIBLE);
                        help_address_recycleview.setVisibility(View.VISIBLE);
                        prompt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (floatActionButton.getVisibility() != View.VISIBLE) {
                                    floatActionButton.setVisibility(View.VISIBLE);
                                    help_address_recycleview.setVisibility(View.GONE);
                                    prompt.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
                    }

                }
            }
        });

        detail_content_tv.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @Override
    public void onBackPressed() {
        if (floatActionButton.getVisibility() != View.VISIBLE) {
            floatActionButton.setVisibility(View.VISIBLE);
            prompt.setVisibility(View.INVISIBLE);
            return;
        }
        finish();
        super.onBackPressed();
    }
    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("资讯详情");
        action.setHomeButtonEnabled(isTrue);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_help_check_goods_detail, menu);
        MenuItem item = menu.findItem(R.id.goods_detail_shopping_cart);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(WaresDetailPageActivity.this, ShoppingCartPageActivity.class);
                intent.putExtra("tag", ShoppingCartPageActivity.class.getSimpleName());
//                intent.putExtra("lists",getList());
                startActivityForResult(intent, 0);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onResponse(Response response) {
        if (response.isSuccessful()) {
            try {
                String json = response.body().string();
                if (!json.contains("[")){
                    return;
                }
                Gson gson=new Gson();
                DetailRoot root = gson.fromJson(json, DetailRoot.class);
                list=root.getData().getList();
                adapter = new ShopInformationAdapter(list);
                adapter.setOnItemClickListener(new OnRecycItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(WaresDetailPageActivity.this,ShopAddressInfoPageActivity.class);
                        intent.putExtra("detail",list.get(position));
                        startActivity(intent);
                    }
                });
                handler.sendEmptyMessage(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailue(Request request, IOException e) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adapter.notifyDataSetChanged();
        badgeView.setText(TApplication.shop_lists.size() + "");
    }

    /**
     * @Description: 创建动画层
     * @param
     * @return void
     * @throws
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;

    }
    private View addViewToAnimLayout(final ViewGroup parent, final View view,
                                     int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }


    /**
     * 动画开始
     * @param v
     * @param startLocation
     */
    private void setAnim(final View v, int[] startLocation,String anim) {
        anim_mask_layout = null;
        anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(v);//把动画小球添加到动画层
        final View view = addViewToAnimLayout(anim_mask_layout, v,
                startLocation);
        int[] endLocation = new int[2];// 存储动画结束位置的X、Y坐标

//        goods_detail_shopping_cart.getLocationInWindow(endLocation);// shopCart是那个购物车
        WindowManager wm = this.getWindowManager();

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        int endX = 0;
        int endY = 0;
        if(anim.equals("plus")) {
            // 计算位移
            endX = 0 - startLocation[0] + width - 80;// 动画位移的X坐标
            endY = 120 - startLocation[1];//
        }else {
            endX = 0 - startLocation[0] + width - 80;// 动画位移的X坐标
            endY = startLocation[1] + height/2;//
        }
        TranslateAnimation translateAnimationX = new TranslateAnimation(0,
                endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        Log.e("endY",endY+"");
        TranslateAnimation translateAnimationY;
        if(anim.equals("minus")) {
            translateAnimationY = new TranslateAnimation(0,0,0,endY);
        }else {
            translateAnimationY = new TranslateAnimation(0, 0,
                    0, endY);
        }
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(800);// 动画的执行时间
        view.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            // 动画的开始
            @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
            }
        });
    }
}
