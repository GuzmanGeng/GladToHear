package com.mb.mmdepartment.biz.userspace.listrecord.getwin;

import com.mb.mmdepartment.listener.RequestListener;

/**
 * Created by jack on 2015/10/19.
 */
public interface IGetWin {
    void getWin(String wincode,String useid,RequestListener listener);
}
