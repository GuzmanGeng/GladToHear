package com.mb.mmdepartment.biz.myaccunt;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.login.Data;
import com.mb.mmdepartment.bean.setting.PersonEditData;
import com.mb.mmdepartment.bean.setting.PersonEditRoot;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.constans.CatlogConsts;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.tools.CustomToast;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by joyone2one on 2015/11/25.
 */
public class EditPersonalMessageBiz {
    private Map<String, String> paramas = new HashMap<>();
    private PersonEditRoot root;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    TApplication.user_nick = root.getData().getNickname();
                    PersonEditData data = root.getData();
                    TApplication.user.setUsername(data.getUsername());
                    TApplication.user.setIntegral(data.getIntegral());
                    TApplication.user.setNickname(data.getNickname());
                    TApplication.user.setGender(data.getGender());
                    TApplication.user.setYear(data.getYear());
                    TApplication.user.setMonth(data.getMonth());
                    TApplication.user.setDay(data.getDay());
                    TApplication.user.setOccupation(data.getOccupation());
                    TApplication.user.setArea(data.getArea());
                    TApplication.user.setIncome_range(data.getIncome_range());
                    TApplication.user.setContent(data.getContent());
                    CustomToast.show(TApplication.getContext(),"提示","设置成功");
                    activity.finish();
                    break;
                case 1:
                    CustomToast.show(TApplication.getContext(),"提示","设置失败");
                    break;
                case 2:
                    CustomToast.show(TApplication.getContext(),"提示","网络异常");
                    break;
            }
        }
    };
    private AppCompatActivity activity;
    public EditPersonalMessageBiz(AppCompatActivity activity){
        this.activity=activity;
    }
    /**
     *
     * @param userid 用户id
     * @param nickname 昵称
     * @param year 年
     * @param month 月
     * @param day 日
     * @param content 简介
     * @param occupation 职业
     * @param area 地区
     * @param income_range 工资范围
     * @param gender 性别
     */
    public void editPersionMessage(String userid,String nickname,String year,String month,String day,String content,String occupation,String area,String income_range,String gender) {
        paramas.put(BaseConsts.APP, CatlogConsts.PersonEdit.params_app);
        paramas.put(BaseConsts.CLASS, CatlogConsts.PersonEdit.params_class);
        paramas.put(BaseConsts.SIGN, CatlogConsts.PersonEdit.params_sign);
        paramas.put("userid", userid);
        paramas.put("nickname", nickname);
        paramas.put("year", year);
        paramas.put("month", month);
        paramas.put("day", day);
        paramas.put("content", content);
        paramas.put("occupation", occupation);
        paramas.put("area", area);
        paramas.put("income_range", income_range);
        paramas.put("gender", gender);
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(2);
            }
            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String json = response.body().string();
                    root = gson.fromJson(json, PersonEditRoot.class);
                    if (root.getStatus() == 0) {
                        handler.sendEmptyMessage(0);
                    } else {
                        handler.sendEmptyMessage(1);
                    }
                }
            }
        });
    }
}
