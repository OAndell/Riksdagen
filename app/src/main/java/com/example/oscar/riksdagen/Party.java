package com.example.oscar.riksdagen;

import android.graphics.Paint;

/**
 * Created by Oscar on 2017-03-26.
 */

public class Party extends Page{

    private String id;

    public Party(String name, int symbol,int banner, String rssUrl, String id) {
        super(name, symbol, banner, rssUrl);
        this.id = id;
    }

    public String getID(){
        return id;
    }
}
