package com.mb.mmdepartment.activities;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;

import com.mb.mmdepartment.R;
import com.mb.mmdepartment.adapter.GuideFragmentAdapter;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.fragment.main.guide.FragmentGuideOne;
import com.mb.mmdepartment.fragment.main.guide.FragmentGuideFour;
import com.mb.mmdepartment.fragment.main.guide.FragmentGuideTwo;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.List;
public class GuideActivity extends BaseActivity {
    private final String  TAG=GuideActivity.class.getSimpleName();
    private List<Fragment> list=new ArrayList<>();
    private Fragment guideOne,guideTwo,guideThree,guide_four;
    private ViewPager guide_vp;
    private GuideFragmentAdapter adapter;
    @Override
    public int getLayout() {
        return R.layout.activity_giude;
    }
    @Override
    public void init(Bundle savedInstanceState) {
        initData();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
    }

    private void initView() {
        guide_vp= (ViewPager) findViewById(R.id.guide_vp);
        adapter=new GuideFragmentAdapter(getSupportFragmentManager(),list);
        guide_vp.setAdapter(adapter);
    }

    private void initData() {
        guideThree=new FragmentGuideFour();
        guideOne=new FragmentGuideOne();
        guideTwo=new FragmentGuideTwo();
        guide_four=new FragmentGuideFour();
        list.add(guideOne);
        list.add(guideTwo);
        list.add(guide_four);
        list.add(guideThree);
    }

    @Override
    protected void setToolBar(ActionBar action,boolean isTrue) {

    }
}
