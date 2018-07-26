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

      implementation 'com.github.mayurjksol:Advertising:1.0'

=> AndroidManifest.xml

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
           CustomAds.dialogAds(1);
