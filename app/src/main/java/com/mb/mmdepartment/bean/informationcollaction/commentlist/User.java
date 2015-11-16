package com.mb.mmdepartment.bean.informationcollaction.commentlist;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/9/24 0024.
 */
public class User implements Serializable{
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
