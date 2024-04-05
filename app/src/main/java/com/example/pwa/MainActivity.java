package com.example.pwa;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private WebView webview;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Prevents the app of being closed if the return button is pressed
        // and the page can go back
        WebView webView = findViewById(R.id.webView);
        if(keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get web view component
        WebView webView = findViewById(R.id.webView);
        // Set website url
        webView.loadUrl("https://douglasvolcato.github.io/pokedex");
        // Enable JavaScript execution
        webView.getSettings().setJavaScriptEnabled(true);
        // Makes it open the url inside the app, not in the browser
        webView.setWebViewClient(new WebViewClient());

        // Override methods to make the progressbar visible on page loading
        final ProgressBar progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.INVISIBLE);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.INVISIBLE);
            }

            // This method can only be executed after this version
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (request.getUrl().toString().contains("douglasvolcato")) {
                    return false;
                }

                // Tries to open url if it has a specific app installed, like youtube
                Intent intent = new Intent(Intent.ACTION_VIEW, request.getUrl());
                startActivity(intent);

                return true;
            }
        });
    }
}