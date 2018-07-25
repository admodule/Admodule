package com.jksol.admodule;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.ViewGroup;

import com.jksol.admodule.BuildConfig;
import com.jksol.admodule.classes.Constants;
import com.jksol.admodule.customad.AdManager;
import com.jksol.admodule.customad.BannerAdClass;
import com.jksol.admodule.customad.FbADmanager;
import com.jksol.admodule.customad.InterstrialAdActivity;
import com.jksol.admodule.customad.NativeAdClass;
import com.jksol.admodule.interfaceclass.FacebookCallBackEvent;
import com.jksol.admodule.interfaceclass.GoogleCallBackEvent;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.Random;

/**
 * Created by pratik on 28-04-18.
 */

public class ADCaller {

    private static ADCaller singleton;
    Activity activity;
    GoogleCallBackEvent googleCallBackEvent;
    FacebookCallBackEvent facebookCallBackEvent;

    private String fb_InterstrialAdID, google_InterstrialAdID;


    public ADCaller() {
    }

    public static ADCaller getInstance() {
        if (singleton == null) {
            singleton = new ADCaller();
        }

        return singleton;
    }

    public void IntializeApiData(Activity activity) {
        try {
            Toast.makeText(activity, "Test1", Toast.LENGTH_SHORT).show();
            
            this.activity = activity;
            if (new ConnectionDetector(activity).isConnectingToInternet()) {
                InitlaizeApiData initlaizeApiDat = new InitlaizeApiData(activity);
                initlaizeApiDat.isFlikerDownload();
            }
        } catch (Exception e) {
        }
    }

    public void IntializeAdvanceNativeAd(String ADMOB_APP_ID) {
        try {
            if (ADMOB_APP_ID != null) {

                MobileAds.initialize(activity, ADMOB_APP_ID);
            }
        } catch (Exception e) {
        }
    }

    public void LoardBannerAd(ViewGroup viewGroup, String BannerId, String type) {
        try {
            if (new ConnectionDetector(activity).isConnectingToInternet()) {
                BannerAdClass bannerAdClass = new BannerAdClass(activity);
                bannerAdClass.ShowBannerAd(viewGroup, BannerId, type);
            }
        } catch (Exception e) {
        }
    }

    public void PreloardGoogleAD(final int adsNo) {
        try {
            this.google_InterstrialAdID = BuildConfig.GOOGLE_INTERSTITIAL;

            if (new ConnectionDetector(activity).isConnectingToInternet()) {
                //-----Google Ad First
                InitializeGoogleInterface();
                AdManager adManager = AdManager.getInstance();
                adManager.SetListener(googleCallBackEvent);
                adManager.createAd(activity, google_InterstrialAdID);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ShowAd(adsNo);
                    }
                }, 4000);
            }
        } catch (Exception e) {
        }
    }

    public void PreloardFacebookAD(final int adsNo) {
        try {
            this.fb_InterstrialAdID = BuildConfig.FACEBOOK_INTERSTITIAL;
            if (new ConnectionDetector(activity).isConnectingToInternet()) {
                //----------------Fb Ad First
                IntilaizeFacebookInterface();
                FbADmanager fbADmanager = FbADmanager.getInstance();
                fbADmanager.SetListener(facebookCallBackEvent);
                fbADmanager.createAd(activity, fb_InterstrialAdID);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Utills.Subtype = 2;
                        ShowAd(adsNo);
                    }
                }, 4000);
            }
        } catch (Exception e) {
        }
    }

    public void ShowAd(int adsShow) {
        Random random = new Random();
        int no = random.nextInt(adsShow);
        if (no == 0) {
            no = 1;
        }
        if (no == 1) {
            if (Utills.Subtype == 1) {
                try {
                    AdManager adManager = AdManager.getInstance();
                    InterstitialAd ad = adManager.getAd();
                    if (ad != null) {
                        if (ad.isLoaded()) {
                            ad.show();
                        }
                    } else {
                        if (googleCallBackEvent == null) {
                            InitializeGoogleInterface();
                            adManager.SetListener(googleCallBackEvent);
                        }
                        adManager.createAd(activity, google_InterstrialAdID);
                    }
                } catch (Exception e) {
                }
            } else if (Utills.Subtype == 2) {
                try {
                    FbADmanager adManager = FbADmanager.getInstance();
                    com.facebook.ads.InterstitialAd ad = adManager.getAd();
                    if (ad != null) {
                        if (ad.isAdLoaded()) {
                            ad.show();
                        }
                    } else {
                        if (facebookCallBackEvent == null) {
                            IntilaizeFacebookInterface();
                            adManager.SetListener(facebookCallBackEvent);
                        }
                        adManager.createAd(activity, fb_InterstrialAdID);
                    }
                } catch (Exception e) {
                }
            } else if (Utills.Subtype == 3) {
                customInterstrial();
            }
        }
    }

    public void InitializeGoogleInterface() {
        googleCallBackEvent = new GoogleCallBackEvent() {
            @Override
            public void AdLoardSuccess() {
            }

            @Override
            public void AdLoardFailed() {
                if (Utills.Maintype == 1) {
                    if (fb_InterstrialAdID != null) {
                        Utills.Subtype = 2;
                        IntilaizeFacebookInterface();
                        FbADmanager fbADmanager = FbADmanager.getInstance();
                        fbADmanager.SetListener(facebookCallBackEvent);
                        fbADmanager.createAd(activity, fb_InterstrialAdID);
                    } else {
                        customInterstrial();
                    }
                } else if (Utills.Maintype == 2) {
                    Utills.Subtype = 3;
                    customInterstrial();
                }

            }
        };
    }

    public void IntilaizeFacebookInterface() {

        facebookCallBackEvent = new FacebookCallBackEvent() {
            @Override
            public void AdLoardSuccess() {
            }

            @Override
            public void AdLoardFailed() {
                if (Utills.Maintype == 1) {
                    Utills.Subtype = 3;
                    customInterstrial();
                } else if (Utills.Maintype == 2) {
                    if (google_InterstrialAdID != null) {
                        Utills.Subtype = 1;
                        InitializeGoogleInterface();
                        AdManager adManager = AdManager.getInstance();
                        adManager.SetListener(googleCallBackEvent);
                        adManager.createAd(activity, google_InterstrialAdID);
                    } else {
                        customInterstrial();
                    }
                }
            }
        };
    }

    public void customInterstrial() {
        try {
            if (new ConnectionDetector(activity).isConnectingToInternet() && Constants.adDataProviders.size() > 0) {
                Intent intent = new Intent(activity, InterstrialAdActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
            }
        } catch (Exception e) {
            Utills.Subtype = 1;//---loard google if failed custom ad
        }
    }

    public void LoardNativeAd(ViewGroup view, String Native_ADUnit_Id) {

        try {
            if (new ConnectionDetector(activity).isConnectingToInternet()) {
                NativeAdClass nativeAdClass = new NativeAdClass(activity);
                nativeAdClass.refreshAd(view, Native_ADUnit_Id);
            }
        } catch (Exception e) {
        }
    }
}
