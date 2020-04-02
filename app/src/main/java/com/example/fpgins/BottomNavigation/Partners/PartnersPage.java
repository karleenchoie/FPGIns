package com.example.fpgins.BottomNavigation.Partners;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.PagerAdapter;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import com.example.fpgins.R;
import com.wang.avi.AVLoadingIndicatorView;

public class PartnersPage extends AppCompatActivity {

    private WebView mWebView;
    private SwipeRefreshLayout mSwipeRefresh;
    private ImageView mBackButton;
    private String urlWeb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partners_page);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            urlWeb = bundle.getString("url");
        }

        mWebView = findViewById(R.id.wv_partners);
        mSwipeRefresh = findViewById(R.id.sswipreRefresh);
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.setWebViewClient(new displayWebPage());

        mWebView.loadUrl(urlWeb);

        mBackButton = findViewById(R.id.img_backbutton);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWebView.loadUrl(urlWeb);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private class displayWebPage extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url){
            mSwipeRefresh.setRefreshing(false);
            urlWeb = url;
            super.onPageFinished(view, urlWeb);
        }
    }
}
