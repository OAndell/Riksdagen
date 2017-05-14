package com.example.oscar.riksdagen.Tools;

import android.os.AsyncTask;

import com.example.oscar.riksdagen.ListItem;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.rss.Item;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Oscar on 2017-03-26.
 */

public class HtmlDownloader extends AsyncTask<String, String, String> {

    private ListItem listItem;
    private String url;
    private static int maxTitleLength = 300;

    public HtmlDownloader(ListItem listitem, String url){
        this.listItem = listitem;
        this.url = url;
    }



    @Override
    protected String doInBackground(String... strings) {
        return connect();
    }

    @Override
    protected void onPostExecute(String result){
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
    }

    private String connect() {
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                    .timeout(10000)
                    .get();
            doc.text().replaceAll("\\<h2\\>[\\s\\S]*\\<\\/p\\>", "");
            doc.select("br").append("br2n");
            doc.select("p").prepend("br2n");
            doc.select("h2").append("");
            return Jsoup.parse(doc.html()).text().replaceAll("br2n","\n");

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
