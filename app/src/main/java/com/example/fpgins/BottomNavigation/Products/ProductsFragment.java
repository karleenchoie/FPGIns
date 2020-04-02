package com.example.fpgins.BottomNavigation.Products;


import android.media.Image;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.fpgins.BottomNavigation.Claims.MainClaimsAdapter;
import com.example.fpgins.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends AppCompatActivity {

    private WebView mWebView;
    private String string = "https://www.fpgins.com/ph/products";

    private String motor = "https://www.fpgins.com/ph/products/motor";
    private String travel = "https://www.fpgins.com/ph/products/travel";
    private String pa = "https://www.fpgins.com/ph/products/personal-accident";
    private String home = "https://www.fpgins.com/ph/products/home/home-insurance";
    private String corpo = "https://www.fpgins.com/ph/corporate/corporate-products";
    private SwipeRefreshLayout mSwipeRefresh;

    private MainClaimsAdapter mainClaimsAdapter;
    private ImageView mBackButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_products);

        mWebView = findViewById(R.id.webview);
        mSwipeRefresh = findViewById(R.id.sswipreRefresh);
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.loadUrl(mainClaimsAdapter.urlWeb);
        mWebView.setWebViewClient(new displayWebPage());
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
                mWebView.loadUrl(mainClaimsAdapter.urlWeb);
            }
        });
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
            mainClaimsAdapter.urlWeb = url;
            super.onPageFinished(view, mainClaimsAdapter.urlWeb);
        }
    }

}
