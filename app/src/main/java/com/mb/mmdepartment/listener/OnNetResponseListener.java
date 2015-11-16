package com.mb.mmdepartment.listener;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Administrator on 2015/9/24 0024.
 */
public interface OnNetResponseListener {
    void onNetResponse(Response response);
    void onNetFailue(Request request, IOException e);
}
