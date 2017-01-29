package com.company.photogallery.network;

import android.net.Uri;
import android.util.Log;

import com.company.photogallery.constant.Constant;
import com.company.photogallery.model.GalleryItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hectorleyvavillanueva on 1/4/17.
 */

public class FlickerFetchr {

    private static final String TAG = FlickerFetchr.class.getSimpleName();


    public byte[] getUrlBytes(String url){
        URL urlObj;
        ByteArrayOutputStream out = null;
        HttpURLConnection connection = null;
        try{
            urlObj = new URL(url);
            connection = (HttpURLConnection)urlObj.openConnection();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " + url);
            }
            out = new ByteArrayOutputStream();
            int bytesRead;
            byte[] buffer = new byte[1024];
            while((bytesRead = in.read(buffer)) > 0){
                out.write(buffer, 0, bytesRead);
            }
            out.close();

        }catch (MalformedURLException e){
            Log.e(TAG, "error in the url " + e.getMessage());
        }catch(IOException e) {
            Log.e(TAG, "error in the connection " + e.getMessage());
        }finally {
            if(connection != null)
                connection.disconnect();
        }
        return out.toByteArray();
    }


    public String getUrlString(String urlSpec){
        return new String(getUrlBytes(urlSpec));
    }


    public List<GalleryItem> downloadGalleryItems(String url) {

        List<GalleryItem> list = new ArrayList<>();

        String jsonString = getUrlString(url);
        Log.i(TAG, "Received JSON: " + jsonString);

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            parseItems(list,jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;

    }

    public void parseItems(List<GalleryItem> itemList, JSONObject jsonBody) throws
        IOException, JSONException{

        JSONObject photosJsonObject = jsonBody.getJSONObject("photos");
        JSONArray photoJsonArray = photosJsonObject.getJSONArray("photo");

        JSONObject photoJsonObject;
        GalleryItem item;
        for(int i = 0; i < photoJsonArray.length(); i++){
            photoJsonObject = photoJsonArray.getJSONObject(i);
            item = new GalleryItem();
            item.setmId(photoJsonObject.getString("id"));
            item.setmCaption(photoJsonObject.getString("title"));

            if(!photoJsonObject.has("url_s")){
                continue;
            }

            item.setmUrl(photoJsonObject.getString("url_s"));
            itemList.add(item);

        }

    }

    private String buildUrl(String method, String query) {
        Uri.Builder uBuilder = Constant.ENDPOINT.buildUpon()
                .appendQueryParameter(Constant.METHOD, method);
        if (method.equalsIgnoreCase(Constant.SEARCH_METHOD)) {
            uBuilder.appendQueryParameter("text", query);
        }
        return uBuilder.build().toString();
    }

    public List<GalleryItem> fetchRecentPhotos(){
        String url = buildUrl(Constant.FETCH_RECENTS_METHOD, null);
        return downloadGalleryItems(url);
    }

    public List<GalleryItem> searchPhotos(String query) {
        String url = buildUrl(Constant.SEARCH_METHOD, query);
        return downloadGalleryItems(url);
    }


}