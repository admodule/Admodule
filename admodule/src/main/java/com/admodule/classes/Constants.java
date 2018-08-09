package com.admodule.classes;


import android.os.Environment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constants {
   
   public static final String APP_NAME = "adMob";
   public static final String PARENT_DIR = Environment.getExternalStorageDirectory().toString();
   public static final String AD_DIR = Environment.getExternalStorageDirectory().toString() + "/.adMob/";
   public static String ADDOWNLOAD = "ad_download";
   public static String Install_From=null; //
   public static String ISDownLoadComplete="isdownloadcomplete";

   public static ArrayList<DataProvider> interstitialList = new ArrayList<>();
   public static ArrayList<DataProvider> bannerList = new ArrayList<>();
   public static ArrayList<DataProvider> dialogList = new ArrayList<>();

   public static List<String> packages = Arrays.asList("");

   public static String google_banner_id;
   public static String google_interstitial_id;
   public static String facebook_banner_id;
   public static String facebook_interstitial_id;

}
