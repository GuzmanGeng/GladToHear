package com.mb.mmdepartment.biz.userspace.listrecord.exchangeprizerecord;

import com.mb.mmdepartment.listener.RequestListener;

import java.util.HashMap;

/**
 * Created by jack on 2015/10/19.
 */
public interface IExchangePrizeRecordBiz {
    void getExchangePrizeRecord(String userid,RequestListener listener);
}
