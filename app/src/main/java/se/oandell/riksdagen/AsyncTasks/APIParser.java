package se.oandell.riksdagen.AsyncTasks;

import android.os.AsyncTask;

import se.oandell.riksdagen.MainModule.Pages.Page;
import se.oandell.riksdagen.MainModule.Pages.Party;
import se.oandell.riksdagen.MainModule.Updater;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URI;


/**
 * Created by Oscar on 2017-03-29.
 */

public class APIParser extends AsyncTask<String, String, String> {

    private Updater updater;
    private String query;

    public APIParser(Updater updater, Party party){
        this.updater = updater;
        query = "https://data.riksdagen.se/dokumentlista2/?avd=dokument&del=dokument&facets=3&parti="+party.getID()+"&fcs=1&sort=datum&sortorder=desc&utformat=xml&p="+ party.getPageNumber();
    }

    public APIParser(Updater updater, Page page){
        this.updater = updater;
        query = page.getAPIquery();
    }

    @Override
    protected String doInBackground(String... strings) {
        return download();
    }

    @Override
    protected void onPostExecute(String result){
        updater.update(result);
    }

    private String download() {
        try {
            Document doc = Jsoup.connect(URI.create(query).toASCIIString())
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
