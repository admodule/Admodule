package com.jksol.admodule.caller;

import android.view.ViewGroup;

import com.jksol.admodule.ADCaller;
import com.jksol.admodule.BuildConfig;
import com.jksol.admodule.Utills;

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
