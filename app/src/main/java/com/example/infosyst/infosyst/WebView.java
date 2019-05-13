package com.example.infosyst.infosyst;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WebView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        View webView = (android.webkit.WebView)findViewById(R.id.webView);
        ((android.webkit.WebView) webView).getSettings().setJavaScriptEnabled(true);
        ((android.webkit.WebView) webView).loadUrl("http://infopower.sytes.net:8061/");

    }
}
