
package com.mb.mmdepartment.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.widget.Toast;
import com.mb.mmdepartment.activities.LoginActivity;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.user.User;
import com.mb.mmdepartment.tools.log.Log;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;
    // public static final String APP_KEY_WEIXIN = "wx13bfc51c959a8c2e";
    public static boolean isFromShareDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("weixin","weixin2");
        /*
		 * 此处仍需再次"registerApp"主要还是为"handleIntent"，否则，即便activity已实例，
		 * onResp或onReq依旧无法响应事件
		 */
        api = WXAPIFactory.createWXAPI(this, TApplication.APP_ID_FOR_WECHAT, true);// 通过WXAPIFactory工厂，获取IWXAPI的实例
        api.registerApp(TApplication.APP_ID_FOR_WECHAT);// 将该app注册到微信
        api.handleIntent(getIntent(), this);
    }

//    private void regToWx(){
//        api = WXAPIFactory.createWXAPI(this, LoginConsts.Account.WXLogin.WEIXIN_APP_ID, true);
//        api.registerApp(LoginConsts.Account.WXLogin.WEIXIN_APP_ID);
//    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.i("req", baseReq.toString());

    }

    /**
     * 处理微信发出的向第三方应用请求app message
     * <p>
     * 在微信客户端中的聊天页面有“添加工具”，可以将本应用的图标添加到其中 此后点击图标，下面的代码会被执行。Demo仅仅只是打开自己而已，但你可
     * 做点其他的事情，包括根本不打开任何页面
     */
    public void onGetMessageFromWXReq(WXMediaMessage msg) {
        Intent iLaunchMyself = getPackageManager().getLaunchIntentForPackage(getPackageName());
        startActivity(iLaunchMyself);
    }

    /**
     * 处理微信向第三方应用发起的消息
     * <p>
     * 此处用来接收从微信发送过来的消息，比方说本demo在wechatpage里面分享
     * 应用时可以不分享应用文件，而分享一段应用的自定义信息。接受方的微信 客户端会通过这个方法，将这个信息发送回接收方手机上的本demo中，当作 回调。
     * <p>
     * 本Demo只是将信息展示出来，但你可做点其他的事情，而不仅仅只是Toast
     */
    public void onShowMessageFromWXReq(WXMediaMessage msg) {
        if (msg != null && msg.mediaObject != null && (msg.mediaObject instanceof WXAppExtendObject)) {
            WXAppExtendObject obj = (WXAppExtendObject) msg.mediaObject;
            Toast.makeText(this, obj.extInfo, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResp(BaseResp resp) {
        // System.out.print("[============wei xin error=====================]"+resp.errCode);
        String result = "";
        if (isFromShareDialog) {
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    result = "发送成功";
//                    MyApplication.content_id = "1";
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    result = "发送取消";
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    result = "发送失败";
                    break;
                default:
                    result = "出现异常";
                    break;
            }
        } else {
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    result = "授权成功";
//                    MyApplication.content_id = "1";

                    final String GetCodeRequest = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + TApplication.APP_ID_FOR_WECHAT + "&secret="
                            + TApplication.APP_SECRET + "&code=" + ((SendAuth.Resp) resp).code + "&grant_type=authorization_code";

                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            WXGetAccessToken(GetCodeRequest);
                        }
                    });
                    thread.start();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    result = "授权取消";
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    result = "授权失败";
                    break;
                default:
                    result = "授权异常";
                    break;
            }
        }

        finish();
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }

    /*
	 * 获取微信登录用户信息
	 */
    private void WXGetAccessToken(String get_access_token) {
        HttpClient get_access_token_httpClient = new DefaultHttpClient();
        String access_token = "";
        String openid = "";
        try {
            HttpPost postMethod = new HttpPost(get_access_token);
            HttpResponse response = get_access_token_httpClient.execute(postMethod); // 执行POST方法
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                InputStream is = response.getEntity().getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String str = "";
                StringBuffer sb = new StringBuffer();
                while ((str = br.readLine()) != null) {
                    sb.append(str);
                }
                is.close();
                String josn = sb.toString();
                JSONObject json1 = new JSONObject(josn);
                // {"openid":"oEiGSuAfLvTy7V_jxaJQJT3gLwT8","expires_in":7200,"scope":"snsapi_userinfo","refresh_token":"OezXcEiiBSKSxW0eoylIeDSZaf5CTklKsfFW0WMUql-BWCN7sto1ScfksGIu_pqTvtHKHiMyegGKs05AMD4cnjZYQpiSAsYQavRqK4dL3fzgA0N0SeHi-42as7tQ2SXbZ3-oHhSMmArMT0NBxZ_6NA",
                // "access_token":"OezXcEiiBSKSxW0eoylIeDSZaf5CTklKsfFW0WMUql-BWCN7sto1ScfksGIu_pqT7hROeifT6NBHtMX8yQ2HIAXykf3M-achu_VbUiVD3QLX6PGD5EOm38x6Z6ImD0AkDnWhpD2fWwQqCZ-uTGXqAw",
                // "unionid":"od4a6jghoXH4rt4t39tm_2MQ_7DI"}
                Log.i("json",josn);
                access_token = (String) json1.get("access_token");
                openid = (String) json1.get("openid");

            } else {
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String get_user_info_url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid + "";
        // String get_user_info_url=getUserInfo(access_token,openid);
        WXGetUserInfo(get_user_info_url);
    }

    /**
     * 获取微信用户个人信息
     *
     * @param get_user_info_url
     *            调用URL
     */
    private void WXGetUserInfo(String get_user_info_url) {
        HttpClient get_access_token_httpClient = new DefaultHttpClient();
        try {
            HttpGet getMethod = new HttpGet(get_user_info_url);
            HttpResponse response = get_access_token_httpClient.execute(getMethod); // 执行GET方法
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                InputStream is = response.getEntity().getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String str = "";
                StringBuffer sb = new StringBuffer();
                while ((str = br.readLine()) != null) {
                    sb.append(str);
                }
                is.close();
                String josn = sb.toString();
                System.out.println("微信" + josn);
                JSONObject json1 = new JSONObject(josn);
                Log.i("json",josn);
                // {"sex":1,"nickname":"小白","unionid":"od4a6jghoXH4rt4t39tm_2MQ_7DI","privilege":[],"province":"Shanghai","openid":"oEiGSuAfLvTy7V_jxaJQJT3gLwT8","language":"zh_CN",
                // "headimgurl":"http:\/\/wx.qlogo.cn\/mmopen\/M9HtUDQ1p7P8iabI1p3JeGDCycR2TY4M6x8ibxQv64icznLdEw5vRdpW2ZNUiastfxqROM5G39w3pF8oA2aAJr2Fp3lQQuic4icFWz\/0",
                // "country":"CN","city":"Baoshan"}

                User user = new User();
                user.setType("weixin");
                user.setUsername("nickname");
                user.setToken("openid");

                Message msg = new Message();
                msg.what = 2;
                msg.obj = user;
                LoginActivity login = new LoginActivity();
                login.handlerInstance.sendMessage(msg);
            } else {
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
