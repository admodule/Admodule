package com.admodule.customad;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.admodule.R;
import com.admodule.classes.Constants;
import com.admodule.classes.DataProvider;
import com.bumptech.glide.Glide;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.android.gms.ads.AdRequest;

import java.io.File;
import java.util.Random;

/**
 * Created by pratik on 03-05-18.
 */

public class BannerAdClass {

    public Activity activity;
    private ImageView appicon;
    private TextView txtApptitle, txt_descrip;
    private RatingBar ratingbar1;
    Button btn_install;
    LinearLayout adViewLayout;

    ViewGroup viewGroup;
    String BannerId, type;

    public BannerAdClass(Activity activity) {
        this.activity = activity;
    }

    public void ShowBannerAd(final ViewGroup viewGroup, String BannerId, String type, int mainType) {

        this.viewGroup = viewGroup;
        this.BannerId = BannerId;
        this.type = type;

        if (this.type.equals("google")) {
            google(mainType);
        } else if (this.type.equals("facebook")) {
            facebook(mainType);
        } else {
            LoardEvent(mainType);
        }

    }

    public void google(final int mainType) {
        final com.google.android.gms.ads.AdView mAdView = new com.google.android.gms.ads.AdView(activity);
        mAdView.setAdSize(com.google.android.gms.ads.AdSize.SMART_BANNER);
        mAdView.setAdUnitId(BannerId);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                viewGroup.removeAllViews();
                viewGroup.addView(mAdView);
                viewGroup.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                mAdView.setVisibility(View.GONE);
                if (mainType == 1 || mainType == 3) {
                    facebook(1);
                } else if (mainType == 2) {
                    LoardEvent(2);
                }
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
            }
        });
    }

    public void facebook(final int mainType) {
        AdSettings.addTestDevice("cf4cf9f0-e8cb-43db-a1e0-3bbbdb028f3e");
        final AdView adView = new AdView(activity, BannerId, AdSize.BANNER_HEIGHT_50);

        adView.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                //Log.e("AdError", adError.getErrorMessage());
                adView.setVisibility(View.GONE);
                if (mainType == 1) {
                    LoardEvent(mainType);
                }

            }

            @Override
            public void onAdLoaded(Ad ad) {
                viewGroup.addView(adView);
                viewGroup.setVisibility(View.VISIBLE);
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

        adView.loadAd();
    }

    public void LoardEvent(int mainType) {
        try {

            View view = activity.getLayoutInflater().inflate(R.layout.custom_banner_ad, null);
            view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            appicon = view.findViewById(R.id.appicon);
            txtApptitle = view.findViewById(R.id.txt_Apptitle);
            ratingbar1 = view.findViewById(R.id.ratingbar1);
            btn_install = view.findViewById(R.id.btn_install);
            adViewLayout = view.findViewById(R.id.adViewLayout);
            txt_descrip = view.findViewById(R.id.txt_descrip);

            int size = Constants.bannerList.size();

            if (size > 0) {
                Random random = new Random();

                int limit = Constants.bannerList.size() - 1;
                if (limit != 0) {
                    limit = random.nextInt(limit);
                }
                final DataProvider dataProvider = Constants.bannerList.get(limit);

                File jpgFile = new File(Constants.PARENT_DIR + Constants.AD_DIR + "B_" + dataProvider.getAppname() + ".jpg");

                if (jpgFile.exists()) {
                    Glide.with(activity)
                            .load(jpgFile)
                            .into(appicon);

                    Typeface titleFont = Typeface.createFromAsset(activity.getAssets(), "textfont/" + activity.getString(R.string.font));

                    txtApptitle.setText(dataProvider.getAppname());
                    txt_descrip.setText(dataProvider.getDescription());

                    txtApptitle.setTypeface(titleFont);
                    txt_descrip.setTypeface(titleFont);

                    btn_install.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Constants.Install_From="Banner";
                            String url = "https://play.google.com/store/apps/details?id=" + dataProvider.getPackagename();
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            activity.startActivity(i);
                        }
                    });

                    viewGroup.addView(view);
                    viewGroup.setVisibility(View.VISIBLE);
                } else {
                    adViewLayout.setVisibility(View.GONE);
                    if (mainType == 3) {
                        google(3);
                    }
                }
            }
        } catch (Exception w) {
        }
    }
}
