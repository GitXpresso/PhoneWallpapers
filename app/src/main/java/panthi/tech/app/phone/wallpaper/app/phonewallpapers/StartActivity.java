package panthi.tech.app.phone.wallpaper.app.phonewallpapers;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

//import com.facebook.ads.NativeAdLayout;
//
//import baby.name.mean.baby.babynamemeaningbabyname.custom.Custom_Bannerad;
//import baby.name.mean.baby.babynamemeaningbabyname.custom.Custom_nativeads;
//import baby.name.mean.baby.babynamemeaningbabyname.custom.FBPreLoadAds;

public class StartActivity extends AppCompatActivity {
    public static android.app.Activity main_activity;
    android.app.Activity home_activity = null;
    ImageView iv_more, iv_rate, iv_policy, iv_share;
    Animation push_animation;

    ImageView set_wallpaper_img;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_start);
        this.home_activity = this;

//        NativeAdLayout nativeAdLayout = (NativeAdLayout) findViewById(R.id.banner_container);
//        Custom_Bannerad common_bannerad = new Custom_Bannerad();
//        common_bannerad.loadbannerad(getApplicationContext(), nativeAdLayout);
//
//        NativeAdLayout nativeAdLayout2 = (NativeAdLayout) findViewById(R.id.facebook_native_lay);
//        Custom_nativeads common_nativeads = new Custom_nativeads();
//        common_nativeads.loadnative(this, nativeAdLayout2);

        iv_policy = findViewById(R.id.iv_policy);
        iv_share = findViewById(R.id.iv_share);
        iv_more = findViewById(R.id.iv_more);
        iv_rate = findViewById(R.id.iv_rate);

        this.push_animation = AnimationUtils.loadAnimation(this, R.anim.view_push);
        main_activity = new StartActivity();
        this.set_wallpaper_img = (ImageView) findViewById(R.id.set_wallpaper_img);

        this.set_wallpaper_img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                view.startAnimation(StartActivity.this.push_animation);
                startActivity(new Intent(StartActivity.this, FrameActivity.class));
            }
        });

        iv_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublicMethod.privacyPolicy(StartActivity.this);
            }
        });

        iv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublicMethod.shareApp(StartActivity.this);
            }
        });

        iv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublicMethod.moreAppsPlayStore(StartActivity.this);
            }
        });

        iv_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublicMethod.rateApp(StartActivity.this);
            }
        });


    }

//    public void LoadAd(Intent intent) {
//        new FBPreLoadAds().ShowIntertistialWithIntentAds(this, intent, new FBPreLoadAds.OnIntertistialAdsListner() {
//            public void onAdsDismissed() {
//                Log.w(NotificationCompat.CATEGORY_MESSAGE, "Interstitial ad dismissed.");
//            }
//
//            public void onAdsLoaded() {
//                Log.w(NotificationCompat.CATEGORY_MESSAGE, "Interstitial ad displayed.");
//            }
//
//            public void onAdsFailedToLoad(int i) {
//                Log.e(NotificationCompat.CATEGORY_MESSAGE, "Interstitial ad failed to load: " + i);
//            }
//
//            public void onAllEmpty() {
//                Log.e(NotificationCompat.CATEGORY_MESSAGE, "Interstitial ad failed to load!");
//            }
//
//            public void onAdClicked() {
//                Log.w(NotificationCompat.CATEGORY_MESSAGE, "Interstitial ad clicked!");
//            }
//
//            public void onLoggingImpression() {
//                Log.w(NotificationCompat.CATEGORY_MESSAGE, "Interstitial ad impression logged!");
//            }
//        });
//    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.traparentdialog);
        View inflate = LayoutInflater.from(this).inflate(R.layout.exitdialog, (ViewGroup) null);
        if (Build.VERSION.SDK_INT >= 23) {
            inflate.setForegroundGravity(17);
        }
        builder.setView(inflate);
        final AlertDialog create = builder.create();
        create.show();


//        NativeAdLayout nativeAdLayout2 = (NativeAdLayout) inflate.findViewById(R.id.native_ad_container22);
//        Custom_nativeads common_nativeads = new Custom_nativeads();
//        common_nativeads.loadnative(this, nativeAdLayout2);


        ((TextView) inflate.findViewById(R.id.yes)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                StartActivity.this.finishAffinity();
                create.dismiss();
            }
        });
        ((TextView) inflate.findViewById(R.id.no)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                create.dismiss();
            }
        });
        ((TextView) inflate.findViewById(R.id.rate)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                create.dismiss();
                String str = "android.intent.action.VIEW";
                Intent intent2 = new Intent(str);
                StringBuilder sb2 = new StringBuilder();
                sb2.append("http://play.google.com/store/apps/details?id=");
                sb2.append(getPackageName());
                intent2.setData(Uri.parse(sb2.toString()));
                startActivity(intent2);
            }
        });
    }
}
