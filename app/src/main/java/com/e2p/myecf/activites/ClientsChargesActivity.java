package com.e2p.myecf.activites;

import static com.e2p.myecf.helpers.ConstantConfig.AB_TITLE;
import static com.e2p.myecf.helpers.ConstantConfig.ALL_STATEMENTS;
import static com.e2p.myecf.helpers.ConstantConfig.CLIENT_CHARGES;
import static com.e2p.myecf.helpers.ConstantConfig.CURENT_EXERCICE;
import static com.e2p.myecf.helpers.ConstantConfig.SELECTED_ANNUAL_CHARGE;
import static com.e2p.myecf.helpers.ConstantConfig.SELECTED_CLIENT;
import static com.e2p.myecf.helpers.Utils.showSnackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.e2p.myecf.R;
import com.e2p.myecf.adapters.GroupAdapter;
import com.e2p.myecf.helpers.MySettings;
import com.e2p.myecf.models.AnnualCharge;
import com.e2p.myecf.models.Statement;
import com.e2p.myecf.models.StatementsGroup;
import com.e2p.myecf.retrofit.RetrofitClient;
import com.e2p.myecf.retrofit.RetrofitInterface;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientsChargesActivity extends AppCompatActivity {

    private static final String TAG = "C_CHARGES_ACTIVITTY";

    private Toolbar toolbar;
    private MySettings mySettings;

    private NestedScrollView nsvMain;
    private TableLayout tlCharges;
    private RelativeLayout emptyListView;
    private LinearLayoutCompat progressView;
    private AppCompatButton btnEmptyViewRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients_charges);

        bindViews();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            SELECTED_ANNUAL_CHARGE = null;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.client_charges_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            loadeClientCharges();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**********************************************************************************************/

    private void bindViews() {
        try {

            toolbar = (Toolbar) findViewById(R.id.toolbar);

            nsvMain = (NestedScrollView) findViewById(R.id.nsv_main_container);
            tlCharges = (TableLayout) findViewById(R.id.tl_client_charges);
            emptyListView = (RelativeLayout) findViewById(R.id.empty_list_view);
            btnEmptyViewRefresh = (AppCompatButton) findViewById(R.id.btn_empty_view_refresh);

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

            btnEmptyViewRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        loadeClientCharges();
                    } catch (Exception e) {
                        showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
                    } finally {
                    }
                }
            });

            loadeClientCharges();

        } catch (Exception e) {
            Log.e(TAG, e.toString());
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
        }
    }

    /**********************************************************************************************/

    private void loadeClientCharges() {

        progressView.setVisibility(View.VISIBLE);
        emptyListView.setVisibility(View.GONE);
        nsvMain.setVisibility(View.GONE);

        Calendar calendar = Calendar.getInstance();

        String URL = "Client/ChargeAnnuelleClient?";
        RetrofitInterface service = RetrofitClient.getClientApi().create(RetrofitInterface.class);
        Call<ArrayList<AnnualCharge>> apiCall = service.getAllClientsChargesQuery(URL, 1, calendar.get(Calendar.YEAR));

        apiCall.enqueue(new Callback<ArrayList<AnnualCharge>>() {
            @Override
            public void onResponse(Call<ArrayList<AnnualCharge>> call, Response<ArrayList<AnnualCharge>> response) {
                progressView.setVisibility(View.GONE);
                if (response.raw().code() == 200) {
                    nsvMain.setVisibility(View.VISIBLE);
                    CLIENT_CHARGES = response.body();

                    for (AnnualCharge charge : CLIENT_CHARGES) {
                        addRow(charge, tlCharges);
                    }

                } else {
                    emptyListView.setVisibility(View.VISIBLE);
                    nsvMain.setVisibility(View.GONE);
                    showSnackbar(findViewById(android.R.id.content), response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AnnualCharge>> call, Throwable t) {
                progressView.setVisibility(View.GONE);
                emptyListView.setVisibility(View.VISIBLE);
                nsvMain.setVisibility(View.VISIBLE);
            }
        });
    }

    public void addRow(AnnualCharge obj, TableLayout tab) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View mView = inflater.inflate(R.layout.item_table_annual_charge_row, null);

        AppCompatTextView tvCode = (AppCompatTextView) mView.findViewById(R.id.tv_table_row_code);
        AppCompatTextView tvClient = (AppCompatTextView) mView.findViewById(R.id.tv_table_row_client);

        AppCompatTextView tvMonth_1 = (AppCompatTextView) mView.findViewById(R.id.tv_table_row_month_1);
        AppCompatTextView tvMonth_2 = (AppCompatTextView) mView.findViewById(R.id.tv_table_row_month_2);
        AppCompatTextView tvMonth_3 = (AppCompatTextView) mView.findViewById(R.id.tv_table_row_month_3);
        AppCompatTextView tvMonth_4 = (AppCompatTextView) mView.findViewById(R.id.tv_table_row_month_4);
        AppCompatTextView tvMonth_5 = (AppCompatTextView) mView.findViewById(R.id.tv_table_row_month_5);
        AppCompatTextView tvMonth_6 = (AppCompatTextView) mView.findViewById(R.id.tv_table_row_month_6);
        AppCompatTextView tvMonth_7 = (AppCompatTextView) mView.findViewById(R.id.tv_table_row_month_7);
        AppCompatTextView tvMonth_8 = (AppCompatTextView) mView.findViewById(R.id.tv_table_row_month_8);
        AppCompatTextView tvMonth_9 = (AppCompatTextView) mView.findViewById(R.id.tv_table_row_month_9);
        AppCompatTextView tvMonth_10 = (AppCompatTextView) mView.findViewById(R.id.tv_table_row_month_10);
        AppCompatTextView tvMonth_11 = (AppCompatTextView) mView.findViewById(R.id.tv_table_row_month_11);
        AppCompatTextView tvMonth_12 = (AppCompatTextView) mView.findViewById(R.id.tv_table_row_month_12);

        AppCompatTextView tvTotal = (AppCompatTextView) mView.findViewById(R.id.tv_table_row_total);

        tvCode.setText(obj.getCode());
        tvClient.setText(obj.getClient());

        tvMonth_1.setText(String.valueOf(obj.getMonth_1()));
        tvMonth_2.setText(String.valueOf(obj.getMonth_2()));
        tvMonth_3.setText(String.valueOf(obj.getMonth_3()));
        tvMonth_4.setText(String.valueOf(obj.getMonth_4()));
        tvMonth_5.setText(String.valueOf(obj.getMonth_5()));
        tvMonth_6.setText(String.valueOf(obj.getMonth_6()));
        tvMonth_7.setText(String.valueOf(obj.getMonth_7()));
        tvMonth_8.setText(String.valueOf(obj.getMonth_8()));
        tvMonth_9.setText(String.valueOf(obj.getMonth_9()));
        tvMonth_10.setText(String.valueOf(obj.getMonth_10()));
        tvMonth_11.setText(String.valueOf(obj.getMonth_11()));
        tvMonth_12.setText(String.valueOf(obj.getMonth_12()));

        tvTotal.setText(String.valueOf(obj.getTotal()));

        TableRow tr = new TableRow(getApplicationContext());
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
        tr.addView(mView);

        tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SELECTED_ANNUAL_CHARGE = obj;
                } catch (Exception e) {
                    showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
                } finally {
                }
            }
        });

        tab.addView(tr);
    }

}