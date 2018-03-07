package se.oandell.riksdagen.MainModule.Pages;

import android.content.Context;

import se.oandell.riksdagen.MainModule.ContentContainer;
import se.oandell.riksdagen.R;

/**
 * Created by oscar on 2017-07-24.
 */
public class AboutPage extends PageSuper {

    ContentContainer contentContainer;

    public AboutPage(String name, int symbol, int banner, Context context) {
        super(name, symbol, banner);
        contentContainer = new ContentContainer();
        contentContainer.setTitle(  context.getResources().getString(R.string.about_title));
        contentContainer.setText(context.getResources().getString(R.string.about_text));
        contentContainer.addImage(R.drawable.riksdagshuset);
    }

    public ContentContainer getContentContainer() {
        return contentContainer;
    }

}
