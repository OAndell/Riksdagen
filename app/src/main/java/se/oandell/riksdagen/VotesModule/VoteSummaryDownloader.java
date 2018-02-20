package se.oandell.riksdagen.VotesModule;

import android.os.AsyncTask;
import android.os.Build;
import android.text.Html;
import android.widget.TextView;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by Oscar on 2017-05-16.
 */

public class VoteSummaryDownloader extends AsyncTask<String,String,String> {

    private String apiQuery = "http://data.riksdagen.se/dokumentlista/?sok=";
    private String searchTerm;
    private TextView textView;
    private String docUrl;

    public VoteSummaryDownloader(String searchTerm,  TextView textView){
        this.searchTerm = searchTerm;
        this.textView = textView;
    }

    @Override
    protected String doInBackground(String... strings) {
            return search();
    }

    @Override
    protected void onPostExecute(String results){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(results,Html.FROM_HTML_OPTION_USE_CSS_COLORS).toString());
        }
        else{
            textView.setText(Html.fromHtml(results).toString());
        }
    }

    private String search(){
        try {
            Document doc = Jsoup.connect(apiQuery+searchTerm)
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                    .timeout(10000)
                    .get();
            docUrl =  doc.getElementsByTag("dokument_url_html").get(0).text();
            VoteActivity.setFullTextUrl(docUrl);
            return doc.getElementsByTag("notis").get(0).text();
        } catch (IOException e) {
            return null;
        }
    }
}
