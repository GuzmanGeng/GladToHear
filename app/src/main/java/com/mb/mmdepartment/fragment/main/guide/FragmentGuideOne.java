package com.mb.mmdepartment.fragment.main.guide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mb.mmdepartment.R;

/**
 * Created by Administrator on 2015/9/8.
 */
public class FragmentGuideOne extends Fragment{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.guide_first, null, false);
        return view;
    }
}
