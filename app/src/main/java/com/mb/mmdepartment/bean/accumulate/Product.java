package com.mb.mmdepartment.bean.accumulate;

/**
 * Created by Administrator on 2015/9/11 0011.
 */
public class Product {
    private String content_id;

    private String title;

    private String image;

    private String exchange_integral;

    private String exchange_time;

    private String recommended;

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
    public void setImage(String image){
        this.image = image;
    }
    public String getImage(){
        return this.image;
    }
    public void setExchange_integral(String exchange_integral){
        this.exchange_integral = exchange_integral;
    }
    public String getExchange_integral(){
        return this.exchange_integral;
    }
    public void setExchange_time(String exchange_time){
        this.exchange_time = exchange_time;
    }
    public String getExchange_time(){
        return this.exchange_time;
    }
    public void setRecommended(String recommended){
        this.recommended = recommended;
    }
    public String getRecommended(){
        return this.recommended;
    }

    @Override
    public String toString() {
        return "Product{" +
                "content_id='" + content_id + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", exchange_integral='" + exchange_integral + '\'' +
                ", exchange_time='" + exchange_time + '\'' +
                ", recommended='" + recommended + '\'' +
                '}';
    }
}
