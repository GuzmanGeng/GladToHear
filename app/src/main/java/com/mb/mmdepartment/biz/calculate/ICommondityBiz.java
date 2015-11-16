package com.mb.mmdepartment.biz.calculate;

import com.mb.mmdepartment.listener.RequestListener;

/**
 * Created by Administrator on 2015/9/26 0026.
 */
public interface ICommondityBiz {
    void getCommodityList(int parent_id,int parentId,String shop_id,String tag,final RequestListener listener);
}
