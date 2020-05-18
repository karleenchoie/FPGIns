package com.example.fpgins.BottomNavigation.Products;


import android.media.Image;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.KeyEvent;
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
    private SwipeRefreshLayout mSwipeRefresh;

    private MainClaimsAdapter mainClaimsAdapter;
    private ImageView mBackButton;
    private String urlWeb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_products);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            urlWeb = bundle.getString("product_url");
        }

        mWebView = findViewById(R.id.webview);
        mSwipeRefresh = findViewById(R.id.sswipreRefresh);

        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.loadUrl(urlWeb);
        mWebView.setWebViewClient(new displayWebPage());

        mWebView.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if(event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    WebView webView = (WebView) v;
                    switch(keyCode)
                    {
                        case KeyEvent.KEYCODE_BACK:
                            if(webView.canGoBack())
                            {
                                webView.goBack();
                                return true;
                            }
                            break;
                    }
                }
                return false;
            }
        });

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
