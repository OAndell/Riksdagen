package com.example.oscar.riksdagen;


/**
 * Created by Oscar on 2017-03-26.
 */

public class Party extends Page{

    private String id;

    public Party(String name, int symbol,int banner, String apiQuery, String id) {
        super(name, symbol, banner,  apiQuery);
        this.id = id;
    }

    public String getID(){
        return id;
    }
}
