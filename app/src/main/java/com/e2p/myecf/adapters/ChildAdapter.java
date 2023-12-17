package com.e2p.myecf.adapters;

import static android.graphics.Color.parseColor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.e2p.myecf.R;
import com.e2p.myecf.helpers.Utils;
import com.e2p.myecf.models.Statement;

import java.util.ArrayList;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.NestedViewHolder> {

    private ArrayList<Statement> mList;

    public ChildAdapter(ArrayList<Statement> mList){
        this.mList = mList;
    }
    @NonNull
    @Override
    public NestedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_children_row , parent , false);
        return new NestedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NestedViewHolder holder, int position) {

        Statement _Statement = mList.get(position);
        holder.tvTitle.setText(_Statement.getLibelle1());
        holder.tvData.setText(_Statement.getLibelle2());
        holder.tvTotal.setText("");

        if (!Utils.stringEmptyOrNull(_Statement.getColor())) {
            holder.rl_color_l.setBackgroundColor(parseColor(_Statement.getColor()));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class NestedViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout rl_color_l;
        private RelativeLayout rl_color_r;
        private AppCompatTextView tvTitle;
        private AppCompatTextView tvData;
        private AppCompatTextView tvTotal;
        public NestedViewHolder(@NonNull View itemView) {
            super(itemView);
            rl_color_l = itemView.findViewById(R.id.rl_color_item_child_left);
            rl_color_r = itemView.findViewById(R.id.rl_color_item_child_right);
            tvTitle = itemView.findViewById(R.id.tv_item_child_title);
            tvData = itemView.findViewById(R.id.tv_item_child_data);
            tvTotal = itemView.findViewById(R.id.tv_item_child_total);
        }
    }
}