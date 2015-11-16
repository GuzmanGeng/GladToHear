package com.mb.mmdepartment.account.activity.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.mb.mmdepartment.account.activity.util.UdinicAuthenticator;

/**
 * Created with IntelliJ IDEA.
 * User: Udini
 * Date: 19/03/13
 * Time: 19:10
 */
public class UdinicAuthenticatorService extends Service {
    @Override
    public IBinder onBind(Intent intent) {

        UdinicAuthenticator authenticator = new UdinicAuthenticator(this);
        return authenticator.getIBinder();
    }
}
