package com.e2p.myecf.activites;

import static com.e2p.myecf.helpers.Utils.showSnackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.e2p.myecf.R;
import com.e2p.myecf.helpers.MySettings;

import java.util.Locale;

public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = "SPLASH_SCREEN_ACTIVITTY";

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;

    private MySettings mySettings;

    // Views
    private AppCompatImageView ivSplashLogo;
    private AppCompatTextView tvSplashHeader;
    private AppCompatTextView tvSplashFooter;
    private ProgressBar pbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        bindViews();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            String _Language = Locale.getDefault().getLanguage();
            mySettings.setCurLanguage(_Language);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
        }
    }

    /**********************************************************************************************/

    private void bindViews() {
        try {
            ivSplashLogo = (AppCompatImageView) findViewById(R.id.iv_splash_logo);
            tvSplashHeader = (AppCompatTextView) findViewById(R.id.tv_splash_screen_header);
            tvSplashFooter = (AppCompatTextView) findViewById(R.id.tv_splash_screen_footer);
            pbLoading = (ProgressBar) findViewById(R.id.pb_splash_screen_footer);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
        }
    }

    private void init() {
        try {
            //settings
            mySettings = new MySettings(getApplicationContext());

            //load logo
            Glide.with(getApplicationContext())
                    .load(R.drawable.logo)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.logo)
                    .into(ivSplashLogo);

            startDelay();

        } catch (Exception e) {
            Log.e(TAG, e.toString());
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
        }
    }

    /**********************************************************************************************/

    private void startDelay() {
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
        }

    }

}