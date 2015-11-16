package com.mb.mmdepartment.biz.maininformation.IInfomationRecordBiz;

import com.mb.mmdepartment.listener.RequestListener;

/**
 * Created by krisi on 2015/11/3.
 */
public interface IIformationRecordBiz {
    void getinformationrecord(String content_id,String start_time,String end_time,String record_time,String location,
                              String device_no,RequestListener listener);
}
