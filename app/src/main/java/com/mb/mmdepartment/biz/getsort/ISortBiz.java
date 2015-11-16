package com.mb.mmdepartment.biz.getsort;

import com.mb.mmdepartment.listener.RequestListener;

/**
 * Created by krisi on 2015/10/22.
 */
public interface ISortBiz {
    void sort(String tag,String device_no,String order_type,String category_id,
    String shop_id,int group,int city,int order,RequestListener listener);
}
