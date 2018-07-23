package com.jksol.admodule.customad;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jksol.admodule.R;
import com.jksol.admodule.classes.Constants;
import com.jksol.admodule.classes.DataProvider;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.Random;

public class InterstrialAdActivity extends AppCompatActivity {

    private LinearLayout mAdContainerLayout;
    private LinearLayout mHeaderLayout;
    private ImageView mIvImage1;
    private LinearLayout mAdLayout;
    private TextView mTvAppname;
    private RatingBar mRatingbar1;
    private TextView mTvDownloads;
    private TextView mTvDiscription;
    private FrameLayout mFrmDownload;
    private ImageView mIvClose;
    RelativeLayout rootview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstrial_ad);
        init();
    }

    private void init() {
        mAdContainerLayout = findViewById(R.id.adContainerLayout);
        mHeaderLayout = findViewById(R.id.headerLayout);
        mIvImage1 = findViewById(R.id.iv_image1);
        mAdLayout = findViewById(R.id.adLayout);
        mTvAppname = findViewById(R.id.tv_appname);
        mRatingbar1 = findViewById(R.id.ratingbar1);
        mTvDownloads = findViewById(R.id.tv_downloads);
        mTvDiscription = findViewById(R.id.tv_discription);
        mFrmDownload = findViewById(R.id.frm_download);
        mIvClose = findViewById(R.id.iv_close);
        rootview = findViewById(R.id.rootview);

        try {
            int size = Constants.adDataProviders.size();

            if (size > 0) {
                Random random = new Random();
                final int pos = random.nextInt(Constants.adDataProviders.size() - 1);

                float rate = Float.parseFloat("4." + random.nextInt(6));

                final DataProvider dataProvider = Constants.adDataProviders.get(pos);
                File jpgFile = new File(Constants.PARENT_DIR + Constants.AD_DIR + dataProvider.getFilename() + ".jpg");
                if (jpgFile.exists()) {
                    Glide.with(getApplicationContext())
                            .load(jpgFile)
                            .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                            .into(mIvImage1);

                    mTvAppname.setText(dataProvider.getappname());
                    mTvDiscription.setText(dataProvider.getDescrip());
                    mRatingbar1.setRating(rate);
                    mTvDownloads.setText("");

                    mIvClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    });

                    rootview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String url = "https://play.google.com/store/apps/details?id=" + dataProvider.getpackagename();
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }
                    });
                } else {
                    rootview.setVisibility(View.GONE);
                }
            }
        } catch (Exception w) {
        }
    }
}




