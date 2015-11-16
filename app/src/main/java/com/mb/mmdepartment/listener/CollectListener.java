package com.mb.mmdepartment.listener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
public interface CollectListener {
    void onCollectionResponse(Response response);
    void onCollectioFailue(Request request, IOException e);
}
