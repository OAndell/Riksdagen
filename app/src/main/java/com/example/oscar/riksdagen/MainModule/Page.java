package com.example.oscar.riksdagen.MainModule;


/**
 * Created by Oscar on 2017-03-24.
 */
public  class Page {
    private String name;
    private int symbol; //Page logo
    private int banner;
    private String apiQuery;


    public Page(String name, int symbol, int banner, String apiQuery){
        this.name = name;
        this.symbol = symbol;
        this.apiQuery = apiQuery;
        this.banner = banner;
    }

    public int getBanner() {
        return banner;
    }

    public String getName(){
        return name;
    }

    public String getAPIquery(){
        return apiQuery;
    }

    public int getSymbol() {
        return symbol;
    }
}


