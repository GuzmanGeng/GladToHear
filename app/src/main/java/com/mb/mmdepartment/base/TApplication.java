package com.mb.mmdepartment.base;
import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;
import com.facebook.stetho.Stetho;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.bean.buyplan.byprice.DataList;
import com.mb.mmdepartment.bean.marcketseldetail.Lists;
import com.mb.mmdepartment.bean.user.User;
import com.mb.mmdepartment.tools.TDevide;
import com.mb.mmdepartment.tools.log.Log;
import com.mb.mmdepartment.tools.log.LogWrapper;
import com.mb.mmdepartment.tools.sp.SPCache;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2015/8/27.
 */
public class TApplication extends Application{
    private static final String TAG=TApplication.class.getSimpleName();
    public static TApplication getInstance=null;
    //购物车
    public static Map<String,Lists> shop_lists=new HashMap<>();//已经选中的商品此处根据id来判断是否在购物车
    public static List<String> ids=new ArrayList<>();//存放所有选择的id
    public static List<DataList> shop_list_to_pick=new ArrayList<>();//提供匹配的列表


    public static boolean isSel;
    //高德地图定位
    private static String spName="yiErYi_gaoDe_local";
    private static SharedPreferences.Editor edit;
    public static String city_id;
    public static String city_name;
    public static String user_id;
    //昵称
    public static User user;
    //用户名
    public static String user_name;
    public static String token="";
    private PushAgent mPushAgent;
    // device_no
    public static String device_no;
    //超市id和名称
    public static Map<String,String> market = new HashMap<>();
    /**
     *
     * Wechat
     */
    // 喜闻乐评：wx13bfc51c959a8c2e
    public static final String APP_ID_FOR_WECHAT = "wx0d047246911bcdc5";
    // 喜闻乐评：b549ee623dbaa946e982dbf3298e14e6；
    public static final String APP_SECRET = "ea73cfe880553e56d2520ce15b6d1528";

    //是否在前台运行
    public static boolean isAlive = true;
    /**
     * 上下文对象
     */
    private static Context mContext;

    //保存用户操作数据
    public static List<LuPinModel> luPinModels = new ArrayList<>();
    //数据备份
    public static List<LuPinModel> luPinModelList = new ArrayList<>();

    public static Context getContext(){
        return mContext;
    }
    /**
     * @return instance
     * 获取Application 单例
     */
    private static TApplication instance;
    public static TApplication getInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        if ("com.mb.mmdepartment".equals(TDevide.getCurProcessName(this))){
            instance=this;
            mContext=getApplicationContext();

//            //极光推送
            JPushInterface.setDebugMode(false); 	// 设置开启日志,发布时请关闭日志
            JPushInterface.init(this);     		// 初始化 JPush

            File cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(), "imageloader/Cache"); //缓存文件的存放地址
            ImageLoaderConfiguration config = new ImageLoaderConfiguration
                    .Builder(getApplicationContext())
                    .memoryCacheExtraOptions(480, 800) // max width, max height
                    .threadPoolSize(3)//线程池内加载的数量
                    .threadPriority(Thread.NORM_PRIORITY - 2)  //降低线程的优先级保证主UI线程不受太大影响
                    .denyCacheImageMultipleSizesInMemory()
                    .memoryCache(new LruMemoryCache(5 * 1024 * 1024)) //建议内存设在5-10M,可以有比较好的表现
                    .memoryCacheSize(5 * 1024 * 1024)
                    .discCacheSize(50 * 1024 * 1024)
                    .discCacheFileNameGenerator(new Md5FileNameGenerator())
                    .tasksProcessingOrder(QueueProcessingType.LIFO)
                    .discCacheFileCount(100) //缓存的文件数量
                    .discCache(new UnlimitedDiscCache(cacheDir))
                    .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                    .imageDownloader(new BaseImageDownloader(getApplicationContext(), 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)
                    .writeDebugLogs() // Remove for release app
                    .build();


//            ImageLoaderConfiguration configuration= ImageLoaderConfiguration.createDefault(this);
            ImageLoader.getInstance().init(config);
            initSharePreference();
            android.util.Log.e("infolog","ahhhhhhhhhhhhaa");
            initLog();
            initStetho();
            initPushAgent();
            initUmengAnalytics();
        }

    }
    /**
     * 初始化 日志
     */
    private void initLog() {
        Log.setLogNode(new LogWrapper());
    }
    /**
     * 初始化Stetho facebook的Android调试工具
     */
    private void initStetho() {
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
    }
    /**
     * 初始化sharepreference
     * 用法：
     * int count = SpCache.getInt(ACTIVITY_CREATE_COUNT, 0) + 1;
     * SpCache.putInt(ACTIVITY_CREATE_COUNT, count);
     */
    private void initSharePreference() {
        SPCache.init(this,"com.mb.mmdepartment");
    }

    /**
     * 初始化友盟推送
     */
    private void initPushAgent(){
        mPushAgent=PushAgent.getInstance(this);
        mPushAgent.setDebugMode(true);
        /**
         *该Handler是在IntentService中被调用，故
         * 1. 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         * 2. IntentService里的onHandleIntent方法是并不处于主线程中，因此，如果需调用到主线程，需如下所示;
         * 	      或者可以直接启动Service
         */
        UmengMessageHandler umengMessageHandler=new UmengMessageHandler(){
            @Override
            public void dealWithCustomMessage(final Context context, final UMessage uMessage) {
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        UTrack.getInstance(getApplicationContext()).trackMsgClick(uMessage);
                        Toast.makeText(context, uMessage.custom, Toast.LENGTH_LONG).show();
                    }
                });
            }
            @Override
            public Notification getNotification(Context context, UMessage uMessage) {
                switch (uMessage.builder_id) {
                    case 0:
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                                .setLargeIcon(getAppIcon())
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle(uMessage.title)
                                .setContentText(uMessage.text);
                        mBuilder.setAutoCancel(true);
                        Notification mNotification = mBuilder.build();
                        return mNotification;
                    default:
                        //默认为0，若填写的builder_id并不存在，也使用默认。
                        return super.getNotification(context, uMessage);
                }
            }
        };
        mPushAgent.setMessageHandler(umengMessageHandler);
        /**
         * 该Handler是在BroadcastReceiver中被调用，故
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         * */
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                handleNotificationClick(msg);
            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);
    }

    /**
     * 初始化友盟统计
     */
    private void initUmengAnalytics() {
        MobclickAgent.setDebugMode(true);
        MobclickAgent.setCatchUncaughtExceptions(true);
    }
    /**
     * 处理推送结果
     * @param msg
     */
    private void handleNotificationClick(UMessage msg) {
        Map<String, String> params = msg.extra;
        String notifyTitle = params.get("title");
        String notifyContent = params.get("content");
        //设置到新的antivity
        Toast.makeText(this,"推送的结果是:notifyTitle="+notifyTitle+",notifyContent="+notifyContent,Toast.LENGTH_LONG).show();
    }

    /**
     * 获得app的icon
     * @return
     */
    private Bitmap getAppIcon() {
        BitmapDrawable bitmapDrawable;
        Bitmap appIcon;
        bitmapDrawable = (BitmapDrawable) TApplication.getContext().getApplicationInfo().loadIcon(getPackageManager());
        appIcon = bitmapDrawable.getBitmap();
        return appIcon;
    }

    public static void createSp(String spName){
        SharedPreferences preferences = getInstance.getSharedPreferences(spName, MODE_PRIVATE);
        edit = preferences.edit();
    }
    public static void put(String key,Object o){
        if (o instanceof Integer){
            edit.putInt(key,(Integer)o);
        }else if (o instanceof Long){
            edit.putLong(key,(Long)o);
        }else if (o instanceof Boolean){
            edit.putBoolean(key, (Boolean)o);
        }else if (o instanceof Float){
            edit.putFloat(key, (Float)o);
        }else if (o instanceof String){
            edit.putString(key, (String)o);
        }else {
            Log.i(TAG, "you may be put a invalid object" + o);
        }
    }
}
