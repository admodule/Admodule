package com.advertising;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.admodule.ADCaller;
import com.admodule.caller.CustomAds;
import com.admodule.caller.GoogleAds;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    LinearLayout bannerAds;
    Button showAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bannerAds = findViewById(R.id.bannerAds);
        showAds = findViewById(R.id.showAds);

        ADCaller.getInstance().init(MainActivity.this);


        GoogleAds.bannerAds(bannerAds);
        GoogleAds.interstitialAds(1);
        showAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoogleAds.interstitialAds(1);
            }
        });
//            FacebookAds.bannerAds(bannerAds);
//            FacebookAds.interstitialAds(1);
//
//            CustomAds.bannerAds(bannerAds);
//            CustomAds.interstitialAds(1);
//
        CustomAds.dialogAds(this, 1);
    }

}
