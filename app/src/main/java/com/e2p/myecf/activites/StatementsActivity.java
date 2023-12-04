package com.e2p.myecf.activites;

import static com.e2p.myecf.helpers.ConstantConfig.AB_TITLE;
import static com.e2p.myecf.helpers.ConstantConfig.ALL_CLIENTS;
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

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatementsActivity extends AppCompatActivity {

    private static final String TAG = "STATEMENTS_ACTIVITTY";

    private Toolbar toolbar;
    private MySettings mySettings;
    private AppCompatSpinner spExercice;
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

    /**********************************************************************************************/

    private void bindViews() {
        try {

            toolbar = (Toolbar) findViewById(R.id.toolbar);

            spExercice = (AppCompatSpinner) findViewById(R.id.sp_statement_filter_exercice);
            spClient = (AppCompatSpinner) findViewById(R.id.sp_statement_filter_client);

            rlFilter = (RelativeLayout) findViewById(R.id.rl_statements_filter);
            emptyListView = (RelativeLayout) findViewById(R.id.empty_list_view);

            ibCollaps = (AppCompatImageButton) findViewById(R.id.ibtn_collaps_filter);
            btnEmptyViewRefresh = (AppCompatButton) findViewById(R.id.btn_empty_view_refresh);

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

            ExerciceSpinnerAdapter exerciceSpinnerAdapter = new ExerciceSpinnerAdapter(getApplicationContext(), Stub.getExercicesList());
            spExercice.setAdapter(exerciceSpinnerAdapter);

            ClientSpinnerAdapter clientSpinnerAdapter = new ClientSpinnerAdapter(getApplicationContext(), ALL_CLIENTS);
            spClient.setAdapter(clientSpinnerAdapter);

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