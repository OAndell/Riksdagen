package com.example.oscar.riksdagen.VotesModule;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oscar.riksdagen.R;
import com.example.oscar.riksdagen.ReadModule.ReadActivity;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Oscar on 2017-05-15.
 */

public class VoteActivity extends AppCompatActivity {

    private ArrayList<GraphView> graphs = new ArrayList<>();
    private static String fullTextUrl;
    private Context context;
    private ImageView toolbarIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        context = this;
        Intent myIntent = getIntent();
        String pageDesc = myIntent.getStringExtra("pageDesc");
        TextView summaryTextView = (TextView) findViewById(R.id.infoView);
        summaryTextView.setTextColor(Color.BLACK);
        downloadSummaryText(pageDesc, summaryTextView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarIcon = (ImageView) findViewById(R.id.toolbar_icon);
        toolbarIcon.setImageDrawable(getResources().getDrawable(R.drawable.votbanner));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Resultat");

        GraphView graphS = (GraphView) findViewById(R.id.votegraphS);
        graphs.add(graphS);
        GraphView graphM = (GraphView) findViewById(R.id.votegraphM);
        graphs.add(graphM);
        GraphView graphSD = (GraphView) findViewById(R.id.votegraphSD);
        graphs.add(graphSD);
        GraphView graphMP = (GraphView) findViewById(R.id.votegraphMP);
        graphs.add(graphMP);
        GraphView graphC = (GraphView) findViewById(R.id.votegraphC);
        graphs.add(graphC);
        GraphView graphV = (GraphView) findViewById(R.id.votegraphV);
        graphs.add(graphV);
        GraphView graphL = (GraphView) findViewById(R.id.votegraphL);
        graphs.add(graphL);
        GraphView graphKD = (GraphView) findViewById(R.id.votegraphKD);
        graphs.add(graphKD);
        GraphView graphTotal = (GraphView) findViewById(R.id.votegraphTotal);
        graphs.add(graphTotal);

        //Set no background grid and no vertical labels for all graphs
        for (int i = 0; i < graphs.size(); i++) {
            graphs.get(i).getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
            graphs.get(i).getGridLabelRenderer().setVerticalLabelsVisible(false);
        }


        TextView descTextView = (TextView) findViewById(R.id.descText);
        descTextView.setTextColor(Color.BLACK);
        descTextView.setText(pageDesc.split("betänkande\\s(\\d+\\/\\d{1,2}\\:[^\\s]+)")[1]);

        //Download and setup bargraphs
        new VoteTableDownloader(myIntent.getStringExtra("pageURL"),graphs).execute();

        //init "go back" button

        //init read more button
        final TextView readMoreButton = (TextView) findViewById(R.id.readMoreTextView);
        readMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent readPage = new Intent(context, ReadActivity.class);
                readPage.putExtra("url",fullTextUrl);
                readPage.putExtra("bannerImage",R.drawable.votbanner);
                context.startActivity(readPage);
            }
        });

    }


    public static void setupGraph(final GraphView graph, int data1, int data2, int data3, int data4, boolean hideLabels){
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(0,  data1),
                new DataPoint(1,  data2),
                new DataPoint(2,  data3),
                new DataPoint(3,  data4)
        });
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                if(data.getX() == 0){
                    return Color.rgb(105, 254, 0);
                }
                if(data.getX() == 1){
                    return Color.RED;
                }
                if(data.getX() == 2){
                    return Color.GRAY;
                }
                if(data.getX() == 3){
                    return Color.DKGRAY;
                }
                return 0;
            }
        });

        series.setValuesOnTopColor(Color.BLACK);
        graph.addSeries(series);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(-1);
        graph.getViewport().setMaxX(4);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"","Ja","Nej","Avst.","Frånv.",""});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        if(hideLabels){
            graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
            graph.getViewport().setYAxisBoundsManual(true);
            graph.getViewport().setMaxY(110);
            graph.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BarGraphSeries<DataPoint> series = (BarGraphSeries<DataPoint>) graph.getSeries().get(0);
                    if(series.isDrawValuesOnTop()){
                        series.setDrawValuesOnTop(false);
                    }
                    else {
                        series.setDrawValuesOnTop(true);
                    }
                    graph.invalidate();
                }
            });
        }
        else{
            series.setDrawValuesOnTop(true);
            graph.setTitleTextSize(50);
            graph.setTitle("Resultat");
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return true;
    }

    private static void downloadSummaryText(String pageDesc, TextView summaryTextView){
        Pattern pattern = Pattern.compile("betänkande\\s(\\d+\\/\\d{1,2}\\:[^\\s]+)");
        Matcher matcher = pattern.matcher(pageDesc);
        if (matcher.find())
        {
            new VoteSummaryDownloader(matcher.group(1), summaryTextView).execute();
        }
    }

    public static void setFullTextUrl(String url){
        fullTextUrl = url;
    }

}
