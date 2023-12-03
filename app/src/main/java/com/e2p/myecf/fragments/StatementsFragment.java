package com.e2p.myecf.fragments;

import static com.e2p.myecf.helpers.Utils.showSnackbar;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.e2p.myecf.R;
import com.e2p.myecf.adapters.ExerciceSpinnerAdapter;
import com.e2p.myecf.adapters.MyStatementsTypesAdapter;
import com.e2p.myecf.helpers.MySettings;
import com.e2p.myecf.helpers.Stub;
import com.google.android.material.navigation.NavigationView;

public class StatementsFragment extends Fragment {
    private static final String TAG = "STATEMENTS_FRAGMENT";
    private AppCompatSpinner spExercice;
    private ListView lvTypes;

    private MySettings mySettings;

    public StatementsFragment() {
        // Required empty public constructor
    }

    public static StatementsFragment newInstance() {
        StatementsFragment fragment = new StatementsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statements, container, false);

        bindViews(view);
        init();

        return view;
    }

    /***************************************************************************************************/

    private void bindViews(View view) {
        spExercice = (AppCompatSpinner) view.findViewById(R.id.sp_statement_exercice);
        lvTypes = (ListView) view.findViewById(R.id.lv_statement_types);
    }

    private void init() {

        ExerciceSpinnerAdapter exerciceSpinnerAdapter = new ExerciceSpinnerAdapter(getActivity(), Stub.getExercicesList());
        spExercice.setAdapter(exerciceSpinnerAdapter);

        MyStatementsTypesAdapter mChannelsAdapter = new MyStatementsTypesAdapter(Stub.getStatementTypesList(), getActivity());
        lvTypes.setAdapter(mChannelsAdapter);

        lvTypes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {

            }
        });
    }

    /***************************************************************************************************/
}