package com.admodule.caller;

import android.view.ViewGroup;

import com.admodule.ADCaller;
import com.admodule.BuildConfig;
import com.admodule.Utills;

public class FacebookAds {
    public static void bannerAds(ViewGroup bannerAds) {
        ADCaller.getInstance().LoardBannerAd(bannerAds, Utills.facebook_banner_id, "facebook",2);
    }

    public static void interstitialAds(int RandomMaxNo) {
        ADCaller.getInstance().facebookInterstitialShow(RandomMaxNo,2);
    }
}
