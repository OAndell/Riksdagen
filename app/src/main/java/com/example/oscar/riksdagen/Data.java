package com.example.oscar.riksdagen;

import java.util.ArrayList;

/**
 * Created by Oscar on 2017-03-26.
 */

public class Data {
    public static ArrayList<Page> getPages(){
        ArrayList<Page> pages = new ArrayList<>();
        pages.add(new Page("Aktuellt", R.drawable.rlogo ,R.drawable.topbanner, "https://data.riksdagen.se/dokumentlista2/?avd=aktuellt&facets=3&sort=datum&sortorder=desc&lang=sv&cmskategori=startsida&utformat=xml" /* "https://data.riksdagen.se/dokumentlista2/?avd=aktuellt&facets=3&sort=datum&sortorder=desc&lang=sv&cmskategori=startsida&utformat=rss"*/));
        pages.add(new Party("Socialdemokraterna", R.drawable.slogo,R.drawable.sbanner, "https://data.riksdagen.se/dokumentlista2/?avd=dokument&facets=3&del=sog&sort=datum&sortorder=desc&parti=S&sz=3&lang=sv&utformat=rss", "S"));
        pages.add(new Party("Moderaterna", R.drawable.mlogo,R.drawable.mbanner, "https://data.riksdagen.se/dokumentlista2/?avd=dokument&facets=3&del=sog&sort=datum&sortorder=desc&parti=M&sz=3&lang=sv&utformat=rss", "M"));
        pages.add(new Party("Sverigedemokraterna",R.drawable.sdlogo,R.drawable.sdbanner, "https://data.riksdagen.se/dokumentlista2/?avd=dokument&facets=3&del=sog&sort=datum&sortorder=desc&parti=SD&sz=3&lang=sv&utformat=rss", "SD"));
        pages.add(new Party("Miljöpartiet",R.drawable.mplogo,R.drawable.mpbanner, "https://data.riksdagen.se/dokumentlista2/?avd=dokument&facets=3&del=sog&sort=datum&sortorder=desc&parti=MP&sz=3&lang=sv&utformat=rss", "MP"));
        pages.add(new Party("Centerpartiet",R.drawable.clogo,R.drawable.cbanner, "https://data.riksdagen.se/dokumentlista2/?avd=dokument&facets=3&del=sog&sort=datum&sortorder=desc&parti=C&sz=3&lang=sv&utformat=rss", "C"));
        pages.add(new Party("Vänsterpartiet",R.drawable.vlogo,R.drawable.vbanner, "https://data.riksdagen.se/dokumentlista2/?avd=dokument&facets=3&del=sog&sort=datum&sortorder=desc&parti=V&sz=3&lang=sv&utformat=rss", "V"));
        pages.add(new Party("Liberalerna",R.drawable.llogo,R.drawable.lbanner, "https://data.riksdagen.se/dokumentlista2/?avd=dokument&facets=3&del=sog&sort=datum&sortorder=desc&parti=L&sz=3&lang=sv&utformat=rss", "L"));
        pages.add(new Party("Kristdemokraterna",R.drawable.kdlogo, R.drawable.kdbanner,"https://data.riksdagen.se/dokumentlista2/?avd=dokument&facets=3&del=sog&sort=datum&sortorder=desc&parti=KD&sz=3&lang=sv&utformat=rss", "KD"));
        pages.add(new Page("Voteringar",R.drawable.votlogo ,R.drawable.votbanner,"http://data.riksdagen.se/dokumentlista/?sok=&doktyp=votering&rm=&from=&tom=&ts=&bet=&tempbet=&nr=&org=&iid=&webbtv=&talare=&exakt=&planering=&sort=rel&sortorder=desc&rapport=&utformat=xml&a=s#soktraff"));
        return pages;
    }
}
