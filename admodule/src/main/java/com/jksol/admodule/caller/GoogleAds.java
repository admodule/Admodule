package com.jksol.admodule.caller;

import android.view.ViewGroup;

import com.jksol.admodule.ADCaller;
import com.jksol.admodule.BuildConfig;
import com.jksol.admodule.Utills;

public class GoogleAds {
    public static void bannerAds(ViewGroup bannerAds) {
        ADCaller.getInstance().LoardBannerAd(bannerAds, Utills.google_banner_id, "google");
    }

    public static void interstitialAds(int RandomMaxNo) {
        Utills.Maintype = 1;
        ADCaller.getInstance().PreloardGoogleAD(RandomMaxNo);
    }
}