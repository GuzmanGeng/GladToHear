package com.mb.mmdepartment.biz.submitorders;

import com.mb.mmdepartment.listener.RequestListener;

/**
 * Created by krisi on 2015/10/27.
 */
public interface IsubmitordersBiz {
    void submitorders(String list,String tag,RequestListener listener);
}
