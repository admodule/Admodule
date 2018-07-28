package com.admodule.caller;

import android.view.ViewGroup;

import com.admodule.ADCaller;
import com.admodule.BuildConfig;
import com.admodule.Utills;

public class GoogleAds {
    public static void bannerAds(ViewGroup bannerAds) {
        ADCaller.getInstance().LoardBannerAd(bannerAds, Utills.google_banner_id, "google",1);
    }

    public static void interstitialAds(int RandomMaxNo) {
        ADCaller.getInstance().googleInterstitialShow(RandomMaxNo,1);
    }
}