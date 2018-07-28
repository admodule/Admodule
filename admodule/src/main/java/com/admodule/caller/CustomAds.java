package com.admodule.caller;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;

import com.admodule.ADCaller;

public class CustomAds {
    public static void bannerAds(ViewGroup bannerAds) {
        ADCaller.getInstance().LoardBannerAd(bannerAds, "", "", 3);
    }

    public static void interstitialAds(int RandomMaxNo) {
        ADCaller.getInstance().PreloardCustomAD(RandomMaxNo);
    }

    public static void dialogAds(Activity activity, int adsShow) {
        ADCaller.getInstance().dialogShow(activity, adsShow);
    }
}
