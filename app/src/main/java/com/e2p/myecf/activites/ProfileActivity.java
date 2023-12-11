package com.e2p.myecf.activites;

import static com.e2p.myecf.helpers.ConstantConfig.AB_TITLE;
import static com.e2p.myecf.helpers.ConstantConfig.CURENT_CLIENT;
import static com.e2p.myecf.helpers.ConstantConfig.CURENT_EXERCICE;
import static com.e2p.myecf.helpers.ConstantConfig.YEAR_0;
import static com.e2p.myecf.helpers.ConstantConfig.YEAR_1;
import static com.e2p.myecf.helpers.ConstantConfig.YEAR_2;
import static com.e2p.myecf.helpers.ConstantConfig.YEAR_3;
import static com.e2p.myecf.helpers.Utils.collapse;
import static com.e2p.myecf.helpers.Utils.expand;
import static com.e2p.myecf.helpers.Utils.showSnackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.e2p.myecf.R;
import com.e2p.myecf.helpers.MySettings;

import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "PROFILE_ACTIVITTY";

    private Toolbar toolbar;
    private AppCompatImageView imvProfilePic;
    private AppCompatTextView tvName;
    private MySettings mySettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        bindViews();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {

        } catch (Exception e) {
            Log.e(TAG, e.toString());
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**********************************************************************************************/

    private void bindViews() {
        try {

            toolbar = (Toolbar) findViewById(R.id.toolbar);

            imvProfilePic = (AppCompatImageView) findViewById(R.id.imv_profile_photo);

            tvName = (AppCompatTextView) findViewById(R.id.tv_profile_name);

        } catch (Exception e) {
            Log.e(TAG, e.toString());
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
        }
    }

    private void init() {
        try {
            //settings
            mySettings = new MySettings(getApplicationContext());

            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(AB_TITLE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            tvName.setText((CURENT_CLIENT != null) ? CURENT_CLIENT.getName() : "");

        } catch (Exception e) {
            Log.e(TAG, e.toString());
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
        }
    }

/**********************************************************************************************/
}