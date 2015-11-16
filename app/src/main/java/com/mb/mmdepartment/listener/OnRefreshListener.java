package com.mb.mmdepartment.listener;
public interface OnRefreshListener {
	void onDownPullRefresh();
	void onLoadingMore();
	void onScroll(int firstVisibleItem, int visibleItemCount);
}
