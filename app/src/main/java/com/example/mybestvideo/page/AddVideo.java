package com.example.mybestvideo.page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.mybestvideo.DBcategory;
import com.example.mybestvideo.R;
import com.example.mybestvideo.database.Dao.CategoryDao;
import com.example.mybestvideo.database.interfaces.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class AddVideo extends Fragment {
    private CategoryDao categoryDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_video, container, false);

        DBcategory db = DBcategory.getDatabase(getContext());
        categoryDao = db.categoryDao();

        Spinner spinner = view.findViewById(R.id.spinner);

        // Utiliser Executors pour effectuer l'opération en arrière-plan
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Category> categories = categoryDao.getAll();
            String[] items = convertCategoryNamesToArray(categories);
            getActivity().runOnUiThread(() -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
                spinner.setAdapter(adapter);
            });
        });

        return view;
    }

    private String[] convertCategoryNamesToArray(List<Category> categories) {
        List<String> categoryNamesList = new ArrayList<>();
        for (Category category : categories) {
            categoryNamesList.add(category.getName());
        }
        return categoryNamesList.toArray(new String[0]);
    }
}
