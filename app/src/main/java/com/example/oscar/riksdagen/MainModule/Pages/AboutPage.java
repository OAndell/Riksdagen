package com.example.oscar.riksdagen.MainModule.Pages;

import android.content.Context;

import com.example.oscar.riksdagen.MainModule.ContentContainer;
import com.example.oscar.riksdagen.R;

/**
 * Created by oscar on 2017-07-24.
 */
public class AboutPage extends PageSuper {

    ContentContainer contentContainer;

    public AboutPage(String name, int symbol, int banner, Context context) {
        super(name, symbol, banner);
        contentContainer = new ContentContainer(context);
        contentContainer.setTitle(  context.getResources().getString(R.string.about_title));
        contentContainer.setText(context.getResources().getString(R.string.about_text));
        contentContainer.addImage(R.drawable.riksdagshuset);
    }

    public ContentContainer getContentContainer() {
        return contentContainer;
    }

}
