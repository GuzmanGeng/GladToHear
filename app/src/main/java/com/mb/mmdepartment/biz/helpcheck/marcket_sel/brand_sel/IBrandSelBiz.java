package com.mb.mmdepartment.biz.helpcheck.marcket_sel.brand_sel;

import com.mb.mmdepartment.listener.RequestListener;

/**
 * 品牌选择
 */
public interface IBrandSelBiz {
    void getHotSearch(int search_type,String tag,RequestListener listener);
//    void getCatLogList();
}
