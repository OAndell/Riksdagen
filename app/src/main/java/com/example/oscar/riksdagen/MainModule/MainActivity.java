package com.example.oscar.riksdagen.MainModule;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.oscar.riksdagen.MainModule.Menu.MainMenu;
import com.example.oscar.riksdagen.MainModule.Pages.Page;
import com.example.oscar.riksdagen.MainModule.Pages.PageSuper;
import com.example.oscar.riksdagen.MainModule.Pages.Party;
import com.example.oscar.riksdagen.R;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private MainMenu mainMenu;
    private ArrayList<PageSuper> pages = new ArrayList<>();
    private Updater updater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPages();
        LinearLayout itemLayout = (LinearLayout) findViewById(R.id.itemLayout);
        ImageView bannerView = (ImageView) findViewById(R.id.bannerImage) ;
        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        updater = new Updater(this, itemLayout, bannerView, scrollView);
        updater.downloadAndUpdate(pages.get(0));

        //Init main Menu
        mainMenu = new MainMenu(this, pages, updater);
        final Button menuButton = (Button) findViewById(R.id.menuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mainMenu.isShowing()){
                    mainMenu.showAsDropDown(menuButton);
                }
                else{
                    mainMenu.dismiss();
                }
            }
        });
    }

    private void initPages(){
        pages.add(new Page("Aktuellt", R.drawable.rlogo ,R.drawable.topbanner, "https://data.riksdagen.se/dokumentlista2/?avd=aktuellt&facets=3&sort=datum&sortorder=desc&lang=sv&cmskategori=startsida&utformat=xml&p="));
        pages.add(new Party("Socialdemokraterna", R.drawable.slogo,R.drawable.sbanner, "S"));
        pages.add(new Party("Moderaterna", R.drawable.mlogo,R.drawable.mbanner,  "M"));
        pages.add(new Party("Sverigedemokraterna",R.drawable.sdlogo,R.drawable.sdbanner, "SD"));
        pages.add(new Party("Miljöpartiet",R.drawable.mplogo,R.drawable.mpbanner, "MP"));
        pages.add(new Party("Centerpartiet",R.drawable.clogo,R.drawable.cbanner, "C"));
        pages.add(new Party("Vänsterpartiet",R.drawable.vlogo,R.drawable.vbanner, "V"));
        pages.add(new Party("Liberalerna",R.drawable.llogo,R.drawable.lbanner, "L"));
        pages.add(new Party("Kristdemokraterna",R.drawable.kdlogo, R.drawable.kdbanner,"KD"));
        pages.add(new Page("Riksdagsbeslut", R.drawable.betlogo, R.drawable.betbanner, "https://data.riksdagen.se/dokumentlista2/?avd=dokument&doktyp=bet&beslutad=1&sort=beslutsdag&sortorder=desc&utformat=xml&p="));
        pages.add(new Page("Voteringar",R.drawable.votlogo ,R.drawable.votbanner,"http://data.riksdagen.se/dokumentlista/?sok=&doktyp=votering&rm=&sort=dat&sortorder=desc&rapport=&utformat=xml&p="));
        pages.add(new Page("Kammarprotokoll", R.drawable.protlogo, R.drawable.protbanner, "http://data.riksdagen.se/dokumentlista/?sok=&doktyp=prot&sort=datum&sortorder=desc&utformat=xml&p="));
    }

    @Override
    public void onBackPressed() {
        if(mainMenu.isShowing()){
            mainMenu.dismiss();
        }
        else {
            updater.handleBackButton();
        }
    }
}
