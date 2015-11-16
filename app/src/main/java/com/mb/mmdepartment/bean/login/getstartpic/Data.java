package com.mb.mmdepartment.bean.login.getstartpic;

import java.util.List;

/**
 * Created by krisi on 2015/10/29.
 */
public class Data {
    private String advert_group_id;
    private String device_type;
    private List<Description> adverts;

    public String getAdvert_group_id() {
        return advert_group_id;
    }

    public void setAdvert_group_id(String advert_group_id) {
        this.advert_group_id = advert_group_id;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public List<Description> getAdverts() {
        return adverts;
    }

    public void setAdverts(List<Description> adverts) {
        this.adverts = adverts;
    }
}
