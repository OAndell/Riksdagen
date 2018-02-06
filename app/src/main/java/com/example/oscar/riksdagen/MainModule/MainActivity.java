package com.example.oscar.riksdagen.MainModule;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.example.oscar.riksdagen.MainModule.Menu.DrawerAdapter;
import com.example.oscar.riksdagen.MainModule.Menu.MainMenu;
import com.example.oscar.riksdagen.MainModule.Pages.AboutPage;
import com.example.oscar.riksdagen.MainModule.Pages.Page;
import com.example.oscar.riksdagen.MainModule.Pages.PageSuper;
import com.example.oscar.riksdagen.MainModule.Pages.Party;
import com.example.oscar.riksdagen.R;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private MainMenu mainMenu;
    private ArrayList<PageSuper> pages = new ArrayList<>();
    private Updater updater;

    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private boolean mToolBarNavigationListenerIsRegistered = false;
    boolean showMenu = false;
    private int menuResource = R.drawable.topbanner;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPages();
        LinearLayout itemLayout = (LinearLayout) findViewById(R.id.itemLayout);
        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        spinner = (ProgressBar) findViewById(R.id.spinner);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(pages.get(0).getName());
        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action__home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ListView drawerList = (ListView) findViewById(R.id.left_drawer);

        DrawerAdapter adapter = new DrawerAdapter(this,R.id.left_drawer,pages);
        drawerList.setAdapter(adapter);
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pages.get(i).resetPageNumber(); //start at first page
                updater.downloadAndUpdate(pages.get(i));
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });



        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(toggle);

        updater = new Updater(this, itemLayout, scrollView);
        updater.downloadAndUpdate(pages.get(0));


    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem dummy = menu.findItem(R.id.dummy_menu);
        dummy.setIcon(getResources().getDrawable(menuResource));

        return true;
    }

    public void setMenuResource(int menuResource) {
        this.menuResource = menuResource;
        invalidateOptionsMenu();
    }


    public void setLoading(boolean loading){
        if(loading) spinner.setVisibility(View.VISIBLE);
        else spinner.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public boolean onSupportNavigateUp() {
        updater.handleBackButton();
        return true;
    }

    public ActionBarDrawerToggle getToggle() {
        return toggle;
    }

    public void setShowUpButton(boolean enable) {
        if (enable) {
            toggle.setToolbarNavigationClickListener(null);
            toggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (!mToolBarNavigationListenerIsRegistered) {
                toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });

                mToolBarNavigationListenerIsRegistered = true;
            }

        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            toggle.setDrawerIndicatorEnabled(true);
            mToolBarNavigationListenerIsRegistered = false;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(item.getItemId() == android.R.id.home) {
            updater.handleBackButton();
            //return true;
        }

        if(toggle.onOptionsItemSelected(item)) return true;


        return super.onOptionsItemSelected(item);
    }



    private void initPages(){
        pages.add(new Page("Aktuellt", R.drawable.rlogo ,R.drawable.topbanner, "https://data.riksdagen.se/dokumentlista2/?avd=aktuellt&facets=3&sort=datum&sortorder=desc&lang=sv&cmskategori=startsida&utformat=xml&p="));
        pages.add(new Party("Socialdemokraterna", R.drawable.slogo,R.drawable.sbanner, "S"));
        pages.add(new Party("Moderaterna", R.drawable.mlogo,R.drawable.mbanner,  "M"));
        pages.add(new Party("Sverigedemokraterna",R.drawable.sdlogo,R.drawable.sdbanner, "SD"));
        pages.add(new Party("Miljöpartiet",R.drawable.mplogo,R.drawable.mpbanner, "MP"));
        pages.add(new Party("Centerpartiet",R.drawable.clogo,R.drawable.cbanner, "C"));
        pages.add(new Party("Vänsterpartiet",R.drawable.vlogo,R.drawable.vbanner, "V"));
        pages.add(new Party("Liberalerna",R.drawable.llogo,R.drawable.lbanner, "L"));
        pages.add(new Party("Kristdemokraterna",R.drawable.kdlogo, R.drawable.kdbanner,"KD"));
        pages.add(new Page("Riksdagsbeslut", R.drawable.betlogo, R.drawable.betbanner, "https://data.riksdagen.se/dokumentlista2/?avd=dokument&doktyp=bet&beslutad=1&sort=beslutsdag&sortorder=desc&utformat=xml&p="));
        pages.add(new Page("Voteringar",R.drawable.votlogo ,R.drawable.votbanner,"http://data.riksdagen.se/dokumentlista/?sok=&doktyp=votering&rm=&sort=dat&sortorder=desc&rapport=&utformat=xml&p="));
        pages.add(new Page("Kammarprotokoll", R.drawable.protlogo, R.drawable.protbanner, "http://data.riksdagen.se/dokumentlista/?sok=&doktyp=prot&sort=datum&sortorder=desc&utformat=xml&p="));
        pages.add(new AboutPage("Om Appen", R.drawable.abouticon ,R.drawable.aboutbanner,this));
    }


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
            drawerLayout.closeDrawer(Gravity.LEFT);
        }
        else {
            updater.handleBackButton();
        }
    }
}
