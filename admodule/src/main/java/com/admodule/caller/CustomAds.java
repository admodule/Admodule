package com.admodule.caller;

import android.view.ViewGroup;

import com.admodule.ADCaller;
import com.admodule.BuildConfig;
import com.admodule.Utills;

public class CustomAds {
    public static void bannerAds(ViewGroup bannerAds) {
        ADCaller.getInstance().LoardBannerAd(bannerAds, "", "");
    }

    public static void interstitialAds(int RandomMaxNo) {
        Utills.Maintype = 1;
        ADCaller.getInstance().PreloardCustomAD(RandomMaxNo);
    }

    public static void dialogAds(int adsShow){
        ADCaller.getInstance().dialogShow(adsShow);
    }
}