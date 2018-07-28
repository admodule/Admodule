package com.admodule;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.admodule.classes.Constants;
import com.admodule.classes.DataProvider;
import com.admodule.customad.BannerAdClass;
import com.admodule.customad.InterstrialAdActivity;
import com.facebook.ads.AdSettings;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.util.Random;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by pratik on 28-04-18.
 */

public class ADCaller implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static ADCaller singleton;
    Activity activity;

    private String fb_InterstrialAdID, google_InterstrialAdID;

    private static final int MY_PERMISSIONS_REQUEST_CODE = 1;
    String[] perms = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA"};

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

                Constants.google_banner_id = bundle.getString("google_banner_id");
                Constants.google_interstitial_id = bundle.getString("google_interstitial_id");
                Constants.facebook_banner_id = bundle.getString("facebook_banner_id");
                Constants.facebook_interstitial_id = bundle.getString("facebook_interstitial_id");

            } catch (Throwable t) {
                t.printStackTrace();
            }

            PreloardGoogleAD();
            PreloardFacebookAD();

            if (!checkPermission()) {
                requestPermission();
            } else {
                if (new ConnectionDetector(activity).isConnectingToInternet()) {
                    InitlaizeApiData initlaizeApiDat = new InitlaizeApiData(activity);
                    initlaizeApiDat.isFlikerDownload();
                }
            }
        } catch (Exception e) {
        }
    }

    public void LoardBannerAd(ViewGroup viewGroup, String BannerId, String type,int mainType) {
        try {
            if (new ConnectionDetector(activity).isConnectingToInternet()) {
                BannerAdClass bannerAdClass = new BannerAdClass(activity);
                bannerAdClass.ShowBannerAd(viewGroup, BannerId, type,mainType);
            }
        } catch (Exception e) {
        }
    }

    InterstitialAd interstitialAd;
    com.facebook.ads.InterstitialAd fbInterstitial;

    public void PreloardGoogleAD() {
        try {
            this.google_InterstrialAdID = Constants.google_interstitial_id;

            if (new ConnectionDetector(activity).isConnectingToInternet()) {
                //-----Google Ad First
                interstitialAd = new InterstitialAd(activity);
                interstitialAd.setAdUnitId(google_InterstrialAdID);
                interstitialAd.loadAd(new AdRequest.Builder().build());
                interstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        // Load the next interstitial.
                        interstitialAd.loadAd(new AdRequest.Builder().build());
                    }
                });
            }
        } catch (Exception e) {
        }
    }

    public void PreloardFacebookAD() {
        try {
            this.fb_InterstrialAdID = Constants.facebook_interstitial_id;
            if (new ConnectionDetector(activity).isConnectingToInternet()) {
                //----------------Fb Ad First
                AdSettings.addTestDevice("cf4cf9f0-e8cb-43db-a1e0-3bbbdb028f3e");
                fbInterstitial = new com.facebook.ads.InterstitialAd(activity, fb_InterstrialAdID);
                fbInterstitial.loadAd();
            }
        } catch (Exception e) {
        }
    }

    public void googleInterstitialShow(int adsShow, int mainType) {
        try {
            Random random = new Random();
            int no = random.nextInt(adsShow);
            if (no == 0) {
                no = 1;
            }
            if (no == 1) {
                if (interstitialAd != null && interstitialAd.isLoaded()) {
                    interstitialAd.show();
                } else {
                    PreloardGoogleAD();
                    if (mainType == 1 || mainType==3) {
                        facebookInterstitialShow(adsShow, 1);
                    } else if (mainType == 2) {
                        customInterstrial(adsShow, 2);
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public void facebookInterstitialShow(int adsShow, int mainType) {
        try {
            Random random = new Random();
            int no = random.nextInt(adsShow);
            if (no == 0) {
                no = 1;
            }
            if (no == 1) {
                if (fbInterstitial != null && fbInterstitial.isAdLoaded()) {
                    fbInterstitial.show();
                } else {
                    PreloardFacebookAD();
                    if (mainType == 1) {
                        customInterstrial(adsShow, 1);
                    } else if (mainType == 2) {
                        googleInterstitialShow(adsShow, 2);
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public void PreloardCustomAD(final int adsNo) {
        try {
            if (new ConnectionDetector(activity).isConnectingToInternet()) {
                customInterstrial(adsNo, 1);
            }
        } catch (Exception e) {
        }
    }

    public void customInterstrial(final int adsNo, final int mainType) {
        try {
            if (new ConnectionDetector(activity).isConnectingToInternet()) {
                if (Constants.interstitialList.size() > 0) {
                    Random random = new Random();
                    int no = random.nextInt(adsNo);
                    if (no == 0) {
                        no = 1;
                    }
                    if (no == 1) {
                        Intent intent = new Intent(activity, InterstrialAdActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(intent);
                    }
                } else {
                    if(mainType==3){
                        googleInterstitialShow(adsNo,3);
                    }
                }
            }
        } catch (Exception e) {
            customInterstrial(adsNo, mainType);//---loard google if failed custom ad
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

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(activity, WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(activity, perms, MY_PERMISSIONS_REQUEST_CODE);
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] strings, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean writeAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (writeAccepted) {
                        if (new ConnectionDetector(activity).isConnectingToInternet()) {
                            InitlaizeApiData initlaizeApiDat = new InitlaizeApiData(activity);
                            initlaizeApiDat.isFlikerDownload();
                        }
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (activity.shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) {
                                showMessageOKCancel("You need to allow access to all the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    activity.requestPermissions(perms, MY_PERMISSIONS_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }

    }
}
