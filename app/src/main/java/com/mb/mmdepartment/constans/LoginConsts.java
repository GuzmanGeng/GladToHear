package com.mb.mmdepartment.constans;

import com.mb.mmdepartment.tools.MD5Utils;

/**
 * 登录时候的信息判断
 */
public class LoginConsts {
    public static final int NO_FORMAT_ERROR=0;//格式没有错误
    public static final int PHONE_FORMAT_ERROR=1;//手机号格式错误
    public static final int PASS_FORMAT_ERROR=2;//密码格式错误
    public static final int ACCOUNT_ERROR=3;//手机账号错误
    public static final int PASS_ERROR=4;//密码信息错误
    public static final int PHONE_EMPTY=5;//手机号为空
    public static final int PASS_EMPTY=6;//密码为空
    /**
     *
     * @ClassName: Account
     * @Description: 用户系统接口
     * @author gengchuan
     * @date 2015年9月2日 下午3:45:34
     */
    public class Account {
        /**
         * 用户验证 app
         */
        public static final String APP_ADDRESS = "cas";
        /**
         *
         * @ClassName: GetMessage
         * @Description: 用户获取验证码
         * @author lip
         * @date 2014年7月29日 下午3:45:01
         */
        public class GetMessage {
            public static final String params_class = "code";
            public static final String params_sign = "3cb050df47628de032f63fac8d3eb2bf";
        }

        /**
         * 顶，踩
         *
         * @author guozw 2014-9-12
         *
         */
        public class Agree {
            public static final String params_class = "addagree";
            public static final String params_sign = "0075fa7b763226061794cd7bec46167c";
        }

        /**
         *
         * @ClassName: UserName
         * @Description: 获取收藏信息
         * @author wenmr
         * @date 2015年5月21日 下午15:16:10
         */
        public class UserName {
            public static final String params_class = "username";
        }

        /**
         *
         * @ClassName: Reg
         * @Description: 注册
         * @author lip
         * @date 2014年7月29日 下午4:58:33
         */
        public class Reg {
            public static final String params_class = "in";
            public static final String params_sign = "62fa87ace6f05416fed79e020fb9661c";
        }

        /**
         *
         * @ClassName: Reg
         * @Description: 注册
         * @author lip
         * @date 2014年7月29日 下午4:58:33
         */
        public class setFindPwd {
            public static final String params_class = "phonepwd";
            public static final String params_sign = "5b354263b3d6e5fc9024ce94318b540a";
        }

        /**
         *
         * @ClassName: Login
         * @Description: 登录
         * @author lip
         * @date 2014年7月29日 下午4:58:41
         */
        public class Login {
            public static final String params_class = "sign";
            public static final String params_sign = "7e515e1245e7abb6409aa239335d5f2d";
        }

        /**
         * 第三方QQ登录
         */
        public final class QQLogin {
//            public static final String QQ_APP_KEY = "1104765835";
            public static final String QQ_APP_KEY = "1103492733";
            private static final String WEIXIN_APP_ID = "wx13bfc51c959a8c2e";

//            public static final String QQ_APPSECRET = "tc4jiqzbzzXfiFIM";
            public static final String QQ_APPSECRET = "N4wivGXEKHZRr5hZ";
            public static final String DESCRIPTOR = "com.mb.mmdepartment";
        }


        /**
         * 第三方微信登录
         */
        public final class WXLogin {
            public static final String WEIXIN_APP_ID = "wx0d047246911bcdc5";
            //b549ee623dbaa946e982dbf3298e14e6
            public static final String QQ_APPSECRET = "ea73cfe880553e56d2520ce15b6d1528";
            public static final String DESCRIPTOR = "com.mb.mmdepartment";
        }


        /**
         * 第三方新浪登录
         */
        public final class SinaLogin {
            public static final String APP_KEY_FOR_BLOG = "1218845790";
            //b549ee623dbaa946e982dbf3298e14e6
            public static final String REDIRECT_URL = "http://open.weibo.com/";// 微博回调网页
            public static final String SCOPE = "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read," + "follow_app_official_microblog,invitation_write";

            public static final long SimuMatchId = 1;
            public static final long BookMatchId = 10000;
        }

        /**
         *
         * @ClassName: MyDetilUpdate
         * @Description: 修改个人信息
         * @author lip
         * @date 2014年7月30日 下午2:00:24
         */
        public class MyDetilUpdate {
            public static final String params_class = "personal";
            public static final String params_sign = "d438f60a4c9a8771be1afb959678ba5a";
        }

        /**
         *
         * @ClassName: EditPassword
         * @Description: 用户修改密码
         * @author lip
         * @date 2014年7月30日 下午2:52:01
         */
        public class EditPassword {
            public static final String params_class = "pwd";
            public static final String params_sign = "1c1a4a0a148932677b5cdc2e84300c85";
        }

        /**
         *
         * @ClassName: GetCollent
         * @Description: 获取收藏信息
         * @author lip
         * @date 2014年7月30日 下午3:16:10
         */
        public class GetCollent {
            public static final String params_class = "getuserfavorite";
            public static final String params_sign = "7a6c4cc07ea327f5741f2c3f245f8462";
        }

        /**
         *
         * @ClassName: DelCollent
         * @Description: 删除收藏
         * @author lip
         * @date 2014年7月31日 下午3:00:45
         */
        public class DelCollent {
            public static final String params_class = "deleteuserfavorite";
            public static final String params_sign = "2c085c7c077f14188b88b6aa91727abb";
        }

        /**
         *
         * @ClassName: AtMine
         * @Description: //@我的
         * @author lip
         * @date 2014年7月31日 上午10:08:28
         */
        public class AtMine {
            public static final String params_class = "getsmslist";
            public static final String params_sign = "33ec575faa1c1301b9088347f4069040";
        }

        /**
         *
         * @ClassName: FriendList
         * @Description: 好友列表
         * @author lip
         * @date 2014年7月31日 下午2:02:08
         */
        public class FriendList {
            public static final String params_class = "getfriendlist";
            public static final String params_sign = "f4205c68892ba53fb0477a5a9b441b99";
        }

        /**
         *
         * @ClassName: Randfriend
         * @Description: 推荐好友
         * @author lip
         * @date 2014年7月31日 下午2:21:56
         */
        public class Randfriend {
            public static final String params_class = "randfriend";
        }

        /**
         *
         * @ClassName: Addfriend
         * @Description: 添加好友
         * @author lip
         * @date 2014年7月31日 下午3:21:10
         */
        public class Addfriend {
            public static final String params_class = "addfriend";
            public static final String params_sign = "5709df0cc2326eface7ace05e1a3351f";
        }

        /**
         *
         * @ClassName: Searchfriend
         * @Description: 查找好友
         * @author lip
         * @date 2014年7月31日 下午3:21:41
         */
        public class Searchfriend {
            public static final String params_class = "searchfriend";
        }

        /**
         *
         * @ClassName: Friendinfo
         * @Description: 用户个人信息列表
         * @author lip
         * @date 2014年7月31日 下午4:43:15
         */
        public class Friendinfo {
            public static final String params_class = "getfriendinfo";
        }

        /**
         *
         * @ClassName: AtFriend
         * @Description: @对方
         * @author lip
         * @date 2014年7月31日 下午5:33:24
         */
        public class AtFriend {
            public static final String params_class = "addsms";
        }

        /**
         *
         * @ClassName: Delfreiend
         * @Description: 删除好友
         * @author lip
         * @date 2014年8月1日 上午9:28:30
         */
        public class Delfreiend {
            public static final String params_class = "deletefriend";
        }

        /**
         *
         * @ClassName: Updateuserheadpic
         * @Description: 上传头像的接口
         * @author lip
         * @date 2014年8月4日 上午11:51:22
         */
        public class Updateuserheadpic {
            public static final String params_class = "updateuserheadpic";
        }

        /**
         *
         * @ClassName: AddUserFavorite
         * @Description: 收藏的接口
         * @author lip
         * @date 2014年8月5日 上午11:24:21
         */
        public class AddUserFavorite {
            public static final String params_class = "adduserfavorite";

        }
        /**
         *
         * @ClassName: Getuserheadpic
         * @Description: 上传头像的接口
         * @author lip
         * @date 2014年8月4日 上午11:51:22
         */
        public class Getuserheadpic {
            public static final String params_class = "getuserheadpic";
            public static final String params_sign = "6e711fed20abd52817c800a8fd03276a";
        }
    }

    /**
     * @ClassName:GetCities
     * @Descripition:查找城市
     * @author krisian
     * @date 2015年10月19日01:51:06
     */
    public class GetCities {
        public static final String params_class = "gethotcitylisting";
        public static final String APP_ADDRESS = "system";
    }

    /**
     * @ClassName:GetCities
     * @Descripition:查找城市
     * @author krisian
     * @date 2015年10月19日01:51:06
     */
    public class SerachCities {
        public static final String params_class = "getcitybycityname";
        public static final String APP_ADDRESS = "system";
    }

    /**
     * @ClassName:GetCities
     * @Descripition:查找城市
     * @author krisian
     * @date 2015年10月19日01:51:06
     */
    public class GetLetterCities {
        public static final String params_class = "getcitylisting";
        public static final String APP_ADDRESS = "system";
    }

    /**
     * @ClassName:GetCities
     * @Descripition:兑换奖品
     * @author krisian
     * @date 2015年10月19日14:42:08
     */
    public class GetWinCode {
        public static final String params_class = "getwincode";
        public static final String APP_ADDRESS = "shop";
    }

    /**
     * @ClassName:GetExchangePrizeRecord
     * @Descripition:兑换记录
     * @author krisian
     * @date 2015年10月19日14:42:18
     */
    public class GetExchangePrizeRecord {
        public static final String params_class = "getexchange";
        public static final String APP_ADDRESS = "product";
    }

    /**
     * @ClassName:GetListRecordDetail
     * @Descripition:兑换详情
     * @author krisian
     * @date 2015年10月20日14:22:52
     */
    public class GetListRecordDetail {
        public static final String params_class = "getpo";
        public static final String APP_ADDRESS = "shop";
    }
}
