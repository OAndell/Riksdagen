package com.example.oscar.riksdagen.Tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.oscar.riksdagen.MainModule.ContentContainer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Oscar on 2017-04-02.
 */
public class ImageDownloader extends AsyncTask<String,Void,Bitmap> {

    public static int INPUT_NAME = 0;
    public static int INPUT_URL = 1;

    private ContentContainer contentContainer;
    private String query;
    private int currentSetting;

    public ImageDownloader(ContentContainer contentContainer, String input, int setting){
        this.contentContainer = contentContainer;
        currentSetting = setting;
        if(setting == INPUT_NAME){
            query = "http://data.riksdagen.se/personlista/?iid=&fnamn="+ input.split(" ")[0].trim() +"&enamn="+ input.split(" ")[1].trim() +"&utformat=xml&termlista=";
        }
        if(setting == INPUT_URL){
            query = input;
        }

    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        return downloadJSON();
    }

    @Override
    protected void onPostExecute(Bitmap image){
        if(currentSetting == INPUT_NAME){
            contentContainer.addPortrait(image);
        }
        if(currentSetting == INPUT_URL){
            contentContainer.addImage(image);
        }
    }

    private Bitmap downloadJSON() {
        if(currentSetting == INPUT_NAME){
            try {
                Document doc = Jsoup.connect(query)
                        .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                        .timeout(10000)
                        .get();
                if(doc.getElementsByTag("bild_url_192").size() > 0){
                    return getImage(doc.getElementsByTag("bild_url_192").get(0).text());
                }
                return null;
            } catch (IOException e) {
                return null;
            }
        }
        else if(currentSetting == INPUT_URL){
            return getImage(query);
        }
        else{
            return null;
        }
    }

    public static Bitmap getImage(String url){
        try {
            return BitmapFactory.decodeStream( (InputStream) new URL(url).getContent());
        } catch (IOException e) {
            return null;
        }
    }
}
