package com.example.oscar.riksdagen.MainModule.Menu;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.PopupWindowCompat;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;

import com.example.oscar.riksdagen.MainModule.Pages.PageSuper;
import com.example.oscar.riksdagen.MainModule.Updater;
import com.example.oscar.riksdagen.R;

import java.util.ArrayList;

/**
 * Created by Oscar on 2017-03-24.
 */

public class MainMenu extends PopupWindow {

    private ArrayList<PageSuper> pages;
    private LinearLayout menuLayout;
    private Updater updater;
    private PopupWindow thisPopupMenu;

    public MainMenu(Context context, ArrayList<PageSuper> parties, Updater updater) {
        super(context);
        this.updater = updater;
        thisPopupMenu = this;
        menuLayout = new LinearLayout(context);
        menuLayout.setOrientation(LinearLayout.VERTICAL);
        menuLayout.setBackgroundColor(Color.WHITE);
        ScrollView scrollView = new ScrollView(context);
        this.setContentView(scrollView);
        this.pages = parties;
        initMenuItems(context);
        this.setAnimationStyle(R.style.animationMenu);
    }

    public LinearLayout getMenuLayout() {
        return menuLayout;
    }

    private void initMenuItems(Context context){
        for (int i = 0; i < pages.size(); i++) {
            final MenuItem menuItem = new MenuItem(context, pages.get(i));
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
