package com.mb.mmdepartment.bean.mychat;

/**
 * Created by joyone2one on 2015/11/19.
 */
public class Comment {
    private String id;

    private String content_id;

    private String title;

    private String nickname;

    private String userid;

    private String parent_userid;

    private String body;

    private String count_agree;

    private String count_disagree;

    private int is_favorite;

    private User user;

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setContent_id(String content_id){
        this.content_id = content_id;
    }
    public String getContent_id(){
        return this.content_id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setNickname(String nickname){
        this.nickname = nickname;
    }
    public String getNickname(){
        return this.nickname;
    }
    public void setUserid(String userid){
        this.userid = userid;
    }
    public String getUserid(){
        return this.userid;
    }
    public void setParent_userid(String parent_userid){
        this.parent_userid = parent_userid;
    }
    public String getParent_userid(){
        return this.parent_userid;
    }
    public void setBody(String body){
        this.body = body;
    }
    public String getBody(){
        return this.body;
    }
    public void setCount_agree(String count_agree){
        this.count_agree = count_agree;
    }
    public String getCount_agree(){
        return this.count_agree;
    }
    public void setCount_disagree(String count_disagree){
        this.count_disagree = count_disagree;
    }
    public String getCount_disagree(){
        return this.count_disagree;
    }
    public void setIs_favorite(int is_favorite){
        this.is_favorite = is_favorite;
    }
    public int getIs_favorite(){
        return this.is_favorite;
    }
    public void setUser(User user){
        this.user = user;
    }
    public User getUser(){
        return this.user;
    }
}
