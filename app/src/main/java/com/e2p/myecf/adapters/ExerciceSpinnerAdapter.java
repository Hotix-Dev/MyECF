package com.e2p.myecf.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.appcompat.widget.AppCompatTextView;

import com.e2p.myecf.models.Exercice;
import com.e2p.myecf.R;

import java.util.ArrayList;

public class ExerciceSpinnerAdapter extends BaseAdapter {

    Context context;
    ArrayList<Exercice> exercices = new ArrayList<>() ;
    LayoutInflater inflter;

    public ExerciceSpinnerAdapter(Context applicationContext, ArrayList<Exercice> exercices) {
        this.context = applicationContext;
        this.exercices = exercices;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return exercices.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.spinner_simple_row_item, null);
        AppCompatTextView names = (AppCompatTextView) view.findViewById(R.id.spinner_simple_row_tv);
        names.setText(exercices.get(i).getName());
        return view;
    }
}
