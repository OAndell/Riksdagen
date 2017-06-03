package com.example.oscar.riksdagen.Tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.oscar.riksdagen.MainModule.ListItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Oscar on 2017-04-02.
 */
public class ImageDownloader extends AsyncTask<String,Void,Bitmap> {

    private ListItem listItem;
    String query;

    public ImageDownloader(ListItem listItem, String name){
        this.listItem = listItem;
        query = "http://data.riksdagen.se/personlista/?iid=&fnamn="+ name.split(" ")[0].trim() +"&enamn="+ name.split(" ")[1].trim() +"&f_ar=&kn=&parti=&valkrets=&rdlstatus=&org=&utformat=xml&termlista=";
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        return downloadJSON();
    }

    @Override
    protected void onPostExecute(Bitmap image){
        listItem.addImage(image);
    }

    private Bitmap downloadJSON() {
        try {
            Document doc = Jsoup.connect(query)
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                    .timeout(10000)
                    .get();
            final Bitmap image_;
            if(doc.getElementsByTag("bild_url_192").size() > 0){
                image_ = BitmapFactory.decodeStream( (InputStream) new URL(doc.getElementsByTag("bild_url_192").get(0).text()).getContent());
                return image_;

            }else{
                return null;
            }

        } catch (IOException e) {
            return null;
        }
    }
}
