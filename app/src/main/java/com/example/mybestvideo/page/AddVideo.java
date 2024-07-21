package com.example.mybestvideo.page;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.example.mybestvideo.R;
import com.example.mybestvideo.database.DBVideo;
import com.example.mybestvideo.database.interfaces.Video;
import com.example.mybestvideo.database.Dao.VideoDao;
import com.example.mybestvideo.database.DBcategory;
import com.example.mybestvideo.database.Dao.CategoryDao;
import com.example.mybestvideo.database.interfaces.Category;
import com.example.mybestvideo.item.SpinnerItem;
import com.example.mybestvideo.item.VideoItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class AddVideo extends Fragment {
    private CategoryDao categoryDao;
    private VideoDao videoDao;

    private EditText titleVideoEditText;
    private EditText urlEditText;
    private Spinner categorySpinner;
    private int id_select;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        DBcategory bdC = DBcategory.getDatabase(getContext());
        categoryDao = bdC.categoryDao();
        DBVideo DBV = DBVideo.getDatabase(getContext());
        videoDao = DBV.videoDao();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_video, container, false);

        Spinner spinner = view.findViewById(R.id.spinner);

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
                        id_select = selectedItem.getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            });
        });

        titleVideoEditText = view.findViewById(R.id.title_video);
        urlEditText = view.findViewById(R.id.valueurl);
        categorySpinner = view.findViewById(R.id.spinner);

        Button buttonClickMe = view.findViewById(R.id.submit_video_add);
        buttonClickMe.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String title = titleVideoEditText.getText().toString().trim();
                String url = urlEditText.getText().toString().trim();

                int categoryId = id_select;
                Log.d("AddVideo", "Title: " + title);
                Log.d("AddVideo", "URL: " + url);
                Log.d("AddVideo", "Category ID: " + categoryId);

                if (!title.isEmpty() && !url.isEmpty()) {

                    Executors.newSingleThreadExecutor().execute(() -> {
                        Video video = new Video(title, url, (int) id_select);
                        try {
                            videoDao.insertAll(video);
                        } catch (Exception e) {
                            Log.e("AddVideo", "Error inserting video", e);
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "empty input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
    private SpinnerItem[] convertCategoriesToSpinnerItems(List<Category> categories) {
        List<SpinnerItem> itemsList = new ArrayList<>();
        for (Category category : categories) {
            itemsList.add(new SpinnerItem(category.getName(), category.getId()));
        }
        return itemsList.toArray(new SpinnerItem[0]);
    }
    private String[] convertCategoryNamesToArray(List<Category> categories) {
        List<String> categoryNamesList = new ArrayList<>();
        for (Category category : categories) {
            categoryNamesList.add(category.getName());
        }
        return categoryNamesList.toArray(new String[0]);
    }
}
