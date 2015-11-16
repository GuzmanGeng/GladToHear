package com.mb.mmdepartment.listener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;

/**
 * Created by Administrator on 2015/9/22 0022.
 */
public interface RegistResponse {
    void onResponseSuccess(Response response);
    void onFailueRequess(Request request, IOException e);
}
