package com.mb.mmdepartment.bean.setting;

/**
 * Created by joyone2one on 2015/11/20.
 */
public class UserPassChangeData {
    private String userid;

    private String pwd;

    private String pwd1;

    private String pwd2;

    public void setUserid(String userid){
        this.userid = userid;
    }
    public String getUserid(){
        return this.userid;
    }
    public void setPwd(String pwd){
        this.pwd = pwd;
    }
    public String getPwd(){
        return this.pwd;
    }
    public void setPwd1(String pwd1){
        this.pwd1 = pwd1;
    }
    public String getPwd1(){
        return this.pwd1;
    }
    public void setPwd2(String pwd2){
        this.pwd2 = pwd2;
    }
    public String getPwd2(){
        return this.pwd2;
    }

}
