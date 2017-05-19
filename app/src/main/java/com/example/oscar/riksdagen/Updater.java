package com.example.oscar.riksdagen;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.oscar.riksdagen.Tools.DocumentDownloader;
import com.example.oscar.riksdagen.Tools.HtmlDownloader;
import com.example.oscar.riksdagen.Tools.ImageDownloader;
import com.example.oscar.riksdagen.Votes.VoteActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Stack;


/**
 * Created by Oscar on 2017-03-24.
 */

public class Updater {
    private LinearLayout listLayout;
    private ImageView banner;
    private Context context;
    private Stack<Page> backStack = new Stack<>(); //Stores history of visited pages
    private boolean documentView = false;

    public Updater(Context context, LinearLayout listLayout, ImageView banner){
        this.listLayout = listLayout;
        this.context = context;
        this.banner = banner;
    }

    public void downloadAndUpdate(Page page){
        backStack.push(page);
        updateBanner(page.getBanner());
        documentView = false;
        if(page.getClass() == Party.class){
            Party party =  (Party) page;
            new DocumentDownloader(this, party.getID()).execute();
        }else{
            new DocumentDownloader(this,page).execute();
        }
    }

    /**
     * Update with xml api query
     * @param xml
     */
    public void update(String xml){
        listLayout.removeAllViews();
        Document xmlDoc = Jsoup.parse(xml);
        final Elements allDocs = xmlDoc.getElementsByTag("dokument");
        for (int i = 0; i < allDocs.size(); i++) {
            createItem(allDocs.get(i));
        }
        TextView sourceTxt = new TextView(context);
        sourceTxt.setText("Källa: www.riksdagen.se");
        sourceTxt.setTextSize(9);
        listLayout.addView(sourceTxt);
    }

    /**
     * Changes the top banner image.
     * @param img image resource id
     */
    public void updateBanner(int img){
        banner.setImageResource(img);
    }


    private void createItem(final Element doc){
        final ListItem item = new ListItem(context);
        if(doc.getElementsByTag("debattnamn").get(0).text().length() > 0){ //Check to see if you should look for summaries instead of documents, Not great code....
            item.setTitle(doc.getElementsByTag("titel").get(0).text() + " (" + doc.getElementsByTag("debattnamn").get(0).text() +")" );
            item.setText(doc.getElementsByTag("undertitel").get(0).text());
            item.setText(item.getText() + "\n" + doc.getElementsByTag("systemdatum").get(0).text());
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(doc.getElementsByTag("namn").hasText()){
                        getHTMLDocument(doc.getElementsByTag("dokument_url_html").text(), doc.getElementsByTag("namn").get(0).text());
                    }
                    else { //Voteringar
                        Intent votePage = new Intent(context, VoteActivity.class);
                        votePage.putExtra("pageURL", doc.getElementsByTag("dokument_url_html").text());
                        votePage.putExtra("pageDesc", doc.getElementsByTag("titel").get(0).text());
                        context.startActivity(votePage);
                    }
                    documentView = true;
                }
            });
            listLayout.addView(item);
        }else{ //Items fot start page
            item.setTitle(doc.getElementsByTag("titel").get(0).text());
            item.setText(cleanupText(doc.getElementsByTag("summary").get(0).text()));
            item.setText(item.getText() + "\n" + doc.getElementsByTag("systemdatum").get(0).text());
            listLayout.addView(item);
        }

    }

    /**
     * Removes unwanted text from the xml entry
     */
    private String cleanupText(String string){
        string = string.replaceAll("&#229;", "å")
            .replaceAll("&#228;", "ä")
            .replaceAll("&#246;", "ö")
            .replaceAll("&#214;", "Ö");
        return string.substring(9,string.length()-10); //Removes the "&lt;p&gt;" and "&lt;/p&gt;" from the beginning of the document
    }

    public void getHTMLDocument(String url, String name){
        listLayout.removeAllViews();
        ListItem item = new ListItem(context);
        listLayout.addView(item);
        new ImageDownloader(item, name).execute();
        new HtmlDownloader(item, url).execute();
    }

    public void getHTMLDocument(String url){
        listLayout.removeAllViews();
        ListItem item = new ListItem(context);
        listLayout.addView(item);
        new HtmlDownloader(item, url).execute();
    }

    /**
     * Loads the last visited page. If stack is empty the app closes.
     */
    public void handleBackButton(){
        if(backStack.peek() == null){
            System.exit(0);
        }
        else if(documentView){
            downloadAndUpdate(backStack.pop());
            documentView = false;
        }
        else{
            backStack.pop();
            if(backStack.peek() == null) {
                System.exit(0);

            }else {
                downloadAndUpdate(backStack.pop());
            }
        }
    }

}
