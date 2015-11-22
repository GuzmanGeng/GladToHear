package com.mb.mmdepartment.constans;

/**
 * Created by Administrator on 2015/9/9.
 */
public class CatlogConsts {

    /**
     * 获取超市
     */
    public class GetMarket{
        public static final String params_app="shop";
        public static final String params_class="getaddress";
        public static final String params_sign="e4bd9c59021dc095736ef5d44d202e70";
    }
    /**
     * 获取超市搜索列表
     */
    public class GetMarketSearchList{
        public static final String params_app="shop";
        public static final String params_class="searchlist";
        public static final String params_sign="bb28a82b860ca10c420934463d267666";
    }
    /**
     * 获取分类
     */
    public class GetCommodity{
        public static final String params_app="shop";
        public static final String params_class="getcategory";
        public static final String  params_sign="b595c72c94df9ed88d9a530cf17d9d47";
    }
    /**
     * 根据四个名称获得资讯汇总
     */
    public class GetInfomation{
        public static final String params_app="news";
        public static final String params_class="getnewsbycategory";
        public static final String params_sign="4e4e0cf81c2c8692f67456d80befa82d";
    }

    /**
     * 我的订阅
     */
    public class MyReader {
        public static final String params_app = "cas";
        public static final String params_class = "getusertaglist";
        public static final String params_sign = "26f53261ff04d4dda4359260205cf0d1";
    }
    /**
     * 积分商城
     */
    public class Accumulation{
        public static final String params_app = "product";
        public static final String params_class = "getproductsbystatus";
        public static final String params_sign = "a8f9422aabcc6c995f5e55db3185b5d3";
    }
    /**
     * 积分商城详细
     */
    public class AccumulationDetail{
        public static final String params_app = "product";
        public static final String params_class = "getproductsbyid";
        public static final String params_sign = "0719e21500a5ebd4bdd32b4455fd330a";
    }

    /**
     * mains 品牌资讯列表  获取搜索列表
     */
    public class Brand{
        public static final String params_app = "news";
        public static final String params_class = "getnewslist";
        public static final String params_sign = "1b84c7f0ad96051add15393009b77ae0";
    }
    /**
     * mains 品牌资讯列表详情
     */
    public class InformationDetail{
        public static final String params_app = "news";
        public static final String params_class = "getnewscontent";
        public static final String params_sign = "64dd7e40f27f959880b57415f1c3d8a5";
    }
    /**
     * user space prize_exchange
     */
    public class PrizeExchangeCode{
        public static final String params_app = "cas";
        public static final String params_class = "code";
        public static final String params_sign = "3cb050df47628de032f63fac8d3eb2bf";
    }
    /**
     * user space lsit_record
     */
    public class UserSpaceListRecord{
        public static final String params_app = "shop";
        public static final String params_class = "getorders";
        public static final String params_sign = "42e555d2dbba6b9da67924e7fa871fdf";
    }
    /**
     * user space exchange_record
     */
    public class UserSpaceExchangeRecord{
        public static final String params_app = "product";
        public static final String params_class = "getexchange";
        public static final String params_sign = "ef8187b36dce1d53deb7bd8bcef9d250";
    }
    /**
     * user space exchange_add_record
     */
    public class UserSpaceAddExchangeRecord{
        public static final String params_app = "product";
        public static final String params_class = "addexchange";
        public static final String params_sign = "3c200e636334989a6ebc8c54a524dac7";
    }
    /**
     * 获取帮你查热门
     */
    public class HelpCheckHot{
        public static final String params_app = "shop";
        public static final String params_class = "hotsearch";
        public static final String params_sign = "a75786dc420a30e8a018b3882e663c5e";
    }
    /**
     * 获取短信验证码
     */
    public class GetMailCode{
        public static final String params_app = "cas";
        public static final String params_class = "code";
        public static final String params_sign = "3cb050df47628de032f63fac8d3eb2bf";
    }
    /**
     * 用户注册
     */
    public class Regist{
        public static final String params_app = "cas";
        public static final String params_class = "in";
        public static final String params_sign = "62fa87ace6f05416fed79e020fb9661c";
    }
    /**
     * 评论列表
     */
    public class CommentList{
        public static final String params_app = "comment";
        public static final String params_class = "getcommentlist";
        public static final String params_sign = "e9d1ce83c2c6b1ecc74f0b0212145f96";
    }
    /**
     * 添加评论
     */
    public class AddComment{
        public static final String params_app = "comment";
        public static final String params_class = "addcomment";
        public static final String params_sign = "87bc68824af863d478e34d5845dfeb05";
    }
    /**
     * 购买方案
     */
    public class SortPlan{
        public static final String params_app = "shop";
        public static final String params_class = "getcontent";
        public static final String params_sign = "6984eb4afaf95f6b97c2419e1597d6c3";
    }

    public class Submitorders{
        public static final String params_app = "shop";
        public static final String params_class = "submitorders";
        public static final String params_sign = "586be835837e91337c5022ab60af55bf";
    }
    /**
     * 评论列表
     */
    public class SendComment{
        public static final String params_app = "comment";
        public static final String params_class = "addcomment";
        public static final String params_sign = "87bc68824af863d478e34d5845dfeb05";
    }
    /**
     * 获取hot信息
     */
    public class TagList{
        public static final String params_app = "cas";
        public static final String params_class = "gethottaglist";
        public static final String params_sign = "c81d4919124eb9ea470360f130382937";
    }
    /**
     * 添加搜索信息
     */
    public class AddHistoryTag{
        public static final String params_app = "cas";
        public static final String params_class = "addsearchkeyword";
        public static final String params_sign = "022d5b89cd554dffcc0d27b56f7f8f4a";
    }
    /**
     * 获取我的评论信息
     */
    public class MainMyChat{
        public static final String params_app = "comment";
        public static final String params_class = "getcommentforme";
        public static final String params_sign = "b23bede7439cb433491e83a719e75d70";
    }
    /**
     * 获取我的回复
     */
    public class MainMyReplay{
        public static final String params_app = "comment";
        public static final String params_class = "getcommentaboutme";
        public static final String params_sign = "1d278fddb34b12663e4b91690eef04dc";
    }
    /**
     * 意见反馈
     */
    public class FeedBack{
        public static final String params_app = "feedback";
        public static final String params_class = "addfeedback";
        public static final String params_sign = "3d63d9dcf90e8a89e350ef9a0cecd8cd";
    }
    /**
     * 头像上传
     */
    public class ReloadPhoto{
        public static final String params_app = "cas";
        public static final String params_class = "updateuserheadpic";
        public static final String params_sign = "92a6cb45afef88007742be1d2ea82804";
    }
    /**
     * 积分查询
     */
    public class ScoreSearch{
        public static final String params_app = "cas";
        public static final String params_class = "integral";
        public static final String params_sign = "e9ee44230247679934b96016cbe81acc";
    }
    /**
     * 版本更新
     */
    public class RefereshUpdate{
        public static final String params_app = "system";
        public static final String params_class = "getversion";
        public static final String params_sign = "090ed9f4dafa9cac13eace31574c3d90";
    }
    /**
     * 城市区域
     */
    public class Areas{
        public static final String params_app = "shop";
        public static final String params_class = "getcityarea";
        public static final String params_sign = "989a67e9fefc710f849a5b418eb6da7f";
    }
    /**
     * 附近超市
     */
    public class AreasList{
        public static final String params_app = "shop";
        public static final String params_class = "getnearbyshop";
        public static final String params_sign = "49613c70b2114e25e2c850b77d5e8a85";
    }
}
