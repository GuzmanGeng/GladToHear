package com.mb.mmdepartment.bean.user;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/10/2 0002.
 */
public class User implements Serializable {

    private static final long serialVersionUID = -2592481415774097534L;
    private String userid;
    private String user_group_id;
    private String user_lv_id;
    private String username;
    private String thirdid;
    private String phone;
    private String verifiedPhone;
    private String nickname;
    private String avatar;
    private String integral;
    private String year;
    private String month;
    private String day;
    private String content;
    /** 职业类型 */
    private String occupation;
    /** 居住区域 */
    private String area;
    /** 薪资范围 */
    private String income_range;
    /** 性别 */
    private String gender;

    private String ctime;
    private String last_login_time;
    private String last_login_ip;
    private String realname;
    private String experience;
    private String type;
    private String token;

    public User() {
    }

    public User(String userid, String user_group_id, String user_lv_id, String username, String thirdid, String phone, String verifiedPhone, String nickname,
                String avatar, String integral, String year, String month, String day, String content, String occupation, String area, String income_range,
                String gender, String ctime, String last_login_time, String last_login_ip, String realname, String experience, String type, String token) {
        super();
        this.userid = userid;
        this.user_group_id = user_group_id;
        this.user_lv_id = user_lv_id;
        this.username = username;
        this.thirdid = thirdid;
        this.phone = phone;
        this.verifiedPhone = verifiedPhone;
        this.nickname = nickname;
        this.avatar = avatar;
        this.integral = integral;
        this.year = year;
        this.month = month;
        this.day = day;
        this.content = content;
        this.occupation = occupation;
        this.area = area;
        this.income_range = income_range;
        this.gender = gender;
        this.ctime = ctime;
        this.last_login_time = last_login_time;
        this.last_login_ip = last_login_ip;
        this.realname = realname;
        this.experience = experience;
        this.type = type;
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUser_group_id() {
        return user_group_id;
    }

    public void setUser_group_id(String user_group_id) {
        this.user_group_id = user_group_id;
    }

    public String getUser_lv_id() {
        return user_lv_id;
    }

    public void setUser_lv_id(String user_lv_id) {
        this.user_lv_id = user_lv_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getThirdid() {
        return thirdid;
    }

    public void setThirdid(String thirdid) {
        this.thirdid = thirdid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVerifiedPhone() {
        return verifiedPhone;
    }

    public void setVerifiedPhone(String verifiedPhone) {
        this.verifiedPhone = verifiedPhone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
    }

    public String getLast_login_ip() {
        return last_login_ip;
    }

    public void setLast_login_ip(String last_login_ip) {
        this.last_login_ip = last_login_ip;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getIncome_range() {
        return income_range;
    }

    public void setIncome_range(String income_range) {
        this.income_range = income_range;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User [userid=" + userid + ", user_group_id=" + user_group_id + ", user_lv_id=" + user_lv_id + ", username=" + username + ", thirdid=" + thirdid
                + ", phone=" + phone + ", verifiedPhone=" + verifiedPhone + ", nickname=" + nickname + ", avatar=" + avatar + ", integral=" + integral
                + ", year=" + year + ", month=" + month + ", day=" + day + ", content=" + content + ", occupation=" + occupation + ", area=" + area
                + ", income_range=" + income_range + ", gender=" + gender + ", ctime=" + ctime + ", last_login_time=" + last_login_time + ", last_login_ip="
                + last_login_ip + ", realname=" + realname + "]";
    }
}
