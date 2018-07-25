package com.jksol.admodule.caller;

import android.view.ViewGroup;

import com.jksol.admodule.ADCaller;
import com.jksol.admodule.BuildConfig;
import com.jksol.admodule.Utills;

public class FacebookAds {
    public static void bannerAds(ViewGroup bannerAds) {
        ADCaller.getInstance().LoardBannerAd(bannerAds, Utills.facebook_banner_id, "facebook");
    }

    public static void interstitialAds(int RandomMaxNo) {
        Utills.Maintype = 2;
        ADCaller.getInstance().PreloardFacebookAD(RandomMaxNo);
    }
}
