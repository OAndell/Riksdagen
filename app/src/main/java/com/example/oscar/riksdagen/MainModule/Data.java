package com.example.oscar.riksdagen.MainModule;

import com.example.oscar.riksdagen.MainModule.Pages.Page;
import com.example.oscar.riksdagen.MainModule.Pages.PageSuper;
import com.example.oscar.riksdagen.MainModule.Pages.Party;
import com.example.oscar.riksdagen.R;

import java.util.ArrayList;

/**
 * Created by Oscar on 2017-03-26.
 */

public class Data {
    public static ArrayList<PageSuper> getPages(){
        ArrayList<PageSuper> pages = new ArrayList<>();
        pages.add(new Page("Aktuellt", R.drawable.rlogo ,R.drawable.topbanner, "https://data.riksdagen.se/dokumentlista2/?avd=aktuellt&facets=3&sort=datum&sortorder=desc&lang=sv&cmskategori=startsida&utformat=xml&p="));
        pages.add(new Party("Socialdemokraterna", R.drawable.slogo,R.drawable.sbanner, "S"));
        pages.add(new Party("Moderaterna", R.drawable.mlogo,R.drawable.mbanner,  "M"));
        pages.add(new Party("Sverigedemokraterna",R.drawable.sdlogo,R.drawable.sdbanner, "SD"));
        pages.add(new Party("Miljöpartiet",R.drawable.mplogo,R.drawable.mpbanner, "MP"));
        pages.add(new Party("Centerpartiet",R.drawable.clogo,R.drawable.cbanner, "C"));
        pages.add(new Party("Vänsterpartiet",R.drawable.vlogo,R.drawable.vbanner, "V"));
        pages.add(new Party("Liberalerna",R.drawable.llogo,R.drawable.lbanner, "L"));
        pages.add(new Party("Kristdemokraterna",R.drawable.kdlogo, R.drawable.kdbanner,"KD"));
        pages.add(new Page("Voteringar",R.drawable.votlogo ,R.drawable.votbanner,"http://data.riksdagen.se/dokumentlista/?sok=&doktyp=votering&rm=&from=&tom=&ts=&bet=&tempbet=&nr=&org=&iid=&webbtv=&talare=&exakt=&planering=&sort=dat&sortorder=desc&rapport=&utformat=xml&p="));
        return pages;
    }
}
