package com.mb.mmdepartment.bean.main_search;

/**
 * Created by joyone2one on 2015/11/13.
 */
public class Tags {
    private String tag_id;

    private String tag_category_id;

    private String tag_name;

    private String is_system;

    private String tag_type;

    private String rel_count;

    private String clicks;

    private String status;

    private String is_hot;

    private String sort_order;

    public void setTag_id(String tag_id){
        this.tag_id = tag_id;
    }
    public String getTag_id(){
        return this.tag_id;
    }
    public void setTag_category_id(String tag_category_id){
        this.tag_category_id = tag_category_id;
    }
    public String getTag_category_id(){
        return this.tag_category_id;
    }
    public void setTag_name(String tag_name){
        this.tag_name = tag_name;
    }
    public String getTag_name(){
        return this.tag_name;
    }
    public void setIs_system(String is_system){
        this.is_system = is_system;
    }
    public String getIs_system(){
        return this.is_system;
    }
    public void setTag_type(String tag_type){
        this.tag_type = tag_type;
    }
    public String getTag_type(){
        return this.tag_type;
    }
    public void setRel_count(String rel_count){
        this.rel_count = rel_count;
    }
    public String getRel_count(){
        return this.rel_count;
    }
    public void setClicks(String clicks){
        this.clicks = clicks;
    }
    public String getClicks(){
        return this.clicks;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public String getStatus(){
        return this.status;
    }
    public void setIs_hot(String is_hot){
        this.is_hot = is_hot;
    }
    public String getIs_hot(){
        return this.is_hot;
    }
    public void setSort_order(String sort_order){
        this.sort_order = sort_order;
    }
    public String getSort_order(){
        return this.sort_order;
    }
}
