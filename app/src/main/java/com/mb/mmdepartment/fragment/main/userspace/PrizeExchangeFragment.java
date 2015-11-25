package com.mb.mmdepartment.fragment.main.userspace;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.mb.mmdepartment.tools.CustomToast;
import com.sina.weibo.sdk.api.share.Base;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.activities.UserSpaceActivity;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.userspace.listrecord.getwin.Root;
import com.mb.mmdepartment.biz.userspace.listrecord.getwin.GetWin;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.tools.TDevide;
import com.mb.mmdepartment.tools.ToastControl;
import com.mb.mmdepartment.tools.log.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/9/17 0017.
 */
public class PrizeExchangeFragment extends Fragment implements RequestListener,ToastControl{
    private EditText prize_exchange_code_ed;
    private TextView prize_exchange_commit_tv;
    private String input_code,info;
    private String json_code="abcd";
    private static String lastToast=null;
    private static long lastToastTime=0;
    private Handler handler = new Handler(){
      @Override
      public void handleMessage(Message msg){
          super.handleMessage(msg);
          switch (msg.what){
              case 0:
                  CustomToast.show(getActivity(), "提示", info);
                  break;
          }
      }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.prize_exchange_fragment,container,false);
        initView(view);
        setListener();
        return view;
    }
    private void setListener() {
        prize_exchange_commit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BaseActivity)getActivity()).LuPing("btn_Exchage_price","other","exchange",new Date());

                input_code = prize_exchange_code_ed.getText().toString();
                if (TextUtils.isEmpty(input_code)) {
                    ((UserSpaceActivity) getActivity()).showToast("兑换码不能为空");
                    return;
                }
                GetWin getWin = new GetWin();
                getWin.getWin(input_code, BaseConsts.SharePreference.USER_ID, new RequestListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response.isSuccessful()) {
                            Log.i("tag", "兑奖请求成功");
                            Gson gson = new Gson();
                            try {
                                String json=response.body().string();
                                final Root root = gson.fromJson(json, Root.class);
                                if (root.getStatus()== OkHttp.NET_STATE
                                        ||root.getStatus()== 1) {
                                    info = root.getData().getInfo();
                                    handler.sendEmptyMessage(0);
                                }else {
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailue(Request request, IOException e) {
                        showToast("兑奖失败");
                    }
                });
            }
        });
    }
    private void initView(View view) {
        prize_exchange_commit_tv=(TextView)view.findViewById(R.id.prize_exchange_commit_tv);
        prize_exchange_code_ed=(EditText)view.findViewById(R.id.prize_exchange_code_ed);
    }
    @Override
    public void onResponse(Response response) {
        if (response.isSuccessful()){
            Gson gson=new Gson();
            try {
                String json=response.body().string();
            } catch (IOException e) {

            }
        }
    }

    @Override
    public void onFailue(Request request, IOException e) {

    }

    @Override
    public void showToast(int message) {
        showToast(message, Toast.LENGTH_LONG, 0);
    }

    @Override
    public void showToast(String message) {
        showToast(message, Toast.LENGTH_SHORT, 0, 17);
    }

    @Override
    public void showToast(int message, int icon) {
        showToast(message, Toast.LENGTH_LONG, icon);
    }

    @Override
    public void showToast(String message, int icon) {
        showToast(message, Toast.LENGTH_LONG, icon, 17);
    }

    @Override
    public void showToastShort(int message) {
        showToast(message, Toast.LENGTH_SHORT, 0);
    }

    @Override
    public void showToastShort(String message) {
        showToast(message, Toast.LENGTH_SHORT, 0, 17);
    }

    @Override
    public void showToastShort(int message, Object... args) {
        showToast(message, Toast.LENGTH_SHORT, 0, 17, args);
    }

    @Override
    public void showToast(int message, int duration, int icon) {
        showToast(message, duration, icon, 17);
    }

    @Override
    public void showToast(int message, int duration, int icon,
                          int gravity) {
        showToast(TApplication.getContext().getString(message), duration, icon, gravity);
    }

    @Override
    public void showToast(int message, int duration, int icon,
                          int gravity, Object... args) {
        showToast(TApplication.getContext().getString(message, args), duration, icon, gravity);
    }

    /**
     * 弹出框toast
     * @param message
     * @param duration
     * @param icon
     * @param gravity
     */
    @Override
    public void showToast(String message, int duration, int icon,
                          int gravity) {
        if (message != null && !message.equalsIgnoreCase("")) {
            long time = System.currentTimeMillis();
            if (!message.equalsIgnoreCase(lastToast)
                    || Math.abs(time - lastToastTime) > 2000) {
                View view = LayoutInflater.from(TApplication.getContext()).inflate(
                        R.layout.toast_view, null);
                ((TextView) view.findViewById(R.id.title_tv)).setText(message);
                if (icon != 0) {
                    ((ImageView) view.findViewById(R.id.icon_iv))
                            .setImageResource(icon);
                    (view.findViewById(R.id.icon_iv))
                            .setVisibility(View.VISIBLE);
                }
                Toast toast = new Toast(this.getActivity());
                toast.setView(view);
                toast.setGravity(Gravity.BOTTOM | gravity, 0, TDevide.dip2px(84, this.getActivity()));
                toast.setDuration(duration);
                toast.show();
                lastToast = message;
                lastToastTime = System.currentTimeMillis();
            }
        }
    }
}
