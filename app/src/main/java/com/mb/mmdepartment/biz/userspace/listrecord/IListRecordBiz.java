package com.mb.mmdepartment.biz.userspace.listrecord;

import com.mb.mmdepartment.listener.RequestListener;

/**
 * Created by Administrator on 2015/9/24 0024.
 */
public interface IListRecordBiz {
    void getListRecord(String page,String user_id,String device_no,String tag,RequestListener listener);
}
