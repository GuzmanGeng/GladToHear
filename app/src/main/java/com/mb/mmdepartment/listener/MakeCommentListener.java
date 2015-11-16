package com.mb.mmdepartment.listener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
public interface MakeCommentListener {
    void onMakeCommentResponse(Response response);
    void onMakeCommentFailue(Request request, IOException e);
}
