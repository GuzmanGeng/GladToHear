package com.mb.mmdepartment.bean.main_search;

/**
 * Created by joyone2one on 2015/11/13.
 */
public class UserKeyword {
    private String search_id;

    private String userid;

    private String keyword;

    public void setSearch_id(String search_id){
        this.search_id = search_id;
    }
    public String getSearch_id(){
        return this.search_id;
    }
    public void setUserid(String userid){
        this.userid = userid;
    }
    public String getUserid(){
        return this.userid;
    }
    public void setKeyword(String keyword){
        this.keyword = keyword;
    }
    public String getKeyword(){
        return this.keyword;
    }

}
