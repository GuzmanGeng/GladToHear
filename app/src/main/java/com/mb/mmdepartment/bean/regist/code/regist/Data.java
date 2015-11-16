package com.mb.mmdepartment.bean.regist.code.regist;

/**
 * Created by Administrator on 2015/9/22 0022.
 */
public class Data {
    private String username;

    private String password;

    private String password1;

    private String phone;

    private String code;

    private String encrypt;

    private String salt;

    private int verifiedPhone;

    private String ctime;

    private String userid;

    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return this.username;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return this.password;
    }
    public void setPassword1(String password1){
        this.password1 = password1;
    }
    public String getPassword1(){
        return this.password1;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
    public String getPhone(){
        return this.phone;
    }
    public void setCode(String code){
        this.code = code;
    }
    public String getCode(){
        return this.code;
    }
    public void setEncrypt(String encrypt){
        this.encrypt = encrypt;
    }
    public String getEncrypt(){
        return this.encrypt;
    }
    public void setSalt(String salt){
        this.salt = salt;
    }
    public String getSalt(){
        return this.salt;
    }
    public void setVerifiedPhone(int verifiedPhone){
        this.verifiedPhone = verifiedPhone;
    }
    public int getVerifiedPhone(){
        return this.verifiedPhone;
    }
    public void setCtime(String ctime){
        this.ctime = ctime;
    }
    public String getCtime(){
        return this.ctime;
    }
    public void setUserid(String userid){
        this.userid = userid;
    }
    public String getUserid(){
        return this.userid;
    }
}
