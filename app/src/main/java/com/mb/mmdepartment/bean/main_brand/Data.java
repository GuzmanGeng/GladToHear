package com.mb.mmdepartment.bean.main_brand;

import java.util.List;

/**
 * Created by Administrator on 2015/9/17 0017.
 */
public class Data {
    private String device_type;

    private String userid;

    private String city_id;

    private String page;

    private String keyword;

    private String zixun_category;

    private int perpage;

    private String count;

    private List<News> news ;

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
    public void setCity_id(String city_id){
        this.city_id = city_id;
    }
    public String getCity_id(){
        return this.city_id;
    }
    public void setPage(String page){
        this.page = page;
    }
    public String getPage(){
        return this.page;
    }
    public void setKeyword(String keyword){
        this.keyword = keyword;
    }
    public String getKeyword(){
        return this.keyword;
    }
    public void setZixun_category(String zixun_category){
        this.zixun_category = zixun_category;
    }
    public String getZixun_category(){
        return this.zixun_category;
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
    public void setNews(List<News> news){
        this.news = news;
    }
    public List<News> getNews(){
        return this.news;
    }
}
