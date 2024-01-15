package com.e2p.myecf.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import static com.e2p.myecf.helpers.ConstantConfig.SELECTED_CLIENT;

import com.e2p.myecf.R;
import com.e2p.myecf.models.Client;
import com.e2p.myecf.models.Statement;
import com.e2p.myecf.models.StatementsGroup;

import java.util.ArrayList;

public class ClientsAdapter extends RecyclerView.Adapter<ClientsAdapter.ItemViewHolder> {

    private ArrayList<Client> mList;
    private Context mContext;

    public ClientsAdapter(Context context, ArrayList<Client> mList){
        this.mContext  = context;
        this.mList  = mList;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_client_row , parent , false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        Client model = mList.get(position);
        holder.tvTitle.setText(model.getName());

        holder.rlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SELECTED_CLIENT = model;
                notifyItemChanged(holder.getAdapterPosition());
                ((AppCompatActivity)mContext).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout rlMain;
        private AppCompatTextView tvTitle;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            rlMain = itemView.findViewById(R.id.rl_item_client_main);
            tvTitle = itemView.findViewById(R.id.tv_client_title);
        }
    }
}