package com.example.mawuli.chasers;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mawuli on 11/14/16.
 */
class MyAdapter extends BaseAdapter {
    private  Context context;
    private List<String> items;

    public MyAdapter(Context context, List<String> values) {
        this.context = context;
        this.items = values;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return items.indexOf(items.get(i));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        LayoutInflater theInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = theInflater.inflate(R.layout.imagelayout,null);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.textView1);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView1);
        }else {
          viewHolder = (ViewHolder)convertView.getTag();
        }

        String item = items.get(position);

        viewHolder.textView.setText(item);
        return convertView;
    }

    private class ViewHolder{
        private TextView textView;
        private ImageView imageView;
    }
}
