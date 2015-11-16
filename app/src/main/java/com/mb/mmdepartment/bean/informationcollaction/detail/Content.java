package com.mb.mmdepartment.bean.informationcollaction.detail;

/**
 * Created by Administrator on 2015/9/24 0024.
 */
public class Content {
    private String content_id;

    private String summary;

    private String title;

    private String image_default;

    private String author;

    private String ctime;

    private String identify;

    private String contentbody;

    private String share_url;

    public void setContent_id(String content_id){
        this.content_id = content_id;
    }
    public String getContent_id(){
        return this.content_id;
    }
    public void setSummary(String summary){
        this.summary = summary;
    }
    public String getSummary(){
        return this.summary;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setImage_default(String image_default){
        this.image_default = image_default;
    }
    public String getImage_default(){
        return this.image_default;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public String getAuthor(){
        return this.author;
    }
    public void setCtime(String ctime){
        this.ctime = ctime;
    }
    public String getCtime(){
        return this.ctime;
    }
    public void setIdentify(String identify){
        this.identify = identify;
    }
    public String getIdentify(){
        return this.identify;
    }
    public void setContentbody(String contentbody){
        this.contentbody = contentbody;
    }
    public String getContentbody(){
        return this.contentbody;
    }
    public void setShare_url(String share_url){
        this.share_url = share_url;
    }
    public String getShare_url(){
        return this.share_url;
    }
}
