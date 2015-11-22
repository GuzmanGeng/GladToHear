package com.mb.mmdepartment.tools;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mb.mmdepartment.R;

public class CustomToast {

	/**
	 * 显示Toast
	 * 
	 * @param context
	 * @param root
	 * @param tvString
	 */
	public static void show(Context context, String title, String content) {
		View layout = LayoutInflater.from(context).inflate(
				R.layout.custom_toast, null);
		TextView tv_title = (TextView) layout.findViewById(R.id.tv_title);
		TextView tv_content = (TextView) layout.findViewById(R.id.tv_content);
		tv_title.setText(title);
		tv_content.setText(content);
		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}

	/**
	 * 显示Toast
	 * 
	 * @param context
	 * @param root
	 * @param tvString
	 */
	public static void show(Context context, int title, String content) {
		View layout = LayoutInflater.from(context).inflate(
				R.layout.custom_toast, null);
		TextView tv_title = (TextView) layout.findViewById(R.id.tv_title);
		TextView tv_content = (TextView) layout.findViewById(R.id.tv_content);
		tv_title.setText(title);
		tv_content.setText(content);
		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}

	public static void show(Context context, int titleId, int contentId) {
		View layout = LayoutInflater.from(context).inflate(
				R.layout.custom_toast, null);
		TextView tv_title = (TextView) layout.findViewById(R.id.tv_title);
		TextView tv_content = (TextView) layout.findViewById(R.id.tv_content);
		tv_title.setText(titleId);
		tv_content.setText(contentId);
		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}

}
