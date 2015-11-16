package com.mb.mmdepartment.biz.password;

import com.mb.mmdepartment.listener.RequestListener;

/**
 * Created by krisi on 2015/11/9.
 */
public interface IChangePswBiz {
    void changepsw(String old_psw,String change_psw,String psw_again,String TAG,RequestListener listener);
}
