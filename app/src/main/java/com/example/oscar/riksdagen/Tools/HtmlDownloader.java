package com.example.oscar.riksdagen.Tools;

import android.os.AsyncTask;
import android.os.Build;
import android.text.Html;

import com.example.oscar.riksdagen.ListItem;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by Oscar on 2017-03-26.
 */

public class HtmlDownloader extends AsyncTask<String, String,Document> {

    private ListItem listItem;
    private String url;
    private static int maxTitleLength = 300;

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
        //TODO but till webview?
        /*
        listItem.setText(result);
        String[] resultSplit = result.split("(?m)^\\s*$"); //Split text on blank line
        if(resultSplit[0].length() > maxTitleLength){
            listItem.setText(result);
        }
        else{
            listItem.setTitle(resultSplit[0]); //Cut out doc title text
            String textBody = "";
            for (int i = 1; i < resultSplit.length; i++) { //Add together text with title excluded
                textBody = textBody + resultSplit[i];
            }
            listItem.setText(textBody);
        }
        */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            listItem.setTitle(Html.fromHtml(result.getElementsByTag("h2").html() +"<br>"+ result.getElementsByClass("av").html() +"<br>"+ result.getElementsByClass("till").html(),Html.FROM_HTML_MODE_COMPACT).toString());
            result.getElementsByTag("h2").remove();
            result.getElementsByClass("av").remove();
            result.getElementsByClass("till").remove();
            result.getElementsByTag("style").remove();
            listItem.setText(Html.fromHtml(result.html(),Html.FROM_HTML_MODE_COMPACT).toString());
        }
    }

    private Document download() {
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                    .timeout(10000)
                    .get();
               /*doc.text().replaceAll("\\<h2\\>[\\s\\S]*\\<\\/p\\>", "");
               doc.select("br").append("br2n");
               doc.select("p").prepend("br2n");
               doc.select("h2").append("");
               return Jsoup.parse(doc.html()).text().replaceAll("br2n","\n");*/
                return doc;


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
