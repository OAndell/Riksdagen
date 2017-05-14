package com.example.oscar.riksdagen;


/**
 * Created by Oscar on 2017-03-24.
 */
public  class Page {
    private String name;
    private int symbol; //Page logo
    private int banner;
    private String rssUrl;


    public Page(String name, int symbol, int banner, String rssUrl){
        this.name = name;
        this.symbol = symbol;
        this.rssUrl = rssUrl;
        this.banner = banner;
    }

    public int getBanner() {
        return banner;
    }

    public String getName(){
        return name;
    }

    public String getRSSUrl(){
        return rssUrl;
    }

    public int getSymbol() {
        return symbol;
    }
}


