package com.mb.mmdepartment.biz.regist;

import com.mb.mmdepartment.listener.RequestListener;

/**
 * Created by Administrator on 2015/9/22 0022.
 */
public interface IGetCodeBiz {
    public void getCode(String phone,RequestListener listener);
}
