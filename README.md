# Advertising

Add it in your root build.gradle at the end of repositories:

    allprojects {
        repositories {
            google()
            jcenter()
            maven { url 'https://jitpack.io' }
        }
    }
  
Add it in your APP build.gradle at the end of repositories:

      implementation 'com.github.admodule:Admodule:2.2'
      
      defaultConfig {
        manifestPlaceholders = [onesignal_app_id               : "YOUR ONE SINGNAL ID",
                                onesignal_google_project_number: "REMOTE"]
    }

=> AndroidManifest.xml

    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />

=>Application tag inside added lines.

        <meta-data
            android:name="google_banner_id"
            android:value="YOUR GOOGLE BANNER ID" />

        <meta-data
            android:name="google_interstitial_id"
            android:value="YOUR GOOGLE INTERSTITIAL ID" />

        <meta-data
            android:name="facebook_banner_id"
            android:value="YOUR FACEBOOK BANNER ID" />

        <meta-data
            android:name="facebook_interstitial_id"
            android:value="YOUR FACEBOOK INTERSTITIAL ID" />

=> get your permission for internet and write storage.

=> get permission then keep this line in your activity: 

    ADCaller.getInstance().init(activity);

=> banner ads layout .xml file :
        
    <RelativeLayout
        android:id="@+id/layout_bannerAds"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
       
=> .java file :
     
    RelativeLayout layout_bannerAds = findViewById(R.id.layout_bannerAds);

=> How to call method:

      * Google Advertising :
           GoogleAds.bannerAds(layout);
           GoogleAds.interstitialAds(1);
      
      * Facebook Advertising :
           FacebookAds.bannerAds(layout);
           FacebookAds.interstitialAds(1);
           
      * Custom Advertising :
           CustomAds.bannerAds(layout);
           CustomAds.interstitialAds(1);
           CustomAds.dialogAds(activity,1);
