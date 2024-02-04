package com.e2p.myecf.adapters;

import static com.e2p.myecf.helpers.ConstantConfig.SELECTED_CLIENT;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.e2p.myecf.R;
import com.e2p.myecf.Utils.FileManager;
import com.e2p.myecf.models.Client;

import org.apache.commons.net.ftp.FTPFile;

import java.util.ArrayList;

public class FtpFilesAdapter extends RecyclerView.Adapter<FtpFilesAdapter.ItemViewHolder> {

    private ArrayList<FTPFile> mList;
    private Context mContext;

    public FtpFilesAdapter(Context context, ArrayList<FTPFile> mList) {
        this.mContext = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ftp_file_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        FTPFile model = mList.get(position);

        holder.imgIcon.setImageResource(getIconBasedOnFileType(model));

        holder.tvTitle.setText(model.getName());

        long _SizeKB = model.getSize() / 1024;
        long _SizeMB = _SizeKB / 1024;
        long _SizeGB = _SizeMB / 1024;

        if (_SizeKB < 1024) {
            holder.tvSize.setText(Math.toIntExact(_SizeKB) + " KB");
        } else if (_SizeMB < 1024) {
            holder.tvSize.setText(Math.toIntExact(_SizeMB) + " MB");
        } else {
            holder.tvSize.setText(Math.toIntExact(_SizeGB) + " GB");
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private int getIconBasedOnFileType(FTPFile file) {
        if (file.isDirectory()) {
            return R.drawable.ic_normal_white_grid_folder;
        } else {
            String fileName = file.getName();
            int i = fileName.lastIndexOf('.');
            if (i > 0) {
                String extension = fileName.substring(i + 1).toLowerCase();
                return FileManager.getIcon(extension);
            } else {
                return R.drawable.ic_normal_white_grid_unknown;
            }
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rlMain;
        private AppCompatImageView imgIcon;
        private AppCompatTextView tvTitle;
        private AppCompatTextView tvSize;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            rlMain = itemView.findViewById(R.id.rl_item_ftp_file_main);
            imgIcon = itemView.findViewById(R.id.img_ftp_file_icon);
            tvTitle = itemView.findViewById(R.id.tv_ftp_file_title);
            tvSize = itemView.findViewById(R.id.tv_ftp_file_size);
        }
    }
}