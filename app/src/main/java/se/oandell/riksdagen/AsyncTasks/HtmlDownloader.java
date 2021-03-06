package se.oandell.riksdagen.AsyncTasks;

import android.os.AsyncTask;
import android.os.Build;
import android.text.Html;

import se.oandell.riksdagen.MainModule.ContentContainer;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URI;


/**
 * Downloads a text in html format and display the text in specified ContentContainer.
 * Created by Oscar on 2017-03-26.
 */
public class HtmlDownloader extends AsyncTask<String, String,Document> {

    private ContentContainer contentContainer;
    private String url;

    public HtmlDownloader(ContentContainer listitem, String url){
        this.contentContainer = listitem;
        this.url = url;
    }

    @Override
    protected Document doInBackground(String... strings) {
        return download();
    }

    @Override
    protected void onPostExecute(Document result){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            contentContainer.setTitle(contentContainer.getTitle() + Html.fromHtml(result.getElementsByTag("h2").html() +"<br>"+ result.getElementsByClass("av").html() +"<br>"+ result.getElementsByClass("till").html(),Html.FROM_HTML_MODE_COMPACT).toString());
        }
        else {
            contentContainer.setTitle(contentContainer.getTitle() + Html.fromHtml(result.getElementsByTag("h2").html() +"<br>"+ result.getElementsByClass("av").html() +"<br>"+ result.getElementsByClass("till").html()).toString());

        }
        result.getElementsByTag("h2").remove();
        result.getElementsByClass("av").remove();
        result.getElementsByClass("till").remove();
        result.getElementsByTag("style").remove();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            contentContainer.setText(Html.fromHtml(result.html(),Html.FROM_HTML_OPTION_USE_CSS_COLORS ).toString().trim());
        }
        else {
            contentContainer.setText(Html.fromHtml(result.html()).toString().trim());
        }

    }

    private Document download() {
        try {
            Document doc = Jsoup.connect(URI.create(url).toASCIIString())
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                    .timeout(10000)
                    .get();
            return doc;
        } catch (Exception e) {
            return null;
        }
    }
}
