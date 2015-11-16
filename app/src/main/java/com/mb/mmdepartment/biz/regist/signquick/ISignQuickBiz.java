package com.mb.mmdepartment.biz.regist.signquick;

import com.mb.mmdepartment.listener.RequestListener;

/**
 * Created by krisi on 2015/11/6.
 */
public interface ISignQuickBiz {
    void signquick(String phone,String code,String device_no,RequestListener listener);
}
