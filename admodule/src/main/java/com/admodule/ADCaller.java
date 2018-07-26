package com.admodule;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.admodule.classes.Constants;
import com.admodule.classes.DataProvider;
import com.admodule.customad.AdManager;
import com.admodule.customad.BannerAdClass;
import com.admodule.customad.FbADmanager;
import com.admodule.customad.InterstrialAdActivity;
import com.admodule.customad.NativeAdClass;
import com.admodule.interfaceclass.FacebookCallBackEvent;
import com.admodule.interfaceclass.GoogleCallBackEvent;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
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

    public void init(Activity activity) {
        try {
            this.activity = activity;

            try {
                ApplicationInfo ai = activity.getPackageManager().getApplicationInfo(activity.getPackageName(), PackageManager.GET_META_DATA);
                Bundle bundle = ai.metaData;

                Utills.google_banner_id = bundle.getString("google_banner_id");
                Utills.google_interstitial_id = bundle.getString("google_interstitial_id");
                Utills.facebook_banner_id = bundle.getString("facebook_banner_id");
                Utills.facebook_interstitial_id = bundle.getString("facebook_interstitial_id");

            } catch (Throwable t) {
                t.printStackTrace();
            }

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
            this.google_InterstrialAdID = Utills.google_interstitial_id;

            if (new ConnectionDetector(activity).isConnectingToInternet()) {
                //-----Google Ad First
                InitializeGoogleInterface();
                AdManager adManager = AdManager.getInstance();
                adManager.SetListener(googleCallBackEvent);
                adManager.createAd(activity, google_InterstrialAdID);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Utills.Subtype = 1;
                        ShowAd(adsNo);
                    }
                }, 2000);
            }
        } catch (Exception e) {
        }
    }

    public void PreloardCustomAD(final int adsNo) {
        try {
            if (new ConnectionDetector(activity).isConnectingToInternet()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Utills.Subtype = 3;
                        ShowAd(adsNo);
                    }
                }, 1000);
            }
        } catch (Exception e) {
        }
    }


    public void PreloardFacebookAD(final int adsNo) {
        try {
            this.fb_InterstrialAdID = Utills.facebook_interstitial_id;
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
                }, 2000);
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
                        Utills.Subtype = 3;
                        customInterstrial();
                    }
                }
            }
        };
    }

    public void customInterstrial() {
        try {
            if (new ConnectionDetector(activity).isConnectingToInternet()) {
                if (Constants.interstitialList.size() > 0) {
                    Intent intent = new Intent(activity, InterstrialAdActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(intent);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            customInterstrial();
                        }
                    }, 3000);
                }
            }
        } catch (Exception e) {
            customInterstrial();//---loard google if failed custom ad
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

    public void dialogShow(int adsShow) {
        Random random = new Random();
        int no = random.nextInt(adsShow);
        if (no == 0) {
            no = 1;
        }
        if (no == 1) {
            int size = Constants.dialogList.size();
            if (size > 0) {
                int limit = Constants.dialogList.size() - 1;
                if (limit != 0) {
                    limit = random.nextInt(limit);
                }
                final DataProvider dataProvider = Constants.dialogList.get(limit);
                File jpgFile = new File(Constants.PARENT_DIR + Constants.AD_DIR + "D_" + dataProvider.getAppname() + ".jpg");

                final Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                View view = activity.getLayoutInflater().inflate(R.layout.dialog_ads, null);
                dialog.setCancelable(false);
                dialog.setContentView(view);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

                Typeface titleFont = Typeface.createFromAsset(activity.getAssets(), "textfont/" + activity.getString(R.string.font));

                TextView appcancelbtn = dialog.findViewById(R.id.appcancelbtn);
                ImageView appicon = dialog.findViewById(R.id.appicon);
                TextView appname = dialog.findViewById(R.id.appname);
                TextView appdescirp = dialog.findViewById(R.id.appdescirp);
                TextView appinstallbtn = dialog.findViewById(R.id.appinstallbtn);

                if (jpgFile.exists()) {
                    Glide.with(activity)
                            .load(jpgFile)
                            .into(appicon);

                    appname.setText(dataProvider.getAppname());
                    appdescirp.setText(dataProvider.getDescription());

                    appname.setTypeface(titleFont);
                    appdescirp.setTypeface(titleFont);

                    appinstallbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String url = "https://play.google.com/store/apps/details?id=" + dataProvider.getPackagename();
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            activity.startActivity(i);
                        }
                    });

                    appcancelbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                } else {
                    dialog.dismiss();
                    dialogShow(adsShow);
                }
            }
        }
    }
}
