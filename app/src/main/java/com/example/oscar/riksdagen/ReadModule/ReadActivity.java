package com.example.oscar.riksdagen.ReadModule;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.example.oscar.riksdagen.R;

/**
 * Created by Oscar on 2017-05-15.
 */

public class ReadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        Intent myIntent = getIntent();
        String url = myIntent.getStringExtra("url");
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl(url);
        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
