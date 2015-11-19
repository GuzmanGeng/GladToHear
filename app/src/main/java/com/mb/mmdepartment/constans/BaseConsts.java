package com.mb.mmdepartment.constans;

import com.mb.mmdepartment.bean.helpcheck.shopcart.ShopRoot;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/2.
 */
public class BaseConsts {
    private BaseConsts() {
    }

    //url
    public static final String BASE_URL = "http://admin.joyone2one.com//interface";
    //image_url
    public static final String BASE_IMAGE_URL = "http://admin.joyone2one.com/upload";
    /**
     * MD5加密的秘钥(sign)
     */
    public static final String APP_KEY = "O]dWJ,[*g)%k\"?q~g6Co!`cQvV>>Ilvw";
    //参数
    public static final String APP = "app";
    /**
     * 用户注册 class
     */
    public static final String CLASS = "class";
    /**
     * 用户注册 sign
     */
    public static final String SIGN = "sign";

    /**
     * 用户模块 用户名
     */
    public static final String username = "username";
    /**
     * 用户模块 密码
     */
    public static final String password = "password";

    public static final class SharePreference {
        private SharePreference() {

        }

        public static final String SCREEN_WIDTH = "screen_width";
        public static final String SCREEN_HEIGHT = "screen_height";
        public static final String IS_FIRST_INTO = "is_first_into";
        public static final String USER_TOKEN = "user_token";
        public static final String USER_ID = "user_id";
        public static final String USER_NAME = "joy_one2one_user_name";
        public static final String USER_PASS = "joy_one2one_user_pass";
        public static final String USER_SCORE = "user_score";
        public static final String USER_LITTLE_IMAGE = "user_little_image";
        public static final String NICK_NAME = "nick_name";
        public static final String USER_USERSTATUS = "user_userstatus";

    }

    //程序退出广播的action
    public static final String INTENT_ACTION_EXIT_APP = "com.mb.mmdepartment.broadcast.exit";
    //resultcode

}
