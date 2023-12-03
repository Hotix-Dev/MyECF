package com.e2p.myecf.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.appcompat.widget.AppCompatTextView;

import com.e2p.myecf.models.StatementType;
import com.e2p.myecf.R;

import java.util.ArrayList;

public class MyStatementsTypesAdapter extends ArrayAdapter<StatementType> {
    Context mContext;
    private ArrayList<StatementType> dataSet;

    public MyStatementsTypesAdapter(ArrayList<StatementType> data, Context context) {
        super(context, R.layout.item_data_obj_row, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        StatementType dataModel = getItem(position);

        MyStatementsTypesAdapter.ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new MyStatementsTypesAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_data_obj_row, parent, false);
            viewHolder.item_title = (AppCompatTextView) convertView.findViewById(R.id.tv_data_obj_row_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyStatementsTypesAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.item_title.setText(dataModel.getName());

        // Return the completed view to render on screen
        return convertView;
    }

    // View lookup cache
    private static class ViewHolder {
        AppCompatTextView item_title;
    }
}
