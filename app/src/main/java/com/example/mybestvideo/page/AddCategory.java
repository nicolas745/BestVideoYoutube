package com.example.mybestvideo.page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mybestvideo.R;
import com.example.mybestvideo.database.DBcategory;
import com.example.mybestvideo.database.Dao.CategoryDao;
import com.example.mybestvideo.database.interfaces.Category;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddCategory extends Fragment {
    private CategoryDao categoryDao;
    private ExecutorService executorService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBcategory db = DBcategory.getDatabase(getContext());
        categoryDao = db.categoryDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_category, container, false);

        EditText editTextCategory = view.findViewById(R.id.category_value);

        Button buttonClickMe = view.findViewById(R.id.submit_category);
        buttonClickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = editTextCategory.getText().toString();
                if (!category.isEmpty()) {
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            categoryDao.insertAll(new Category(category));
                            //je n'ai pas comprix pourquoi que ca marche dans se cas quand je rajouter getActivity ... je voulais juste ajouter Toest
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "add sucefull: " + category, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "empty category", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}
