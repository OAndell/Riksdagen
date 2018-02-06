package com.example.oscar.riksdagen.MainModule.Menu;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oscar.riksdagen.MainModule.Pages.Page;
import com.example.oscar.riksdagen.MainModule.Pages.PageSuper;
import com.example.oscar.riksdagen.R;

import java.util.ArrayList;

/**
 * Created by gustavaaro on 2018-02-05.
 */

public class DrawerAdapter extends ArrayAdapter<PageSuper> {

    Context context;
    ArrayList<PageSuper> pages;

    public DrawerAdapter(@NonNull Context context, int resource, ArrayList<PageSuper> pages) {
        super(context, resource);
        this.context = context;
        this.pages = pages;
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @Nullable
    @Override
    public PageSuper getItem(int position) {
        return pages.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.drawer_item,parent,false);
        }
        System.out.println("PAGE");
        TextView title = (TextView) convertView.findViewById(R.id.drawer_title);
        ImageView ic = (ImageView) convertView.findViewById(R.id.drawer_logo);

        PageSuper page = getItem(position);


        title.setText(page.getName());
        ic.setImageDrawable(context.getResources().getDrawable(page.getSymbol()));

        return convertView;

    }
}