package com.admodule;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import com.admodule.classes.Constants;
import com.admodule.classes.DataProvider;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static android.content.Context.MODE_PRIVATE;



public class InitlaizeApiData {

    private Activity activity;
    private String Api = "https://api.flickr.com/services/rest/?method=flickr.photosets.getPhotos&api_key=892bdb8d0e6519769124a90c84643290&photoset_id=72157671119679828&extras=description%2C+url_m%2C+url_o%2C+url_l&per_page=20&page=1&format=json&nojsoncallback=1";


    InitlaizeApiData(Activity activity) {
        this.activity = activity;
    }

    public void isFlikerDownload() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constants.APP_NAME, MODE_PRIVATE);

        if (sharedPreferences.getString(Constants.ADDOWNLOAD, null) == null) {
            if (new ConnectionDetector(activity).isConnectingToInternet()) {
                LoardData();
            }
        } else {
            String response = sharedPreferences.getString(Constants.ADDOWNLOAD, "");
            flikerData(response);
            LoardData();
        }
    }

    private void LoardData() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(60 * 1000);
        client.get(Api, new GenerateResponse());
    }

    public class GenerateResponse extends AsyncHttpResponseHandler {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            String response = new String(responseBody);

            SharedPreferences sharedPreferences = activity.getSharedPreferences(Constants.APP_NAME, MODE_PRIVATE);
            sharedPreferences.edit().putString(Constants.ADDOWNLOAD, response).apply();

            flikerData(response);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        }
    }

    private void flikerData(String response) {
        if (!response.equalsIgnoreCase("")) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject photoset = jsonObject.getJSONObject("photoset");
                JSONArray jsonArray = photoset.getJSONArray("photo");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    DataProvider dp = new DataProvider();

                    String s = jsonObject1.getString("title");
                    String[] split = s.split("_");
                    String index = split[0];
                    String packageName = split[1];
                    String appname = split[2];

                    JSONObject jsonObject2 = jsonObject1.getJSONObject("description");
                    String description = jsonObject2.getString("_content");

                    String imageUrl = jsonObject1.getString("url_o");

                    dp.setPackagename(packageName);
                    dp.setAppname(appname);
                    dp.setImageurl(imageUrl);
                    dp.setDescription(description);

                    boolean isinstall = isAppInstalled(split[1], activity);
                    if (!isinstall && !Constants.packages.contains(split[1])) {
                        if (index.equals("i")) {
                            downloadImage(dp.getImageurl(), "I_" + dp.getAppname(), activity);
                            Constants.interstitialList.add(dp);
                        } else if (index.equals("b")) {
                            downloadImage(dp.getImageurl(), "B_" + dp.getAppname(), activity);
                            Constants.bannerList.add(dp);
                        } else if (index.equals("c")) {
                            downloadImage(dp.getImageurl(), "D_" + dp.getAppname(), activity);
                            Constants.dialogList.add(dp);
                        }
                    }
                }
            } catch (JSONException ignored) {

            }
        }
    }

    private static boolean isAppInstalled(String packageName, Context context) {
        Intent mIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        return mIntent != null;
    }

    private void downloadImage(String url, String appname, Activity activity) {
        File direct = new File(Constants.PARENT_DIR + Constants.AD_DIR);

        if (!direct.exists()) {
            direct.mkdirs();
        }
        if (!new File(direct + "/" + appname + ".jpg").exists()) {
            DownloadManager mgr = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);

            Uri downloadUri = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(downloadUri);

            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false).setTitle(appname + ".jpg")
                    .setDescription("Downloading " + appname + ".jpg")
                    .setDestinationInExternalPublicDir(Constants.AD_DIR, appname + ".jpg");

            if (mgr != null) {
                mgr.enqueue(request);
            }
        }
    }
}
