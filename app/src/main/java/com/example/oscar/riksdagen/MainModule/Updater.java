package com.example.oscar.riksdagen.MainModule;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.oscar.riksdagen.ReadModule.ReadActivity;
import com.example.oscar.riksdagen.Tools.APIParser;
import com.example.oscar.riksdagen.Tools.HtmlDownloader;
import com.example.oscar.riksdagen.Tools.ImageDownloader;
import com.example.oscar.riksdagen.Tools.ReplyFinder;
import com.example.oscar.riksdagen.Tools.TextCleaner;
import com.example.oscar.riksdagen.VotesModule.VoteActivity;

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
            new APIParser(this, party.getID()).execute();
        }else{
            new APIParser(this,page).execute();
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
                    if(doc.getElementsByTag("doktyp").text().equals("mot")){//Start ReadActvity for motions.
                        Intent readPage = new Intent(context, ReadActivity.class);
                        readPage.putExtra("url",doc.getElementsByTag("dokument_url_html").text());
                        context.startActivity(readPage);
                    }
                    else if(doc.getElementsByTag("namn").hasText()){ //Questions
                        getHTMLDocument(doc.getElementsByTag("dokument_url_html").text(), doc.getElementsByTag("namn").get(0).text());
                        getReplyDoc(doc.getElementsByTag("titel").get(0).text());
                    }
                    else { //Votes
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
            item.setText(TextCleaner.cleanupText(doc.getElementsByTag("summary").get(0).text()));
            item.setText(item.getText() + "\n" + doc.getElementsByTag("systemdatum").get(0).text());
            listLayout.addView(item);
        }
    }

    /**
     * Clears the layout of all views and
     * finds and downloads a document + politician image
     */
    public void getHTMLDocument(String url, String name){
        listLayout.removeAllViews();
        ListItem item = new ListItem(context);
        listLayout.addView(item);
        new ImageDownloader(item, name).execute();
        new HtmlDownloader(item, url).execute();
    }

    /**
     * Find and dispay replies to written questions.
     * The content is displayed below the original questions.
     * Calls a ReplyFinder task.
     */
    private void getReplyDoc(String title){
        ListItem reply = new ListItem(context);
        listLayout.addView(reply);
        title = title.replace(" ", "+");
        reply.setTitle("Svar på skriftlig fråga:");
        new ReplyFinder(title,reply).execute();
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
