package com.example.oscar.riksdagen.Tools;

import android.os.AsyncTask;
import com.example.oscar.riksdagen.MainModule.ListItem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;



/**
 * Created by Oscar on 2017-06-03.
 * Finds the reply to written question be searching for the title, using doctype=frs as a filter
 * Input : ListItem to display reply
 */

public class ReplyFinder extends AsyncTask<String,String,String> {

    private String apiQuery = "http://data.riksdagen.se/dokumentlista/?sok=";
    private String docType = "&doktyp=frs";
    private String searchTerm;
    private ListItem listItem;

    public ReplyFinder(String searchTerm,  ListItem listItem){
        this.searchTerm = searchTerm;
        this.listItem = listItem;
    }

    @Override
    protected String doInBackground(String... strings) {
        return search();
    }

    @Override
    protected void onPostExecute(String results){
        if(results != null){
            new HtmlDownloader(listItem,results).execute();
        }
        else listItem.setText("(ej besvarad)");
    }

    private String search(){
        try {
            Document doc = Jsoup.connect(apiQuery+searchTerm.trim()+docType)
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                    .timeout(10000)
                    .get();
            return doc.getElementsByTag("dokument_url_html").get(0).text();
        } catch (Exception e) {
            return null;
        }
    }
}
