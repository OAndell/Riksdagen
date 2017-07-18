package com.example.oscar.riksdagen.ReadModule;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

import com.example.oscar.riksdagen.R;

/**
 * Created by Oscar on 2017-05-15.
 * Activity for displaying full documents in a webwiew.
 */

public class ReadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        Intent myIntent = getIntent();
        String url = myIntent.getStringExtra("url");

        ImageView bannerImage = (ImageView) findViewById(R.id.bannerImage);
        bannerImage.setImageResource(myIntent.getIntExtra("bannerImage",R.drawable.topbanner));

        //TODO fix window size
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setBuiltInZoomControls(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        }
        webView.loadUrl(url);

        //init "go back" button
        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }


}
