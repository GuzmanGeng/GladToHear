package com.mb.mmdepartment.bean.mychat;

import java.util.List;

/**
 * Created by joyone2one on 2015/11/19.
 */
public class CommentList {
    private String userid;

    private List<Comment> comment ;

    public void setUserid(String userid){
        this.userid = userid;
    }
    public String getUserid(){
        return this.userid;
    }
    public void setComment(List<Comment> comment){
        this.comment = comment;
    }
    public List<Comment> getComment(){
        return this.comment;
    }
}
