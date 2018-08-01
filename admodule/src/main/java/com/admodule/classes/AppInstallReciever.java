package com.admodule.classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.util.Log;

import com.admodule.R;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class AppInstallReciever extends BroadcastReceiver {

    public String Api = "https://api.mlab.com/api/1/databases/adlib/collections/device_info?apiKey=bRr4KD3gns45gh4PA4pS2hpcgO-adLMU";

    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            String action = intent.getAction();
            String packageName = intent.getData().getEncodedSchemeSpecificPart();

            if (!packageName.equalsIgnoreCase(context.getPackageName())) {


                if (action.equalsIgnoreCase("android.intent.action.PACKAGE_ADDED")) {

                    if(Constants.Install_From!=null) {

                        DataProvider dataProvider=new DataProvider();
                        dataProvider.setPackagename(packageName);

                        if(Constants.interstitialList.contains(dataProvider)) {
                            InstallApp(context, packageName);
                        }
                    }

                }
            }
        } catch (Exception e) {
            Log.e("=>Error",""+e.getMessage());
        }
    }

    public void InstallApp(Context context, String install_app_pkg) {

        try {

            ApplicationInfo app = context.getPackageManager().getApplicationInfo(install_app_pkg, 0);
            String install_appname = (String) context.getPackageManager().getApplicationLabel(app);
            String android_id = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
            String deviceName = android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL;
            String Os = Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName();
            String Version = Build.VERSION.RELEASE;

            Log.e("=>>>>", install_appname + "===" + install_app_pkg + "=" + android_id + "===" + context.getPackageName() + "===" + context.getString(R.string.app_name));
            Log.e("=>>>>", deviceName + "===" + Os + "===" + Version);
            //-------------------------------------------

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("device_id", android_id);
                jsonObject.put("device_name", deviceName);
                jsonObject.put("operating_system", Os);
                jsonObject.put("operating_version", Version);
                jsonObject.put("main_package_name", context.getPackageName());
                jsonObject.put("main_app_name", context.getString(R.string.app_name));
                jsonObject.put("install_packahe_name", install_app_pkg);
                jsonObject.put("install_app_name", install_appname);
                jsonObject.put("created_date", new Date());
                jsonObject.put("install_from",Constants.Install_From);
                SendPostJSONRequest sendPostJSONRequest = (SendPostJSONRequest) new SendPostJSONRequest(jsonObject).execute(Api);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            //----------------------------------------------


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


    public class UpdateDataHandler extends AsyncHttpResponseHandler {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            String response = new String(responseBody);

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        }
    }
}
