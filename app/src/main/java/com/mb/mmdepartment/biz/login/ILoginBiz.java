package com.mb.mmdepartment.biz.login;

import com.mb.mmdepartment.listener.RequestListener;
public interface ILoginBiz {
    void login(String username,String password,String device_no,RequestListener listener);
}
