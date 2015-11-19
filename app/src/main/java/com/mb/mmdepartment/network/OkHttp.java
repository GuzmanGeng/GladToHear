package com.mb.mmdepartment.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.tools.ImageUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class OkHttp {
    private static final String TAG = "OkHttp";
    public static final OkHttpClient mOkHttpClient = new OkHttpClient();
    public static final int NET_STATE=0;
    /**
     * 设置的缓存大小
     */
    private static int cacheSize = 10 * 1024 * 1024; // 10 MiB

    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    // timeout
    static {
        mOkHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
        mOkHttpClient.setWriteTimeout(60, TimeUnit.SECONDS);
        mOkHttpClient.setReadTimeout(60, TimeUnit.SECONDS);
        mOkHttpClient.setCookieHandler(new CookieManager(new PersistentCookieStore(TApplication.getContext()), CookiePolicy.ACCEPT_ALL));
        /**
         * 当你的应用在被用户卸载后，SDCard/Android/data/你的应用的包名/ 这个目录下的所有文件都会被删除，不会留下垃圾信息
         */
        mOkHttpClient.setCache(new Cache(TApplication.getContext().getExternalCacheDir(), cacheSize));
    }

    /**
     * 取消请求
     *
     * @param no
     */
    public static void cancleMainNetWork(String[] no) {
        String[] ccs = new String[]{"FirstFragment", "SecondFragment", "ThirdFragment", "doctor_type", "doctor_right", "active_message", "active_contact"};

        for (int i = 0; i < ccs.length; i++) {
            for (int j = 0; j < no.length; j++) {
                if (!ccs[i].equals(no[j]))
                    mOkHttpClient.cancel(ccs[i]);
            }
        }
    }
    /**
     * 取消请求
     *
     * @param no
     */
    public static void cancleInforMationNetWork(String[] no) {
        String[] ccs = new String[]{"FoodDrinkFragment", "MomBabyFragment", "FamilyHomeFragment", "CosmticSkinFragment"};

        for (int i = 0; i < ccs.length; i++) {
            for (int j = 0; j < no.length; j++) {
                if (!ccs[i].equals(no[j]))
                    mOkHttpClient.cancel(ccs[i]);
            }
        }
    }
    /**
     * 取消请求
     *
     * @param no
     */
    public static void cancleAccumulateNetWork(String[] no) {
        String[] ccs = new String[]{"BeautifulPresentFragment", "DataAddFragment", "ScoreSortFragment"};

        for (int i = 0; i < ccs.length; i++) {
            for (int j = 0; j < no.length; j++) {
                if (!ccs[i].equals(no[j]))
                    mOkHttpClient.cancel(ccs[i]);
            }
        }
    }

    /**
     * 不使用异步线程。
     *
     * @param request
     * @return
     * @throws IOException
     */
    private static Response execute(Request request) throws IOException {
        return mOkHttpClient.newCall(request).execute();
    }

    /**
     * 开启异步线程访问网络
     *
     * @param request
     * @param responseCallback
     */
    private static void enqueue(Request request, Callback responseCallback) {

        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }

    /**
     * 同步Get(一般不使用)
     *
     * @param url
     * @return String
     */
    public static String syncGet(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = execute(request);
        if (!response.isSuccessful())
            throw new IOException("Unexpected code " + response);
        Headers responseHeaders = response.headers();
        for (int i = 0; i < responseHeaders.size(); i++) {
            Log.i(TAG, responseHeaders.name(i) + ": " + responseHeaders.value(i));
        }
        Log.i(TAG, "cache response:    " + response.cacheResponse());
        Log.i(TAG, "network response:  " + response.networkResponse());
        return response.body().string();
    }

    /**
     * 异步get
     *
     * @param url
     * @param callback
     * @return
     */
    public static void asyncGet(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        enqueue(request, callback);
    }

    // post without file
    public static void asyncPost(String url, Map<String, String> body, Callback callback) {


        FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
        for (String key : body.keySet()) {
            if (TextUtils.isEmpty(body.get(key))) {
                return;
            }
            formEncodingBuilder.add(key, body.get(key));
        }
        RequestBody formBody = formEncodingBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        enqueue(request, callback);
    }

//    // post without file
//    public static void asyncPost(String url, Map<String, Object> body, Callback callback) {
//
//
//        FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
//        for (String key : body.keySet()) {
//            if (TextUtils.isEmpty((String)body.get(key))) {
//                return;
//            }
//            formEncodingBuilder.add(key, body.get(key));
//        }
//        RequestBody formBody = formEncodingBuilder.build();
//        Request request = new Request.Builder()
//                .url(url)
//                .post(formBody)
//                .build();
//        enqueue(request, callback);
//    }

    // post without file with tag
    public static void asyncPost(String url, Map<String, String> body, String tag, Callback callback) {
        FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
        for (String key : body.keySet()) {
            if (TextUtils.isEmpty(body.get(key))) {
                return;
            }
            formEncodingBuilder.add(key, body.get(key));
        }
        RequestBody formBody = formEncodingBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .tag(tag)
                .build();
        enqueue(request, callback);
    }

    //	 post with file
    public static void asyncPost(String url, Map<String, String> body, File file, Callback callback) {
        MultipartBuilder multipartBuilder = new MultipartBuilder();
        multipartBuilder.type(MultipartBuilder.FORM);
        for (String key : body.keySet()) {
            multipartBuilder.addFormDataPart(key, body.get(key));
        }
        if (file != null && file.exists()) {
            multipartBuilder.addFormDataPart("headpic","image", RequestBody.create(MEDIA_TYPE_PNG, getSmallBitmap(file.getAbsolutePath())));
        }
        RequestBody formBody = multipartBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        enqueue(request, callback);
    }
    public static byte[] getSmallBitmap(String filePath) {
        ByteArrayOutputStream baos = null;
        Bitmap bitmap = null;
        Bitmap bitmapCache = null;
        byte[] bytes = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, options);
//            options.inSampleSize = calculateInSampleSize(options, 320, 540);
            options.inSampleSize = calculateInSampleSize(options, 150, 250);
            options.inJustDecodeBounds = false;
            bitmapCache = BitmapFactory.decodeFile(filePath, options);
            if (bitmapCache == null) {
                return null;
            }
            bitmap = ImageUtils.rotaingImageView(ImageUtils.readPictureDegree(filePath), bitmapCache);
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
            bytes = baos.toByteArray();
        } catch (Exception e) {

        } finally {
            if (baos != null) try {
                baos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (baos != null) try {
                baos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
}
