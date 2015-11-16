package com.mb.mmdepartment.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mb.mmdepartment.R;

/**
 * Created by Administrator on 2015/9/18 0018.
 */
public class LoadingDialog extends Dialog {
    private ImageView mImageView;
    private Animation mAnimation;
    private Context mContext;
    public LoadingDialog(Context context,int theme) {
        super(context, theme);
        mContext = context;
    }
    public LoadingDialog(Context context) {
        super(context);
        mContext = context;
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mImageView = new ImageView(mContext);
//        mImageView.setBackgroundResource(R.drawable.animation_waiting_dialog);
//        mImageView.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
//        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        setContentView(mImageView);
//        ((AnimationDrawable)mImageView.getBackground()).start();
        View view = LayoutInflater.from(mContext).inflate(R.layout.loading_dialog, null);
        setContentView(view);
    }
    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(false);
    }
}
