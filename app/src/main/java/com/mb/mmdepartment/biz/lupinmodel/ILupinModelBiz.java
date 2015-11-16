package com.mb.mmdepartment.biz.lupinmodel;

import com.mb.mmdepartment.listener.RequestListener;

/**
 * Created by krisi on 2015/11/1.
 */
public interface ILupinModelBiz {
    void sendLuPinModel(String text,String tag,RequestListener listener);
}
