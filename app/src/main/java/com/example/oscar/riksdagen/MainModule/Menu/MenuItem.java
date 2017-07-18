package com.example.oscar.riksdagen.MainModule.Menu;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.oscar.riksdagen.MainModule.Pages.PageSuper;

/**
 * Created by Oscar on 2017-03-24.
 */

public class MenuItem extends LinearLayout{

    private int TEXT_SIZE = 22;
    private int HEIGHT  = 120;
    private int HORIZONTALPADDING = 20;


    public MenuItem(Context context, PageSuper party) {
        super(context);
        this.setOrientation(HORIZONTAL);
        this.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, HEIGHT));
        TextView textView = new TextView(context);
        textView.setText(party.getName());
        textView.setTextSize(TEXT_SIZE);
        textView.setTextColor(Color.BLACK);
        textView.setPadding(HORIZONTALPADDING,0,HORIZONTALPADDING,0);
        ImageView imageView = new ImageView(context);
        LayoutParams imageParams = new LayoutParams(110, 110);
        imageParams.gravity = Gravity.LEFT;
        imageView.setLayoutParams(imageParams);
        imageView.setImageResource(party.getSymbol());
        this.addView(imageView);
        this.addView(textView);

    }

}
