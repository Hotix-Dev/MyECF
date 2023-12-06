package com.e2p.myecf.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.appcompat.widget.AppCompatTextView;

import com.e2p.myecf.R;
import com.e2p.myecf.models.Client;
import com.e2p.myecf.models.Exercice;

import java.util.ArrayList;

public class ClientSpinnerAdapter extends BaseAdapter {

    Context context;
    ArrayList<Client> clients = new ArrayList<>() ;
    LayoutInflater inflter;

    public ClientSpinnerAdapter(Context applicationContext, ArrayList<Client> exercices) {
        this.context = applicationContext;
        this.clients = exercices;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return clients.size();
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
        names.setText(clients.get(i).getName());
        return view;
    }
}
