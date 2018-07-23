# Advertising

Add it in your root build.gradle at the end of repositories:

    allprojects {
        repositories {
            google()
            jcenter()
        }

        project.ext {
            GoogleBannerAdsID = '"ca-app-pub-3940256099942544/6300978111"'
            GoogleInterstitialAdsID = '"ca-app-pub-3940256099942544/1033173712"'

            FacebookBannerAdsID = '"2010034689325707_2010034965992346"'
            FacebookInterstitialAdsID = '"172463163471184_172463380137829"'
        }
    }
  
Add it in your APP build.gradle at the end of repositories:

      implementation 'com.github.mayurjksol:Advertising:1.0'

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
