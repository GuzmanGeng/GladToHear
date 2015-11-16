package com.mb.mmdepartment.account.activity.control;
import com.mb.mmdepartment.listener.RegistResponse;
import com.mb.mmdepartment.listener.RequestListener;

/**
 * User: GC
 * Date: 3/27/13
 * Time: 2:35 AM
 */
public interface ServerAuthenticate {
    public void userSignUp(final String name, final String password,final String again_pass, final String tel,String code, String authType,RegistResponse response) throws Exception;
    public void userSignIn(final String user, final String pass, String authType,RequestListener listener) throws Exception;
}
