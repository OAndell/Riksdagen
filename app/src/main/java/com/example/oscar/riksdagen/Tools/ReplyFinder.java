package com.example.oscar.riksdagen.Tools;

import android.os.AsyncTask;
import com.example.oscar.riksdagen.MainModule.ContentContainer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by Oscar on 2017-06-03.
 * Finds the reply to written question be searching for the title, using doctype=frs as a filter
 * Input : ContentContainer to display reply
 */

public class ReplyFinder extends AsyncTask<String,String,String> {

    private String apiQuery1 = "http://data.riksdagen.se/dokumentlista/?sok=";
    private String apiQuery2 = "&doktyp=frs&bet=";
    private String apiQuery3 = "&sort=datum&sortorder=desc&rapport=&utformat=xml&a=s#soktraff";

    private String searchTerm;
    private String id;
    private ContentContainer contentContainer;

    public ReplyFinder(String title, String id, ContentContainer contentContainer){
        this.searchTerm = title;
        this.id = id;
        this.contentContainer = contentContainer;
    }

    @Override
    protected String doInBackground(String... strings) {
        return search();
    }

    @Override
    protected void onPostExecute(String results){
        if(results != null){
            new HtmlDownloader(contentContainer,results).execute();
        }
        else{
            contentContainer.setText("(ej besvarad)");
        }
    }

    private String search(){
        try {
            Document doc = Jsoup.connect(apiQuery1+searchTerm+apiQuery2+id+apiQuery3)
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                    .timeout(10000)
                    .get();
            return doc.getElementsByTag("dokument_url_html").get(0).text();
        } catch (Exception e) {
            return null;
        }
    }
}
