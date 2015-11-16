package com.mb.mmdepartment.tools;

import android.location.Location;
import android.os.Bundle;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;

/**
 * 定位监听器
 */
public class AmapLocationUtils implements AMapLocationListener {
    private OnLocalListener error;
    public AmapLocationUtils(OnLocalListener error){
        this.error=error;
    }
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation!=null){
            error.onSuccess(aMapLocation);
        }else {
            error.setError("请检查网络链接是否正确.");
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
