package com.example.mybestvideo;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mybestvideo.page.AddCategory;
import com.example.mybestvideo.page.AddVideo;
import com.example.mybestvideo.page.ListVideo;

public class MainActivity extends AppCompatActivity {
    private static final int VIDEO_ADD_ID = R.id.video_add;
    private static final int CATEGORY_ADD_ID = R.id.category_add;
    private static final int VIDEO_LIST_ID = R.id.video_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadFragment(new ListVideo());
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //je voulais utlise switch mais il me dit "Constant expression required"
        /*

        switch (id) {
            case R.id.category_add:
                loadFragment(new AddVideo());
                return true;
            case R.id.category_add:
                loadFragment(new AddCategory());
                return true;
            case R.id.video_list:
                loadFragment(new ListVideo());
                return true;
            default:
                return super.onOptionsItemSelected(item);
            */
        if (id == R.id.video_add) {
            loadFragment(new AddVideo());
        } else if (id == R.id.category_add) {
            loadFragment(new AddCategory());
        }else if(id == R.id.video_list){
            loadFragment(new ListVideo());
        } else {
            return super.onOptionsItemSelected(item);
        }
        return  true;
    }
}
