package com.admodule.caller;

import android.view.ViewGroup;

import com.admodule.ADCaller;
import com.admodule.classes.Constants;

public class GoogleAds {
    public static void bannerAds(ViewGroup bannerAds) {
        ADCaller.getInstance().LoardBannerAd(bannerAds, Constants.google_banner_id, "google",1);
    }

    public static void interstitialAds(int RandomMaxNo) {
        ADCaller.getInstance().googleInterstitialShow(RandomMaxNo,1);
    }
}