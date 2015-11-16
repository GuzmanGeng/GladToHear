package com.mb.mmdepartment.activities;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.biz.getsort.SortBiz;
public class ProposedProjectActivity extends BaseActivity implements View.OnClickListener{
    private RecyclerView buy_list_recycle;
    private int whichSel=0;//记录哪个按钮被选择了
    private int state[]=new int[3];//记录每个按钮的排序
    private TextView buy_plan_price,buy_plan_discount,buy_plan_percent;
    private View help_you_calculate_goods_detail_ll;
    private TextView[] tv_backgrounds=new TextView[3];
    private SortBiz biz;
    @Override
    public int getLayout() {
        return R.layout.activity_proposed_project;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initView();
        biz=new SortBiz(this,buy_list_recycle);
        setListeners();
    }

    private void setListeners() {
        buy_plan_price.setOnClickListener(this);
        buy_plan_discount.setOnClickListener(this);
        buy_plan_percent.setOnClickListener(this);
    }

    private void initView() {
        buy_list_recycle = (RecyclerView) findViewById(R.id.buy_list_recycle);
        buy_plan_price = (TextView) findViewById(R.id.buy_plan_price);
        buy_plan_discount = (TextView) findViewById(R.id.buy_plan_discount);
        buy_plan_percent = (TextView) findViewById(R.id.buy_plan_percent);
        tv_backgrounds[0]=buy_plan_price;
        tv_backgrounds[1]=buy_plan_discount;
        tv_backgrounds[2]=buy_plan_percent;
        setState(0,1,2);
        help_you_calculate_goods_detail_ll = findViewById(R.id.help_you_calculate_goods_detail_ll);
    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("建议购物方案");
        action.setHomeButtonEnabled(isTrue);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.help_you_calculate_goods_detail_ll:

                break;
            case R.id.buy_plan_price:
                whichSel=0;
                if (state[0] == 0) {
                    buy_plan_price.setText("按价格排序↓");
                    state[0]=1;

                } else {
                    buy_plan_price.setText("按价格排序↑");
                    state[0]=0;


                }
                setState(0,1,2);
                break;
            case R.id.buy_plan_discount:
                whichSel=1;
                if (state[1] == 0) {
                    buy_plan_discount.setText("按折扣排序↓");
                    state[1]=1;


                } else {
                    buy_plan_discount.setText("按折扣排序↑");
                    state[1]=0;


                }
                setState(1,0,2);
                break;
            case R.id.buy_plan_percent:
                whichSel=2;
                if (state[2] == 0) {
                    buy_plan_percent.setText("按节省比排序↓");
                    state[2]=1;


                } else {
                    buy_plan_percent.setText("按节省比排序↑");
                    state[2]=0;


                }
                setState(2,1,0);
                break;
        }
    }

    /**
     * 设置被选中
     * @param yes
     * @param no1
     * @param no2
     */
    private void setState(int yes,int no1,int no2) {

        tv_backgrounds[yes].setTextColor(Color.WHITE);
        tv_backgrounds[yes].setBackgroundColor(getResources().getColor(R.color.text_little_half_red));

        tv_backgrounds[no1].setTextColor(getResources().getColor(R.color.text_little_half_red));
        tv_backgrounds[no1].setBackgroundColor(Color.WHITE);

        tv_backgrounds[no2].setTextColor(getResources().getColor(R.color.text_little_half_red));
        tv_backgrounds[no2].setBackgroundColor(Color.WHITE);
    }
}
