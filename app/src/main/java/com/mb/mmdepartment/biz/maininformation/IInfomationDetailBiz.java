package com.mb.mmdepartment.biz.maininformation;

import com.mb.mmdepartment.listener.RequestListener;

/**
 * Created by Administrator on 2015/9/24 0024.
 */
public interface IInfomationDetailBiz {
    void getDetailInformation(String content_id,String userid,String device_type,String tag,RequestListener listener);
}
