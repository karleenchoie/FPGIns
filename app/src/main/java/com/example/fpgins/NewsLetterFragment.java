package com.example.fpgins;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.fpgins.ui.NotificationMessage.NotifMessage;

public class NewsLetterFragment extends DialogFragment {

    //karleennnnnnnnnnnnnn

    private ImageButton mImageButtonClose;
    private RoundedCornerImageView mImageButtonCoverBanner;
    private TextView mViewMore;
    private TextView mNews;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_letter, container, true);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        setCancelable(false);

        mImageButtonClose = view.findViewById(R.id.imageButtonClose);
        mImageButtonCoverBanner = view.findViewById(R.id.imageButtonCoverBanner);
        mNews = view.findViewById(R.id.information);
        mViewMore = view.findViewById(R.id.btn_viewMore);

        mImageButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NotifMessage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                dismiss();
            }
        });
        return view;
    }
}
