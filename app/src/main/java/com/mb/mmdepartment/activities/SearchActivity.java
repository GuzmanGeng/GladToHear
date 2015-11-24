package com.mb.mmdepartment.activities;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.biz.main_search.MainAddSearchKeyword;
import com.mb.mmdepartment.biz.main_search.MainSearchHotBiz;
import com.mb.mmdepartment.tools.log.Log;

public class SearchActivity extends AppCompatActivity implements TextWatcher{
    private RecyclerView hot_brand_recycle,hot_channels_recycle,history_recycle;
    private EditText tv_search_input;
    private TextView search_cancle;
    private RecyclerView[] recyclerViews = new RecyclerView[3];
    private MainAddSearchKeyword add_biz;
//    private ImageView back_to_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initData();
    }
    private void initData() {
        add_biz=new MainAddSearchKeyword();
        MainSearchHotBiz biz = new MainSearchHotBiz(this,recyclerViews);
        if (TextUtils.isEmpty(TApplication.user_id)) {
            biz.getHotBrand("1");
        } else {
            biz.getHotBrand(TApplication.user_id);
        }
    }

    private void initView() {
        hot_brand_recycle = (RecyclerView) findViewById(R.id.hot_brand_recycle);
        hot_channels_recycle = (RecyclerView) findViewById(R.id.hot_channels_recycle);
        history_recycle = (RecyclerView) findViewById(R.id.history_recycle);
//        back_to_main = (ImageView) findViewById(R.id.back_to_main);
        recyclerViews[0]=hot_brand_recycle;
        recyclerViews[1]=hot_channels_recycle;
        recyclerViews[2]=history_recycle;

        tv_search_input = (EditText) findViewById(R.id.tv_search_input);
        tv_search_input.addTextChangedListener(this);

        search_cancle = (TextView) findViewById(R.id.search_cancle);
        search_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((TextView) v).getText().toString().equals("确定")) {
                    Intent intent = new Intent(SearchActivity.this, SearchListActivity.class);
                    intent.putExtra("keyword",tv_search_input.getText().toString());
                    startActivity(intent);
                    tv_search_input.setText("");
                    if (TextUtils.isEmpty(TApplication.user_id)) {
                        add_biz.addHistory("1", tv_search_input.getText().toString());
                    } else {
                        add_biz.addHistory(TApplication.user_id, tv_search_input.getText().toString());
                    }
                } else {
                    Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
//        back_to_main.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
    }
    /**
     * 一开始检测用户是否有输入
     * @param s
     * @param start
     * @param count
     * @param after
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    /**
     * 判断用户是否有输入，有输入的话按钮显示确定没有的话显示取消
     * @param s
     * @param start
     * @param before
     * @param count
     */
    @Override
    public void onTextChanged(final CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(s.toString())) {
            if (search_cancle.getText().toString().equals("取消")){
                search_cancle.setText("确定");
            }
        } else {
            search_cancle.setText("取消");
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
