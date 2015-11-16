package com.mb.mmdepartment.biz.userspace.listrecord;

import com.mb.mmdepartment.listener.RequestListener;

import java.util.Map;

/**
 * Created by krisi on 2015/10/20.
 */
public interface IListRecordDetailBiz {
    void getListRecordDetail(String onumber,String device_no, RequestListener listener);
}