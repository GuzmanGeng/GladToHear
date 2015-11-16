package com.mb.mmdepartment.bean.main_search;

/**
 * Created by joyone2one on 2015/11/13.
 */
public class Market {
    private String market_id;

    private String market_category_id;

    private String market_name;

    private String is_system;

    private String market_type;

    private String rel_count;

    private String clicks;

    private String status;

    private String is_hot;

    private String sort_order;

    public void setMarket_id(String market_id){
        this.market_id = market_id;
    }
    public String getMarket_id(){
        return this.market_id;
    }
    public void setMarket_category_id(String market_category_id){
        this.market_category_id = market_category_id;
    }
    public String getMarket_category_id(){
        return this.market_category_id;
    }
    public void setMarket_name(String market_name){
        this.market_name = market_name;
    }
    public String getMarket_name(){
        return this.market_name;
    }
    public void setIs_system(String is_system){
        this.is_system = is_system;
    }
    public String getIs_system(){
        return this.is_system;
    }
    public void setMarket_type(String market_type){
        this.market_type = market_type;
    }
    public String getMarket_type(){
        return this.market_type;
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
