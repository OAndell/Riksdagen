package se.oandell.riksdagen.MainModule.Menu;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;

import se.oandell.riksdagen.MainModule.MainActivity;
import se.oandell.riksdagen.MainModule.Pages.PageSuper;
import se.oandell.riksdagen.MainModule.Updater;
import se.oandell.riksdagen.R;

import java.util.ArrayList;

/**
 * Created by Oscar on 2017-03-24.
 */

public class MainMenu extends PopupWindow {

    private ArrayList<PageSuper> pages;
    private LinearLayout menuLayout;
    private Updater updater;
    private PopupWindow thisPopupMenu;

    public MainMenu( ArrayList<PageSuper> parties, Updater updater) {
        super(MainActivity.getInstance());
        Context context = MainActivity.getInstance();
        this.updater = updater;
        thisPopupMenu = this;
        menuLayout = new LinearLayout(context);
        menuLayout.setOrientation(LinearLayout.VERTICAL);
        menuLayout.setBackgroundColor(Color.WHITE);
        ScrollView scrollView = new ScrollView(context);
        this.setContentView(scrollView);
        this.pages = parties;
        initMenuItems();
        this.setAnimationStyle(R.style.animationMenu);
    }

    public LinearLayout getMenuLayout() {
        return menuLayout;
    }

    private void initMenuItems(){
        for (int i = 0; i < pages.size(); i++) {
            final MenuItem menuItem = new MenuItem( pages.get(i));
            final int finalI = i;
            menuItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pages.get(finalI).resetPageNumber(); //start at first page
                    updater.downloadAndUpdate(pages.get(finalI));
                    thisPopupMenu.dismiss();

                }
            });
            menuLayout.addView(menuItem);
        }

    }


}
