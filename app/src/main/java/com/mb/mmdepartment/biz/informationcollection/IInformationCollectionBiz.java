package com.mb.mmdepartment.biz.informationcollection;

import com.mb.mmdepartment.listener.RequestListener;

/**
 * 资讯汇总接口
 */
public interface IInformationCollectionBiz {
    void getInformation(int city_id,int category_id,int page,int perPage,String tag,RequestListener listener);
}
