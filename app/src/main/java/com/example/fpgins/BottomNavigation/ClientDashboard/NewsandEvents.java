package com.example.fpgins.BottomNavigation.ClientDashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fpgins.R;
import com.example.fpgins.ui.NotificationMessage.NotifImageAdapter;
import com.example.fpgins.ui.NotificationMessage.NotifMessage;

import java.util.ArrayList;

public class NewsandEvents extends AppCompatActivity {

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<String> ImagesArray = new ArrayList<String>();
    private ImageView mBackButton;
    private ImageView mPrevious, mNext;
    private TextView mTitle;
    private TextView mDate;
    private TextView mTime;
    private TextView mContent;
    private String mLink;

    private RelativeLayout slideshowImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsand_events);

        mPager = findViewById(R.id.vp_Notif);
        mBackButton = findViewById(R.id.img_backbutton);
        mPrevious = findViewById(R.id.img_previous);
        mNext = findViewById(R.id.img_next);
        mTitle = findViewById(R.id.title);
        mDate = findViewById(R.id.date);
        mTime = findViewById(R.id.time);
        mContent = findViewById(R.id.content);

        slideshowImages = findViewById(R.id.slideshowPicture);


        Bundle bundle = getIntent().getExtras();

        if (bundle != null){
            mTitle.setText(bundle.getString("title"));
            mDate.setText(bundle.getString("date"));
            mTime.setText(bundle.getString("time"));
            mContent.setText(bundle.getString("content"));
            mLink = bundle.getString("link");
            ImagesArray = bundle.getStringArrayList("pictures");

            if (ImagesArray.size() == 0){
                slideshowImages.setVisibility(View.GONE);
            } else {
                slideshowImages.setVisibility(View.VISIBLE);
                init();
            }
        }

        mPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = mPager.getCurrentItem();
                if (tab > 0){
                    tab--;
                    mPager.setCurrentItem(tab);
                }else if (tab == 0){
                    mPager.setCurrentItem(tab);
                }
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = mPager.getCurrentItem();
                tab++;
                mPager.setCurrentItem(tab);
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void init() {
        mPager.setAdapter(new NotifImageAdapter(ImagesArray, NewsandEvents.this));
        NUM_PAGES = ImagesArray.size();

    }
}
