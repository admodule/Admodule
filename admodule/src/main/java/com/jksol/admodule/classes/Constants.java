package com.jksol.admodule.classes;


import android.os.Environment;

import java.util.ArrayList;

public class Constants {
   
   public static final String APP_NAME = "adMob";
   public static final String PARENT_DIR = Environment.getExternalStorageDirectory().toString();
   public static final String AD_DIR = Environment.getExternalStorageDirectory().toString() + "/.adMob/";
   public static String ADDOWNLOAD = "ad_download";
   public static ArrayList<DataProvider> interstitialList = new ArrayList<>();
   public static ArrayList<DataProvider> bannerList = new ArrayList<>();
   public static ArrayList<DataProvider> dialogList = new ArrayList<>();

}
