package com.example.oscar.riksdagen.MainModule;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.oscar.riksdagen.MainModule.Menu.MainMenu;
import com.example.oscar.riksdagen.MainModule.Pages.PageSuper;
import com.example.oscar.riksdagen.R;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private MainMenu mainMenu;
    private ArrayList<PageSuper> pages = new ArrayList<>();
    private Updater updater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pages = Data.getPages();
        LinearLayout itemLayout = (LinearLayout) findViewById(R.id.itemLayout);
        ImageView bannerView = (ImageView) findViewById(R.id.bannerImage) ;
        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        updater = new Updater(this, itemLayout, bannerView, scrollView);
        updater.downloadAndUpdate(pages.get(0));

        //Init main Menu
        mainMenu = new MainMenu(this, pages, updater);
        final Button menuButton = (Button) findViewById(R.id.menuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mainMenu.isShowing()){
                    mainMenu.showAsDropDown(menuButton);
                }
                else{
                    mainMenu.dismiss();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(mainMenu.isShowing()){
            mainMenu.dismiss();
        }
        else {
            updater.handleBackButton();
        }
    }
}
