package com.mb.mmdepartment.bean.buyplan.byprice;

import com.mb.mmdepartment.bean.marcketseldetail.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * Created by krisi on 2015/10/23.
 */
public class DataList2 implements Serializable{
    private String name;
    private List<Lists> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Lists> getList() {
        return list;
    }

    public void setList(List<Lists> list) {
        this.list = list;
    }
}
