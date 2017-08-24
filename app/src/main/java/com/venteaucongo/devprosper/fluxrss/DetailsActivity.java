package com.venteaucongo.devprosper.fluxrss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class DetailsActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        webView = (WebView)findViewById(R.id.webwiew);
        Bundle bundle = getIntent().getExtras();
        webView.loadUrl(bundle.getString("Link"));
    }
}
