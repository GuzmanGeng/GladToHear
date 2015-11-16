package com.mb.mmdepartment.listener;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Administrator on 2015/9/11 0011.
 */
public interface RequestListener {
    void onResponse(Response response);
    void onFailue(Request request, IOException e);
}
