package com.e2p.myecf.activites;

import static com.e2p.myecf.helpers.ConstantConfig.ALL_CLIENTS;
import static com.e2p.myecf.helpers.Utils.showSnackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.e2p.myecf.R;
import com.e2p.myecf.helpers.MySettings;
import com.e2p.myecf.models.Client;
import com.e2p.myecf.retrofit.RetrofitClient;
import com.e2p.myecf.retrofit.RetrofitInterface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            //loadingClients();

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

    /**********************************(  Loading Clients  )*************************************/
    public void loadingClients() {

        String URL = "Client/GetClient";

        RetrofitInterface service = RetrofitClient.getClientApi().create(RetrofitInterface.class);
        Call<ArrayList<Client>> apiCall = service.getAllClientsQuery(URL);

        pbLoading.setVisibility(View.VISIBLE);
        tvSplashFooter.setVisibility(View.VISIBLE);
        tvSplashFooter.setText(getString(R.string.msg_loading_data));

        apiCall.enqueue(new Callback<ArrayList<Client>>() {
            @Override
            public void onResponse(Call<ArrayList<Client>> call, Response<ArrayList<Client>> response) {

                pbLoading.setVisibility(View.GONE);
                tvSplashFooter.setText("");
                tvSplashFooter.setVisibility(View.GONE);

                if (response.raw().code() == 200) {
                    ALL_CLIENTS = response.body();
                    Log.e(TAG, ALL_CLIENTS.size() + "");
                }

                mySettings.setFirstStart(false);
                Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();

            }

            @Override
            public void onFailure(Call<ArrayList<Client>> call, Throwable t) {
                pbLoading.setVisibility(View.GONE);
                tvSplashFooter.setVisibility(View.GONE);
                tvSplashFooter.setText("");

                mySettings.setFirstStart(false);
                Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });

    }

}