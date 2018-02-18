package se.oandell.riksdagen.ReadModule;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import se.oandell.riksdagen.R;

/**
 * Created by Oscar on 2017-05-15.
 * Activity for displaying full documents in a webwiew.
 */

public class ReadActivity extends AppCompatActivity {

    ImageView toolbarIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        Intent myIntent = getIntent();
        String url = myIntent.getStringExtra("url");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarIcon = (ImageView) findViewById(R.id.toolbar_icon);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(myIntent.getStringExtra("docType"));

        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setBuiltInZoomControls(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        }
        webView.loadUrl(url);
        setToolbarIcon();

    }

    public void setToolbarIcon() {
        this.toolbarIcon.setImageDrawable(getResources().getDrawable(getIntent().getIntExtra("bannerImage", R.drawable.topbanner)));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) finish();
        return true;
    }
}
