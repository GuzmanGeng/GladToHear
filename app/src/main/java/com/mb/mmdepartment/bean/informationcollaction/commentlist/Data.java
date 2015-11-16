package com.mb.mmdepartment.bean.informationcollaction.commentlist;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/9/24 0024.
 */
public class Data implements Serializable{
    private String page;

    private String device_type;

    private String userid;

    private String content_id;

    private int perpage;

    private String count;

    private List<Comment> comment ;

    public void setPage(String page){
        this.page = page;
    }
    public String getPage(){
        return this.page;
    }
    public void setDevice_type(String device_type){
        this.device_type = device_type;
    }
    public String getDevice_type(){
        return this.device_type;
    }
    public void setUserid(String userid){
        this.userid = userid;
    }
    public String getUserid(){
        return this.userid;
    }
    public void setContent_id(String content_id){
        this.content_id = content_id;
    }
    public String getContent_id(){
        return this.content_id;
    }
    public void setPerpage(int perpage){
        this.perpage = perpage;
    }
    public int getPerpage(){
        return this.perpage;
    }
    public void setCount(String count){
        this.count = count;
    }
    public String getCount(){
        return this.count;
    }
    public void setComment(List<Comment> comment){
        this.comment = comment;
    }
    public List<Comment> getComment(){
        return this.comment;
    }
}
