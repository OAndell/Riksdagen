package com.example.oscar.riksdagen.MainModule.Pages;


/**
 * Created by Oscar on 2017-03-26.
 */

public class Party extends PageSuper {

    private String id;

    public Party(String name, int symbol,int banner, String id) {
        super(name, symbol, banner);
        this.id = id;
    }

    public String getID(){
        return id;
    }
}
