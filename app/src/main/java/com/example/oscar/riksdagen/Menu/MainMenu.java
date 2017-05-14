package com.example.oscar.riksdagen.Menu;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.oscar.riksdagen.Page;
import com.example.oscar.riksdagen.Updater;

import java.util.ArrayList;

/**
 * Created by Oscar on 2017-03-24.
 */

public class MainMenu extends PopupWindow {

    private ArrayList<Page> pages;
    private LinearLayout menuLayout;
    private Updater updater;
    private PopupWindow thisPopupMenu;

    public MainMenu(Context context, ArrayList<Page> parties, Updater updater) {
        super(context);
        this.updater = updater;
        thisPopupMenu = this;
        menuLayout = new LinearLayout(context);
        menuLayout.setOrientation(LinearLayout.VERTICAL);
        menuLayout.setBackgroundColor(Color.WHITE);
        this.setContentView(menuLayout);
        this.pages = parties;
        initMenuItems(context);
    }

    private void initMenuItems(Context context){
        for (int i = 0; i < pages.size(); i++) {
            MenuItem menuItem = new MenuItem(context, pages.get(i));
            final int finalI = i;
            menuItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updater.downloadAndUpdate(pages.get(finalI));
                    thisPopupMenu.dismiss();
                }
            });
            menuLayout.addView(menuItem);
        }

    }


}
