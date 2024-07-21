package com.example.mybestvideo.item;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mybestvideo.R;
import com.example.mybestvideo.page.AddVideo;
import com.example.mybestvideo.page.showvideo;

import java.util.List;

public class VideoItemAdapter extends BaseAdapter {
    private Context context;
    private List<VideoItem> videoItems;

    public VideoItemAdapter(Context context, List<VideoItem> videoItems) {
        // Vérifiez que le contexte est une instance de FragmentActivity
        if (!(context instanceof FragmentActivity)) {
            throw new IllegalArgumentException("Le contexte doit être une instance de FragmentActivity");
        }
        this.context = context;
        this.videoItems = videoItems;
    }

    @Override
    public int getCount() {
        Log.d("VideoItemAdapter", "Item count: " + videoItems.size());
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
        }

        TextView titreView = convertView.findViewById(R.id.titreView);
        TextView texteView = convertView.findViewById(R.id.texteView);
        Button button = convertView.findViewById(R.id.button1);

        VideoItem videoItem = videoItems.get(position);
        titreView.setText(videoItem.getTitre());
        texteView.setText(videoItem.getTexte());

        // Ajouter un écouteur de clic à chaque élément
        convertView.setOnClickListener(v -> {
        });

        // Ajouter un écouteur de clic au bouton
        button.setOnClickListener(v -> {
            Log.d("button", "Button cliqué sur : " + videoItem.getid());
            loadFragment(new showvideo(videoItem.getTexte()));
        });

        return convertView;
    }

    private void loadFragment(Fragment fragment) {
        FragmentActivity activity = (FragmentActivity) context;
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
