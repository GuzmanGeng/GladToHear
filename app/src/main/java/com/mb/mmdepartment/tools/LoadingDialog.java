package com.mb.mmdepartment.tools;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mb.mmdepartment.R;
import com.mb.mmdepartment.base.TApplication;

/**
 * Created by joyone2one on 2015/11/23.
 */
public class LoadingDialog {
    private  View loadView;
    private  View progress;
    private static LoadingDialog single=null;
    private static LayoutInflater inflater;
    private LinearLayout progress_container;

    //静态工厂方法
    public static LoadingDialog getInstance() {
        if (single == null) {
            single = new LoadingDialog();
            inflater = LayoutInflater.from(TApplication.getContext());
        }
        return single;
    }

    /**
     * 设置loading
     * @param view
     */
    public void setProgress(View view) {
        if (progress != null) {
            return;
        }
        loadView = view;
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        ViewParent parent = view.getParent();
        FrameLayout container = new FrameLayout(TApplication.getContext());
        ViewGroup group = (ViewGroup) parent;
        int index = group.indexOfChild(view);
        group.removeView(view);
        group.addView(container, index, lp);
        container.addView(view);
        if (inflater != null) {
            progress = inflater.inflate(R.layout.loading_dialog, null);
            progress_container = (LinearLayout) progress
                    .findViewById(R.id.progress_container);
            container.addView(progress);
            progress_container.setTag(view);
            view.setVisibility(View.GONE);
        }
        group.invalidate();
    }
    public void endProgress() {
        if (progress_container != null) {
            //这个与progress_container为Tag关系的是ListView对象，即progress_container.getTag()为ListView对象
            ((View) progress_container.getTag()).setVisibility(View.VISIBLE);

            progress_container.setVisibility(View.GONE);
            progress.setVisibility(View.GONE);
        }
    }
}
