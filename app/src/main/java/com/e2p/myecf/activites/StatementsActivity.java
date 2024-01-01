package com.e2p.myecf.activites;

import static com.e2p.myecf.helpers.ConstantConfig.AB_TITLE;
import static com.e2p.myecf.helpers.ConstantConfig.ALL_CLIENTS;
import static com.e2p.myecf.helpers.ConstantConfig.CURENT_CLIENT;
import static com.e2p.myecf.helpers.ConstantConfig.CURENT_EXERCICE;
import static com.e2p.myecf.helpers.ConstantConfig.YEAR_0;
import static com.e2p.myecf.helpers.ConstantConfig.YEAR_1;
import static com.e2p.myecf.helpers.ConstantConfig.YEAR_2;
import static com.e2p.myecf.helpers.ConstantConfig.YEAR_3;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.e2p.myecf.R;
import com.e2p.myecf.adapters.ClientSpinnerAdapter;
import com.e2p.myecf.adapters.DashbordGridAdapter;
import com.e2p.myecf.adapters.ExerciceSpinnerAdapter;
import com.e2p.myecf.adapters.GroupAdapter;
import com.e2p.myecf.adapters.StatementsExpandableAdapter;
import com.e2p.myecf.helpers.MySettings;
import com.e2p.myecf.helpers.Stub;
import com.e2p.myecf.models.Client;
import com.e2p.myecf.models.DashItem;
import com.e2p.myecf.models.Statement;
import com.e2p.myecf.models.StatementsGroup;
import com.e2p.myecf.retrofit.RetrofitClient;
import com.e2p.myecf.retrofit.RetrofitInterface;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatementsActivity extends AppCompatActivity {

    private static final String TAG = "STATEMENTS_ACTIVITTY";

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

    private ExpandableListView listView;
    private RecyclerView rvList;

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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.statements_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            loadeStatements();
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
            btnExercice_2 = (AppCompatButton) findViewById(R.id.btn_statements_filter_exercice_2);
            btnExercice_3 = (AppCompatButton) findViewById(R.id.btn_statements_filter_exercice_3);

            listView = (ExpandableListView) findViewById(R.id.lv_statements);
            rvList = (RecyclerView) findViewById(R.id.rv_statements);
            rvList.setHasFixedSize(true);
            rvList.setLayoutManager(new LinearLayoutManager(this));

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

            Calendar calendar = Calendar.getInstance();
            YEAR_0 = calendar.get(Calendar.YEAR);
            YEAR_1 = calendar.get(Calendar.YEAR) - 1;
            YEAR_2 = calendar.get(Calendar.YEAR) - 2;
            YEAR_3 = calendar.get(Calendar.YEAR) - 3;

            btnExercice_0.setText(YEAR_0 + "");
            btnExercice_1.setText(YEAR_1 + "");
            btnExercice_2.setText(YEAR_2 + "");
            btnExercice_3.setText(YEAR_3 + "");

            setExerciceFilter(YEAR_0);

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

            btnEmptyViewRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        loadeStatements();
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
                        setExerciceFilter(YEAR_0);
                        loadeStatements();
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
                        setExerciceFilter(YEAR_1);
                        loadeStatements();
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
                        setExerciceFilter(YEAR_2);
                        loadeStatements();
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
                        setExerciceFilter(YEAR_3);
                        loadeStatements();
                    } catch (Exception e) {
                        showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
                    } finally {
                    }
                }
            });


//            ClientSpinnerAdapter clientSpinnerAdapter = new ClientSpinnerAdapter(getApplicationContext(), ALL_CLIENTS);
//            spClient.setAdapter(clientSpinnerAdapter);


            listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                @Override
                public void onGroupExpand(int groupPosition) {
                    //showSnackbar(findViewById(android.R.id.content), listView.get(groupPosition) + " List Expanded.");
                }
            });

            listView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                @Override
                public void onGroupCollapse(int groupPosition) {
                    //showSnackbar(findViewById(android.R.id.content), listView.get(groupPosition) + " List Collapsed.");
                }
            });

            listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v,
                                            int groupPosition, int childPosition, long id) {
//                    Toast.makeText(
//                            getApplicationContext(),
//                            expandableListTitle.get(groupPosition)
//                                    + " -> "
//                                    + expandableListDetail.get(
//                                    expandableListTitle.get(groupPosition)).get(
//                                    childPosition), Toast.LENGTH_SHORT
//                    ).show();
                    return false;
                }
            });


            loadeStatements();

        } catch (Exception e) {
            Log.e(TAG, e.toString());
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
        }
    }

    private void setExerciceFilter(Integer exercice) {
        try {

            CURENT_EXERCICE = exercice;

            btnExercice_0.setTextColor(ResourcesCompat.getColor(getResources(), (exercice == YEAR_0)
                    ? R.color.white
                    : R.color.colorPrimaryDark, null));
            btnExercice_0.setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(), (exercice == YEAR_0)
                    ? R.drawable.selected_bg_bordered_button_end_radus
                    : R.drawable.normal_bg_bordered_button_end_radus, null));

            btnExercice_1.setTextColor(ResourcesCompat.getColor(getResources(), (exercice == YEAR_1)
                    ? R.color.white
                    : R.color.colorPrimaryDark, null));
            btnExercice_1.setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(), (exercice == YEAR_1)
                    ? R.drawable.selected_bg_bordered_button_no_radus
                    : R.drawable.normal_bg_bordered_button_no_radus, null));

            btnExercice_2.setTextColor(ResourcesCompat.getColor(getResources(), (exercice == YEAR_2)
                    ? R.color.white
                    : R.color.colorPrimaryDark, null));
            btnExercice_2.setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(), (exercice == YEAR_2)
                    ? R.drawable.selected_bg_bordered_button_no_radus
                    : R.drawable.normal_bg_bordered_button_no_radus, null));

            btnExercice_3.setTextColor(ResourcesCompat.getColor(getResources(), (exercice == YEAR_3)
                    ? R.color.white
                    : R.color.colorPrimaryDark, null));
            btnExercice_3.setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(), (exercice == YEAR_3)
                    ? R.drawable.selected_bg_bordered_button_start_radus
                    : R.drawable.normal_bg_bordered_button_start_radus, null));

        } catch (Exception e) {
            Log.e(TAG, e.toString());
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
        }
    }

    /**********************************************************************************************/

    private void loadeStatements() {

        progressView.setVisibility(View.VISIBLE);
        emptyListView.setVisibility(View.GONE);

        String URL = "Client/GetDeclarations?";
        RetrofitInterface service = RetrofitClient.getClientApi().create(RetrofitInterface.class);
        Call<ArrayList<Statement>> apiCall = service.getAllStatementsQuery(URL, 1, SELECTED_CLIENT.getId(), CURENT_EXERCICE, CURENT_EXERCICE);

        apiCall.enqueue(new Callback<ArrayList<Statement>>() {
            @Override
            public void onResponse(Call<ArrayList<Statement>> call, Response<ArrayList<Statement>> response) {
                progressView.setVisibility(View.GONE);
                if (response.raw().code() == 200) {

                    ALL_STATEMENTS = response.body();
//                    StatementsExpandableAdapter _Adapter = new StatementsExpandableAdapter(getApplicationContext(), ALL_STATEMENTS);
//                    listView.setAdapter(_Adapter);

                    ArrayList<StatementsGroup> _Groups = new ArrayList<>();
                    StatementsGroup _Group = new StatementsGroup();
                    String _LastGrpCode = "";

                    for (Statement _Item : ALL_STATEMENTS) {

                        if (!_Item.getGrpCode().equals(_LastGrpCode)) {
                            if (_Group.getStatements().size() > 0) {
                                _Groups.add(_Group);
                            }
                            _LastGrpCode = _Item.getGrpCode();
                            _Group = new StatementsGroup(_Item.getOrdre(), _Item.getGrpCode(), _Item.getGrpName(), false);
                        }
                        _Group.getStatements().add(_Item);
                    }

                    if (_Group.getStatements().size() > 0) {
                        _Groups.add(_Group);
                    }

                    GroupAdapter _GroupAdapter = new GroupAdapter(_Groups);
                    rvList.setAdapter(_GroupAdapter);

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