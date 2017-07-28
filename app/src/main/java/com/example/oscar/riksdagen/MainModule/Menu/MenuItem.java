package com.example.oscar.riksdagen.MainModule.Menu;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.oscar.riksdagen.MainModule.Pages.PageSuper;
import com.example.oscar.riksdagen.R;

import org.w3c.dom.Text;

/**
 * Created by Oscar on 2017-03-24.
 */

public class MenuItem extends LinearLayout{

    private static int TEXT_SIZE = 21;
    private static int HEIGHT  = 115;
    private static int HORIZONTAL_PADDING = 20;
    private static int IMAGE_WIDTH  = 112;
    private static int IMAGE_HEIGHT = 112;

    private TextView textView;
    private ImageView imageView;

    public MenuItem(final Context context, PageSuper page) {
        super(context);
        this.setOrientation(HORIZONTAL);
        this.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, HEIGHT));
        textView = new TextView(context);
        textView.setTextSize(TEXT_SIZE);
        textView.setTextColor(Color.BLACK);
        textView.setTypeface(null,Typeface.ITALIC);
        textView.setPadding(HORIZONTAL_PADDING,0,HORIZONTAL_PADDING,0);
        imageView = new ImageView(context);
        LayoutParams imageParams = new LayoutParams(IMAGE_WIDTH, IMAGE_HEIGHT);
        imageView.setLayoutParams(imageParams);
        this.addView(imageView);
        this.addView(textView);
        textView.setText(page.getName());
        imageView.setImageResource(page.getSymbol());
    }




}
