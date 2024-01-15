package com.e2p.myecf.activites;

import static com.e2p.myecf.helpers.ConstantConfig.ALL_CLIENTS;
import static com.e2p.myecf.helpers.ConstantConfig.CURENT_CLIENT;
import static com.e2p.myecf.helpers.ConstantConfig.SELECTED_CLIENT;
import static com.e2p.myecf.helpers.Utils.showSnackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.e2p.myecf.R;
import com.e2p.myecf.helpers.InputValidation;
import com.e2p.myecf.helpers.MySettings;
import com.e2p.myecf.models.Client;
import com.e2p.myecf.retrofit.RetrofitClient;
import com.e2p.myecf.retrofit.RetrofitInterface;
import com.e2p.myecf.views.kbv.KenBurnsView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LOGIN_ACTIVITTY";

    private MySettings mySettings;
    private InputValidation mInputValidation;

    // Views
    private KenBurnsView kenBurns;
    private AppCompatImageView ivLoginLogo;
    private TextInputLayout ilUsername;
    private TextInputEditText etUsername;
    private TextInputLayout ilPassword;
    private TextInputEditText etPassword;
    private AppCompatButton btnLogin;
    private ProgressBar pbLogin;
    private AppCompatTextView tvAppName;
    private AppCompatTextView tvAppVersion;
    private AppCompatImageButton btnSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindViews();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            //loadeImage();
            btnLogin.setEnabled(true);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
        }
    }

    @Override
    public void onBackPressed() {
        try {
            startExitDialog();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
        }
    }

    /***************************************************************************************************/

    private void bindViews() {

        ilUsername = (TextInputLayout) findViewById(R.id.il_login_username);
        etUsername = (TextInputEditText) findViewById(R.id.et_login_username);

        ilPassword = (TextInputLayout) findViewById(R.id.il_login_password);
        etPassword = (TextInputEditText) findViewById(R.id.et_login_password);

        btnLogin = (AppCompatButton) findViewById(R.id.btn_login);
        pbLogin = (ProgressBar) findViewById(R.id.pb_login);
        tvAppName = (AppCompatTextView) findViewById(R.id.tv_login_app_name);
        tvAppVersion = (AppCompatTextView) findViewById(R.id.tv_login_version);
        btnSetting = (AppCompatImageButton) findViewById(R.id.ibtn_login_setting);

        ivLoginLogo = (AppCompatImageView) findViewById(R.id.img_login_logo);

        kenBurns = (KenBurnsView) findViewById(R.id.ken_burns_images);
        kenBurns.setImageResource(R.drawable.login_bg);
    }

    //This method is for init Views.
    private void init() {

        mInputValidation = new InputValidation(this);

        //loadeImage();
        //etUsername.setText("admin");

        tvAppName.setText(R.string.app_name);

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            tvAppVersion.setText(getString(R.string.text_version) + " " + pInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.toString());
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    btnLogin.setEnabled(false);
                    if (inputTextValidation()) {
                        CURENT_CLIENT = null;

                        if (etUsername.getText().toString().toUpperCase().trim().equals("ADMIN")) {

                            CURENT_CLIENT = new Client(-1, "ADMIN", "Admin", "", "", "", true);
                            Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                            finish();

                        } else {
                            login();
                        }

                    }
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                    showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
                } finally {
                    btnLogin.setEnabled(true);
                }
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    //This method is for EditText valus validation.
    private boolean inputTextValidation() {

        if (!mInputValidation.isInputEditTextFilled(etUsername, ilUsername, getString(R.string.error_message_field_required))) {
            return false;
        }

//        if (!mInputValidation.isInputEditTextFilled(etPassword, ilPassword, getString(R.string.error_message_field_required))) {
//            return false;
//        }
        //Return true if all the inputs are valid
        return true;

    }

    private void loadeImage() {
        try {
            //load BG
            Glide.with(getApplicationContext())
                    .load(R.drawable.login_bg)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.login_bg)
                    .into(kenBurns);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
        }
    }

    //This method show exit dialog.
    private void startExitDialog() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);

        View mView = getLayoutInflater().inflate(R.layout.dialog_exit, null);
        AppCompatButton btnYes = (AppCompatButton) mView.findViewById(R.id.btn_dialog_exit_yes);
        AppCompatButton btnCancel = (AppCompatButton) mView.findViewById(R.id.btn_dialog_exit_cancel);

        mBuilder.setView(mView);
        mBuilder.setCancelable(false);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    /**********************************(  Login  )*************************************/
    public void login() {
        try {
            btnLogin.setEnabled(false);
            pbLogin.setVisibility(View.VISIBLE);
            String Code = etUsername.getText().toString();
            String URL = "Client/GetOne?";

            RetrofitInterface service = RetrofitClient.getClientApi().create(RetrofitInterface.class);
            Call<Client> apiCall = service.getClientByCodeQuery(URL, -1, Code);

            apiCall.enqueue(new Callback<Client>() {
                @Override
                public void onResponse(Call<Client> call, Response<Client> response) {

                    pbLogin.setVisibility(View.GONE);

                    if (response.raw().code() == 200) {
                        CURENT_CLIENT = response.body();
                        CURENT_CLIENT.setAdmin(false);
                        SELECTED_CLIENT = CURENT_CLIENT;
                        Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        startActivity(i);
                        finish();
                    } else {
                        showSnackbar(findViewById(android.R.id.content), response.message());
                        Log.e(TAG, response.raw().code() + "");
                        btnLogin.setEnabled(true);
                    }
                }

                @Override
                public void onFailure(Call<Client> call, Throwable t) {
                    pbLogin.setVisibility(View.GONE);
                    showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_server_down));
                    btnLogin.setEnabled(true);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
            btnLogin.setEnabled(true);
        }

    }

}