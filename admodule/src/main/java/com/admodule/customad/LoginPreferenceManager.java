package com.admodule.customad;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by Umiya Mataji on 5/24/2016.
 */
public class LoginPreferenceManager
{
    public Context context;

    public LoginPreferenceManager(Context context) {
        this.context = context;
    }


    public static void SaveStringData(Context context, String Key, String data)
    {
        SharedPreferences preferences = context.getSharedPreferences("prefrencemanage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Key, data);
        editor.commit();

    }

    public static String GetStringData(Context context, String Key)
    {
        SharedPreferences preferences = context.getSharedPreferences("prefrencemanage", Context.MODE_PRIVATE);
        return preferences.getString(Key, null);
    }

    public static void SavebooleanData(Context context, String Key, boolean flag)
    {
        SharedPreferences preferences = context.getSharedPreferences("prefrencemanage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Key, flag);
        editor.commit();

    }

    public static boolean GetbooleanData(Context context, String Key)
    {
        SharedPreferences preferences = context.getSharedPreferences("prefrencemanage", Context.MODE_PRIVATE);
        return preferences.getBoolean(Key, false);
    }

    public static boolean GetbooleanAppNotify(Context context, String Key)
    {
        SharedPreferences preferences = context.getSharedPreferences("prefrencemanage", Context.MODE_PRIVATE);
        return preferences.getBoolean(Key, true);
    }



    public static void SaveIntgData(Context context, String Key, int data)
    {
        SharedPreferences preferences = context.getSharedPreferences("prefrencemanage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Key, data);
        editor.commit();

    }

    public static int GetIntData(Context context, String Key)
    {
        SharedPreferences preferences = context.getSharedPreferences("prefrencemanage", Context.MODE_PRIVATE);
        return preferences.getInt(Key, -1);
    }


}
