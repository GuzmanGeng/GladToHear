package com.mb.mmdepartment.bean.informationcollaction.detail;

/**
 * Created by Administrator on 2015/9/24 0024.
 */
public class Data {
    private String content_id;

    private String userid;

    private String device_type;

    private String like_num;

    private String common_num;

    private String dislike_num;

    private String join_num;

    private String disjoin_num;

    private String user_action;

    private Content content;

    public void setContent_id(String content_id){
        this.content_id = content_id;
    }
    public String getContent_id(){
        return this.content_id;
    }
    public void setUserid(String userid){
        this.userid = userid;
    }
    public String getUserid(){
        return this.userid;
    }
    public void setDevice_type(String device_type){
        this.device_type = device_type;
    }
    public String getDevice_type(){
        return this.device_type;
    }
    public void setLike_num(String like_num){
        this.like_num = like_num;
    }
    public String getLike_num(){
        return this.like_num;
    }
    public void setCommon_num(String common_num){
        this.common_num = common_num;
    }
    public String getCommon_num(){
        return this.common_num;
    }
    public void setDislike_num(String dislike_num){
        this.dislike_num = dislike_num;
    }
    public String getDislike_num(){
        return this.dislike_num;
    }
    public void setJoin_num(String join_num){
        this.join_num = join_num;
    }
    public String getJoin_num(){
        return this.join_num;
    }
    public void setDisjoin_num(String disjoin_num){
        this.disjoin_num = disjoin_num;
    }
    public String getDisjoin_num(){
        return this.disjoin_num;
    }
    public void setUser_action(String user_action){
        this.user_action = user_action;
    }
    public String getUser_action(){
        return this.user_action;
    }
    public void setContent(Content content){
        this.content = content;
    }
    public Content getContent(){
        return this.content;
    }
}
