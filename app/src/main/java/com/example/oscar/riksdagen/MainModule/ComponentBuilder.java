package com.example.oscar.riksdagen.MainModule;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.oscar.riksdagen.R;

/**
 * Created by oscar on 2017-07-08.
 */

public class ComponentBuilder {

    private Updater updater;


    static View getPageNavigator(Context context, final Updater updater){
        LinearLayout footer = new LinearLayout(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();

        View pageNav = inflater.inflate(R.layout.page_navigator,null,false);
        TextView currentPageText = (TextView) pageNav.findViewById(R.id.page_indicator);
        ImageButton prevArrow = (ImageButton) pageNav.findViewById(R.id.left_arrow);
        ImageButton nextArrow = (ImageButton) pageNav.findViewById(R.id.right_arrow);

        currentPageText.setText("Sida: " + updater.getCurrentPage().getPageNumber());

        if(updater.getCurrentPage().getPageNumber() == 1){
            prevArrow.setImageResource(R.drawable.ic_navigate_before_gray_24dp);
        }
        else {
            prevArrow.setImageResource(R.drawable.ic_navigate_before_black_24dp);
        }

        prevArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(updater.getCurrentPage().getPageNumber() > 1){
                    updater.getCurrentPage().previousPage();
                    updater.downloadAndUpdate(updater.getCurrentPage());

                }
            }
        });

        nextArrow .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updater.getCurrentPage().nextPage();
                updater.downloadAndUpdate(updater.getCurrentPage());

            }
        });

        return pageNav;

    }




}
