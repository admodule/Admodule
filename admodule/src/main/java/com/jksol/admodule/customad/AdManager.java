package com.jksol.admodule.customad;

import android.content.Context;

import com.jksol.admodule.interfaceclass.GoogleCallBackEvent;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


/**
* Created by Kirk on 10/26/2017.
*/

public class AdManager {

    private static AdManager singleton;
    public InterstitialAd interstitialAd;
    GoogleCallBackEvent googleCallBackEvent;

    public AdManager() {}

    /***
     * returns an instance of this class. if singleton is null create an instance
     * else return  the current instance
     * @return
     */
    public static AdManager getInstance() {
        if (singleton == null) {
            singleton = new AdManager();
        }

        return singleton;
    }

    public void SetListener( GoogleCallBackEvent googleCallBackEvent){
        this.googleCallBackEvent=googleCallBackEvent;
    }
    /***
     * Create an interstitial ad
     * @param context
     */
    public void createAd(final Context context,String Intertial_ad) {
        interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(Intertial_ad);
        interstitialAd.loadAd(new AdRequest.Builder().build());
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
              //  interstitialAd.loadAd(new AdRequest.Builder().build());
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                googleCallBackEvent.AdLoardSuccess();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                googleCallBackEvent.AdLoardFailed();
            }
        });


    }

    /***
     * get an interstitial Ad
     * @return
     */
    public InterstitialAd getAd() {
        return interstitialAd;
    }
}