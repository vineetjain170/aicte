package com.vscholars.stack2code.aicte_phaseone;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by vineet_jain on 8/6/17.
 */

public class AdapterForMainNavigationList extends BaseAdapter{
    Context context;
    String[] items;
    AdapterForMainNavigationList(Context context,String[] items){
        this.context=context;
        this.items=items;
    }
    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=((Activity)context).getLayoutInflater();
        convertView=layoutInflater.inflate(R.layout.label_main_navigation_drawer_list,null);
        TextView textView=(TextView)convertView.findViewById(R.id.x_label_main_navgation_drawer_list_text);
        textView.setText(items[position]);
        return convertView;
    }

}
