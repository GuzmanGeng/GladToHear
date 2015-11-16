package com.mb.mmdepartment.bean.market_address;
/**
 * Created by Administrator on 2015/9/9.
 */
public class Description {
    private String parent_id;

    private String addres;

    private String business_hours;

    private String shop_name2;

    private String special;

    private String route;

    private String shop_id;

    private String shop_name;

    private String shop_logo;

    public void setParent_id(String parent_id){
        this.parent_id = parent_id;
    }
    public String getParent_id(){
        return this.parent_id;
    }
    public void setAddres(String addres){
        this.addres = addres;
    }
    public String getAddres(){
        return this.addres;
    }
    public void setBusiness_hours(String business_hours){
        this.business_hours = business_hours;
    }
    public String getBusiness_hours(){
        return this.business_hours;
    }
    public void setShop_name2(String shop_name2){
        this.shop_name2 = shop_name2;
    }
    public String getShop_name2(){
        return this.shop_name2;
    }
    public void setSpecial(String special){
        this.special = special;
    }
    public String getSpecial(){
        return this.special;
    }
    public void setRoute(String route){
        this.route = route;
    }
    public String getRoute(){
        return this.route;
    }
    public void setShop_id(String shop_id){
        this.shop_id = shop_id;
    }
    public String getShop_id(){
        return this.shop_id;
    }
    public void setShop_name(String shop_name){
        this.shop_name = shop_name;
    }
    public String getShop_name(){
        return this.shop_name;
    }
    public void setShop_logo(String shop_logo){
        this.shop_logo = shop_logo;
    }
    public String getShop_logo(){
        return this.shop_logo;
    }

    @Override
    public String toString() {
        return "Description{" +
                "parent_id='" + parent_id + '\'' +
                ", addres='" + addres + '\'' +
                ", business_hours='" + business_hours + '\'' +
                ", shop_name2='" + shop_name2 + '\'' +
                ", special='" + special + '\'' +
                ", route='" + route + '\'' +
                ", shop_id='" + shop_id + '\'' +
                ", shop_name='" + shop_name + '\'' +
                ", shop_logo='" + shop_logo + '\'' +
                '}';
    }
}
