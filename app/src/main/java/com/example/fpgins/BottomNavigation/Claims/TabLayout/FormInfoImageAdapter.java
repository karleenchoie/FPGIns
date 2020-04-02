package com.example.fpgins.BottomNavigation.Claims.TabLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.fpgins.DataModel.Images;
import com.example.fpgins.R;

import java.util.List;

public class FormInfoImageAdapter extends RecyclerView.Adapter<FormInfoImageAdapter.ViewHolder> {

    private List<Images> mData;
    private Context mContext;
    private Images mImageData, mEdit, mSend;

    public FormInfoImageAdapter (List<Images> list, Context context){
        mData = list;
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ProgressBar progressBar;
        public ImageView imgView;
        public TextView imgName;
        public TextView imgDateTaken;
        public ImageView imgEdit, imgDelete;
        private AlertDialog alertDialog;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            progressBar = (ProgressBar) itemView.findViewById(R.id.imageUploadProgressBar);
            imgView = (ImageView) itemView.findViewById(R.id.imageUploaded);
            imgName = (TextView) itemView.findViewById(R.id.imageTitle);
            imgDateTaken = (TextView) itemView.findViewById(R.id.dateUploaded);
            imgEdit =  itemView.findViewById(R.id.imgEditTitle);
            imgDelete = itemView.findViewById(R.id.imgDeleteImage);

            imgView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final Images img = mData.get(getLayoutPosition());
            final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                LayoutInflater inflater = LayoutInflater.from(mContext);
                final View dialogView = inflater.inflate(R.layout.image_viewing, null);
                ImageView img1 = dialogView.findViewById(R.id.picture);
                TextView title = dialogView.findViewById(R.id.txtFirst);
                ImageView close = dialogView.findViewById(R.id.img_close);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (alertDialog!=null && alertDialog.isShowing()){
                            alertDialog.dismiss();
                        }
                    }
                });
                title.setText(img.getImageName());
                Glide.with(mContext)
                        .asBitmap()
                        .placeholder(R.drawable.default_image)
                        .load(img.getId())
                        .into(img1);
                alertDialog.setView(dialogView);
                alertDialog.setCancelable(false);
                alertDialog.show();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_file, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        mImageData = mData.get(position);

        Glide.with(mContext)
                .asBitmap()
                .placeholder(R.drawable.default_image)
                .load(mImageData.getId())
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.imgView);

        holder.imgName.setText(mImageData.getImageName());
        holder.imgDateTaken.setText(mImageData.getDateTaken());
        holder.imgDelete.setVisibility(View.GONE);
        holder.imgEdit.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



}
