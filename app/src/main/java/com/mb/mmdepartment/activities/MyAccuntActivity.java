package com.mb.mmdepartment.activities;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.login.getuserheadpic.Root;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.mb.mmdepartment.bean.setting.LoadPhotoRoot;
import com.mb.mmdepartment.biz.login.getuserpic.GetUserPicBiz;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.constans.CatlogConsts;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.tools.sp.SPCache;
import com.mb.mmdepartment.view.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tencent.stat.StatService;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class MyAccuntActivity extends BaseActivity implements View.OnClickListener,RequestListener{
    private TextView tv_check_out_login;
    private TextView change_psw;
    private TextView user_info;
    private GetUserPicBiz biz;
    private String path;
    private CircleImageView userpic;
    private LuPinModel luPinModel;
    private TextView user_name;
    private Uri imageUri;
    private Button carema;
    private Button album;
    private Button give_up;
    private String currentHeadPath;
    private ProgressDialog dialogpg;
    private Handler handlerOkHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (dialogpg != null) {
                        dialogpg.dismiss();
                    }
                    showToast("上传成功");
                    break;
                case 1:
                    if (dialogpg != null) {
                        dialogpg.dismiss();
                    }
                    showToast("上传失败");
                    break;
                case 2:
                    if (dialogpg != null) {
                        dialogpg.dismiss();
                    }
                    showToast("网络异常,请检查网络在重试");
                    break;
            }
        }
    };

    @Override
    public int getLayout() {
        return R.layout.activity_my_accunt;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TApplication.activities.remove(this);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        Log.e("userid", TApplication.user_id);
        user_info = (TextView) findViewById(R.id.user_info);
        change_psw = (TextView) findViewById(R.id.change_psw);
        user_name = (TextView) findViewById(R.id.user_name);
        user_name.setText(TApplication.user_name);
        tv_check_out_login = (TextView) findViewById(R.id.tv_check_out_login);
        tv_check_out_login.setOnClickListener(this);
        change_psw.setOnClickListener(this);
        user_info.setOnClickListener(this);
        userpic = (CircleImageView) findViewById(R.id.userpic);
        biz = new GetUserPicBiz();
        biz.getuserpic(MyAccuntActivity.class.getSimpleName(), this);
        userpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upDataHead();
            }
        });
    }
    protected static final int ACTION_IMAGE_CAPTURE = 2;
    protected static final int ACTION_IMAGE_PICK = 1;
    public void upDataHead() {
        View view = LayoutInflater.from(this).inflate(R.layout.photo_choose_dialog, null);
        carema = (Button) view.findViewById(R.id.camera);
        album = (Button) view.findViewById(R.id.album);
        give_up = (Button) view.findViewById(R.id.give_up);
        final Dialog dialog = new Dialog(this,R.style.Translucent_NoTitle);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.mypopwindow_anim_style);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.onWindowAttributesChanged(wl);
        dialog.setCanceledOnTouchOutside(true);
        carema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (checkCameraHardWare()) {
                    String status = Environment.getExternalStorageState();
                    if (status.equals(Environment.MEDIA_MOUNTED)) {
                        SimpleDateFormat format = new SimpleDateFormat(
                                "yyyyMMddHHmmss");
                        Date date = new Date(System.currentTimeMillis());
                        String filename = format.format(date);
                        File path = Environment
                                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                        File outputImage = new File(path, filename + ".jpg");
                        try {
                            if (outputImage.exists()) {
                                outputImage.delete();
                            }
                            outputImage.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        imageUri = Uri.fromFile(outputImage);
                        Intent intent = new Intent(
                                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        intent.putExtra("return-data", true);
                        startActivityForResult(intent, ACTION_IMAGE_CAPTURE);
                    }
                }
            }
        });
        album.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, ACTION_IMAGE_PICK);
            }
        });

        give_up.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ACTION_IMAGE_CAPTURE:
                if (new File(getPath(imageUri)).length() == 0) {
                    return;
                }
                currentHeadPath = getPath(imageUri);
                Bitmap secretaryHead1 = BitmapFactory.decodeFile(currentHeadPath);
                userpic.setImageBitmap(secretaryHead1);
                break;
            case ACTION_IMAGE_PICK:
                if (data == null) {
                    return;
                }
                Uri uri = data.getData();
                if (uri == null) {
                    return;
                }
                currentHeadPath = getPath(uri);
                Bitmap secretaryHead = BitmapFactory.decodeFile(currentHeadPath);
                userpic.setImageBitmap(secretaryHead);
                break;
        }
        reLoadPhoto();
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void reLoadPhoto() {
        Log.e("reload", "头像在上传");
        HashMap<String, String> params = new HashMap<>();
        File file = null;
        dialogpg = new ProgressDialog(this, R.style.Translucent_NoTitle);
        dialogpg.setMessage("图片上传中");
        dialogpg.show();
        params.put(BaseConsts.APP, CatlogConsts.ReloadPhoto.params_app);
        params.put(BaseConsts.CLASS, CatlogConsts.ReloadPhoto.params_class);
        params.put(BaseConsts.SIGN, CatlogConsts.ReloadPhoto.params_sign);
        params.put("userid", TApplication.user_id);
        if (!TextUtils.isEmpty(currentHeadPath)) {
            Log.e("userid", currentHeadPath);
            file = new File(currentHeadPath);
        }
        OkHttp.asyncPost(BaseConsts.BASE_URL, params, file, new Callback() {
            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String result = response.body().string();
                    Log.e("jsons", result);
                    LoadPhotoRoot root = gson.fromJson(result, LoadPhotoRoot.class);
                    if (root.getStatus() == 0) {
                        handlerOkHandler.sendEmptyMessage(0);
                    } else {
                        handlerOkHandler.sendEmptyMessage(1);
                    }
                }
            }

            @Override
            public void onFailure(Request arg0, IOException arg1) {
                handlerOkHandler.sendEmptyMessage(2);
            }
        });
    }

    private String getPath(Uri imageUri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat && DocumentsContract.isDocumentUri(this,imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                final String docId = DocumentsContract.getDocumentId(imageUri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }

            }
            else if (isDownloadsDocument(imageUri)) {

                final String id = DocumentsContract.getDocumentId(imageUri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(imageUri)) {
                final String docId = DocumentsContract.getDocumentId(imageUri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] { split[1] };

                return getDataColumn(contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(imageUri))
                return imageUri.getLastPathSegment();

            return getDataColumn(imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }

        return null;
    }

    private boolean isDownloadsDocument(Uri imageUri) {
        return "com.android.providers.downloads.documents".equals(imageUri
                .getAuthority());
    }

    private boolean isMediaDocument(Uri imageUri) {
        return "com.android.providers.media.documents".equals(imageUri
                .getAuthority());
    }

    private boolean isGooglePhotosUri(Uri imageUri) {
        return "com.google.android.apps.photos.content".equals(imageUri.getAuthority());
    }

    private boolean isExternalStorageDocument(Uri imageUri) {
        return "com.android.externalstorage.documents".equals(imageUri.getAuthority());
    }

    private String getDataColumn(Uri uri, String selection,String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };

        try {
            cursor = getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private boolean checkCameraHardWare() {
        PackageManager packageManager =getPackageManager();
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        }
        return false;
    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("我的账号");
        action.setHomeButtonEnabled(isTrue);
    }

    @Override
    public void onResponse(Response response) {
        if(response.isSuccessful()){
            Gson gson = new Gson();
            try {
                String json = response.body().string();
                Root root = gson.fromJson(json,Root.class);
                if(root.getStatus()==0){
                    path = root.getData();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ImageLoader.getInstance().displayImage(path, userpic, new ImageLoadingListener() {
                                @Override
                                public void onLoadingStarted(String s, View view) {
                                    ((ImageView) view).setImageResource(R.mipmap.iv_hearder_default);
                                }

                                @Override
                                public void onLoadingFailed(String s, View view, FailReason failReason) {

                                }

                                @Override
                                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                                    if (path == null) {
                                        ((ImageView) view).setImageResource(R.mipmap.iv_hearder_default);
                                    } else {
                                        ((ImageView) view).setImageBitmap(bitmap);
                                    }
                                }

                                @Override
                                public void onLoadingCancelled(String s, View view) {

                                }
                            });
                        }
                    });
                }
            }catch (Exception e){

            }
        }
    }

    @Override
    public void onFailue(Request request, IOException e) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_check_out_login:
                LuPinModel checkout = new LuPinModel();
                checkout.setName("checkoutbutton");
                checkout.setType("other");
                checkout.setState("next");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                checkout.setOperationtime(sdf.format(new Date()));
                TApplication.luPinModels.add(checkout);
                TApplication.user_id="";
                TApplication.user_name="";
                TApplication.user_avatar="";
                SPCache.clear();
                finish();
                break;
            case R.id.user_info:
                startActivity(MyAccuntActivity.this, MyAccountDetailActivity.class);
                break;
            case R.id.change_psw:
                startActivity(MyAccuntActivity.this,ModifyPasswordPageActivity.class);
                break;
        }
    }
}
