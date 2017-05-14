package com.example.oscar.riksdagen.Tools;

import android.os.AsyncTask;

import com.example.oscar.riksdagen.ListItem;
import com.example.oscar.riksdagen.Page;
import com.example.oscar.riksdagen.Updater;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;


/**
 * Created by Oscar on 2017-03-29.
 */

public class DocumentDownloader extends AsyncTask<String, String, String> {

    private Updater updater;

    //private String docType = "doktyp=fr&doktyp=frs&doktyp=prop"; //Hämtar frågor, svar och propositioner
    private String docType = "doktyp=fr&doktyp=mot"; //Hämtar frågor, svar och propositioner
    private String query;

    public DocumentDownloader(Updater updater, String partyID){
        this.updater = updater;
        //query = "http://data.riksdagen.se/dokumentlista/?sok=&" + docType +"&rm=&from=&tom=&ts=&bet=&tempbet="+ partyID + "&nr=&org=&iid=&parti="+ partyID + "&webbtv=&talare=&exakt=&planering=&sort=datum&sortorder=desc&rapport=&utformat=xml&a=s#soktraff";
        query = "https://data.riksdagen.se/dokumentlista2/?avd=dokument&del=dokument&facets=3&parti="+partyID+"&fcs=1&sort=datum&sortorder=desc&utformat=xml";
    }

    public DocumentDownloader(Updater updater, Page page){
        this.updater = updater;
        query = page.getRSSUrl();
    }

    @Override
    protected String doInBackground(String... strings) {
        return downloadJSON();
    }

    @Override
    protected void onPostExecute(String result){
        updater.update(result);
    }

    private String downloadJSON() {
        try {
            Document doc = Jsoup.connect(query)
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                    .timeout(10000)
                    .get();
            return doc.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
