package com.company.photogallery.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.company.photogallery.perference.QueryPreferences;
import com.company.photogallery.thread.PollService;

/**
 * Created by hectorleyvavillanueva on 1/18/17.
 */

public class StartupReceiver extends BroadcastReceiver {
    private static final String TAG = StartupReceiver.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Received broadcast intent : " + intent.getAction());

        boolean isOn = QueryPreferences.isAlarmOn(context);
        PollService.setServiceAlarm(context, isOn);
    }
}
