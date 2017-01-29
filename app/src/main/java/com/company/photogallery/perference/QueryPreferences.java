package com.company.photogallery.perference;

import android.content.Context;
import android.preference.PreferenceManager;

import com.company.photogallery.constant.Constant;

/**
 * Created by hectorleyvavillanueva on 1/6/17.
 */

public class QueryPreferences {

    public static String getStoredQuery(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(Constant.PREF_SEARCH_QUERY, null);
    }

    public static void setStoreQuery(Context context, String query) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(Constant.PREF_SEARCH_QUERY, query)
                .apply();
    }

    public static String getLastResultId(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(Constant.PREF_LAST_RESULT_ID, null);
    }

    public static void setLastResultId(Context context, String lastResultId) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(Constant.PREF_LAST_RESULT_ID, lastResultId)
                .apply();
    }

    public static boolean isAlarmOn(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(Constant.IS_ALARM_ON, false);
    }

    public static void setAlarmOn(Context context, boolean isOne) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(Constant.IS_ALARM_ON,isOne)
                .apply();
    }

}
