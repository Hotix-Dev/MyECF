package com.e2p.myecf.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e2p.myecf.R;
import com.e2p.myecf.models.Statement;
import com.e2p.myecf.models.StatementsGroup;

import java.util.ArrayList;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ItemViewHolder> {

    private ArrayList<StatementsGroup>mList;
    private ArrayList<Statement> list = new ArrayList<>();

    public GroupAdapter(ArrayList<StatementsGroup> mList){
        this.mList  = mList;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_row , parent , false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        StatementsGroup model = mList.get(position);
        holder.tvTitle.setText(model.getGroupName());

        boolean isExpandable = model.getExpandable();
        holder.rlExpandable.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

        if (isExpandable){
            holder.ivArrow.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
        }else{
            holder.ivArrow.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
        }

        ChildAdapter adapter = new ChildAdapter(list);
        holder.rvChildren.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.rvChildren.setHasFixedSize(true);
        holder.rvChildren.setAdapter(adapter);
        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setExpandable(!model.getExpandable());
                list = model.getStatements();
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private LinearLayoutCompat llMain;
        private RelativeLayout rlExpandable;
        private AppCompatTextView tvTitle;
        private AppCompatImageView ivArrow;
        private RecyclerView rvChildren;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            llMain = itemView.findViewById(R.id.ll_item_group);
            rlExpandable = itemView.findViewById(R.id.rl_item_group_expand);
            tvTitle = itemView.findViewById(R.id.tv_item_group);
            ivArrow = itemView.findViewById(R.id.iv_item_group);
            rvChildren = itemView.findViewById(R.id.rv_item_group);
        }
    }
}