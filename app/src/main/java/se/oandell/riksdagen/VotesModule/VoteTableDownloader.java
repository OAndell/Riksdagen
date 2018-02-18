package se.oandell.riksdagen.VotesModule;

import android.os.AsyncTask;

import com.jjoe64.graphview.GraphView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Oscar on 2017-05-16.
 */

public class VoteTableDownloader extends AsyncTask<Element,Element,Element> {

    private String url;
    private ArrayList<GraphView> graphs;

    public VoteTableDownloader(String url,ArrayList<GraphView> graphs){
        this.url = url;
        this.graphs = graphs;
    }

    @Override
    protected Element doInBackground(Element... elements) {
        return download();
    }

    @Override
    protected void onPostExecute(Element table){
        Elements rows = table.select("tr");
        String data[];
        for (int i = 0; i < graphs.size()-1; i++) {;
            data = rows.get(i+2).text().split(" ");
            VoteActivity.setupGraph(graphs.get(i),Integer.parseInt(data[1]),Integer.parseInt(data[2]),Integer.parseInt(data[3]),Integer.parseInt(data[4]), true);
        }
        //The data for the total result is located att rows.size()-2
        data = rows.get(rows.size()-2).text().split(" ");
        VoteActivity.setupGraph(graphs.get(graphs.size()-1),Integer.parseInt(data[1]),Integer.parseInt(data[2]),Integer.parseInt(data[3]),Integer.parseInt(data[4]), false);

    }

    private Element download(){
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                    .timeout(10000)
                    .get();
            return doc.select("table").get(0); //select the first table.
        } catch (IOException e) {
            return null;
        }
    }



}
