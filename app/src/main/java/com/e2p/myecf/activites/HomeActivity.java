package com.e2p.myecf.activites;

import static com.e2p.myecf.helpers.Utils.showSnackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.e2p.myecf.R;
import com.e2p.myecf.fragments.ContactsFragment;
import com.e2p.myecf.fragments.MessagesFragment;
import com.e2p.myecf.fragments.StatementsFragment;
import com.e2p.myecf.helpers.InputValidation;
import com.e2p.myecf.helpers.MySettings;
import com.e2p.myecf.views.kbv.KenBurnsView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HOME_ACTIVITTY";

    // Views
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    public ActionBarDrawerToggle drawerToggle;

    // Fragments
    Fragment FContent = null;

    private MySettings mySettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bindViews();
        init();

        if (savedInstanceState == null) {
            navView.setCheckedItem(R.id.nav_statements);
            FContent = new StatementsFragment();
            loadFragment(FContent);
            getSupportActionBar().setTitle(R.string.menu_my_statements);
        }
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
    public void onBackPressed() {
        Fragment currFrag = getSupportFragmentManager().findFragmentById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    /***************************************************************************************************/

    private void bindViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navView = (NavigationView) findViewById(R.id.nav_view);
    }

    //This method is for init Views.
    private void init() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.text_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navView.setNavigationItemSelectedListener(item -> {
            FContent = null;
            showSnackbar(findViewById(android.R.id.content), item.getOrder() + "");
            switch (item.getOrder()) {

                case 1:
                    FContent = new StatementsFragment();
                    loadFragment(FContent);
                    getSupportActionBar().setTitle(R.string.menu_my_statements);
                    break;
                case 2:
                    FContent = new MessagesFragment();
                    loadFragment(FContent);
                    getSupportActionBar().setTitle(R.string.menu_my_messages);
                    break;
                case 3:
                    FContent = new ContactsFragment();
                    loadFragment(FContent);
                    getSupportActionBar().setTitle(R.string.menu_my_contacts);
                    break;
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void loadFragment(Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_home_content, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}