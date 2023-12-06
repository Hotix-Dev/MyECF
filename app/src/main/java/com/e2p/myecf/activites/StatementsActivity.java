package com.e2p.myecf.activites;

import static com.e2p.myecf.helpers.ConstantConfig.AB_TITLE;
import static com.e2p.myecf.helpers.ConstantConfig.ALL_CLIENTS;
import static com.e2p.myecf.helpers.ConstantConfig.CURENT_EXERCICE;
import static com.e2p.myecf.helpers.ConstantConfig.SELECTED_CLIENT;
import static com.e2p.myecf.helpers.ConstantConfig.ALL_STATEMENTS;
import static com.e2p.myecf.helpers.Utils.collapse;
import static com.e2p.myecf.helpers.Utils.expand;
import static com.e2p.myecf.helpers.Utils.showSnackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.e2p.myecf.R;
import com.e2p.myecf.adapters.ClientSpinnerAdapter;
import com.e2p.myecf.adapters.DashbordGridAdapter;
import com.e2p.myecf.adapters.ExerciceSpinnerAdapter;
import com.e2p.myecf.helpers.MySettings;
import com.e2p.myecf.helpers.Stub;
import com.e2p.myecf.models.Client;
import com.e2p.myecf.models.DashItem;
import com.e2p.myecf.models.Statement;
import com.e2p.myecf.retrofit.RetrofitClient;
import com.e2p.myecf.retrofit.RetrofitInterface;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatementsActivity extends AppCompatActivity {

    private static final String TAG = "STATEMENTS_ACTIVITTY";
    private static Integer year_0 = 1970;
    private static Integer year_1 = 1970;
    private static Integer year_2 = 1970;
    private static Integer year_3 = 1970;
    private Toolbar toolbar;
    private MySettings mySettings;
    private AppCompatButton btnExercice_0;
    private AppCompatButton btnExercice_1;
    private AppCompatButton btnExercice_2;
    private AppCompatButton btnExercice_3;
    private AppCompatSpinner spClient;

    private RelativeLayout rlFilter;
    private RelativeLayout emptyListView;
    private LinearLayoutCompat progressView;

    private AppCompatImageButton ibCollaps;
    private AppCompatButton btnEmptyViewRefresh;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statements);

        bindViews();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            listView.setEnabled(true);

            if (mySettings.getFilterCollapsed()) {
                collapse(rlFilter);
                ibCollaps.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
            }

        } catch (Exception e) {
            Log.e(TAG, e.toString());
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.statements_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            loadeStatementss();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**********************************************************************************************/

    private void bindViews() {
        try {

            toolbar = (Toolbar) findViewById(R.id.toolbar);

            spClient = (AppCompatSpinner) findViewById(R.id.sp_statement_filter_client);

            rlFilter = (RelativeLayout) findViewById(R.id.rl_statements_filter);
            emptyListView = (RelativeLayout) findViewById(R.id.empty_list_view);

            ibCollaps = (AppCompatImageButton) findViewById(R.id.ibtn_collaps_filter);
            btnEmptyViewRefresh = (AppCompatButton) findViewById(R.id.btn_empty_view_refresh);

            btnExercice_0 = (AppCompatButton) findViewById(R.id.btn_statements_filter_exercice_0);
            btnExercice_1 = (AppCompatButton) findViewById(R.id.btn_statements_filter_exercice_1);
            btnExercice_2= (AppCompatButton) findViewById(R.id.btn_statements_filter_exercice_2);
            btnExercice_3 = (AppCompatButton) findViewById(R.id.btn_statements_filter_exercice_3);

            listView = (ListView) findViewById(R.id.lv_statements);

            progressView = (LinearLayoutCompat) findViewById(R.id.loading_view);

        } catch (Exception e) {
            Log.e(TAG, e.toString());
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
        }
    }


    private void init() {
        try {
            //settings
            mySettings = new MySettings(getApplicationContext());

            Calendar calendar = Calendar.getInstance();
            year_0 = calendar.get(Calendar.YEAR);
            year_1 = year_0 - 1;
            year_2 = year_0 - 2;
            year_3 = year_0 - 3;

            CURENT_EXERCICE = year_0;

            btnExercice_0.setText(year_0 + "");
            btnExercice_0.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
            btnExercice_0.setBackgroundDrawable( ResourcesCompat.getDrawable(getResources(), R.drawable.selected_bg_bordered_button_end_radus, null));

            btnExercice_1.setText(year_1 + "");
            btnExercice_1.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
            btnExercice_1.setBackgroundDrawable( ResourcesCompat.getDrawable(getResources(), R.drawable.normal_bg_bordered_button_no_radus, null));

            btnExercice_2.setText(year_2 + "");
            btnExercice_2.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
            btnExercice_2.setBackgroundDrawable( ResourcesCompat.getDrawable(getResources(), R.drawable.normal_bg_bordered_button_no_radus, null));

            btnExercice_3.setText(year_3 + "");
            btnExercice_3.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
            btnExercice_3.setBackgroundDrawable( ResourcesCompat.getDrawable(getResources(), R.drawable.normal_bg_bordered_button_start_radus, null));

            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(AB_TITLE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            ibCollaps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (mySettings.getFilterCollapsed()) {
                            mySettings.setFilterCollapsed(false);
                            expand(rlFilter);
                            ibCollaps.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                        } else {
                            mySettings.setFilterCollapsed(true);
                            collapse(rlFilter);
                            ibCollaps.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                        }
                    } catch (Exception e) {
                        showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
                    } finally {
                    }
                }
            });

            btnExercice_0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        CURENT_EXERCICE = year_0;

                        btnExercice_0.setText(year_0 + "");
                        btnExercice_0.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
                        btnExercice_0.setBackgroundDrawable( ResourcesCompat.getDrawable(getResources(), R.drawable.selected_bg_bordered_button_end_radus, null));

                        btnExercice_1.setText(year_1 + "");
                        btnExercice_1.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
                        btnExercice_1.setBackgroundDrawable( ResourcesCompat.getDrawable(getResources(), R.drawable.normal_bg_bordered_button_no_radus, null));

                        btnExercice_2.setText(year_2 + "");
                        btnExercice_2.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
                        btnExercice_2.setBackgroundDrawable( ResourcesCompat.getDrawable(getResources(), R.drawable.normal_bg_bordered_button_no_radus, null));

                        btnExercice_3.setText(year_3 + "");
                        btnExercice_3.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
                        btnExercice_3.setBackgroundDrawable( ResourcesCompat.getDrawable(getResources(), R.drawable.normal_bg_bordered_button_start_radus, null));

                    } catch (Exception e) {
                        showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
                    } finally {
                    }
                }
            });

            btnExercice_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        CURENT_EXERCICE = year_1;

                        btnExercice_0.setText(year_0 + "");
                        btnExercice_0.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
                        btnExercice_0.setBackgroundDrawable( ResourcesCompat.getDrawable(getResources(), R.drawable.normal_bg_bordered_button_end_radus, null));

                        btnExercice_1.setText(year_1 + "");
                        btnExercice_1.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
                        btnExercice_1.setBackgroundDrawable( ResourcesCompat.getDrawable(getResources(), R.drawable.selected_bg_bordered_button_no_radus, null));

                        btnExercice_2.setText(year_2 + "");
                        btnExercice_2.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
                        btnExercice_2.setBackgroundDrawable( ResourcesCompat.getDrawable(getResources(), R.drawable.normal_bg_bordered_button_no_radus, null));

                        btnExercice_3.setText(year_3 + "");
                        btnExercice_3.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
                        btnExercice_3.setBackgroundDrawable( ResourcesCompat.getDrawable(getResources(), R.drawable.normal_bg_bordered_button_start_radus, null));


                    } catch (Exception e) {
                        showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
                    } finally {
                    }
                }
            });

            btnExercice_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        CURENT_EXERCICE = year_2;

                        btnExercice_0.setText(year_0 + "");
                        btnExercice_0.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
                        btnExercice_0.setBackgroundDrawable( ResourcesCompat.getDrawable(getResources(), R.drawable.normal_bg_bordered_button_end_radus, null));

                        btnExercice_1.setText(year_1 + "");
                        btnExercice_1.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
                        btnExercice_1.setBackgroundDrawable( ResourcesCompat.getDrawable(getResources(), R.drawable.normal_bg_bordered_button_no_radus, null));

                        btnExercice_2.setText(year_2 + "");
                        btnExercice_2.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
                        btnExercice_2.setBackgroundDrawable( ResourcesCompat.getDrawable(getResources(), R.drawable.selected_bg_bordered_button_no_radus, null));

                        btnExercice_3.setText(year_3 + "");
                        btnExercice_3.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
                        btnExercice_3.setBackgroundDrawable( ResourcesCompat.getDrawable(getResources(), R.drawable.normal_bg_bordered_button_start_radus, null));


                    } catch (Exception e) {
                        showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
                    } finally {
                    }
                }
            });

            btnExercice_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        CURENT_EXERCICE = year_3;

                        btnExercice_0.setText(year_0 + "");
                        btnExercice_0.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
                        btnExercice_0.setBackgroundDrawable( ResourcesCompat.getDrawable(getResources(), R.drawable.normal_bg_bordered_button_end_radus, null));

                        btnExercice_1.setText(year_1 + "");
                        btnExercice_1.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
                        btnExercice_1.setBackgroundDrawable( ResourcesCompat.getDrawable(getResources(), R.drawable.normal_bg_bordered_button_no_radus, null));

                        btnExercice_2.setText(year_2 + "");
                        btnExercice_2.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
                        btnExercice_2.setBackgroundDrawable( ResourcesCompat.getDrawable(getResources(), R.drawable.normal_bg_bordered_button_no_radus, null));

                        btnExercice_3.setText(year_3 + "");
                        btnExercice_3.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
                        btnExercice_3.setBackgroundDrawable( ResourcesCompat.getDrawable(getResources(), R.drawable.selected_bg_bordered_button_start_radus, null));


                    } catch (Exception e) {
                        showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
                    } finally {
                    }
                }
            });


//            ClientSpinnerAdapter clientSpinnerAdapter = new ClientSpinnerAdapter(getApplicationContext(), ALL_CLIENTS);
//            spClient.setAdapter(clientSpinnerAdapter);

            loadeStatementss();

        } catch (Exception e) {
            Log.e(TAG, e.toString());
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
        }
    }

    /**********************************************************************************************/

    private void loadeStatementss() {

        progressView.setVisibility(View.VISIBLE);
        emptyListView.setVisibility(View.GONE);

        String URL = "Client/GetDeclarations?";
        RetrofitInterface service = RetrofitClient.getClientApi().create(RetrofitInterface.class);
        Call<ArrayList<Statement>> apiCall = service.getAllStatementsQuery(URL, "1",  "192", "2023", "2023");

        apiCall.enqueue(new Callback<ArrayList<Statement>>() {
            @Override
            public void onResponse(Call<ArrayList<Statement>> call, Response<ArrayList<Statement>> response) {
                progressView.setVisibility(View.GONE);
                if (response.raw().code() == 200) {

                    //ALL_STATEMENTS = response.body();

                } else {
                    emptyListView.setVisibility(View.VISIBLE);
                    showSnackbar(findViewById(android.R.id.content), response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Statement>> call, Throwable t) {
                progressView.setVisibility(View.GONE);
                emptyListView.setVisibility(View.VISIBLE);
                listView.setEmptyView(findViewById(R.id.empty_list_view));
            }
        });
    }

}