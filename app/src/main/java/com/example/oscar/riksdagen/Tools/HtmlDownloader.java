package com.example.oscar.riksdagen.Tools;

import android.os.AsyncTask;
import android.os.Build;
import android.text.Html;

import com.example.oscar.riksdagen.MainModule.ListItem;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


/**
 * Created by Oscar on 2017-03-26.
 */

public class HtmlDownloader extends AsyncTask<String, String,Document> {

    private ListItem listItem;
    private String url;

    public HtmlDownloader(ListItem listitem, String url){
        this.listItem = listitem;
        this.url = url;
    }

    @Override
    protected Document doInBackground(String... strings) {
        return download();
    }

    @Override
    protected void onPostExecute(Document result){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            listItem.setTitle(listItem.getTitle() + Html.fromHtml(result.getElementsByTag("h2").html() +"<br>"+ result.getElementsByClass("av").html() +"<br>"+ result.getElementsByClass("till").html(),Html.FROM_HTML_MODE_COMPACT).toString());
            result.getElementsByTag("h2").remove();
            result.getElementsByClass("av").remove();
            result.getElementsByClass("till").remove();
            result.getElementsByTag("style").remove();
            listItem.setText(Html.fromHtml(result.html(),Html.FROM_HTML_OPTION_USE_CSS_COLORS ).toString());
    }
    }

    private Document download() {
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                    .timeout(10000)
                    .get();
            return doc;
        } catch (Exception e) {
            return null;
        }
    }
}
