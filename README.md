# Advertising

Add it in your root build.gradle at the end of repositories:

allprojects {
  repositories {
        google()
        jcenter()

        maven { url 'https://jitpack.io' }
  }

    project.ext {
        GoogleBannerAdsID = '"YOUR ADMOB GOOGLE BANNER ID"'
        GoogleInterstitialAdsID = '"YOUR ADMOB GOOGLE INTERSTITIAL ID"'

        FacebookBannerAdsID = '"YOUR ADMOB FACEBOOK BANNER ID"'
        FacebookInterstitialAdsID = '"YOUR ADMOB FACEBOOK INTERSTITIAL ID"'
    }
}
  
  
Add it in your APP build.gradle at the end of repositories:

dependencies {
    implementation 'com.github.mayurjksol:Advertising:1.0'
}

====================================================================
=> get your permission for internet and write storage.

=> get permission then keep this line in your activity: 
      ADCaller.getInstance().IntializeApiData(MainActivity.this);

=> How to call method:
      * Google Advertising :
          GoogleAds.bannerAds(bannerAds);
          GoogleAds.interstitialAds(1);
      
      * Facebook Advertising :
           FacebookAds.bannerAds(bannerAds);
           FacebookAds.interstitialAds(1);
