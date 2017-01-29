package com.company.photogallery.constant;

import android.app.AlarmManager;
import android.net.Uri;

/**
 * Created by hectorleyvavillanueva on 1/4/17.
 */

public class Constant {

    private Constant(){}

    public static final String BASE_URL = "https://api.flickr.com/services/rest/";
    public static final String METHOD = "method";
    public static final String FETCH_RECENTS_METHOD = "flickr.photos.getRecent";
    public static final String SEARCH_METHOD = "flickr.photos.search";
    public static final String API_KEY = "api_key";
    public static final String API_KEY_VALUE = "a17520c027f84a24c7c248a9175a5c3a";
    public static final String SECRET = "073159472c056df3";
    public static final String FORMAT = "format";
    public static final String FORMAT_VALUE = "json";
    public static final String NO_JSON_CALLBACK = "nojsoncallback";
    public static final String NO_JSON_CALLBACK_VALUE = "1";
    public static final String EXTRAS = "extras";
    public static final String EXTRAS_VALUES = "url_s";
    public static final String PREF_SEARCH_QUERY = "search_query";
    public static final String PREF_LAST_RESULT_ID = "lastResultId";
    public static final int POLL_INTERVAL = 1000 * 20; //60 seconds
    public static final String IS_ALARM_ON = "isAlarmOn";
    public static final long POLL_ITERVAL = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
    public static final String ACTION_SHOW_NOTIFICATION = "com.company.photogallery.SHOW_NOTIFICATION";
    public static final String PERM_PRIVATE = "com.company.photogallery.PRIVATE";
    public static final String REQUEST_CODE = "REQUEST_CODE";
    public static final String NOTIFICATION = "NOTIFICATION";


    public static final Uri ENDPOINT = Uri.parse(Constant.BASE_URL)
            .buildUpon()
            .appendQueryParameter(Constant.API_KEY, Constant.API_KEY_VALUE)
            .appendQueryParameter(Constant.FORMAT, Constant.FORMAT_VALUE)
            .appendQueryParameter(Constant.NO_JSON_CALLBACK, Constant.NO_JSON_CALLBACK_VALUE)
            .appendQueryParameter(Constant.EXTRAS, Constant.EXTRAS_VALUES)
            .build();



}
