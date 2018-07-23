package com.jksol.admodule.customad;

import android.content.Context;
import android.util.Log;

import com.jksol.admodule.interfaceclass.FacebookCallBackEvent;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAdListener;


/**
 * Created by pratik on 03-05-18.
 */

public class FbADmanager {

    private static FbADmanager singleton;
    com.facebook.ads.InterstitialAd interstitialAd = null;
    FacebookCallBackEvent facebookCallBackEvent;

    public FbADmanager() {
    }

    /***
     * returns an instance of this class. if singleton is null create an instance
     * else return  the current instance
     * @return
     */
    public static FbADmanager getInstance() {
        if (singleton == null) {
            singleton = new FbADmanager();
        }

        return singleton;
    }

    public void SetListener(FacebookCallBackEvent facebookCallBackEvent) {
        this.facebookCallBackEvent = facebookCallBackEvent;
    }

    public void createAd(final Context context, String FabId) {

        try {

            interstitialAd = new com.facebook.ads.InterstitialAd(context, FabId);
            // Set listeners for the Interstitial Ad
//            AdSettings.addTestDevice("2a0f42de-bc31-44dd-8c5e-27460307cceb");
            interstitialAd.setAdListener(new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {
                    // Interstitial displayed callback
                }

                @Override
                public void onInterstitialDismissed(Ad ad) {
                    // Interstitial dismissed callback
                    //  interstitialAd.loadAd();
                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    // Ad error callback
                    Log.e("AdErrorFb", adError.getErrorMessage());
                    facebookCallBackEvent.AdLoardFailed();
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    // Show the ad when it's done loading.
                    facebookCallBackEvent.AdLoardSuccess();
                }

                @Override
                public void onAdClicked(Ad ad) {
                    // Ad clicked callback
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    // Ad impression logged callback
                }
            });

            // For auto play video ads, it's recommended to load the ad
            // at least 30 seconds before it is shown
            interstitialAd.loadAd();
        } catch (Exception e) {
        }
    }

    public com.facebook.ads.InterstitialAd getAd() {
        return interstitialAd;
    }

}
