package com.example.oscar.riksdagen.MainModule.Menu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
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

    private static int TEXT_SIZE = 22;
    private static int HEIGHT  = 120;
    private static int HORIZONTAL_PADDING = 20;
    private static int IMAGE_WIDTH  = 110;
    private static int IMAGE_HEIGHT = 110;

    public MenuItem(Context context, PageSuper party) {
        super(context);
        this.setOrientation(HORIZONTAL);
        this.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, HEIGHT));
        TextView textView = new TextView(context);
        textView.setText(party.getName());
        textView.setTextSize(TEXT_SIZE);
        textView.setTextColor(Color.BLACK);
        textView.setTypeface(null,Typeface.ITALIC);
        textView.setPadding(HORIZONTAL_PADDING,0,HORIZONTAL_PADDING,0);
        ImageView imageView = new ImageView(context);
        LayoutParams imageParams = new LayoutParams(IMAGE_WIDTH, IMAGE_HEIGHT);
        imageParams.gravity = Gravity.LEFT;
        imageView.setLayoutParams(imageParams);
        imageView.setImageResource(party.getSymbol());
        this.addView(imageView);
        this.addView(textView);

    }

}
