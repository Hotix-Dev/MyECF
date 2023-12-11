package com.e2p.myecf.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import com.e2p.myecf.R;
import com.e2p.myecf.models.Statement;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class StatementsExpandableAdapter extends BaseExpandableListAdapter {

    private Context _context;

    private ArrayList<Statement> dataSet;

    private ArrayList<Statement> parentItems;
    private ArrayList<ArrayList<Statement>> childItems;

    public StatementsExpandableAdapter(Context context, ArrayList<Statement> data) {
        this._context = context;
        this.dataSet = data;

        parentItems = new ArrayList<Statement>();
        childItems = new ArrayList<ArrayList<Statement>>();

        for (Statement _Item : dataSet) {
            boolean _Add = true;

            for (Statement _Statement : parentItems) {
                if (_Statement.getGrpCode().equals(_Item.getGrpCode())) {
                    _Add = false;;
                }
            }

            if (_Add) {
                parentItems.add(_Item);
            }
        }

        for (Statement _Parent : parentItems) {

            ArrayList<Statement> childs = new ArrayList<Statement>();

            for (Statement _Child : dataSet) {
                if (_Child.getGrpCode().equals(_Parent.getGrpCode())) {
                    childs.add(_Child);
                }
            }
            childItems.add(childs);
        }
    }

    @Override
    public int getGroupCount() {
        return parentItems.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return (childItems.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return parentItems.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childItems.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return parentItems.get(groupPosition).getOrdre();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        ViewHolderParent viewHolderParent;

        if (convertView == null) {

            viewHolderParent = new ViewHolderParent();

            LayoutInflater inflater = LayoutInflater.from(_context);
            convertView = inflater.inflate(R.layout.item_statement_group_row, parent, false);

            viewHolderParent.statement_group_title = (AppCompatTextView) convertView.findViewById(R.id.tv_statement_group_title);

            Statement _Statement = parentItems.get(groupPosition);

            viewHolderParent.statement_group_title.setText(_Statement.getGrpName());

        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderChild viewHolderChild;
        if (convertView == null) {

            viewHolderChild = new ViewHolderChild();

            LayoutInflater inflater = LayoutInflater.from(_context);
            convertView = inflater.inflate(R.layout.item_statement_row, parent, false);

            viewHolderChild.statement_title = (AppCompatTextView) convertView.findViewById(R.id.tv_statement_title);
            viewHolderChild.statement_data = (AppCompatTextView) convertView.findViewById(R.id.tv_statement_data);
            viewHolderChild.statement_total = (AppCompatTextView) convertView.findViewById(R.id.tv_statement_total);

            Statement _Statement = childItems.get(groupPosition).get(childPosition);

            viewHolderChild.statement_title.setText(_Statement.getLibelle1());
            viewHolderChild.statement_data.setText(_Statement.getLibelle2());
            //viewHolderChild.statement_total.setText(_Statement.getColumn6() + "");
            viewHolderChild.statement_total.setText("");

        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    private class ViewHolderParent {
        AppCompatTextView statement_group_title;
    }

    private class ViewHolderChild {

        AppCompatTextView statement_title;
        AppCompatTextView statement_total;
        AppCompatTextView statement_data;
    }

}
