package com.mb.mmdepartment.biz.getcities;

import com.mb.mmdepartment.listener.RequestListener;

import java.util.List;

/**
 * Created by jack on 2015/10/19.
 */
public interface IGetcitiesBiz {
    void gethotcities(RequestListener listener);
    void getcities(RequestListener listener);
    void serachcities(String cityname);
}
