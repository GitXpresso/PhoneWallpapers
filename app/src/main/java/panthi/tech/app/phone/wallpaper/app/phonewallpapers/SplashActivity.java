package panthi.tech.app.phone.wallpaper.app.phonewallpapers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

//import com.facebook.ads.Ad;
//import com.facebook.ads.AdError;
//import com.facebook.ads.CacheFlag;
//import com.facebook.ads.InterstitialAd;
//import com.facebook.ads.InterstitialAdExtendedListener;

import java.util.EnumSet;


public class SplashActivity extends AppCompatActivity {
//    public static InterstitialAd FBinterstitialAd = null;
//    public static String TAG = "msg";

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_splash);

        SplashActivity.this.ContinueWithoutAdsProcess();

//        LoadFBIntertistialAds();
    }


    public void ContinueWithoutAdsProcess() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(SplashActivity.this, StartActivity.class));
            }
        }, 3000);
    }

//    public void LoadFBIntertistialAds() {
//        InterstitialAd interstitialAd = new InterstitialAd(this, getString(R.string.INTRESTITIAL_FB_AD));
//        FBinterstitialAd = interstitialAd;
//        interstitialAd.loadAd();
//        FBinterstitialAd.buildLoadAdConfig().withAdListener(new InterstitialAdExtendedListener() {
//            @Override
//            public void onInterstitialActivityDestroyed() {
//
//            }
//
//            @Override
//            public void onInterstitialDisplayed(Ad ad) {
//                Log.e(SplashActivity.TAG, "Interstitial ad_folder displayed.");
//            }
//
//            @Override
//            public void onInterstitialDismissed(Ad ad) {
//                Log.e(SplashActivity.TAG, "Interstitial ad_folder dismissed.");
//            }
//
//            @Override
//            public void onError(Ad ad, AdError adError) {
//                String str = SplashActivity.TAG;
//                Log.e(str, "Interstitial ad_folder failed to load: " + adError.getErrorMessage());
//            }
//
//            @Override
//            public void onAdLoaded(Ad ad) {
//                Log.d(SplashActivity.TAG, "Interstitial ad_folder is loaded and ready to be displayed!");
//            }
//
//            @Override
//            public void onAdClicked(Ad ad) {
//                Log.d(SplashActivity.TAG, "Interstitial ad_folder clicked!");
//            }
//
//            @Override
//            public void onLoggingImpression(Ad ad) {
//                Log.d(SplashActivity.TAG, "Interstitial ad_folder impression logged!");
//            }
//
//            @Override
//            public void onRewardedAdCompleted() {
//
//            }
//
//            @Override
//            public void onRewardedAdServerSucceeded() {
//
//            }
//
//            @Override
//            public void onRewardedAdServerFailed() {
//
//            }
//        }).withCacheFlags(EnumSet.of(CacheFlag.VIDEO)).build();
//    }

}
