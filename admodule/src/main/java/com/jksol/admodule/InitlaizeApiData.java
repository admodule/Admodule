package com.jksol.admodule;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import com.jksol.admodule.classes.Constants;
import com.jksol.admodule.classes.DataProvider;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by pratik on 26-04-18.
 */

public class InitlaizeApiData {

    Activity activity;
    String Api = "https://api.flickr.com/services/rest/?method=flickr.photosets.getPhotos&api_key=f09df5e58b151763fb42e2ad977d9d63&user_id=144318798@N02&format=json&photoset_id=72157671119679828&nojsoncallback=1";

    public InitlaizeApiData(Activity activity) {
        this.activity = activity;
    }

    public void isFlikerDownload() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constants.APP_NAME, MODE_PRIVATE);

        if (sharedPreferences.getString(Constants.ADDOWNLOAD, null) == null) {
            if (new ConnectionDetector(activity).isConnectingToInternet()) {
                LoardData();
            }
        } else {
            try {
                ArrayList<DataProvider> iconList = new ArrayList<>();
                ArrayList<DataProvider> descList = new ArrayList<>();
                String response = sharedPreferences.getString(Constants.ADDOWNLOAD, "");
                if (!response.equalsIgnoreCase("")) {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject photoset = jsonObject.getJSONObject("photoset");
                    JSONArray jsonArray = photoset.getJSONArray("photo");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        DataProvider dp = new DataProvider();

                        String s = jsonObject1.getString("title");
                        String[] split = s.split("_");
                        String index = split[0];
                        String value = split[1];

                        if (index.equals("i")) {
                            String packageName = split[2];
                            String appname = split[3];

                            boolean isinstall = isAppInstalled(packageName, activity);
                            if (!isinstall) {
                                dp.setFor1(value);
                                dp.setfarm(jsonObject1.getString("farm"));
                                dp.setid(jsonObject1.getString("id"));
                                dp.setserverid(jsonObject1.getString("server"));
                                dp.setsecret(jsonObject1.getString("secret"));
                                dp.setpackagename(packageName);
                                dp.setappname(appname);
                                dp.seturl("https://farm" + dp.getfarm() + ".staticflickr.com/" + dp.getserverid() + "/" + dp.getid() + "_" + dp.getsecret() + "_b" + ".jpg");
                                downloadImage(dp.geturl(), dp.getappname(), activity);
                                iconList.add(dp);
                            }
                        } else {
                            dp.setBannerurl("https://farm" + jsonObject1.getString("farm") + ".staticflickr.com/" + jsonObject1.getString("server") + "/" + jsonObject1.getString("id") + "_" + jsonObject1.getString("secret") + "_b" + ".jpg");
                            dp.setFilename(jsonObject1.getString("server"));
                            dp.setFor1(value);
                            dp.setDescrip(split[2]);
                            downloadImage(dp.getBannerurl(), jsonObject1.getString("server"), activity);
                            descList.add(dp);
                        }

                    }

                    for (int indexI = 0; indexI < iconList.size(); indexI++) {
                        DataProvider dataProvider = iconList.get(indexI);
                        for (int indexD = 0; indexD < descList.size(); indexD++) {
                            if (dataProvider.getFor1().equals(descList.get(indexD).getFor1())) {
                                dataProvider.setDescrip(descList.get(indexD).getDescrip());
                                dataProvider.setFilename(descList.get(indexD).getFilename());
                                Constants.adDataProviders.add(dataProvider);
                                break;
                            }
                        }
                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }
        }
    }

    public void LoardData() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(60 * 1000);
        client.get(Api, new GenerateResponse());
    }

    public class GenerateResponse extends AsyncHttpResponseHandler {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            String result = new String(responseBody);

            ArrayList<DataProvider> iconList = new ArrayList<>();
            ArrayList<DataProvider> descList = new ArrayList<>();

            try {
                SharedPreferences sharedPreferences = activity.getSharedPreferences(Constants.APP_NAME, MODE_PRIVATE);
                sharedPreferences.edit().putString(Constants.ADDOWNLOAD, result).apply();
                JSONObject jsonObject = new JSONObject(result);
                JSONObject photoset = jsonObject.getJSONObject("photoset");
                JSONArray jsonArray = photoset.getJSONArray("photo");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    DataProvider dp = new DataProvider();

                    String s = jsonObject1.getString("title");
                    String[] split = s.split("_");
                    String index = split[0];
                    String value = split[1];

                    if (index.equals("i")) {
                        String packageName = split[2];
                        String appname = split[3];

                        boolean isinstall = isAppInstalled(packageName, activity);
                        if (!isinstall) {
                            dp.setFor1(value);
                            dp.setfarm(jsonObject1.getString("farm"));
                            dp.setid(jsonObject1.getString("id"));
                            dp.setserverid(jsonObject1.getString("server"));
                            dp.setsecret(jsonObject1.getString("secret"));
                            dp.setpackagename(packageName);
                            dp.setappname(appname);
                            dp.seturl("https://farm" + dp.getfarm() + ".staticflickr.com/" + dp.getserverid() + "/" + dp.getid() + "_" + dp.getsecret() + "_b" + ".jpg");
                            downloadImage(dp.geturl(), dp.getappname(), activity);
                            iconList.add(dp);
                        }
                    } else {
                        dp.setBannerurl("https://farm" + jsonObject1.getString("farm") + ".staticflickr.com/" + jsonObject1.getString("server") + "/" + jsonObject1.getString("id") + "_" + jsonObject1.getString("secret") + "_b" + ".jpg");
                        dp.setFilename(jsonObject1.getString("server"));
                        dp.setFor1(value);
                        dp.setDescrip(split[2]);
                        downloadImage(dp.getBannerurl(), jsonObject1.getString("server"), activity);
                        descList.add(dp);
                    }

                }

                for (int indexI = 0; indexI < iconList.size(); indexI++) {
                    DataProvider dataProvider = iconList.get(indexI);
                    for (int indexD = 0; indexD < descList.size(); indexD++) {
                        if (dataProvider.getFor1().equals(descList.get(indexD).getFor1())) {
                            dataProvider.setDescrip(descList.get(indexD).getDescrip());
                            dataProvider.setFilename(descList.get(indexD).getFilename());
                            Constants.adDataProviders.add(dataProvider);
                            break;
                        }
                    }
                }

            } catch (JSONException e) {
                //e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        }
    }

    public static boolean isAppInstalled(String packageName, Context context) {
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
