package com.mb.mmdepartment.bean.market_sel;

/**
 * Created by Administrator on 2015/11/22.
 */
public class AreaSelData {
    private String id;

    private String province;

    private String province_code;

    private String city;

    private String city_code;

    private String district;

    private String district_code;

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setProvince(String province){
        this.province = province;
    }
    public String getProvince(){
        return this.province;
    }
    public void setProvince_code(String province_code){
        this.province_code = province_code;
    }
    public String getProvince_code(){
        return this.province_code;
    }
    public void setCity(String city){
        this.city = city;
    }
    public String getCity(){
        return this.city;
    }
    public void setCity_code(String city_code){
        this.city_code = city_code;
    }
    public String getCity_code(){
        return this.city_code;
    }
    public void setDistrict(String district){
        this.district = district;
    }
    public String getDistrict(){
        return this.district;
    }
    public void setDistrict_code(String district_code){
        this.district_code = district_code;
    }
    public String getDistrict_code(){
        return this.district_code;
    }
}
