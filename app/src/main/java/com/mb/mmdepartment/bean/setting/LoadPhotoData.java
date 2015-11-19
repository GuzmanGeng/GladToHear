package com.mb.mmdepartment.bean.setting;

/**
 * Created by joyone2one on 2015/11/20.
 */
public class LoadPhotoData {
    private String userid;

    private String headpic;

    private String avatar;

    public void setUserid(String userid){
        this.userid = userid;
    }
    public String getUserid(){
        return this.userid;
    }
    public void setHeadpic(String headpic){
        this.headpic = headpic;
    }
    public String getHeadpic(){
        return this.headpic;
    }
    public void setAvatar(String avatar){
        this.avatar = avatar;
    }
    public String getAvatar(){
        return this.avatar;
    }

}
