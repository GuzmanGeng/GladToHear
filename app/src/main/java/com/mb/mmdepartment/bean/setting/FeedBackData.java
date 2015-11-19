package com.mb.mmdepartment.bean.setting;

/**
 * Created by joyone2one on 2015/11/19.
 */
public class FeedBackData {
    private String userid;

    private String body;

    private String attachment;

    private String title;

    private String label;

    private String location;

    private String tag_name;

    private String ctime;

    public void setUserid(String userid){
        this.userid = userid;
    }
    public String getUserid(){
        return this.userid;
    }
    public void setBody(String body){
        this.body = body;
    }
    public String getBody(){
        return this.body;
    }
    public void setAttachment(String attachment){
        this.attachment = attachment;
    }
    public String getAttachment(){
        return this.attachment;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setLabel(String label){
        this.label = label;
    }
    public String getLabel(){
        return this.label;
    }
    public void setLocation(String location){
        this.location = location;
    }
    public String getLocation(){
        return this.location;
    }
    public void setTag_name(String tag_name){
        this.tag_name = tag_name;
    }
    public String getTag_name(){
        return this.tag_name;
    }
    public void setCtime(String ctime){
        this.ctime = ctime;
    }
    public String getCtime(){
        return this.ctime;
    }
}
