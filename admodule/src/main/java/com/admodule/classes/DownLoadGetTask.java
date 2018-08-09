package com.admodule.classes;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownLoadGetTask extends AsyncTask<String, String, String> {
   private Handler handler;
   private String downloadLocation;
   private Dialog dialog;
   private Activity activity;
   String appname;
   
   public DownLoadGetTask(Activity activity, Handler handler, String appname, String downloadLocation) {
      this.activity = activity;
      this.handler = handler;
      this.appname=appname;
      this.downloadLocation = downloadLocation;
      Log.e("=>>>>",""+downloadLocation+" "+appname);
   }


   @Override
   protected String doInBackground(String... params) {
      int count;
      String result = "error";
      try {
         URL url = new URL(params[0]);//Create Download URl
         HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();//Open Url Connection
         httpURLConnection.setRequestMethod("GET");//Set Request Method to "GET" since we are grtting data
         httpURLConnection.setDoOutput(true);
         httpURLConnection.connect();//connect the URL Connection
         
         int lenghtOfFile = httpURLConnection.getContentLength();
         
         InputStream input = new BufferedInputStream(url.openStream());
         File file = new File(downloadLocation+appname+".jpg");
         
         if (file.exists()) {
            file.delete();
         }
         FileOutputStream output = new FileOutputStream(file);
         
         byte data[] = new byte[1024];
         long total = 0;
         while ((count = input.read(data)) != -1) {
            total += count;
            publishProgress("" + (int) ((total * 100) / lenghtOfFile));
            output.write(data, 0, count);
         }
         
         output.flush();
         output.close();
         input.close();
         result = "done";
      } catch (Exception e) {
         e.printStackTrace();
      }
      
      return result;
   }
   
   
   /**
    * After completing background task Dismiss the progress dialog
    **/
   @Override
   protected void onPostExecute(String result) {

      if (handler != null) {
         Message msg = new Message();
         msg.what = 100;
         msg.obj = result;
         handler.sendMessage(msg);
      }
   }
}