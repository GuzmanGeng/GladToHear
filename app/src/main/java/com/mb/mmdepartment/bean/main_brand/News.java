package com.mb.mmdepartment.bean.main_brand;

/**
 * Created by Administrator on 2015/9/17 0017.
 */
public class News {
    private String content_id;

    private String title;

    private String image;

    private String summary;

    private String city_id;

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
    public void setSummary(String summary){
        this.summary = summary;
    }
    public String getSummary(){
        return this.summary;
    }
    public void setCity_id(String city_id){
        this.city_id = city_id;
    }
    public String getCity_id(){
        return this.city_id;
    }

    @Override
    public String toString() {
        return "News{" +
                "content_id='" + content_id + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", summary='" + summary + '\'' +
                ", city_id='" + city_id + '\'' +
                '}';
    }
}
