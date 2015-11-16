package com.mb.mmdepartment.biz.getcities;

import com.google.gson.Gson;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.getcities.Root;
import com.mb.mmdepartment.tools.sp.SPCache;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.constans.LoginConsts;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.tools.MD5Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jack on 2015/10/19.
 */
public class GetcitiesBiz implements IGetcitiesBiz {
    private Map<String,String> paramas;
    public void getcities(final RequestListener listener){
        List<String> hotcities = new ArrayList<String>();
        paramas = new HashMap<>();
        paramas.put(BaseConsts.APP, LoginConsts.GetLetterCities.APP_ADDRESS);
        paramas.put(BaseConsts.CLASS, LoginConsts.GetLetterCities.params_class);
        String getSign = MD5Utils.MD5(LoginConsts.GetLetterCities.APP_ADDRESS +
                LoginConsts.GetLetterCities.params_class +
                "O]dWJ,[*g)%k\"?q~g6Co!`cQvV>>Ilvw");
        paramas.put(BaseConsts.SIGN,getSign);
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onFailue(request,e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                listener.onResponse(response);
            }
        });
    }

    public void gethotcities(final RequestListener listener){
        List<String> cities = new ArrayList<String>();
        paramas = new HashMap<>();
        paramas.put(BaseConsts.APP, LoginConsts.GetCities.APP_ADDRESS);
        paramas.put(BaseConsts.CLASS, LoginConsts.GetCities.params_class);
        String getSign = MD5Utils.MD5(LoginConsts.GetCities.APP_ADDRESS +
                LoginConsts.GetCities.params_class +
                "O]dWJ,[*g)%k\"?q~g6Co!`cQvV>>Ilvw");
        paramas.put(BaseConsts.SIGN,getSign);
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onFailue(request, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                listener.onResponse(response);
            }
        });
    }

    /**
     * 地位成功之后获取城市id
     * @param city_name
     */
    public void serachcities(String city_name){
        List<String> cities = new ArrayList<String>();
        paramas = new HashMap<>();
        paramas.put(BaseConsts.APP, LoginConsts.SerachCities.APP_ADDRESS);
        paramas.put(BaseConsts.CLASS, LoginConsts.SerachCities.params_class);
        String getSign = MD5Utils.MD5(LoginConsts.SerachCities.APP_ADDRESS +
                LoginConsts.SerachCities.params_class +
                "O]dWJ,[*g)%k\"?q~g6Co!`cQvV>>Ilvw");
        paramas.put(BaseConsts.SIGN,getSign);
        paramas.put("city_name",city_name);
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }
            @Override
            public void onResponse(Response response) throws IOException {
                Gson gson=new Gson();
                String json = response.body().string();
                Root root = gson.fromJson(json,Root.class);
                if (root.getData().size() != 0) {
                    TApplication.city_id = root.getData().get(0).getCity_id();
                    TApplication.city_name=root.getData().get(0).getCity_name();
                }
            }
        });
    }
    /**
     * 地位成功之后获取城市id
     * @param city_name
     */
    public void serachcities(String city_name, final RequestListener listener){
        List<String> cities = new ArrayList<String>();
        paramas = new HashMap<>();
        paramas.put(BaseConsts.APP, LoginConsts.SerachCities.APP_ADDRESS);
        paramas.put(BaseConsts.CLASS, LoginConsts.SerachCities.params_class);
        String getSign = MD5Utils.MD5(LoginConsts.SerachCities.APP_ADDRESS +
                LoginConsts.SerachCities.params_class +
                "O]dWJ,[*g)%k\"?q~g6Co!`cQvV>>Ilvw");
        paramas.put(BaseConsts.SIGN,getSign);
        paramas.put("city_name",city_name);
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }
            @Override
            public void onResponse(Response response) throws IOException {
               listener.onResponse(response);
            }
        });
    }
    /**
     * 定位成功之后获取城市id
     * @param city_name
     */
    public void getLocationCityId(String city_name){
        paramas = new HashMap<>();
        paramas.put(BaseConsts.APP, LoginConsts.SerachCities.APP_ADDRESS);
        paramas.put(BaseConsts.CLASS, LoginConsts.SerachCities.params_class);
        String getSign = MD5Utils.MD5(LoginConsts.SerachCities.APP_ADDRESS +
                LoginConsts.SerachCities.params_class +
                "O]dWJ,[*g)%k\"?q~g6Co!`cQvV>>Ilvw");
        paramas.put(BaseConsts.SIGN,getSign);
        paramas.put("city_name",city_name);
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }
            @Override
            public void onResponse(Response response) throws IOException {
                Gson gson=new Gson();
                String json = response.body().string();
                Root root = gson.fromJson(json,Root.class);
                if (root.getData().size() != 0) {
                    SPCache.putString("city_id",root.getData().get(0).getCity_id());
                }
            }
        });
    }
}
