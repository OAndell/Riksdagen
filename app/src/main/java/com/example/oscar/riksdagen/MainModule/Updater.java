package com.example.oscar.riksdagen.MainModule;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.oscar.riksdagen.MainModule.Pages.Page;
import com.example.oscar.riksdagen.MainModule.Pages.PageSuper;
import com.example.oscar.riksdagen.MainModule.Pages.Party;
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
    private Stack<PageSuper> backStack = new Stack<>(); //Stores history of visited pages
    private boolean documentView = false;
    private PageSuper currentPage;
    private ScrollView scrollView;

    public Updater(Context context, LinearLayout listLayout, ImageView banner , ScrollView scrollView){
        this.listLayout = listLayout;
        this.context = context;
        this.banner = banner;
        this.scrollView = scrollView;
    }

    public void downloadAndUpdate(PageSuper page){
        currentPage = page;
        listLayout.removeAllViews();
        scrollView.scrollTo(0,0);
        backStack.push(page);
        updateBanner(page.getBanner());
        documentView = false;
        if(page.getClass() == Party.class){
            new APIParser(this,(Party)page).execute();
        }
        else{
            new APIParser(this,(Page) page).execute();
        }
    }

    /**
     * Update with xml api query, called by async task
     * @param xml
     */
    public void update(String xml){
        Document xmlDoc = Jsoup.parse(xml);
        final Elements allDocs = xmlDoc.getElementsByTag("dokument");
        for (int i = 0; i < allDocs.size(); i++) {
            createItem(allDocs.get(i));
        }
        TextView sourceTxt = new TextView(context);
        sourceTxt.setText("Källa: www.riksdagen.se");
        sourceTxt.setTextSize(9);
        listLayout.addView(sourceTxt);
        listLayout.addView(new PageNavigator(context, this));
    }

    /**
     * Changes the top banner image.
     * @param img image resource id
     */
    public void updateBanner(int img){
        banner.setImageResource(img);
    }

    /**
     * Create a listItem customized according to the current page.
     */
    private void createItem(final Element doc){
        ListItem item = new ListItem(context);
        if(doc.getElementsByTag("debattnamn").get(0).text().length() > 0){ //Check to see if you should look for summaries instead of documents
            if(currentPage.getClass() == Party.class) {
                createPartyItem(doc, item);
            }
            else if(doc.getElementsByTag("doktyp").text().equals("prot")){
                    createProtocolItem(doc, item);
            }
            else if(doc.getElementsByTag("doktyp").text().equals("votering")){
                createVoteItem(doc, item);
            }
            else if(doc.getElementsByTag("doktyp").text().equals("bet")){
                createBetItem(doc, item);
            }
        }
        else{
            createNonClickableItem(doc, item);
        }
        listLayout.addView(item);
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
     * Find and display replies to written questions.
     * The content is displayed below the original questions.
     * Calls a ReplyFinder task.
     */
    private void getReplyDoc(String title, String id){
        ListItem reply = new ListItem(context);
        listLayout.addView(reply);
        title = title.replace(" ", "+");
        reply.setTitle("Svar på skriftlig fråga:");
        new ReplyFinder(title,id,reply).execute();
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

    public PageSuper getCurrentPage(){
        return currentPage;
    }

    public ScrollView getScrollView() {
        return scrollView;
    }

    /**
     * Create non interactive items for startpage.
     */
    private void createNonClickableItem(Element doc, ListItem item){
        item.setTitle(doc.getElementsByTag("titel").get(0).text());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //TODO look up how to deal with this
            String summary = Html.fromHtml(doc.getElementsByTag("summary").get(0).text(),Html.FROM_HTML_OPTION_USE_CSS_COLORS).toString();
            item.setText(TextCleaner.cleanupText(summary));
        }
        item.setText(item.getText() + "\n" + doc.getElementsByTag("systemdatum").get(0).text());
    }

    /**
     * Add list items for the party specific pages
     */
    private void createPartyItem(final Element doc, ListItem item){
        item.setTitle(doc.getElementsByTag("titel").get(0).text() + " (" + doc.getElementsByTag("debattnamn").get(0).text() +")" );
        item.setText(doc.getElementsByTag("undertitel").get(0).text());
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(doc.getElementsByTag("doktyp").text().equals("mot") ){//Start ReadActvity for motions.
                    Intent readPage = new Intent(context, ReadActivity.class);
                    readPage.putExtra("url",doc.getElementsByTag("dokument_url_html").text());
                    readPage.putExtra("bannerImage", currentPage.getBanner());
                    context.startActivity(readPage);
                }
                else if(doc.getElementsByTag("namn").hasText()){
                    getHTMLDocument(doc.getElementsByTag("dokument_url_html").text(), doc.getElementsByTag("namn").get(0).text());
                    if(doc.getElementsByTag("doktyp").text().equals("fr")){//Questions
                        getReplyDoc(doc.getElementsByTag("titel").get(0).text(),doc.getElementsByTag("beteckning").get(0).text());
                    }
                }
                documentView = true;
            }
        });
    }

    /**
     *Create specific listItem for the vote page
     */
    private void createVoteItem(final Element doc, ListItem item){
        item.setTitle(doc.getElementsByTag("titel").get(0).text());
        item.setText(doc.getElementsByTag("undertitel").get(0).text());
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent votePage = new Intent(context, VoteActivity.class);
                votePage.putExtra("pageURL", doc.getElementsByTag("dokument_url_html").text());
                votePage.putExtra("pageDesc", doc.getElementsByTag("titel").get(0).text());
                context.startActivity(votePage);
            }
        });
    }

    /**
     *Create specific listItem for the protocol page
     */
    private void createProtocolItem(final Element doc, ListItem item){
        item.setTitle(doc.getElementsByTag("titel").get(0).text());
        item.setText(doc.getElementsByTag("summary").get(0).text()+ "...");
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent readPage = new Intent(context, ReadActivity.class);
                readPage.putExtra("url",doc.getElementsByTag("dokument_url_html").text());
                readPage.putExtra("bannerImage", currentPage.getBanner());
                context.startActivity(readPage);
            }
        });
    }

    private void createBetItem(final Element doc, ListItem item){
        item.setTitle(doc.getElementsByTag("notisrubrik").get(0).text());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//Todo look up on how to deal with this
            item.setText(Html.fromHtml(doc.getElementsByTag("notis").get(0).text(),Html.FROM_HTML_OPTION_USE_CSS_COLORS).toString());
        }
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent readPage = new Intent(context, ReadActivity.class);
                readPage.putExtra("url",doc.getElementsByTag("dokument_url_html").text());
                readPage.putExtra("bannerImage", currentPage.getBanner());
                context.startActivity(readPage);
            }
        });
    }
}
