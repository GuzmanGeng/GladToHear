package com.mb.mmdepartment.bean.userspace.listrecord.getexchangeprizerecord;

import java.util.List;

/**
 * Created by jack on 2015/10/19.
 */
public class Data {
    private String userid;
    private List<Exchange> exchange;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public List<Exchange> getExchanges() {
        return exchange;
    }

    public void setExchanges(List<Exchange> exchange) {
        this.exchange = exchange;
    }
}
