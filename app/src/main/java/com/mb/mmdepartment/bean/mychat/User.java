package com.mb.mmdepartment.bean.mychat;

/**
 * Created by joyone2one on 2015/11/19.
 */
public class User {
    private String userid;

    private String username;

    private String nickname;

    private String avatar;

    public void setUserid(String userid){
        this.userid = userid;
    }
    public String getUserid(){
        return this.userid;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return this.username;
    }
    public void setNickname(String nickname){
        this.nickname = nickname;
    }
    public String getNickname(){
        return this.nickname;
    }
    public void setAvatar(String avatar){
        this.avatar = avatar;
    }
    public String getAvatar(){
        return this.avatar;
    }

}
