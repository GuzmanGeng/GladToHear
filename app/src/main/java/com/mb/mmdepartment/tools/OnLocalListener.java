package com.mb.mmdepartment.tools;

import com.amap.api.location.AMapLocation;

/**
 * 定位服务类
 */
public interface OnLocalListener {
    /**
     * 定位失败的时候调用
     * @param error
     */
    public void setError(String error);

    /**
     * 定位成功的时候调用
     * @param aMapLocation
     */
    public void onSuccess(AMapLocation aMapLocation);
}
