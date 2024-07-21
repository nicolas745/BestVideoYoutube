package com.example.mybestvideo.page;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mybestvideo.R;
import com.example.mybestvideo.database.DBVideo;
import com.example.mybestvideo.database.DBcategory;
import com.example.mybestvideo.database.Dao.CategoryDao;
import com.example.mybestvideo.database.Dao.VideoDao;
import com.example.mybestvideo.database.interfaces.Category;
import com.example.mybestvideo.database.interfaces.Video;
import com.example.mybestvideo.item.SpinnerItem;
import com.example.mybestvideo.item.VideoItem;
import com.example.mybestvideo.item.VideoItemAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class ListVideo extends Fragment {

    private ListView listView;
    private VideoItemAdapter adapter;
    private List<VideoItem> videoItemList;

    private CategoryDao categoryDao;
    private VideoDao videoDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBcategory bdC = DBcategory.getDatabase(getContext());
        categoryDao = bdC.categoryDao();
        DBVideo dbV = DBVideo.getDatabase(getContext());
        videoDao = dbV.videoDao();  // Initialisation de videoDao
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_list, container, false);

        Spinner spinner = view.findViewById(R.id.select_category);
        videoItemList = new ArrayList<>();

        Executors.newSingleThreadExecutor().execute(() -> {
            List<Category> categories = categoryDao.getAll();
            SpinnerItem[] items = convertCategoriesToSpinnerItems(categories);
            getActivity().runOnUiThread(() -> {
                ArrayAdapter<SpinnerItem> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
                spinner.setAdapter(adapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        SpinnerItem selectedItem = (SpinnerItem) parent.getItemAtPosition(position);
                        int selectedId = selectedItem.getId();

                        // Opération de base de données en arrière-plan
                        Executors.newSingleThreadExecutor().execute(() -> {
                            List<Video> videos = videoDao.getVideosByCategoryId(selectedId);
                            videoItemList = new ArrayList<>();

                            Log.d("video size", videos.size()+"");
                            for (Video video : videos) {
                                videoItemList.add(new VideoItem(video.name, "https://youtu.be/"+video.url, video.id));
                            }
                            // Mise à jour de l'interface utilisateur sur le thread principal
                            new Handler(Looper.getMainLooper()).post(() -> {
                                VideoItemAdapter newadaptera = new VideoItemAdapter(getContext(), videoItemList);
                                listView.setAdapter(newadaptera);
                                setListViewHeightBasedOnChildren(listView);
                            });
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            });
        });

        listView = view.findViewById(R.id.video_list);

        adapter = new VideoItemAdapter(getContext(), videoItemList);
        listView.setAdapter(adapter);

        setListViewHeightBasedOnChildren(listView);

        return view;
    }

    private SpinnerItem[] convertCategoriesToSpinnerItems(List<Category> categories) {
        List<SpinnerItem> itemsList = new ArrayList<>();
        for (Category category : categories) {
            itemsList.add(new SpinnerItem(category.getName(), category.getId()));
        }
        return itemsList.toArray(new SpinnerItem[0]);
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    private String[] convertCategoryNamesToArray(List<Category> categories) {
        List<String> categoryNamesList = new ArrayList<>();
        for (Category category : categories) {
            categoryNamesList.add(category.getName());
        }
        return categoryNamesList.toArray(new String[0]);
    }
}
