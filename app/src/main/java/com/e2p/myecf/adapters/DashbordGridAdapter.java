package com.e2p.myecf.adapters;

import static com.e2p.myecf.helpers.Utils.getImage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.e2p.myecf.R;
import com.e2p.myecf.models.DashItem;

import java.util.ArrayList;

public class DashbordGridAdapter extends BaseAdapter {

    private ArrayList<DashItem> mItems;
    private Context mContext;

    public DashbordGridAdapter(Context context, ArrayList<DashItem> items) {
        mContext = context;
        this.mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View grid;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (convertView == null) {
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.item_dashbord_grid_sell, null);
        } else {
            grid = (View) convertView;
        }

        AppCompatImageView imgIcon = (AppCompatImageView) grid.findViewById(R.id.img_grid_sell_icon);
        AppCompatTextView tvTitle = (AppCompatTextView) grid.findViewById(R.id.tv_grid_sell_title);

        try {
            Glide.with(mContext)
                    .load(getImage(mContext, mItems.get(position).getImage()))
                    .fitCenter()
                    .error(R.drawable.logo)
                    .placeholder(R.drawable.loading_animation)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgIcon);

        } catch (Exception e) {
        }
        tvTitle.setText(mItems.get(position).getName());

        return grid;
    }
}
