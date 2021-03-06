package se.oandell.riksdagen.MainModule;

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
public class ContentContainer extends LinearLayout{

    private TextView titleTextView;
    private TextView contentTextView;
    private TextView footerTextView;
    private ImageView portraitImageView;
    private ImageView bottomImageView;
    private View divider;

    public ContentContainer() {
        super(MainActivity.getInstance());
        Context context = MainActivity.getInstance();
        this.setOrientation(LinearLayout.VERTICAL);


        //Create horizontal layout in which to place potential portrait and title text.
        //This is done so they can be placed next to each other
        LinearLayout imageAndTitleLayout = new LinearLayout(context);
        imageAndTitleLayout.setOrientation(HORIZONTAL);
        this.addView(imageAndTitleLayout);

        //Title text
        titleTextView = new TextView(context);
        titleTextView.setTextSize(15);
        titleTextView.setTextColor(Color.BLACK);
        titleTextView.setTypeface(Typeface.DEFAULT_BOLD);

        //Text body
        contentTextView = new TextView(context);
        contentTextView.setTextColor(Color.BLACK);
        this.addView(contentTextView);

        //Image view to display document author portraits
        portraitImageView = new ImageView(context);
        portraitImageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        imageAndTitleLayout.addView(portraitImageView);
        imageAndTitleLayout.addView(titleTextView);

        bottomImageView = new ImageView(context);
        bottomImageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        this.addView(bottomImageView);

        footerTextView = new TextView(context);
        footerTextView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        this.addView(footerTextView);


        this.setPadding(0,0,0,25);

        //Black line to divide list items
        divider = new View(context);
        divider.setBackgroundColor(Color.BLACK);
        divider.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));
        this.addView(divider);
    }

    public void addPortrait(Bitmap img){
        if(img!=null){
            Bitmap imgScaled = Bitmap.createScaledBitmap(img,192,256, true); //Rescale bitmap is necessary for some reason
            portraitImageView.setPadding(0,0,20,0);
            portraitImageView.setImageBitmap(imgScaled);
        }
    }

    public void addImage(Bitmap img){
        if(img!=null){
            bottomImageView.setImageBitmap(img);
        }
    }

    public void addImage(int imgID){
        bottomImageView.setImageResource(imgID);
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

    public TextView getFooterTextView(){
        return footerTextView;
    }


}
