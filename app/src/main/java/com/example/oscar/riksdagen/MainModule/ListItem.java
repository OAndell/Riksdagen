package com.example.oscar.riksdagen.MainModule;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Oscar on 2017-03-24.
 */
public class ListItem extends LinearLayout{

    private TextView titleTextView;
    private TextView contentTextView;
    private ImageView imageView;

    public ListItem(Context context) {
        super(context);
        this.setOrientation(LinearLayout.VERTICAL);

        //Create horizontal layout in which to place potential portrait and title text.
        //This is done so they can be placed next to each other
        LinearLayout imageAndTitleLayout = new LinearLayout(context);
        imageAndTitleLayout.setOrientation(HORIZONTAL);
        this.addView(imageAndTitleLayout);

        //Title
        titleTextView = new TextView(context);
        titleTextView.setTextSize(15);
        titleTextView.setTextColor(Color.BLACK);
        titleTextView.setTypeface(Typeface.DEFAULT_BOLD);

        //Text body
        contentTextView = new TextView(context);
        contentTextView.setTextColor(Color.BLACK);
        this.addView(contentTextView);

        //Image view to display document author portraits
        imageView = new ImageView(context);
        imageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        imageAndTitleLayout.addView(imageView);
        imageAndTitleLayout.addView(titleTextView);


        this.setPadding(0,0,0,25);

        //Black line to divide list items
        View divider = new View(context);
        divider.setBackgroundColor(Color.BLACK);
        divider.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));
        this.addView(divider);
    }

    public void addImage(Bitmap img){
        if(img!=null){
            Bitmap imgScaled = Bitmap.createScaledBitmap(img,192,256, true); //Rescale bitmap is nessary for some reason
            imageView.setPadding(0,0,20,0);
            imageView.setImageBitmap(imgScaled);
        }
    }

    public void setTitle(String title){
        titleTextView.setText(title);
    }

    public String getTitle(){
        return titleTextView.getText().toString();
    }

    public void setText(String text){
        contentTextView.setText(text);
    }

    public String getText(){
        return contentTextView.getText().toString();
    }


}
