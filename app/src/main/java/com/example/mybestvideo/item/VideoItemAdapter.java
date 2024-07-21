package com.example.mybestvideo.item;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class VideoItemAdapter extends BaseAdapter {
    private Context context;
    private List<VideoItem> videoItems;

    public VideoItemAdapter(Context context, List<VideoItem> videoItems) {
        this.context = context;
        this.videoItems = videoItems;
    }

    @Override
    public int getCount() {
        Log.d("dddddddddd",""+videoItems.size());
        return videoItems.size();
    }

    @Override
    public Object getItem(int position) {
        return videoItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false);
        }

        TextView titreView = convertView.findViewById(android.R.id.text1);
        TextView texteView = convertView.findViewById(android.R.id.text2);

        VideoItem videoItem = videoItems.get(position);
        titreView.setText(videoItem.getTitre());
        texteView.setText(videoItem.getTexte());

        return convertView;
    }
}
