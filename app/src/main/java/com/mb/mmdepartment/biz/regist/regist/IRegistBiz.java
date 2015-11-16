package com.mb.mmdepartment.biz.regist.regist;
import com.mb.mmdepartment.listener.RegistResponse;
import com.mb.mmdepartment.listener.RequestListener;

public interface IRegistBiz {
    void regist(String username,String password,String again_password,String phone_num,String code,RegistResponse response);
    void checkname(String username,String tag,RequestListener listener);
}
