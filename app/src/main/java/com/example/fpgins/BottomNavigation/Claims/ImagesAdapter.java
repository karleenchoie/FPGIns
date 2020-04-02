package com.example.fpgins.BottomNavigation.Claims;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.fpgins.DataModel.Images;
import com.example.fpgins.DataModel.UserData;
import com.example.fpgins.R;
import com.example.fpgins.SQLiteDB.DBHelper;
import com.example.fpgins.Utility.DefaultDialog;

import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {

    private List<Images> mData;
    private Context mContext;
    private Images mImageData;


    public ImagesAdapter (List<Images> list, Context context){
        mData = list;
        mContext = context;
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
                .load(mImageData.getBitmap())
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


    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ProgressBar progressBar;
        public ImageView imgView;
        public TextView imgName;
        public TextView imgDateTaken;
        public ImageView imgEditTitle;
        public ImageView imgDeleteImage;
        public UserData userData;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userData = new UserData(PreferenceManager.getDefaultSharedPreferences(mContext));

            progressBar = (ProgressBar) itemView.findViewById(R.id.imageUploadProgressBar);
            imgView = (ImageView) itemView.findViewById(R.id.imageUploaded);
            imgName = (TextView) itemView.findViewById(R.id.imageTitle);
            imgDateTaken = (TextView) itemView.findViewById(R.id.dateUploaded);
            imgEditTitle = (ImageView) itemView.findViewById(R.id.imgEditTitle);
            imgDeleteImage = (ImageView) itemView.findViewById(R.id.imgDeleteImage);

            imgView.setOnClickListener(this);
            imgEditTitle.setOnClickListener(this);
            imgDeleteImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final Images img = mData.get(getLayoutPosition());

            if (v.getId() == R.id.imgEditTitle){

                new DefaultDialog.Builder(mContext)
                        .message("Edit image name")
                        .detail("Modifying image name must be descriptive words that corresponds the picture")
                        .editText("Enter image name", View.VISIBLE)
                        .negativeAction(R.string.cancel, new DefaultDialog.OnClickListener() {
                            @Override
                            public void onClick(Dialog dialog, String et) {
                                dialog.dismiss();
                            }
                        })
                        .positiveAction(R.string.confirm, new DefaultDialog.OnClickListener() {
                            @Override
                            public void onClick(Dialog dialog, String et) {
                                if (et.isEmpty()){
                                    Toast.makeText(mContext, "Please provide name", Toast.LENGTH_SHORT).show();
                                } else {
                                    DBHelper dbHelper = new DBHelper(mContext);
                                    dbHelper.open();
                                    boolean isUpdated = dbHelper.updateRow(img.getId(), et);

                                    if (isUpdated){
                                        Toast.makeText(mContext, "Image name successfully updated", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(mContext, "Image name not updated", Toast.LENGTH_LONG).show();
                                    }
                                    ((ClaimsActivity) mContext).getImages(img.getCustCode());
                                    dialog.dismiss();
                                }
                            }
                        })
                        .build()
                        .show();

            } else if (v.getId() == R.id.imgDeleteImage){
                new DefaultDialog.Builder(mContext)
                        .message("Are you sure you want to delete image?")
                        .detail("Deleting this image cannot restore back.")
                        .negativeAction("Cancel", new DefaultDialog.OnClickListener() {
                            @Override
                            public void onClick(Dialog dialog, String et) {
                                dialog.dismiss();
                            }
                        })
                        .positiveAction("Ok", new DefaultDialog.OnClickListener() {
                            @Override
                            public void onClick(Dialog dialog, String et) {
                                DBHelper dbHelper = new DBHelper(mContext);
                                dbHelper.open();
                                boolean isDeleted = dbHelper.deleteRow(img.getId(), img.getImageName());
                                if (isDeleted){
                                    Toast.makeText(mContext, "Image deleted", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(mContext, "Image not deleted", Toast.LENGTH_LONG).show();
                                }
                                ((ClaimsActivity) mContext).getImages(img.getCustCode());
                                dialog.dismiss();
                                notifyDataSetChanged();
                            }
                        })
                        .build()
                        .show();
            } else {
                final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                LayoutInflater inflater = LayoutInflater.from(mContext);
                View dialogView = inflater.inflate(R.layout.image_viewing, null);
                ImageView img1 = (ImageView) dialogView.findViewById(R.id.picture);
                TextView title = (TextView) dialogView.findViewById(R.id.txtFirst);
                ImageView close = dialogView.findViewById(R.id.img_close);
                title.setText(img.getImageName());
                Glide.with(mContext)
                        .asBitmap()
                        .load(img.getBitmap())
                        .into(img1);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (alertDialog!=null && alertDialog.isShowing()){
                            alertDialog.dismiss();
                        }
                    }
                });
                alertDialog.setView(dialogView);
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

}
