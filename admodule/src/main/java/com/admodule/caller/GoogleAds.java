package com.admodule.caller;

import android.view.ViewGroup;

import com.admodule.ADCaller;
import com.admodule.BuildConfig;
import com.admodule.Utills;

public class GoogleAds {
    public static void bannerAds(ViewGroup bannerAds) {
        ADCaller.getInstance().LoardBannerAd(bannerAds, Utills.google_banner_id, "google");
    }

    public static void interstitialAds(int RandomMaxNo) {
        Utills.Maintype = 1;
        ADCaller.getInstance().PreloardGoogleAD(RandomMaxNo);
    }
}