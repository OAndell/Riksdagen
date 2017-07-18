package com.example.oscar.riksdagen.MainModule;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.oscar.riksdagen.R;

/**
 * Created by oscar on 2017-07-08.
 */

public class PageNavigator extends LinearLayout {

    private Updater updater;

    public PageNavigator(Context context, final Updater updater) {
        super(context);
        this.updater = updater;
        this.setOrientation(LinearLayout.HORIZONTAL);

        TextView currentPageText = new TextView(context);
        currentPageText.setText("Sida: " + updater.getCurrentPage().getPageNumber());
        currentPageText.setTypeface(Typeface.DEFAULT_BOLD);
        currentPageText.setPadding(0,20,20,0);

        LayoutParams arrowSize = new LayoutParams(180,90);

        ImageView prevArrow = new ImageView(context);
        prevArrow.setLayoutParams(arrowSize);
        if(updater.getCurrentPage().getPageNumber() == 1){
            prevArrow.setImageResource(R.drawable.arrowleftgray);
        }
        else {
            prevArrow.setImageResource(R.drawable.arrowleft);
        }
        prevArrow.setPadding(20,0,0,0);


        prevArrow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(updater.getCurrentPage().getPageNumber() > 1){
                    updater.getCurrentPage().previousPage();
                    handleClick();
                }
            }
        });

        ImageView nextArrow = new ImageView(context);
        nextArrow.setLayoutParams(arrowSize);
        nextArrow.setImageResource(R.drawable.arrowright);
        nextArrow.setPadding(20,0,0,0);
        nextArrow .setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                updater.getCurrentPage().nextPage();
                handleClick();
            }
        });

        View arrowDivider = new View(context);
        arrowDivider.setLayoutParams(arrowSize);

        currentPageText.setTextColor(Color.BLACK);

        this.addView(currentPageText);
        this.addView(prevArrow);
        this.addView(arrowDivider);
        this.addView(nextArrow);
    }

    private void handleClick(){
        updater.downloadAndUpdate(updater.getCurrentPage());
    }

}
