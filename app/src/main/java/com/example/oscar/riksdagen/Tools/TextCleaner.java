package com.example.oscar.riksdagen.Tools;

/**
 * Created by Oscar on 2017-05-21.
 */

public class TextCleaner {

    //Todo this is not complete
    public static String cleanupText(String in){
        String out = in.replaceAll("&#229;", "å")
                .replaceAll("&#228;", "ä")
                .replaceAll("&#246;", "ö")
                .replaceAll("&#214;", "Ö")
                .replaceAll("&lt;p&gt;", "")
                .replaceAll("&lt;/p&gt;", "")
                .replaceAll("<p>","")
                .replace("</p>","")
                .replaceAll("&nsbp;"," ")
                .replaceAll("&nbsp;"," ");
        return out;
    }
}
